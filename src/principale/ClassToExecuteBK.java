/**
 * 
 */
package principale;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpBK;

/**
 * @author Virgilio
 *
 */
public class ClassToExecuteBK {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
						
		String pathLogBK = HelpBK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageBK.calculateLinkageBK(pathLogBK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);
		
		
		
		
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
