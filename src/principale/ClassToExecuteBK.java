/**
 * 
 */
package principale;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import helper.HelpBK;
import metrics.NAUTHmetricBK;
import proportion.ProportionBK;

/**
 * @author Virgilio
 *
 */
public class ClassToExecuteBK {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, ParseException, SQLException, InterruptedException {
		Logger logger=Logger.getLogger("MyLogger");	
		 
		/*String pathLogBK = HelpBK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageBK.calculateLinkageBK(pathLogBK);
			
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);*/
		
		/*ReleasesBK release = new ReleasesBK();
		String pathFileTicketsWithAffectedVersions = HelpBK.getMyProperty("pathFileTicketsWithAffectedVersions");
		String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpBK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionBK");
		release.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
		release.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        String pathTicketsBugWithFVOVdatesBK = HelpBK.getMyProperty("pathTicketsBugWithFVOVdates");
        release.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);*/
		
		/*ProportionBK proportion = new ProportionBK();		
		proportion.calcolaProportionTicketsWithIV();
		double pMedio = proportion.calculatePmedio();
		System.out.println("pMedio "+pMedio);
		proportion.ristimaDiNuovoInjectedVersions(pMedio);*/
		
		/*AutoriBK autori = new AutoriBK();
		String pathLog = HelpBK.getMyProperty("pathLogFileNOsnoring");
		autori.getNameAutorCommitDateCommitfromGitLog(pathLog);*/
		
		/*String pathLog = HelpBK.getMyProperty("pathLogFileNOsnoring");
		CommitTicketBK com = new CommitTicketBK();
		com.createTripleCommitTicketDate(pathLog);*/
		
		/*JavaClassesProjectBK javaClasses = new JavaClassesProjectBK();
		String pathLog = HelpBK.getMyProperty("pathLogFileNOsnoring");
		String pathProjFile = HelpBK.getMyProperty("pathInfoFileProject");
		javaClasses.createPairsVersionJavaClass(pathLog, pathProjFile);*/
		
		
		NAUTHmetricBK auth= new NAUTHmetricBK();
		auth.caculateNAUTHforEveryVersion();
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
