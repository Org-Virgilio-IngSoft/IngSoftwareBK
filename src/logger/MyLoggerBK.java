/**
 * 
 */
package logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Virgilio
 *
 */
public class MyLoggerBK {

	 private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggerBK.class);
	    
	    public static void logDebug(String logging) {
	        LOGGER.debug(logging);
	    }
	    public static void logInfo(String logging) {
	        LOGGER.info(logging);
	    }
	    
	    public static void logError(String logging) {
	        LOGGER.error(logging);
	    }
	
	    
	    private MyLoggerBK() {
	    	
	    }
}
	

