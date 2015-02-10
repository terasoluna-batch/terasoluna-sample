package jp.terasoluna.batch.functionsample.b008.b008001;

import jp.terasoluna.batch.functionsample.b008.ZipCode;

public interface B008001BatchDao {

    /**
     * ZIPCODEテーブルの削除
     * @return 削除件数
     */
    int deleteZipCode();

    /**
     * ZIPCODEテーブルへのデータ挿入
     * @param zipCode 挿入対象オブジェクト
     * @return 挿入件数
     */
    int insertZipCode(ZipCode zipCode);

}
