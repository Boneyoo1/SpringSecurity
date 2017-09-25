<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.denkensol.universaladmission.entity.Account"%>
<%@ page import="java.net.URL"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<link rel="stylesheet" href="css/vendor/jquery-te-1.4.0.css"
	crossorigin="anonymous">

<link rel="stylesheet" href="css/vendor/bootstrap.min.css"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/vendor/bootstrap-theme.min.css"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/header.css" crossorigin="anonymous">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="css/form-elements.css">
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
<script src="js/vendor/jquery-3.1.1.min.js"></script>
<script src="js/vendor/bootstrap.min.js" crossorigin="anonymous"></script>
<script src="js/header.js" crossorigin="anonymous"></script>
<script src="js/vendor/jquery-ui.min.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/jquery-ui.min.css">
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/vendor/jquery.validate.min.js" crossorigin="anonymous"></script>
<script src="js/vendor/jquery-te-1.4.0.min.js" crossorigin="anonymous"></script>
<script src="js/vendor/jquery-confirm.min.js" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(document).ready(function() {

		sessionTime();
		browserCompatabity();

	});
	var idleTime = 0;
	function sessionTime() {

		$(this).mousemove(function(e) {
			idleTime = 0;
		});
		$(this).keypress(function(e) {
			idleTime = 0;
		});

		var idleInterval = setInterval(function() {
			timerIncrement();
		}, 14 * 60 * 1000); // 14 minute 
	}
	function timerIncrement() {
		idleTime = idleTime + 1;
		if (idleTime > 1 && idleTime < 3) { // hit when idleTime 2
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/signOff",
				success : function(data) {
				},
				error : function(e) {
					$
					.alert({
						content : "Error while signoff"
					});
				}
			});
			$(window).focus(function() {
				$(this).off('focus');
				sesionTimeoutRedirect();
			});
		}
	}
</script>

</head>

<%
	String loggedInUserName = null;
	Object accountDetails = (Object) session.getAttribute("account");
	String userEmail = null;
	String accountType = null;
	Long accountGuid = null;
	Account accountData = null;
	if (accountDetails != null) {
		accountData = (Account) accountDetails;
		String UserName = accountData.getEmailAddress();
		String[] loggedInUserNames = UserName.split("@");
		loggedInUserName = loggedInUserNames[0];
		userEmail = accountData.getEmailAddress();
		accountGuid = accountData.getGuid();
		accountType = accountData.getType();
	}
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<body style="padding-right: 0px;'">
	<div class="container_fluid">
		<input type="hidden" id="login_account_guid" value="<%=accountGuid%>">
		<nav role="navigation"
			class="navbar navbar-inverse navbar-fixed-top app_header">
		<div class="navbar-header">
			<button type="button" data-target="#navbarCollapse"
				data-toggle="collapse" class="navbar-toggle">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a href="<%=baseURL%>" class="navbar-brand"><i
				class="app_logo fa fa-university fa-2x" aria-hidden="true"></i>SKOOLVILLE
			</a>
		</div>
		<div id="navbarCollapse" class="collapse navbar-collapse">

			<ul class="nav navbar-nav">
				<%
					if (loggedInUserName == null || loggedInUserName.isEmpty()) {
				%>
				<li class="active nav_menu" id="home_menu"><a
					href="<%=baseURL%>">Home</a></li>
				<li id="college_search_menu" class="nav_menu"><a
					href="collegeSearch">College Search</a></li>
				<%
					} else if (accountType != null && "SUPER_ADMIN".equals(accountType)) {
				%>
				<li id="college_setup_menu" class="nav_menu"><a
					href="collegeSetup">College Setup</a></li>


				<%
					} else if (accountType != null && !accountType.equalsIgnoreCase("SCHOOL_ADMIN")) {
				%>
				<li class="nav_menu" id="dashboard_menu"><a href="dashboard">Dashboard</a></li>
				<li id="my_colleges_menu" class="nav_menu"><a href="myColleges">My
						Colleges</a></li>

				<li id="college_search_menu" class="nav_menu"><a
					href="collegeSearch">College Search</a></li>

				<%
					} else if (accountType != null && accountType.equalsIgnoreCase("SCHOOL_ADMIN")) {
				%>

				<li id="college_setup_menu_one" class="nav_menu"><a
					id="adminDashboard" href="adminDashboard">Admin Dashboard</a></li>

				<li id="college_setup_menu_two" class="nav_menu"><a
					id="collegeSetup" href="collegeSetup">College Setup</a></li>

				<%
					}
				%>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
					if (loggedInUserName == null || loggedInUserName.isEmpty()) {
				%>

				<li id="login_menu" class="nav_menu"><a href="login">Login</a></li>
				<li id="register_menu" class="nav_menu" ><a href="register">Register</a></li>
				<%
					} else {
				%>

				<li class="dropdown nav_menu" id="plan_ahead_menu"><a
					data-toggle="dropdown" class="dropdown-toggle" href="#" > <%=loggedInUserName%>
						<b class="caret"></b>
				</a>
					<ul role="menu" class="dropdown-menu">

						<%
							if (accountType != null && !accountType.equalsIgnoreCase("SCHOOL_ADMIN")
										&& !accountType.equalsIgnoreCase("SUPER_ADMIN")) {
						%>
						<li><a href="profile">Profile</a></li>
						<%
							}
						%>
						<li><a href="#">Account Settings</a></li>
						<li><a href="signOut">Sign Out</a></li>


					</ul></li>
				<script type="text/javascript">
					function sesionTimeoutRedirect() {

						$
						.alert({
							content : "Sesssion timed out due to inactivity. Please login again."
						});
						window.location.href = "/login";

					}
				</script>
				<%
					}
				%>

			</ul>
		</div>
		</nav>
	</div>
</body>
</html>
