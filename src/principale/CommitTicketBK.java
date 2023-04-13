/**
 * 
 */
package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBaseBK;
import helper.HelpBK;
/**
 * @author Virgilio
 *
 */
public class CommitTicketBK {

	public void createPairCommitTicket(String pathFileLogGit) throws IOException, SQLException {
		var commit="";
		var date="";
		var ticket="";
		String lineFile;		
		
		Connection con;		     
        
       con =DBaseBK.connectToDBtickectBugBookkeeper();
        
           
		try (
			FileReader fr=new FileReader(pathFileLogGit);
			BufferedReader br=new BufferedReader(fr);
			                                          ){
			 			
			 while( (lineFile=br.readLine() ) !=null ) {
					
				
				if(lineFile.startsWith("commit") ) {
					commit=lineFile.substring(7);
										
				}
				
				if(lineFile.startsWith("Date") ) {
					date=lineFile.substring(8,18);
					
				}
				
				if(lineFile.startsWith("    BOOKKEEPER-") ||  lineFile.startsWith("    [BOOKKEEPER-") ) {
					ticket=HelpBK.projectStringTicket(lineFile);
					String queryInsert="INSERT INTO \"CommitTicketsBK\" ( \"Commit\" ,\"TicketID\" ,\"Date\")  "+
							"VALUES ( ? , ?, ? )";
					
					try(PreparedStatement statUpdate=con.prepareStatement(queryInsert) ){
						statUpdate.setString(1, commit);
					    statUpdate.setString(2, ticket);
					    statUpdate.setString(3, date);
						statUpdate.executeUpdate();
					}
				}								
					
	          }//while
		}//try
		
		
	}//fine metodo
	
}
