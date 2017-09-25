<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<script src="js/adminMenu.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">

<link rel="stylesheet" href="css/adminMenu.css">
</head>
<body>
	<div id="school-name-tab-menu">
		<div id="school_admin_menu" class="custom_scroll_bar">
			<div id="school_admin_each_menu_1" class="school_admin_each_menu row"
				onclick="gotoSubMenu('applicants')">
				<div class='col-xs-10'>Applicants</div>
			</div>
			<div id="school_admin_each_menu_2" class="school_admin_each_menu row"
				onclick="gotoSubMenu('prospects')">
				<div class='col-xs-10'>Prospects</div>
			</div>
		</div>
	</div>
</body>
</html>