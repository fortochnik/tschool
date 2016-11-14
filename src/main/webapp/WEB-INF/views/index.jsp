<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Shop Homepage - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/shop-homepage.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom/spaces.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>


<div class="container">
    <div class="page-header">
        <jsp:include page="/WEB-INF/views/header.jsp"/>
    </div>
</div>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <div class="col-md-3">
            <p class="lead">EShop</p>

            <div class="list-group">
                    <form class="form left" method="post" action="/search" id="tab">
                        <jsp:include page="/WEB-INF/views/search/search.jsp"/>

                    </form>
            </div>
        </div>
        <div class="col-md-9">

            <div class="row carousel-holder .hidden-xs .hidden-sm">

                <div class="col-md-12">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="item active">
                                <img class="slide-image" src="/image?image=main1.jpg" alt="">
                            </div>
                            <div class="item">
                                <img class="slide-image" src="/image?image=main2.jpg" alt="">
                            </div>
                            <div class="item">
                                <img class="slide-image" src="/image?image=main3.jpg" alt="">
                            </div>
                        </div>
                        <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                    </div>
                </div>

            </div>

            <div class="row">
                <c:forEach items="${products}" var="product">
                    <div class="col-sm-4 col-lg-4 col-md-4">
                        <div class="thumbnail">
                            <div class="img-height">
                                <img class="catalog-image " src="/image?image=${product.id}-image1.jpg" alt=""  width="320"
                                     height="150">
                            </div>
                            <div class="caption">
                                <h4 class="pull-right">$<c:out value="${product.price}"/></h4>
                                <h4><a href="/product/<c:out value="${product.id}"/>"><c:out
                                        value="${product.name}"/></a>
                                </h4>

                                <p><c:out value="${product.parameters}"/></p>
                            </div>
                            <c:if test="${product.count eq 0}">
                                not available
                            </c:if>
                            <c:if test="${product.count ne 0}">
                                <div class="button-block">
                                    <button id="product-<c:out value="${product.id}"/>" type="button"
                                            class=" button btn btn-warning btn-sm " onclick="addToBasket(this.id)"
                                            style="color: black">
                                        Add to basket
                                    </button>
                                    <select class="selectpicker" id="number-product-"${product.id}" >
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    </select>

                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>

    </div>

</div>
<!-- /.container -->

<div class="container">

    <hr>

    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p>&copy; eShop 2016</p>
            </div>
        </div>
    </footer>

</div>
<!-- /.container -->

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<script type="text/javascript">
    $(function () {


    })

    addToBasket = function (id) {
        var number = $("#" + id).siblings(".selectpicker").val();
        var productId = id;
        $.ajax({
            method: "POST",
            url: "/addtobasket",
            data: {
                product: id,
                number: number
            }
        })
    }

</script>

</body>

</html>
