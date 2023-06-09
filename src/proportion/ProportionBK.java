/**
 * 
 */
package proportion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import database.DBaseBK;
import helper.HelpBK;
import helper.HelpInfoProjectBK;
import helper.HelpMathBK;

/**
 * @author Virgilio
 * 
 */
public class ProportionBK {
		
	 public void calcolaProportionTicketsWithIV() throws SQLException, IOException {
		  	
		  double p=0.0; 	  
		  int fixV=0;
		  int openV=0;
		  int iv=0;
		  String bugID;
		  
		  List<Double> pValues = new ArrayList<>();
		  List<String> ticketsBugID = new ArrayList<>();
		  
		  ResultSet rsTicketsWITHInjectedVersion;
		  Connection con;			        
	      con =DBaseBK.connectToDBtickectBugBookkeeper();
		  
	      String queryTicketsWITHinjectedVersion="SELECT * "
	    		     +" FROM \"Ticket_FV_OV_P_IV_BK\"  AS FV_OV_P_IV "
	      		     + "JOIN \"TicketWithInjectedVersionBK\"  AS tickWithIV  "
	      		     + "ON FV_OV_P_IV.\"TicketBugID\"  =  tickWithIV.\"TicketBugID\"   "
	      		     + "WHERE  \"FV\" > \"OV\"  ";
			
	      try(PreparedStatement stat=con.prepareStatement(queryTicketsWITHinjectedVersion) ){
	  		  rsTicketsWITHInjectedVersion=stat.executeQuery();
	  		
	  		
	          while( rsTicketsWITHInjectedVersion.next() ) {
	        	  bugID = rsTicketsWITHInjectedVersion.getString("TicketBugID");
	        	  fixV = rsTicketsWITHInjectedVersion.getInt("FV");
	        	  openV = rsTicketsWITHInjectedVersion.getInt("OV");
	        	  iv = rsTicketsWITHInjectedVersion.getInt("InjectedVersion");
	            	        	  	        	  
	        	  p = ProportionBasicBK.calculatePspecificBug(fixV, openV, iv);
	        	  
	        	  ticketsBugID.add(bugID);
	        	  pValues.add(p);  
	          }//while
	          
	      }//try   
	      
	      
	      //Execute sql query
	    
	      String query="UPDATE \"TicketWithInjectedVersionBK\"  "
	      		     + "SET   \"P\" = ?  "+
					   "WHERE \"TicketBugID\" =?    ";
			  try(PreparedStatement statUpdate=con.prepareStatement(query) ){
	    	
			   for (int j = 0; j <  ticketsBugID.size(); j++) {
				 statUpdate.setDouble(1, pValues.get(j));  
			     statUpdate.setString(2, ticketsBugID.get(j));
				
				 statUpdate.executeUpdate();	
		       }//for  
			   			   
		    }//try 
			
		 String query2="UPDATE  \"Ticket_FV_OV_P_IV_BK\"   "
		      		 + "SET     \"P\" = ?  "+
					   "WHERE   \"TicketBugID\"  =?";
		 try(PreparedStatement statUpdate=con.prepareStatement(query2) ){
		    	
			   for (int k = 0; k <  ticketsBugID.size(); k++) {
				 statUpdate.setDouble(1, pValues.get(k));  
			     statUpdate.setString(2, ticketsBugID.get(k));
				
				 statUpdate.executeUpdate();	
		       }//for  
			   			   
		    }//try 
		 
		 		  
}//fine metodo
	  
	  
	  public double calculatePmedio() throws SQLException, IOException {
		  
		  double p;
		  List<Double> pValues = new ArrayList<>();
		  ResultSet resultP;
		  
		  Connection con;			        
	      con =DBaseBK.connectToDBtickectBugBookkeeper();
		  
	      String queryP=" SELECT  *  "   
  				+ "FROM \"TicketWithInjectedVersionBK\"    "     			  				
  				+ "WHERE \"P\" >= 1  ";
  				
	      try(var stat=con.prepareStatement(queryP) ){
			  resultP=stat.executeQuery();
						  
	          while( resultP.next() ) {
	        	  p = resultP.getDouble("P"); 
	        	  pValues.add(p);
	          }
	        		        	
	      }//try
	      	       
		  return HelpMathBK.findMean(pValues);
	  }//fine metodo 
	 
	  
	  
	//ristima di nuovo InjectedV di bug per cui fixV == openV e p non � calcolabile conseguentemente
	// il parametro double pMedio � calcolato con in bug effettivamente disponibili per cui fixV != openV
	  public void ristimaDiNuovoInjectedVersions(double pMedio) throws SQLException, IOException {
		 	
		  String bugID = ""; 
		  int fixV=0;
		  int openV=0;
		  int iv=0;
		  String dateIV="";
		  	
		  List<String> listSQLbugID= new ArrayList<>();
	      List<Integer> listSQLiv= new ArrayList<>();
	      List<String> listSQLdatesIV= new ArrayList<>();
	  
	      
		  ResultSet rsTicketsNOinjectedVersion;
		  Connection con;			        
	      con =DBaseBK.connectToDBtickectBugBookkeeper();
		  
	      String pathInfoFileProject = HelpBK.getMyProperty("pathInfoFileProject");
	      String[] datesAllVersions = HelpInfoProjectBK.getDatesOfVersions(pathInfoFileProject);
	        
	      
	      String queryTicketsWITHinjectedVersion="SELECT * "
	    		     +" FROM \"Ticket_FV_OV_P_IV_BK\"  "	      		    
	      		     + "WHERE  \"FV\" = \"OV\"  ";
			
	      try(PreparedStatement stat=con.prepareStatement(queryTicketsWITHinjectedVersion) ){
	  		  rsTicketsNOinjectedVersion=stat.executeQuery();
	  		
	  		
	          while( rsTicketsNOinjectedVersion.next() ) {
	        	  bugID = rsTicketsNOinjectedVersion.getString("TicketBugID");
	        	  fixV = rsTicketsNOinjectedVersion.getInt("FV");
	        	  openV = rsTicketsNOinjectedVersion.getInt("OV");
	        	  
	        	  fixV = fixV + 1;
	            	        	  	        	  
	        	  iv = ProportionBasicBK.calculateIVspecificBug(pMedio,fixV ,openV);
	        	  dateIV = datesAllVersions[iv];
	        	  
	        	  listSQLbugID.add(bugID);
	        	  listSQLiv.add(iv); 
	        	  listSQLdatesIV.add(dateIV);
	          }//while          
	      }//try
	      
	      //Execute query update sql
	      String queryUpdate="UPDATE \"Ticket_FV_OV_P_IV_BK\"  "
	      		     + "SET  \"P\" = ?, \"IV\" = ?, \"DateInjectedVersion\" = ?    "+
					   "WHERE \"TicketBugID\" =?  ";
			  try(PreparedStatement statUpdate=con.prepareStatement(queryUpdate) ){
	    	
			   for (int j = 0; j <  listSQLbugID.size(); j++) {
				 statUpdate.setDouble(1, pMedio); 
				 statUpdate.setInt(2, listSQLiv.get(j) );
				 statUpdate.setString(3, listSQLdatesIV.get(j) );
			     statUpdate.setString(4, listSQLbugID.get(j));
				
				 statUpdate.executeUpdate();	
		       }//for  
			    	 	    	   			   
		  }//try interno
	      
	      
	  }//fine metodo
	
	  
	 
	  
}
