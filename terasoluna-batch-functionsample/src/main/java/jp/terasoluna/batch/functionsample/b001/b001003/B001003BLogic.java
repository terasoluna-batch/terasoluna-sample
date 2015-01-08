package jp.terasoluna.batch.functionsample.b001.b001003;

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
 * 同期型ジョブ・トランザクション管理機能のサンプル３<br>
 * <p>
 * 事前準備：EMPLOYEEテーブルを作成しており、<br>
 * １件以上のデータが存在すること<br>
 * </p>
 * <p>
 * サンプル内容：入力データ取得機能を使用し、DBを参照し、DBをバッチ更新するサンプル<br>
 * AbstractTransactionBLogicを継承しフレームワーク側にトランザクション管理を任せる<br>
 * (データは全件一括に更新する)<br>
 * 処理終了後は、すべてのデータが鈴木太郎に書き換えられる<br>
 * </p>
 */
@Component
public class B001003BLogic extends AbstractTransactionBLogic {

    private Logger log = LoggerFactory.getLogger(B001003BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Inject
    private B001003BatchDao dao;

    public int doMain(BLogicParam arg0) {

        Collector<B001003Param> collector = null;

        try {
            B001003Param param = new B001003Param();
            collector = new DBCollector<B001003Param>(dao, "collectEmployee",
                    param);

            while (collector.hasNext()) {
                B001003Param data = collector.next();

                if (log.isInfoEnabled()) {
                    log.info("ID:" + data.getId() + " FIMILYNAME:"
                            + data.getFamilyName() + " FIRSTNAME:"
                            + data.getFirstName() + " AGE:" + data.getAge());
                }

                data.setFamilyName("鈴木");
                data.setFirstName("太郎");

                dao.updateEmployee(data);
            }
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            CollectorUtility.closeQuietly(collector);
        }
        return BATCH_NORMAL_END;

    }

}
