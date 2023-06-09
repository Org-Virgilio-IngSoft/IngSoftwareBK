/**
 * 
 */
package dataset;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBaseBK;

/**
 * @author Virgilio
 *
 */
public class DatasetJavaClassesAndVersionsBK {

	public void fillFirstTwoColumnDataset() throws SQLException, IOException {
		
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connInsert=DBaseBK.connectToDBtickectBugBookkeeper();
		ResultSet rsJavaNames;
		
		String queryJavaClassesNames="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+ "FROM \"ListJavaClassesBK\"  "
				+ "WHERE \"NameClass\" LIKE '%.java'  "
				+ "ORDER BY \"Version\"  " ;
		
		try(PreparedStatement stat=conn.prepareStatement(queryJavaClassesNames) ){
			rsJavaNames=stat.executeQuery();
		
				
          while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
			int version = rsJavaNames.getInt("Version");
			
			String queryInsert="INSERT INTO \"DataSetBK\"  "
			         +" ( \"Version\"  , \"NameClass\"  )  "
					 +"VALUES ( ? , ? ) " ;
					           		
														 		
			  try(PreparedStatement statIns=connInsert.prepareStatement(queryInsert)){													
				  statIns.setInt(1, version);							
				  statIns.setString(2, fileJavaName);
							
				  statIns.executeUpdate();
			  }//try
																												
			
          }//while
          
		}//try  
	}//fine metodo
	
	
}
