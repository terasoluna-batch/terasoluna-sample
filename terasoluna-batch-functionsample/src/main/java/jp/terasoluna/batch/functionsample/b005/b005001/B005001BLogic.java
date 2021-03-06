package jp.terasoluna.batch.functionsample.b005.b005001;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;
import jp.terasoluna.fw.batch.message.MessageAccessor;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * メッセージ管理機能のサンプル<br>
 * <p>
 * 事前準備：なし<br>
 * </p>
 * <p>
 * サンプル内容：メッセージ管理機能のサンプル。<br>
 * ジョブを実行した際に「application-messages.properties」に定義したメッセージを<br>
 * 呼び出していることを確認する。<br>
 * </p>
 */
@Component
public class B005001BLogic implements BLogic {

    private static Logger log = LoggerFactory.getLogger(B005001BLogic.class);

    @Inject
    MessageAccessor messageAccessor;

    public int execute(BLogicParam arg0) {

        log.info(messageAccessor.getMessage("message.test1", null));

        String[] arg1 = { "[test10]" };
        log.info(messageAccessor.getMessage("message.test2", arg1));

        String[] arg2 = { "[test20]", "[test21]" };
        log.info(messageAccessor.getMessage("message.test3", arg2));

        String[] arg3 = { "[test30]", "[test31]", "[test32]" };
        log.info(messageAccessor.getMessage("message.test4", arg3));

        return 0;
    }

}
