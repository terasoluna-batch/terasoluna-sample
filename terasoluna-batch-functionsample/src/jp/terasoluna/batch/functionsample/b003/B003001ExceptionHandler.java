package jp.terasoluna.batch.functionsample.b003;

import jp.terasoluna.fw.batch.exception.handler.ExceptionHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class B003001ExceptionHandler implements ExceptionHandler {

	private static Log log = LogFactory.getLog(B003001ExceptionHandler.class);
	
	public int handleThrowableException(Throwable e) {
		
		log.info("RuntimeException is Thrown...");
		
		return 100;
	}

}
