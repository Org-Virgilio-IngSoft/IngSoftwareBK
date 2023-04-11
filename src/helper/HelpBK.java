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
public class  HelpBK {
 
	private static HelpBK INSTANCE;
	
	public static String getMyProperty(String propertyName) throws IOException {
		String config="config";
		
		try(FileReader fr = new FileReader(config)){
			var property = new Properties();
			property.load(fr);
			
			return property.getProperty(propertyName);
		}//try
		  	
	}//fine metodo
	
	 private HelpBK() {
		 //private constructor
	 }

	 
	 public static HelpBK getInstance() {
	     if (INSTANCE == null) {
	         INSTANCE = new HelpBK();
	     }

	     return INSTANCE;
	}//fine 
	
}
