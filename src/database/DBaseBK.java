/**
 * 
 */
package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import helper.HelpBK;

/**
 * @author Virgilio
 *
 */
public class DBaseBK {

	private String urlDBbookeeper="jdbc:postgresql://localhost:5432/TicketBugDB_BOOK";
			
	//metodo per la connessione verso il database con i dati di Bookkeeper
		public Connection  connectToDBtickectBugBookkeeper() throws SQLException, IOException {
			String user;
			String password;
			
			user=HelpBK.getMyProperty("userid");
			password=HelpBK.getMyProperty("password");
					
			return DriverManager.getConnection(urlDBbookeeper,user,password);
		}
		
		
	
}
