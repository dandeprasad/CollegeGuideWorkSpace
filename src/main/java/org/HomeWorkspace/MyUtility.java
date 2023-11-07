package org.HomeWorkspace;

public class MyUtility {

	
    public  String Nullcheck(String key) throws Exception {
if(key==null){
    key="";
}
        return key;



    }

	public boolean NullBoolcheck(String key) {
		if(key==null){
			return false;
		}
		return true;
	}
}
