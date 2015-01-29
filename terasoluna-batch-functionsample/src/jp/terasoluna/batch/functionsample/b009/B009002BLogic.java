package jp.terasoluna.batch.functionsample.b009;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportImpl;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DBValidateCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

/**
 * ���̓f�[�^�擾�@�\���g�p�����ۂ̊g����O�n���h�����O�̃T���v��<br>
 * <p>
 * ���O�����FC:\tmp�z����inputB009001.csv�t�@�C����z�u���邱��(DB�������p)<br>
 * </p>
 * <p>
 * �T���v�����e�FEMPLOYEE3�e�[�u����ǂݎ��AEMPLOYEE2�e�[�u���ɏo�͂���B<br>
 * �g����O�n���h�����O���g�p���A�X�e�[�^�X�ɂ�END��ԋp����B<br>
 * (�G���[��ValidationErrorException����������悤�Ɏ������Ă���)<br>
 * ���̓`�F�b�N�G���[�������������_�ŏI�����邪�A�I���X�e�[�^�X��100���Z�b�g����B<br>
 * (�^�C�~���O�ɂ���āAEMPLOYEE2�e�[�u���ɑ}������錏���͕ϓ�����)
 *</p>
 */
@Component
public class B009002BLogic extends AbstractTransactionBLogic {

    private Log log = LogFactory.getLog(B009002BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Autowired
    @Qualifier("queryRowHandleDAO")
    private QueryRowHandleDAO queryRowHandleDAO = null;

    @Autowired
    @Qualifier("updateDAO")
    private UpdateDAO updateDAO = null;

    @Autowired
    @Qualifier("beanValidator")
    private Validator beanValidator = null;

    public int doMain(BLogicParam arg0) {

        int returnCode = BATCH_NORMAL_END;

        int insertCount = 0;

        if (log.isInfoEnabled()) {
            log.info("EMPLOYEE3�e�[�u���ǂݍ���:�J�n");
        }

        BatchUpdateSupport bus = new BatchUpdateSupportImpl();

        CustomCollectorExceptionHandler cceHandler = new CustomCollectorExceptionHandler();

        Collector<CsvRecord> collector = new DBValidateCollector<CsvRecord>(
                queryRowHandleDAO, "B009002.selectEmployees", CsvRecord.class,
                20, cceHandler, beanValidator);

        try {

            while (collector.hasNext()) {
                CsvRecord csvRecord = collector.next();
                if (csvRecord != null) {
                    log.info("NAME:" + csvRecord.getFamilyName());
                    // �o�b�`�X�V�ɒǉ�
                    bus.addBatch("B009002.insertEmployee2", csvRecord);
                }
            }

            // �o�b�`�X�V���s
            insertCount = bus.executeBatch(updateDAO);

        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // �R���N�^�̃N���[�Y
            CollectorUtility.closeQuietly(collector);
        }

        if (log.isInfoEnabled()) {
            log.info("EMPLOYEE2�e�[�u��:" + insertCount + "���}�����܂����B");
        }

        // �P���ł��G���[�����������ꍇ�́A"100"��ԋp����B
        if (cceHandler.getErrorFieldCount() > 0) {
            return 100;
        }

        // ����I��
        return returnCode;
    }
}