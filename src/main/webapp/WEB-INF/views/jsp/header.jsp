<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Menu</title>
   <link href="<c:url value="/css/buttons.css" />" rel="stylesheet"
          type="text/css">

    <script type="text/javascript" src="/js/myscript.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>
<body>


<div class="mainHeader">

    <ul>
        <li>
            <a href="<c:url value="/html/about.html" />">
                <button type="button">About</button>
            </a>
        </li>
        <li>
            <a href="<c:url value="/html/contact.html" />">
                <button type="button">Contacts</button>
            </a>
        </li>
        <li>
            <form action="main">
                <input type="submit" value="Products">
            </form>
        </li>


        <li>
            <button onclick="dropDown()">Categories</button>

            <div id="myDropdown" class="dropdown-content">

                <form action="computers">
                    <input type="submit" value="Computers">
                </form>

                <form action="phones">
                    <input type="submit" value="Phones">
                </form>


                <form action="others">
                    <input type="submit" value="Others">
                </form>

            </div>

        </li>

        <li>
            <form action="myprofile">
                <input type="submit" value="View My profile">
            </form>
        </li>
        <li>
            <form action="address" method="get">
                <input type="submit" value="Delivery Address">
            </form>
        </li>
        <li>
            <form action="cart" method="get">
                <input type="submit" value="MyCart">
            </form>
        </li>
    </ul>

</div>


</body>
</html>