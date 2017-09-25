<%@ page language="java" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<head>
<%@ include file="header.jsp"%>
<link rel="stylesheet" href="css/account.css" crossorigin="anonymous">
<link rel="stylesheet" href="css/portfolio.css" crossorigin="anonymous">
<script src="js/portfolio.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
</head>
<%
	String tabName = "";
	Object tabNameObj = (Object) request.getAttribute("tabName");
	if (tabNameObj != null) {
		tabName = (String) tabNameObj;
	}
%>
<body>
	<input type="hidden" id="hidden_tab_name" value="<%=tabName%>">
	<div class="container page_container">
		<div class="row">
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 apply-tab-container">
				<div class="col-lg-2 col-md-2 col-sm-4 col-xs-6 apply-tab-menu">
					<div id="profile_sections_completed_uncompleted">
						<i class="fa fa-user" aria-hidden="true"></i>
						<div class='applied_non_applied_count'>
							<label id="profile_completed_sections_count"> </label> / <label
								id="profile_total_sections_count"> </label> &nbsp; <label>
								Sections Completed </label>
						</div>
					</div>
					<div class="list-group">
						<a href="#" class="list-group-item tab_active text-center row">
							<div class="col-xs-10">
								<span class="fa fa-user"></span>Profile
							</div>
							<div class="col-xs-2 school_app_status" id="profile_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-book"></span>Education
							</div>
							<div class="col-xs-2 school_app_status" id="education_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-graduation-cap"></span>Testing
							</div>
							<div class="col-xs-2 school_app_status" id="testing_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-pencil"></span>Essay
							</div>
							<div class="col-xs-2 school_app_status" id="writing_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-file"></span>Documents
							</div>
							<div class="col-xs-2 school_app_status" id="document_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-users"></span>Employment
							</div>
							<div class="col-xs-2 school_app_status"
								id="employement_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a> <a href="#" class="list-group-item text-center row">
							<div class="col-xs-10">
								<span class="fa fa-users"></span>Recommenders
							</div>
							<div class="col-xs-2 school_app_status"
								id="recommendors_completed">
								<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-10 col-md-10 col-sm-8 col-xs-6 apply-tab">
					<div class="apply-tab-content active">
						<%@ include file="profile.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="education.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="testing.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="writing.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="documents.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="employement.jsp"%>
					</div>
					<div class="apply-tab-content">
						<%@ include file="recommenders.jsp"%>
					</div>
				</div>
				<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10 loading-bg">
					<div class="loading-bg-icon">
						<i class="fa fa-spinner fa-spin"></i>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>