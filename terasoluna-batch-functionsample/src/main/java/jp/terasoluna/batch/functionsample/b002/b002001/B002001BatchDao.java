package jp.terasoluna.batch.functionsample.b002.b002001;

import org.apache.ibatis.session.ResultHandler;

public interface B002001BatchDao {

    /**
     * EMPLOYEE�e�[�u���̃f�[�^���擾����B
     * @param object SQL�p�����[�^�����I�u�W�F�N�g
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler handler);

    /**
     * EMPLOYEE2�e�[�u���Ƀf�[�^��}������B
     * @param param �}���ΏۃI�u�W�F�N�g
     * @return �}������
     */
    int insertEmployee2(B002001Param param);

}