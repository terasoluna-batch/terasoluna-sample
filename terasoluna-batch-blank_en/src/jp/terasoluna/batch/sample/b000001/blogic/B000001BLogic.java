package jp.terasoluna.batch.sample.b000001.blogic;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class B000001BLogic implements BLogic {

    private static Log log = LogFactory.getLog(B000001BLogic.class);

    public int execute(BLogicParam param) {
        if (log.isInfoEnabled()) {
            log.info("#################");
            log.info("Hello TERASOLUNA.");
            log.info("#################");
        }
        return 0;
    }

}
