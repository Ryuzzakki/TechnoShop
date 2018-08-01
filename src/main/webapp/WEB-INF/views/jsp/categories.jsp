<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="/js/myscript.js"></script>
    <script src="/js/jquery.min.js"></script>
    <link href="/js/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="<c:url value="/css/buttons.css" />" rel="stylesheet" type="text/css">
    <title>Main</title>
</head>
<body>

<c:if test="${ sessionScope.user != null }">
    <jsp:include page="header.jsp"></jsp:include>
</c:if>

<c:if test="${ requestScope.added == true }">
    <h4>Successfull add to cart!</h4>
</c:if>

<c:if test="${ requestScope.order == true }">
    <h4>Successfull order!</h4>
</c:if>


<c:if test="${ sessionScope.user == null }">
    <div class="mainHeader">
        <ul>
            <li>
                <form action="login">
                    <input type="submit" value="Login here" class="btn">
                </form>
            </li>
        </ul>
    </div>
</c:if>


<div class="container">

    <c:if test="${ requestScope.favorite == true }">
        <div class="alert alert-info">
            <strong>Info!</strong> Item successfully added to favorites!
        </div>
    </c:if>
    <c:if test="${ requestScope.hasFavorite == true }">
        <div class="alert alert-warning">
            <strong>Info!</strong> Item already in favorites!
        </div>
    </c:if>


    <div class="columns">
        <div class="row">
            <c:forEach items="${applicationScope.products}" var="product">
                <c:if test="${ product.category == requestScope.category }">
                    <div class="main-menu">
                        <div class="col-md-4" style="min-width: 300px;">
                            <figure class="card card-product">
                                <div class="img-wrap">
                                    <img src="productPic?currentProductId=${ product.id }"
                                         height="auto">
                                </div>
                                <figcaption class="info-wrap">
                                    <h4 class="title">${ product.name }</h4>
                                    <p class="desc">Price: ${ product.price }</p>
                                    <div class="rating-wrap" style="min-height: 50px;">
                                        <div class="label-rating"><c:forEach items="${product.parts}" var="ingr">
                                            <c:out value="${ ingr.name }"></c:out>
                                        </c:forEach></div>
                                    </div>
                                </figcaption>
                                <div class="bottom-wrap">
                                    <form action="cart" method="post">
                                        <input type="hidden" name="productId" value="${ product.id }"/>
                                        <input class="btn btn-primary float-right" type="submit"
                                               name="cart_submit"
                                               value="Add to Cart"/>
                                    </form>
                                    <c:if test="${ sessionScope.user != null }">
                                        <form action="makeFav" method="post">
                                            <input type="hidden" name="productId" value="${ product.id }"/>
                                            <input class="btn btn-primary float-left" type="submit"
                                                   name="makeFav"
                                                   value="Make Favorite"/>
                                        </form>
                                    </c:if>
                                </div>
                            </figure>
                        </div>
                    </div>
                </c:if>


            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>