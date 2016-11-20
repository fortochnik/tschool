<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 13.10.2016
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h3>Change password</h3>

    <div class="control-group">
        <label class="control-label">Old Password</label>

        <div class="controls inline">
            <input id="old-pass" type="password" class="input-xlarge" name="old-password">

            <p class="help-block"></p>
        </div>
    </div>


<spring:bind path="password">
    <div class="control-group ${status.error ? 'has-error' : ''}">
        <label class="control-label">New Password</label>

        <div class="controls inline">
            <form:input path="password" id="new-pass" type="password" class="input-xlarge" name="new-password"/>
            <form:errors path="password" cssClass="error"></form:errors>

            <p class="help-block"></p>
        </div>
    </div>
</spring:bind>
<spring:bind path="passwordConfirm">
    <div class="control-group div-new-password${status.error ? 'has-error' : ''}">

        <label class="control-label">Repeat New Password</label>

        <div class="controls inline">
            <form:input path="passwordConfirm" id="new-pass2" type="password" class="input-xlarge" name="new-password-repeat"/>
            <form:errors path="passwordConfirm" cssClass="error"></form:errors>

            <p class="help-block"></p>
        </div>
    </div>
</spring:bind>
