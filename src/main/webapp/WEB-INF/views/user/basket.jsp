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
    <link href="${pageContext.request.contextPath}/resources/css/custom/spaces.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <div class="page-header">
        <jsp:include page="/WEB-INF/views/header.jsp"/>
    </div>
</div>
<c:if test="${empty basket.productList}">
    <div id="catlist1" class="catlist">

        <dl>
            <dd>
                <p>The basket is empty</p>
            </dd>
        </dl>

        <c:if test="${not empty deficiency}">
            <c:forEach items="${deficiency}" var="product">
                <p class="help-block"></p>

                <div class="form-group top15">
                    <div class="alert alert-warning fade in ">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <c:out value="${product}"/>
                    </div>
                </div>

            </c:forEach>

        </c:if>


    </div>
</c:if>
<c:if test="${not empty basket.productList}">

<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${basket.id}"/>">Order
                #
                <c:out value="${basket.id}"/></a>
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
                                    <img src="/image?image=${productInList.product.id}-image1.jpg"
                                         alt="Product image" width="93"
                                         height="62"/>
                                    <strong>
                                        $<span class="product-price"><c:out
                                            value="${productInList.product.price}"/></span>
                                        *
                                        <input type="text" class="count" value="<c:out value="${productInList.count}"/>"
                                               maxlength="3"
                                               onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>

                                        ($<span class="total-product-price"><c:out value="${totalProduct}"/></span>)
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
                    <span class="pull-right total-price">Total price: $<c:out value="${total}"/></span>

                    <c:if test="${not empty deficiency}">
                        <c:forEach items="${deficiency}" var="product">
                            <p class="help-block"></p>

                            <div class="form-group top15">
                                <div class="alert alert-warning fade in ">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <c:out value="${product}"/>
                                </div>
                            </div>

                        </c:forEach>
                    </c:if>
                </div>
                <div class="button-block">
                    <button id="product-save" type="button"
                            class="pull-right button btn btn-warning btn-sm click-update"
                            style="color: black">
                        Save
                    </button>
                    <c:if test="${LOGIN eq 'true'}">

                        <button id="product-buy" type="submit"
                                class="button btn btn-warning btn-sm  button-block click-buy" style="color: black">
                            $Buy
                        </button>
                        <form id="form-submit" method="post" action="pay">
                        </form>
                    </c:if>
                    <c:if test="${LOGIN ne 'true'}">
                        <button id="product-login" type="submit"
                                class="button btn btn-warning btn-sm  button-block click-login" style="color: black">
                            $Buy
                        </button>
                        <form id="form-login" method="get" action="login">
                        </form>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
    </c:if>


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
        /* $(function () {


         })*/

        //        updateBasket = function () {

        $('.click-buy').on('click', function (e) {
            $('.click-update').click();
            document.getElementById("form-submit").submit();

        });

        $('.click-login').on('click', function (e) {
            $('.click-update').click();
            document.getElementById("form-login").submit();

        });


        $('.click-update').on('click', function (e) {
            var total = 0;
            $('dt').each(function (index, item) {
                var number = $(item).find(".count").val();
                var price = $(item).find(".product-price").text();
                if (number == "0" || number == "") {
                    $(item).find(".count").val("0");
                    $(item).find('.remove-product').click();
                    number = 0;

                }
                else {
                    $(item).find(".total-product-price").replaceWith("<span class='total-product-price'>" + price * number + "</span>");
                }
                total = total + price * number;
            });
            $(".total-price").replaceWith("<span class=\"pull-right total-price\">Total price: $" + total + "</span>");

            var order = {basket: []};
            $('dt').each(function (index, item) {
                var count = $(item).find(".count").val();

                var product = $(item).attr('id').replace(/[A-Za-z$-]/g, "");
                order.basket.push(
                        {product: product, count: count}
                );
            });

            $.ajax({
                type: 'POST', // it's easier to read GET request parameters
                url: '/updatebasket',
                dataType: 'JSON',
                async: false,
                data: {
                    loadProds: 1,
                    order: JSON.stringify(order) // look here!
                },
                success: function (data) {
                }
            });
        });

    </script>
</body>
</html>
