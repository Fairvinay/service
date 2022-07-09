package com.external.dataservice.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.external.dataservice.service.FileFetchFromURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.external.dataservice.util.ExcelWorkBookCreator;
import com.internal.model.CellMapping;
import com.internal.model.ExternalObjectForm;
import com.internal.model.RowMapping;

//https://www.youtube.com/watch?v=fZe6ClmdbZg  Strangle
/*
 * Emma -- 
 *   aahh i aware of , what we get from 
 *   reach out to someone 
 *  
 *  Maintenance 
 *  
 *  Admin consle -- tony  --- larsen Weebika ... update
 *  sharing a database tool 
 *  like to have the email address 
 *  nice database tool --  tool approved by nordea 
 *  
 *  Aggreement Overwiew Page -- defect 13939 inactivity pop-up 
 *  Pyius Kumar update 
 *  
 *  Mads loking into -- chnage the profile 
 *  able the maged reprocude it , is 
 *  initial value  
 *   wrong value --- 
 *   
 *   Thomsen Bo --- 
 *   
 *   Bhudahni Jadgish 
 *   no wonder you remember -- 
 *   some obersvatoin for Sweden and Finland 
 *   
 *   amalie Ardhal --- 
 *    gives us a half a day 
 *    really have like to have a llooking 
 *    may be similar functionalites 
 *    that will be it 
 *    
 *   testing , bug fixing -- metting 
 *   we are in good shape 
 *   
 *   Behera Bihunanda --- 
 *   
 */
@Controller
public class ExternalReaderController {

	@Autowired
	ExcelWorkBookCreator wbC;
	
	@Autowired
	FileFetchFromURL fRL;
	
	@Autowired
    Environment env;
	
	@Value("${excel.file.location}")
	private String excelPath;
	
	
	//private static final Logger logger = 
	//        LoggerFactory.getLogger(ExternalReaderController.class);
	
	private static final Logger logger = 
			  (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.external.dataservice");
	private static final Logger logger2 = 
			  (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.internal");
	
	 @RequestMapping(value = "/", method = RequestMethod.GET)
	    public String welcome(@ModelAttribute("externalObject") ExternalObjectForm externalObject ,  Model model) {

	        logger.debug("welcome() is executed, value {}", "Data Bank");
	        logger2.debug("welcome() is executed, value {}", "Data Wellcome");
	        
	        //logger.error("This is Error message", new Exception("Testing"));
	        
	        model.addAttribute("msg", "Hello Spring MVC + Logback");
	        return "reader";

	    }
	 
	 @RequestMapping(value = "fetchurl", method = RequestMethod.POST)
	    public String fetch(@ModelAttribute("externalObject") ExternalObjectForm externalObject ,  Model model) {

		    final  String urlString =externalObject.getUrl();
		    StringBuffer content = new StringBuffer();
			 
	        int MAXLINE_TOKENS = 6;
	        Map<String , List<String>> mapListToken= new HashMap<String , List<String >>();
	        List<String> tokenList = new ArrayList<>();
			
			
	        logger.debug("fetch() is being executed ");
	        
	        logger.debug("fetch() url requested  "+urlString); 
			
			// check path contains HTTP/S 
			
			if (urlString.indexOf("http") >-1){ 
			
				// Normal fethc from the HTTP urls 
				mapListToken = fRL.fileFromURL(urlString);
				
				
			}
			else {
			  
				mapListToken = fRL.fileFromLocal(urlString);
			}
	       
	        
	        if(urlString!=null )
	        {	
	             URL url;
				try {
					url = new URL(urlString);
				
	             HttpURLConnection con = (HttpURLConnection) url.openConnection();
	             con.setRequestMethod("GET");
	             con.setConnectTimeout(5000);
	             //con.setReadTimeout(5000);
	             BufferedReader in = new BufferedReader(
	            		  new InputStreamReader(con.getInputStream()));
	             String inputLine;
	             logger.debug("read DAT file started ");
	             while ((inputLine = in.readLine()) != null) {
	            	 inputLine = inputLine.trim();
	            	 if(!inputLine.isEmpty())
	            	 { content.append(inputLine);
	                                
	            	 }
	             }
	            
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("URL reading Error message", e);
				}
	        //logger.error("This is Error message", new Exception("Testing"));
	        }
	        // Sort the Map based on key 
	        
	        Map <String ,List<String>> sortResult = mapListToken.entrySet().stream()
	        		.sorted(Map.Entry.comparingByKey())
	        		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	        				(oldValue ,newValue) -> oldValue,LinkedHashMap::new));
	        
	        
		/*
		 * Map <String ,List<CellMapping>> sortResultCell =
		 * mapListToken.entrySet().stream() .map(x -> { List<CellMapping> cList = new
		 * ArrayList<>(); List<String > xV = x.getValue(); xV.forEach( a ->
		 * {cList.add(new CellMapping() ); } );
		 * 
		 * } ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
		 * (oldValue ,newValue) -> oldValue,LinkedHashMap::new));
		 */
	        
	        Map <String ,List<CellMapping>> sortResultC =  new LinkedHashMap <String ,List<CellMapping>>();
	        
	        for(Entry<String ,List<String>> entry : sortResult.entrySet()) {
	        	 logger.debug("key "+entry.getKey() + "  ");
	        	 List<String > entVal = entry.getValue();
	        	 List<CellMapping> cList=   entVal.stream().map(e -> { CellMapping cm = new CellMapping();
	        	 							cm.setToken(e.toString());
	        	 						return cm;
	        	  } )
	        	  .collect(Collectors.toList());
	        	 sortResultC.put(entry.getKey(), cList);
	        	
	        }
	        		
	       // Entry<String ,List<CellMapping>> entryCell = new Entry<String ,List<CellMapping>>();
	        
	        // print the map Token List 
	        for(Entry<String ,List<String>> entry : sortResult.entrySet()) {
	        	 logger.debug("key "+entry.getKey() + "  ");
	        	 List<String > entVal = entry.getValue();
	        	 String token =   entVal.stream().map(e -> e.toString()).reduce("", String::concat);
	        	 String s = entVal.stream().map(Object::toString).collect(Collectors.joining(","));
	        	 logger.debug("token list "+s +  " \n ");
	        	
	        }
	        RowMapping mappings = new RowMapping();
	        mappings.setRowCells(sortResultC);
	        LocalDate localDate = LocalDate.now();
	        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        String dateString = FOMATTER.format(localDate);
	        // String formattedDate = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
	        System.out.println("SHORT format: " + dateString);
	        SimpleDateFormat formatter = new SimpleDateFormat(
	        	      "dd-MM-yyyy");
	        String name = "NSE_"+ dateString+"_SecurityWise";
	        
	        wbC.setSpreadSheetName(name);
	        wbC.setWorkBookData(content);
	        
	        wbC.writeWorkbookData();
	        
	        externalObject.setData(content);
	        
	        model.addAttribute("data",externalObject.getData());
	        model.addAttribute("scriptData",mappings);
	        model.addAttribute("data_stored_to",excelPath+name);
	        //model.addAttribute("msg", "Hello Spring MVC + Logback");
	        return "datafetch";

	    }
	 
}
