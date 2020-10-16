package com.sort.employee.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sort.employee.domain.Employee;

public class BasicComparator implements Comparator<Employee> {
     public String comp = "";
	 public BasicComparator(String  comapreOn) {
       this.comp = comapreOn;
       
     }
	 
	 @Override public int compare(final Employee emp1, final Employee emp2) {
		    int c =-1;
		    
		    if (this.comp=="age")
		    { c = emp1.getAge().compareTo(emp2.getAge());
		    
		    }
		    else if (this.comp=="country") {
		    
		    	 c = emp1.getCountry().compareTo(emp2.getCountry());
		    	
		    	
		    }
		  
		    return c;
		}
	 
	 
}
