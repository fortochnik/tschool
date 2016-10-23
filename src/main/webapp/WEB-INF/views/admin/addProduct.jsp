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
<div class="panel-group">

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
            <fieldset>
  <div class="panel panel-default">


    <div id="collapse" class="">
      <form method="post" action="add" id="add"/>">

        <!-- Содержимое 3 панели -->
        <div class="container">
          <c:set var="total" value="${0}"/>
          <div class="row">

            <!-- Product name -->
            <div class="control-group ">
              <label class="control-label">Product Name</label>

              <div class="controls inline">
                <input id="first-name" name="first-name" type="text" placeholder="first name"
                       class="input-xlarge" value="<c:out value="${userdata.name}"/>">

                <p class="help-block"></p>
              </div>
            </div>

            <%--category--%>

            <div class="form-group">
              <label class="sr-only" for="form-country">Category</label>
              <select class="selectpicker show-tick" name="form-category" id="form-country" title="Choose country...">
                <c:forEach items="${categories}" var="category">
                  <option name="<c:out value="${category.id}"/>"><c:out value="${category.name}"/></option>
                </c:forEach>
              </select>
            </div>

            <!-- parameters-->
            <div class="control-group ">
              <label class="control-label">Parameters</label>

              <div class="controls inline">
                <%--<input id="parameters" name="parameters" type="text" placeholder="parameters"
                       class="input-xlarge" value="<c:out value="${userdata.name}"/>">--%>
                <textarea id="parameters" name="parameters" placeholder="Parameters" class="input-xlarge"></textarea>

                <p class="help-block"></p>
              </div>
            </div>


            <!-- weight -->
            <div class="control-group ">
              <label class="control-label">Weight</label>

              <div class="controls inline">
                <input id="weight" name="weight" type="text" placeholder="weight"
                       class="input-xlarge" maxlength="3"
                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                        />
                <p class="help-block"></p>
              </div>
            </div>

            <!-- volume -->
            <div class="control-group ">
              <label class="control-label">Volume</label>

              <div class="controls inline">
                <input id="volume" name="volume" type="text" placeholder="volume"
                       class="input-xlarge" maxlength="3"
                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                        />
                <p class="help-block"></p>
              </div>
            </div>


            <!-- price -->
            <div class="control-group ">
              <label class="control-label">Price</label>

              <div class="controls inline">
                <input id="price" name="price" type="text" placeholder="price"
                       class="input-xlarge" maxlength="6"
                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                        />
                <p class="help-block"></p>
              </div>
            </div>


            <!-- count -->
            <div class="control-group ">
              <label class="control-label">Count</label>

              <div class="controls inline">
                <input id="count" name="count" type="text" placeholder="count"
                       class="input-xlarge" maxlength="4"
                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                        />
                <p class="help-block"></p>
              </div>
            </div>

            <!-- count -->
            <div class="control-group ">
              <label class="control-label">Company</label>

              <div class="controls inline">
                <input id="company" name="count" type="text" placeholder="company"
                       class="input-xlarge" maxlength="3"
                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                        />
                <p class="help-block"></p>
              </div>
            </div>

          </div>
        </div>

        add imgs uploud

        <button class="btn .btn-xs btn-primary" name="order-update-admin" value="add"
                type="submit" <%--onclick="submitForms()"--%>>
          Update
        </button>
      </form>
    </div>
  </div>
              </fieldset>
            </div>
          </div>
        </div>
      </div>
    </div>

</div>





<image src="/image?image=image.jpg"/>
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





