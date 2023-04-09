/**
 * 
 */
package principale;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

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
		
		String config="config";
		FileReader fr = new FileReader(config);
    	var property = new Properties();
		property.load(fr);
		
		String pathLogBK = property.getProperty("pathLogFileLinkage");
			
		double l = link.calculateLinkageBK(pathLogBK);
		
		Logger log=Logger.getLogger("MyLogger");
		log.info("LINKAGE BOOKKEEPER: "+ l);
		
		
	}

}
