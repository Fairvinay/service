package com.external.dataservice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ExcelWorkBookCreator {

	private static final Logger logger = 
			  (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.external.dataservice");
	//private static final Logger logger = LoggerFactory.getLogger(ExcelWorkBookCreator.class);
	
	private ResourceLoader resourceLoader;
	
	@Value("${excel.file.location}")
	private String excelPath;

	String spreadName;

	XSSFWorkbook workbook = new XSSFWorkbook();

	XSSFSheet spreadsheet = workbook.createSheet("Market Data");
	
	Object[][] bookData = {
            {"Dummy", "Mdummy", 79},
            {"Effective Java", "Joshua Bloch", 36},
            {"Clean Code", "Robert martin", 42},
            {"Thinking in Java", "Bruce Eckel", 35},
    };
	StringBuffer bookDataStr;  
	

	public void setSpreadSheetName(String name) {
		spreadName = name;

	}

	public  XSSFWorkbook getWorkbook() {
		return this.workbook;
	}
	
	public void setWorkBookData(Object[][] exterBookData ) { 
		
		this.bookData =exterBookData;
	}

	public void setWorkBookData(StringBuffer exterBookData ) { 
		
		this.bookDataStr =exterBookData;
		
	}
	public void writeWorkbookData() {

		
		 int rowCount = 0;
		 String []tokens = bookDataStr.toString().split("\n");
		 
//		 for(int i = tokens.length - 1; i >= 0; --i)
//	      {
//			 logger.debug(" " + tokens[i]);
//	      }
//		 
//		    for(int i = tokens.length - 1; i >= 0; --i)
//		      {
//	            Row row = spreadsheet.createRow(++rowCount);
//	             
//	            int columnCount = 0;
//	             
//	            for (Object field : tokens) {
//	                Cell cell = row.createCell(++columnCount);
//	                if (field instanceof String) {
//	                    cell.setCellValue((String) field);
//	                } else if (field instanceof Integer) {
//	                    cell.setCellValue((Integer) field);
//	                }
//	            }
//	             
//	        }
//		
		
	  //Resource resource = resourceLoader.getResource("file:c:/temp/filesystemdata.txt");
		File fp = new File (excelPath + spreadName + ".xlsx");
		try (FileOutputStream outputStream = new FileOutputStream( fp, false)) {
			workbook.write(outputStream);
			logger.debug("writeWorkbookData is executed, value {}", spreadName + ".xlsx");

		} catch (FileNotFoundException  e) {
			System.out.println("FileNot Found Exception ");

			logger.error(spreadName + ".xlsx FileNot Found Exception ", e);

		} catch (IOException e) {
			System.out.println(spreadName + ".xlsx IOException ");

			logger.error(spreadName + ".xlsx FileNot Found Exception ", e);
		}

	}

	 /*
	 String word = ""; 
	        for(int i = bookDataStr.length() - 1; i >= 0; --i)
	        {
	            if(bookDataStr.charAt(i) != '\n')
	            {
	                word = bookDataStr.charAt(i) + word;
	            }
	            else{
	                System.out.println(word);
	                word = "";
	            }
	        }   
	   
	    List<Optional<String>> optionalList = Arrays.asList(
                Optional.of("hello"),
                Optional.empty(),
                Optional.of("world"),
                Optional.empty(),
                Optional.of("welcome to JavaProgramTo.com blog"));

        List<String> nonEmptyValuesList = optionalList
                .stream()
                .filter(o -> o.isPresent())
                .map(Optional::get)
                .collect(Collectors.toList());
	   
	    // https://spring.io/projects/spring-hateoas#samples
	    // https://howtodoinjava.com/java7/forkjoin-framework-tutorial-forkjoinpool-example/ 
	    // https://www.springboottutorial.com/spring-boot-and-iBatis-with-h2-tutorial
	    // https://hexopus.hexaware.com/AttendanceSSO/WFHRequestSummary.aspx
	    // https://spring.io/projects/spring-data-jdbc#learn
	    // https://www.javatpoint.com/java-8-functional-interfaces
	  */
}
