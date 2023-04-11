package principale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SnoringReleasesBK {

	
	public String getDateLastReleaseNotSnoring(String pathInfoFileProject) throws IOException {
		
		int count = 0;
    	int i = 0;
    	String lineFile="";  
    	String split[];
		
		try(FileReader fr=new FileReader(pathInfoFileProject);
    	    BufferedReader br=new BufferedReader(fr); ){
			
			int numberReleases = howManyReleasesThereAre(pathInfoFileProject);
	    	count = (numberReleases/2) + 1;
	    	while( (lineFile=br.readLine() ) !=null && i < count ) {
													
	    		i=i+1;
		   }//while
			
		}//try 
		   	    	    	      	
       split=lineFile.split(",");	
       
       //this returns the date
       return split[3].substring(0, 10) ;
    
}//fine metodo
    public int howManyReleasesThereAre(String pathInfoFileProject) throws IOException {
    	
    	int count = 0;
    	
    	try(FileReader fr=new FileReader(pathInfoFileProject);
            BufferedReader br=new BufferedReader(fr); ){
    		
    		while( (br.readLine() ) !=null ) {
				
      			count=count+1; 					  				
            }//while
    		
    	}//try
		   	   	
       return count ;
	
   }//fine metodo
    
}
