var firstSchoolGuid = "";
var collegesApplied = 0;
var collegesAdded = 0;
var selectedSchoolGuid = "";
$(document).ready(function() {
	$("#error_message").hide();
	$(".nav_menu").removeClass("active");
	$("#my_colleges_menu").addClass("active");
	firstSchoolGuid = $("#hidden_school_guid").val();
	$("#colleges_completed_sections_count").text(collegesApplied);
	$("#colleges_total_sections_count").text(collegesAdded);
	$("#questions-form").validate();
	setCollegesTabHeight();
	getAllStudentSchools();
	getStudentWritingsError();
	getStudentProfileStatus();
	var profileCompleted = false;

});
$(window).resize(function() {
	setCollegesTabHeight();
});

function setCollegesTabHeight() {
	$("#school-names").css(
			"max-height",
			$(document).innerHeight()
					- $("#school-names-completed_uncompleted").innerHeight()
					- $(".app_header").innerHeight() - 2);

	$("#school-names").css(
			"min-height",
			$(document).innerHeight()
					- $("#school-names-completed_uncompleted").innerHeight()
					- $(".app_header").innerHeight() - 2);
}
function getAllStudentSchools() {
	$
			.ajax({
				type : "GET",
				url : "school/getStudentSchools",
				success : function(results) {
					processAllStudentSchools(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading Student Schools. Please Try Again"
							});
				}
			});
}
function processAllStudentSchools(results) {
	$("#error_message").hide();
	if (results.length > 0) {
		$("#school-content").fadeIn("slow");
		$("#colleges_total_sections_count").text(results.length);
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			if (i == 0 || i === 0) {
				if (firstSchoolGuid == '' || firstSchoolGuid == undefined) {
					firstSchoolGuid = eachRow['schoolGuid'];
				}
			}
			var statusHtml = "<i class='fa fa-check ' aria-hidden='true'></i>";
			applicationStatus = eachRow['applicationStatus'];
			if (applicationStatus == "Pending" || applicationStatus == ""
					|| applicationStatus == null
					|| applicationStatus == undefined) {
				statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>";
			} else if (applicationStatus == "Completed") {
				statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";
				collegesApplied++;
			}
			allRows += "<div class='each_school_name row' id='school_name_"
					+ eachRow['schoolGuid']
					+ "' onclick=\"getSchoolInfo('"
					+ eachRow['schoolGuid']
					+ "','"
					+ eachRow['applicationStatus']
					+ "')\"><div class='col-xs-10'>"
					+ eachRow['schoolName']
					+ "</div><div class='school_app_status' id='school_app_status_"
					+ eachRow['schoolGuid'] + "'>" + statusHtml
					+ "</div></div>";
		}
		$("#school-names").append(allRows);
		$("#no_colleges_found_list").hide();
		getSchoolInfo(firstSchoolGuid);
	} else {
		$("#no_colleges_found_list").show();
	}

	$("#colleges_completed_sections_count").text(collegesApplied);
}
var applicationStatus = "";
var eachRow = "";
var collegeinfoDocumentList = "";
function getSchoolInfo(schoolGuid, applicationStat) {
	applicationStatus = applicationStat;

	$("#error_message").hide();
	$("#school_tabs_info_documents").empty();
	selectedSchoolGuid = schoolGuid;
	getCollegeInfoDocuments();
	$(".each_school_name").removeClass("school_name_active");
	$("#school_name_" + schoolGuid).addClass("school_name_active");
	$.ajax({
		type : "GET",
		url : "school/getSchoolInfo/" + schoolGuid,
		success : function(results) {
			processSchoolInfo(results);
			$("#school_data").show();
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Info. Please Try Again"
			});
		}
	});
}
var allRequiresDocumentsFields = [];
var allRequiresDocumentsIsTransferFields = [];
var schoolQuestionsStatusGlobal = "";
var schoolDocumentsStatusGlobal = "";
var isTransferGlobal = "";
var allRequiresScore = [];
var allRequiresIsTransferScore = [];
var allWritingErrorMessage = "";
var schoolDataGlobal = "";
function processSchoolInfo(schoolData) {
	schoolDataGlobal = schoolData;
	allRequiresScore = [];
	allRequiresIsTransferScore = [];
	allRequiresDocumentsFields = [];
	allRequiresDocumentsIsTransferFields = [];
	$("#school_img").html(
			"<img src='images/dummy_school.jpg' class='img-responsive'/>");
	var schoolName = schoolData["schoolName"];
	$("#school_name_col").html(schoolName);
	if (schoolData["schoolInfo"] != "" && schoolData["schoolInfo"] != undefined
			&& schoolData["schoolInfo"] != null) {
		$("#school_info_content").show();
		$("#school_info_content").html(schoolData["schoolInfo"]);
	} else {
		$("#school_info_content").hide();
	}

	var phoneNumber = (schoolData["phoneNumber"] == null) ? "NA"
			: schoolData["phoneNumber"];
	var emailAddress = (schoolData["emailAddress"] == null) ? "NA"
			: schoolData["emailAddress"];
	var website = (schoolData["website"] == null) ? "NA"
			: schoolData["website"];
	var addressLineOne = (schoolData["addressLineOne"] == null) ? ""
			: schoolData["addressLineOne"];
	var addressLineTwo = (schoolData["addressLineTwo"] == null) ? ""
			: schoolData["addressLineTwo"];
	var city = (schoolData["city"] == null) ? "" : schoolData["city"];
	var state = (schoolData["state"] == null) ? "" : schoolData["state"];
	var zipCode = (schoolData["zipCode"] == null) ? "" : schoolData["zipCode"];
	var contactInfoHtml = "<p>" + schoolName
			+ "</p><p> Website : <a href='http://" + website
			+ "' target='_blank'>" + website + " </a></p><p> Email : "
			+ emailAddress + "</p><p> Contact Number : " + phoneNumber
			+ "</p><p>" + addressLineOne + "</p><p>" + addressLineTwo
			+ "</p><p>" + city + ", " + state + " - " + zipCode + "</p>";

	$("#school_contact_info_content").html(contactInfoHtml);
	$("#form-preferred_start_term").empty().append(
			"<option value=''>Select Preferred start term</option>");
	$("#school_terms_info_content").empty();
	var schoolTerms = schoolData["schoolTerms"];
	schoolTerms = removeDuplicatesSchoolTerms(schoolTerms);
	var eachTermTableHtml = '';
	if (schoolTerms.length > 0) {
		for (var i = 0; i < schoolTerms.length; i++) {
			var eachTerm = schoolTerms[i];
			var termYear = (eachTerm["termYear"] == null) ? "NA"
					: eachTerm["termYear"];
			var term = (eachTerm["term"] == null) ? "NA" : eachTerm["term"];
			var schoolTermGuid = eachTerm["schoolTermGuid"];
			var applicationDeadLineDate = convertDate(eachTerm["applicationDeadLineDate"]);
			var applicationSubmissionStartDate = convertDate(eachTerm["applicationSubmissionStartDate"]);
			var documentsDeadLineDate = convertDate(eachTerm["documentsDeadLineDate"]);
			var earlyDecisionDeadLineDate = convertDate(eachTerm["earlyDecisionDeadLineDate"]);
			var eachTermHtml = "<option value='" + schoolTermGuid + "'>"
					+ termYear + "-" + term + "</option>";
			$("#form-preferred_start_term").append(eachTermHtml);

			eachTermTableHtml += ("<tr><td>" + termYear + "</td><td >" + term
					+ "</td>" + "<td >" + applicationDeadLineDate
					+ "</td><td >" + applicationSubmissionStartDate
					+ "</td><td >" + documentsDeadLineDate + "</td><td >"
					+ earlyDecisionDeadLineDate + "</td></tr>");
		}
		$("#school_terms_info_content")
				.html(
						"<table><tr><th >Term Year</th><th >Term</th><th >Application End Date</th><th > Application Submission Start Date</th><th >Document End Date</th><th >Early Decision End Date</th></tr>"
								+ eachTermTableHtml + "<table>");

	}
	var studentApplicationResponse = schoolData["studentApplicationResponse"];
	isTransferGlobal = studentApplicationResponse["isTransferStudent"];
	var schoolDegreeGuid = studentApplicationResponse["schoolDegreeGuid"];

	$("#school_degree_info_content").empty();
	$("#form-preferred_start_degree").empty().append(
			"<option value=''>Select Preferred Degree</option>");
	var schoolDegrees = schoolData["schoolDegrees"];
	schoolDegrees = removeDuplicatesSchoolDegrees(schoolDegrees,
			"degreeOffered");

	if (schoolDegrees.length > 0) {
		for (var i = 0; i < schoolDegrees.length; i++) {
			var eachDegree = schoolDegrees[i];
			var degreeOffered = eachDegree["degreeOffered"];
			var schoolDegreeGuids = eachDegree["schoolDegreeGuid"];
			if (schoolDegreeGuid == eachDegree["schoolDegreeGuid"]) {
				var GREScore = (eachDegree["requiresGREScore"] == null) ? "NA"
						: eachDegree["requiresGREScore"];
				var GMATScore = (eachDegree["requiresGMATScore"] == null) ? "NA"
						: eachDegree["requiresGMATScore"];
				if (GREScore == "YES") {
					allRequiresScore.push("GRE SCORE");
				}
				if (GREScore == "YES FOR TRANSFER") {
					allRequiresIsTransferScore.push("GRE SCORE");
				}

				if (GMATScore == "YES") {
					allRequiresScore.push("GMAT SCORE");
				}
				if (GMATScore == "YES FOR TRANSFER") {
					allRequiresIsTransferScore.push("GMAT SCORE");
				}
			}

			var eachDegreeHtml = "<option value='" + schoolDegreeGuids + "'>"
					+ degreeOffered + "</option>";
			$("#form-preferred_start_degree").append(eachDegreeHtml);
			var allSchoolDegreeHtml = "<p>" + degreeOffered + "</p>";
			$("#school_degree_info_content").append(allSchoolDegreeHtml);
		}
	}
	applicationStatus = "";
	applicationStatus = schoolData["applicationStatus"]
	var internationAppFees = (schoolData["internationAppFees"] == null) ? "NA"
			: "$" + schoolData["internationAppFees"];
	var internationCredentialEvalFees = (schoolData["internationCredentialEvalFees"] == null) ? "NA"
			: "$" + schoolData["internationCredentialEvalFees"];
	var mailingFee = (schoolData["mailingFee"] == null) ? "NA" : "$"
			+ schoolData["mailingFee"];
	var transcripts = (schoolData["transcriptsRequires"] == null) ? "NA"
			: schoolData["transcriptsRequires"];
	var recLetters = (schoolData["recLettersRequires"] == null) ? "NA"
			: schoolData["recLettersRequires"];
	var sop = (schoolData["sopRequires"] == null) ? "NA"
			: schoolData["sopRequires"];
	var solvCERT = (schoolData["solvCERTRequires"] == null) ? "NA"
			: schoolData["solvCERTRequires"];
	var resume = (schoolData["resumeRequires"] == null) ? "NA"
			: schoolData["resumeRequires"];
	var passport = (schoolData["requiresPassport"] == null) ? "NA"
			: schoolData["requiresPassport"];
	var GREScore = (schoolData["minimumGREScore"] == null) ? "NA"
			: schoolData["minimumGREScore"];
	var GMATScore = (schoolData["minimumGMATcore"] == null) ? "NA"
			: schoolData["minimumGMATcore"];
	var TOEFLScore = (schoolData["minimumTOEFLScore"] == null) ? "NA"
			: schoolData["minimumTOEFLScore"];
	var requiresEAD = (schoolData["requiresEAD"] == null) ? "NA"
			: schoolData["requiresEAD"];
	var requiresF1Visa = (schoolData["requiresF1Visa"] == null) ? "NA"
			: schoolData["requiresF1Visa"];
	var requiresI20 = (schoolData["requiresI20"] == null) ? "NA"
			: schoolData["requiresI20"];
	var requiresTOFELScore = (schoolData["requiresTOFELScore"] == null) ? "NA"
			: schoolData["requiresTOFELScore"];
	var GPA = (schoolData["minimumGPA"] == null) ? "NA"
			: schoolData["minimumGPA"];
	var greScoreSubmissionSchoolCode = (schoolData["greScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["greScoreSubmissionSchoolCode"];
	var gmatScoreSubmissionSchoolCode = (schoolData["gmatScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["gmatScoreSubmissionSchoolCode"];
	var toeflScoreSubmissionSchoolCode = (schoolData["toeflScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["toeflScoreSubmissionSchoolCode"];
	var schoolCode = schoolData["schoolId"];
	if (sop == "YES") {
		getStudentWritingsError();
	}
	if (sop == "YES" && isTransferGlobal == "X") {
		getStudentWritingsError();
	}
	if (transcripts == "YES") {
		allRequiresDocumentsFields.push("TRANSCRIPTS");
	}
	if (transcripts == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("TRANSCRIPTS");
	}

	if (recLetters == "YES") {
		allRequiresDocumentsFields.push("RECOMMENDATION LETTER");
	}
	if (recLetters == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("RECOMMENDATION LETTER");
	}

	if (solvCERT == "YES") {
		allRequiresDocumentsFields.push("SOLVENCY CERTIFICATE");
	}
	if (solvCERT == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("SOLVENCY CERTIFICATE");
	}

	if (resume == "YES") {
		allRequiresDocumentsFields.push("RESUME");
	}
	if (resume == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("RESUME");
	}

	if (passport == "YES") {
		allRequiresDocumentsFields.push("PASSPORT");
	}
	if (passport == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("PASSPORT");
	}

	if (requiresEAD == "YES") {
		allRequiresDocumentsFields.push("EAD");
	}

	if (requiresEAD == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("EAD");
	}

	if (requiresF1Visa == "YES") {
		allRequiresDocumentsFields.push("F1-VISA");
	}
	if (requiresF1Visa == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("F1-VISA");
	}

	if (requiresI20 == "YES") {
		allRequiresDocumentsFields.push("I-20");
	}
	if (requiresI20 == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("I-20");
	}
	if (requiresTOFELScore == "YES") {
		allRequiresDocumentsFields.push("TOEFL SCORE");
	}
	if (requiresTOFELScore == "YES FOR TRANSFER") {
		allRequiresDocumentsIsTransferFields.push("TOEFL SCORE");
	}
	var requirenmentFeesHtml = "<label class='sub_header'>Application Fees</label>"
			+ "<p> International App Fees : "
			+ internationAppFees
			+ "</p>"
			+ "<p> International Credential Eval Fees : "
			+ internationCredentialEvalFees + "</p>";

	var gradTuitionFeePerCredit = (schoolData["gradTuitionFeePerCredit"] == null) ? "NA"
			: "$" + schoolData["gradTuitionFeePerCredit"];
	var gradCreditsRequired = (schoolData["gradCreditsRequired"] == null) ? "NA"
			: schoolData["gradCreditsRequired"];
	$("#gradTuitionFeePerCredit").text(gradTuitionFeePerCredit);
	$("#gradCreditsRequired").text(gradCreditsRequired);
	$("#tuitionFeesLink").text(tuitionFeesLink);
	var tuitionFeesLink = schoolData["tuitionFeesLink"];
	var tuitionFeesLinkHtml = "<a href='" + tuitionFeesLink
			+ "' target='_blank'> Tuition & Fees Link</a>";
	$("#tuitionFeesLink").html(tuitionFeesLinkHtml);
	;
	var applicationCheckListLink = (schoolData["applicationCheckListLink"] == null) ? "NA"
			: schoolData["applicationCheckListLink"];
	var applicationCheckListLinkHtml = "<a href='" + applicationCheckListLink
			+ "' target='_blank'> Application Check List Link</a>";
	$("#applicationCheckListLink").html(applicationCheckListLinkHtml);

	var mailingFeesHtml = "<p>Mailing Fees : " + mailingFee + "</p>"
			+ "<p>Grad Tuition Fee Per Credit : " + gradTuitionFeePerCredit
			+ "</p>" + "<p>Grad Credits Required : " + gradCreditsRequired
			+ "</p>" + "<p>" + tuitionFeesLinkHtml + "</p>";

	var requireDocumentHtml = "<label class='sub_header'>Documents</label>"
			+ "<p> Transcript : "
			+ transcripts
			+ "</p>"
			+ "<p> REC Letter : "
			+ recLetters
			+ "</p>"
			+ "<p> SOP : "
			+ sop
			+ "</p>"
			+ "<p> SOLV Certificate : "
			+ solvCERT
			+ "</p>"
			+ "<p> Resume : "
			+ resume
			+ "</p>"
			+ "<p> Passport : "
			+ passport
			+ "</p>"
			+ "<p> TOEFL : "
			+ requiresTOFELScore
			+ "</p>"
			+ "<p> EAD : "
			+ requiresEAD
			+ "</p>"
			+ "<p> I-20 : "
			+ requiresI20
			+ "</p>"
			+ "<p> F-1 VISA : "
			+ requiresF1Visa
			+ "</p>"
			+ collegeinfoDocumentList
			+ "<p>"
			+ applicationCheckListLinkHtml + "</p>";
	var minimumScorehtml = "<label class='sub_header'>Minimum Score</label>"
			+ "<p> GRE Score : " + GREScore + "</p>" + "<p> GMAT Score : "
			+ GMATScore + "</p>" + "<p> TOEFL Score : " + TOEFLScore + "</p>"
			+ "<p> GPA Score : " + GPA + "</p>";
	var submissionScoreHtml = "<label class='sub_header'>Submission School Code</label>"
			+ "<p> GRE Code : "
			+ greScoreSubmissionSchoolCode
			+ "</p>"
			+ "<p> GMAT Code : "
			+ gmatScoreSubmissionSchoolCode
			+ "</p>"
			+ "<p> TOEFL Code : " + toeflScoreSubmissionSchoolCode + "</p>";
	$("#school_requirements_info_content").html(
			requirenmentFeesHtml + mailingFeesHtml + requireDocumentHtml
					+ minimumScorehtml + submissionScoreHtml);
	var schoolTermGuid = studentApplicationResponse["schoolTermGuid"];
	var schoolQuestionsStatus = "";
	var schoolDocumentsStatus = "";
	var schoolApplicationGuid = "";
	var acceptTermsAndConditions = "";
	if (studentApplicationResponse != null
			&& studentApplicationResponse != undefined) {
		schoolQuestionsStatus = studentApplicationResponse["schoolQuestionsStatus"];
		schoolDocumentsStatus = studentApplicationResponse["schoolDocumentsStatus"];

		if (studentApplicationResponse["schoolDocumentsStatus"] == null
				|| studentApplicationResponse["schoolDocumentsStatus"] == undefined) {
			schoolDocumentsStatus = determineStudentSchoolDocumentStatus();

		}

		schoolApplicationGuid = studentApplicationResponse["schoolApplicationGuid"];
		acceptTermsAndConditions = studentApplicationResponse["acceptTermsAndConditions"];
	}
	getSchoolAllDocuments();
	schoolQuestionsStatusGlobal = schoolQuestionsStatus;
	schoolDocumentsStatusGlobal = schoolDocumentsStatus;
	$("#school_application_guid").val(schoolApplicationGuid);

	if (schoolQuestionsStatus == "Completed") {
		var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
		$("#college_questions_status").html(statusHtml);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;";
		$("#college_questions_status").html(statusHtml);
	}
	if (schoolDocumentsStatus == "Completed") {
		var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
		$("#college_documents_status").html(statusHtml);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;";
		$("#college_documents_status").html(statusHtml);

	}

	$("#form-preferred_start_term").val(schoolTermGuid);
	$("#form-preferred_start_degree").val(schoolDegreeGuid);

	if (acceptTermsAndConditions == "X") {
		var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
		$("#accept_terms_conditions_check").prop('checked', true);
		$("#accept_terms_conditions_check").prop("disabled", true);
		$("#submit_application_info").prop("disabled", true);
		$("#review_app_status").html(statusHtml);
		$("#download_application_info").show();

	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;";
		$("#accept_terms_conditions_check").prop("disabled", false);
		$("#review_app_status").html(statusHtml);
		$("#accept_terms_conditions_check").prop('checked', false);
		$("#submit_application_info").prop("disabled", false);
		$("#download_application_info").hide();

	}
	$("#form-preferred_start_term").val(schoolTermGuid);
	$("#form-preferred_start_degree").val(schoolDegreeGuid);
	gotoSchoolTab('college_info_school_tab');
}

function getCollegeInfoDocuments() {

	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "student/getSchoolDocuments/" + selectedSchoolGuid,
				dataType : 'json',
				success : function(results) {

					processCollegeInfoDocuments(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Documents.Please Try Again"
							});
				}
			});
}

function processCollegeInfoDocuments(results) {
	collegeinfoDocumentList = "";
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();

	var eachSchoolDegreeDocuments = [];

	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachSchoolDegreeDocument = results[i];

			var degreeList = eachSchoolDegreeDocument["degreeList"];
			if (schoolDegreeGuid == "") {

				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			} else {
				if (degreeList != null || degreeList != null) {
					if (degreeList.indexOf(schoolDegreeGuid) > -1) {
						eachSchoolDegreeDocuments
								.push(eachSchoolDegreeDocument);
					}
				}
				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			}
		}
	}

	if (eachSchoolDegreeDocuments.length > 0) {

		for (var i = 0; i < eachSchoolDegreeDocuments.length; i++) {

			eachRow = eachSchoolDegreeDocuments[i];

			collegeinfoDocumentList += "<p><a href='" + eachRow["documentPath"]
					+ "' target='_blank'>" + eachRow["documentName"]
					+ "</a>&nbsp;:&nbsp;" + eachRow["isMandatory"] + "</p>";

		}
	}

}

function gotoSchoolTab(selectedTabId) {
	$(".school_tabs_info").hide();
	$(".school_tab").removeClass("selected_school_tab");
	$("#" + selectedTabId).addClass("selected_school_tab");

	if (selectedTabId == 'college_info_school_tab') {
		$("#school_tabs_info_college").show();
	} else if (selectedTabId == 'questions_school_tab') {

		loadAllSchoolSectionQuestions();

		$("#school_tabs_info_questions").show();

		if (applicationStatus == "Completed") {
			$("#isInitialStudent").prop("disabled", true);
			$("#isTransferStudent").prop("disabled", true);
			$("#form-preferred_start_term").prop("disabled", true);
			$("#form-preferred_start_degree").prop("disabled", true);
			$("#save-questions-info").prop('disabled', true);
			$("#question_sections").find("input,button,textarea,select").prop(
					"disabled", true);

		} else {
			$("#isInitialStudent").prop("disabled", false);
			$("#isTransferStudent").prop("disabled", false);
			$("#form-preferred_start_term").prop("disabled", false);
			$("#form-preferred_start_degree").prop("disabled", false);
			$("#save-questions-info").prop('disabled', false);
			$("#question_sections").find("input,button,textarea,select").prop(
					"disabled", false);

		}
	} else if (selectedTabId == 'documents_school_tab') {

		getSchoolDocuments();
		$("#school_tabs_info_documents").show();
	} else if (selectedTabId == 'review_school_tab') {
		$("#school_tabs_info_review_submit").show();
	}
}

function generatePDFPacket() {
	var succeed = false;
	var schoolApplicationGuid = $("#school_application_guid").val();
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "school/generatePdfPacket/" + schoolApplicationGuid,
		success : function(data) {
			var result = data;
			if (result == '' || result == undefined || result == null) {
				succeed = false;
				$("#download_application_info").hide();
			} else {
				succeed = true;
				downloadDocument(result);
				$("#download_application_info").show();
				$("#download_application_info").prop("disabled", false);

			}
		},
		error : function(e) {
			$("#download_application_info").hide();
		}
	});
	return succeed;
}

function getApplicationPDF(buttonId) {
	var schoolApplicationGuid = $("#school_application_guid").val();
	$
			.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "school/getApplicationPDF/" + schoolApplicationGuid,
				success : function(documentPath) {
					if (documentPath == '' || documentPath == undefined
							|| documentPath == null) {
						$
								.alert({
									content : "Error While Loading Application PDF. Please Try Again"
								});
						$("#" + buttonId).prop("disabled", false);

					} else {
						downloadDocument(documentPath);
					}
				},
				error : function(e) {
					$("#" + buttonId).prop("disabled", false);
					$
							.alert({
								content : "Error While Loading School PDF. Please Try Again"
							});
				}
			});
}

function loadSchoolQuestionBySchoolDergees() {
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();
}
function loadAllSchoolSectionQuestions() {
	$
			.ajax({
				type : "GET",
				url : "school/getSchoolSectionQuestions/" + selectedSchoolGuid,
				success : function(results) {
					processAllSchoolSectionQuestions(results);
					bindQuestionAnswers();
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Questions. Please Try Again"
							});
				}
			});

}

var allSectionQuestionsList = [];
function processAllSchoolSectionQuestions(results) {
	var isTransferStudentScheck = false;
	allSectionQuestionsList = [];
	$("#isInitialStudent").prop('checked', true);
	if (results.length > 0) {
		var sectionsHtml = "";
		for (var i = 0; i < results.length; i++) {
			var eachSection = results[i];
			var schoolQuestionSectionGuid = eachSection["schoolQuestionSectionGuid"];
			var sectionName = eachSection["sectionName"];

			var sectionQuestions = eachSection["questions"];
			var isTransferStudent = eachSection["isTransferStudent"];
			if (isTransferStudent = !"" && isTransferStudent == "X") {
				$("#isInitialStudent").prop('checked', false);
				$('#isTransferStudent').prop('checked', true);
			}
			if (schoolQuestionSectionGuid != null && sectionName != null
					&& sectionQuestions != null) {
				var eachSectionQuestionsData = {
					"schoolQuestionSectionGuid" : schoolQuestionSectionGuid,
					"sectionQuestions" : sectionQuestions
				};
				allSectionQuestionsList.push(eachSectionQuestionsData);

				var eachQuestionsHtml = getSectionQuestionsHtml(schoolQuestionSectionGuid);
				sectionsHtml += "<div class='each_question_section' id='each_question_section' onclick=\"showSectionQuestions('"
						+ schoolQuestionSectionGuid
						+ "')\"><i id='close_"
						+ schoolQuestionSectionGuid
						+ "' class='fa fa-arrow-right' aria-hidden='true'></i>"
						+ sectionName
						+ "</div><div class='each_section_questions' id='"
						+ schoolQuestionSectionGuid
						+ "_questions_list'>"
						+ eachQuestionsHtml + "</div>";
			}
		}

		$("#question_sections").empty().html(sectionsHtml);
		expandQuestions(results);
	}

}
function expandQuestions(results) {
	if (results.length > 0) {
		var sectionsHtml = "";
		for (var i = 0; i < results.length; i++) {
			var eachSection = results[i];
			var schoolQuestionSectionGuid = eachSection["schoolQuestionSectionGuid"];
			showSectionQuestions(schoolQuestionSectionGuid);
		}
	}
}
function showSectionQuestions(schoolQuestionSectionGuid) {
	$("#" + schoolQuestionSectionGuid + "_questions_list").slideToggle();
}
var sectionQuestionsList = [];
function getSectionQuestionsHtml(schoolQuestionSectionGuid) {
	sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var questionsHtml = "";
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();

	if (sectionQuestionsList.length > 0) {
		for (var i = 0; i < sectionQuestionsList.length; i++) {
			var eachQuestion = sectionQuestionsList[i];
			var degreeListGuid = eachQuestion["degreeList"];
			if (schoolDegreeGuid == "") {
				if (degreeListGuid == "ALL") {
					var eachQuestionByDegrees = eachQuestion;
					var eachQuestionsHtml = getQuestionData(
							schoolQuestionSectionGuid, eachQuestionByDegrees);
					questionsHtml += eachQuestionsHtml;
				}
			} else {
				if (degreeListGuid != null || degreeListGuid != undefined) {
					if (degreeListGuid.indexOf(schoolDegreeGuid) > -1) {
						var eachQuestionByDegrees = eachQuestion;
						var eachQuestionsHtml = getQuestionData(
								schoolQuestionSectionGuid,
								eachQuestionByDegrees);
						questionsHtml += eachQuestionsHtml;
					} else if (degreeListGuid == "ALL") {
						var eachQuestionByDegrees = eachQuestion;
						var eachQuestionsHtml = getQuestionData(
								schoolQuestionSectionGuid,
								eachQuestionByDegrees);
						questionsHtml += eachQuestionsHtml;
					}
				}
			}
		}
	}
	return questionsHtml;
}

function getSectionQuestions(inputSchoolQuestionSectionGuid) {
	var sectionQuestionsListNew = [];
	if (allSectionQuestionsList.length > 0) {
		for (var i = 0; i < allSectionQuestionsList.length; i++) {
			var eachSectionQuestions = allSectionQuestionsList[i];
			if (inputSchoolQuestionSectionGuid == eachSectionQuestions.schoolQuestionSectionGuid) {
				sectionQuestionsListNew = eachSectionQuestions.sectionQuestions;
				break;
			}
		}
	}
	return sectionQuestionsListNew;
}

function getSectionQuestionData(sectionQuestionsList, inputSchoolQuestionGuid) {
	var questionData = {};
	if (sectionQuestionsList.length > 0) {
		for (var i = 0; i < sectionQuestionsList.length; i++) {
			var eachSectionQuestionData = sectionQuestionsList[i];
			if (inputSchoolQuestionGuid == eachSectionQuestionData.schoolQuestionGuid) {
				questionData = eachSectionQuestionData;
				break;
			}
		}
	}
	return questionData;
}

function getQuestionData(schoolQuestionSectionGuid, eachQuestions) {
	var questionsHtml = "";
	var att = "";
	if (applicationStatus == "Completed") {
		att = "disabled='disabled'";
	} else {
		att = "enabled='enabled'";
	}
	var schoolQuestionGuid = eachQuestions["schoolQuestionGuid"];
	var questionTitle = eachQuestions["questionTitle"];
	var questionType = eachQuestions["questionType"];
	var answerOptions = eachQuestions["answerOptions"];
	var questionMandatory = eachQuestions["questionMandatory"];
	var questionAnswerGuid = eachQuestions["questionAnswerGuid"];
	var questionLongText = eachQuestions["questionLongText"];
	var requiredHtml = "";
	var requiredAstricHtml = "";
	if (questionMandatory == "Yes") {
		requiredHtml = "required='required'";
		requiredAstricHtml = "<sup class='color_red'>*</sup>";
	}

	var dropDownHtml = "<select name='question_" + schoolQuestionGuid + "_"
			+ schoolQuestionSectionGuid + "_" + questionAnswerGuid
			+ "' id='question_" + schoolQuestionGuid + "_"
			+ schoolQuestionSectionGuid + "_" + questionAnswerGuid + "' " + att
			+ " onchange=\"loadChildQuestions('" + schoolQuestionSectionGuid
			+ "','" + schoolQuestionGuid + "','" + questionAnswerGuid + "')\" "
			+ requiredHtml + "><option value=''>Select " + questionTitle
			+ "</option>";

	var questionAnswerText = "";

	if (questionType == "Drop Down") {
		if (answerOptions.length > 0) {
			for (var j = 0; j < answerOptions.length; j++) {
				var eachAnswerOption = answerOptions[j];
				var optionName = eachAnswerOption["answer"];
				var schoolQuestionAnswerOptionGuid = eachAnswerOption["schoolQuestionAnswerOptionGuid"];
				dropDownHtml += "<option value='"
						+ schoolQuestionAnswerOptionGuid + "'>" + optionName
						+ "</option>";
			}
		}
		dropDownHtml += "</select>";
		questionAnswerText = dropDownHtml;
	} else if (questionType == "Yes/No") {
		questionAnswerText = "<div><label class='radio-inline'><input type='radio'	name='question_"
				+ schoolQuestionGuid
				+ "_"
				+ schoolQuestionSectionGuid
				+ "_"
				+ questionAnswerGuid
				+ "' value='Yes' "
				+ att
				+ " onclick=\"loadChildQuestions('"
				+ schoolQuestionSectionGuid
				+ "','"
				+ schoolQuestionGuid
				+ "','"
				+ questionAnswerGuid
				+ "')\" "
				+ requiredHtml
				+ ">Yes</label> <label class='radio-inline'><input type='radio' name='question_"
				+ schoolQuestionGuid
				+ "_"
				+ schoolQuestionSectionGuid
				+ "_"
				+ questionAnswerGuid
				+ "' value='No' "
				+ att
				+ " onclick=\"loadChildQuestions('"
				+ schoolQuestionSectionGuid
				+ "','"
				+ schoolQuestionGuid
				+ "','"
				+ questionAnswerGuid
				+ "')\" " + requiredHtml + ">No</label></div>";
	} else if (questionType == "Text") {
		questionAnswerText = "<div><textarea name='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' " + att + " placeholder='"
				+ questionTitle + "' class='form-control' id='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' value='' " + requiredHtml
				+ "></textarea></div>";
	} else if (questionType == "Input") {
		questionAnswerText = "<div><input type='text' name='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' " + att + " placeholder='"
				+ questionTitle + "' class='form-control' id='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' value='' " + requiredHtml
				+ "/></div>";
	}

	else if (questionType == "Attachment") {
		questionAnswerText = "<div class='col-lg-6 col-md-6 col-sm-12 col-xs-12'><input type='file' name='question_"
				+ schoolQuestionGuid
				+ "_"
				+ questionAnswerGuid
				+ "' placeholder='"
				+ questionTitle
				+ "' class='form-control' id='question_"
				+ schoolQuestionGuid
				+ "_"
				+ schoolQuestionSectionGuid
				+ "_"
				+ questionAnswerGuid
				+ "' "
				+ att
				+ " value='' "
				+ requiredHtml

				+ "/></div><div class='col-lg-6 col-md-6 col-sm-12 col-xs-12' id='attachment_question_answer_"
				+ schoolQuestionGuid
				+ "_"
				+ schoolQuestionSectionGuid
				+ "_"
				+ questionAnswerGuid + "'></div>";
	}

	if (questionType == "Information") {
		questionsHtml += "<div class='each_question' id='each_question_div_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "_" + questionType
				+ "'><div class='form-group'>";
		questionsHtml += "<div class='each_question_answer_text' id='each_question_long_text'>"
				+ questionLongText + "</div></div>";
	} else {
		questionsHtml += "<div class='each_question' id='each_question_div_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "_" + questionType
				+ "'><div class='form-group'><div class='each_question_title'>"
				+ questionTitle + requiredAstricHtml + "</div>";
		questionsHtml += "<div class='each_question_answer_text'>"
				+ questionAnswerText + "</div></div>";
		questionsHtml += "</div><div id='each_question_sub_questions_"
				+ schoolQuestionGuid
				+ "'></div><div id='each_question_tab_questions_"
				+ schoolQuestionGuid + "'></div>";

	}
	return questionsHtml;
}
function loadChildQuestions(schoolQuestionSectionGuid, inputSchoolQuestionGuid,
		questionAnswerGuid) {

	var sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var eachQuestion = getSectionQuestionData(sectionQuestionsList,
			inputSchoolQuestionGuid);

	var schoolQuestionGuid = eachQuestion["schoolQuestionGuid"];
	var questionType = eachQuestion["questionType"];

	var childQuestions = eachQuestion["childQuestions"];
	var tableQuestions = eachQuestion["tableQuestions"];

	var selectedParentQuestionAnswerCondition = "";
	if (questionType == "Drop Down") {
		selectedParentQuestionAnswerCondition = $(
				"#question_" + schoolQuestionGuid + "_"
						+ schoolQuestionSectionGuid + "_" + questionAnswerGuid)
				.val();
	} else if (questionType == "Yes/No") {
		selectedParentQuestionAnswerCondition = $(
				'input[name=question_' + schoolQuestionGuid + '_'
						+ schoolQuestionSectionGuid + '_' + questionAnswerGuid
						+ ']:checked').val();
	}

	$("#each_question_sub_questions_" + schoolQuestionGuid).empty();
	$("#each_question_tab_questions_" + schoolQuestionGuid).empty();

	if (childQuestions.length > 0) {
		var subQuestionsHtml = "";
		var eachSubQuestionHtml = "";
		for (var i = 0; i < childQuestions.length; i++) {
			var eachSubQuestionData = childQuestions[i];
			var parentQuestionAnswerCondition = eachSubQuestionData["parentQuestionAnswerCondition"];
			if (parentQuestionAnswerCondition == selectedParentQuestionAnswerCondition) {
				eachSubQuestionHtml = getQuestionData(
						schoolQuestionSectionGuid, eachSubQuestionData);
				$("#each_question_sub_questions_" + schoolQuestionGuid).append(
						eachSubQuestionHtml);
			}
		}
	}
	if (tableQuestions.length > 0) {
		loadTableQuestions(eachQuestion, schoolQuestionSectionGuid);
	}

}

function loadTableQuestions(eachQuestion, schoolQuestionSectionGuid) {
	addTableQuestion(eachQuestion, schoolQuestionSectionGuid);
}

function addMoreTableQuestionRow(schoolQuestionSectionGuid, schoolQuestionGuid) {
	var htmlContent = "<div class='add_another' id='add_another_"
			+ schoolQuestionGuid + "' onclick=\"addMoreTableQuestion('"
			+ schoolQuestionSectionGuid + "','" + schoolQuestionGuid
			+ "')\">Add Another</div>";
	$("#each_question_tab_questions_" + schoolQuestionGuid).append(htmlContent);
}

function addMoreTableQuestion(schoolQuestionSectionGuid, schoolQuestionGuid) {
	var sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var eachQuestion = getSectionQuestionData(sectionQuestionsList,
			schoolQuestionGuid);
	$("#add_another_" + schoolQuestionGuid).empty();
	$("#add_another_" + schoolQuestionGuid).remove();
	addTableQuestion(eachQuestion, schoolQuestionSectionGuid);
}
function closeTableQuestion(schoolQuestionGuid, questionTblNo) {
	$(
			"#each_question_table_question_" + schoolQuestionGuid + "_"
					+ questionTblNo).empty();
	$(
			"#each_question_table_question_" + schoolQuestionGuid + "_"
					+ questionTblNo).remove();
}
function addTableQuestion(eachQuestion, schoolQuestionSectionGuid) {
	var schoolQuestionGuid = eachQuestion["schoolQuestionGuid"];
	var tableQuestions = eachQuestion["tableQuestions"];
	var questionTblNo = $('.each_question_table_question').length;
	if (tableQuestions.length > 0) {
		var subQuestionsHtml = "<div class='each_question_table_question'  id='each_question_table_question_"
				+ schoolQuestionGuid
				+ "_"
				+ questionTblNo
				+ "'><div class='close_tbl_question' onclick=\"closeTableQuestion('"
				+ schoolQuestionGuid + "','" + questionTblNo + "')\">X</div>";
		for (var i = 0; i < tableQuestions.length; i++) {
			var eachSubQuestionData = tableQuestions[i];
			var eachSubQuestionHtml = getQuestionData(
					schoolQuestionSectionGuid, eachSubQuestionData);
			subQuestionsHtml += eachSubQuestionHtml;
		}
		subQuestionsHtml += "</div>";
		$("#each_question_tab_questions_" + schoolQuestionGuid).append(
				subQuestionsHtml);
		addMoreTableQuestionRow(schoolQuestionSectionGuid, schoolQuestionGuid);
	}
}

function getChildQuestions(schoolQuestionSectionGuid, schoolQuestionGuid) {
	var sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var childQuestions = [];
	if (sectionQuestionsList.length > 0) {
		for (var i = 0; i < sectionQuestionsList.length; i++) {
			var eachQuestionsData = sectionQuestionsList[i];
			var eachQuestionParentQuestionId = eachQuestionsData.parentQuestionId;
			if (eachQuestionParentQuestionId == schoolQuestionGuid) {
				childQuestions.push(eachQuestionsData);
			}
		}
	}
	return childQuestions;
}

function saveQuestionsInfo(selectedId) {
	var isTransferStudent = $(
			'input[name="form-preferred_is_transfer_student"]:checked').val();
	isTransferGlobal = isTransferStudent;
	if ($("#questions-form").valid() && isTransferStudent != null) {
		$("#save-questions-info").prop("disabled", true);
		var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
		$("#college_questions_status").html(statusHtml);
		var data = {};

		data["termId"] = $("#form-preferred_start_term").val();
		data["degreeId"] = $("#form-preferred_start_degree").val();
		data["schoolGuid"] = selectedSchoolGuid;
		data["isTransferStudent"] = isTransferStudent;
		var questionAnswers = [];

		$(".each_question")
				.each(
						function() {
							var eachQuestionDivId = $(this).attr("id");
							var eachQuestionId = eachQuestionDivId.split("_")[3];
							var eachQuestionSectionId = eachQuestionDivId
									.split("_")[4];
							var schoolQuestionAnswerGuid = eachQuestionDivId
									.split("_")[5];

							var eachQuestionAnswer = {
								"questionId" : eachQuestionId,
								"schoolQuestionAnswerGuid" : schoolQuestionAnswerGuid,
								"questionAnswer" : null,
								"tableQuestions" : {}
							};

							var questionAnswer = "";
							var questionType = eachQuestionDivId.split("_")[6];
							if (questionType == "Drop Down"
									|| questionType == "Text"
									|| questionType == "Input") {
								questionAnswer = $(
										"#question_" + eachQuestionId + "_"
												+ eachQuestionSectionId + "_"
												+ schoolQuestionAnswerGuid)
										.val();
							} else if (questionType == "Yes/No") {
								questionAnswer = $(
										'input[name=question_' + eachQuestionId
												+ '_' + eachQuestionSectionId
												+ '_'
												+ schoolQuestionAnswerGuid
												+ ']:checked').val();

							}
							eachQuestionAnswer.questionAnswer = questionAnswer;
							eachQuestionAnswer.tableQuestions = getTableQuestions(eachQuestionId);
							questionAnswers.push(eachQuestionAnswer);
						});

		data["questionAnswers"] = questionAnswers;
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "school/submitSchoolQuestions",
			data : JSON.stringify(data),
			dataType : 'json',
			success : function(data) {
				$("#" + selectedId).prop("disabled", false);
				saveFileTypeQuestions();
				schoolQuestionsStatusGlobal = "Completed";
				getSchoolAllDocuments();
			},
			error : function(e) {
				$.alert({
					content : "Error While Submitting Questions Data"
				});
				$("#" + selectedId).prop("disabled", false);
			}
		});
	}
}

function getTableQuestions(eachQuestionId) {
	var tableQuestionData = {};
	var i = 0;
	$("#each_question_tab_questions_" + eachQuestionId)
			.find(".each_question_table_question")
			.each(
					function() {
						var eachTableQuestionId = $(this).attr('id');
						i++;
						var questionAnswers = [];
						$("#" + eachTableQuestionId + " .each_question")
								.each(
										function() {

											var eachQuestionDivId = $(this)
													.attr("id");
											var eachQuestionId = eachQuestionDivId
													.split("_")[3];
											var eachQuestionSectionId = eachQuestionDivId
													.split("_")[4];
											var schoolQuestionAnswerGuid = eachQuestionDivId
													.split("_")[5];

											var eachQuestionAnswer = {
												"questionId" : eachQuestionId,
												"schoolQuestionAnswerGuid" : schoolQuestionAnswerGuid,
												"questionAnswer" : null
											};

											var questionAnswer = "";
											var questionType = eachQuestionDivId
													.split("_")[6];
											if (questionType == "Drop Down"
													|| questionType == "Text"
													|| questionType == "Input") {
												questionAnswer = $(
														"#question_"
																+ eachQuestionId
																+ "_"
																+ eachQuestionSectionId
																+ "_"
																+ schoolQuestionAnswerGuid)
														.val();
											} else if (questionType == "Yes/No") {
												questionAnswer = $(
														'input[name=question_'
																+ eachQuestionId
																+ '_'
																+ eachQuestionSectionId
																+ '_'
																+ schoolQuestionAnswerGuid
																+ ']:checked')
														.val();

											}
											eachQuestionAnswer.questionAnswer = questionAnswer;
											questionAnswers
													.push(eachQuestionAnswer);
										});

						tableQuestionData[i] = questionAnswers;
					});

	return tableQuestionData;
}
function saveFileTypeQuestions() {

	$.ajax({
		url : 'school/submitAttachmentSchoolQuestions',
		type : "POST",
		data : new FormData(document.getElementById("questions-form")),
		enctype : 'multipart/form-data',
		processData : false,
		contentType : false
	}).done(function(data) {
		$.alert({
			content : "Questions Submitted Succesffully"
		});
	}).fail(function(jqXHR, textStatus) {
		$.alert({
			content : "Error While Uploading Document. Please Try Again"
		});
	});
}

function getQuestionType(schoolQuestionSectionGuid, schoolQuestionGuid) {
	var sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var returnQuestionType = "";
	if (sectionQuestionsList.length > 0) {
		for (var i = 0; i < sectionQuestionsList.length; i++) {
			var eachQuestions = sectionQuestionsList[i];
			var eachQuestionQuestionId = eachQuestions.schoolQuestionGuid;
			if (eachQuestionQuestionId == schoolQuestionGuid) {
				returnQuestionType = eachQuestions.questionType;
				break;
			}
		}
	}
	return returnQuestionType;
}

function bindQuestionAnswers() {

	for (var i = 0; i < allSectionQuestionsList.length; i++) {
		var eachSectionQuestionData = allSectionQuestionsList[i];
		var eachQuestionSectionId = eachSectionQuestionData.schoolQuestionSectionGuid;
		var sectionQuestionsList = eachSectionQuestionData.sectionQuestions;
		for (var j = 0; j < sectionQuestionsList.length; j++) {
			var eachQuestionData = sectionQuestionsList[j];
			populateQuestionAnswer(eachQuestionSectionId, eachQuestionData);
		}
	}
}

function populateQuestionAnswer(eachQuestionSectionId, eachQuestionData) {
	var questionType = eachQuestionData["questionType"];
	var eachQuestionId = eachQuestionData["schoolQuestionGuid"];
	var questionAnswer = eachQuestionData["questionAnswer"];
	var questionAnswerGuid = eachQuestionData["questionAnswerGuid"];

	var childQuestions = eachQuestionData["childQuestions"];

	if (questionType == "Drop Down" || questionType == "Text"
			|| questionType == "Input") {
		$(
				"#question_" + eachQuestionId + "_" + eachQuestionSectionId
						+ "_" + questionAnswerGuid).val(questionAnswer);
	} else if (questionType == "Yes/No") {

		if (questionAnswer == "No" || questionAnswer === "No") {
			$(
					'input:radio[name=question_' + eachQuestionId + '_'
							+ eachQuestionSectionId + '_' + questionAnswerGuid
							+ ']:last').attr('checked', true);
		} else if (questionAnswer == "Yes" || questionAnswer === "Yes") {
			$(
					'input:radio[name=question_' + eachQuestionId + '_'
							+ eachQuestionSectionId + '_' + questionAnswerGuid
							+ ']:first').attr('checked', true);
		}
	}

	if (questionType == "Attachment") {
		if (questionAnswer != null && questionAnswer != ''
				&& questionAnswer != undefined) {
			$(
					"#attachment_question_answer_" + eachQuestionId + "_"
							+ eachQuestionSectionId + "_" + questionAnswerGuid)
					.html(
							"<a href='" + questionAnswer
									+ "' download>View File</a>");
		}
	}
	if (questionType == "Drop Down" || questionType == "Yes/No") {
		if (childQuestions.length > 0) {
			for (var k = 0; k < childQuestions.length; k++) {
				var eachSubQuestionData = childQuestions[k];
				var parentQuestionAnswerCondition = eachSubQuestionData["parentQuestionAnswerCondition"];

				if (parentQuestionAnswerCondition == questionAnswer) {
					var eachSubQuestionHtml = getQuestionData(
							eachQuestionSectionId, eachSubQuestionData);
					var subQuestionsHtml = eachSubQuestionHtml;
					$("#each_question_sub_questions_" + eachQuestionId).append(
							subQuestionsHtml);
				}
				populateQuestionAnswer(eachQuestionSectionId,
						eachSubQuestionData);
			}
		}
	}
}
function getSchoolDocuments() {

	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "student/getSchoolDocuments/" + selectedSchoolGuid,
				dataType : 'json',
				success : function(results) {
					if (results.length > 0 == false) {
						schoolDocumentsStatusGlobal = "Completed";
						var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
						$("#college_documents_status").html(statusHtml);
					}
					processSchoolDocuments(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Documents. Please Try Again"
							});
				}
			});
}

function processSchoolDocuments(results) {
	$("#school_tabs_info_documents").empty();
	var att = "";
	if (applicationStatus == "Completed") {
		att = "disabled='disabled'";
	} else {
		att = "enabled='enabled'";
	}
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();
	var eachSchoolDegreeDocuments = [];
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachSchoolDegreeDocument = results[i];
			var degreeList = eachSchoolDegreeDocument["degreeList"];
			if (schoolDegreeGuid == "") {
				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			} else {
				if (degreeList != null || degreeList != null) {
					if (degreeList.indexOf(schoolDegreeGuid) > -1) {
						eachSchoolDegreeDocuments
								.push(eachSchoolDegreeDocument);
					}
				}
				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			}
		}
	}
	if (eachSchoolDegreeDocuments.length > 0) {
		for (var i = 0; i < eachSchoolDegreeDocuments.length; i++) {

			var eachRow = eachSchoolDegreeDocuments[i];

			var schoolDocumentStatus = eachRow["schoolDocumentStatus"];
			var uploadedPathName = eachRow["uploadedDocumentName"];
			if (uploadedPathName == null || uploadedPathName == undefined
					|| uploadedPathName == "") {
				uploadedPathName = "";
			} else {
				var uploadedDocName = uploadedPathName.replace(/^.*[\\\/]/, '');
				var documentPath = eachRow["documentPath"];

			}
			var eachRowHtml = "<div class='row school_document'><div class='col-xs-12 school_document_name'>"
					+ eachRow['documentName']
					+ "</div><div class='col-xs-12' id='school_document_name_uploaded_bar_"
					+ i
					+ "'>"
					+ "</div><div class='col-xs-12 uploaded_doc_name' id='school_document_name_uploaded_"
					+ i
					+ "'><a  id='uploaded_path_file_name_"
					+ i
					+ "' href='"
					+ uploadedPathName
					+ "' download>"
					+ eachRow["studentSchoolDocumentName"]
					+ "</a>"
					+ "</div><div class='col-xs-12 school_document_upload_btn'><form name='documentForm_"
					+ i
					+ "' id='documentForm_"
					+ i
					+ "' method='post'><button type='button' id='upload_school_student_btn_"
					+ i
					+ "' "
					+ att
					+ " class='btn'  onclick=\"showAddDocumentModal('"
					+ i
					+ "')\"><i class='fa fa-cloud-upload'></i>&nbsp;&nbsp;Upload Filled Document</abbr></button>   <button type='button' id='download_school_sample_btn_"
					+ i
					+ "' class='btn' onclick=\"window.open('"
					+ eachRow['documentPath']
					+ "')\"><i class='fa fa-file-pdf-o'></i>&nbsp;&nbsp;Download Sample Document</abbr></button>  <button type='button' id='download_school_student_btn_"
					+ i
					+ "' class='btn' onclick=\"downloadDocument('"
					+ eachRow["viewDocumentUrl"]
					+ "')\"><i class='fa fa-file-pdf-o'></i>&nbsp;&nbsp;View Filled Document</abbr></button><input type='hidden' style='display:none' name='school_document_guid_"
					+ i
					+ "' value='"
					+ eachRow['schoolDocumentGuid']
					+ "'/><input type='hidden' style='display:none' name='student_school_document_guid_"
					+ i
					+ "' value='"
					+ eachRow['studentSchoolDocumentGuid']
					+ "'/> <input type='hidden' style='display:none' name='index_no' value='"
					+ i
					+ "' /><input type='file' style='display:none' name='document_file_"
					+ i
					+ "' id='document_file_"
					+ i
					+ "' required='required' accept='.jpg,.png,.jpeg,.pdf' onchange=\"showDocumentErrorMessage('"
					+ i + "')\"/></form></div></div>";

			$("#school_tabs_info_documents").append(eachRowHtml);
			$("#school_document_name_uploaded_bar_" + i).progressbar({
				value : 100
			});

			if (eachRow['studentSchoolDocumentGuid'] != undefined
					&& eachRow['studentSchoolDocumentGuid'] != null) {
				$("#school_document_name_uploaded_bar_" + i + " div").addClass(
						"filled_color");
			}
			if (uploadedPathName == null || uploadedPathName == undefined
					|| uploadedPathName == "") {
				$("#download_school_student_btn_" + i).prop("disabled", true);
				$("#uploaded_path_file_name_" + i).hide();
			}
		}

	} else {
		$("#school_documents_tfoot_row_no_data").show();
	}
}

function showAddDocumentModal(indexNo) {
	$("#document_file_" + indexNo).click();
}
function showDocumentErrorMessage(indexNo) {
	var filename = $("#document_file_" + indexNo).val();
	var fileExtension = [ 'pdf', 'jpg', 'jpeg', 'png' ];
	if ($.inArray(filename.split('.').pop().toLowerCase(), fileExtension) == -1) {
		$.alert({
			content : "File type should be .pdf, .jpg, .png, .jpeg"
		});
	} else {
		uploadNewDocument(indexNo);
	}
}
function uploadNewDocument(indexNo) {

	var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
	$("#college_documents_status").html(statusHtml);
	$("#upload_school_student_btn_" + indexNo).prop("disabled", true);
	$
			.ajax(
					{
						url : 'student/uploadStudentSchoolDocument',
						type : "POST",
						data : new FormData(document
								.getElementById("documentForm_" + indexNo)),
						enctype : 'multipart/form-data',
						processData : false,
						contentType : false
					})
			.done(
					function(data) {
						$.alert({
							content : "Document Uploaded Successfully"
						});
						$("#upload_school_student_btn_" + indexNo).prop(
								"disabled", false);
						$('#documentForm_' + indexNo)[0].reset();
						getSchoolDocuments();
						getSchoolAllDocuments();
					})
			.fail(
					function(jqXHR, textStatus) {
						var statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>&nbsp;";
						$("#college_documents_status").html(statusHtml);
						$
								.alert({
									content : "Error While Uploading Document. Please Try Again"
								});
					});

}
function determineStudentSchoolDocumentStatus() {
	var documentStatus = "Completed";
	var eachSchoolDegreeDocuments = schoolDataGlobal["schoolDocuments"];
	if (eachSchoolDegreeDocuments != null
			&& eachSchoolDegreeDocuments.length > 0) {
		for (var i = 0; i < eachSchoolDegreeDocuments.length; i++) {
			var eachRow = eachSchoolDegreeDocuments[i];
			var isMandatory = eachRow["isMandatory"];
			if (isMandatory == "YES") {
				documentStatus = "";
				break;
			}
		}
	}
	return documentStatus;
}

var allRequiresDocuments = [];
function submitApplication(buttonId) {
	$("#error_message").html("");
	allRequiresDocuments = [];
	if (allRequiresDocumentsIsTransferFields != null && isTransferGlobal == "X") {
		allRequiresDocuments = allRequiresDocumentsIsTransferFields;
		var requiredDocumentError = getRequiredDocumentsErrorMessage(allRequiresDocumentsIsTransferFields);

		allRequiresDocuments = allRequiresDocumentsFields;
		var requiredYesDocumentError = getRequiredDocumentsErrorMessage(allRequiresDocumentsFields);
		if (requiredYesDocumentError != ""
				&& requiredYesDocumentError != undefined) {
			var requiredDocumentError = requiredYesDocumentError
					+ +requiredDocumentError;
		}
	} else if (isTransferGlobal == "") {
		allRequiresDocuments = allRequiresDocumentsFields;
		var requiredDocumentError = getRequiredDocumentsErrorMessage(allRequiresDocumentsFields);
	}
	if (requiredDocumentError == "" || requiredDocumentError == undefined) {
		allRequiresDocuments = allRequiresScore;
		var requiredDocumentError = getRequiredDocumentsErrorMessage(allRequiresDocumentsIsTransferFields);
	} else if (requiredDocumentError == ""
			|| requiredDocumentError == undefined && isTransferGlobal == "X"
			&& allRequiresIsTransferScore != null) {
		allRequiresDocuments = allRequiresIsTransferScore;
		var requiredDocumentError = getRequiredDocumentsErrorMessage(allRequiresDocumentsIsTransferFields);
	}
	var statusHtml = "<i class='fa fa-check-circle green' aria-hidden='true'></i>&nbsp;";
	var schoolAppStatusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";
	var schoolApplicationGuid = $("#school_application_guid").val();
	var errorMessage = "";
	var isValid = true;
	if ($("#accept_terms_conditions_check").is(':checked') == false) {
		isValid = false;
		var errorMessage = "Accept the terms and conditions";
	}
	var requiredDocumentErrors = "";
	if (requiredDocumentError != "" && requiredDocumentError != undefined
			&& requiredDocumentError != null) {
		requiredDocumentErrors = requiredDocumentError;
		requiredDocumentErrors = requiredDocumentErrors.replace("NaN", "");
	}

	var requiredDocumentErrorMessage = requiredDocumentErrors;
	var profileErrorMessage = "";
	if (profileCompleted == false) {
		isValid = false;
		profileErrorMessage = "<span style='display: inline-block;'  aria-hidden='true'>Profile Section - </span></br>"
				+ allWritingErrorMessage + requiredDocumentErrorMessage;
	}

	var questionErrorMessage = "";
	if (schoolQuestionsStatusGlobal != "Completed") {
		isValid = false;
		questionErrorMessage = "Questions Section";

	}
	var uploadDocumentErrorMessage = "";
	if (uploadedStudentDocumentsErrorMessage != "") {
		isValid = false;
		uploadDocumentErrorMessage = uploadedStudentDocumentsErrorMessage;
	}
	if (allWritingErrorMessage != "") {
		isValid = false;
	}
	if (requiredDocumentErrorMessage != "") {
		isValid = false;
	}
	if (isValid == true) {
		$("#accept_terms_conditions_check").prop("disabled", true);

		$("#review_app_status").html(statusHtml);
		$("#school_app_status_" + selectedSchoolGuid).html(schoolAppStatusHtml);
		$("#" + buttonId).prop("disabled", true);
		$
				.ajax({
					type : "POST",
					url : "student/submitSchoolApplication",
					data : schoolApplicationGuid,
					dataType : 'json',
					contentType : "application/json",
					success : function(data) {
						$("#error_message").hide();
						if (data) {
							collegesApplied++;
							$("#colleges_completed_sections_count").text(
									collegesApplied);
							applicationStatus = "Completed";
							generatePDFPacket();
							$
									.alert("Your application is submitted sucessfully to the university");
						}
					},
					error : function(e) {
						var statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>";
						$.alert({
							content : "Error While Submitting Application"
						});
						$("#school_app_status_" + selectedSchoolGuid).html(
								statusHtml);
						$("#review_app_status").html(statusHtml);
					}
				});
	} else {

		$("#error_message").show();
		$("#error_message")
				.html(
						"Please complete the following items before submitting the application</br>");
		if (errorMessage != null && errorMessage != "") {
			$("#error_message")
					.append(
							"<span style='display: inline-block;' id='error_cause_message' aria-hidden='true'>"
									+ errorMessage + "</span></br>");
		}
		if (profileErrorMessage != null && profileErrorMessage != "") {
			$("#error_message").append(
					"<span style='display: inline-block;'  aria-hidden='true'>"
							+ profileErrorMessage + "</span></br>");
		}
		if (profileCompleted != false && allWritingErrorMessage != null) {
			$("#error_message").append(allWritingErrorMessage);
		}
		if (profileCompleted != false && requiredDocumentErrorMessage != null) {
			$("#error_message").append(requiredDocumentErrorMessage);

		}
		if (questionErrorMessage != null && questionErrorMessage != "") {
			$("#error_message").append(
					"<span	style='display: inline-block;'  aria-hidden='true'>"
							+ questionErrorMessage + "</span></br>");
		}
		if (uploadDocumentErrorMessage != null
				&& uploadDocumentErrorMessage != "") {
			$("#error_message").append(uploadDocumentErrorMessage);

		}
	}
}

function hideSubmitAllErrorMessage() {
	$("#error_message").hide();
	$("#profile_error_cause_message").hide();
	$("#question_error_cause_message").hide();
	$("#document_upload_error_cause_message").hide();
}
function removeDuplicatesSchoolTerms(arr) {
	return arr.reduce(function(p, c) {

		var id = [ c.termYear, c.term ].join('|');
		if (p.temp.indexOf(id) === -1) {
			p.out.push(c);
			p.temp.push(id);
		}
		return p;
	}, {
		temp : [],
		out : []
	}).out;
}
function removeDuplicatesSchoolDegrees(originalArray, prop) {
	var newArray = [];
	var lookupObject = {};

	for ( var i in originalArray) {
		lookupObject[originalArray[i][prop]] = originalArray[i];
	}

	for (i in lookupObject) {
		newArray.push(lookupObject[i]);
	}
	return newArray;
}
function downloadDocument(documentPath) {
	window
			.open(
					documentPath,
					'winname',
					'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no');
}
function getRequiredDocumentsErrorMessage(allRequiresDocuments) {
	var errorMessage = "";
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentDocuments",
		async : false,
		dataType : 'json',
		success : function(results) {
			errorMessage = getDocumentError(results);
		},
	});

	return errorMessage;
}
function getSchoolAllDocuments() {
	if (selectedSchoolGuid == null || selectedSchoolGuid == undefined) {
		selectedSchoolGuid = "1";
	}
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getSchoolDocuments/" + selectedSchoolGuid,
		dataType : 'json',
	}).done(function(results) {
		getDocumentByDegree(results);
	});
}
function getDocumentByDegree(results) {
	var eachSchoolDegreeDocuments = [];
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			eachSchoolDegreeDocument = results[i];
			var degreeList = eachSchoolDegreeDocument["degreeList"];
			if (degreeList.indexOf(schoolDegreeGuid) > -1
					&& degreeList != "ALL") {
				eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);

			}
			if (degreeList == "ALL"
					&& eachSchoolDegreeDocument["isMandatory"] != "NO") {
				eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
			}
		}
	}
	checkStudentSchoolDocuments(eachSchoolDegreeDocuments);
}
var uploadedStudentDocumentsErrorMessage = "";
function checkStudentSchoolDocuments(schoolAllDocuments) {
	uploadedStudentDocumentsErrorMessage = "";
	var documentErrorMessage = "";
	if (isTransferGlobal == null) {
		isTransferGlobal = "";
	}
	if (isTransferGlobal == "") {
		if (schoolAllDocuments != null && schoolAllDocuments.length > 0) {
			for (var i = 0; i < schoolAllDocuments.length; i++) {
				var eachSchoolDocument = schoolAllDocuments[i];
				var isManadatory = eachSchoolDocument['isMandatory'];
				if (isManadatory == "YES"
						&& eachSchoolDocument["studentSchoolDocumentGuid"] == null) {
					documentErrorMessage += "<span	style='display: inline-block;' >Document Tab - "
							+ eachSchoolDocument['documentName']
							+ " </span></br>";
				}
			}
		}
	} else if (isTransferGlobal == "X") {
		if (schoolAllDocuments != null && schoolAllDocuments.length > 0) {
			for (var i = 0; i < schoolAllDocuments.length; i++) {
				var eachSchoolDocument = schoolAllDocuments[i];
				var isManadatory = eachSchoolDocument['isMandatory'];
				if ((isManadatory == "YES" || isManadatory == "YES FOR TRANSFER")
						&& eachSchoolDocument["studentSchoolDocumentGuid"] == null) {
					var DocumentErrorName = eachSchoolDocument['documentName'];
					documentErrorMessage += "<span	style='display: inline-block;'  >Document Tab - "
							+ DocumentErrorName + " </span><br/>";
				}
			}
		}

	}

	uploadedStudentDocumentsErrorMessage = documentErrorMessage;
}
function getDocumentError(result) {
	if (allRequiresDocuments.length > 0) {
		for (var i = 0; i < allRequiresDocuments.length; i++) {
			var isFound = false;
			if (result.length > 0) {
				for (var j = 0; j < result.length; j++) {
					var eachDoc = result[j];
					if (allRequiresDocuments[i] == eachDoc["documentType"]) {
						delete allRequiresDocuments[i];
						break;
					}
				}
			}
		}
	}
	var errorMessage = "";
	if (allRequiresDocuments.length > 0) {
		for (var i = 0; i < allRequiresDocuments.length; i++) {
			if (allRequiresDocuments[i] != ""
					&& allRequiresDocuments[i] != undefined) {
				errorMessage += "<span	style='display: inline-block;'  >Profile Section - Documents - "
						+ allRequiresDocuments[i] + "</span></br> ";
			}
		}
	}

	return errorMessage;
}

function getStudentWritingsError() {
	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "student/getStudentWritings",
				dataType : 'json',
				success : function(results) {
					if (processStudentWritingsErrorMessage(results) == false) {
						allWritingErrorMessage = "<span	style='display: inline-block;'  >Profile Section - PERSONAL ESSAY </span></br>";
					}
				},
			});
}
function processStudentWritingsErrorMessage(results) {
	var flag = false;
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachDoc = results[i];
			if (eachDoc["writingType"] == "SOP"
					|| eachDoc["writingType"] == "PERSONAL ESSAY") {
				flag = true;
			}
		}
	}
	return flag;
}
function getStudentProfileStatus() {
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "student/getStudentProfile",
		dataType : 'json',
		success : function(data) {
			profileCompleted = data["profileCompleted"];
			;
		},
	});
}

function convertDate(inputFormat) {
	if (inputFormat != null) {
		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}
		var d = new Date(inputFormat);
		return [ pad(d.getMonth() + 1), pad(d.getDate()), d.getFullYear() ]
				.join('/');
	} else {
		return "NA";
	}

}
