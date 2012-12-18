<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:page.template>
	<jsp:attribute name="title">Login</jsp:attribute>
	<jsp:attribute name="head">
	
 <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">
	</jsp:attribute>
	<jsp:body>
<%-- <body onload='document.loginForm.j_username.focus();'> --%>

	<c:if test="${not empty param.error}">
		<div class="alert alert-block alert-error fade in">
            <h4 class="alert-heading">Log in attempt was unsuccessful!</h4>
            <p>Cause: <c:out
					value='${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}' /> </p>
          </div>
	</c:if>
	<div class="container">
	<form name='loginForm' class="form-signin" action="<c:url value='logincheck' />"
			method='POST'>
			<h2 class="form-signin-heading">Please log in</h2>
			<input type="text" class="input-block-level" name='j_username' placeholder="User name">
			<input type="password" class="input-block-level" name='j_password' placeholder="Password">
			<button class="btn btn-large btn-primary" type="submit">Sign in</button>
	</form>
	</div>
	</jsp:body>
</t:page.template>