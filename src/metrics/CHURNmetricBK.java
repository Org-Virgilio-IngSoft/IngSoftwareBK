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

/**
 * @author Virgilio
 *
 */
public class CHURNmetricBK {
	
public void calculateCHURNforEveryVersion() throws IOException, SQLException, InterruptedException {
		
		int i=0;
		String maxNumberOfversions = HelpBK.getMyProperty("maxNumberOfversions"); 
		int max = Integer.parseInt(maxNumberOfversions);
		
		for ( i = 1; i <= max ; i++) {
			calculateCHURNforSpecificVersion(i);
		}
				
	}//fine metodo
	
public void calculateCHURNforSpecificVersion(int version) throws IOException, InterruptedException, SQLException {
		
		List<String> listaFile=new ArrayList<>();
		int churn=0;
		int churnMax=0;
		double churnAvg=0;
		List<Integer> listChurnValues=new ArrayList<>();
	
		CommandGitShowBK comGitShow= new  CommandGitShowBK();
				
		Connection conn=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection conn2=DBaseBK.connectToDBtickectBugBookkeeper();
		Connection connUpdate=DBaseBK.connectToDBtickectBugBookkeeper();
		ResultSet rsJavaClasses;
		ResultSet rsChurn;
		
		String queryClasses="SELECT DISTINCT \"NameClass\", \"Version\"  "
				+"FROM \"ListJavaClassesBK\" "
			    +"WHERE \"NameClass\" LIKE '%.java'  AND \"Version\"= ? ";
					
		try(PreparedStatement stat=conn.prepareStatement(queryClasses) ){
			stat.setInt(1, version);
			rsJavaClasses=stat.executeQuery();
		
				
          while( rsJavaClasses.next() ) {
        	
			String fileNameJava=rsJavaClasses.getString("NameClass");
			
			String query2 = "SELECT * FROM \"ListJavaClassesBK\"  "
					+"WHERE  \"NameClass\" =? AND \"Version\"= ? ";
			
			try(PreparedStatement stat2=conn2.prepareStatement(query2) ){
				stat2.setString(1, fileNameJava);
				stat2.setInt(2, version);
				rsChurn=stat2.executeQuery();
				
				while(rsChurn.next()) {
																
		          String commit = rsJavaClasses.getString("Commit");
		    
		          listaFile=comGitShow.commandGitShow(commit);		    			      
			
			      handleListFilesGitShow(listaFile, fileNameJava, listChurnValues);
			     
          }//while interno
				
			churn=HelpMathBK.findSum(listChurnValues);
	        churnAvg=HelpMathBK.findAVG(listChurnValues);
		    churnMax=HelpMathBK.findMax(listChurnValues);	
				
			String queryUpdChurn="UPDATE \"DataSetBK\"  "
	                   +"SET  \"Churn\"= ? , \"MaxChurn\"= ? , \"AvgChurn\"= ? "                
			           +"WHERE \"NameClass\"= ?  AND \"Version\"= ? ";
			           		
			
			try(PreparedStatement statUpd=connUpdate.prepareStatement(queryUpdChurn)){
				statUpd.setInt(1, churn);
				statUpd.setInt(2, churnMax);
				statUpd.setDouble(3, churnAvg);
				statUpd.setString(4, fileNameJava);
				statUpd.setInt(5, version);
				
				statUpd.executeUpdate();
			}//try interno
			
			listChurnValues.clear();
			
          }//try medio	
		}//while
		
	}//try
		
					 
}//fine metodo 
	
public void handleListFilesGitShow(List<String> listaFile,String fileNameJava, List<Integer> listChurnValues) {
	
	  String[] bufSplit;		
	  String locAdded="/";
	  String locDeleted="/";
	  boolean fileTrovato=false;
	  boolean buffSplitLenghtOK=false;
	
	 int sizeList=listaFile.size();
	 for(int j=(sizeList-1);j>=0;j--) {
	
	
	  if(listaFile.get(j).contains(fileNameJava)) {
		fileTrovato = true;
	  }
	
	  bufSplit = listaFile.get(j).split("\t");
	
	  if( (bufSplit.length) == 3 ) {
	    buffSplitLenghtOK = true;
	  }
	  if(  fileTrovato && buffSplitLenghtOK ) {
	     bufSplit = listaFile.get(j).split("\t");
							
		 locAdded=bufSplit[0];
		 locDeleted=bufSplit[1];
							
		 locAdded=specialCaseChurnValuselocAdded(locAdded);
		 locDeleted=specialCaseChurnValuselocDeleted(locDeleted);
		
		 int churn=Integer.parseInt(locAdded)-Integer.parseInt(locDeleted);
		
		 listChurnValues.add(churn);
							
		 fileTrovato=false;
		 buffSplitLenghtOK=false;
		 break;
	 }//if
	
   }//for
	
}//fine metodo

	
	//metodo che elimina il caso LocAdded = "-"  	
public String specialCaseChurnValuselocAdded(String locAdded) {
		
	if(locAdded.equals("-")) {
		return "0";
	}
	return locAdded;
					
}//fine metodo


//metodo che elimina il caso LocDeleted = "-"  
public String specialCaseChurnValuselocDeleted(String locDeleted) {
	
	if(locDeleted.equals("-")) {
		return "0";
	}
	return locDeleted;
	
}//fine metodo

	
}

	


	