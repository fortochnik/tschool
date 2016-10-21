<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 13.10.2016
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-group" id="accordion">


    <c:set var="stepafterdraft" value="${0}"/>
    <c:if test="${(orders[0].orderStatus == 'DRAFT') && (empty orders[0].productList )}">
        <c:set var="stepafterdraft" value="${1}"/>
    </c:if>
    <c:if test="${(orders[0].orderStatus == 'DRAFT') && (not empty orders[0].productList )}">

        <c:set var="stepafterdraft" value="${stepafterdraft + 1}"/>

        <c:forEach items="${orders}" var="order" end="0">
            <div class="panel panel-default">
                <!-- Заголовок 3 панели -->
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#accordion"
                           href="#collapse<c:out value="${order.id}"/>">Order
                            #
                            <c:out value="${order.id}"/></a>
                        <a href="/basket" class="con-order pull-right btn btn-warning btn-sm active"
                           aria-pressed="true">Continue
                            to order</a>
                    </h4>
                </div>
                <div id="collapse<c:out value="${order.id}"/>" class="panel-collapse collapse in">
                    <!-- Содержимое 3 панели -->
                    <div class="container">
                        <c:set var="total" value="${0}"/>
                        <div class="row">
                            <div class="panel-body">
                                <div id="order-<c:out value="${order.id}"/>" class="catlist">
                                    <c:forEach items="${order.productList}" var="productInList">
                                        <dl>
                                            <c:set var="totalProduct"
                                                   value="${productInList.product.price * productInList.count}"/>
                                            <c:set var="total" value="${total + totalProduct}"/>
                                            <dt><img src="http://placehold.it/300x200"
                                                     alt="Product image" width="93"
                                                     height="62"/><strong>$<c:out
                                                    value="${productInList.product.price}"/> *
                                                <c:out
                                                        value="${productInList.count}"/> ($<c:out
                                                        value="${totalProduct}"/>)</strong><a
                                                    href="/product/<c:out value="${productInList.product.id}"/>"><c:out
                                                    value="${productInList.product.name}"/></a></dt>
                                            <dd>
                                                <p><c:out value="${productInList.product.parameters}"/></p>
                                            </dd>
                                        </dl>
                                    </c:forEach>
                                </div>
                                <span class="pull-right">Total price: $<c:out value="${total}"/></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </c:if>

    <!-- 3 панель -->
    <c:forEach items="${orders}" var="order" begin="${stepafterdraft}">
        <div class="panel panel-default">
            <!-- Заголовок 3 панели -->
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse<c:out value="${order.id}"/>">Order
                        #
                        <c:out value="${order.id}"/></a>
                </h4>
            </div>
            <div id="collapse<c:out value="${order.id}"/>" class="panel-collapse collapse">
                <!-- Содержимое 3 панели -->
                <div class="container">
                    <c:set var="total" value="${0}"/>
                    <div class="row">
                        <div class="panel-body">
                            <div id="order-<c:out value="${order.id}"/>" class="catlist">
                                <c:forEach items="${order.productList}" var="productInList">
                                    <dl>
                                        <c:set var="totalProduct" value="${productInList.price * productInList.count}"/>
                                        <c:set var="total" value="${total + totalProduct}"/>
                                        <dt><img src="http://placehold.it/300x200"
                                                 alt="Product image" width="93"
                                                 height="62"/><strong>$<c:out value="${productInList.price}"/> * <c:out
                                                value="${productInList.count}"/> ($<c:out
                                                value="${totalProduct}"/>)</strong><a
                                                href="/product/<c:out value="${productInList.product.id}"/>"><c:out
                                                value="${productInList.product.name}"/></a></dt>
                                        <dd>
                                            <p><c:out value="${productInList.product.parameters}"/></p>
                                        </dd>
                                    </dl>
                                </c:forEach>
                            </div>
                            <p class="help-block"></p>
                            <ul class="list-group">
                                <li class="list-group-item"><b>State : </b><c:out value="${order.state}"/></li>
                                <li class="list-group-item"><b>State : </b><c:out value="${order.state}"/></li>
                                <li class="list-group-item"><b>Order date : </b><c:out value="${order.orderDate}"/></li>
                                    <%--<li class="list-group-item"><b>Delivery date : </b><c:out value="${order.deliveryDate}"/></li>--%>
                                <li class="list-group-item"><b>Order status : </b><c:out value="${order.orderStatus}"/>
                                </li>
                                <li class="list-group-item"><b>Delivery type : </b><c:out
                                        value="${order.deliveryType}"/></li>
                                <li class="list-group-item"><b>Payment type : </b><c:out value="${order.paymentType}"/>
                                </li>
                                <li class="list-group-item"><b>Payment status: </b><c:out
                                        value="${order.paymentStatus}"/></li>
                                <li class="list-group-item"><b>Address: </b><c:out value="${order.address.zipcode}"/>;
                                    <c:out
                                            value="${order.address.country.country}"/>, <c:out
                                            value="${order.address.city}"/>,
                                    street <c:out value="${order.address.street}"/>, <c:out
                                            value="${order.address.building}"/> app.<c:out
                                            value="${order.address.apartment}"/></li>

                            </ul>
                            <span class="pull-right">Total price: $<c:out value="${total}"/></span>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <%--<div class="panel panel-default">
      <!-- Заголовок 3 панели -->
      <div class="panel-heading">
        <h4 class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">3.
            Order</a>
        </h4>
      </div>
      <div id="collapseThree" class="panel-collapse collapse">
        <!-- Содержимое 3 панели -->
        <div class="panel-body">
          <p>Создание сайта - это трудоемкий процесс, который состоит из нескольких
            этапов. В качестве примера рассмотрим создание сайта визитки на этом <a
                    href="http://itchief.ru/lessons/bootstrap-3/website-creation-business-cards-(part-1)"
                    target="_blank">уроке</a>, в котором рассмотрим процесс загрузки
            необходимых пакетов и проектирования макета сайта.</p>
        </div>
      </div>
    </div>--%>
</div>
