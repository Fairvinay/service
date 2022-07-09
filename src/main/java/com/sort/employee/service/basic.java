
import java.util.*;
public class  basic { 

 public static void main( String[] args )
    {

String abd = "aabgderfass";
	
LinkedHashMap<String,String> lnkUniLargSubStrng = new LinkedHashMap<>();
	
 for(int i =0 ; i < abd.length(); i++ ) 
		
  {
   char uniChar = abd.charAt(i);
		
       if(!lnkUniLargSubStrng.containsKey(uniChar+""))
		{
		lnkUniLargSubStrng.put(uniChar+"", i +"");
	
		 }
			
	 else {
				 
		lnkUniLargSubStrng.remove(uniChar+"");
	
	  }
			 
	
   }
	
     Set<String> keySet = lnkUniLargSubStrng.keySet();

   System.out.println("String : " + abd);
   
   System.out.print("largest uni subStr :  ");
   for(String k : keySet) {
		
 	System.out.print(""+k);
		
   }
   System.out.println(" ");
} 

} 	
