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
package jp.terasoluna.batch.tutorial.sample003;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;
import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileValidateCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

/**
 * ビジネスロジッククラス。(CSVファイルを読み込み、DBにデータを挿入する）
 */
@Component
public class SMP003BLogic extends AbstractTransactionBLogic {

    private static final Logger log = LoggerFactory
            .getLogger(SMP003BLogic.class);

    @Inject
    protected SMP003Dao dao;

    @Inject
    @Named("csvFileQueryDAO")
    protected FileQueryDAO csvFileQueryDAO;

    @Inject
    @Named("jsr303BeanValidator")
    protected Validator validator;

    public int doMain(BLogicParam param) {

        // ジョブ終了コード(0:正常終了、-1:異常終了）
        int returnCode = 0;

        CustomValidationErrorHandler customValidationErrorHandler = new CustomValidationErrorHandler();

        // コレクタ
        Collector<NyusyukkinData> collector = new FileValidateCollector<NyusyukkinData>(
                this.csvFileQueryDAO, "inputFile/SMP003_input.csv",
                NyusyukkinData.class, validator, customValidationErrorHandler);

        try {
            // ファイルから取得したデータを格納するオブジェクト
            NyusyukkinData inputData = null;
            while (collector.hasNext()) {

                // ファイルからデータを取得
                inputData = collector.next();

                // DB更新処理
                dao.insertNyusyukkinData(inputData);
            }
        } catch (DataAccessException e) {
            if (log.isErrorEnabled()) {
                log.error("データアクセスエラーが発生しました", e);
            }

            returnCode = -1;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("エラーが発生しました", e);
            }

            returnCode = -1;
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);

            // 正常終了時にログ残し
            if (returnCode == 0 && log.isInfoEnabled()) {
                log.info("DBの更新が正常に終了しました。");
            }
        }

        return returnCode;
    }
}
