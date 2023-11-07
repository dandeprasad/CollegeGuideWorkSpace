import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.LocalDateTime;    
public class datecheck {    
  public static void main(String[] args) {    

	  LinkedHashMap<String,String> hm=new LinkedHashMap<String,String>();
	  hm.put("NIT_PY","Amit");  
	  hm.put("NIT_C","Vijay");  
	  hm.put("NIT_K","Rahul");
	  
	  for(Map.Entry m:hm.entrySet()){  
		   System.out.println(m.getKey()+" "+m.getValue());  
		  }
	if(  hm.containsKey("NIT_PY")){
		hm.remove("NIT_PY");
		 hm.put("NIT_PY","Amit"); 
	}
	  for(Map.Entry m:hm.entrySet()){  
		   System.out.println(m.getKey()+" "+m.getValue());  
		  }
  }    
} 