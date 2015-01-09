package jp.terasoluna.batch.functionsample.b003.b003001;

import jp.terasoluna.fw.batch.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class B003001ExceptionHandler implements ExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(B003001ExceptionHandler.class);

    public int handleThrowableException(Throwable e) {

        log.info("RuntimeException is Thrown...");

        return 100;
    }

}
