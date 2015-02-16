package jp.terasoluna.batch.functionsample.b001.b001001;

import org.apache.ibatis.session.ResultHandler;

public interface B001001Dao {

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
    int updateEmployee(B001001Param param);

}
