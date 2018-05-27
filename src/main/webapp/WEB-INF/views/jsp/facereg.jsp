<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/css/styles2.css" />" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facebook Register</title>
</head>
<body>
 <center>

</center>

<div id="login-box">
		<div class="left">
			<form action="facebookregister" method="post">
			<h3>Choose a password for your account</h3	>
				<input type="hidden" name="firstName" value="${ param.firstName }"/> 
				<input type="hidden" name="lastName" value="${ param.lastName }"/>
				<input type="hidden" name="email" value="${ param.email }"/>
				<input type="password" name="pass" placeholder="Password" /> 
				<input type="password" name="pass2" placeholder="Confirm Password" /> 
				<input type="text" name="phone" placeholder="Phone number" /> 
				<input type="submit" name="login_submit" value="Facebook Reg" />
			</form>
		</div> 
		<div class="right">
			<h2>Hello  ${ param.firstName }	</h2>
			<h3>Already have an account?</h3>
			<form action="login">
				<input type="submit" value="Login here!">
			</form>
		</div>
		<div class="or">or</div>
	</div>
</body>
</html>