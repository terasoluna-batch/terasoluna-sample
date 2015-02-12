package jp.terasoluna.batch.functionsample.b001.b001004;

import javax.inject.Inject;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DaoCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

/**
 * 同期型ジョブ・トランザクション管理機能のサンプル４<br>
 * <p>
 * 事前準備：EMPLOYEEテーブルを作成しており、<br>
 * １件以上のデータが存在すること<br>
 * </p>
 * <p>
 * サンプル内容：入力データ取得機能を使用し、DBを参照し、DBをバッチ更新するサンプル<br>
 * BLogicを継承し、ビジネスロジック内でトランザクションの管理を行う<br>
 * (データは10件毎に更新する)<br>
 * 処理終了後は、すべてのデータが鈴木太郎に書き換えられる<br>
 * </p>
 */
@Component
public class B001004BLogic implements BLogic {

    private Logger log = LoggerFactory.getLogger(B001004BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Inject
    B001004BatchDao dao;

    @Inject
    PlatformTransactionManager transactionManager;

    public int execute(BLogicParam arg0) {

        TransactionStatus outerStat = null;
        Collector<B001004Param> collector = null;

        outerStat = BatchUtil.startTransaction(transactionManager);

        try {
            B001004Param param = new B001004Param();

            collector = new DaoCollector<B001004Param>(dao, "collectEmployee",
                    param);

            // REQUIRES_NEWのトランザクション定義を生成
            TransactionDefinition def = BatchUtil.getTransactionDefinition(
                    TransactionDefinition.PROPAGATION_REQUIRES_NEW,
                    TransactionDefinition.ISOLATION_DEFAULT,
                    TransactionDefinition.TIMEOUT_DEFAULT, false);

            TransactionStatus innerStat = null;

            try {
                // トランザクション開始
                innerStat = BatchUtil.startTransaction(transactionManager, def);

                int updateCount = 0;
                int commitCount = 0;

                while (collector.hasNext()) {
                    B001004Param data = collector.next();

                    if (log.isInfoEnabled()) {
                        log.info("ID:{} FAMILYNAME:{} FIRSTNAME:{} AGE:{}",
                                data.getId(), data.getFamilyName(),
                                data.getFirstName(), data.getAge());
                    }

                    data.setFamilyName("鈴木");
                    data.setFirstName("太郎");

                    dao.updateEmployee(data);
                    updateCount++;

                    // 10件ごとにバッチ更新実行とコミット
                    if (updateCount % 10 == 0) {
                        log.info("バッチ更新実行とコミット");
                        // トランザクションコミットし再開始する
                        innerStat = BatchUtil.commitRestartTransaction(
                                transactionManager, innerStat, def);
                        commitCount++;
                    }
                }

                BatchUtil.commitTransaction(transactionManager, innerStat);
                BatchUtil.commitTransaction(transactionManager, outerStat);
            } catch (BatchException e) {
                throw e;
            } catch (Exception e) {
                throw new BatchException(e);
            } finally {
                BatchUtil.endTransaction(transactionManager, innerStat);
            }
        } catch (BatchException e) {
            throw e;
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            CollectorUtility.closeQuietly(collector);
            BatchUtil.endTransaction(transactionManager, outerStat);
        }

        return BATCH_NORMAL_END;

    }
}
