package jp.terasoluna.batch.functionsample.b001;

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
 * 同期型ジョブ・トランザクション管理機能のサンプル１<br>
 * <p>
 * 事前準備：EMPLOYEEテーブルを作成しており、<br>
 *１件以上のデータが存在すること<br>
 * </p>
 * <p>
 * サンプル内容：入力データ取得機能を使用し、DBを参照し、DBを更新するサンプル<br>
 * AbstractTransactionBLogicを継承しフレームワーク側にトランザクション管理を任せる<br>
 * (データは全件一括に更新する)<br>
 * 処理終了後は、すべてのデータが鈴木太郎に書き換えられる<br>
 * </p>
 */
@Component
public class B001001BLogic extends AbstractTransactionBLogic {

    private Log log = LogFactory.getLog(B001001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Autowired
    @Qualifier("queryRowHandleDAO")
    private QueryRowHandleDAO queryRowHandleDAO = null;

    @Autowired
    @Qualifier("updateDAO")
    private UpdateDAO updateDAO = null;

    public int doMain(BLogicParam arg0) {

        Collector<B001001Param> collector = null;
        BatchUpdateSupport bus = new BatchUpdateSupportImpl();

        try {
            B001001Param param = new B001001Param();
            collector = new DBCollector<B001001Param>(queryRowHandleDAO,
                    "B001001.selectEmployee", param);

            while (collector.hasNext()) {
                B001001Param data = collector.next();

                if (log.isInfoEnabled()) {
                    log.info("ID:" + data.getId() + " FIMILYNAME:"
                            + data.getFamilyName() + " FIRSTNAME:"
                            + data.getFirstName() + " AGE:" + data.getAge());
                }

                data.setFamilyName("鈴木");
                data.setFirstName("太郎");

                bus.addBatch("B001001.updateEmployee", data);

            }
            bus.executeBatch(updateDAO);

        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            CollectorUtility.closeQuietly(collector);
        }
        return BATCH_NORMAL_END;

    }

}
