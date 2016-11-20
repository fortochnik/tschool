<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 13.10.2016
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Address form -->

<h3>User Profile</h3>

<!-- first-name input-->
<spring:bind path="name">
    <div class="control-group ${status.error ? 'has-error' : ''}">
    <label class="control-label">First Name</label>

    <div class="controls inline">
        <form:input path="name" id="first-name" name="first-name" type="text" placeholder="first name"
               class="input-xlarge" value="${userdata.name}"/>
        <form:errors path="name" cssClass="error"></form:errors>

        <p class="help-block"></p>
    </div>
</div>
</spring:bind>

<!-- last-name input-->
<spring:bind path="sername">
    <div class="control-group ${status.error ? 'has-error' : ''}">
        <label class="control-label">Last Name</label>

        <div class="controls  inline">
            <form:input path="sername" id="last-name" name="last-name" type="text" placeholder="last name"
                        class="input-xlarge" value="${userdata.sername}"></form:input>
            <form:errors path="sername" cssClass="error"></form:errors>

            <p class="help-block"></p>
        </div>
    </div>
</spring:bind>
<!-- email -->
<spring:bind path="email">
    <div class="control-group ${status.error ? 'has-error' : ''}">
        <label class="control-label">Email</label>

        <div class="controls  inline">
            <form:input path="email" id="email" name="email" type="text" placeholder="email"
                        class="input-xlarge" value="${userdata.email}"></form:input>
            <form:errors path="email" cssClass="error"></form:errors>
            <p class="help-block"></p>
        </div>
    </div>
</spring:bind>
<!-- Date of Birth-->
<spring:bind path="birthday">
    <div class="control-group ${status.error ? 'has-error' : ''}">
        <label class="control-label">Date of Birth</label>

        <div class="controls  inline">
            <form:input path="birthday" type="text" id="birthday" name="birthday"
            class="date-empty"
            value="${userdata.birthday}"></form:input>
            <form:errors path="birthday" cssClass="error"></form:errors>
            <p class="help-block"></p>

        </div>
    </div>
</spring:bind>
<p class="help-block"></p>