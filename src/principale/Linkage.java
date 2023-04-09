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

	
	
public double CalculateLinkageBK(String pathLogGitFile) throws IOException {
		
		double count=0.0;
		double countTickets=0.0;
		FileReader fr=new FileReader(pathLogGitFile);
		BufferedReader br=new BufferedReader(fr);
		
		String lineFile;
		
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
		 
		 br.close();
		 return (countTickets/count) ;
	}
	
}
