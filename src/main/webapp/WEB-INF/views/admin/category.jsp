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
    <%--<link href="${pageContext.request.contextPath}/resources/css/custom/bootstrap-datetimepicker.min.css"
          rel="stylesheet">--%>
</head>
<body>
<div class="container">
    <div class="page-header">
        <jsp:include page="/WEB-INF/views/header.jsp"/>
    </div>
</div>

<div class="inner-bg">

    <div class="container">

        <div class="container">
            <%----%>
            <form name="form-category-new" method="post" action="/category" id="tab2">
                <div class="form-bottom">
                    <p class="help-block"></p>


                    <ul class="list-group">
                        <li class="list-group-item"><b>Create category: </b></li>
                        <div class="container">

                            <table class="table table-nonfluid">

                                <tbody>

                                <%--<button id="add-new" class="btn btn-primary btn-xs">Add Field:</button>--%>
                                <i id="add-new" class="fa fa-plus-circle " aria-hidden="true"></i>
                                <p class="help-block"></p>
                                <p class="help-block"></p>
                                <div id="items">
                                    <div><input type="text" name="category-new"></div>
                                </div>

                                </tbody>
                            </table>


                        </div>
                    </ul>
                </div>
                <div>
                    <button class="btn btn-primary" type="submit" <%--onclick="submitForms()"--%>>
                        Add category
                    </button>
                </div>
            </form>
        </div>
        <p class="help-block"></p>
        <form name="form-category-exist" method="post" action="/category" id="tab" class="userinfo-form">
            <div class="row">
                <div class="col-sm-5">

                    <div class="form-box">
                        <div class="form-top">
                            <div class="form-top-left">
                                <h3>Categories</h3>
                            </div>
                            <div class="form-top-right">
                                <i class="fa fa-key"></i>
                            </div>
                        </div>
                        <div class="form-bottom">
                            <p class="help-block"></p>


                            <ul class="list-group">
                                <li class="list-group-item"><b>Existing category: </b></li>
                                <div class="container">

                                    <table class="table table-nonfluid">
                                        <thead>
                                        <tr>
                                            <th class="col-md-1">delete</th>
                                            <th class="col-md-1">category</th>
                                            <th class="col-md-3"></th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${categoryexist}" var="category" varStatus="status">
                                            <tr>
                                                <td>

                                                    <input type="checkbox" name="category-del-chbx"
                                                           value="<c:out value="${category.id}"/>">
                                                </td>

                                                <td class="text-left">
                                                    <div>
                                                        <div class="controls">
                                                            <input class="category" type="text"
                                                                   name="category-name-update-<c:out value="${category.id}"/>"
                                                                   value="<c:out value="${category.name}"/>">
                                                        </div>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>


                                </div>
                            </ul>


                        </div>


                    </div>
                </div>
            </div>
            <input type="hidden" name="update-form" value="true">
        </form>
        <div>
            <button class="btn btn-primary update">Save all and
                Remove checked
            </button>

        </div>
    </div>

</div>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/custom/profile.js"></script>

<script>
    $(document).ready(function () {

        $("#add-new").click(function (e) {
            //Append a new row of code to the "#items" div
            $("#items").append('<div><input name="category-new" type="text" /><button class="delete btn btn-primary btn-xs">Delete</button></div>');
        })


        $("body").on("click", ".delete", function (e) {
            $(this).parent("div").remove();
        });
    })


</script>
</body>


</html>
