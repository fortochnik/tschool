<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mipan
  Date: 27.09.2016
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bootstrap Login &amp; Register Templates</title>

  <!-- CSS -->
  <%--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">--%>
  <link rel="stylesheet" href="resources/css/bootstrap.min.css">
  <link rel="stylesheet" href="resources/css/custom/spaces.css">
  <%--<link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">--%>
  <link rel="stylesheet" href="resources/css/login/form-elements.css">
  <link rel="stylesheet" href="resources/css/login/style.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Favicon and touch icons -->
  <link rel="shortcut icon" href="assets/ico/favicon.png">
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">

</head>

<body>

<!-- Top content -->
<div class="top-content">

  <div class="container">
    <div class="page-header">
      <jsp:include page="/WEB-INF/views/header.jsp"/>
    </div>
  </div>


  <div class="inner-bg">
    <div class="container">

     <%-- <div class="row">
        <div class="col-sm-8 col-sm-offset-2 text">
          <h1>Bootstrap Login &amp; Register Forms</h1>
          <div class="description">
            <p>
              This is a free responsive <strong>"login and register forms"</strong> template made with Bootstrap.
              Download it on <a href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>,
              customize and use it as you like!
            </p>
          </div>
        </div>
      </div>--%>

      <div class="row">
        <div class="col-sm-5">

          <div class="form-box">
            <div class="form-top">
              <div class="form-top-left">
                <h3>Login to our site</h3>
                <p>Enter username and password to log on:</p>
              </div>
              <div class="form-top-right">
                <i class="fa fa-key"></i>
              </div>
            </div>
            <div class="form-bottom">

              <form role="form" action="login" method="post" class="login-form">
                <div class="form-group">
                  <label class="sr-only" for="form-username">Username</label>
                  <input type="text" name="form-username" placeholder="Username..." class="form-username form-control" id="form-username">
                </div>
                <div class="form-group">
                  <label class="sr-only" for="form-password">Password</label>
                  <input type="password" name="form-password" placeholder="Password..." class="form-password form-control" id="form-password">
                </div>
                <button type="submit" name="form-login-submit" value="login" class="btn">Sign in!</button>
                <c:if test="${not empty requestScope.get('errorSignInMessage')}">
                  <div class="form-group top15">
                    <div class="alert alert-danger fade in ">
                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                      <strong>Error!</strong>  <c:out value="${requestScope.get('errorSignInMessage')}"/>
                    </div>
                  </div>
                </c:if>
              </form>
            </div>
          </div>

          <%--<div class="social-login">
            <h3>...or login with:</h3>
            <div class="social-login-buttons">
              <a class="btn btn-link-1 btn-link-1-facebook" href="#">
                <i class="fa fa-facebook"></i> Facebook
              </a>
              <a class="btn btn-link-1 btn-link-1-twitter" href="#">
                <i class="fa fa-twitter"></i> Twitter
              </a>
              <a class="btn btn-link-1 btn-link-1-google-plus" href="#">
                <i class="fa fa-google-plus"></i> Google Plus
              </a>
            </div>
          </div>--%>

        </div>

        <div class="col-sm-1 middle-border"></div>
        <div class="col-sm-1"></div>

        <div class="col-sm-5">

          <div class="form-box">
            <div class="form-top">
              <div class="form-top-left">
                <h3>Sign up now</h3>
                <p>Fill in the form below to get instant access:</p>
              </div>
              <div class="form-top-right">
                <i class="fa fa-pencil"></i>
              </div>
            </div>
            <div class="form-bottom">
              <form role="form" action="" method="post" class="registration-form">
                <div class="form-group">
                  <label class="sr-only" for="form-first-name">First name</label>
                  <input type="text" name="form-first-name" placeholder="First name..." class="form-first-name form-control" id="form-first-name">
                </div>
                <div class="form-group">
                  <label class="sr-only" for="form-last-name">Last name</label>
                  <input type="text" name="form-last-name" placeholder="Last name..." class="form-last-name form-control" id="form-last-name">
                </div>
                <div class="form-group">
                  <label class="sr-only" for="form-email">Email</label>
                  <input type="text" name="form-email" placeholder="Email..." class="form-email form-control" id="form-email">
                </div>
                <div class="form-group">
                  <label class="sr-only" for="form-password">Password</label>
                  <input type="password" name="form-password" placeholder="Password..." class="form-password form-control"
                         title="6 characters minimum"
                         id="form-password-registration">
                  <div class="help-block">Minimum of 6 characters</div>
                </div>
                <button type="submit" name="form-registration-submit" class="btn">Sign me up!</button>
                <c:if test="${not empty requestScope.get('errorSignUpMessage')}">
                  <div class="form-group top15">
                    <div class="alert alert-danger fade in ">
                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                      <strong>Error!</strong>  <c:out value="${requestScope.get('errorSignUpMessage')}"/>
                    </div>
                  </div>
                </c:if>
              </form>
            </div>
          </div>

        </div>
      </div>

    </div>
  </div>

</div>

<!-- Footer -->
<footer>
  <div class="container">
    <div class="row">

      <div class="col-sm-8 col-sm-offset-2">
        <div class="footer-border"></div>
        <p>Made by Anli Zaimi at <a href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>
          having a lot of fun. <i class="fa fa-smile-o"></i></p>
      </div>

    </div>
  </div>
</footer>

<!-- Javascript -->
<script src="resources/js/login/jquery-1.11.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/login/scripts.js"></script>

<!--[if lt IE 10]>
<script src="resources/js/login/placeholder.js"></script>
<![endif]-->

</body>

</html>