<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link href="<c:url value="/css/buttons.css" />" rel="stylesheet"
          type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="text/javascript" src="/js/myscript.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Profile</title>
</head>
<body>

<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>


<div class="mainHeader">
    <ul>
        <li>
            <form action="logout" method="post">
                <input class="btn" type="submit" value="Logout">
            </form>
        </li>
        <li>
            <form action="profileaddress">
                <input class="btn" type="submit" value="Addresses">
            </form>
        </li>
        <li>
            <form action="main">
                <input class="btn" type="submit" value="Menu">
            </form>
        </li>
        <li>
            <form action="favorites">
                <input class="btn" type="submit" value="Favorites">
            </form>
        </li>
        <li>
            <form action="orders">
                <input class="btn" type="submit" value="View My orders">
            </form>
        </li>

        <form action="avatar" method="post" enctype="multipart/form-data">
            <li>
                <input type="submit" class="btn" value="Upload Avatar">
            </li>
            <li>
                <input type="file" name="avatar" accept="image/*"/>
            </li>
        </form>
    </ul>
</div>

<div class="container">
    <h1>Welcome, ${sessionScope.user.firstName}</h1>
    <img class="img-thumbnail" style="max-width:20% " src="avatar">
</div>


</body>
</html>