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
<div class="panel-group" id="accordion">

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane active in" id="home">
            <div class="container">
                <div class="row">

                    <div class="container">
                        <table class="table">

                            <tbody>


                            <tr>
                                <td>
                                    <fieldset>
                                        <%----%>
                                        <form class="form-horizontal left" id="by-order-number" method="post"
                                              action="orders">
                                            <div class="control-group">
                                                <label class="control-label">Order Number</label>
                                                <p class="help-block"></p>

                                                <div class="controls  ">
                                                    <input id="order-number" name="order-number" type="text"
                                                           placeholder="search by order number"
                                                           class="input-xlarge" value="">

                                                </div>
                                            </div>
                                            <p class="help-block"></p>
                                            <div class="control-group">
                                                <label class="control-label">Email</label>
                                                <p class="help-block"></p>

                                                <div class="controls  ">
                                                    <input id="email" name="email" type="text"
                                                           placeholder="search by client's email"
                                                           class="input-xlarge" value="">

                                                    <p class="help-block"></p>
                                                </div>
                                            </div>
                                            <input hidden name="is-custom-search" value="true">
                                            <button class="btn btn-primary"
                                                    type="submit" <%--onclick="submitForms()"--%>>
                                                Search
                                            </button>
                                            <p class="help-block"></p>
                                        </form>
                                    </fieldset>
                                </td>
                                <td>
                                    <form class="form-horizontal right" id="by-not-delivered" method="post"
                                          action="orders">
                                        <button class="btn btn-primary" name="all-not-delivered" value="true"
                                                type="submit" <%--onclick="submitForms()"--%>>
                                            Not delivered
                                        </button>
                                        </form>
                                </td>
                                <td>
                                    <form class="form-horizontal left" id="by-not-paid" method="post"
                                          action="orders">
                                        <button class="btn btn-primary" name="all-not-paid" value="true"
                                                type="submit" <%--onclick="submitForms()"--%>>
                                            Not paid
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <form class="form-horizontal left" id="orders-all" method="post"
                                          action="orders">
                                        <button class="btn btn-primary" name="orders-all" value="true"
                                                type="submit" <%--onclick="submitForms()"--%>>
                                            All
                                        </button>
                                    </form>
                                </td>

                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${empty orders}">
        <div id="catlist1" class="catlist">

            <dl>
                <dd>
                    <p>List is empty</p>
                </dd>
            </dl>
        </div>
    </c:if>

    <c:if test="${(not empty orders)}">
        <c:forEach items="${orders}" var="order">
            <div class="panel panel-default">
                <!-- Заголовок 3 панели -->
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${order.id}"/>">Order
                            #
                            <c:out value="${order.id}"/></a>
                    </h4>

                </div>


                <div id="collapse<c:out value="${order.id}"/>" class="panel-collapse collapse">
                    <form method="post" action="modifyorder" id="modify<c:out value="${order.id}"/>">


                        <button class="btn .btn-xs btn-primary" name="modify" value="<c:out value="${order.id}"/>"
                                type="submit" <%--onclick="submitForms()"--%>>
                            modify
                        </button>

                    </form>
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
                                                     height="62"/><strong>$<c:out value="${productInList.price}"/> *
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
                                    </li>
                                    <li class="list-group-item"><b>Delivery type : </b><c:out
                                            value="${order.deliveryType}"/></li>
                                    <li class="list-group-item"><b>Payment type : </b><c:out
                                            value="${order.paymentType}"/>
                                    </li>
                                    <li class="list-group-item"><b>Payment status: </b><c:out
                                            value="${order.paymentStatus}"/></li>
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
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
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
