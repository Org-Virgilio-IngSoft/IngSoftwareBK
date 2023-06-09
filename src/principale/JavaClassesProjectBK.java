package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import commands.CommandGitShowBK;
import database.DBaseBK;
import helper.HelpBK;
import helper.HelpInfoProjectBK;

public class JavaClassesProjectBK { 

	//metodo per associare una versione ad una classe java
		public void createPairsVersionJavaClass(String fileLogGit,String projectInfo) throws IOException, ParseException, SQLException, InterruptedException{
						 
			String line;		 	
			int version;
			String commit="";
			String dataCommit="/";
			List<String> nameFiles; 
			int indexDataJavaClassVersion;
			
		    Connection con;

			String queryInsert;			
					
			String[] datesVersions;
			int[] versions;
			
			con=DBaseBK.connectToDBtickectBugBookkeeper();
				
			versions=HelpInfoProjectBK.getVersionsIndex(projectInfo);
			datesVersions = HelpInfoProjectBK.getDatesOfVersions(projectInfo);
		
					
			try (
				FileReader fr=new FileReader(fileLogGit);
				BufferedReader br=new BufferedReader(fr);			
					                                      ){
				
				while( (line=br.readLine() ) !=null ) {
						
					 if(line.startsWith("commit") ) {
							commit=line.substring(7);					
					 }
					 
					 
					 if(line.startsWith("Date") ) {
						dataCommit=line.substring(8);					
					 }
					
				   				
					if( line.startsWith("    BOOKKEEPER-") ||  line.startsWith("    [BOOKKEEPER-") ) {	
										  
					    nameFiles=searchAndGetFileNames(commit);
					    indexDataJavaClassVersion=HelpBK.dateBeforeDate(dataCommit, datesVersions);					
						version=versions[indexDataJavaClassVersion];
						
						
					    for(int i=0;i<nameFiles.size();i++) {
									
						 queryInsert="INSERT INTO \"ListJavaClassesBK\" ( \"NameClass\" , \"Commit\" , \"DateCommit\" , \"Version\")  " + 
								"VALUES ( ? , ?, ? ,?) ";
								
						
						try(PreparedStatement statUpdate=con.prepareStatement(queryInsert)){
							statUpdate.setString(1, nameFiles.get(i) );
	    					statUpdate.setString(2, commit);
	    			        statUpdate.setString(3, dataCommit);
	    			        statUpdate.setInt(4, version);
						    statUpdate.executeUpdate();
						}//try interno
						
						
					  }//for
					}//if
				  				
						
		          }//while
			}//try esterno
			
				 		
		}//fine metodo
		
		
      public List<String> searchAndGetFileNames(String commit) throws IOException, InterruptedException {
			List<String> commandResult;
			int sizeResult=0;
			
			List<String> nameFiles=new ArrayList<>();
			
			
			String[] buffSplit;
			String line;
			int i;
			
			CommandGitShowBK cmdShow=new CommandGitShowBK();
			
			commandResult=cmdShow.commandGitShow(commit);
			sizeResult=commandResult.size();
			
			
			i=sizeResult-1;
			while(!(line=commandResult.get(i)).equals("") && i>=0) {
			   
			   buffSplit=line.split("\t");	
		       if( (buffSplit.length) ==3 ) {
		    	   
		    	   String file=buffSplit[2];
			       nameFiles.add(file);	       	    	       	      
			   } 
		       
		       i=i-1;
			}   
		      	      	
			return nameFiles;
		}//fine metodo
	
}
