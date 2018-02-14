import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*https://examples.javacodegeeks.com/core-java/logback-file-appender-example/*/
public class logtester {
	
	static Logger logger = LoggerFactory.getLogger(logtester.class);
	
	public static void test(String[] args){
		
		logger.info("Hello, World!");
		
	}

}
