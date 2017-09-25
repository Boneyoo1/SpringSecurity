<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.denkensol.universaladmission.entity.Account"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	Object accountDetails = (Object) session.getAttribute("account");
	Account account = null;
	String accountType = null;
	if (accountDetails != null) {
		account = (Account) accountDetails;
		accountType = account.getType();
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/dashboard.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/dashboard.css">
</head>
<body>

	<%
		if (accountType != null && "SUPER_ADMIN".equals(accountType)) {
	%>
	<jsp:forward page="collegeSetup.jsp" />
	<%
		} else {
	%>
	<div class="container page_container">
		<div class="row dashboard_tabs">
			<div class="col-lg-5 col-md-6 col-sm-12 col-xs-12 dashboard_tab">
				<div class="dashboard_tab_header row">
					<div class="col-lg-2 col-md-2 col-sm-6 col-xs-6">
						<i class="fa fa-user" aria-hidden="true"></i>
					</div>
					<div class="col-lg-10 col-md-10 col-sm-6 col-xs-6">
						<label class="tab_header_title">Personal Information</label>
						<div>
							<label id="profile_completed_sections_count"> </label> / <label
								id="profile_total_sections_count"> </label> &nbsp; <label>
								Sections Completed </label>
						</div>

					</div>
				</div>
				<div class="profile_tabs">
					<div class="row each_section" onclick="gotoProfile('profile')">
						<div class="col-xs-10">Profile</div>
						<div class="col-xs-2 school_app_status" id="profile_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('education')">
						<div class="col-xs-10">Education</div>
						<div class="col-xs-2 school_app_status" id="education_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('testing')">
						<div class="col-xs-10">Testing</div>
						<div class="col-xs-2 school_app_status" id="testing_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('writing')">
						<div class="col-xs-10">Personal Essay</div>
						<div class="col-xs-2 school_app_status" id="writing_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('documents')">
						<div class="col-xs-10">Documents</div>
						<div class="col-xs-2 school_app_status" id="document_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('employement')">
						<div class="col-xs-10">Employment</div>
						<div class="col-xs-2 school_app_status" id="employement_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
					<div class="row each_section" onclick="gotoProfile('recommenders')">
						<div class="col-xs-10">Recommenders</div>
						<div class="col-xs-2 school_app_status"
							id="recommendors_completed">
							<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-5 col-md-6 col-sm-12 col-xs-12 dashboard_tab">
				<div class="dashboard_tab_header row">
					<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
						<i class="fa fa-university" aria-hidden="true"></i>
					</div>
					<div class="col-lg-9 col-md-9 col-sm-9 col-xs-6">
						<label class="tab_header_title">My Colleges Information </label>
						<div>
							<label id="schools_completed_sections_count"> </label> / <label
								id="schools_total_sections_count"> </label> &nbsp; <label>
								Colleges Applied </label>
						</div>
					</div>
				</div>
				<div class="profile_tabs row custom_scroll_bar" id="school_list"></div>
			</div>
		</div>
	</div>
	<%
		}
	%>

</body>
</html>