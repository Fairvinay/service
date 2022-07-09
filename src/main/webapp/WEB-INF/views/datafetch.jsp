<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" rel="nofollow">
<!--  https://stackoverflow.com/questions/59772804/spring-boot-primefaces-modular-app-dont-see-xhtml-page  -->
<!--  https://notearena.com/spring-mvc-helloworld-example-maven-bootstrap/  -->
<html>
<head>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/styles/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script
	src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.20/css/dataTables.bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>External Reader</title>
</head>
<body>
   <c:set var="entry" value="${scriptData.rowCells}" scope="page"></c:set>
   
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Basket Property </a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Dashboard</a></li>
			<li><a href="#">Add URL</a></li>

		</ul>
	</div>
	</nav>
	<div style="width: 700px; margin: auto;">
		<h3 align="center">
			<b>External : Data Management System</b>
		</h3>
		<form:form class="form-horizontal" role="form" action="fecthurl.do"
			modelAttribute="externalObject">
			<div class="form-group">
				<h2>Data Read Details</h2>
				
				 <div class="panel panel-default">
				 <div class="panel-heading">Snippet of Market Data Read</div>
 				 <div class="panel-body">
 				 	<table  cellspacing=0 class="table table-bordered table-hover table-striped" id="example">
 				 	  <thead>
				            <tr>
				           
				                <th>Record Type</th>
				                <th>Sr No</th>
				                <th>Name of Security</th>
				                <th>Quantity Traded</th>
				                <th>Deliverable Quantity(gross across client level)</th>
				                <th>% of Deliverable Quantity to Traded Quantity</th>
				            </tr>
				        </thead>
				       <tbody>
				          <!-- this gives me exception -->
				            <c:forEach items="${entry}" var="m" varStatus="loop">
				             <c:set var="keyNum" value="${m['key']}" scope="page"></c:set> 
				              <c:set var="listTokens" value="${m['value']}" scope="page"></c:set> 
				              <c:if test = "${keyNum != 1 }">
				              
				              	  <tr>
				                    <td><c:out value="${listTokens['0'].token}"/></td>
				                      <td><c:out value="${listTokens['1'].token}"/></td>
				                        <td><c:out value="${listTokens['2'].token}"/></td>
				                          <td><c:out value="${listTokens['3'].token}"/></td>
				                            <td><c:out value="${listTokens['4'].token}"/></td>
				                              <td><c:out value="${listTokens['5'].token}"/></td>
				    		  	 </tr>
				    		  
				    		   </c:if>
				            </c:forEach>
				
				        </tbody>
				    </table>
 				 
 				 
 				 </div>
 				 <div class="panel-footer">
 				   <strong>Data Stored To :</strong>
 				 ${data_stored_to}</div>
					</div>
			</div>
			
			
		</form:form>
	</div>

</body>
<script type="text/javascript">

$(document).ready(function() {
	  $('#example').DataTable();
	});
</script>
</html>

<!--  
  c:forEach items="${listTokens}" var="cel" varStatus="loop"
  	  c:forEach
  <thead>
				            <tr>
				           
				                <th>Record Type</th>
				                <th>Sr No</th>
				                <th>Name of Security</th>
				                <th>Quantity Traded</th>
				                <th>Deliverable Quantity(gross across client level)</th>
				                <th>% of Deliverable Quantity to Traded Quantity</th>
				            </tr>
				        </thead>

 -->