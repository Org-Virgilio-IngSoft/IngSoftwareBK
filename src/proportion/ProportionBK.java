/**
 * 
 */
package proportion;

/**
 * @author Virgilio
 *
 */
public class ProportionBK {

	
	public double calculatePspecificBug(int fixV, int openV,int injectedV) {
		double p;
			
		if(fixV==openV) {			
			return -(Math.PI);
		}
		
		p=( ((double)fixV) - injectedV)/(fixV - openV);
		
		return p;
	}//fine metodo
	
	public int calculateIVspecificBug(double p,int fixV, int openV) {
		
		int injectedV;
			
		int intP=(int) Math.round(p);
		
		injectedV= fixV - (fixV-openV) * intP;
		
		return injectedV;
		
	}//fine metodo
	
	//ristima di nuovo InjectedV di bug per cui fixV == openV e p non è calcolabile conseguentemente
	// il parametro double p è calcolato con in bug effettivamente disponibili per cui fixV != openV
	  public void ristimaDiNuovoInjectedV(double p) {
		
		  
		  
	  }//fine metodo
	
}
