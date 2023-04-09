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
		LinkageBK link = new LinkageBK();
		HelpBK help = new HelpBK();
		
		
		String pathLogBK = help.getMyProperty("pathLogFileLinkage");
			
		double l = link.calculateLinkageBK(pathLogBK);
		
		Logger logger=Logger.getLogger("MyLogger");
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", l);
		
		
	}//fine main

}
