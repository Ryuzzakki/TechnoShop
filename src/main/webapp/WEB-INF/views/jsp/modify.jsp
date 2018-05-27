<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>Modify</title>
</head>
<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>
<jsp:include page="header.jsp"></jsp:include>

<div class="container-fluid">
    <br>
    <br>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4 border text-center">
            <c:forEach items="${sessionScope.order.products}" var="product">
                <c:if test="${ product.key.id == param.productId }">
                    <h2>
                            ${ product.key.name }
                    </h2>
                    <p>
                        <c:forEach items="${product.key.parts}" var="part">
                            <c:out value="${part.name}"></c:out>
                        </c:forEach>
                    </p>
                    <p>
                    <form action="cart" method="get">
                        <input type="hidden" name="productId" value="${ currentProduct }" /> <br>
                        <input class="btn btn-info" type="submit" value="Im ready!">
                </form>
                    </p>
                </c:if>
            </c:forEach>
            <div id="card-419299">
                <div class="card">
                    <div class="card-header">
                        <a class="card-link" data-toggle="collapse" data-parent="#card-419299"
                           href="#card-element-30813">Customize your Item</a>
                    </div>
                    <div id="card-element-30813" class="collapse show">
                        <div class="card-body">


                            <c:set var="currentProduct" value="${param.productId}" scope="page"/>
                            <c:forEach items="${applicationScope.parts}" var="part">

                                <form action="modify" method="post">
                                    <input type="hidden" name="partId" value="${ part.id }"/>
                                    <input type="hidden" name="productId" value="${ currentProduct }"/>
                                    <input class="btn btn-sm btn-default" type="submit" name="cart_submit"
                                           value=" ${ part.name }"/>
                                </form>
                                <br>

                                <c:forEach items="${sessionScope.order.products}" var="product">
                                    <c:if test="${ product.key.id == param.productId }">
                                        <c:forEach items="${product.key.parts}" var="ing">


                                            <c:if test="${ ing.id == part.id }">
                                                <form action="removeing" method="post">
                                                    <input type="hidden" name="partId"
                                                           value="${ part.id }"/>
                                                    <input type="hidden" name="productId" value="${ currentProduct }"/>
                                                    <input class="btn btn-sm btn-default" type="submit" name="remove_part"
                                                           value="Remove"/>
                                                </form>
                                                <br>
                                            </c:if>

                                        </c:forEach>
                                    </c:if>
                                </c:forEach>

                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
            <br>
        </div>
    </div>
</div>
<div class="col-md-4">
</div>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/myscript.js"></script>
</body>
</html>
</html>