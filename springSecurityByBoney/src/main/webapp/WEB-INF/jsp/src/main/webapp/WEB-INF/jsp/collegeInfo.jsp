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
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/collegeInfo.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/collegeInfo.css">
</head>
<%
	String schoolGuId = null;
	Object schoolGuIdObj = (Object) request.getAttribute("schoolGuId");
	if (schoolGuIdObj != null) {
		schoolGuId = (String) schoolGuIdObj;
	}
%>
<body>
	<input type="hidden" id="college_info_guid" value="<%=schoolGuId%>" />
	<div class="container page_container college_info_container">
		<div class="row return_to_search_results">
			<a href="collegeSearch">Return to search results</a>
		</div>
		<div class="row college_info_conent">
			<div
				class="col-lg-8 col-md-8 col-sm-12 col-xs-12 college_info_tab_content">
				<div class="college_name">
					<p id="school_name_p"></p>
				</div>
				<div class="college_img">
					<img src="images/dummy_school.jpg" class="img-responsive"
						id="school_img" />

				</div>

				<div class="college_details">
					<div id="college_info_text"></div>

					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>School
							Terms </label>
					</div>
					<div id="college_term_info"></div>
					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>School
							Degrees</label>
					</div>
					<div>
						<ul id="college_degree_info"></ul>
					</div>

					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>Tuition&Fees</label>
					</div>
					<div>
						<ul>

							<li><label> International App Fees : </label> <label
								id="internationAppFees"> </label></li>
							<li><label> International Credential Eval Fees : </label> <label
								id="internationCredentialEvalFees"> </label></li>
							<li><label>Mailing Fees : </label> <label id="mailingFee"></label></li>

							<li><label> Grad Tuition Fee Per Credit : </label> <label
								id="gradTuitionFeePerCredit"> </label></li>
							<li><label> Grad Credits Required : </label> <label
								id="gradCreditsRequired"> </label></li>
							<li><label id="tuitionFeesLink"> </label></li>

						</ul>

					</div>

					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>Required
							Documents</label>
					</div>
					<div>
						<ul>
							<li><label> Transcripts Required: </label> <label
								id="transcriptsRequires"> </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li><label> REC Letter Required: </label> <label
								id="recLettersRequires"> </label>&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li><label> SOP Required: </label> <label id="sopRequires">
							</label></li>
							<li><label> SOLV Certificate Required: </label> <label
								id="solvCERTRequires"> </label>&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li><label> Resume Required: </label> <label
								id="resumeRequires"> </label></li>
							<li><label>Passport Required: </label> <label
								id="requiresPassport"> </label></li>
							<li><label>TOEFL Required: </label> <label
								id="requiredTOEFL"> </label></li>
							<li><label>EAD Required: </label> <label id="requiredEAD">
							</label></li>
							<li><label>I-20 Required: </label> <label id="requiredI20">
							</label></li>
							<li><label>F1 Visa Required: </label> <label
								id="requiredF1VISA"> </label></li>
							<div id="collegeDocuments"></div>
							<li><label id="applicationCheckListLink"> </label></li>
						</ul>
					</div>
					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>Minimum
							Score Details</label>
					</div>
					<div>
						<ul>

							<li><label>Minimum GRE Score: </label> <label
								id="minimumGREScore"> </label></li>
							<li><label>Minimum GMAT Score: </label> <label
								id="minimumGMATScore"> </label></li>
							<li><label>Minimum TOEFL Score: </label> <label
								id="minimumTOEFLScore"> </label></li>
							<li><label>Minimum GPA Score: </label> <label
								id="minimumGPA"> </label></li>
						</ul>
					</div>
					<div class="sub_header">
						<label style='font-size: 18px; font-weight: bold;'>Submission
							School code</label>
					</div>
					<div>
						<ul>
							<li><label>GRE Score Submission School Code: </label> <label
								id="greScoreSubmissionSchoolCode"> </label></li>
							<li><label>GMAT Score Submission School Code:</label><label
								id="gmatScoreSubmissionSchoolCode"></label></li>
							<li><label>TOEFL Score Submission School Code:</label><label
								id="toeflScoreSubmissionSchoolCode"></label></li>
						</ul>

					</div>
					<div class="sub_header"></div>

				</div>
			</div>
			<div
				class="col-lg-4 col-md-4 col-sm-12 col-xs-12 college_info_sub_tab_content">
				<div class="college_tab_contains_"></div>
			</div>

		</div>
	</div>
</body>
</html>
