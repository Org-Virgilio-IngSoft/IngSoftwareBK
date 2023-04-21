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
public class LOCADDEDmetricBK implements Runnable {

private int versione;
	
	public LOCADDEDmetricBK(int versione) {
		this.versione=versione;
	}
	
	@Override
	  public void run() {
	    // use the parameter here
		 
			try {
				calculateLOCADDEDforSpecificVersion(versione);
			} catch (SQLException | IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		
	  }//fine metodo
	
	
public void calculateLOCADDEDforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpBK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateLOCADDEDforSpecificVersion(i);
		}
				
	}//fine metodo
	
public void calculateLOCADDEDforSpecificVersion(int version) throws SQLException, IOException, InterruptedException {
		
		
		List<String> listFiles=new ArrayList<>();		
		int locAdded=0;	
		int locAddedMax=0;
		double locAddedAvg=0.0;
		List<Integer> listLocAdded=new ArrayList<>();
		
		String logMsg="LOCADDED BK V "+Integer.toString(version)+" ";
		
		CommandGitShowBK cmdgitShow=new  CommandGitShowBK();
				
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection conn2=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connUpdate=DBaseBK.connectToDBtickectBugBookkeeper();
		ResultSet rsJavaNames;
		ResultSet rsLocAdded;
		
		String queryForClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+ "FROM \"ListJavaClassesBK\"  "
			    + "WHERE \"NameClass\" LIKE '%.java' AND \"Version\"= ?   ";
		
		try(PreparedStatement stat=conn.prepareStatement(queryForClasses) ){
			stat.setInt(1, version);
			rsJavaNames=stat.executeQuery();
		
			
            while( rsJavaNames.next() ) {
        	
        	 
			 String fileJavaName=rsJavaNames.getString("NameClass");
			 MyLoggerBK.logInfo( logMsg.concat(fileJavaName) );
				   				
			 String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+ "WHERE  \"NameClass\" =? AND \"Version\"= ? ";
					
			
			 try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileJavaName);
				stat2.setInt(2, version);
				rsLocAdded=stat2.executeQuery();
				
				while(rsLocAdded.next()) {
    				   	  				    				   				   	  				     				   	  				    				   	  		  				
			  	  String commit = rsLocAdded.getString("Commit");
			    
			      listFiles=cmdgitShow.commandGitShow(commit);		   				  
				
			      handleListFilesGitShow(listFiles, fileJavaName,listLocAdded);
			      				 
				
			  }//while interno
				
			  locAdded=HelpMathBK.findSum(listLocAdded);
			  locAddedAvg=HelpMathBK.findAVG(listLocAdded);
			  locAddedMax=HelpMathBK.findMax(listLocAdded);
				
								
			  String queryUpd="UPDATE \"DataSetBK\"  "+
		              "SET  \"LOCadded\"= ?, \"MaxLOCadded\"= ? , \"AvgLOCadded\" = ? "+
				      "WHERE \"NameClass\" = ?  AND  \"Version\" = ? " ;
				           		 		
			  try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpd)){
					
					statUpd.setInt(1, locAdded);
					statUpd.setInt(2, locAddedMax);
					statUpd.setDouble(3 , locAddedAvg);
					statUpd.setString(4, fileJavaName);
					statUpd.setInt(5, version);
					statUpd.executeUpdate();
				}//try interno
			  
			  listLocAdded.clear();	
				
			}//try medio
			
			
          }//while
	   }//try
		
	}//fine metodo
	
	
	public void handleListFilesGitShow(List<String> listFiles,String fileJavaName, List<Integer> listLocAdded) {
		
		String[] bufferSplit;
		String locAddedString;
		boolean foundFile=false;
		boolean buffSplitHasRightLenght=false; 
		
		int size=listFiles.size();
		
		  for(int i=(size-1);i>=0;i--) {
			
			  if(listFiles.get(i).contains(fileJavaName) ) {
				  foundFile=true;
			  }
			
			  bufferSplit=listFiles.get(i).split("\t");
			 
			  if((bufferSplit.length) ==3 ) {
				buffSplitHasRightLenght=true;
			  }
			
			  if(foundFile && buffSplitHasRightLenght) {
				
				 bufferSplit=listFiles.get(i).split("\t");
									
				 locAddedString=bufferSplit[0];				
				 locAddedString=specialCaseLOCaddedValue(locAddedString);
				
				 int locAdded=Integer.parseInt(locAddedString);
				
				 listLocAdded.add(locAdded);
								 
				 break;
			  }//if
			
		  }//for
		
	}//fine metodo
	
	//metodo che elimina il caso LocAdded = "-"  
	public String specialCaseLOCaddedValue(String locAddedString) {
			
		if(locAddedString.equals("-")) {
			locAddedString="0";
		}
		return locAddedString;
		
	}//fine metodo
	
}
