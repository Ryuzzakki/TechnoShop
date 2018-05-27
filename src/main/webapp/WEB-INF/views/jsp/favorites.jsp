<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Favorites</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>
<div class="container">

    <table class="table">
        <thead>
        <tr>
            <td><strong>Your favorite products</strong></td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${ sessionScope.user.favorites }" var="product">
        <tr>
            <td>${ product.name }</td>
            <td>
                <form action="removeFav" method="post">
                    <input type="hidden" name="productId" value="${ product.id }"/>
                    <input type="submit" name="removeproduct" value="Remove Fav"/>
                </form>
            </td>
        </tr>
        </c:forEach>
        <tbody>
    </table>
</div>

</body>
</html>