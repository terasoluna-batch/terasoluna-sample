package jp.terasoluna.batch.functionsample.b001.b001003;

import java.util.List;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ResultHandler;

public interface B001003BatchDao {

    /**
     * EMPLOYEEテーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler<B001003Param> handler);

    /**
     * EMPLOYEEテーブルを更新する。
     * @param param 更新対象オブジェクト
     * @return 更新件数
     */
    int updateEmployee(B001003Param param);

    /**
     * バッチ更新を実行する。
     * @return バッチ更新結果のリスト
     */
    @Flush
    List<BatchResult> flush();

}
