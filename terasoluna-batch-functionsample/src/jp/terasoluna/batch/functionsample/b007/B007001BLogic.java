package jp.terasoluna.batch.functionsample.b007;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
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

/**
 * 入力データ取得機能を使用したファイル-DB関連ジョブのサンプル<br>
 * <p>
 * 事前準備：C:\tmp配下にinput.csvファイルを配置すること <br>
 * </p>
 * <p>
 * サンプル内容：入力データ取得機能を使用し、<br>
 * ファイルを読み込み、DBに更新するサンプル。<br>
 * </p>
 */
@Component
public class B007001BLogic extends AbstractTransactionBLogic {

    private Log log = LogFactory.getLog(B007001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    private static final String INPUT_FILE = "C:\\tmp\\input.csv";

    @Autowired
    @Qualifier("csvFileQueryDAO")
    private FileQueryDAO csvFileQueryDAO = null;

    @Autowired
    @Qualifier("updateDAO")
    private UpdateDAO updateDAO = null;

    @Override
    public int doMain(BLogicParam arg0) {

        // EMPLOYEEテーブルの初期化
        updateDAO.execute("B007001.deleteEmployees", null);

        Collector<CsvRecord> collector = new FileCollector<CsvRecord>(
                csvFileQueryDAO, INPUT_FILE, CsvRecord.class);

        try {

            while (collector.hasNext()) {
                CsvRecord record = collector.next();
                log.info("ID:" + record.getId() + " FIMILYNAME:"
                        + record.getFamilyName() + " FIRSTNAME:"
                        + record.getFamilyName() + " AGE:" + record.getAge());

                updateDAO.execute("B007001.insertEmployees", record);

            }
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // コレクタのクローズ
            CollectorUtility.closeQuietly(collector);
        }

        // 正常終了
        return BATCH_NORMAL_END;
    }

}
