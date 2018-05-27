<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Orders</title>
</head>


<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>
<div class="container">
    <h2>Your previous orders:</h2>

    <a href="sortOrders?sort=desc">
        <button class="btn"> Newest to oldest</button>
    </a>
    <a href="sortOrders?sort=asc">
        <button class="btn"> Oldest to newest</button>
    </a>

    <c:forEach items="${ sessionScope.user.orders }" var="order">
        <h4>Date ${ order.orderDate }</h4>
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Quantity</th>
                <th>Extra</th>
                <th>Info</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ order.products }" var="productEntry">
                <tr>
                    <td>${productEntry.key.name }</td>
                    <td>${productEntry.value }</td>
                    <c:if test="${ productEntry.key.category == 1  }">
                        <td>${productEntry.key.dough }</td>
                        <td>${productEntry.key.size }</td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
    </c:forEach>
</div>
</body>
</html>