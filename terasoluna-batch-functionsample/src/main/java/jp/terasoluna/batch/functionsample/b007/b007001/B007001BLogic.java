package jp.terasoluna.batch.functionsample.b007.b007001;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.batch.functionsample.b007.CsvRecord;
import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.file.dao.FileQueryDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(B007001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    private static final String INPUT_FILE = "C:\\tmp\\input.csv";

    @Inject
    B007001BatchDao dao;

    @Inject
    @Named("csvFileQueryDAO")
    FileQueryDAO csvFileQueryDAO;

    @Override
    public int doMain(BLogicParam arg0) {

        // EMPLOYEEテーブルの初期化
        dao.deleteEmployees();

        Collector<CsvRecord> collector = new FileCollector<CsvRecord>(
                csvFileQueryDAO, INPUT_FILE, CsvRecord.class);

        try {

            while (collector.hasNext()) {
                CsvRecord record = collector.next();
                log.info("ID:" + record.getId() + " FIMILYNAME:"
                        + record.getFamilyName() + " FIRSTNAME:"
                        + record.getFirstName() + " AGE:" + record.getAge());

                dao.insertEmployee(record);

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
