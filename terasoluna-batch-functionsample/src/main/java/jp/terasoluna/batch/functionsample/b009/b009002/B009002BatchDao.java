package jp.terasoluna.batch.functionsample.b009.b009002;

import jp.terasoluna.batch.functionsample.b009.CsvRecord;

import org.apache.ibatis.session.ResultHandler;

public interface B009002BatchDao {

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
