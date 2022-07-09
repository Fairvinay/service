<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" rel="nofollow">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!--  https://stackoverflow.com/questions/59772804/spring-boot-primefaces-modular-app-dont-see-xhtml-page  -->
<!--  https://notearena.com/spring-mvc-helloworld-example-maven-bootstrap/  -->
<!--  
http://websystique.com/springmvc/spring-4-mvc-helloworld-tutorial-annotation-javaconfig-full-example/
https://www.baeldung.com/java-http-request
https://springframework.guru/logback-configuration-using-xml/
https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
https://stackoverflow.com/questions/18825950/how-to-get-a-index-value-from-foreach-loop-in-jstl
https://stackoverflow.com/questions/36935230/logback-does-not-creates-log-file
https://stackoverflow.com/questions/9935648/logback-variable-substitution-could-not-locate-property-file-on-the-classpath
https://mkyong.com/java8/java-8-how-to-sort-a-map/
https://stackoverflow.com/questions/1835683/how-to-loop-through-a-hashmap-in-jsp

https://www.tutorialspoint.com/compile_java_online.php
 -->
<html>
<head>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>External Reader</title>
</head>
<body>

	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Basket Property </a>
		</div>
		<!--  https://www1.nseindia.com/archives/equities/mto/MTO_12022021.DAT -->
		<ul class="nav navbar-nav"> <!--  <c:url value="/dashboard.do" /> -->
			<li class="active"><a href="#">Dashboard</a></li>
			<li><a href="#">Add URL</a></li>

		</ul>
	</div>
	</nav>
	<div style="width: 700px; margin: auto;">
		<h3 align="center">
			<b>External : Data Management System</b>
		</h3>
		<form:form class="form-horizontal" role="form" action="fetchurl"
			modelAttribute="externalObject">
			<div class="form-group">
				<h2>Presenting Data Management system</h2>
				
				<label class="control-label col-sm-2" for="name">External URL :</label>
				<div class="col-sm-10">
			    <form:input path="url" value="" type="text" class="form-control" placeholder="External URL"/>
			    
			    
				 </div>
				<div class="form-group"> 
					<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Submit</button>
					 <c:if test = "${ 1 == 1 }"> 
				     <button type="button" class="btn btn-default">Cancel</button>
				     </c:if>
					</div>
				 </div>
			</div>
			
			
		</form:form>
	</div>

</body>
</html>