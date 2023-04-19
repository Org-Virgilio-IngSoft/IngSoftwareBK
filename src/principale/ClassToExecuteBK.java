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
import metrics.LOCADDEDmetricBK;
import metrics.NAUTHmetricBK;
import proportion.ProportionBK;
import weka.CreateArffFileBK;
import weka.ConvertCsvToArffBK;
import weka.WalkForwardBK;

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
		String pathLogNOsnoring = HelpBK.getMyProperty("pathLogFileNOsnoring");
		 
		String pathLogLinkageBK = HelpBK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageBK.calculateLinkageBK(pathLogLinkageBK);		
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);
		
		
		ReleasesBK release = new ReleasesBK();
		String pathFileTicketsWithAffectedVersions = HelpBK.getMyProperty("pathFileTicketsWithAffectedVersions");
		String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpBK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionBK");
		release.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
		release.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        String pathTicketsBugWithFVOVdatesBK = HelpBK.getMyProperty("pathTicketsBugWithFVOVdates");
        release.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);
		
		
		ProportionBK proportion = new ProportionBK();		
		proportion.calcolaProportionTicketsWithIV();
		double pMedio = proportion.calculatePmedio();
		logger.log(Level.INFO ,"pMedio : {0}", pMedio);		
		proportion.ristimaDiNuovoInjectedVersions(pMedio);
		
		
		AutoriBK autori = new AutoriBK();		
		autori.getNameAutorCommitDateCommitfromGitLog(pathLogNOsnoring);
		
	
		CommitTicketBK commit = new CommitTicketBK();
		commit.createTripleCommitTicketDate(pathLogNOsnoring);
		
		JavaClassesProjectBK javaClasses = new JavaClassesProjectBK();
		String pathProjFile = HelpBK.getMyProperty("pathInfoFileProject");
		javaClasses.createPairsVersionJavaClass(pathLogNOsnoring, pathProjFile);
		
		
		NAUTHmetricBK auth= new NAUTHmetricBK();
		auth.caculateNAUTHforEveryVersion();
		
		LOCADDEDmetricBK loc= new LOCADDEDmetricBK();
		loc.calculateLocAdded();
		
		String pathDatasetCSV = HelpBK.getMyProperty("pathDatasetCSV");
		String pathDatasetARFF = HelpBK.getMyProperty("pathDatasetARFF");
		CreateArffFileBK.createArffFile("ZOOKEEPERVersionInfo");
		ConvertCsvToArffBK.convertMyDataset(pathDatasetCSV, pathDatasetARFF);
		
		WalkForwardBK walkForward = new WalkForwardBK();		
		walkForward.walkForwardTraining(pathDatasetARFF);
		walkForward.walkForwardTest(pathDatasetARFF);
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
