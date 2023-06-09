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
import helper.HelpBK;
import helper.HelpMathBK;
import logger.MyLoggerBK;

/**
 * @author Virgilio
 *
 */
public class CHGSETSIZEmetricBK implements Runnable {

	private int versione;
	
	public CHGSETSIZEmetricBK(int versione) {
		this.versione=versione;
	}
	
	@Override
	  public void run() {
	    // use the parameter here
		 
			try {
				calculateCHGSETSIZEforSpecificVersion(versione);
			} catch (SQLException | IOException | InterruptedException e) {
				
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		
	  }//fine metodo
	
	
public void calculateCHGSETSIZEforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpBK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateCHGSETSIZEforSpecificVersion(i);
			
		}
				
	}//fine metodo


	
	public void calculateCHGSETSIZEforSpecificVersion(int version) throws SQLException, IOException, InterruptedException {
		
		List<String> listFiles=new ArrayList<>();
		
		int chgSetSize=0;
		int chgSetSizeMax=0;
		double chgSetSizeAvg=0.0;
		List<Integer> listChgSetSize=new ArrayList<>();
		
		String logMsg="CHGSETSIZE BK V "+Integer.toString(version)+" ";
		
		CommandGitShowBK cmdgitShow = new  CommandGitShowBK();
		
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection conn2=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connUpdate=DBaseBK.connectToDBtickectBugBookkeeper();
		ResultSet rsJavaNames;
		ResultSet rsCHGSETSIZE;
		
		String queryForClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+"FROM \"ListJavaClassesBK\" "
			    +"WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ? ";
		
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			stat.setInt(1, version);
			rsJavaNames=stat.executeQuery();
			
			
            while( rsJavaNames.next() ) {
            	    	
			String fileJavaName=rsJavaNames.getString("NameClass");
			MyLoggerBK.logInfo(logMsg.concat(fileJavaName));
			
			
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+ "WHERE  \"NameClass\" =? AND \"Version\"= ? ";
					
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				stat2.setInt(2, version);
				rsCHGSETSIZE=stat2.executeQuery();
				
				 while(rsCHGSETSIZE.next()) {
					 
					 String commit = rsCHGSETSIZE.getString("Commit");
					   										 
					 listFiles=cmdgitShow.commandGitShow(commit);		   
					 chgSetSize=listFiles.size() - 1;
					
					 listChgSetSize.add(chgSetSize);  				   
	  				  					 					 				 
	  			 }//while interno
				
				
				chgSetSizeAvg=HelpMathBK.findAVG(listChgSetSize);
				chgSetSizeMax=HelpMathBK.findMax(listChgSetSize);
			    
				String queryUpd="UPDATE \"DataSetBK\"  "+
			              "SET  \"ChgSetSize\"= ?, \"MaxChgSetSize\"= ? , \"AvgChgSetSize\" = ? "+
					      "WHERE \"NameClass\" = ?  AND  \"Version\" = ? " ;
					           		 		
					try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpd)){
						
						statUpd.setInt(1, chgSetSize);
						statUpd.setInt(2, chgSetSizeMax);
						statUpd.setDouble(3 , chgSetSizeAvg);
						statUpd.setString(4, fileJavaName);
						statUpd.setInt(5, version);
						statUpd.executeUpdate();
					}
				
				   listChgSetSize.clear();
              }//try interno 
	        }//while
	    }//try
		
	}//fine metodo
	
}
