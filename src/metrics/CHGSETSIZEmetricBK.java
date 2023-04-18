/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commands.CommandGitShowBK;
import database.DBaseBK;
import helper.HelpMathBK;

/**
 * @author Virgilio
 *
 */
public class CHGSETSIZEmetricBK {

	public void calculateChgSetSize() throws SQLException, IOException, InterruptedException {
		
		List<String> listFiles=new ArrayList<>();
		
		int chgSetSize=0;
		int chgSetSizeMax=0;
		double chgSetSizeAvg=0.0;
		List<Integer> listChgSetSize=new ArrayList<>();
		
		CommandGitShowBK cmdgitShow = new  CommandGitShowBK();
		
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection conn2=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connUpdate=DBaseBK.connectToDBtickectBugBookkeeper();
		ResultSet rsJavaNames;
		ResultSet rsCHGSETSIZE;
		
		String queryForClasses="SELECT DISTINCT FROM \"ListJavaClassesBK\" "+
				 " WHERE \"NameClass\" LIKE '%.java' ";
		
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			rsJavaNames=stat.executeQuery();
						
            while( rsJavaNames.next() ) {
        	
			String fileJavaName=rsJavaNames.getString("NameClass");
		         
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+ "WHERE  \"NameClass\" =? "
					+ "ORDER BY \"Version\" ASC ";
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				rsCHGSETSIZE=stat2.executeQuery();
				
				
				String commit = rsCHGSETSIZE.getString("Commit");
			    
			    listFiles=cmdgitShow.commandGitShow(commit);		   
			    chgSetSize=listFiles.size() - 1;
			
			    listChgSetSize.add(chgSetSize);
				chgSetSizeAvg=HelpMathBK.findAVG(listChgSetSize);
				chgSetSizeMax=HelpMathBK.findMax(listChgSetSize);
			    
				String queryUpd="UPDATE \"ListJavaClassesBK\"  "+
			              "SET  \"ChgSetSize\"= ?, \"MaxChgSetSize\"= ? , \"AvgChgSetSize\" = ? "+
					      "WHERE \"NameClass\" = ?  AND  \"Commit\" = ? " ;
					           		 		
					try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpd)){
						
						statUpd.setInt(1, chgSetSize);
						statUpd.setInt(2, chgSetSizeMax);
						statUpd.setDouble(3 , chgSetSizeAvg);
						statUpd.setString(4, fileJavaName);
						statUpd.setString(5, commit);
						statUpd.executeUpdate();
					}
				
				
              }//try interno 
	        }//while
	    }//try
		
	}//fine metodo
	
}
