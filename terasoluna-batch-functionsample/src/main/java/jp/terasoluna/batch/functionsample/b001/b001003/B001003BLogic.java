package jp.terasoluna.batch.functionsample.b001.b001003;

import javax.inject.Inject;
import javax.inject.Named;

import jp.terasoluna.fw.batch.blogic.AbstractTransactionBLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.exception.BatchException;
import jp.terasoluna.fw.collector.Collector;
import jp.terasoluna.fw.collector.db.DaoCollector;
import jp.terasoluna.fw.collector.util.CollectorUtility;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * �����^�W���u�E�g�����U�N�V�����Ǘ��@�\�̃T���v���R<br>
 * <p>
 * ���O�����FEMPLOYEE�e�[�u�����쐬���Ă���A<br>
 * �P���ȏ�̃f�[�^�����݂��邱��<br>
 * </p>
 * <p>
 * �T���v�����e�F���̓f�[�^�擾�@�\���g�p���ADB���Q�Ƃ��ADB���o�b�`�X�V����T���v��<br>
 * AbstractTransactionBLogic���p�����t���[�����[�N���Ƀg�����U�N�V�����Ǘ���C����<br>
 * (�f�[�^�͑S���ꊇ�ɍX�V����)<br>
 * �����I����́A���ׂẴf�[�^����ؑ��Y�ɏ�����������<br>
 * </p>
 */
@Component
public class B001003BLogic extends AbstractTransactionBLogic {

    private Logger log = LoggerFactory.getLogger(B001003BLogic.class);

    private static final int BATCH_NORMAL_END = 0;

    @Inject
    B001003BatchDao dao;

    @Inject
    @Named("batchSqlSessionTemplate")
    SqlSession sqlSession;

    public int doMain(BLogicParam arg0) {

        Collector<B001003Param> collector = null;

        try {
            B001003Param param = new B001003Param();
            collector = new DaoCollector<B001003Param>(dao, "collectEmployee",
                    param);

            int updateCount = 0;

            while (collector.hasNext()) {
                B001003Param data = collector.next();

                if (log.isInfoEnabled()) {
                    log.info("ID:{} FAMILYNAME:{} FIRSTNAME:{} AGE:{}",
                            data.getId(), data.getFamilyName(),
                            data.getFirstName(), data.getAge());
                }

                data.setFamilyName("���");
                data.setFirstName("���Y");

                dao.updateEmployee(data);
                updateCount++;

                // 10�����ƂɃo�b�`�X�V���s
                if (updateCount % 10 == 0) {
                    log.info("�o�b�`�X�V���s");
                    sqlSession.flushStatements();
                }

            }
        } catch (Exception e) {
            throw new BatchException(e);
        } finally {
            CollectorUtility.closeQuietly(collector);
        }
        return BATCH_NORMAL_END;

    }

}