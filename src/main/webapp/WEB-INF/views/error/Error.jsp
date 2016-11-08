<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 18.10.2016
  Time: 22:39
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

<div id="catlist1" class="catlist">

    <dl>
        <dd>
            <p> <div class="form-group top15">
            <div class="alert alert-danger fade in ">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                Sorry! Something happened! We already work with this problem.
            </div>
        </div></p>
        </dd>
    </dl>


</div>
</body>
</html>


