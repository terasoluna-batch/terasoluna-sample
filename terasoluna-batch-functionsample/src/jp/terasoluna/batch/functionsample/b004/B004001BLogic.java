package jp.terasoluna.batch.functionsample.b004;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.file.util.FileControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * ファイル操作機能のサンプル<br>
 * <p>
 * 事前準備：なし<br>
 * </p>
 * <p>
 * サンプル内容：ファイル操作機能のサンプル<br>
 * ジョブを実行した際に「C:\\tmp\\input.csv」が「C:\\tmp\\outputB004001.csv」に<br>
 * コピーされていることを確認する<br>
 * </p>
 */
@Component
public class B004001BLogic implements BLogic {

    @Autowired()
    @Qualifier("fileControl")
    FileControl fileControl = null;

    public int execute(BLogicParam arg0) {

        fileControl
                .copyFile("C:\\tmp\\input.csv", "C:\\tmp\\outputB004001.csv");

        return 0;
    }

}
