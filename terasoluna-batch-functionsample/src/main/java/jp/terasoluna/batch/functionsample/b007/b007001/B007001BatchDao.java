package jp.terasoluna.batch.functionsample.b007.b007001;

import java.util.List;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.executor.BatchResult;

import jp.terasoluna.batch.functionsample.b007.CsvRecord;

public interface B007001BatchDao {

    /**
     * EMPLOYEEテーブルのデータを削除する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    void deleteEmployees();

    /**
     * EMPLOYEEテーブルにデータを挿入する。
     * @param param 挿入対象オブジェクト
     * @return 挿入件数
     */
    int insertEmployee(CsvRecord param);

    /**
     * バッチ更新を実行する。
     * @return バッチ更新結果のリスト
     */
    @Flush
    List<BatchResult> flush();

}
