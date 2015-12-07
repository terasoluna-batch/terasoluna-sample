/*
 * Copyright (c) 2015 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.terasoluna.batch.tutorial.sample001;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;
import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DaoCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.dao.FileUpdateDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * ビジネスロジッククラス。(入出金テーブルをcsvファイルに出力するクラス)
 */
@Component
public class SMP001BLogic implements BLogic {

    private static final Logger log = LoggerFactory
            .getLogger(SMP001BLogic.class);

    @Inject
    protected SMP001Dao dao;

    @Inject
    @Named("csvFileUpdateDAO")
    protected FileUpdateDAO csvFileUpdateDAO;

    public int execute(BLogicParam param) {

        // ジョブ終了コード（0:正常終了、255:異常終了）
        int returnCode = 0;

        // コレクタ
        Collector<NyusyukkinData> collector = new DaoCollector<NyusyukkinData>(
                this.dao, "collectNyusyukkinData", null);

        // ファイル出力用行ライタの取得(入金用)
        FileLineWriter<NyusyukkinData> fileLineWriterNyukin = csvFileUpdateDAO
                .execute("outputFile/SMP001_output_nyukin.csv",
                        NyusyukkinData.class);

        // ファイル出力用行ライタの取得(出金用)
        FileLineWriter<NyusyukkinData> fileLineWriterSyukkin = csvFileUpdateDAO
                .execute("outputFile/SMP001_output_syukkin.csv",
                        NyusyukkinData.class);

        try {
            // DBから取得したデータを格納するオブジェクト
            NyusyukkinData inputData = null;

            while (collector.hasNext()) {
                // DBからデータを取得
                inputData = collector.next();

                // ファイルへデータを出力（1行）
                // 入出金区分により出力ファイルを変更
                if (inputData != null && inputData.getNyusyukkinKubun() == 0) {
                    fileLineWriterNyukin.printDataLine(inputData);
                }
                if (inputData != null && inputData.getNyusyukkinKubun() == 1) {
                    fileLineWriterSyukkin.printDataLine(inputData);
                }
            }
        } catch (DataAccessException e) {
            if (log.isErrorEnabled()) {
                log.error("データアクセスエラーが発生しました", e);
            }

            returnCode = 255;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("エラーが発生しました", e);
            }

            returnCode = 255;
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);

            // ファイルのクローズ
            CollectorUtility.closeQuietly(fileLineWriterNyukin);
            CollectorUtility.closeQuietly(fileLineWriterSyukkin);

            // 正常終了時のログ
            if (returnCode == 0 && log.isInfoEnabled()) {
                log.info("ファイル書き込みが正常に終了しました。");
            }
        }

        return returnCode;
    }
}
