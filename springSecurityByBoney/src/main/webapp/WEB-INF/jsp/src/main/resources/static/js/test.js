var firstSchoolGuid = "";
var collegesApplied = 0;
var collegesAdded = 0;
var selectedSchoolGuid = "";
$(document).ready(function() {
	$(".nav_menu").removeClass("active");
	$("#my_colleges_menu").addClass("active");
	firstSchoolGuid = $("#hidden_school_guid").val();
	$("#colleges_completed_sections_count").text(collegesApplied);
	$("#colleges_total_sections_count").text(collegesAdded);
	$("#questions-form").validate();
	setCollegesTabHeight();
	getAllStudentSchools();
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
	$.ajax({
		type : "GET",
		url : "school/getStudentSchools",
		success : function(results) {
			processAllStudentSchools(results);
		},
		error : function(e) {
			alert("Error While Loading Student Schools.Please Try Again");
		}
	});
}

function processAllStudentSchools(results) {
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
			var statusHtml = "<i class='fa fa-check' aria-hidden='true'></i>";
			var applicationStatus = eachRow['applicationStatus'];
			if (applicationStatus == "Pending") {
				statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>";
			} else if (applicationStatus == "Completed") {
				statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";
				collegesApplied++;
			}
			allRows += "<div class='each_school_name row' id='school_name_"
					+ eachRow['schoolGuid']
					+ "' onclick=\"getSchoolInfo('"
					+ eachRow['schoolGuid']
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

function getSchoolInfo(schoolGuid) {
	selectedSchoolGuid = schoolGuid;
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
			alert("Error While Loading School Info.Please Try Again");
		}
	});
}

function processSchoolInfo(schoolData) {

	$("#school_img").html(
			"<img src='images/dummy_school.jpg' class='img-responsive'/>");

	var schoolName = schoolData["schoolName"];
	$("#school_name_col").html(schoolName);

	if (schoolData["schoolInfo"] != "" && schoolData["schoolInfo"] != undefined
			&& schoolData["schoolInfo"] != null) {

		$("#school_info_content").show();

		$("#school_info_content").html(schoolData["schoolInfo"]);
		$("#school_contact_info").hide();
		$("#school_terms_info").hide();
		$("#requirements_to_apply_info").hide();

	} else {

		$("#school_info_content").hide();

		var phoneNumber = schoolData["phoneNumber"];
		var emailAddress = schoolData["emailAddress"];
		var website = schoolData["website"];
		var addressLineOne = schoolData["addressLineOne"];
		var addressLineTwo = schoolData["addressLineTwo"];
		var city = schoolData["school_city"];
		var state = schoolData["state"];
		var zipCode = schoolData["zipCode"];

		var contactInfoHtml = "<p>" + schoolName + "</p><p>" + addressLineOne
				+ "</p><p>" + addressLineTwo + "</p><p>" + city + "</p><p>"
				+ state + "</p><p>" + zipCode + "</p><p>" + emailAddress
				+ "</p><p>" + phoneNumber + "</p><p>" + website + "</p>";

		$("#school_contact_info_content").html(contactInfoHtml);
	}
	var schoolTerms = schoolData["schoolTerms"];

	if (schoolTerms.length > 0) {
		for (var i = 0; i < schoolTerms.length; i++) {
			var eachTerm = schoolTerms[i];
			var termYear = eachTerm["termYear"];
			var term = eachTerm["term"];
			var schoolTermGuid = eachTerm["schoolTermGuid"];

			var eachTermHtml = "<option value='" + schoolTermGuid + "'>"
					+ termYear + "-" + term + "</option>";
			$("#form-preferred_start_term").append(eachTermHtml);
		}
	}

	var schoolDegrees = schoolData["schoolDegrees"];

	if (schoolDegrees.length > 0) {
		for (var i = 0; i < schoolDegrees.length; i++) {
			var eachDegree = schoolDegrees[i];
			var degreeOffered = eachDegree["degreeOffered"];
			var schoolDegreeGuid = eachDegree["schoolDegreeGuid"];

			var eachDegreeHtml = "<option value='" + schoolDegreeGuid + "'>"
					+ degreeOffered + "</option>";
			$("#form-preferred_start_degree").append(eachDegreeHtml);
		}
	}

	var studentApplicationResponse = schoolData["studentApplicationResponse"];
	var schoolDegreeGuid = studentApplicationResponse["schoolDegreeGuid"];
	var schoolTermGuid = studentApplicationResponse["schoolTermGuid"];

	var schoolQuestionsStatus = "";
	var schoolDocumentsStatus = "";
	var schoolApplicationGuid = "";
	var acceptTermsAndConditions = "";
	if (studentApplicationResponse != null
			&& studentApplicationResponse != undefined) {
		schoolQuestionsStatus = studentApplicationResponse["schoolQuestionsStatus"];
		schoolDocumentsStatus = studentApplicationResponse["schoolDocumentsStatus"];
		schoolApplicationGuid = studentApplicationResponse["schoolApplicationGuid"];
		acceptTermsAndConditions = studentApplicationResponse["acceptTermsAndConditions"];
	}

	$("#school_application_guid").val(schoolApplicationGuid);

	var statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>&nbsp;";

	if (schoolQuestionsStatus == "Completed") {
		$("#college_questions_status").html(statusHtml);
	}

	if (schoolDocumentsStatus == "Completed") {
		$("#college_documents_status").html(statusHtml);
	}

	$("#form-preferred_start_term").val(schoolTermGuid);
	$("#form-preferred_start_degree").val(schoolDegreeGuid);

	if (acceptTermsAndConditions == "Completed") {
		$("#accept_terms_conditions_check").prop('checked', true);

		$("#submit_application_info").prop("disabled", true);
	} else {
		$("#accept_terms_conditions_check").prop('checked', false);
		$("#submit_application_info").prop("disabled", false);
	}

	gotoSchoolTab('college_info_school_tab');
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
	} else if (selectedTabId == 'documents_school_tab') {
		getSchoolDocuments();
		$("#school_tabs_info_documents").show();
	} else if (selectedTabId == 'review_school_tab') {
		$("#school_tabs_info_review_submit").show();
	}
}

function generatePDFPacket() {

	var schoolApplicationGuid = $("#school_application_guid").val();
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "school/generatePdfPacket/" + schoolApplicationGuid,
		success : function(data) {

			var dataURI = "data:application/pdf;base64," + data;
			window.open(dataURI);
		},
		error : function(e) {
			alert("Error While Loading School PDF. Please Try Again");
		}
	});
}

function loadAllSchoolSectionQuestions() {

	$.ajax({
		type : "GET",
		url : "school/getSchoolSectionQuestions/" + selectedSchoolGuid,
		success : function(results) {
			processAllSchoolSectionQuestions(results);
			bindQuestionAnswers();
		},
		error : function(e) {
			alert("Error While Loading School Questions.Please Try Again");
		}
	});
}

var allSectionQuestionsList = [];
function processAllSchoolSectionQuestions(results) {
	if (results.length > 0) {
		var sectionsHtml = "";
		for (var i = 0; i < results.length; i++) {
			var eachSection = results[i];
			var schoolQuestionSectionGuid = eachSection["schoolQuestionSectionGuid"];
			var sectionName = eachSection["sectionName"];
			var sectionQuestions = eachSection["questions"];

			var eachSectionQuestionsData = {
				"schoolQuestionSectionGuid" : schoolQuestionSectionGuid,
				"sectionQuestions" : sectionQuestions
			};
			allSectionQuestionsList.push(eachSectionQuestionsData);

			var questionsHtml = getSectionQuestionsHtml(schoolQuestionSectionGuid);

			sectionsHtml += "<div class='each_question_section' onclick=\"showSectionQuestions('"
					+ schoolQuestionSectionGuid
					+ "')\"><i id='close_"
					+ schoolQuestionSectionGuid
					+ "' class='fa fa-arrow-right' aria-hidden='true'></i>"
					+ sectionName
					+ "</div><div class='each_section_questions' id='"
					+ schoolQuestionSectionGuid
					+ "_questions_list'>"
					+ questionsHtml + "</div>";
		}
		$("#question_sections").html(sectionsHtml);
	}
}
function showSectionQuestions(schoolQuestionSectionGuid) {
	$("#" + schoolQuestionSectionGuid + "_questions_list").slideToggle();
}
var sectionQuestionsList = [];
function getSectionQuestionsHtml(schoolQuestionSectionGuid) {
	sectionQuestionsList = getSectionQuestions(schoolQuestionSectionGuid);
	var questionsHtml = "";
	if (sectionQuestionsList.length > 0) {
		for (var i = 0; i < sectionQuestionsList.length; i++) {
			var eachQuestion = sectionQuestionsList[i];
			var eachQuestionsHtml = getQuestionData(schoolQuestionSectionGuid,
					eachQuestion);
			questionsHtml += eachQuestionsHtml;
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

	var schoolQuestionGuid = eachQuestions["schoolQuestionGuid"];
	var questionTitle = eachQuestions["questionTitle"];
	var questionType = eachQuestions["questionType"];
	var answerOptions = eachQuestions["answerOptions"];
	var questionMandatory = eachQuestions["questionMandatory"];
	var questionAnswerGuid = eachQuestions["questionAnswerGuid"];

	var requiredHtml = "";
	var requiredAstricHtml = "";
	if (questionMandatory == "Yes") {
		requiredHtml = "required='required'";
		requiredAstricHtml = "<sup class='color_red'>*</sup>";
	}

	var dropDownHtml = "<select name='question_" + schoolQuestionGuid + "_"
			+ schoolQuestionSectionGuid + "_" + questionAnswerGuid
			+ "' id='question_" + schoolQuestionGuid + "_"
			+ schoolQuestionSectionGuid + "_" + questionAnswerGuid
			+ "' onchange=\"loadChildQuestions('" + schoolQuestionSectionGuid
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
				+ "' value='Yes' onclick=\"loadChildQuestions('"
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
				+ "' value='No' onclick=\"loadChildQuestions('"
				+ schoolQuestionSectionGuid
				+ "','"
				+ schoolQuestionGuid
				+ "','"
				+ questionAnswerGuid
				+ "')\" "
				+ requiredHtml
				+ ">No</label></div>";
	} else if (questionType == "Text") {
		questionAnswerText = "<div><textarea name='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' placeholder='" + questionTitle
				+ "' class='form-control' id='question_" + schoolQuestionGuid
				+ "_" + schoolQuestionSectionGuid + "_" + questionAnswerGuid
				+ "' value='' " + requiredHtml + "></textarea></div>";
	} else if (questionType == "Input") {
		questionAnswerText = "<div><input type='text' name='question_"
				+ schoolQuestionGuid + "_" + schoolQuestionSectionGuid + "_"
				+ questionAnswerGuid + "' placeholder='" + questionTitle
				+ "' class='form-control' id='question_" + schoolQuestionGuid
				+ "_" + schoolQuestionSectionGuid + "_" + questionAnswerGuid
				+ "' value='' " + requiredHtml + "/></div>";
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
				+ "' value='' "
				+ requiredHtml
				+ "/></div><div class='col-lg-6 col-md-6 col-sm-12 col-xs-12' id='attachment_question_answer_"
				+ schoolQuestionGuid
				+ "_"
				+ schoolQuestionSectionGuid
				+ "_"
				+ questionAnswerGuid + "'></div>";
	}

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
		for (var i = 0; i < childQuestions.length; i++) {
			var eachSubQuestionData = childQuestions[i];
			var parentQuestionAnswerCondition = eachSubQuestionData["parentQuestionAnswerCondition"];
			if (parentQuestionAnswerCondition == selectedParentQuestionAnswerCondition) {
				var eachSubQuestionHtml = getQuestionData(
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
	if ($("#questions-form").valid()) {
		$("#" + selectedId).prop("disabled", true);

		var data = {};

		data["termId"] = $("#form-preferred_start_term").val();
		data["degreeId"] = $("#form-preferred_start_degree").val();
		data["schoolGuid"] = selectedSchoolGuid;

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
			},
			error : function(e) {
				alert("Error While Submitting Questions Data");
				$("#" + selectedId).prop("disabled", false);
			}
		});

	} else {
		$(".each_question_section").each(function() {
			$(this).click();
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
		alert("Questions Submitted Succesffully");
	}).fail(function(jqXHR, textStatus) {
		alert("Error While Uploading Document.Please Try Again");
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
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getSchoolDocuments/" + selectedSchoolGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolDocuments(results);
		},
		error : function(e) {
			alert("Error While Loading School Documents.Please Try Again");
		}
	});
}

function processSchoolDocuments(results) {
	if (results.length > 0) {
		$("#school_tabs_info_documents").empty();
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			var uploadedDocName = eachRow["uploadedDocumentName"];
			var documentPath = eachRow["documentPath"];
			if (uploadedDocName == null || uploadedDocName == undefined) {
				uploadedDocName = "";
			}
			var eachRowHtml = "<div class='row school_document'><div class='col-xs-12 school_document_name'>"
					+ eachRow['documentName']
					+ "</div><div class='col-xs-12' id='school_document_name_uploaded_bar_"
					+ i
					+ "'>"
					+ "</div><div class='col-xs-12 uploaded_doc_name' id='school_document_name_uploaded_"
					+ i
					+ "'><a href='"
					+ documentPath
					+ uploadedDocName
					+ "' download>"
					+ uploadedDocName
					+ "</a>"
					+ "</div><div class='col-xs-12 school_document_upload_btn'><form name='documentForm_"
					+ i
					+ "' id='documentForm_"
					+ i
					+ "' method='post'><button type='button' id='upload_school_student_btn_"
					+ i
					+ "' class='btn' onclick=\"showAddDocumentModal('"
					+ i
					+ "')\"><i class='fa fa-cloud-upload'></i>&nbsp;&nbsp;Upload Document</abbr></button><input type='hidden' style='display:none' name='school_document_guid_"
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
					+ "' required='required' onchange=\"uploadNewDocument('"
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
		}

	} else {
		$("#school_documents_tfoot_row_no_data").show();
	}
}

function showAddDocumentModal(indexNo) {
	$("#document_file_" + indexNo).click();
}
function uploadNewDocument(indexNo) {
	$("#upload_school_student_btn_" + indexNo).prop("disabled", true);
	$.ajax(
			{
				url : 'student/uploadStudentSchoolDocument',
				type : "POST",
				data : new FormData(document.getElementById("documentForm_"
						+ indexNo)),
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false
			}).done(function(data) {
		alert("Document Uploaded Successfully");
		$("#upload_school_student_btn_" + indexNo).prop("disabled", false);
		$('#documentForm_' + indexNo)[0].reset();
		getSchoolDocuments();
	}).fail(function(jqXHR, textStatus) {
		alert("Error While Uploading Document.Please Try Again");
	});
}

function subimtApplication(buttonId) {
	var schoolApplicationGuid = $("#school_application_guid").val();
	console.log("submit ==" + schoolApplicationGuid);
	if ($("#accept_terms_conditions_check").is(':checked')) {
		$("#" + buttonId).prop("disabled", true);

		$
				.ajax({
					type : "POST",
					url : "student/submitSchoolApplication",
					data : schoolApplicationGuid,
					dataType : 'json',
					contentType : "application/json",
					success : function(data) {

						var statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";
						$("#school_app_status_" + selectedSchoolGuid).html(
								statusHtml);

						collegesApplied++;
						$("#colleges_completed_sections_count").text(
								collegesApplied);
					},
					error : function(e) {
						alert("Error While Submitting Application");
						$("#" + buttonId).prop("disabled", false);
					}
				});
	} else {
		alert("Accept the terms and conditions");
		return false;
	}

}