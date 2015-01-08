package jp.terasoluna.batch.functionsample.b001.b001003;

import org.apache.ibatis.session.ResultHandler;

public interface B001003BatchDao {

    /**
     * EMPLOYテーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    public void collectEmployee(Object object, ResultHandler handler);
    
    /**
     * EMPLOYEEテーブルを更新する。
     * @param param 更新対象オブジェクト
     * @return 更新件数
     */
    public int updateEmployee(B001003Param param);

}
