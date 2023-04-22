/**
 * 
 */
package helper;

import java.io.IOException;

import logger.MyLoggerBK;

/**
 * @author Virgilio
 *
 */
public class HelpPathBK {

	private static String TRAINSETNAME="TrainingSetBK";
	private static String TESTSETNAME="TestSetBK";
	private static final String EXT=".arff";
	
	public static String[] createPathFileTrainingSet() throws IOException {
		
		String pathFolder=HelpBK.getMyProperty("pathArffFolder");
		String nTimes=HelpBK.getMyProperty("numberTrainingSetsToCreate");
		int n=Integer.parseInt(nTimes);
		
		String[] pathTrainSets = new String[n+1]; 
		for (int i = 1; i <= n; i++) {
			
			pathTrainSets[i]=pathFolder+TRAINSETNAME+Integer.toString(i)+EXT;
		}
		
		return pathTrainSets;
	}//fine metodo
	
     public static String[] createPathFileTestSet() throws IOException {
		
    	String pathFolder=HelpBK.getMyProperty("pathArffFolder");	 
    	String nTimes=HelpBK.getMyProperty("numberTestSetsToCreate");
		int n=Integer.parseInt(nTimes); 

		String[] pathTestSets = new String[n+1]; 
		for (int i = 1; i <= n; i++) {
			
			pathTestSets[i]=pathFolder+TESTSETNAME+Integer.toString(i+1)+EXT;
		}
		return pathTestSets;
	}//fine metodo
	
     public static void printPaths(String[] paths) {
    	 
    	int lung=paths.length;
    	
    	for (int i = 1; i < lung; i++) {
			MyLoggerBK.logInfo(paths[i]);
		}
    	 
     }//fine metodo
     
    private HelpPathBK() {
    	
    }
     
}
