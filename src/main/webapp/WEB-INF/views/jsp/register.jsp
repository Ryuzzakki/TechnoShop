<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="/js/myscript.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet"
          type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Register</title>
</head>
<body>


<c:if test="${ requestScope.userExists == true }">
    <h2>User already exists!</h2>
</c:if>

<div id="login-box">
    <div class="left">
        <h2>Techno Store</h2>
        <h1>Sign up</h1>
        <form:form action="register" commandName="userNew">

            <form:errors path="firstName" cssclass="error" style="color:red"></form:errors>
            <form:input placeholder="First name" path="firstName"
                        id="firstname"></form:input>

            <form:errors path="lastName" cssclass="error" style="color:red"></form:errors>
            <form:input type="text" path="lastName" placeholder="Last name"/>

            <form:errors path="password" cssclass="error" style="color:red"></form:errors>
            <form:input type="password" path="password" placeholder="Password"/>

            <input type="password" name="confirmPassword"
                   placeholder="Confirm password"/>

            <form:errors path="phoneNumber" cssclass="error" style="color:red"></form:errors>
            <form:input type="text" path="phoneNumber"
                        placeholder="Phone number"/>

            <form:errors path="email" cssclass="error" style="color:red"></form:errors>
            <form:input path="email" placeholder="Email"/>

            <input type="submit"  class="btn" value="Register"/>


        </form:form>
    </div>
    <div class="right">
        <h2>Already have an account?</h2>

        <form action="login">
            <input type="submit" class="btn" value="Login here!">
        </form>
    </div>
    <div class="or">OR</div>
</div>


</body>
</html>

