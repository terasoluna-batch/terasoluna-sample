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
package jp.terasoluna.batch.tutorial.sample004;

import java.util.Date;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.tutorial.common.NyusyukkinData;
import jp.terasoluna.batch.tutorial.common.NyusyukkinFileOutput;
import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.collector.util.ControlBreakChecker;
import jp.terasoluna.fw.file.dao.FileLineWriter;
import jp.terasoluna.fw.file.dao.FileQueryDAO;
import jp.terasoluna.fw.file.dao.FileUpdateDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * ビジネスロジッククラス。(CSVファイルを読み込み、csvファイルに出力するクラス)
 */
@Component
public class SMP004BLogic implements BLogic {

    private static final Logger log = LoggerFactory.getLogger(SMP004BLogic.class);

    @Inject
    @Named("csvFileQueryDAO")
    protected FileQueryDAO csvFileQueryDAO;

    @Inject
    @Named("csvFileUpdateDAO")
    protected FileUpdateDAO csvFileUpdateDAO;

    public int execute(BLogicParam param) {

        // ジョブ終了コード(0:正常終了、255:異常終了)
        int returnCode = 0;

        // コレクタ
        Collector<NyusyukkinData> collector = new FileCollector<NyusyukkinData>(
                this.csvFileQueryDAO, "inputFile/SMP004_input.csv",
                NyusyukkinData.class);

        // ファイル出力用行ライタの取得
        FileLineWriter<NyusyukkinFileOutput> fileLineWriter = csvFileUpdateDAO
                .execute("outputFile/SMP004_output.csv",
                        NyusyukkinFileOutput.class);

        try {
            // ファイルから取得したデータを格納するオブジェクト
            NyusyukkinData inputData = null;

            // 入金のカウント用
            int nyukinNum = 0;
            // 出金のカウント用
            int syukkinNum = 0;
            // 入金合計用
            int nyukinSum = 0;
            // 出金合計用
            int syukkinSum = 0;

            while (collector.hasNext()) {
                // ファイルからデータを取得
                inputData = collector.next();

                // コントロールブレイク判定
                // 支店名、取引日に変更がある場合
                boolean ctrlBreak = ControlBreakChecker.isBreak(collector,
                        "torihikibi", "shitenName");

                // 入出金区分のカウント、合計計算
                if (inputData != null && inputData.getNyusyukkinKubun() == 0) {
                    syukkinNum++;
                    syukkinSum += inputData.getKingaku();
                } else if (inputData != null
                        && inputData.getNyusyukkinKubun() == 1) {
                    nyukinNum++;
                    nyukinSum += inputData.getKingaku();
                }

                // コントロールブレイク処理
                if (ctrlBreak) {
                    // キーデータをマップで取得
                    Map<String, Object> brkMap = ControlBreakChecker
                            .getBreakKey(collector, "torihikibi", "shitenName");
                    Date torihikibi = null;
                    String shitenName = null;
                    if (brkMap.containsKey("torihikibi")) {
                        torihikibi = (Date) brkMap.get("torihikibi");
                    } else {
                        torihikibi = inputData.getTorihikibi();
                    }
                    if (brkMap.containsKey("shitenName")) {
                        shitenName = (String) brkMap.get("shitenName");
                    } else {
                        shitenName = inputData.getShitenName();
                    }

                    // コントロールブレイク時のデータの作成
                    NyusyukkinFileOutput outputData = new NyusyukkinFileOutput();
                    outputData.setTorihikibi(torihikibi);
                    outputData.setShitenName(shitenName);
                    outputData.setNyukinNum(nyukinNum);
                    outputData.setNyukinSum(nyukinSum);
                    outputData.setSyukkinNum(syukkinNum);
                    outputData.setSyukkinSum(syukkinSum);

                    // データをファイルへ出力（1行）
                    fileLineWriter.printDataLine(outputData);

                    // 入出金区分カウントの初期化
                    nyukinNum = 0;
                    syukkinNum = 0;
                    nyukinSum = 0;
                    syukkinSum = 0;
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
            CollectorUtility.closeQuietly(fileLineWriter);

            // 正常終了時のログ
            if (returnCode == 0 && log.isInfoEnabled()) {
                log.info("ファイル書き込みが正常に終了しました。");
            }
        }

        return returnCode;
    }
}
