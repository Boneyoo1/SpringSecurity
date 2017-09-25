<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="google" content="notranslate" />
<title>SKOOLVILLE</title>

<jsp:include page="header.jsp" />
<script src="js/myColleges.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/myColleges.css">
</head>
<%
	String school = "";
	Object schoolObj = (Object) request.getAttribute("school");
	if (schoolObj != null) {
		school = (String) schoolObj;
	}
%>
<body>
	<input type="hidden" id="hidden_school_guid" value="<%=school%>">
	<input type="hidden" id="school_application_guid" value="<%=school%>">
	<div class="container page_container">
		<div class="row">
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 school-list-container">
				<div class="col-lg-2 col-md-3 col-sm-4 col-xs-6"
					id="school-name-tab-menu">
					<div id="school-names-completed_uncompleted">
						<i class="fa fa-university" aria-hidden="true"></i>
						<div class='applied_non_applied_count'>
							<label id="colleges_completed_sections_count"> </label> / <label
								id="colleges_total_sections_count"> </label> &nbsp; <label>
								Colleges Applied </label>
						</div>
					</div>
					<div id="school-names" class="custom_scroll_bar"></div>
				</div>
				<div class="col-lg-10 col-md-9 col-sm-8 col-xs-6"
					id="school-content">
					<div id="school_img"></div>
					<div id="school_name_deadline_date" class="row">
						<div class='col-lg-8 col-md-8 col-sm-12 col-xs-12'
							id="school_name_col"></div>
						<div class='col-lg-4 col-md-4 col-sm-12 col-xs-12'
							id="school_deadline_col"></div>
					</div>
					<div id="school_tabs" class="row">
						<div class='col-lg-3 col-md-3 col-sm-6 col-xs-12 school_tab'
							id="college_info_school_tab"
							onclick="gotoSchoolTab('college_info_school_tab')">
							College Info<label>&nbsp;</label>
						</div>
						<div class='col-lg-3 col-md-3 col-sm-6 col-xs-12 school_tab'
							onclick="gotoSchoolTab('questions_school_tab')"
							id="questions_school_tab">
							Questions &nbsp;<label id="college_questions_status"><i
								class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;</label>
						</div>
						<div class='col-lg-3 col-md-3 col-sm-6 col-xs-12 school_tab'
							onclick="gotoSchoolTab('documents_school_tab')"
							id="documents_school_tab">
							Documents &nbsp;<label id="college_documents_status"><i
								class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;</label>
						</div>

						<div class='col-lg-3 col-md-3 col-sm-6 col-xs-12 school_tab'
							onclick="gotoSchoolTab('review_school_tab')"
							id="review_school_tab">
							Review and Submit &nbsp;<label id="review_app_status"><i
								class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;</label>
						</div>
					</div>
					<div id="school_tabs_info">
						<div id="school_tabs_info_college" class="school_tabs_info">
							<div id="school_info_content"></div>
							<div id="school_contact_info">
								<label class="sub_header">Contact Information</label>
								<div id="school_contact_info_content"></div>
							</div>
							<div id="school_terms_info">
								<label class="sub_header">Terms Information</label>
								<div id="school_terms_info_content"></div>
							</div>
							<div id="school_terms_info">
								<label class="sub_header">Degree Information</label>
								<div id="school_degree_info_content"></div>
							</div>

							<div id="requirements_to_apply_info">
								<label class="sub_header">Requirements To Apply To
									College</label>
								<div id="school_requirements_info_content"></div>
							</div>
						</div>
						<div id="school_tabs_info_questions" class="school_tabs_info">
							<form role="form" class="questions-form form-horizontal"
								method="post" id="questions-form" name="questions-form">
								<p class="college_specific_questions_p">General Questions</p>
								<div id="general_questions">
									<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
										<label class="control-label col-lg-3 "><input
											type="radio" name="form-preferred_is_transfer_student"
											value="" id="isInitialStudent"> I am a Initial
											Student <sup class='color_red'>*</sup></label> <label
											class="control-label "> <input type="radio"
											name="form-preferred_is_transfer_student" value="X"
											id="isTransferStudent"> I am a Transfer Student<sup
											class='color_red'>*</sup>
										</label>


									</div>
									<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
										<label
											class="control-label col-lg-3 col-md-3 col-sm-6 col-xs-12"
											for="form-preferred_start_term">Preferred start term
										</label>
										<div class="col-lg-9 col-md-9 col-sm-6 col-xs-12">
											<select name="form-preferred_start_term"
												id="form-preferred_start_term" required="required">
											</select>
										</div>
									</div>
									<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
										<label
											class="control-label col-lg-3 col-md-3 col-sm-6 col-xs-12"
											for="form-preferred_start_degree">Preferred Degree </label>
										<div class="col-lg-9 col-md-9 col-sm-6 col-xs-12">
											<select name="form-preferred_start_degree"
												id="form-preferred_start_degree" required="required"
												onchange="loadAllSchoolSectionQuestions()">
											</select>
										</div>
									</div>
								</div>
								<p class="college_specific_questions_p">College Specific
									Questions</p>
								<div id="question_sections"></div>
								<div class="btn-group apply-btn-group">
									<button type="button" class="btn" id="save-questions-info"
										onclick="saveQuestionsInfo(this.id)">
										<i class="fa fa-cloud"></i>&nbsp;&nbsp;Submit Questions</abbr>
									</button>
								</div>
							</form>
						</div>
						<div id="school_tabs_info_documents" class="school_tabs_info">
						</div>
						<div id="school_tabs_info_review_submit" class="school_tabs_info">
							<div class="row">
								<div class="col-xs-12">
									<p class="accept_term_conditions_p" >
										<input type="checkbox" id="accept_terms_conditions_check"
											name="accept_terms_conditions_check"
											class="accept_term_conditions_checkbox" /><span  class="accept_term_conditions_p" style='margin-left:-8px;'>By clicking the
										checkbox, you confirm that you agree to all our
										Terms&Conditions including our data policy. Your application
										and personal information may be shared with any and all of the
										Universities in our database.</span>
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-12">
									<div class="btn-group apply-btn-group">
										<button type="button" class="btn" id="submit_application_info"
											onclick="submitApplication(this.id)">
											<i class="fa fa-cloud"></i>&nbsp;&nbsp;Submit Application</abbr>
										</button>
									</div>
									<div class="btn-group apply-btn-group">
										<button type="button" class="btn"
											id="download_application_info" onclick="getApplicationPDF();">
											<i class="fa fa-cloud"></i>&nbsp;&nbsp;Download Application</abbr>
										</button>
									</div>

								</div>
								&nbsp;&nbsp;&nbsp;<label class="error" aria-hidden="true"
									style="display: inline-block; font-family: sans-serif;"
									id="error_message"> </label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>