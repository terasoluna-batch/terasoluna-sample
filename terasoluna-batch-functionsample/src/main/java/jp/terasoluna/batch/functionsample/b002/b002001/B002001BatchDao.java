package jp.terasoluna.batch.functionsample.b002.b002001;

import org.apache.ibatis.session.ResultHandler;

public interface B002001BatchDao {

    /**
     * EMPLOYEEテーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler handler);

    /**
     * EMPLOYEE2テーブルにデータを挿入する。
     * @param param 挿入対象オブジェクト
     * @return 挿入件数
     */
    int insertEmployee2(B002001Param param);

}
