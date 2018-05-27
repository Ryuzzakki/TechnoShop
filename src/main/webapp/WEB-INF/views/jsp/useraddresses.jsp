<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:include page="header.jsp"></jsp:include>

    <title>Pick an address</title>
</head>
<body>

<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<div class="container">

    <table class="table">
        <thead>

        <form action="addnewaddress">
            <input type="submit" class="btn" value="New Address">
        </form>

        </thead>
        <tbody>
        <c:forEach items="${ sessionScope.user.address }" var="address">
            <tr>
                <td>${ address }</td>
                <td>
                    <form action="homeDelivery" method="post">
                        <input type="hidden" class="btn" name=homeAddress value="${ address }"/>
                        <input type="submit" class="btn" name="signup_submit" value="Choose address"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

</body>
</html>