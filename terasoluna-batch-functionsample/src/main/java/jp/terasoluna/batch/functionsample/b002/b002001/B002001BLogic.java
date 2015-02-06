package jp.terasoluna.batch.functionsample.b002.b002001;

import javax.inject.Inject;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DBCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 非同期型ジョブのサンプル<br>
 * <p>
 * 事前準備：EMPLOYEEテーブルに１件以上のデータが存在すること<br>
 * EMPLOYEE2テーブルが存在しておりデータが存在しないこと<br>
 * </p>
 * <p>
 * サンプル内容：入力データ取得機能を使用し、<br>
 * EMPLOYEEテーブルのすべてのデータを取得する。<br>
 * そして取得したデータをEMPLOYEE2テーブルに挿入するサンプル<br>
 * </p>
 */
@Component
public class B002001BLogic extends AbstractTransactionBLogic {

    private static Logger logger = LoggerFactory.getLogger(B002001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Inject
    B002001BatchDao dao;

    @Override
    public int doMain(BLogicParam arg0) {
        if (logger.isInfoEnabled()) {
            logger.info("Start : (ThreadId:[{}],ThreadName:[{}])",
                    Thread.currentThread().getId(), Thread.currentThread().getName());
        }

        // コレクタ生成(入力データ取得機能)
        Collector<B002001Param> collector = new DBCollector<B002001Param>(dao,
                "collectEmployee", null);

        try {
            while (collector.hasNext()) {
                B002001Param data = collector.next();
                logger.info("ID:" + data.getId() + " FIMILYNAME:"
                        + data.getFamilyName() + " FIRSTNAME:"
                        + data.getFirstName() + " AGE:" + data.getAge());

                dao.insertEmployee2(data);
            }

        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // ファイルのクローズ
            CollectorUtility.closeQuietly(collector);
        }

        // 正常終了
        return BATCH_NORMAL_END;
    }
}
