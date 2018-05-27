<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="<c:url value="/css/tables.css" />" rel="stylesheet" type="text/css">
    <title>Main</title>
</head>
<body>

<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>

<div class="container">

    <c:if test="${ requestScope.added == true }">
        <div class="alert alert-success">
            <strong>Success!</strong> Item Added to Cart!.
        </div>
    </c:if>
    <c:if test="${ requestScope.order == true }">
        <div class="alert alert-success">
            <strong>Success!</strong> Order made successfully.
        </div>
    </c:if>
</div>


<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>