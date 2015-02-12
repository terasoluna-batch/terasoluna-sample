package jp.terasoluna.batch.functionsample.b009.b009002;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.functionsample.b009.CustomCollectorExceptionHandler;
import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DaoValidateCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

/**
 * 入力データ取得機能を使用した際の拡張例外ハンドリングのサンプル<br>
 * <p>
 * 事前準備：C:\tmp配下にinputB009001.csvファイルを配置すること(DB初期化用)<br>
 * </p>
 * <p>
 * サンプル内容：EMPLOYEE3テーブルを読み取り、EMPLOYEE2テーブルに出力する。<br>
 * 拡張例外ハンドリングを使用し、ステータスにはENDを返却する。<br>
 * (エラーはValidationErrorExceptionが投げられるように実装している)<br>
 * 入力チェックエラーが発生した時点で終了するが、終了ステータスは100をセットする。<br>
 * (タイミングによって、EMPLOYEE2テーブルに挿入される件数は変動する)
 * </p>
 */
@Component
public class B009002BLogic extends AbstractTransactionBLogic {

    private Logger log = LoggerFactory.getLogger(B009002BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Inject
    B009002BatchDao dao;

    @Inject
    @Named("beanValidator")
    Validator beanValidator;

    public int doMain(BLogicParam arg0) {

        log.info("EMPLOYEE2テーブル初期化:開始");

        dao.deleteEmployee2();

        log.info("EMPLOYEE2テーブル初期化:終了");

        int returnCode = BATCH_NORMAL_END;

        int insertCount = 0;

        log.info("EMPLOYEE3テーブル読み込み:開始");

        CustomCollectorExceptionHandler cceHandler = new CustomCollectorExceptionHandler();

        Collector<CsvRecord> collector = new DaoValidateCollector<CsvRecord>(
                dao, "collectEmployee", CsvRecord.class, 20, cceHandler,
                beanValidator);

        try {

            while (collector.hasNext()) {
                CsvRecord csvRecord = collector.next();
                if (csvRecord != null) {
                    log.info("NAME:{}", csvRecord.getFamilyName());
                    // バッチ更新に追加
                    dao.insertEmployee2(csvRecord);
                    insertCount++;
                }
            }

        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);
        }

        log.info("EMPLOYEE2テーブル:{}件挿入しました。", insertCount);

        // １件でもエラーが発生した場合は、"100"を返却する。
        if (cceHandler.getErrorFieldCount() > 0) {
            return 100;
        }

        // 正常終了
        return returnCode;
    }
}
