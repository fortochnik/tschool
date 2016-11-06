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


<div id="myTabContent" class="">
    <div class="" id="home">
        <div class="container">
            <div class="row">
                <div class="col-sm-5">
                    <div class="form-bottom">
                        <div>
                            <p class="help-block"></p>


                            <%--<form role="form" class="form-horizontal left userinfo-form" method="post" action="/add">
                                <div class="form-group">
                                    <label class="sr-only" for="form-last-name">Last name</label>
                                    <input type="text" name="test" placeholder="Last name..." class="form-last-name form-control" id="form-last-name">
                                </div>
                                <div>
                                    <button type="submit" name="form-registration-submit" class="btn">up!</button>
                                </div>
                            </form>--%>
                            <div>
                                <button class="btn .btn-xs btn-primary update" name="order-update-admin" value="add">
                                    Create
                                </button>
                            </div>
                            <form role="form" class="form-horizontal left userinfo-form" method="post" action="/add"
                                  id="tab"
                                  enctype="multipart/form-data"
                                    >

                                <p class="help-block"></p>

                                <!-- Product name -->
                                <div class="control-group form-group">
                                    <label class="control-label">Product Name</label>

                                    <div class="controls inline">
                                        <input id="first-name" name="name" type="text"
                                               placeholder="first name"
                                               class="input-xlarge"
                                               value="<c:out value="${userdata.name}"/>">

                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <%--category--%>
                                <label class="control-label">Category</label>

                                <div class="form-group ">
                                    <label class="sr-only" for="form-country">Category</label>

                                    <div class="controls inline">
                                        <select class="selectpicker show-tick" name="form-category"
                                                id="form-country" title="Choose country...">
                                            <c:forEach items="${categories}" var="category">
                                                <option value="<c:out value="${category.id}"/>"><c:out
                                                        value="${category.name}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <!-- parameters-->
                                <div class="control-group form-group">
                                    <label class="control-label">Parameters</label>

                                    <div class="controls inline">
                                                            <textarea id="parameters" name="parameters"
                                                                      placeholder="Parameters"
                                                                      class="input-xlarge"></textarea>

                                        <p class="help-block"></p>
                                    </div>
                                </div>


                                <!-- weight -->
                                <div class="control-group form-group">
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
                                <div class="control-group form-group">
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
                                <div class="control-group form-group">
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
                                <div class="control-group form-group">
                                    <label class="control-label">Count</label>

                                    <div class="controls inline">
                                        <input id="count" name="count" type="text" placeholder="count"
                                               class="input-xlarge" maxlength="4"
                                               onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
                                                />

                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <!-- Company -->
                                <div class="control-group form-group">
                                    <label class="control-label">Company</label>

                                    <div class="controls inline">
                                        <input id="company" name="company" type="text" placeholder="company"
                                               class="input-xlarge"/>

                                        <p class="help-block"></p>
                                    </div>
                                </div>

                                <p></p>

                                <input type="file" name="img1" value="main image"/>

                                <p></p>
                                <input type="file" name="img2" value="second image"/>

                                <p></p>
                                <input type="file" name="img3" value="third image"/>


                                <p></p>

                                <c:if test="${not empty requestScope.get('parsErrorMessage')}">
                                    <p class="help-block"></p>

                                    <div class="form-group top15">
                                        <div class="alert alert-danger fade in ">
                                            <a href="#" class="close" data-dismiss="alert">&times;</a>
                                            <strong>Error!</strong> <c:out
                                                value="${requestScope.get('parsErrorMessage')}"/>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty requestScope.get('parsSuccessMessage')}">
                                    <p class="help-block"></p>

                                    <div class="form-group top15">
                                        <div class="alert alert-success fade in ">
                                            <a href="#" class="close" data-dismiss="alert">&times;</a>
                                            <strong></strong> <c:out
                                                value="${requestScope.get('parsSuccessMessage')}"/>
                                        </div>
                                    </div>
                                </c:if>
                            </form>

                        </div>


                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%--
<image src="/image?image=image.jpg"/>
--%>
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





