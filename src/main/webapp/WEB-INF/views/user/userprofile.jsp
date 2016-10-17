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

<div class="well">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#home" data-toggle="tab">Profile</a></li>
        <%--<li><a href="#profile" data-toggle="tab">Password</a></li>--%>
        <li><a href="#orders" data-toggle="tab">Order's history</a></li>
    </ul>

    <div id="myTabContent" class="tab-content">
        <div class="tab-pane active in" id="home">
            <div class="container">
                <div class="row">
                    <form class="form-horizontal left userinfo-form" id="tab" method="post" action="profile">
                        <fieldset>
                            <jsp:include page="/WEB-INF/views/user/userprofile/info.jsp"/>
                            <%-- <div>
                                 <button class="btn btn-primary">Update</button>
                             </div>--%>

                        </fieldset>
                        <p class="help-block"></p>
                        <fieldset class="password-block">
                            <jsp:include page="/WEB-INF/views/user/userprofile/changepassword.jsp"/>
                        </fieldset>
                    </form>

                </div>
            </div>
        </div>

        <%-- <div class="tab-pane fade" id="profile">
             <div class="container">
                 <div class="row">
                     <form id="tab2" class="left password-form" method="post" action="profile">
                         <jsp:include page="/WEB-INF/views/user/userprofile/changepassword.jsp"/>
                     </form>
                 </div>
             </div>
         </div>--%>

        <div class="tab-pane fade" id="orders">
            <div class="container">
                <div class="row">
                    <p class="help-block"></p>

                    <form id="tab3" method="post" action="profile">
                        <jsp:include page="/WEB-INF/views/user/userprofile/ordershistory.jsp"/>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${not empty requestScope.get('errorInfoMessage')}">
            <p class="help-block"></p>

            <div class="form-group top15">
                <div class="alert alert-danger fade in ">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>Error!</strong> <c:out value="${requestScope.get('errorInfoMessage')}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.get('successInfoMessage')}">
            <p class="help-block"></p>

            <div class="form-group top15">
                <div class="alert alert-success fade in">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>Success!</strong> <c:out
                        value="${requestScope.get('successInfoMessage')}"/>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.get('passwordErrorMessage')}">
            <p class="help-block"></p>

            <div class="form-group top15">
                <div class="alert alert-danger fade in ">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>Error!</strong> <c:out
                        value="${requestScope.get('passwordErrorMessage')}"/>
                </div>
            </div>
        </c:if>
        <div>
            <button class="btn btn-primary update" <%--onclick="submitForms()"--%>>Update</button>
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

</body>

</html>
