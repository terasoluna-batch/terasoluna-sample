package jp.terasoluna.batch.functionsample.b008.b008001;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.functionsample.b008.ZipCode;
import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.collector.util.ControlBreakChecker;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * コントロールブレイク機能のサンプル<br>
 * <p>
 * 事前準備：C:\tmp配下にKEN_ALL.CSVファイルを配置すること<br>
 * </p>
 * <p>
 * KEN_ALL.CSVは、下記の日本郵政グループのホームページからダウンロードできます。<br>
 * {@link http://www.post.japanpost.jp/zipcode/download.html}
 * </p>
 * <p>
 * サンプル内容：ファイルを読み込み、DBに更新するサンプル。 <br>
 * 前処理では複数のブレイクキーを使用し、コントロールブレイク発生時に<br>
 * ログへのヘッダ出力、市町村数のカウントを行っている。<br>
 * 後処理では単一のブレイクキーを用いて、コントロールブレイクの発生の際にバッチ更新を行う。<br>
 * </p>
 */
@Component
public class B008001BLogic implements BLogic {

    private Logger log = LoggerFactory.getLogger(B008001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    private static final String INPUT_FILE = "C:\\tmp\\KEN_ALL.CSV";

    @Inject
    @Named("csvFileQueryDAO")
    FileQueryDAO csvFileQueryDAO;

    @Inject
    B008001BatchDao dao;

    @Inject
    PlatformTransactionManager transactionManager;

    public int execute(BLogicParam param) {
        TransactionStatus stat = null;

        // ////////////
        // 初期化
        log.info("ZipCodeテーブル初期化:開始");

        try {
            // トランザクション開始
            stat = BatchUtil.startTransaction(transactionManager);

            // ZipCodeテーブルの初期化
            dao.deleteZipCode();

            // トランザクションコミット
            BatchUtil.commitTransaction(transactionManager, stat);
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // トランザクション終了
            BatchUtil.endTransaction(transactionManager, stat);
        }

        log.info("ZipCodeテーブル初期化:終了");

        Collector<ZipCode> collector = null;

        // ////////////
        // 登録処理
        log.info("KEN_ALL.CSVファイル読み込み:開始");

        try {
            int municipalDistrictCnt = 0;
            int townRegionCnt = 0;
            int insertCount = 0;

            // トランザクション開始
            stat = BatchUtil.startTransaction(transactionManager);

            collector = new FileCollector<ZipCode>(csvFileQueryDAO, INPUT_FILE,
                    ZipCode.class);

            while (collector.hasNext()) {
                ZipCode record = collector.next();
                townRegionCnt++;

                // コントロールブレイク（ヘッダ側）判定
                boolean preCtrlBreak = ControlBreakChecker.isPreBreak(
                        collector, "adminDivisions", "municipalDistrict");

                // コントロールブレイク（フッタ側）判定
                boolean ctrlBreak = ControlBreakChecker.isBreak(collector,
                        "adminDivisions");

                // コントロールブレイク（ヘッダ側）処理
                if (preCtrlBreak) {

                    Map<String, Object> pbMap = ControlBreakChecker
                            .getPreBreakKey(collector, "adminDivisions",
                                    "municipalDistrict");

                    // 市区町村名が変わった時にカウントする
                    if (pbMap.containsKey("municipalDistrict")) {
                        municipalDistrictCnt++;
                    }

                    // 都道府県名が変わった時にログにヘッダを出力する
                    if (log.isInfoEnabled()
                            && pbMap.containsKey("adminDivisions")) {
                        log.info("=========================");
                        log.info(String.valueOf(pbMap.get("adminDivisions")));
                    }

                }

                // バッチ更新に追加
                dao.insertZipCode(record);
                insertCount++;

                // コントロールブレイク（フッタ側）処理
                if (ctrlBreak) {
                    Map<String, Object> brkMap = ControlBreakChecker
                            .getBreakKey(collector, "adminDivisions");

                    // 都道府県名が変わった時にバッチ更新を実行する
                    if (brkMap.containsKey("adminDivisions")) {
                        log.info("バッチ更新実行 {}件 市区町村数 {}件 町域数 {}件", insertCount,
                                municipalDistrictCnt, townRegionCnt);

                        // トランザクションコミットとトランザクション開始(バッチ更新実行)
                        stat = BatchUtil.commitRestartTransaction(
                                transactionManager, stat);

                        // カウンタリセット
                        municipalDistrictCnt = 0;
                        townRegionCnt = 0;
                        insertCount = 0;
                    }
                }
            }

            // トランザクションコミット(バッチ更新実行)
            BatchUtil.commitTransaction(transactionManager, stat);
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);

            // トランザクション終了
            BatchUtil.endTransaction(transactionManager, stat);
        }

        log.info("KEN_ALL.CSVファイル読み込み:終了");

        // 正常終了
        return BATCH_NORMAL_END;
    }
}
