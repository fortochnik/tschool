<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 18.10.2016
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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

<div class="inner-bg">
    <div class="container">
        <div class="row">
            <div class="col-sm-5">

                <div class="form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Order and delivery data</h3>

                            <p>Fill to complete:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">

                        <form id="form-buy" role="form" action="payfinish" method="post" class="pay-form userinfo-form">
                            <div class="form-group">
                                <label class="sr-only" for="form-country">Address</label>
                                <select class="selectpicker show-tick" name="form-country" id="form-country"
                                        title="Choose country...">
                                    <c:forEach items="${countries}" var="country">
                                        <option name="<c:out value="${country.id}"/>"><c:out
                                                value="${country.country}"/></option>
                                    </c:forEach>
                                </select>


                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-city">City</label>
                                <input type="text" name="form-city" placeholder="City..."
                                       class="form-city form-control " id="form-city"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-street">street</label>
                                <input type="text" name="form-street" placeholder="street..."
                                       class="form-street form-control " id="form-street"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-build">Building</label>
                                <input type="text" name="form-build" placeholder="build..."
                                       class="form-build form-control " id="form-build"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-apartment">Apartment</label>
                                <input type="text" name="form-apartment" placeholder="apartment..."
                                       class="form-apartment form-control " id="form-apartment"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-zipcode">Apartment</label>
                                <input type="text" name="form-zipcode" placeholder="zipcode..."
                                       class="form-zipcode form-control " id="form-zipcode"/>
                            </div>


                            <div class="row">
                                <div class="form-inline">
                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="control-label">payment type:</label>
                                        <label class="btn btn-default active">
                                            <input type="radio" value="CASH" name="payment" checked="">Cash
                                        </label>
                                        <label class="btn btn-default ">
                                            <input type="radio" value="CREDIT_CARD" name="payment">Credit card
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <p class="help-block"></p>

                            <div class="row">
                                <div class="form-inline">
                                    <div class="btn-group" data-toggle="buttons">
                                        <label class="control-label">delivery tipe</label>
                                        <label class="btn btn-default active">
                                            <input type="radio" value="COURIER" name="delivery" checked="">Courier
                                        </label>
                                        <label class="btn btn-default ">
                                            <input type="radio" value="POST" name="delivery">Post
                                        </label>
                                        <label class="btn btn-default ">
                                            <input type="radio" value="PICKUP" name="delivery">Pick up
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </form>
                            <p class="help-block"></p>

                            <button name="form-submit" value="finish" class="btn buy">Finish!</button>

                            <p class="help-block"></p>
                            <c:if test="${not empty requestScope.get('errorSignInMessage')}">
                                <div class="form-group top15">
                                    <div class="alert alert-danger fade in ">
                                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                                        <strong>Error!</strong> <c:out
                                            value="${requestScope.get('errorSignInMessage')}"/>
                                    </div>
                                </div>
                            </c:if>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<%--<script src="resources/js/login/scripts.js"></script>--%>
<!-- Custom -->
<script src="${pageContext.request.contextPath}/resources/js/custom/payment.js"></script>


</body>
</html>
