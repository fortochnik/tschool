<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 26.09.2016
  Time: 23:49
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
<c:if test="${empty basket}">
    <div id="catlist1" class="catlist">

        <dl>
            <dd>
                <p>The basket is empty</p>
            </dd>
        </dl>


    </div>
</c:if>
<c:if test="${not empty basket}">

<div class="panel panel-default">
    <!-- Заголовок 3 панели -->
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${basket.id}"/>">Order
                #
                <c:out value="${basket.id}"/></a>
                <%--<a href="/basket" class="con-order pull-right btn btn-warning btn-sm active" aria-pressed="true">Continue to basket</a>--%>
        </h4>
    </div>
    <div id="collapse<c:out value="${basket.id}"/>" class="panel-collapse collapse in">
        <!-- Содержимое 3 панели -->
        <div class="container">
            <c:set var="total" value="${0}"/>
            <div class="row">
                <div class="panel-body">
                    <div id="order-<c:out value="${basket.id}"/>" class="catlist">
                        <c:forEach items="${basket.productList}" var="productInList">
                            <dl>
                                <c:set var="totalProduct"
                                       value="${productInList.product.price * productInList.count}"/>
                                <c:set var="total" value="${total + totalProduct}"/>
                                <dt id="product-<c:out value="${productInList.product.id}"/>">
                                    <img src="http://placehold.it/300x200"
                                         alt="Product image" width="93"
                                         height="62"/>
                                    <strong>
                                        <span class="product-price">
                                            $<c:out value="${productInList.product.price}"/>
                                        </span>
                                        *
                                        <select id="selectnumber-XXX" class="selectnumber" name="number-select-XX">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                        ($<span class="totul-product-price"><c:out value="${totalProduct}"/></span>)
                                        <span class="glyphicon glyphicon-refresh icn-space refresh-product"/>
                                        <span class="glyphicon glyphicon-remove icn-space remove-product"/>
                                    </strong>
                                    <a href="/product/<c:out value="${productInList.product.id}"/>">
                                        <c:out value="${productInList.product.name}"/>
                                    </a>
                                </dt>
                                <dd>
                                    <p><c:out value="${productInList.product.parameters}"/></p>
                                </dd>
                            </dl>
                        </c:forEach>
                    </div>
                    <span class="pull-right">Total price: $<c:out value="${total}"/></span>

                    <div class="button-block">
                        <button id="product-<c:out value="${product.id}"/>" type="button"
                                class=" button btn btn-warning btn-sm  pull-right" style="color: black">
                            Buy
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </c:if>

    <button id="product-<c:out value="${product.id}"/>" type="button"
            class=" button btn btn-warning btn-sm " onclick="updateBasket()"
            style="color: black">
        Buy!
    </button>
    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

    <!-- JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>


    <!-- Datapicker https://itchief.ru/lessons/bootstrap-3/113-bootstrap-3-datetimepicker -->
    <%--todo extract scripts to files--%>

    <script src="${pageContext.request.contextPath}/resources/js/basket/basket.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/moment/moment-with-locales.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/custom/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#datetimepicker1').datetimepicker({
                language: 'ru',
                minuteStepping: 10,
                defaultDate: "09.01.2015",
                daysOfWeekDisabled: [0, 6]
            });
            $('#birthday').datetimepicker({language: 'ru', pickTime: false, format: 'YYYY-MM-DD'});
        });
    </script>
    <script type="text/javascript">
        $(function () {


        })

        updateBasket = function () {
            /*var order = [
                {pv: 1000, bv: 2000, mp: 3000, cp: 5000},
                {pv: 2500, bv: 3500, mp: 2000, cp: 4444}
            ];*/
            var order ={basket:[]};
            $('dt').each(function(index,item){
                var count = $(item).find(".selectnumber").val();
                var product = $(item).attr('id').replace(/[A-Za-z$-]/g, "");
                order.basket.push(
                        {product: product, count: count}
                );
            });
            $.ajax({
                type: 'POST', // it's easier to read GET request parameters
                url: '/updatebasket',
                dataType: 'JSON',
                data: {
                    loadProds: 1,
                    order: JSON.stringify(order) // look here!
                },
                success: function(data) {

                },
                error: function(data) {
                    alert('fail');
                }
            });


        }

    </script>
</body>
</html>
