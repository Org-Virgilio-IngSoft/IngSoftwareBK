/**
 * 
 */
package helper;

import java.util.List;

/**
 * @author Virgilio
 *
 */
public class HelpMathBK {
	
	
	public static int findMax(List<Integer> numbers) {
		int max=numbers.get(0);
		int temp;
		int i=0;
		for( i=1;i<numbers.size();i++) {
			temp=numbers.get(i);
			if(temp>max) {
				max=temp;
			}		
		}
				
		return max;
	}//fine metodo
    
   
    public static double findMean(List<Double> numbers) {
    	double total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total / numbers.size();
    }//fine metodo
    
    public static double findAVG(List<Integer> numbers) {
    	double total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total / numbers.size();
    }//fine metodo
	
    public static int findSum(List<Integer> numbers) {
    	int total = 0;
    	
    	 
    	for(int i=0; i<numbers.size(); i++){
         	total = total + numbers.get(i);        
    	}

        return total;
    }//fine metodo
    
    private HelpMathBK() {
    	
    }
    
}
