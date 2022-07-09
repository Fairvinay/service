package com.external.dataservice.service;
/*
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
// https://crunchify.com/5-different-ways-to-download-a-file-from-any-given-url-in-java/
// https://howtodoinjava.com/spring-boot2/read-file-from-resources/
// https://mkyong.com/java/java-https-client-httpsurlconnection-example/
*/
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import org.springframework.util.FileCopyUtils; 

import java.nio.charset.StandardCharsets;


 
/**
 * @author Vinayak Anvekar
 * <p>
 * In Java How to Download file from any given URL?
 * <p>
 * 5 different ways:
 * 1. File Download using apache commons-io. Just line of code
 * 2. File Download using Stream Operation
 * 3. File Download using NIO Operation
 * 4. File Download using Files.copy()
 * 5. File Download using Apache HttpComponents()
 */
 @Service
 @PropertySource("classpath:file.properties")
public class FileFetchFromURL {

	@Autowired
    Environment env;
 private static final Logger logger = 
			  (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.external.dataservice");
	private static final Logger logger2 = 
			  (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.internal"); 
			  
   
   @Value("${excel.file.location}")
   private String excelPath;
   
   private static String dataPath = "";
  
   @PostConstruct
	public void init() {
		System.out.println("Inside Init method");
		logger.info("Inside Init method");
		String excelPath = env.getProperty("DATA_PATH");
		this.dataPath = excelPath;
		System.out.println("Outside Init method" + dataPath);
		logger.info("Outside Init method" + dataPath);
	}

	
    public static Map<String , List<String>>  fileFromLocal(String urlString) {
		int MAXLINE_TOKENS = 6;
	     Map<String , List<String>> mapListToken= new HashMap<String , List<String >>();
	     List<String> tokenList = new ArrayList<>();
		  StringBuffer content = new StringBuffer();
			try {
			
				String excelPath = urlString!=null ? urlString : dataPath;
				// read downloaded file and then read data 
				BufferedReader in = new BufferedReader(
	            		  new InputStreamReader(new FileInputStream(new File (excelPath))));
	             String inputLine;
	             logger.debug("read DAT file started ");
	             while ((inputLine = in.readLine()) != null) {
	            	 inputLine = inputLine.trim();
	            	 if(!inputLine.isEmpty())
	            	 { content.append(inputLine);
	                                
	            	 }
	             }
				  String []tokens = content.toString().split("\n");
	             logger.debug("tokens.length  " +tokens.length);
	             for(int i = tokens.length - 1; i >= 0; --i)
	             {
	       			 logger.debug(" - -  " + tokens[i]);
 	       	      }
	             String [] skipSettlementToken = content.toString().split("<N>");
	             logger.debug("skipSettlementToken.length  " +skipSettlementToken.length);
	             
	             for(int j = 0; j < skipSettlementToken.length ;j++)
	             {
	       			 logger.debug("-- " + skipSettlementToken[j]);
	       			 // take 6 tokens to form a line 
	       			 String allCommaSep = skipSettlementToken[j];
	       			 int lineTokens = 0;
	       			 String word = "";
	       			 
	       			 if(j ==1 )
	       			 { 
	       				for(int k = 0 ; k < allCommaSep.length() ; k++ )
	       				{ if(allCommaSep.charAt(k) != ',')
			 	            {
			 	                word =  word + allCommaSep.charAt(k) ;
			 	            }
			 	            else{
			 	                System.out.println(word);
			 	                tokenList.add(word);
			 	                if(tokenList.size() ==  6) {
			 	                	lineTokens++; 
			 	                	mapListToken.put(String.valueOf(lineTokens), tokenList);
			 	                	tokenList = new ArrayList<>();
			 	                }
			 	                word = "";
			 	            }
	       				} 
	       			 } 
 	       	      }
	             
	             
	             in.close();
	             logger.debug(" read DAT file ended ");
							 
			 }catch(Exception ex) {
				  logger.info(" Lcoal Data file reading failed " );
				  
			  }
	  return mapListToken;
		
	}

    public static Map<String , List<String>>  fileFromURL(String urlString) {
		
		 int MAXLINE_TOKENS = 6;
	     Map<String , List<String>> mapListToken= new HashMap<String , List<String >>();
	     List<String> tokenList = new ArrayList<>();
		  StringBuffer content = new StringBuffer();
		  
		 if(urlString!=null)
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
	             String []tokens = content.toString().split("\n");
	             logger.debug("tokens.length  " +tokens.length);
	             for(int i = tokens.length - 1; i >= 0; --i)
	             {
	       			 logger.debug(" - -  " + tokens[i]);
 	       	      }
	             String [] skipSettlementToken = content.toString().split("<N>");
	             logger.debug("skipSettlementToken.length  " +skipSettlementToken.length);
	             
	             for(int j = 0; j < skipSettlementToken.length ;j++)
	             {
	       			 logger.debug("-- " + skipSettlementToken[j]);
	       			 // take 6 tokens to form a line 
	       			 String allCommaSep = skipSettlementToken[j];
	       			 int lineTokens = 0;
	       			 String word = "";
	       			 
	       			 if(j ==1 )
	       			 { 
	       				for(int k = 0 ; k < allCommaSep.length() ; k++ )
	       				{ if(allCommaSep.charAt(k) != ',')
			 	            {
			 	                word =  word + allCommaSep.charAt(k) ;
			 	            }
			 	            else{
			 	                System.out.println(word);
			 	                tokenList.add(word);
			 	                if(tokenList.size() ==  6) {
			 	                	lineTokens++; 
			 	                	mapListToken.put(String.valueOf(lineTokens), tokenList);
			 	                	tokenList = new ArrayList<>();
			 	                }
			 	                word = "";
			 	            }
	       				} 
	       			 } 
 	       	      }
	             
	             
	             in.close();
	             logger.debug(" read DAT file ended ");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("URL reading Error message", e);
				}
	        //logger.error("This is Error message", new Exception("Testing"));
	        }
		return mapListToken;
	}
// Method-3: File Download using NIO Operation
	private static void fileDownloadUsingNIO(String URL, String FileLocalPath) throws IOException {
		URL RobotsURL = new URL(URL);
		
		// ReadableByteChannel(): A channel that can read bytes. Only one read operation upon a readable channel may be in progress at any given time. If one thread initiates a read operation upon a channel then any other thread that attempts to initiate another read operation will block until the first operation is complete.
		// Whether or not other kinds of I/O operations may proceed concurrently with a read operation depends upon the type of the channel.
		
		// openStream(): Opens a connection to this URL and returns an InputStream for reading from that connection.
		ReadableByteChannel ByteChannel = Channels.newChannel(RobotsURL.openStream());
		
		// FileOutputStream(): A file output stream is an output stream for writing data to a File or to a FileDescriptor. Whether or not a file is available or may be created depends upon the underlying platform. Some platforms, in particular, allow a file to be opened for writing by only one FileOutputStream (or other file-writing object) at a time.
		// In such situations the constructors in this class will fail if the file involved is already open.
		FileOutputStream OutputStream = new FileOutputStream(FileLocalPath);
		
		// getChannel(): Returns the unique FileChannel object associated with this file output stream.
		// transferFrom(): Transfers bytes into this channel's file from the given readable byte channel.
		// An attempt is made to read up to count bytes from the source channel and write them to this channel's file starting at the given position. An invocation of this method may or may not transfer all of the requested bytes; whether or not it does so depends upon the natures and states of the channels.
		// Fewer than the requested number of bytes will be transferred if the source channel has fewer than count bytes remaining, or if the source channel is non-blocking and has fewer than count bytes immediately available in its input buffer.
		
		OutputStream.getChannel().transferFrom(ByteChannel, 0, Long.MAX_VALUE);
		// A constant holding the maximum value a long can have.
		
		// Closes this file output stream and releases any system resources associated with this stream. This file output stream may no longer be used for writing bytes.
		OutputStream.close();
		
		// Closes this channel. After a channel is closed, any further attempt to invoke I/O operations upon it will cause a ClosedChannelException to be thrown.
		ByteChannel.close();
		
		logger.info("File Downloaded Successfully with Java NIO ReadableByteChannel Operation \n");
		
	}
  
   
   public static void writeFile(String... args) throws Exception 
   {
    //Resource resource = new ClassPathResource("classpath:data.txt");
	
	BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(dataPath));
		
	FileOutputStream outputStream = new FileOutputStream(dataPath);
    //InputStream inputStream = resource.getInputStream();
	
	
    try {
     /*   byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
        String data = new String(bdata, StandardCharsets.UTF_8);
        logger.info(data);
		*/
		byte[] Space = new byte[2048];
		int Counter = 0;
		
		while ((Counter = inputStream.read(Space, 0, 1024)) != -1) {
			outputStream.write(Space, 0, Counter);
		}
		
		outputStream.close();
		inputStream.close();
    } catch (IOException e) {
      logger.error("IOException", e);
    }
  }
  
  /*
  Below are the ways to load external resources:
	Load resource from the application root folder
	To load a file from the application folder, use this.
	Resource resource = resourceLoader.getResource("file:data.txt");
	Load resource from classpath
	To load a file from the classpath, use this.
	Resource resource = resourceLoader.getResource("classpath:data.txt");
	Load resource from the filesystem
	To load a file from filesystem outside the application folder, use the below template:
	Resource resource = resourceLoader.getResource("file:c:/temp/filesystemdata.txt");
	Load resource from URL
	Similarly, to load a file from any URL, use below template:
	Resource resource = resourceLoader.getResource("https://testsite.com/data.txt");
  
  */
  
  
  
  
  
  
}