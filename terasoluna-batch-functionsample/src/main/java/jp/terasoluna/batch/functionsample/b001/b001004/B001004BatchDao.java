package jp.terasoluna.batch.functionsample.b001.b001004;

import org.apache.ibatis.session.ResultHandler;

public interface B001004BatchDao {

    /**
     * EMPLOYEEテーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler handler);
    
    /**
     * EMPLOYEEテーブルを更新する。
     * @param param 更新対象オブジェクト
     * @return 更新件数
     */
    int updateEmployee(B001004Param param);

}
