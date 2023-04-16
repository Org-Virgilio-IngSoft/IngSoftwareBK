package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import helper.HelpBK;

public class CommandGitShowBK {

   public List<String> commandGitShow(String commit) throws IOException, InterruptedException {
				
		List<String> resultOK = new ArrayList<>();
		String lineOK;
	
		String pathRepoBookkeeper = HelpBK.getMyProperty("pathRepoBookkeeper");
				
		ProcessBuilder pb = new ProcessBuilder();
	    
	    File fromFolder = new File(pathRepoBookkeeper);
	    pb.directory( fromFolder);
	    pb.command("git", "--no-pager", "show",commit,"--numstat");
	    pb.redirectErrorStream(true);
		
		Process process = pb.start();
					
		try(InputStream isOK = process.getInputStream();		
			InputStreamReader isr = new InputStreamReader( isOK );		
			BufferedReader brOK = new BufferedReader(isr);
					                                    ){
										
			while( (lineOK=brOK.readLine()) != null) {				
					resultOK.add(lineOK);								
			}//while
			
			process.waitFor();							
			return resultOK;			
		}//try 
		
	}//fine metodo
	
}
