package jp.terasoluna.batch.functionsample.b002;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportImpl;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DBCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * そして取得したデータをバッチ更新最適化機能を使用し、<br>
 * EMPLOYEE2テーブルに挿入するサンプル<br>
 * </p>
 */
@Component
public class B002001BLogic extends AbstractTransactionBLogic {

    private static Log logger = LogFactory.getLog(B002001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Autowired
    @Qualifier("queryRowHandleDAO")
    private QueryRowHandleDAO queryRowHandleDAO = null;

    @Autowired
    @Qualifier("updateDAO")
    private UpdateDAO updateDAO = null;

    @Override
    public int doMain(BLogicParam arg0) {
        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Start : (");
            sb.append("ThreadId:[");
            sb.append(Thread.currentThread().getId());
            sb.append("],");
            sb.append("ThreadName:[");
            sb.append(Thread.currentThread().getName());
            sb.append("])");
            logger.info(sb.toString());
        }

        // コレクタ生成(入力データ取得機能)
        Collector<B002001Param> collector = new DBCollector<B002001Param>(
                queryRowHandleDAO, "B002001.selectEmployeeList", null);

        // バッチ更新サポート生成(バッチ更新最適化機能)
        BatchUpdateSupport bus = new BatchUpdateSupportImpl();

        try {
            while (collector.hasNext()) {
                B002001Param data = collector.next();
                logger.info("ID:" + data.getId() + " FIMILYNAME:"
                        + data.getFamilyName() + " FIRSTNAME:"
                        + data.getFirstName() + " AGE:" + data.getAge());

                bus.addBatch("B002001.insertEmployee", data);
            }

            // バッチ更新実行
            bus.executeBatch(updateDAO);

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
