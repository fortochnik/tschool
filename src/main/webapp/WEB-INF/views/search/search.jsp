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


<div class="container">


    <%--<div class="col-sm-3">--%>
    <div class="form-bottom">
        <fieldset>
            <div>
                <p class="help-block"></p>
                <%--category--%>

                <div class="pull-left control-group">
                    <label class="control-label">Category</label>
                    <br/>

                    <div class="controls ">
                        <select class="selectpicker show-tick" name="form-category"
                                id="form-country" title="category...">
                            <option value="0">All</option>
                            <c:forEach items="${categories}" var="category">
                                <option
                                        <c:if test="${category.id eq categoryId}">
                                            <c:out value="selected"/>
                                        </c:if>
                                        value="<c:out value="${category.id}"/>"><c:out
                                        value="${category.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                </div>


                <!-- Product name -->

                <div class="pull-left control-group">
                    <label class="control-label">Product Name</label>
                    <br/>

                    <input id="first-name" name="name" type="text"
                           placeholder="Product name"
                           class="input-xlarge medium"
                           value="<c:out value="${name}"/>">

                    <p class="help-block"></p>
                </div>


                <!-- price -->
                <div class="pull-left control-group">

                    <label class="control-label">Price</label>
                    <br/>


                    <input id="price-min" name="price-min" type="text" placeholder="min"
                           class="input-xlarge min" maxlength="7"
                           value="<c:out value="${min}"/>"
                           onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>

                    <input id="price-max" name="price-max" type="text" placeholder="max"
                           class="input-xlarge min" maxlength="7"
                           value="<c:out value="${max}"/>"
                           onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>

                    <p class="help-block"></p>

                </div>

                <!-- price -->
                <div class="pull-left control-group">
                    <label class="control-label">Company</label>
                    <br/>

                    <div class="controls ">
                        <input id="company" name="company" type="text" placeholder="Company name..."
                               value="<c:out value="${company}"/>"
                               class="input-xlarge"/>

                        <p class="help-block"></p>
                    </div>
                </div>

                <!-- Parameters -->
                <div class="pull-left control-group<%--control-group form-group--%>">
                    <label class="control-label">Parameters</label>
                    <br/>

                    <div class="controls ">
                        <input id="parameters" name="parameters" type="text" placeholder="Parameters name..."
                               value="<c:out value="${parameters}"/>"
                               class="input-xlarge "/>

                        <p class="help-block"></p>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
</div>
<div>
    <button class="btn .btn-xs" type="submit">
        Search
    </button>
</div>
<%--</div>--%>


<%--
<image src="/image?image=image.jpg"/>
--%>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<!-- JavaScript -->
<%--<script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>--%>


<!-- Datapicker https://itchief.ru/lessons/bootstrap-3/113-bootstrap-3-datetimepicker -->
<%--todo extract scripts to files--%>

<script src="${pageContext.request.contextPath}/resources/js/moment/moment-with-locales.min.js"></script>


</body>

</html>

