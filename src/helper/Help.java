/**
 * 
 */
package helper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Virgilio
 *
 */
public class Help {

	public String getMyProperty(String propertyName) throws IOException {
		String config="config";
		FileReader fr = new FileReader(config);
    	var property = new Properties();
		property.load(fr);
		
		return property.getProperty("pathLogFileLinkage");
	}
	
}
