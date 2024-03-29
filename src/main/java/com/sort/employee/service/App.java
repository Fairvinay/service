package com.sort.employee.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

import com.sort.employee.domain.Employee;
import com.sort.employee.reader.Reader;
import com.sort.employee.util.EmployeeComparator;

/**
 * Employee World 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	if (args.length < 2) {

			System.out.println("Pass the data as App Employee.json Age, Country ");
			System.exit(1);
		}
    	if (args[1].isEmpty()) {
    		
    		System.out.println("Pass the data as App Employee.json Age, Country ");
    	}
    	
		String abd = "aabgderfass";
		
		LinkedHashMap<String,String> lnkUniLargSubStrng = new LinkedHashMap<>();
		
		for(int i =0 ; i < abd.length(); i++ ) 
		{
			 char uniChar = abd.charAt(i);
			 
			 if(!lnkUniLargSubStrng.containsKey(uniChar+"")) {
				 lnkUniLargSubStrng.put(uniChar+"", i +"");
			 }
			 else {
				 lnkUniLargSubStrng.remove(uniChar+"");
			 }
			 
		}
	     Set<String> keySet = lnkUniLargSubStrng.keySet();
		 
		 for(String k : keySet){
			System.out.println(" "+k);
		 }
		 
		
		
        List<String> complist = new ArrayList<>();
    	for ( int i=1 ; i < args.length; i++ ) {
    		complist.add(args[i]);
    	}
    	// Read the employees 
    	Reader.init();
    	List<Employee> empList = Reader.employees;
    	// pass the sort List to Comparator
    	EmployeeComparator empComp = new EmployeeComparator(complist);
    	//BasicComparator basComp = new BasicComparator("age");
    	
    	// sort the employees based on Comparator
    	Collections.sort(empList, empComp);
    	//basComp = new BasicComparator("country");
    	//Collections.sort(empList, basComp);
    	
    	// print the sorted list 
    	empList.forEach(System.out::println);
    	
    }
}
