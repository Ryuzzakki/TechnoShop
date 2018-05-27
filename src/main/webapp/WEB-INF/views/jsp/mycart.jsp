<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <title>Cart</title>
</head>
<body>

<c:if test="${fn:length(sessionScope.productsInCart) le 0}">
    <c:redirect url="main"></c:redirect>
</c:if>

<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>

<jsp:include page="header.jsp"></jsp:include>

<!-- <table border="1"> -->

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-10 col-md-offset-1">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Add</th>
                    <th>Parts</th>
                    <th class="text-center">Price</th>
                    <th>Remove</th>
                    <th>Quantity</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>

                <c:forEach var="product" items="${sessionScope.productsInCart}">


                <tbody>
                <tr>
                    <td class="col-sm-8 col-md-6">
                        <div class="media">
                            <div class="media-body">
                                <h4 class="media-heading"><c:out value="${ product.key.name }"></c:out></h4>
                                <span>Status: </span><span class="text-success"><strong>In Stock</strong></span>
                            </div>
                        </div>
                    </td>
                    <td class="col-sm-1 col-md-1" style="text-align: center">
                        <form action="add" method="post">
                            <input type="hidden" name="productId" value="${ product.key.id }"/>
                            <input type="submit" class="form-control" name="cart_submit" value="+"/>
                        </form>
                    </td>


                    <td>
                        <c:forEach var="ingredient" items="${product.key.ingredients}">
                            <c:out value="${ingredient.name }"></c:out>
                        </c:forEach>
                    </td>
                    <td class="col-sm-1 col-md-1 text-center"><c:out value="${ product.key.price }"></c:out></td>
                    <td class="col-sm-1 col-md-1 text-center">
                        <form action="remove" method="post">

                            <input type="hidden" name="productId" value="${ product.key.id }"/>
                            <input type="hidden" name="currentProductValue" value="${ product.value }"/>
                            <input type="hidden" name="productValue" min="1" max="${ product.value }"/>
                            <input type="submit" class="form-control" name="cart_submit" value="-"/>
                        </form>
                    </td>

                    <td class="col-sm-1 col-md-1"><c:out value="${ product.value }"></c:out></td>
                    <td class="col-sm-1 col-md-1">
                        <!-- if trqbva> -->
                        <c:if test="${ product.key.category == 1  }">
                        <form action="modify" method="POST">
                            <input type="hidden" name="productId" value="${ product.key.id }"/>
                            <input type="submit" class="btn btn-primary" name="cart_submit" value="Modify"/>
                        </form>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
                <tr>
                    <td>  </td>
                    <td>  </td>
                    <td>  </td>
                    <td>  </td>
                    <c:if test="${ sessionScope.totalPrice != sessionScope.totalPriceWithDiscount }">
                        <td class="text-right"> <h5><strong> Old :  <span style="text-decoration: line-through;"><c:out value="${ sessionScope.totalPrice }"></c:out></span> </strong> </h5> </td>
                           <td class="text-right"> <h5><strong> Price : <c:out value="${ sessionScope.totalPriceWithDiscount }"></c:out> </strong> </h5></td>
                    </c:if>
                    <c:if test="${ sessionScope.totalPrice == sessionScope.totalPriceWithDiscount }">
                        <td><h5>Subtotal</h5></td>
                        <td class="text-right"><h5><strong> <c:out value="${ sessionScope.totalPrice }"></c:out></strong></h5></td>
                    </c:if>
                    <td>
                        <form action="makeOrder" method="post">
                            <input type="submit" class="btn btn-success" onclick="return confirm('Are you sure?')"
                                   name="cart_submit"
                                   value="Make Order!"/>
                        </form>
                    </td>
                </tr>
                <tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>