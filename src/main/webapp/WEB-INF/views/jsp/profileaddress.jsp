<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>
<div class="container">

    <h2>Your addresses:</h2>

    <table class="table">
        <c:forEach items="${ sessionScope.user.address }" var="address">
            <tbody>
            <tr>
                <td>${ address }</td>
                <td>
                    <form action="addressprofile">
                        <input type="hidden" name="address" value="${ address }"/>
                        <input type="submit" name="signup_submit" value="Remove"/>
                    </form>
                </td>
            </tr>
            </tbody>
        </c:forEach>
    </table>


    <form action="addnewaddress">
        <input type="submit" class="btn" value="Add a new Address">
    </form>

</div>

</body>
</html>