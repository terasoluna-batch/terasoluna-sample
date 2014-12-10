package jp.terasoluna.batch.functionsample.b006;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportImpl;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.dao.UpdateDAO;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * バッチ更新最適化機能のサンプル<br>
 * <p>
 * 事前準備：PERSONAL_INFORMATIONテーブルが存在し、101～300までのデータが存在する事<br>
 * </p>
 * <p>
 * サンプル内容：insert,delete,update,insert...の順に格納したSQLが<br>
 * バッチ更新実行時にinsert100件,delete100件,update100件の順に<br>
 * 最適化されていることを確認するサンプル<br>
 * </p>
 */
@Component
public class B006001BLogic implements BLogic {

    private Log log = LogFactory.getLog(B006001BLogic.class);

    @Autowired
    private UpdateDAO updateDAO;

    @Autowired
    @Qualifier(value = "csvFileQueryDAO")
    private FileQueryDAO csvFileQueryDAO = null;

    @Autowired
    private PlatformTransactionManager transactionManager = null;

    public int execute(BLogicParam param) {

        TransactionStatus stat = null;

        // 処理件数
        int count = 0;

        // delete&update用codeの値
        int i = 101;

        BatchUpdateSupport bus = new BatchUpdateSupportImpl(updateDAO);

        // ファイル入力用コレクタの取得
        Collector<SampleFileLineObject> collector = new FileCollector<SampleFileLineObject>(
                csvFileQueryDAO, "C:\\tmp\\inputB006.csv",
                SampleFileLineObject.class);

        try {
            // トランザクション開始
            stat = BatchUtil.startTransaction(transactionManager);
            while (collector.hasNext()) {
                // データ部の読み込み
                SampleFileLineObject insertData = collector.next();

                // insert(00001～00100)
                bus.addBatch("B006001.insertData01", insertData);

                // delete(00101～00200)
                SampleFileLineObject deleteData = new SampleFileLineObject();
                deleteData.setCode(String.valueOf(i));
                bus.addBatch("B006001.deleteData01", deleteData);

                // update(00301～00300)
                SampleFileLineObject updateData = new SampleFileLineObject(
                        String.valueOf((i + 100)), "hoge", "hoge", "hoge",
                        "hoge", "hoge", "hoge", "hoge", "hoge", "hoge");
                bus.addBatch("B006001.updateData01", updateData);

                i++;

            }

            // バッチ更新の実行
            count = bus.executeBatch();
            if (log.isInfoEnabled()) {
                log.info("処理件数：" + count + "件");
                log.info("バッチ更新完了");
            }
            // トランザクションコミット
            BatchUtil.commitTransaction(transactionManager, stat);
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);
            // トランザクション終了
            BatchUtil.endTransaction(transactionManager, stat);
        }

        return 0;
    }
}
