package jp.terasoluna.batch.functionsample.b009.b009002;

import org.apache.ibatis.session.ResultHandler;

public interface B009002BatchDao {

    /**
     * EMPLOYEE2テーブルのデータを削除する。
     * @return 削除件数
     */
    public int deleteEmployee2();

    /**
     * EMPLOYEE3テーブルのデータを取得する。
     * @param object SQLパラメータ引数オブジェクト
     * @param handler ResultHandler
     */
    public void collectEmployee(Object object, ResultHandler handler);

    /**
     * EMPLOYEE2テーブルにデータを挿入する。
     * @param record 挿入対象オブジェクト
     * @return 挿入件数
     */
    public int insertEmployee2(CsvRecord record);

}
