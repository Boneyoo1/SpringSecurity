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
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/account.css" crossorigin="anonymous">
<link rel="stylesheet" href="css/login.css">
<script src="js/vendor/jquery.validate.min.js" crossorigin="anonymous"></script>
<script src="js/login.js" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container_fluid page_container">
		<div class="top-content">
			<div class="container">
				<div class="row">
					<div
						class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 col-sm-8 col-sm-offset-2 form-box"
						style="margin-top: 141.056px;">
					
						<div class="form-bottom">
							<div class="social-login">
								<div class="forgot-password">
									<a href="#" data-target="#forgotPasswordModal"
										data-toggle="modal">Forgot password ?</a>
								</div>
							</div>
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


		<div class="modal fade" id="forgotPasswordModal" role="dialog"
			data-cache="false">
			<div class="vertical-alignment-helper">
				<div class="modal-dialog vertical-align-center">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="modelHeaderHtml">Forgot Password</h4>
						</div>
						<div class="modal-body" name="forgotModalForm">
							<form role="form" id="forgot-form" class="form-horizontal">
								<div class="form-group">
									<div class="col-sm-12">
										<div class="row">
											<div class="col-xs-20">
												<div class="form-group" id="successResetPassword">
													<div class="form-top-left">
														<h3 class="forgot-label" for="forgot-username">Enter
															Email Address</h3>
														<p class="forgot-label">Please enter your email
															address and we'll send you a recovery link.</p>
													</div>
													<input type="text" name="forgot-username"
														placeholder="Enter Email Address"
														class="form-username form-control" id="forgot-username"
														required="required">
												</div>
											</div>
										</div>
									</div>
								</div>

							</form>
						</div>
						<div class="modal-footer" id="successResetPasswordFooter">
							<button type="button" class="btn" data-backdrop="static"
								data-keyboard="false" onclick="closeForgotPasswordModal()">Close</button>

							<button type="button" class="btn" data-backdrop="static"
								data-keyboard="false" id="forgot-password"
								onclick="resetPassword();">Reset Password</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>