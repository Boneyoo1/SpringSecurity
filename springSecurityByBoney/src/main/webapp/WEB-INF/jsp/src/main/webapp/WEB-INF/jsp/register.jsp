<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/account.css" crossorigin="anonymous">
<link rel="stylesheet" href="css/register.css" crossorigin="anonymous">
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/vendor/jquery.validate.min.js" crossorigin="anonymous"></script>
<script src="js/register.js" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container_fluid page_container">
		<div class="top-content">
			<div class="container">
				<div class="row">
					<div
						class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 form-box"
						style="margin-top: 2px !important;">
						<div class="form-top">
							<div class="form-top-left">
								<h3>Register to Skoolville</h3>
								<p>Enter following information to register:</p>
							</div>
							<div class="form-top-right">
								<i class="account_app_logo fa fa-university " aria-hidden="true"></i>
							</div>
						</div>
						<div class="form-bottom">
							<form role="form" class="login-form" method="post"
								id="register_form" name="register_form">
								<div class="row">
									<div class="col-sm-4"
										style="color: white; font-family: sans-serif;">
										<label class="radio-lable"> <input
											class="radio--input" type="radio" name="accountType"
											value="STUDENT"> I am a STUDENT
										</label>
									</div>
									<div class="col-sm-6"
										style="color: white; font-family: sans-serif; font-weight: normal;">
										<label class="radio-lable"> <input type="radio"
											name="accountType" class="radio-input" value="SCHOOL_ADMIN">
											I am a SCHOOL ADMIN
										</label>
									</div>
								</div>
								<div class="form-group">
									<label class="sr-only" for="form-user-firstname">Your
										E-Mail</label> <input type="email" name="form-user-email"
										placeholder="Your E-Mail" class="form-user-email form-control"
										id="form-user-email" required="required"
										change="isRegistered(this.form-user-email)">
								</div>
								<label class="school_name_hidden_lable" id="form-school-name"
									aria-hidden="hidden"
									style="text-align: center; font-weight: normal; color: white; font-size: medium; padding-left: 80px;"></label>
								<div class="form-group">
									<label class="sr-only" for="form-password">Password</label> <input
										type="password" name="form-password"
										placeholder="Choose Password"
										class="form-password form-control" id="form-password"
										required="required">
								</div>
								<div class="form-group">
									<label class="sr-only" for="form-confirm-password">Password</label>
									<input type="password" name="form-confirm-password"
										placeholder="Confirm Password"
										class="form-confirm-password form-control"
										id="form-confirm-password" required="required">
								</div>

								<button type="button" class="btn" id="register-btn"
									onclick="doRegister()">Register!</button>
								<br> <br> <br>
							</form>
							<!--  	<div class="social-login">
								<h4>(OR)</h4>
								<div class="social-login-buttons">
									<a class="btn btn-link-1 btn-link-1-facebook" href="#"> <i
										class="fa fa-facebook"></i> Facebook
									</a>
								</div>
							</div>-->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>