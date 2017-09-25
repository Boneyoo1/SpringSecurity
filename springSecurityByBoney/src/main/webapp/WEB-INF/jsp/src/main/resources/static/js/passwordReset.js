$(document).ready(function() {
	$("#reset-password-error").hide();
	$('#successResetPasswordModal').modal('hide');
	$('#successResetPasswordModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
});

function comparePassword() {
	var newPassword = $("#form-new-password").val();
	var confirmPassword = $("#form-confirm-password").val();
	if (newPassword != confirmPassword) {
		var statusHtml = "Passwords don't match";
		$("#reset-password-error").show();
		$("#reset-password-error").html(statusHtml);
	} else {
		$("#reset-password-error").hide();
	}

}
function updateNewPassword() {
	var emailAddress = "";
	$("#reset-password-btn").prop("disabled", true);
	var newPassword = $("#form-new-password").val();
	var confirmPassword = $("#form-confirm-password").val();
	if (newPassword == confirmPassword) {
		$("#reset-password-error").hide();
		if ($("#reset_password_form").valid()) {
			emailAddress = $("#form-reset-password").val();

			var data = {};
			data["emailAddress"] = emailAddress;
			data["newPassword"] = $("#form-new-password").val();
			data["confirmPassword"] = $("#form-confirm-password").val();

			$
					.ajax({
						type : "POST",
						contentType : "application/json",
						url : "/resetPassword",
						data : JSON.stringify(data),
						dataType : 'json',
						success : function(data) {
							var modelHeaderHtml = "<i class='fa fa-thumbs-o-up w3-text-indigo' style='font-size:72px;text-align:center;color: #F2545B !important;'></i>"
							var modelHtml = "<h3 class='forgot-label' style='text-align:center !important;'> "
									+ emailAddress
									+ "</h3><br></br><p style='text-align:left !important;'>Password reset successful. Please login again. </p>";
							var modelFooterHtml = "<button type='button'  class='btn' data-backdrop='static' data-keyboard='false' onclick='gotoLogin();'>Login</button>";
							$("#successResetPassword").html(modelHtml);
							$("#successResetPasswordFooter").html(
									modelFooterHtml);
							$("#modelHeaderHtml").html(modelHeaderHtml);
							$('#successResetPasswordModal').modal('toggle');

						},

					});
		}
	}
}

function gotoLogin() {
	location.href = "login";
}