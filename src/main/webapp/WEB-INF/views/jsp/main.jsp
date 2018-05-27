
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/css/tables.css" />" rel="stylesheet" type="text/css">
<title>Main</title>
</head>
<body>
	<center>
		<c:if test="${ sessionScope.user == null }">
			<c:redirect url="login"></c:redirect>
		</c:if>
	
		<jsp:include page="header.jsp"></jsp:include>
	
		<c:if test="${ requestScope.added == true }">
			<h4>Successfull add to cart!</h4>
		</c:if>
	
		<c:if test="${ requestScope.order == true }">
		
			<h1>Successfull order!</h1>
		
		</c:if>
	</center>
		
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>