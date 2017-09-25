$(document).ready(function() {
	$(".nav_menu").removeClass("active");
	$("#register_menu").addClass("active");
	$("#form-user-email").change(function() {
		var emailAddress = $("#form-user-email").val();
		isRegistered(emailAddress);
	});
	$(".school_name_hidden_lable").hide();

	$("#register_form").validate({});
	setSignUpContainerHeight();
});

$(window).resize(function() {
	setSignUpContainerHeight();
});

function setSignUpContainerHeight() {
	var headerHeight = $(".app_header").innerHeight();
	var windowHeight = $(window).innerHeight();
	var homeContainerHeight = windowHeight - headerHeight;
	var loginBoxHeight = $(".form-box").innerHeight();
	$(".form-box")
			.css("margin-top", (homeContainerHeight - loginBoxHeight) / 2);
}
function isRegistered(emailAddress) {
	$(".school_name_hidden_lable").hide();
	var data = {};
	var accountType = $('input[name=accountType]:checked', '#register_form')
			.val();
	data["accountType"] = accountType;
	data["emailAddress"] = emailAddress;
	data["userName"] = " ";
	data["password"] = " ";
	if ($("#form-user-email").valid()) {
		if (accountType == "SCHOOL_ADMIN") {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/isSchoolAdmin",
				data : JSON.stringify(data),
				dataType : 'json',
				success : function(data) {
					if (data == undefined && data == null) {
						$.alert({
							content : "School Domain is Not Found"
						});

					} else {
						schoolNameHtml = data["schoolName"].toUpperCase();
						$(".school_name_hidden_lable").show();
						$("#form-school-name").text(schoolNameHtml)
					}

				},
				error : function(e) {
					$.alert({
						content : "Email Not Registered"
					});
				}
			});

		} else if (accountType == "STUDENT") {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/isExists",
				data : JSON.stringify(data),
				dataType : 'json',
				success : function(data) {
					if (data) {
						$.alert({
							content : "Email Address is Already Registered"
						});
					}
				},
				error : function(e) {
				}
			});

		} else {
			$.alert({
				content : "Please Enter Valid Email"
			});
		}

	}

}
function doRegister() {
	if ($("#register_form").valid()) {
		var data = {}
		data["emailAddress"] = $("#form-user-email").val();
		data["userName"] = $("#form-username").val();
		data["password"] = $("#form-password").val();
		var accountType = $('input[name=accountType]:checked', '#register_form')
				.val();
		data["accountType"] = accountType;
		$("#register-btn").prop("disabled", true);
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/registerAccount",
			data : JSON.stringify(data),
			dataType : 'json',
			success : function(data) {
				if (data) {
					location.href = "/profile";
				} else {
					location.href = "/adminDashboard";
				}
			},
			error : function(xhr, status, error) {
				$.alert({
					content : "Email Already Registered"
				});
				$("#register-btn").prop("disabled", false);
			}
		});
	}
}
function doRegister() {

	if ($("#register_form").valid()) {

		var data = {}

		data["emailAddress"] = $("#form-user-email").val();
		data["userName"] = $("#form-username").val();
		data["password"] = $("#form-password").val();
		var accountType = $('input[name=accountType]:checked', '#register_form')
				.val();
		data["accountType"] = accountType;
		$("#register-btn").prop("disabled", true);

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/registerAccount",
			data : JSON.stringify(data),
			dataType : 'json',
			success : function(data) {
				if (data) {
					location.href = "/profile";
				} else if (data == false) {
					location.href = "/adminDashboard";
				}
			},
			error : function(xhr, status, error) {
				$.alert({
					content : "Email Already Registered"
				});
				$("#register-btn").prop("disabled", false);
			}
		});
	}
}