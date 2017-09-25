$(document).ready(function() {
	$(".nav_menu").removeClass("active");
	$("#login_menu").addClass("active");
	setLoginContainerHeight();
	$("#login_form").validate({});
});

$('#forgotPasswordModal').modal({
	backdrop : 'static',
	keyboard : false,
	show : false
});
$("#forgotPasswordModal").on("hidden.bs.modal", function() {
	closeForgotPasswordModal();
});

$(window).resize(function() {
	setLoginContainerHeight();
});

function setLoginContainerHeight() {
	var headerHeight = $(".app_header").innerHeight();
	var windowHeight = $(window).innerHeight();
	var homeContainerHeight = windowHeight - headerHeight;
	var loginBoxHeight = $(".form-box").innerHeight();
	$(".form-box")
			.css("margin-top", (homeContainerHeight - loginBoxHeight) / 2);
}

function doLogin() {
	if ($("#login_form").valid()) {
		var data = {}
		data["emailAddress"] = $("#form-username").val();
		data["password"] = $("#form-password").val();
		$("#login-btn").prop("disabled", true);
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/loginAccount",
			data : JSON.stringify(data),
			dataType : 'json',
			success : function(data) {
				if (data) {
					location.href = "/dashboard";
				} else if (data == false) {
					location.href = "/adminDashboard";
				}
			},
			error : function(xhr, status, error) {
				$.alert({
					content : "Invalid User Credentials"
				});
				$("#login-btn").prop("disabled", false);
			}
		});
	}
}
function closeForgotPasswordModal() {
	$('#forgotPasswordModal').modal('toggle');
	$('#forgotPasswordModal').removeData();
	var modelHeaderHtml = "<h4 class='modal-title' id='modelHeaderHtml'>Forgot Password</h4>";
	var modelHtml = "<div class='form-top-left' ><h3 class='forgot-label' for='forgot-username'>Enter Email Address</h3> <p class='forgot-label'>Please enter your email address and we'll send you a recovery link.</p></div><input type=text' name='forgot-username' placeholder='Enter Email Address' class='form-username form-control' id='forgot-username'	required='required'>";
	var modelFooterHtml = "<button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='closeForgotPasswordModal()'>Close</button><button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='resetPassword()'>Reset Password</button>";
	$("#successResetPassword").html(modelHtml);
	$("#successResetPasswordFooter").html(modelFooterHtml);
	$("#modelHeaderHtml").html(modelHeaderHtml);

}
function openResetForgotModel() {
	var modelHeaderHtml = "<h4 class='modal-title' id='modelHeaderHtml'>Forgot Password</h4>";
	var modelHtml = "<div class='form-top-left' ><h3 class='forgot-label' for='forgot-username'>Enter Email Address</h3> <p class='forgot-label'>Please enter your email address and we'll send you a recovery link.</p></div><input type=text' name='forgot-username' placeholder='Enter Email Address' class='form-username form-control' id='forgot-username'	required='required' ><br/><p class='forgot-label' style='color:#F2545B;'>Invalid email address. Please try again.</p>";
	var modelFooterHtml = "<button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='closeForgotPasswordModal()'>Close</button><button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='resetPassword()'>Reset Password</button>";
	$("#successResetPassword").html(modelHtml);
	$("#successResetPasswordFooter").html(modelFooterHtml);
	$("#modelHeaderHtml").html(modelHeaderHtml);
}
function resetPassword() {
	var emailAddress = "";

	if ($("#forgot-form").valid()) {
		var data = {}
		emailAddress = $("#forgot-username").val();
		data["emailAddress"] = emailAddress;
		$("#forgot-password").prop("disabled", true);
		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/generatePassword",
					data : JSON.stringify(data),
					dataType : 'json',
					success : function(data) {
						if (data) {
							var modelHeaderHtml = "<i class='fa fa-thumbs-o-up w3-text-indigo' style='font-size:72px;text-align:center;color: #F2545B !important;'></i>"
							var modelHtml = "<h3 class='forgot-label' >Password recovery email sent to "
									+ emailAddress
									+ "</h3><br></br><p style='text-align:left;'>If you don't reset the password within the next 30 minutes, the password reset link will get expired. </p>";
							var modelFooterHtml = "<button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='closeForgotPasswordModal()'>Close</button>";
							$("#successResetPassword").html(modelHtml);
							$("#successResetPasswordFooter").html(
									modelFooterHtml);
							$("#modelHeaderHtml").html(modelHeaderHtml);

						} else {
							var modelHeaderHtml = "<i class='fa fa-exclamation-triangle blue' style='font-size:50px;color: #F2545B !important;' aria-hidden='true'></i>"
							var modelHtml = "<p>"
									+ emailAddress
									+ " is not registered with us. For registration please: <a href='register' style='color: #000000;'>Click Here</a></p>";
							var modelFooterHtml = "<button type='button' class='btn' data-backdrop='static' data-keyboard='false' onclick='openResetForgotModel()'>Close</button>";
							$("#successResetPassword").html(modelHtml);
							$("#successResetPasswordFooter").html(
									modelFooterHtml);
							$("#modelHeaderHtml").html(modelHeaderHtml);
						}
					},
					error : function(xhr, status, error) {
						$.alert({
							content : "Email id does not exist"
						});
						$("#forgot-password").prop("disabled", false);
					}
				});
	}

}