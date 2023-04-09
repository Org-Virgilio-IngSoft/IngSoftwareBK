/**
 * 
 */
package principale;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.Help;

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
		Linkage link = new Linkage();
		Help help = new Help();
		
		
		String pathLogBK = help.getMyProperty("pathLogFileLinkage");
			
		double l = link.calculateLinkageBK(pathLogBK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: "+ l);
		
		
	}//fine main

}
