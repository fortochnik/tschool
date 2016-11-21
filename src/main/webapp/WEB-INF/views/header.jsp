<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
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
            <a class="navbar-brand" href="">Eshop</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li>
                    <a href="">Catalog</a>
                </li>
                <sec:authorize access="hasRole('ROLE_CLIENT')">
                    <li>
                        <a href="profile">Profile</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_CLIENT', 'ROLE_ANONYMOUS')">
                    <li>
                        <a href="basket">Basket
                            <c:if test="${BASKET ne '0'}">
                                (<c:out value="${BASKET}"/>)
                            </c:if>
                        </a>

                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')">
                    <li>
                        <a href="statistic"> Statistic</a>
                    </li>
                    <li>
                        <a href="category">Categories</a>
                    </li>
                    <li>
                        <a href="orders">Orders</a>
                    </li>
                    <li>
                        <a href="add">Add new product</a>
                    </li>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAnonymous()">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="login">Login/Sign in</a></li>
                </ul>
            </sec:authorize>

            <sec:authorize access="isAuthenticated()">
                <form id="logoutForm" method="POST" action="logout">
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a onclick="document.forms['logoutForm'].submit()">Logout</a></li>
                </ul>
            </sec:authorize>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

</body>
</html>
