// Import required java libraries
/*package com.servlet;
import java.io.*;
import java.util.Objects;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.election.version.one.webmodel.LoginRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Extend HttpServlet class
//https://mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
//https://www.javaguides.net/2019/02/webservlet-annotation-example.html
@WebServlet(urlPatterns =  {"/basic/*", "/b", "/"}, loadOnStartup = 1)
public class BasicServlet extends HttpServlet {

   private String message;
   Logger logger = LoggerFactory.getLogger(BasicServlet.class);
   public void init() throws ServletException {
      // Do required initialization
      message = "Order Form " ;
      logger.info(" Basic Servlet initialization ");

   }

public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


     // Set response content type
     // response.setContentType("text/html");
    
         // Store info to the request attribute before forwarding.
        Object bean =  request.getSession().getAttribute("loginrequest");
         LoginRequest logReq  = (LoginRequest) bean;
         if ( bean !=null) { 
            String orderId = (String )bean.getOrderId();
            logger.info(" Basic Servlet doPost LoginRequest OrderId : "+ orderId);

         }
         else {

         }
        // If the user has logged in, then forward to the page
        // /WEB-INF/views/userInfoView.jsp
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/display.jsp");
        dispatcher.forward(request, response);

}
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

       //String orderId = (String )request.getParameter("orderId");
       String checkHomePage  = request.getRequestURI();
       String chHme = request.getServletPath();
       logger.info(" Basic Servlet doGet RequestURI : " + checkHomePage
       + " ServletPath "+checkHomePage);

       if (chHme.length()==1){
         RequestDispatcher dispatcher //
         = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeForm.jsp");
            dispatcher.forward(request, response);
       }
       String orderId = (String )request.getParameter("orderId");
       
       logger.info(" Basic Servlet doGet OrderId : "
       + (orderId!=null ? orderId :"is null or empty"));
       Object bean =  request.getSession().getAttribute("loginrequest");
       LoginRequest logReq  = (LoginRequest) bean;

       if (  Objects.nonNull(orderId)  ) { 

         if( logReq !=null && !orderId.equals(logReq.getOrderId())) 
         {  logReq.setOrderId(orderId);
            logReq.totalOrdersInc();
         }
         else {
          logReq = new LoginRequest();
          logReq.setOrderId(orderId);
          }
        
       }
       else {
         logReq = new LoginRequest();
         logReq.setOrderId(orderId);
       }
       // Actual logic goes here.
      
         // Store info to the request attribute before forwarding.
         request.getSession().setAttribute("loginrequest", logReq);
       
       doPost(request , response);
      
   }

   public void destroy() {
      // do nothing.
   }
}
 /*PrintWriter out = response.getWriter();
       out.println("<h1>" + message + "Order Id : " +orderId +"</h1>");
       */
      