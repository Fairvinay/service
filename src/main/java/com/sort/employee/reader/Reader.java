package com.sort.employee.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sort.employee.domain.Employee;

public class Reader {
  
	 static Logger logger = LoggerFactory.getLogger(Reader.class);
	
	 static String fileName = "employee.json";
	//Add employee to list
     static JSONArray employeeList = new JSONArray();
     
	 public static List<Employee> employees = new ArrayList<>();
	 
	 
	 public static void init () {
		 
			//JSON parser object to parse read file
		        JSONParser jsonParser = new JSONParser();
		        
		        ClassLoader classLoader =Reader.class.getClassLoader();
		        File file = new File(classLoader.getResource(fileName).getFile());
		         
		        BufferedInputStream is = new BufferedInputStream(classLoader.getResourceAsStream("employee.json"));
		         
		      
		        try (FileReader reader = new FileReader(file))
		        {
		            //Read JSON file
		            Object obj = jsonParser.parse(reader);
		 
		            employeeList = (JSONArray) obj;
		            System.out.println(employeeList);
		            logger.info(" employeeList size ",employeeList.size());
		            //Iterate over employee array
		            employeeList.forEach( employee -> { employees.add(parseEmployee( (JSONObject) employee )); } );
		 
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		 }
	 private static Employee parseEmployee(JSONObject employee) 
	    {
	        //Get Employee object within list
	        JSONObject employeeObject = (JSONObject) employee.get("employee");
	         
	        //Get employee first name
	        String name = (String) employeeObject.get("firstname");    
	        System.out.println(name);
	         
	        //Get employee last name
	        String lastName = (String) employeeObject.get("lastname");  
	        System.out.println(lastName);
	         
	        String age = (String) employeeObject.get("age");  
	        String country = (String) employeeObject.get("country");  
	        
	        Employee nPeop = new Employee();
	        
	        
	        nPeop.setFirstName(name); 
	        nPeop.setLastName(lastName); 
	         nPeop.setAge(age);
	         nPeop.setCountry(country);
	        
	        return nPeop;
	        
	    }
		
	 
}
