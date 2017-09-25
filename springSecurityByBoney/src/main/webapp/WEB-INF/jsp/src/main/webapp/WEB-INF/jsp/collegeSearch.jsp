<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta name="google" content="notranslate" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/collegeSearch.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/collegeSearch.css">
</head>
<body>
	<div class="container page_container college_search_container">
		<div class="row">
			<div
				class="col-lg-3 col-md-4 col-sm-6 col-xs-12 search_criteria_container">
				<form role="form" name="searchCollegesFilterForm"
					id="searchCollegesFilterForm" method="post">

					<div class="form-group">
						<label for="search_college">College</label> <input type="text"
							oninput="getSchoolsByCriteria()" name="search_college"
							class="form-control" id="search_college">
					</div>
					<div class="form-group">
						<label for="search_state">State</label><select name="search_state"
							id="search_state" onchange="getSchoolsByCriteria()">
							<option value="">Select State</option>
						</select>
					</div>
					<div class="form-group">
						<label for="search_city">City</label> <input type="text"
							class="form-control" id="search_city" name="search_city"
							oninput="getSchoolsByCriteria()">
					</div>
					<div class="form-group">
						<label for="search_min_gre_score">Min GRE Score</label> <input
							type="text" class="form-control" name="search_min_gre_score"
							id="search_min_gre_score" placeholder="260 - 340"
							onkeypress="return isNumber(event);"
							onchange="isValidSearchScore(this.id);">
					</div>
					<div class="form-group">
						<label for="search_min_toefl_score">Min TOEFL Score</label> <input
							type="text" class="form-control" name="search_min_toefl_score"
							id="search_min_toefl_score" placeholder="0 - 120"
							onkeypress="return isNumber(event);"
							onchange="isValidSearchScore(this.id);">

					</div>
					<div class="form-group">
						<label for="search_min_gmat_score">Min GMAT Score</label><input
							type="text" name="search_min_gmat_score" class="form-control"
							id="search_min_gmat_score" placeholder="200 - 800"
							onkeypress="return isNumber(event);"
							onchange="isValidSearchScore(this.id);">
					</div>
					<div class="form-group">
						<label for="search_min_gpa_score">Min GPA Score</label><input
							type="text" name="search_min_gpa_score" class="form-control"
							id="search_min_gpa_score" placeholder="0.0 - 4.0"
							onchange="isValidSearchScore(this.id);">
					</div>
					<div class="form-group">
						<label for="requires_trancripts">Transcripts Required?</label> <label
							class="radio-inline"><input type="radio"
							name="requires_trancripts" value="Yes"
							onchange="getSchoolsByCriteria()">Yes</label> <label
							class="radio-inline"><input type="radio"
							name="requires_trancripts" value="No"
							onchange="getSchoolsByCriteria()">No</label>
					</div>
					<div class="form-group">
						<label for="requires_solv_certficate">Solv Cert Required?</label>
						<label class="radio-inline"><input type="radio"
							name="requires_solv_certficate" value="Yes"
							onchange="getSchoolsByCriteria()">Yes</label> <label
							class="radio-inline"><input type="radio"
							name="requires_solv_certficate" value="No"
							onchange="getSchoolsByCriteria()">No</label>
					</div>
					<div class="form-group">
						<label for="requires_rec_letters">Rec. Letter Required?</label> <label
							class="radio-inline"><input type="radio"
							name="requires_rec_letters" value="Yes"
							onClick="getSchoolsByCriteria()">Yes</label> <label
							class="radio-inline"><input type="radio"
							name="requires_rec_letters" value="No"
							onClick="getSchoolsByCriteria()">No</label>
					</div>
			</div>
			<div class="col-lg-9 col-md-8 col-sm-6 col-xs-12 school-container">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="row schools_found">
						<label id="no_of_schools_found"></label> <label>Schools
							Found</label>
					</div>
					<div class="row school-container-content"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>