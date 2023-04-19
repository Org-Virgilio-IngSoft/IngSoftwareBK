/**
 * 
 */
package weka;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpBK;

/**
 * @author Virgilio
 *
 */
public class CreateArffFileBK {

	public static void createArffFile(String nameArffFile) throws IOException {
		
		Logger logger=Logger.getLogger("MyLogger");	
		
		String pathFolder=HelpBK.getMyProperty("pathArffFolder");
		String ext=".arff";
		
	    File myFile = new File(pathFolder+nameArffFile+ext);
          if (myFile.createNewFile()) {
  	         logger.log(Level.INFO ,"File creato ok: {0}", myFile.getName());        
          } else {
  	         logger.log(Level.INFO ,"File già esiste: {0}", myFile.getName());	       
            
          }
    
  }//fine metodo
	

	
	private CreateArffFileBK() {
		
	}
	
}
