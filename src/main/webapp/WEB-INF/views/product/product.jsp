<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 25.09.2016
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Shop Item - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/shop-item.css" rel="stylesheet">

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
            <p class="lead">Shop Name</p>

            <div class="list-group">
                <a href="#" class="list-group-item active"># <c:out value="${product.category.name}"/></a>

            </div>
        </div>

        <div class="col-md-9">

            <div class="thumbnail">
                <div class="row carousel-holder">

                    <div class="col-md-12">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner ">
                                <div class="item active img-height-max img-height-min">
                                    <a href="image?image=${product.id}-image1.jpg"> <img src="image?image=${product.id}-image1.jpg" alt="Product image" width="800" height="300"/></a>
                                </div>
                                <div class="item img-height-max">
                                    <a href="image?image=${product.id}-image2.jpg"> <img src="image?image=${product.id}-image2.jpg" alt="Product image" width="800" height="300"/></a>
                                </div>
                                <div class="item img-height-max">
                                    <a href="image?image=${product.id}-image3.jpg"><img src="image?image=${product.id}-image3.jpg" alt="Product image" width="800" height="300"/></a>
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
                <%--<img class="img-responsive" src="http://placehold.it/800x300" alt="">--%>

                <div class="caption-full">
                    <h4 class="pull-right">$<c:out value="${product.price}"/></h4>
                    <h4><a href="#"><c:out value="${product.name}"/></a>
                    </h4>

                    <p><c:out value="${product.parameters}"/></p>
                </div>

            </div>

            <div class="well">
                <ul class="list-group">
                    <li class="list-group-item"><b>Weight : </b><c:out value="${product.weight}"/></li>
                    <li class="list-group-item"><b>Volume : </b><c:out value="${product.volume}"/></li>
                    <li class="list-group-item"><b>Company : </b><c:out value="${product.company}"/></li>

                </ul>
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
        var aBasket = $("a.basket-link");
        var basket = $("span.basket");
        if(basket.length==0){
            aBasket.append("<span class='basket'>("+number+")</span>");
        }
        else{
            basket.text("("+(parseInt(basket.text().substr(1, basket.text().length-1), 10 ) + parseInt(number, 10 ))+")");
        }
        $.ajax({
            method: "POST",
            url: "addtobasket",
            data: {
                product: id,
                number: number
            },

        })
    }

</script>

</body>

</html>
