<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/vendor/jquery.dataTables.min.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/dataTables.bootstrap.min.css">
<script src="js/collegeSetup.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/myColleges.css">
<link rel="stylesheet" href="css/vendor/bootstrap-multiselect.css">
<script src="js/vendor/bootstrap-multiselect.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/collegeSetup.css">
<script src="js/vendor/jquery.validate.min.js" crossorigin="anonymous"></script>

</head>
<body>
	<div class="container page_container">
		<div class="row">
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 school-list-container">
				<div class="col-lg-2 col-md-3 col-sm-4 col-xs-6"
					id="school-name-tab-menu">

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
						<div class='col-lg-2 col-md-2 col-sm-6 col-xs-12 school_tab'
							id="school_tabs_info_college_tab"
							onclick="gotoSchoolTab('school_tabs_info_college')">Info</div>
						<div class='col-lg-2 col-md-2 col-sm-6 col-xs-12 school_tab'
							id="school_tabs_student_view_tab"
							onclick="gotoSchoolTab('school_tabs_student_view')">Info
							(Student View)</div>
						<div class='col-lg-2 col-md-2 col-sm-6 col-xs-12 school_tab'
							id="school_tabs_degrees_tab"
							onclick="gotoSchoolTab('school_tabs_degrees')">Degrees</div>
						<div class='col-lg-2 col-md-2 col-sm-6 col-xs-12 school_tab'
							id="school_tabs_terms_tab"
							onclick="gotoSchoolTab('school_tabs_terms')">Terms</div>
						<div class='col-lg-2 col-md-2 col-sm-6 col-xs-12 school_tab'
							onclick="gotoSchoolTab('school_tabs_info_questions')"
							id="school_tabs_info_questions_tab">Questions</div>
						<div class='col-lg-2 col-md-3 col-sm-6 col-xs-12 school_tab'
							onclick="gotoSchoolTab('school_tabs_info_documents')"
							id="school_tabs_info_documents_tab">Documents</div>
					</div>
					<div id="school_tabs_info">
						<div id="school_tabs_info_college" class="school_tabs_info">
							<form role="form" class="form-horizontal" method="post"
								id="college-info-form" name="college-info-form">

								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-permanent-address-line-1"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Address
											Line1</label>
										<div class="col-sm-9">
											<textarea class="form-control" rows="2"
												id="form-school-permanent-address-line-1"
												name="form-school-permanent-address-line-1"
												placeholder="Address Line 1" required="required"></textarea>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-permanent-address-line-2"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Address
											Line2</label>
										<div class="col-sm-9">
											<textarea class="form-control" rows="2"
												id="form-school-permanent-address-line-2"
												name="form-school-permanent-address-line-2"
												placeholder="Address Line 2" required="required"></textarea>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-city"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">City</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-city" placeholder="City"
												class="form-control" id="form-school-city"
												required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-state"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">State</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-state"
												placeholder="State" class="form-control"
												id="form-school-state" required="required" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12"
											for="form-school-country">Country </label>
										<div class="col-sm-9">
											<select name="form-school-country"
												placeholder="Select Country" id="form-school-country"
												required="required">
												<option value="">Select Country</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-zipcode"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Zip
											Code</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-zipcode"
												placeholder="Zip Code" class="form-control"
												id="form-school-zipcode" required="required" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-contact-person"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Contact
											Person</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-contact-person"
												placeholder="Contact Person" class="form-control"
												id="form-school-contact-person" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-contact-number"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Contact
											Number</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-contact-number"
												placeholder="Contact Number" class="form-control"
												id="form-school-contact-number" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-email"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">E-Mail</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-email"
												placeholder="E-Mail" class="form-control"
												id="form-school-email" required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-website"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Website
										</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-website"
												placeholder="Website" class="form-control"
												id="form-school-website" value="" />
										</div>
									</div>
								</div>

								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-gmat-score-submission-code"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">GMAT
											Score Submission School Code</label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-gmat-score-submission-code"
												placeholder="GMAT Score Submission School Code"
												class="form-control"
												id="form-school-gmat-score-submission-code"
												required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-gre-score-submission-code"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">GRE
											Score Submission School Code</label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-gre-score-submission-code"
												placeholder="GRE Score Submission School Code"
												class="form-control"
												id="form-school-gre-score-submission-code" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-toefl-score-submission-code"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">TOEFL
											Score Submission School Code</label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-toefl-score-submission-code"
												placeholder="TOEFL Score Submission School Code"
												class="form-control"
												id="form-school-toefl-score-submission-code"
												required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-internation-app-fees"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">International
											App Fee </label>
										<div class="col-sm-9">
											<input type="text" name="form-school-internation-app-fees"
												placeholder="International App Fee" class="form-control"
												id="form-school-internation-app-fees" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-internation-credential-eval-fees"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">International
											Credential Eval Fee </label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-internation-credential-eval-fees"
												placeholder="International Credential Eval Fee"
												class="form-control"
												id="form-school-internation-credential-eval-fees" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-internation-mailing-fees"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Mailing
											Fee </label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-internation-mailing-fees"
												placeholder="Mailing Fee" class="form-control"
												id="form-school-internation-mailing-fees" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-min-gmat-score"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Min
											GMAT Score</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-min-gmat-score"
												placeholder="Min GMAT Score" class="form-control"
												id="form-school-min-gmat-score" required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-min-gre-score"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Min
											GRE Score</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-min-gre-score"
												placeholder="Min GRE Score" class="form-control"
												id="form-school-min-gre-score" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-min-toefl-score"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Min
											TOEFL Score</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-min-toefl-score"
												placeholder="Min TOEFL Score" class="form-control"
												id="form-school-min-toefl-score" required="required"
												value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-min-gpa-score"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Min
											GPA</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-min-gpa-score"
												placeholder="Min GPA" class="form-control"
												id="form-school-min-gpa-score" value="" />
										</div>
									</div>
								</div>


								<!--New Filed Added 28-6-17  -->
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-email-domain-1"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Email
											Domain 1</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-email-domain-1"
												placeholder="Email Domain 1" class="form-control"
												id="form-school-email-domain-1" required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-email-domain-2"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Email
											Domain 2</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-email-domain-2"
												placeholder="Email Domain 2" class="form-control"
												id="form-school-email-domain-2" required="required" value="" />
										</div>
									</div>

									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-email-domain-3"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Email
											Domain 3</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-email-domain-1"
												placeholder="Email Domain 3" class="form-control"
												id="form-school-email-domain-3" required="required" value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-application-checklist-link"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Application
											Checklist Link</label>
										<div class="col-sm-9">
											<input type="text"
												name="form-school-application-checklist-link"
												placeholder="Application Checklist Link"
												class="form-control"
												id="form-school-application-checklist-link"
												required="required" value="" />
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-school-tuition-fees-link"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Tuition
											& Fees Link</label>
										<div class="col-sm-9">
											<input type="text" name="form-school-tuition-fees-link"
												placeholder="Tuition & Fees Link" class="form-control"
												id="form-school-tuition-fees-link" required="required"
												value="" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-rec-letter-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Rec
											Letters Required ?</label>
										<div class="col-sm-9">
											<select name="form-rec-letter-required"
												placeholder="Select REC Letter"
												id="form-rec-letter-required" required="required">
												<option value="">Select REC Letter</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-passport-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Passport
											Required ?</label>
										<div class="col-sm-9">
											<select name="form-passport-required"
												placeholder="Select Passport Required"
												id="form-passport-required" required="required">
												<option value="">Select Passport Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-resume-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Resume
											Required ?</label>
										<div class="col-sm-9">
											<select name="form-resume-required"
												placeholder="Select Resume Required"
												id="form-resume-required" required="required">
												<option value="">Select Resume Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-solv-cert-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Solv
											Certificate Required ?</label>
										<div class="col-sm-9">
											<select name="form-solv-cert-required"
												placeholder="Select Certificate Required"
												id="form-solv-cert-required" required="required">
												<option value="">Select Certificate Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-sop-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">SOP
											Required ?</label>
										<div class="col-sm-9">
											<select name="form-sop-required"
												placeholder="Select SOP Required" id="form-sop-required"
												required="required">
												<option value="">Select SOP Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-transcripts-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">Transcripts
											Required?</label>
										<div class="col-sm-9">
											<select name="form-transcripts-required"
												placeholder="Select Transcripts Required"
												id="form-transcripts-required" required="required">
												<option value="">Select Transcripts Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-ead-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">EAD
											Required ?</label>
										<div class="col-sm-9">
											<select name="form-ead-required"
												placeholder="Select EAD Required" id="form-ead-required"
												required="required">
												<option value="">Select EAD Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-i20-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">I-20
											Required?</label>
										<div class="col-sm-9">
											<select name="form-i20-required"
												placeholder="Select I-20 Required" id="form-i20-required"
												required="required">
												<option value="">Select I-20 Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>

									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-f1-visa-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">F1-VISA
											Required?</label>
										<div class="col-sm-9">
											<select name="form-f1-visa-required"
												placeholder="Select F1-VISA Required"
												id="form-f1-visa-required" required="required">
												<option value="">Select F1-VISA Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
									<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<label for="form-toefl-score-required"
											class="control-label col-lg-6 col-md-6 col-sm-12 col-xs-12">TOEFL
											SCORE Required?</label>
										<div class="col-sm-9">
											<select name="form-toefl-score-required"
												placeholder="Select TOEFL Score Required"
												id="form-toefl-score-required" required="required">
												<option value="">Select TOEFL Score Required</option>
												<option value="NO">NO</option>
												<option value="YES">YES</option>
												<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<button type="button" class="btn" id="save-college-info"
											onclick="saveSchoolInfo('save-college-info')">
											Save School Info&nbsp;&nbsp;</abbr>
										</button>
									</div>
								</div>
							</form>
						</div>
						<div id="school_tabs_student_view" class="school_tabs_info">
							<textarea id="college_info_text">
							</textarea>
							<div class="row">
								<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
									<button type="button" class="btn"
										id="save-college-info-content"
										onclick="saveSchoolInfoContent('save-college-info-content')">
										Save School Info&nbsp;&nbsp;</abbr>
									</button>
								</div>
							</div>
						</div>
						<div id="school_tabs_degrees" class="school_tabs_info">
							<div class="row">
								<div class="col-xs-12">
									<button type="button" class="btn" id="add-degree-button"
										data-toggle="modal" data-target="#addShoolDegreeModal">
										<i class="fa fa-plus"></i>&nbsp;&nbsp;Add Degree</abbr>
									</button>
								</div>
							</div>
							<table id="school_degrees_list_tbl"
								class="table table-striped table-bordered table-responsive"
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Degree name</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody id="schools_degrees_list_tbody">
								</tbody>
								<tfoot>
									<tr id="school_degrees_tfoot_row_no_data">
										<td colspan="4" align="center">No Degrees Found</td>
									</tr>
								</tfoot>
							</table>
						</div>
						<div id="school_tabs_terms" class="school_tabs_info">
							<div class="row">
								<div class="col-xs-12">
									<button type="button" class="btn" id="add-degree-button"
										data-toggle="modal" data-target="#addShoolTermModal"
										onclick="showAddTermModal()">
										<i class="fa fa-plus"></i>&nbsp;&nbsp;Add Term</abbr>
									</button>
								</div>
							</div>
							<table id="school_terms_list_tbl"
								class="table table-striped table-bordered table-responsive"
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Term Year</th>
										<th>Term</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody id="schools_terms_list_tbody">
								</tbody>
								<tfoot>
									<tr id="school_terms_tfoot_row_no_data">
										<td colspan="4" align="center">No Terms Found</td>
									</tr>
								</tfoot>
							</table>
						</div>
						<div id="school_tabs_info_questions" class="school_tabs_info">
							<div class="row">
								<div class="col-xs-12">
									<button type="button" class="btn"
										id="add-question-section-button" data-toggle="modal"
										data-target="#addSectionModal">
										<i class="fa fa-plus"></i>&nbsp;&nbsp;Add Section</abbr>
									</button>
								</div>
							</div>
							<div id="question_sections"></div>
						</div>
						<div id="school_tabs_info_documents" class="school_tabs_info">

							<div class="row">
								<div class="col-xs-12">
									<button type="button" class="btn" data-toggle="modal"
										data-target="#addShoolDocumentModal"
										onclick="showAddDocumentModal()">
										<i class="fa fa-plus"></i>&nbsp;&nbsp;Add Document</abbr>
									</button>
								</div>
							</div>
							<table id="school_documents_list_tbl"
								class="table table-striped table-bordered table-responsive"
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>S.No</th>
										<th>Document name</th>
										<th>Document URL</th>
										<th>Is Mandatory</th>
										<th>PDF</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody id="school_documents_list_tbody">
								</tbody>
								<tfoot>
									<tr id="school_documents_tfoot_row_no_data">
										<td colspan="7" align="center">No Documents Found</td>
									</tr>
								</tfoot>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="addShoolDocumentModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Document</h4>
					</div>
					<div class="modal-body">
						<form id="add_edit_document_form" role="form"
							class="form-horizontal">
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-document-name" class="control-label col-sm-4">Document
									Name</label>
								<div class="col-sm-8">
									<input type="text" name="form-document-name"
										placeholder="Document Name" class="form-control"
										id="form-document-name" required="true" value="" /> <input
										type="hidden" name="form_hidden_document_guid"
										id="form_hidden_document_guid" value="" />
								</div>
								<input id="form_hidden_schoolGuid" name="form_hidden_schoolGuid"
									type="hidden" />
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-document-url" class="control-label col-sm-4">Document
									URL</label>
								<div class="col-sm-8">
									<input type="text" name="form-document-url"
										placeholder="Document URL" class="form-control"
										id="form-document-url" required="true" value="" /> <input
										type="hidden" name="form-document-url" id=form-document-url
										value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-document-file" class="control-label col-sm-4">Choose
									Document <sup class="color_red ">*</sup>
								</label>
								<div class="col-sm-8">
									<input type="file" accept='.jpg,.png,.jpeg,.pdf'
										name="form-document-file" id="form-document-file"
										required="true">
								</div>

							</div>

							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-document-mandatory"
									class="control-label col-sm-4">Mandatory?</label>
								<div class="col-sm-8">
									<select name="form-document-mandatory"
										id="form-document-mandatory" required="true">
										<option value="">Select Is Mandatory</option>
										<option value="NO">NO</option>
										<option value="YES">YES</option>
										<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>

									</select>

								</div>

							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-applicable-to-degrees-documents"
									class="control-label col-sm-4">Applicable to Degrees</label>
								<div class="col-sm-8">
									<select name="form-applicable-to-degrees-documents"
										class="multiselect" multiple="multiple"
										id="form-applicable-to-degrees-documents">
									</select>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<input type="hidden" name="form-document-degree-list"
									id="form-document-degree-list" aria-hidden="true">
							</div>


						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" id="doc-upload-btn" class="btn btn-default"
							onclick="addSchoolDocument()">Save</button>
					</div>
				</div>
			</div>
		</div>


		<!-- Modal -->
		<div class="modal fade" id="addSectionModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Section</h4>
					</div>
					<div class="modal-body">
						<form id="add_edit_question_section_form" role="form"
							class="form-horizontal">
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-section-name" class="control-label col-sm-3">Section
									Name</label>
								<div class="col-sm-9">
									<input type="text" name="form-section-name"
										placeholder="Section Name" class="form-control"
										id="form-section-name" required="required" value="" /> <input
										type="hidden" name="form-hidden-section-guid"
										id="form-hidden-section-guid" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-seq-no" class="control-label col-sm-3">Sequence
									No</label>
								<div class="col-sm-9">
									<input type="text" name="form-seq-no" placeholder="Seq No"
										class="form-control" id="form-seq-no" required="required"
										value="" />
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addSection()">Save</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="addQuestionModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Question</h4>
					</div>
					<div class="modal-body">
						<form id="add_edit_question_form" role="form"
							class="form-horizontal">
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-section"
									class="control-label col-sm-3">Section </label>
								<div class="col-sm-9">
									<select name="form-question-section" id="form-question-section"
										required="required">
										<option value="">Select Section</option>
									</select> <input type="hidden" name="form-hidden-question-guid"
										id="form-hidden-question-guid" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12"
								id="parent_question_title_row">
								<label for="form-question-title" class="control-label col-sm-3">Parent
									Question Title</label>
								<div class="col-sm-9">
									<input type="hidden" name="parent_question_guid"
										id="parent_question_guid" value="" />
									<p id="parent_question_title"></p>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12"
								id="parent_question_answer_condition_row">
								<label for="form-question-title" class="control-label col-sm-3">Parent
									Question Answer Condition</label>
								<div class="col-sm-9"
									id='parent_question_answer_condition_row_content'></div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-title" class="control-label col-sm-3">Question
									Title</label>
								<div class="col-sm-9">
									<input type="text" name="form-question-title"
										placeholder="Question Title" class="form-control"
										id="form-question-title" required="required" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-long-text"
									class="control-label col-sm-3">Long Text</label>
								<div class="col-sm-9">
									<textarea type="text" name="form-question-long-text"
										placeholder="Question Long Text" class="form-control"
										id="form-question-long-text" required="required" value=""></textarea>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-type" class="control-label col-sm-3">Question
									Type</label>
								<div class="col-sm-9">
									<select name="form-question-type" id="form-question-type"
										required="required" onchange="showAddAnswerOptions()">
										<option value="">Select Question Type</option>
										<option value="Drop Down">Drop Down</option>
										<option value="Yes/No">Yes/No</option>
										<option value="Text">Text</option>
										<option value="Input">Input</option>
										<option value="Attachment">Attachment</option>
										<option value="Information">Information</option>

									</select>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12"
								id="answer_options_row"></div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-mandatory"
									class="control-label col-sm-3">Mandatory?</label>
								<div class="col-sm-9">
									<label class="radio-inline"><input type="radio"
										name="form-question-mandatory" value="Yes">Yes</label> <label
										class="radio-inline"><input type="radio"
										name="form-question-mandatory" value="No">No</label>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-inactive"
									class="control-label col-sm-3">Inactive?</label>
								<div class="col-sm-9">
									<label class="radio-inline"><input type="radio"
										name="form-question-inactive" value="Yes">Yes</label> <label
										class="radio-inline"><input type="radio"
										name="form-question-inactive" value="No">No</label>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-question-seq-no" class="control-label col-sm-3">Sequence
									No</label>
								<div class="col-sm-9">
									<input type="text" name="form-question-seq-no"
										placeholder="Seq No" class="form-control"
										id="form-question-seq-no" required="required" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-applicable-to-degrees"
									class="control-label col-sm-3">Applicable to Degrees</label>
								<div class="col-sm-9">
									<select class="multiselect" multiple="multiple"
										id="form-applicable-to-degrees">
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addQuestion()"  data-dismiss="modal">Save</button>
					</div>
				</div>
			</div>
		</div>


		<!-- Modal -->
		<div class="modal fade" id="addShoolDegreeModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Degree</h4>
					</div>
					<div class="modal-body">
						<form id="add_edit_degree_form" role="form"
							class="form-horizontal">
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-degree-offered" class="control-label col-sm-3">Degree
									Offered</label>
								<div class="col-sm-9">
									<input type="text" name="form-degree-offered"
										placeholder="Degree Name" class="form-control"
										id="form-degree-offered" required="required" value="" /> <input
										type="hidden" name="form-hidden-degree-guid"
										id="form-hidden-degree-guid" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-degree-dept" class="control-label col-sm-3">Degree
									Dept</label>
								<div class="col-sm-9">
									<input type="text" name="form-degree-dept"
										placeholder="Degree Department" class="form-control"
										id="form-degree-dept" required="required" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-contact-person" class="control-label col-sm-3">Contact
									Person</label>
								<div class="col-sm-9">
									<input type="text" name="form-contact-person"
										placeholder="Contact Person" class="form-control"
										id="form-contact-person" required="required" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-email" class="control-label col-sm-3">Email</label>
								<div class="col-sm-9">
									<input type="text" name="form-email"
										placeholder="Email Address" class="form-control"
										id="form-email" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-contact-no" class="control-label col-sm-3">Contact
									No</label>
								<div class="col-sm-9">
									<input type="text" name="form-contact-no"
										placeholder="Contact No" class="form-control"
										id="form-contact-no" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-address-line-1" class="control-label col-sm-3">Address
									Line 1</label>
								<div class="col-sm-9">
									<input type="text" name="form-address-line-1"
										placeholder="Address Line 1" class="form-control"
										id="form-address-line-1" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-address-line-2" class="control-label col-sm-3">Address
									Line 3</label>
								<div class="col-sm-9">
									<input type="text" name="form-address-line-2"
										placeholder="Address Line 2" class="form-control"
										id="form-address-line-2" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-address-line-3" class="control-label col-sm-3">Address
									Line 3</label>
								<div class="col-sm-9">
									<input type="text" name="form-address-line-3"
										placeholder="Address Line 3" class="form-control"
										id="form-address-line-3" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-city" class="control-label col-sm-3">City</label>
								<div class="col-sm-9">
									<input type="text" name="form-city" placeholder="City"
										class="form-control" id="form-city" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-state" class="control-label col-sm-3">State</label>
								<div class="col-sm-9">
									<input type="text" name="form-state" placeholder="State"
										class="form-control" id="form-state" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-zipcode" class="control-label col-sm-3">Zipcode</label>
								<div class="col-sm-9">
									<input type="text" name="form-zipcode" placeholder="Zipcode"
										class="form-control" id="form-zipcode" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-min-gre-score"
									class="control-label col-sm-3">Min GRE Score</label>
								<div class="col-sm-9">
									<input type="text" name="form-dept-min-gre-score"
										placeholder="Min GRE Score" class="form-control"
										id="form-dept-min-gre-score" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-min-toefl-score"
									class="control-label col-sm-3">Min TOEFL Score</label>
								<div class="col-sm-9">
									<input type="text" name="form-dept-min-toefl-score"
										placeholder="Min TOEFL Score" class="form-control"
										id="form-dept-min-toefl-score" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-min-gpa" class="control-label col-sm-3">Min
									GPA</label>
								<div class="col-sm-9">
									<input type="text" name="form-dept-min-gpa"
										placeholder="Min GPA" class="form-control"
										id="form-dept-min-gpa" value="" />
								</div>
							</div>

							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-min-gmat-score"
									class="control-label col-sm-3">Min GMAT Score</label>
								<div class="col-sm-9">
									<input type="text" name="form-dept-min-gpa"
										placeholder="Min GMAT Score" class="form-control"
										id="form-dept-min-gmat-score" value="" />
								</div>
							</div>

							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-gre-score-required"
									class="control-label col-sm-3">GRE Score Required ?</label>
								<div class="col-sm-9">
									<select name="form-dept-gre-score-required"
										placeholder="GRE Score Required ?"
										id="form-dept-gre-score-required">
										<option value="">Select GRE Score Required ?</option>
										<option value="NO">NO</option>
										<option value="YES">YES</option>
										<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
									</select>
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-dept-gre-score-score"
									class="control-label col-sm-3">GMAT Score Required ?</label>
								<div class="col-sm-9">
									<select name="form-dept-gmat-score-required"
										placeholder="GMAT Score Required ?"
										id="form-dept-gmat-score-required">
										<option value="">Select GMAT Score Required ?</option>
										<option value="NO">NO</option>
										<option value="YES">YES</option>
										<option value="YES FOR TRANSFER">YES FOR TRANSFER</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addSchoolDegree()">Save</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="addShoolTermModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Term</h4>
					</div>
					<div class="modal-body">
						<form id="add_edit_term_form" role="form" class="form-horizontal">
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-term-year" class="control-label col-sm-3">Term
									Year</label>
								<div class="col-sm-9">
									<input type="text" name="form-term-year"
										placeholder="Term Year" class="form-control"
										id="form-term-year" required="required" value="" /> <input
										type="hidden" name="form-hidden-term-guid"
										id="form-hidden-term-guid" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-term-name" class="control-label col-sm-3">Term
									Name </label>
								<div class="col-sm-9">
									<input type="text" name="form-term-name"
										placeholder="Term Name" class="form-control"
										id="form-term-name" required="required" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-app-submission-start-date"
									class="control-label col-sm-3">Appln Submission Start
									Date</label>
								<div class="col-sm-9">
									<input type="text" name="form-app-submission-start-date"
										placeholder="Application Submission Start Date"
										class="form-control" id="form-app-submission-start-date"
										value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-app-deadline-date"
									class="control-label col-sm-3">Appln Deadline Date</label>
								<div class="col-sm-9">
									<input type="text" name="form-app-deadline-date"
										placeholder="Application Deadline Date" class="form-control"
										id="form-app-deadline-date" value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-early-decision-deadline-date"
									class="control-label col-sm-3">Early Decision Deadline
									Date</label>
								<div class="col-sm-9">
									<input type="text" name="form-early-decision-deadline-date"
										placeholder="Earlyl Decision Deadline Date"
										class="form-control" id="form-early-decision-deadline-date"
										value="" />
								</div>
							</div>
							<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
								<label for="form-documents-deadline-date"
									class="control-label col-sm-3">Documents Deadline Date</label>
								<div class="col-sm-9">
									<input type="text" name="form-documents-deadline-date"
										placeholder="Documents Deadline Date" class="form-control"
										id="form-documents-deadline-date" value="" />
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addSchoolTerm()">Save</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>