<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Footer</title>
	<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
	<div class="footer">
		<a href="<c:url value="/html/about.html" />"><button type="button">About</button></a>
		<a href="<c:url value="/html/contact.html" />"><button type="button">Contacts</button></a>
	</div>
</body>
</html>