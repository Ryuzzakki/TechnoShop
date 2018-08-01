<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link href="<c:url value="/css/buttons.css" />" rel="stylesheet" type="text/css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <link href="/js/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="/js/bootstrap.min.js"></script>


</head>

<body>
<div class="mainHeader">
    <ul class="text-center">
        <li>
            <form action="login">
                <input type="submit" value="Login">
            </form>
        </li>
        <li>
            <form action="register">
                <input type="submit" value="Register here">
            </form>
        </li>
        <li>
            <form action="main">
                <input type="submit" value="See our products">
            </form>
        </li>
    </ul>
</div>

<div class="container">

    <div class="row">
        <div class="col-sm-8">
            <h2 class="mt-4">What Is My WebApp</h2>
            <p>This is my website for selling Computers, Phones and other stuff</p>
        </div>
        <div class="col-sm-4">
            <h2 class="mt-4">Contact Me</h2>
            <address>
                <strong>Ivelin Iliev</strong>
                <br>TU - Sofia
                <br>
            </address>
            <address>
                Email: <a href="mailto:#">ivelin.iliev@gmail.com</a>
            </address>
        </div>
    </div>
    <!-- /.row -->

    <div class="row">
        <div class="col-sm-4 my-4">
            <div class="card">
                <img class="card-img-top" src="/img/laptop 1.png" alt="">
                <div class="card-body">
                    <h4 class="card-title">Computers</h4>
                    <p></p>
                </div>
                <div class="card-footer">
                    <a href="/computers" class="btn btn-info">Find Out More!</a>
                </div>
            </div>
        </div>
        <div class="col-sm-4 my-4">
            <div class="card">
                <img class="card-img-top" src="/img/phone 2.jpg" alt="">
                <div class="card-body">
                    <h4 class="card-title">Phones</h4>
                    <p class="card-text"></p>
                </div>
                <div class="card-footer">
                    <a href="/phones" class="btn btn-info">Find Out More!</a>
                </div>
            </div>
        </div>
        <div class="col-sm-4 my-4">
            <div class="card">
                <img class="card-img-top" src="/img/charger 3.jpg" alt="">
                <div class="card-body">
                    <h4 class="card-title">Others</h4>
                    <p class="card-text"></p>
                </div>
                <div class="card-footer">
                    <a href="/others" class="btn btn-info">Find Out More!</a>
                </div>
            </div>
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.container -->
<!-- Footer -->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Welcome to my Techno Shop
            <a href="<c:url value="/html/about.html" />">
                <button class="btn" type="button">About</button>
            </a>
            <a href="<c:url value="/html/contact.html" />">
                <button class="btn" type="button">Contacts</button>
            </a>
        </p>
    </div>
    <!-- /.container -->
</footer>

</body>
</html>
