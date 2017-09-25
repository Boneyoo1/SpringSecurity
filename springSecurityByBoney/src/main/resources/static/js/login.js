$(document).ready(function() {
	function doLogin() {

		var data = {}
		data["emailAddress"] = $("#username").val();
		data["password"] = $("#password").val();
		
		console.log("Control comes");
		console.log(data);
		$.ajax({
			type : "POST",
			contentType : "application/javascript",
			url : "/login",
			data : JSON.stringify(data),
			dataType : 'application/javascript',
			success : function(data) {
				console.log(data);
				if (data) {
					location.href = "/dashboard";
				} else if (data == false) {
					location.href = "/adminDashboard";
				}
			},
			error : function(xhr, status, error) {
				alert("Invalid User Credentials");
			}
		});

	}
});
