<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Choose a restaurant or get a delivery</title>
</head>
<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">
    <h3>Find Shops or Get delivery?</h3>
    <br>
    <form action="address" method="post">
        <div class="btn-group btn-group-toggle" data-toggle="buttons">
            <label class="btn btn-secondary">
                <input type="radio" id="option1" name="address" autocomplete="off" value="restaurant"/> Shop
            </label>
            <label class="btn btn-secondary">
                <input type="radio" id="option2" name="address" value="home" autocomplete="off"/> Home
            </label>
        </div>
        <button type="submit" class="btn" value="Submit">Submit</button>
    </form>
</div>

</body>
</html>