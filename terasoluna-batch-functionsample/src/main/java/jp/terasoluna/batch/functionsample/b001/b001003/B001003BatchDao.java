package jp.terasoluna.batch.functionsample.b001.b001003;

import org.apache.ibatis.session.ResultHandler;

public interface B001003BatchDao {

    /**
     * EMPLOYEE�e�[�u���̃f�[�^���擾����B
     * @param object SQL�p�����[�^�����I�u�W�F�N�g
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler handler);
    
    /**
     * EMPLOYEE�e�[�u�����X�V����B
     * @param param �X�V�ΏۃI�u�W�F�N�g
     * @return �X�V����
     */
    int updateEmployee(B001003Param param);

}