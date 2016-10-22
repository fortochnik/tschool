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

        <%-- <div class="row">
           <div class="col-sm-8 col-sm-offset-2 text">
             <h1>Bootstrap Login &amp; Register Forms</h1>
             <div class="description">
               <p>
                 This is a free responsive <strong>"login and register forms"</strong> template made with Bootstrap.
                 Download it on <a href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>,
                 customize and use it as you like!
               </p>
             </div>
           </div>
         </div>--%>

        <div class="row">
            <div class="col-sm-5">

                <div class="form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Statistics</h3>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <p class="help-block"></p>
                        <ul class="list-group">
                            <li class="list-group-item"><b>Top products: </b></li>
                            <div class="container">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>#id</th>
                                        <th>Name</th>
                                        <th>Quantity</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="product" items="${top_products}" varStatus="status">
                                        <tr>
                                            <td><c:out value="${status.index}"/>.</td>
                                            <td><a href="/product/<c:out value="${product[0].id}"/>"><c:out
                                                value="${product[0].id}"/></a></td>
                                            <td><c:out value="${product[0].name}"/></td>
                                            <td><c:out value="${product[1]}"/></td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                            <li class="list-group-item"><b>Top clients: </b></li>
                            <div class="container">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>#id</th>
                                        <th>Name</th>
                                        <th>Sername</th>
                                        <th>email</th>
                                        <th>Quantity orders</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${top_user}" varStatus="status">
                                        <tr>
                                            <td><c:out value="${status.index}"/>.</td>
                                            <td><c:out value="${user[0].id}"/></td>
                                            <td><c:out value="${user[0].name}"/></td>
                                            <td><c:out value="${user[0].sername}"/></td>
                                            <td><c:out value="${user[0].email}"/></td>
                                            <td><c:out value="${user[1]}"/></td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>

                            <li class="list-group-item"><b>Weekly proceeds : </b>$ <c:out value="${proceeds_week}"/>
                            </li>
                            <li class="list-group-item"><b>Monthly proceeds : </b>$ <c:out value="${proceeds_month}"/>
                            </li>
                        </ul>
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

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>
</body>


</html>
