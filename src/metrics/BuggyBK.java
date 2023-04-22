/**
 * 
 */
package metrics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBaseBK;
import logger.MyLoggerBK;

/**
 * @author Virgilio
 *
 */
public class BuggyBK {

	public void giveLabelBuggytoJavaClasses() throws SQLException, IOException {
		
		String javaClassName;		
		int versionFixJavaClass;
		int versionInjJavaClass;
		
		String msg="Buggy BK ";
		
		ResultSet rsBuggy;
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connUpdate=DBaseBK.connectToDBtickectBugBookkeeper();
		
		String queryBuggy=" SELECT *  "
		      +"FROM \"CommitTicketsBK\"  AS ct "
		      +"JOIN \"Ticket_FV_OV_P_IV_BK\"  AS fvov  "
		      +"ON ct.\"TicketID\" = fvov.\"TicketBugID\"  "
		      +"JOIN \"ListJavaClassesBK\"  AS ljc  "
		      +"ON  ljc.\"Commit\" = ct.\"Commit\"  "
		      +"WHERE ljc.\"NameClass\"  LIKE '%.java' AND "
              +"  fvov.\"P\" >= 1 AND ( fvov.\"IV\" >= 1 OR fvov.\"FV\" =1 ) "; 
		       	                                
		
		try(PreparedStatement stat=conn.prepareStatement(queryBuggy) ){
			  rsBuggy=stat.executeQuery();
					 
	          while( rsBuggy.next() ) {
	        	 
	        	  javaClassName=rsBuggy.getString("NameClass");	        	 
	        	  versionFixJavaClass=rsBuggy.getInt("FV");
	        	  versionInjJavaClass = rsBuggy.getInt("IV");
	        	  
	        	  if(versionFixJavaClass==1  && versionInjJavaClass==0 ) {
		        	  versionInjJavaClass=1;
	        	  }
	        	 
	        	  
	        	  String queryUpdate="UPDATE \"DataSetBK\"  "
	    	        	  + "SET \"Buggy\" = true  "
	    	        	  + "WHERE \"NameClass\" = ?  AND   \"Version\" = ? ";
	    	     
	    		  
	    		  try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpdate) ){
	    			  statUpd.setString(1,javaClassName);
	    			  statUpd.setInt(2, versionInjJavaClass);
	    			  	    			 
	    			  MyLoggerBK.logInfo(msg.concat(javaClassName) );
	    			  statUpd.executeUpdate();
	    		  }//try interno	
	        	  
	          }//while
		}//try
				
		
	}//fine metodo
	
}
