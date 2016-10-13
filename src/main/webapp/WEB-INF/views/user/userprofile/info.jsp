<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 13.10.2016
  Time: 22:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Address form -->

<h2>User Profile</h2>

<!-- first-name input-->
<div class="control-group ">
    <label class="control-label">First Name</label>

    <div class="controls inline">
        <input id="first-name" name="first-name" type="text" placeholder="first name"
               class="input-xlarge" value="<c:out value="${userdata.name}"/>">

        <p class="help-block"></p>
    </div>
</div>

<!-- last-name input-->
<div class="control-group">
    <label class="control-label">Last Name</label>

    <div class="controls  inline">
        <input id="last-name" name="last-name" type="text" placeholder="last name"
               class="input-xlarge" value="<c:out value="${userdata.sername}"/>">

        <p class="help-block"></p>
    </div>
</div>

<!-- email -->
<div class="control-group">
    <label class="control-label">Email</label>

    <div class="controls  inline">
        <input id="email" name="last-name" type="text" placeholder="email"
               class="input-xlarge" value="<c:out value="${userdata.email}"/>">

        <p class="help-block"></p>
    </div>
</div>

<!-- Date of Birth-->
<div class="control-group">
    <label class="control-label">Date of Birth</label>

    <div class='col-sm-6 controls inline'>
        <div class="form-group">
            <div class='input-group date' id='datetimepicker'>
                <input type='text' class="form-control" value="<c:out value="${userdata.birthday}"/>"/>
                                          <span class="input-group-addon">
                                              <span class="glyphicon glyphicon-calendar"></span>
                                          </span>
            </div>
        </div>
    </div>
    <!-- <div class="controls inline">
      <div data-date-format="dd-mm-yyyy" data-date="12-02-2012" id="dp3" class="input-append date">
        <input type="text" readonly="" value="12-02-2012" size="16" class="span2">
        <span class="add-on"><i class="icon-calendar"></i></span>
      </div>
                        </div> -->
</div>
<p class="help-block"></p>


<!-- city input-->
<div class="control-group">
    <label class="control-label">City / Town</label>

    <div class="controls inline">
        <input id="city" name="city" type="text" placeholder="city"
               class="input-xlarge">

        <p class="help-block"></p>
    </div>
</div>
<!-- Street-->
<div class="control-group">
    <label class="control-label">Street</label>

    <div class="controls inline">
        <input id="street" name="street" type="text" placeholder="street"
               class="input-xlarge">

        <p class="help-block"></p>
    </div>
</div>
<!-- Building-->
<div class="control-group">
    <label class="control-label">Build</label>

    <div class="controls inline">
        <input id="building" name="building" type="text" placeholder="building"
               class="input-xlarge">

        <p class="help-block"></p>
    </div>
</div>
<!-- Apartament-->
<div class="control-group">
    <label class="control-label">Apartament</label>

    <div class="controls inline">
        <input id="apartament" name="apartament" type="text" placeholder="apartament"
               class="input-xlarge">

        <p class="help-block"></p>
    </div>
</div>
<!-- country select -->
<div class="control-group">
    <label>Country</label>

    <div class="controls inline" class="inline">
        <select id="country" name="country" class="input-xlarge">
            <option value="RU">Russia</option>
            <option value="AF" selected="selected">Afghanistan</option>
        </select>
    </div>
</div>
