package com.internal.model;

import java.util.stream.IntStream;

public class ExternalObjectForm {
	
	private String url ;
	private String data ; 
	
	private Object [] [] bookData ; 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getData() { 
		
		return this.data;
	}
	public void setData( StringBuffer sd) {
		
		 String []tokens = sd.toString().split("\n");
		for(int i =0; i < tokens.length && i < 10 ;  i++)
		{ 
			 this.data  += tokens[i];
		
		}
	}
	
	
	@Override
	public String toString() {
		 
	return "ExternalObjectForm [url=" + url + ", bookData=" + printBookData() +" ]";
			
	}

	private String printBookData() {
		
		StringBuffer stfB  = new StringBuffer();
		
		IntStream.range(0, 10 ).forEach( i -> {
			
			  Object data[]  =     bookData[i] ; 
			  
			  IntStream.range(0, 10 ).forEach( j -> {
					
				  Object subdata =  data[j];
				  stfB.append(subdata);
				  
				  
			  });
			  stfB.append("\n");
		});
		
		
		
		
		return stfB.toString();
	} 

}
