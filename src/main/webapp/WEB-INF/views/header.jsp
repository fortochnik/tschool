<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 08.10.2016
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Eshop</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="/">Catalog</a>
                </li>
                <c:if test="${LOGIN eq 'true'}">
                    <li>
                        <a href="/profile">Profile</a>
                    </li>
                </c:if>
                <li>
                    <a href="/basket">Basket
                        <c:if test="${BASKET ne '0'}">
                            (<c:out value="${BASKET}"/>)
                        </c:if>
                    </a>

                </li>
                <c:if test="${(LOGIN eq 'true') and ((ROLE eq 'EMPLOYEE') or (ROLE eq 'ADMIN'))}">
                    <li>
                        <a href="/statistic">Statistic</a>
                    </li>
                    <li>
                        <a href="/category">Categories</a>
                    </li>
                    <li>
                        <a href="/orders">Orders</a>
                    </li>
                </c:if>

            </ul>
            <c:if test="${LOGIN eq 'false'}">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/login">Login/Sign in</a></li>
                </ul>
            </c:if>

            <c:if test="${LOGIN eq 'true'}">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/logout">Logout</a></li>
                </ul>
            </c:if>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

</body>
</html>
