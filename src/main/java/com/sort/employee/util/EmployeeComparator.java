package com.sort.employee.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.sort.employee.domain.Employee;

public class EmployeeComparator implements Comparator<Employee> {
	
	protected List<String> fields;
	
	 public EmployeeComparator(List<String>  orderedFields) {
         fields = new ArrayList<String>();
         for (String field : orderedFields) {
             fields.add(field);
         }
     }
	 
	 public int compare(Employee emplA, Employee emplB) {
         Integer sameCnt = 0;
         Boolean moveNext = true;
         Iterator itFields = fields.iterator();

         while (itFields.hasNext() && moveNext) {
             String field = (String) itFields.next();
             Integer isequal = 0;
             if (field.equalsIgnoreCase("age")) {
            	 isequal = emplA.getAge().compareTo(emplB.getAge());
             } else if (field.equalsIgnoreCase("country")) {
            	 isequal = emplA.getCountry().compareTo(emplB.getCountry());
             } else if (field.equalsIgnoreCase("firstname")) {
            	 isequal = emplA.getFirstName().compareTo(emplB.getFirstName());
             }
             else if (field.equalsIgnoreCase("lastname")) {
            	 isequal = emplA.getLastName().compareTo(emplB.getLastName());
             }
             if (isequal != 0) {
            	 moveNext = false;
             }
             sameCnt = isequal;
         }

         return sameCnt;
     }
	 
	 
}
