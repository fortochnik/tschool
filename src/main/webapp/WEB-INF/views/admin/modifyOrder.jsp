<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 27.09.2016
  Time: 0:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/custom/ordershistory.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/login/form-elements.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom/userprofile.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="page-header">
        <jsp:include page="/WEB-INF/views/header.jsp"/>
    </div>
</div>
<div class="panel-group">


    <div class="panel panel-default">
        <!-- Заголовок 3 панели -->
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${order.id}"/>">Order
                    #
                    <c:out value="${order.id}"/></a>
            </h4>

        </div>


        <div id="collapse<c:out value="${order.id}"/>" class="">
            <form method="post" action="modifyorder" id="modify<c:out value="${order.id}"/>">

                <!-- Содержимое 3 панели -->
                <div class="container">
                    <c:set var="total" value="${0}"/>
                    <div class="row">
                        <div class="panel-body">
                            <div id="order-<c:out value="${order.id}"/>" class="catlist">
                                <c:forEach items="${order.productList}" var="productInList">
                                    <dl>
                                        <c:set var="totalProduct"
                                               value="${productInList.price * productInList.count}"/>
                                        <c:set var="total" value="${total + totalProduct}"/>
                                        <dt><img src="image?image=${productInList.product.id}-image1.jpg"
                                                 alt="Product image" width="93"
                                                 height="62"/>
                                            <strong>$<c:out value="${productInList.price}"/> *
                                            <c:out
                                                    value="${productInList.count}"/> ($<c:out
                                                    value="${totalProduct}"/>)</strong><a
                                                href="product/<c:out value="${productInList.product.id}"/>"><c:out
                                                value="${productInList.product.name}"/></a></dt>
                                        <dd>
                                            <p><c:out value="${productInList.product.parameters}"/></p>
                                        </dd>
                                    </dl>
                                </c:forEach>
                            </div>
                            <p class="help-block"></p>
                            <ul class="list-group">
                                <li class="list-group-item"><b>State : </b><c:out value="${order.state}"/></li>
                                <li class="list-group-item"><b>State : </b><c:out value="${order.state}"/></li>
                                <li class="list-group-item"><b>Order date : </b><c:out value="${order.orderDate}"/>
                                </li>
                                <%--<li class="list-group-item"><b>Delivery date : </b><c:out value="${order.deliveryDate}"/></li>--%>
                                <li class="list-group-item"><b>Order status : </b><c:out
                                        value="${order.orderStatus}"/>
                                    <select class="selectpicker show-tick" name="form-delivery-status"
                                            id="form-delivery-type"
                                            title="Choose delivery type...">
                                        <option name="PLACED" value="PLACED">Plaiced</option>
                                        <option name="DELIVERY" value="DELIVERY">Delivery</option>
                                        <option name="RECEIVED" value="RECEIVED">Received</option>
                                    </select>
                                </li>
                                <li class="list-group-item"><b>Delivery type : </b><c:out
                                        value="${order.deliveryType}"/>

                                </li>
                                <li class="list-group-item"><b>Payment type : </b><c:out
                                        value="${order.paymentType}"/>
                                </li>
                                <li class="list-group-item"><b>Payment status: </b><c:out
                                        value="${order.paymentStatus}"/>

                                    <select class="selectpicker show-tick" name="form-payment-status"
                                            id="form-payment-sratus"
                                            title="Choose payment sratus...">
                                        <option name="NOT_PAID" value="NOT_PAID">Not paid</option>
                                        <option name="PAID" value="PAID">Paid</option>
                                    </select>
                                </li>

                                <li class="list-group-item"><b>Address: </b><c:out
                                        value="${order.address.zipcode}"/>;
                                    <c:out
                                            value="${order.address.country.country}"/>, <c:out
                                            value="${order.address.city}"/>,
                                    street <c:out value="${order.address.street}"/>, <c:out
                                            value="${order.address.building}"/> app.<c:out
                                            value="${order.address.apartment}"/></li>

                            </ul>
                            <span class="pull-right">Total price: $<c:out value="${total}"/></span>

                        </div>
                        <p class="help-block"></p>
                        <c:if test="${not empty saved}">
                            <div class="form-group top15">
                                <div class="alert alert-success fade in ">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    Saved!
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>


                <button class="btn .btn-xs btn-primary" name="order-update-admin" value="<c:out value="${order.id}"/>"
                        type="submit" <%--onclick="submitForms()"--%>>
                    Update
                </button>
            </form>
        </div>
    </div>

</div>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>


<!-- Datapicker https://itchief.ru/lessons/bootstrap-3/113-bootstrap-3-datetimepicker -->
<%--todo extract scripts to files--%>

<script src="${pageContext.request.contextPath}/resources/js/moment/moment-with-locales.min.js"></script>


</body>

</html>
