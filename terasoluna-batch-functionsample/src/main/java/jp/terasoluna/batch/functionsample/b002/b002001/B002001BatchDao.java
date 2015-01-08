package jp.terasoluna.batch.functionsample.b002.b002001;

import org.apache.ibatis.session.ResultHandler;

public interface B002001BatchDao {

    /**
     * EMPLOYテーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    public void collectEmployee(Object object, ResultHandler handler);

    /**
     * EMPLOYEE2テーブルにデータを挿入する。
     * @param param 挿入対象オブジェクト
     * @return 挿入件数
     */
    public int insertEmployee2(B002001Param param);

}
