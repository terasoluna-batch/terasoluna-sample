package jp.terasoluna.batch.functionsample.b003.b003001;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;

import org.springframework.stereotype.Component;

/**
 * 例外ハンドリング機能のサンプル<br>
 * <p>
 * 事前準備：なし<br>
 * </p>
 * <p>
 * サンプル内容：例外ハンドリング機能のサンプル。<br>
 * ジョブを実行した際にB003001ExceptionHandlerクラスを使用して<br>
 * ログに"RuntimeException is Thrown..."が表示されることを確認する。<br>
 * </p>
 */
@Component
public class B003001BLogic implements BLogic {

    public int execute(BLogicParam arg0) {

        throw new RuntimeException();

    }

}
