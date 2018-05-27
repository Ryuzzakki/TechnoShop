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
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link href="<c:url value="/css/styles2.css" />" rel="stylesheet"
          type="text/css">
    <title>Login</title>
</head>
<body>

<div class="container">
    <c:if test="${ sessionScope.user != null }">
        <c:redirect url="main"></c:redirect>
    </c:if>

    <c:if test="${ requestScope.userNotExists == true }">
        <div class="alert alert-warning">
            <strong>Warning!</strong> Wrong e-mail or password! Try again!
        </div>
    </c:if>

    <c:if test="${ requestScope.userRegistered == true }">
        <div class="alert alert-info">
            <strong>Info!</strong> Successful register. You may login now!
        </div>
    </c:if>
</div>

<div id="login-box">
    <div class="left">
        <h3>Techno Store</h3>
        <h1>Login here!</h1>
        <form action="login" method="post">
            <input type="text" name="email" placeholder="E-mail"/>
            <input type="password" name="pass" placeholder="Password"/>
            <input type="submit" class="btn" name="login_submit" value="Login"/>
        </form>
    </div>
    <div class="right">
        <h2>Don't have an account?</h2>
        <form action="register">
            <input type="submit" class="btn" value="Register here!">
        </form>
        <br>
        <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
        </fb:login-button>
        <div id="status"></div>
        <script type="text/javascript">

        </script>
    </div>
    <div class="or">OR</div>
</div>


<script>
    function statusChangeCallback(response) {
        console.log('statusChangeCallback');
        console.log(response);
        if (response.status === 'connected') {
            testAPI();
        } else if (response.status === 'not_authorized') {
            document.getElementById('status').innerHTML = 'Login with Facebook ';
        } else {
            document.getElementById('status').innerHTML = 'Login with Facebook ';
        }
    }

    function checkLoginState() {
        FB.getLoginStatus(function (response) {
            statusChangeCallback(response);
        });
    }

    window.fbAsyncInit = function () {
        FB.init({
            appId: '1491542940937674',
            cookie: true,
            xfbml: true,
            version: 'v2.2'
        });
        FB.getLoginStatus(function (response) {
            statusChangeCallback(response);
        });
    };
    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id))
            return;
        js = d.createElement(s);
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

    function testAPI() {
        console.log('Welcome! Fetching your information.... ');
        FB
            .api(
                '/me?fields=name,email',
                function (response) {
                    console.log('Successful login for: '
                        + response.name);

                    document.getElementById("status").innerHTML = '<p>Welcome to our Techno Site '
                        + response.name
                        + '! <a href=facebook?user_name='
                        + response.name.replace(" ", "_")
                        + '&user_email='
                        + response.email
                        + '>Continue with facebook login</a></p>'
                });
    }

    FB.logout(function (response) {
    });
</script>

</div>
</body>

</html>