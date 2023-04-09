/**
 * 
 */
package principale;

import java.io.IOException;

/**
 * @author Virgilio
 *
 */
public class ClassToExecuteBK {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Linkage link = new Linkage();
		
		String pathBK = "D:\\Libri\\Universita\\ProgettoFalessi\\BookkeeperRepo\\gitLogLinkage.txt";
			
		double l = link.CalculateLinkageBK(pathBK);
		System.out.println("LINKAGE BOOKKEEPER: " + l);
		
	}

}
