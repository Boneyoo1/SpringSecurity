<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/adminDashboard.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/adminDashboard.css">
</head>
<body>
	<div class="page_container">
		<div class="school_admin_container">
			<div class="row school_admin_menu_container">
				<div
					class="col-lg-2 col-md-3 col-sm-12 col-xs-12 school_admin_menu_content">
					<jsp:include page="adminMenu.jsp" />
				</div>
				<div class="col-lg-10 col-md-9 col-sm-8 col-xs-6"
					id="school_admin_tab_content"></div>
			</div>
		</div>
	</div>
</body>
</html>