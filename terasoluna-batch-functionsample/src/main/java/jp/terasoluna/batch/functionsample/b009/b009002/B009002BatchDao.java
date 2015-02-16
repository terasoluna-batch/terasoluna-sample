package jp.terasoluna.batch.functionsample.b009.b009002;

import org.apache.ibatis.session.ResultHandler;

public interface B009002BatchDao {

    /**
     * EMPLOYEE2テーブルのデータを削除する。
     * @return 削除件数
     */
    int deleteEmployee2();

    /**
     * EMPLOYEE3テーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    void collectEmployee(Object object, ResultHandler handler);

    /**
     * EMPLOYEE2テーブルにデータを挿入する。
     * @param record 挿入対象オブジェクト
     * @return 挿入件数
     */
    int insertEmployee2(CsvRecord record);

}
