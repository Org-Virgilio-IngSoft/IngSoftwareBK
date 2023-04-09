/**
 * 
 */
package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Virgilio
 *
 */
public class Linkage {

	
	
public double calculateLinkageBK(String pathLogGitFile) throws IOException {
		
		double count=0.0;
		double countTickets=0.0;
		FileReader fr=new FileReader(pathLogGitFile);
		
		
		String lineFile;
		
		try( BufferedReader br=new BufferedReader(fr) ){
			
            while( (lineFile=br.readLine() ) !=null ) {
				
				if(lineFile.startsWith("commit") ) {
					count=count+1;
					
				}
				if(lineFile.startsWith("    BOOKKEEPER-") ) {
					countTickets=countTickets+1;
					
				}
				if(lineFile.startsWith("    [BOOKKEEPER-") ) {
					countTickets=countTickets+1;
					
				}
		 }//while
		 				 
		 if(count==0.0) {
			return -1; 
		 }
		 
		 return (countTickets/count) ;
			
		}//try
		
		 
	}//fine metodo
	
}
