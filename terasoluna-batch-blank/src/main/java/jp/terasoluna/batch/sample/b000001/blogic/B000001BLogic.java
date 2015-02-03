package jp.terasoluna.batch.sample.b000001.blogic;

import jp.terasoluna.fw.batch.blogic.BLogic;
import jp.terasoluna.fw.batch.blogic.vo.BLogicParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class B000001BLogic implements BLogic {

    private static Logger log = LoggerFactory.getLogger(B000001BLogic.class);

    public int execute(BLogicParam param) {
        if (log.isInfoEnabled()) {
            log.info("#################");
            log.info("Hello TERASOLUNA.");
            log.info("#################");
        }
        return 0;
    }
}
