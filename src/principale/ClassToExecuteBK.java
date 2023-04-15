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
	 */
	public static void main(String[] args) throws IOException, ParseException, SQLException {
		Logger logger=Logger.getLogger("MyLogger");	
		 
		String pathLogBK = HelpBK.getMyProperty("pathLogFileLinkage");			
		double linkage = LinkageBK.calculateLinkageBK(pathLogBK);
			
		logger.log(Level.INFO ,"LINKAGE BOOKKEEPER: {0}", linkage);
		
		String pathFileTicketsWithAffectedVersions = HelpBK.getMyProperty("pathFileTicketsWithAffectedVersions");
		String pathTicketsIDwithAffectedVersionAndIDversionBK = HelpBK.getMyProperty("pathTicketsIDwithAffectedVersionAndIDversionBK");
		ReleasesBK.findAffectedVersionsIndex(pathFileTicketsWithAffectedVersions);
        ReleasesBK.findInjectedVersions(pathTicketsIDwithAffectedVersionAndIDversionBK);
		
        String pathTicketsBugWithFVOVdatesBK = HelpBK.getMyProperty("pathTicketsBugWithFVOVdates");
		ReleasesBK.findFixVersionsOpenVersionsIndex(pathTicketsBugWithFVOVdatesBK);
		
		ProportionBK proportion = new ProportionBK();		
		proportion.calcolaProportionTicketsWithIV();
		double pMedio = proportion.calculatePmedio();
		proportion.ristimaDiNuovoInjectedVersions(pMedio);
		
		logger.log(Level.INFO ,"FINE ClassToExecuteBK!!");
			
	}//fine main

}
