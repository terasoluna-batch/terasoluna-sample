package jp.terasoluna.batch.functionsample.b008;

import java.util.Map;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupport;
import jp.terasoluna.fw.batch.dao.support.BatchUpdateSupportImpl;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.batch.util.BatchUtil;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.file.FileCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;
import jp.terasoluna.fw.collector.util.ControlBreakChecker;
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
 * �R���g���[���u���C�N�@�\�̃T���v��<br>
 * <p>
 * ���O�����FC:\tmp�z����KEN_ALL.CSV�t�@�C����z�u���邱��<br>
 * </p>
 * <p>
 * KEN_ALL.CSV�́A���L�̓��{�X���O���[�v�̃z�[���y�[�W����_�E�����[�h�ł��܂��B<br>
 * {@link http://www.post.japanpost.jp/zipcode/download.html}
 * </p>
 * <p>
 * �T���v�����e�F�t�@�C����ǂݍ��݁ADB�ɍX�V����T���v���B <br>
 * �O�����ł͕����̃u���C�N�L�[���g�p���A�R���g���[���u���C�N��������<br>
 * ���O�ւ̃w�b�_�o�́A�s�������̃J�E���g���s���Ă���B<br>
 * �㏈���ł͒P��̃u���C�N�L�[��p���āA�R���g���[���u���C�N�̔����̍ۂɃo�b�`�X�V���s���B<br>
 *</p>
 */
@Component
public class B008001BLogic implements BLogic {

    private Log log = LogFactory.getLog(B008001BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    private static final String INPUT_FILE = "C:\\tmp\\KEN_ALL.CSV";

    @Autowired
    @Qualifier("csvFileQueryDAO")
    private FileQueryDAO csvFileQueryDAO = null;

    @Autowired
    @Qualifier("updateDAO")
    private UpdateDAO updateDAO = null;

    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager = null;

    public int execute(BLogicParam param) {
        TransactionStatus stat = null;

        // ////////////
        // ������
        if (log.isInfoEnabled()) {
            log.info("ZipCode�e�[�u��������:�J�n");
        }

        try {
            // �g�����U�N�V�����J�n
            stat = BatchUtil.startTransaction(transactionManager);

            // ZipCode�e�[�u���̏�����
            updateDAO.execute("B008001.deleteZipCode", null);

            // �g�����U�N�V�����R�~�b�g
            BatchUtil.commitTransaction(transactionManager, stat);
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // �g�����U�N�V�����I��
            BatchUtil.endTransaction(transactionManager, stat);
        }

        if (log.isInfoEnabled()) {
            log.info("ZipCode�e�[�u��������:�I��");
        }

        Collector<ZipCode> collector = null;
        BatchUpdateSupport bus = new BatchUpdateSupportImpl();

        // ////////////
        // �o�^����
        if (log.isInfoEnabled()) {
            log.info("KEN_ALL.CSV�t�@�C���ǂݍ���:�J�n");
        }

        try {
            int municipalDistrictCnt = 0;
            int townRegionCnt = 0;

            // �g�����U�N�V�����J�n
            stat = BatchUtil.startTransaction(transactionManager);

            collector = new FileCollector<ZipCode>(csvFileQueryDAO, INPUT_FILE,
                    ZipCode.class);

            while (collector.hasNext()) {
                ZipCode record = collector.next();
                townRegionCnt++;

                // �R���g���[���u���C�N�i�w�b�_���j����
                boolean preCtrlBreak = ControlBreakChecker.isPreBreak(
                        collector, "adminDivisions", "municipalDistrict");

                // �R���g���[���u���C�N�i�t�b�^���j����
                boolean ctrlBreak = ControlBreakChecker.isBreak(collector,
                        "adminDivisions");

                // �R���g���[���u���C�N�i�w�b�_���j����
                if (preCtrlBreak) {
                    StringBuilder sb = new StringBuilder();

                    Map<String, Object> pbMap = ControlBreakChecker
                            .getPreBreakKey(collector, "adminDivisions",
                                    "municipalDistrict");

                    // �s�撬�������ς�������ɃJ�E���g����
                    if (pbMap.containsKey("municipalDistrict")) {
                        municipalDistrictCnt++;
                    }

                    // �s���{�������ς�������Ƀ��O�Ƀw�b�_���o�͂���
                    if (log.isInfoEnabled()
                            && pbMap.containsKey("adminDivisions")) {
                        sb.setLength(0);
                        sb.append(pbMap.get("adminDivisions"));
                        log.info("=========================");
                        log.info(sb.toString());
                    }

                }

                // �o�b�`�X�V�ɒǉ�
                bus.addBatch("B008001.insertZipCode", record);

                // �R���g���[���u���C�N�i�t�b�^���j����
                if (ctrlBreak) {
                    Map<String, Object> brkMap = ControlBreakChecker
                            .getBreakKey(collector, "adminDivisions");

                    // �s���{�������ς�������Ƀo�b�`�X�V�����s����
                    if (brkMap.containsKey("adminDivisions")) {
                        if (log.isInfoEnabled()) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("�o�b�`�X�V���s ");
                            sb.append(bus.size());
                            sb.append("�� ");
                            sb.append("�s�撬���� ");
                            sb.append(municipalDistrictCnt);
                            sb.append("�� ");
                            sb.append("���搔 ");
                            sb.append(townRegionCnt);
                            sb.append("�� ");
                            log.info(sb.toString());
                        }

                        // �o�b�`�X�V���s
                        bus.executeBatch(updateDAO);

                        // �J�E���^���Z�b�g
                        municipalDistrictCnt = 0;
                        townRegionCnt = 0;
                    }
                }
            }

            if (log.isInfoEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("�o�b�`�X�V���s ");
                sb.append(bus.size());
                sb.append("��");
                log.info(sb.toString());
            }

            // �o�b�`�X�V���s
            bus.executeBatch(updateDAO);

            // �g�����U�N�V�����R�~�b�g
            BatchUtil.commitTransaction(transactionManager, stat);
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            // �R���N�^�̃N���[�Y
            CollectorUtility.closeQuietly(collector);

            // �g�����U�N�V�����I��
            BatchUtil.endTransaction(transactionManager, stat);
        }

        if (log.isInfoEnabled()) {
            log.info("KEN_ALL.CSV�t�@�C���ǂݍ���:�I��");
        }

        // ����I��
        return BATCH_NORMAL_END;
    }

}