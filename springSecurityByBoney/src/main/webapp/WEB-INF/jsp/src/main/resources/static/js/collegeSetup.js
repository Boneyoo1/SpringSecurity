var firstSchoolGuid = "";
var selectedSchoolGuid = "";
var schoolCountryGuid;
$(document)
		.ready(
				function() {
					$('#addSectionModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});

					$('#addShoolDegreeModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});

					$('#addQuestionModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});
					$('#addShoolTermModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});
					$('#addShoolDocumentModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});
					$("#addSectionModal").on("hidden.bs.modal", function() {
						clearAddSectionModal();
					});

					$("#addShoolDegreeModal").on("hidden.bs.modal", function() {
						clearAddDegreeModal();
					});

					$("#addQuestionModal").on("hidden.bs.modal", function() {
						clearAddQuestionModal();
					});

					$("#addShoolTermModal").on("hidden.bs.modal", function() {
						clearAddSchoolTermModal();
					});
					$("#addShoolDocumentModal").on("hidden.bs.modal",
							function() {
								clearAddSchoolDocumentModal();
							});
					$("#add_edit_document_form").validate();
					$("#college_info_text").jqte();
					$(".nav_menu").removeClass("active");
					$("#college_setup_menu").addClass("active");
					$("#questions-form").validate();
					setCollegesTabHeight();
					getAllSchools();
					$('#form-applicable-to-degrees')
							.multiselect(
									{

										buttonContainer : '<div class="btn-group" />',
										numberDisplayed : 1,
										allSelectedText : 'All',
										buttonWidth : '100%',
										includeSelectAllOption : true,
										buttonHeight : '40px',
										buttonClass : 'btn-multiselect',
										inheritClass : false,
										maxHeight : 100,
										dropRight : 'true',
										onChange : function(element, checked) {
											var optionVal = $('#form-applicable-to-degrees option:selected');
											var selected = [];
											$(optionVal).each(
													function(index, optionVal) {
														selected.push([ $(this)
																.val() ]);
													});
										}
									});
					$('#form-applicable-to-degrees-documents')
							.multiselect(
									{
										buttonContainer : '<div class="btn-group" />',
										numberDisplayed : 1,
										allSelectedText : 'All',
										buttonWidth : '100%',
										includeSelectAllOption : true,
										buttonClass : 'btn-multiselect',
										buttonHeight : '40px',
										inheritClass : true,
										maxHeight : 100,
										dropRight : 'true',
										onChange : function(element, checked) {
											var optionVal = $('#form-applicable-to-degrees-documents option:selected');
											var selectedDocumentVal = [];
											$(optionVal)
													.each(
															function(index,
																	optionVal) {
																selectedDocumentVal
																		.push([ $(
																				this)
																				.val() ]);
															});
										}

									});

					$('#form-document-file')
							.change(
									function() {
										var fileName = $(this).val();
										var fileExtension = [ 'pdf', 'jpg',
												'jpeg', 'png' ];
										if ($.inArray(fileName.split('.').pop()
												.toLowerCase(), fileExtension) == -1) {
											$("#doc-upload-btn").prop(
													"disabled", true);
											$
													.alert({
														content : "File type should be .pdf, .jpg, .png, .jpeg"
													});
										} else {
											$("#doc-upload-btn").prop(
													"disabled", false);
										}
									});
				});

function clearAddSchoolDocumentModal() {
	$("label.error").hide();
	$(".error").removeClass("error");

	$("#add_edit_document_form")[0].reset();
	$("#form_hidden_document_guid").val("");
}

function clearAddSchoolTermModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$("#add_edit_term_form")[0].reset();
	$("#form-hidden-term-guid").val("");
}
function clearAddQuestionModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$("#add_edit_question_form")[0].reset();
	$("#form-hidden-question-guid").val("");
	$("#parent_question_guid").val("");
}
function clearAddSectionModal() {
	$("label.error").hide();
	$(".error").removeClass("error");

	$("#add_edit_question_section_form")[0].reset();
	$("#form-hidden-section-guid").val("");
}
function clearAddDegreeModal() {
	$("label.error").hide();
	$(".error").removeClass("error");

	$("#add_edit_degree_form")[0].reset();
	$("#form-hidden-degree-guid").val("");
}

function getAllCountries() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllCountries",
		dataType : 'json',
		success : function(results) {
			processAllCountries(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Countries.Please Try Again"
			});
		}
	});
}

function processAllCountries(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<option value='" + eachRow["guid"] + "'>"
					+ eachRow["countryName"] + "</option>";

		}
		$("#form-school-country").append(allRows);
		$("#form-school-country").val(schoolCountryGuid);

	}
}

$(window).resize(function() {
	setCollegesTabHeight();
});
function setCollegesTabHeight() {
	$("#school-names").css(
			"max-height",
			$(document).innerHeight()
					- $("#school-names-completed_uncompleted").innerHeight()
					- $(".app_header").innerHeight() - 2);
}
function getAllSchools() {
	var data = {};
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/getSchools",
		dataType : 'json',
		data : JSON.stringify(data),
		success : function(results) {
			processAllSchools(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Schools.Please Try Again"
			});
		}
	});
}

function processAllSchools(results) {
	if (results.length > 0) {
		$("#school-content").fadeIn("slow");
		var allRows = "";
		for (var i = 0; i < results.length; i++) {

			var eachRow = results[i];
			if (i == 0 || i === 0) {
				firstSchoolGuid = eachRow['schoolGuid'];
			}

			allRows += "<div class='each_school_name row' id='school_name_"
					+ eachRow['schoolGuid'] + "' onclick=\"getSchoolInfo('"
					+ eachRow['schoolGuid'] + "')\"><div class='col-xs-10'>"
					+ eachRow['schoolName'] + "</div></div>";
		}
		$("#school-names").append(allRows);
		$("#no_colleges_found_list").hide();
		getSchoolInfo(firstSchoolGuid);
	} else {
		$("#no_colleges_found_list").show();
	}
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
			getAllCountries();
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Info.Please Try Again"
			});
		}
	});
}
var schoolGuid = "";
function processSchoolInfo(schoolData) {
	schoolDegrees = "";

	schoolDegrees = schoolData['schoolDegrees'];
	$("#school_img").html(
			"<img src='images/dummy_school.jpg' class='img-responsive'/>");
	schoolGuid = schoolData["schoolGuid"];

	var schoolName = schoolData["schoolName"];
	$("#school_name_col").html(schoolName);
	var phoneNumber = schoolData["phoneNumber"];
	var contactPerson = schoolData["contactPerson"];
	var emailAddress = schoolData["emailAddress"];
	var website = schoolData["website"];
	var addressLineOne = schoolData["addressLineOne"];
	var addressLineTwo = schoolData["addressLineTwo"];
	var city = schoolData["city"];
	var state = schoolData["state"];
	var zipCode = schoolData["zipCode"];
	schoolCountryGuid = schoolData["schoolCountryGuid"];
	var gmatScoreSubmissionSchoolCode = schoolData["gmatScoreSubmissionSchoolCode"];
	var greScoreSubmissionSchoolCode = schoolData["greScoreSubmissionSchoolCode"];
	var toeflScoreSubmissionSchoolCode = schoolData["toeflScoreSubmissionSchoolCode"];
	var internationAppFees = schoolData["internationAppFees"];
	var internationCredentialEvalFees = schoolData["internationCredentialEvalFees"];
	var mailingFee = schoolData["mailingFee"];
	var minimumGMATcore = schoolData["minimumGMATcore"];
	var minimumGREScore = schoolData["minimumGREScore"];
	var minimumTOEFLScore = schoolData["minimumTOEFLScore"];
	var minimumGPA = schoolData["minimumGPA"];
	var recLettersRequires = schoolData["recLettersRequires"];
	if (recLettersRequires == "" || recLettersRequires == null
			|| recLettersRequires == undefined) {
		recLettersRequires = "NO";
	}

	var requiresPassport = schoolData["requiresPassport"];
	if (requiresPassport == "" || requiresPassport == null
			|| requiresPassport == undefined) {
		requiresPassport = "NO";
	}
	var resumeRequires = schoolData["resumeRequires"];
	if (resumeRequires == "" || resumeRequires == null
			|| resumeRequires == undefined) {
		resumeRequires = "NO";
	}

	var solvCERTRequires = schoolData["solvCERTRequires"];
	if (solvCERTRequires == "" || solvCERTRequires == null
			|| solvCERTRequires == undefined) {
		solvCERTRequires = "NO";
	}

	var sopRequires = schoolData["sopRequires"];
	if (sopRequires == "" || sopRequires == null || sopRequires == undefined) {
		sopRequires = "NO";
	}
	var transcriptsRequires = schoolData["transcriptsRequires"];
	if (transcriptsRequires == "" || transcriptsRequires == null
			|| transcriptsRequires == undefined) {
		transcriptsRequires = "NO";
	}
	var schoolInfoStudentView = schoolData["schoolInfo"];
	var schoolGuid = schoolData['schoolGuid'];
	var emailDomain1 = schoolData["emailDomainOne"];
	var emailDomain2 = schoolData["emailDomainTwo"];
	var emailDomain3 = schoolData["emailDomainThree"];
	var applicationChecklistLink = schoolData["applicationCheckListLink"];
	var tuitionsFeesLink = schoolData["tuitionFeesLink"];
	var requiresEAD = schoolData["requiresEAD"];
	if (requiresEAD == "" || requiresEAD == null || requiresEAD == undefined) {
		requiresEAD = "NO";
	}
	var requiresI20 = schoolData["requiresI20"];
	if (requiresI20 == "" || requiresI20 == null || requiresI20 == undefined) {
		requiresI20 = "NO";
	}
	var requiresF1Visa = schoolData["requiresF1Visa"];
	if (requiresF1Visa == "" || requiresF1Visa == null
			|| requiresF1Visa == undefined) {
		requiresF1Visa = "NO";
	}
	var requiresTOEFLScore = schoolData["requiresTOFELScore"];
	if (requiresTOEFLScore == "" || requiresTOEFLScore == null
			|| requiresTOEFLScore == undefined) {
		requiresTOEFLScore = "NO";
	}
	$("#form_hidden_schoolGuid").val(schoolGuid);
	$("#form-school-permanent-address-line-1").val(addressLineOne);
	$("#form-school-permanent-address-line-2").val(addressLineTwo);
	$("#form-school-city").val(city);
	$("#form-school-state").val(state);
	$("#form-school-zipcode").val(zipCode);
	$("#form-school-contact-person").val(contactPerson);
	$("#form-school-contact-number").val(phoneNumber);
	$("#form-school-email").val(emailAddress);
	$("#form-school-website").val(website);
	$("#form-school-gmat-score-submission-code").val(
			gmatScoreSubmissionSchoolCode);
	$("#form-school-gre-score-submission-code").val(
			greScoreSubmissionSchoolCode);
	$("#form-school-toefl-score-submission-code").val(
			toeflScoreSubmissionSchoolCode);
	$("#form-school-internation-app-fees").val(internationAppFees);
	$("#form-school-internation-credential-eval-fees").val(
			internationCredentialEvalFees);
	$("#form-school-internation-mailing-fees").val(mailingFee);

	$("#form-school-min-gmat-score").val(minimumGMATcore);
	$("#form-school-min-gre-score").val(minimumGREScore);
	$("#form-school-min-toefl-score").val(minimumTOEFLScore);
	$("#form-school-min-gpa-score").val(minimumGPA);
	$("#form-rec-letter-required").val(recLettersRequires);
	$("#form-passport-required").val(requiresPassport);
	$("#form-resume-required").val(resumeRequires);
	$("#form-solv-cert-required").val(solvCERTRequires);
	$("#form-sop-required").val(sopRequires);
	$("#form-transcripts-required").val(transcriptsRequires);
	$("#form-school-email-domain-1").val(emailDomain1);
	$("#form-school-email-domain-2").val(emailDomain2);
	$("#form-school-email-domain-3").val(emailDomain3);
	$("#form-school-application-checklist-link").val(applicationChecklistLink);
	$("#form-school-tuition-fees-link").val(tuitionsFeesLink);
	$("#form-ead-required").val(requiresEAD);
	$("#form-i20-required").val(requiresI20);
	$("#form-f1-visa-required").val(requiresF1Visa);
	$("#form-toefl-score-required").val(requiresTOEFLScore);

	$(".jqte_editor").html(schoolInfoStudentView);

	gotoSchoolTab('school_tabs_info_college');
}

function saveSchoolInfo(buttonId) {
	var data = {};
	data["schoolGuid"] = schoolGuid;
	data["schoolGuid"] = selectedSchoolGuid;
	data["addressLineOne"] = $("#form-school-permanent-address-line-1").val();
	data["addressLineTwo"] = $("#form-school-permanent-address-line-2").val();
	data["city"] = $("#form-school-city").val();
	data["state"] = $("#form-school-state").val();
	data["zipCode"] = $("#form-school-zipcode").val();
	data["contactPerson"] = $("#form-school-contact-person").val();
	data["phoneNumber"] = $("#form-school-contact-number").val();
	data["emailAddress"] = $("#form-school-email").val();
	data["website"] = $("#form-school-website").val();
	data["gmatScoreSubmissionSchoolCode"] = $(
			"#form-school-gmat-score-submission-code").val();
	data["greScoreSubmissionSchoolCode"] = $(
			"#form-school-gre-score-submission-code").val();
	data["toeflScoreSubmissionSchoolCode"] = $(
			"#form-school-toefl-score-submission-code").val();
	data["internationAppFees"] = $("#form-school-internation-app-fees").val();
	data["internationCredentialEvalFees"] = $(
			"#form-school-internation-credential-eval-fees").val();
	data["mailingFee"] = $("#form-school-internation-mailing-fees").val();

	data["minimumGMATcore"] = $("#form-school-min-gmat-score").val();
	data["minimumGREScore"] = $("#form-school-min-gre-score").val();
	data["minimumTOEFLScore"] = $("#form-school-min-toefl-score").val();
	data["minimumGPA"] = $("#form-school-min-gpa-score").val();

	data["recLettersRequires"] = $("#form-rec-letter-required").val();

	data["requiresPassport"] = $("#form-passport-required").val();

	data["resumeRequires"] = $("#form-resume-required").val();

	data["solvCERTRequires"] = $("#form-solv-cert-required").val();

	data["sopRequires"] = $("#form-sop-required").val();

	data["transcriptsRequires"] = $("#form-transcripts-required").val();
	data["schoolCountryGuid"] = $("#form-school-country").val();
	data["emailDomainOne"] = $("#form-school-email-domain-1").val();
	data["emailDomainTwo"] = $("#form-school-email-domain-2").val();
	data["emailDomainThree"] = $("#form-school-email-domain-3").val();
	data["applicationCheckListLink"] = $(
			"#form-school-application-checklist-link").val();
	data["tuitionFeesLink"] = $("#form-school-tuition-fees-link").val();
	data["requiresEAD"] = $("#form-ead-required").val();
	data["requiresI20"] = $("#form-i20-required").val();
	data["requiresF1Visa"] = $("#form-f1-visa-required").val();
	data["requiresTOFELScore"] = $("#form-toefl-score-required").val();
	$("#" + buttonId).prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveSchoolInfo",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$("#" + buttonId).prop("disabled", false);
			$.alert({
				content : "School Data Saved Succesffully"
			});
		},
		error : function(e) {
			$alert({
				content : "Error While Saving School Data"
			});
			$("#" + buttonId).prop("disabled", false);
		}
	});
}

function gotoSchoolTab(selectedTabId) {
	$(".school_tabs_info").hide();
	$(".school_tab").removeClass("selected_school_tab");
	$("#" + selectedTabId + "_tab").addClass("selected_school_tab");

	$("#" + selectedTabId).show();

	if (selectedTabId == 'school_tabs_degrees') {
		getSchoolDegrees();
	}

	if (selectedTabId == "school_tabs_terms") {
		getSchoolTerms();
	}

	if (selectedTabId == "school_tabs_info_questions") {
		loadAllSchoolSectionQuestions();
	}

	if (selectedTabId == "school_tabs_info_documents") {
		getSchoolDocuments();
	}

}

function loadAllSchoolSectionQuestions() {

	$
			.ajax({
				type : "GET",
				url : "school/getSchoolQuestionSections/" + selectedSchoolGuid,
				success : function(results) {
					processAllSchoolQuestionSections(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Questions.Please Try Again"
							});
				}
			});
}

function processAllSchoolQuestionSections(results) {
	$("#form-question-section").empty();
	var sectionsHtml = "";
	var optinsHtml = "";
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachSection = results[i];
			var schoolQuestionSectionGuid = eachSection["schoolQuestionSectionGuid"];
			var sectionName = eachSection["sectionName"];

			sectionsHtml += "<div class='each_question_section' onclick=\"showSectionQuestions('"
					+ schoolQuestionSectionGuid
					+ "')\"><i id='close_"
					+ schoolQuestionSectionGuid
					+ "' class='fa fa-arrow-right' aria-hidden='true'></i>"
					+ sectionName
					+ "<i class='fa fa-trash add_question_i' aria-hidden='true' onclick=\"deleteSection('"
					+ schoolQuestionSectionGuid
					+ "')\"></i><i class='fa fa-edit add_question_i' aria-hidden='true' data-toggle='modal' data-target='#addSectionModal' onclick=\"editSection('"
					+ schoolQuestionSectionGuid
					+ "')\"></i><i class='fa fa-plus add_question_i' aria-hidden='true' data-toggle='modal' data-target='#addQuestionModal' onclick=\"addQuestionToSection('"
					+ schoolQuestionSectionGuid
					+ "')\"></i></div><div class='each_section_questions' id='"
					+ schoolQuestionSectionGuid + "_questions_list'></div>";

			optinsHtml += "<option value='" + schoolQuestionSectionGuid + "'>"
					+ sectionName + "</option>";
		}
	} else {
		sectionsHtml = "<div id='no-sections-found'>No Question Sections Found</div>";
	}

	$("#form-question-section").append(optinsHtml);
	$("#question_sections").html(sectionsHtml);
}

function editSection(schoolQuestionSectionGuid) {

	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "school/getSchoolQuestionSection/"
						+ schoolQuestionSectionGuid,
				dataType : 'json',
				success : function(results) {

					processSchoolSection(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Questions Section.Please Try Again"
							});
				}
			});

}

function processSchoolSection(data) {

	$("#form-hidden-section-guid").val(data["schoolQuestionSectionGuid"]);
	$("#form-section-name").val(data["sectionName"]);
	$("#form-seq-no").val(data["seqNo"]);

}

function deleteSection(schoolQuestionSectionGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Section and Correspinding Section Questions ?',
				columnClass : 'col-md-8 col-md-offset-4',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "school/deleteSchoolQuestionSection",
									data : schoolQuestionSectionGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Section & Corresponding Section Questions Deleted Successfully",
													columnClass : 'col-md-8 col-md-offset-4',
												});
										loadAllSchoolSectionQuestions();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Section.Please Try Again",
												});
									}
								});

					},
				}
			});

}

function addQuestionToSection(schoolQuestionSectionGuid) {

	$("#addQuestionModal").on("show.bs.modal", function() {
		$("#addQuestionModal").removeData();
	});
	degreeListSelectForQuestionSection();
	$("#form-question-section").val(schoolQuestionSectionGuid);
	$("input:radio[name=form-question-mandatory]:first").attr('checked', true);
	$("input:radio[name=form-question-inactive]:last").attr('checked', true);
}
function degreeListSelectForQuestionSection() {
	if (schoolDegrees.length > 0) {
		var eachDegreesHtml = "";
		for (var i = 0; i < schoolDegrees.length; i++) {
			var eachRow = schoolDegrees[i];
			eachDegreesHtml += '<option value="' + eachRow['schoolDegreeGuid']
					+ '">' + eachRow['degreeOffered'] + '</option>';
		}
		$("#form-applicable-to-degrees").empty().html(eachDegreesHtml);
		$('#form-applicable-to-degrees').multiselect('rebuild');
		$('#form-applicable-to-degrees').multiselect('selectAll', false)
		$('#form-applicable-to-degrees').multiselect('updateButtonText');

	}
}
function showSectionQuestions(schoolQuestionSectionGuid) {
	$("#" + schoolQuestionSectionGuid + "_questions_list").slideToggle();
	loadSectionQuestions(schoolQuestionSectionGuid);
}

function saveSchoolInfoContent(buttonId) {
	var schoolInfoContent = $("#college_info_text").val();
	var data = {};

	data["schoolGuid"] = selectedSchoolGuid;
	data["schoolInfo"] = schoolInfoContent;
	$("#" + buttonId).prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveSchoolInfoContent",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$("#" + buttonId).prop("disabled", false);
			$.alert({
				content : "School Data Saved Succesffully"
			});
		},
		error : function(e) {
			$.alert({
				content : "Error While Saving School Data"
			});
			$("#" + buttonId).prop("disabled", false);
		}
	});
}

function getSchoolDegrees() {
	destroyDataTable('school_degrees_list_tbl');
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "school/getSchoolDegrees/" + selectedSchoolGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolDegrees(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Degrees.Please Try Again"
			});
		}
	});
}
var schoolDegrees = [];
function processSchoolDegrees(results) {
	schoolDegrees = [];
	schoolDegrees = results;

	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<tr id='tr_"
					+ eachRow['schoolDegreeGuid']
					+ "'><td>"
					+ (i + 1)
					+ "</td><td id='selected_school_degree_name_"
					+ eachRow['schoolDegreeGuid']
					+ "'>"
					+ eachRow['degreeOffered']
					+ "</td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#addShoolDegreeModal' onclick=\"editSchoolDegree('"
					+ eachRow["schoolDegreeGuid"]
					+ "')\"></a></td><td><a class='fa fa-trash edit-delete-icon' onclick=\"deleteSchoolDegree('"
					+ eachRow["schoolDegreeGuid"] + "')\"></a></td></tr>";
		}
		$("#schools_degrees_list_tbody").empty().html(allRows);
		$('#school_degrees_list_tbl').DataTable({});
		$("#school_degrees_list_tbl_length").empty();
		$("#school_degrees_list_tbl_length").remove();
		$("#school_degrees_list_tbl_filter").empty();
		$("#school_degrees_list_tbl_filter").remove();
		$("#school_degrees_list_tbl_info").empty();
		$("#school_degrees_list_tbl_info").remove();
		$("#school_degrees_list_tbl_paginate").empty();
		$("#school_degrees_list_tbl_paginate").remove();
		$("#school_degrees_tfoot_row_no_data").hide();
	} else {
		$("#school_degrees_tfoot_row_no_data").show();
	}
}

function editSchoolDegree(schoolDegreeGuid) {

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "school/getSchoolDegree/" + schoolDegreeGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolDegree(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Degrees.Please Try Again"
			});
		}
	});

}

function processSchoolDegree(data) {

	$("#form-hidden-degree-guid").val(data["schoolDegreeGuid"]);
	$("#form-degree-offered").val(data["degreeOffered"]);
	$("#form-degree-dept").val(data["degreeDepartment"]);
	$("#form-contact-person").val(data["contactPerson"]);
	$("#form-email").val(data["emailAddress"]);
	$("#form-contact-no").val(data["phoneNumber"]);
	$("#form-dept-min-gre-score").val(data["departmentMinGREScore"]);
	$("#form-dept-min-toefl-score").val(data["departmentMinTOEFLScore"]);
	$("#form-dept-min-gpa").val(data["departmentMinGPA"]);
	$("#form-address-line-1").val(data["addressLineOne"]);
	$("#form-address-line-2").val(data["addressLineTwo"]);
	$("#form-address-line-3").val(data["addressLine3"]);
	$("#form-city").val(data["city"]);
	$("#form-state").val(data["state"]);
	$("#form-zipcode").val(data["zipCode"]);
	$("#form-dept-min-gmat-score").val(data["deptMinGMATScore"]);
	var requiresGMATScore = data["requiresGMATScore"];
	if (requiresGMATScore == "" || requiresGMATScore == null
			|| requiresGMATScore == undefined) {
		requiresGMATScore = "NO";
	}
	$("#form-dept-gmat-score-required").val(requiresGMATScore);
	var requiresGREScore = data["requiresGREScore"];
	if (requiresGREScore == "" || requiresGREScore == null
			|| requiresGREScore == undefined) {
		requiresGREScore = "NO";
	}
	$("#form-dept-gre-score-required").val(requiresGREScore);

}

function deleteSchoolDegree(schoolDegreeGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Degree ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "school/deleteSchoolDegree",
									data : schoolDegreeGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Degree Deleted Successfully"
												});
										getSchoolDegrees();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Degree.Please Try Again"
												});
									}
								});
					},
				}
			});
}
function addSection() {

	var data = {};

	data["schoolQuestionSectionGuid"] = $("#form-hidden-section-guid").val();
	data["sectionName"] = $("#form-section-name").val();
	data["seqNo"] = $("#form-seq-no").val();
	data["schoolGuid"] = selectedSchoolGuid;

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveQuestionSection",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$('#addSectionModal').modal('toggle');
			$.alert({
				content : "Section Saved Succesffully"
			});
			loadAllSchoolSectionQuestions();
		},
		error : function(e) {
			$.alert({
				content : "Error While Saving Section Data"
			});
		}
	});
}

function addQuestion() {

	var data = {};
	var selectedDocVal = $("#form-applicable-to-degrees").val();
	if (selectedDocVal != null) {
		if (selectedDocVal.length > 0) {
			var selectedDocvalues = "";
			if (selectedDocVal.length == schoolDegrees.length) {
				data["degreeList"] = "ALL,";
			} else {
				for (var i = 0; i < selectedDocVal.length; i++) {
					selectedDocvalues += selectedDocVal[i] + ",";
				}
				data["degreeList"] = selectedDocvalues;
			}
		}
	}
	var sectionId = $("#form-question-section").val();
	data["schoolQuestionSecionGuid"] = sectionId;
	data["schoolQuestionGuid"] = $("#form-hidden-question-guid").val();
	data["questionTitle"] = $("#form-question-title").val();
	var questionType = $("#form-question-type").val();
	data["questionType"] = questionType;
	data["questionLongText"] = $("#form-question-long-text").val();
	data["questionMandatory"] = $('input[name=form-question-mandatory]:checked')
			.val();
	data["questionInactive"] = $('input[name=form-question-inactive]:checked')
			.val();
	var parentQuestionId = $("#parent_question_guid").val();
	if (parentQuestionId != null && parentQuestionId != undefined
			&& parentQuestionId != '') {
		data["parentQuestionId"] = parentQuestionId;
		data["childQuestionseqNo"] = $("#form-question-seq-no").val();
		if (questionType = "Yes/No") {
			data["parentQuestionAnswerCondition"] = $(
					'input[name=form-parent-question-answer-condition]:checked')
					.val();
		} else if ("Drop Down") {
			data["parentQuestionAnswerCondition"] = $(
					"#form-parent-question-answer-condition").val();
		}
	} else {
		data["seqNo"] = $("#form-question-seq-no").val();
	}

	var answerOptions = [];
	$(".answer_option")
			.each(
					function() {
						var eachQuestionDivId = $(this).attr("id");
						var eachRowIndex = eachQuestionDivId.split("_")[3];
						var answer = $("#answer_option_text_" + eachRowIndex)
								.val();
						var schoolQuestionAnswerOptionGuid = $(
								"#answer_option_guid_" + eachRowIndex).val();
						var seqNo = $("#answer_option_seq_no_" + eachRowIndex)
								.val();

						var eachAnswerOpiton = {};
						eachAnswerOpiton["schoolQuestionAnswerOptionGuid"] = schoolQuestionAnswerOptionGuid;
						eachAnswerOpiton["answer"] = answer;
						eachAnswerOpiton["seqNo"] = seqNo;

						answerOptions.push(eachAnswerOpiton);

					});

	data["answerOptions"] = answerOptions;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveQuestion",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			// $('#addQuestionModal').modal('toggle');
			loadSectionQuestions(sectionId);
			$.alert({
				content : "Question Saved Succesffully"
			});
		},
		error : function(e) {
			$.alert({
				content : "Error While Saving Question Data"
			});
		}
	});
}

function loadSectionQuestions(schoolQuestionSecionGuid) {
	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "school/getSchoolSectionQuestionsBySection/"
						+ schoolQuestionSecionGuid,
				dataType : 'json',
				success : function(results) {
					processSectionQuestions(results, schoolQuestionSecionGuid);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Section Questions.Please Try Again"
							});
				}
			});
}

function processSectionQuestions(results, schoolQuestionSecionGuid) {
	$("#" + schoolQuestionSecionGuid + "_questions_list").empty();
	var questionsHtml = "";
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachSection = results[i];
			var schoolQuestionGuid = eachSection["schoolQuestionGuid"];
			var questionTitle = eachSection["questionTitle"];

			questionsHtml += "<div class='each_question_section'><i class='fa fa-arrow-right' aria-hidden='true'></i>"
					+ questionTitle
					+ "<i class='fa fa-trash add_question_i' aria-hidden='true' onclick=\"deleteQuestion('"
					+ schoolQuestionGuid
					+ "','"
					+ schoolQuestionSecionGuid
					+ "')\"></i><i class='fa fa-edit add_question_i' aria-hidden='true' data-toggle='modal' data-target='#addQuestionModal' onclick=\"editQuestion('"
					+ schoolQuestionGuid
					+ "')\"></i>"
					+ "<i class='fa fa-plus add_question_i' aria-hidden='true' data-toggle='modal' data-target='#addQuestionModal' onclick=\"addChildQuestionToQuestion('"
					+ schoolQuestionSecionGuid
					+ "','"
					+ schoolQuestionGuid
					+ "')\"></i> &nbsp; &nbsp; </div><div class='each_section_questions' id='"
					+ schoolQuestionGuid + "_questions_list'></div>";
		}
	} else {
		questionsHtml = "<div id='no-sections-found'>No Question Found</div>";
	}
	$("#" + schoolQuestionSecionGuid + "_questions_list").html(questionsHtml);

}

function addChildQuestionToQuestion(schoolQuestionSectionGuid,
		schoolQuestionGuid) {
	$("#answer_options_row").empty();
	$("#form-question-section").val(schoolQuestionSectionGuid);
	$("input:radio[name=form-question-mandatory]:first").attr('checked', true);
	$("input:radio[name=form-question-inactive]:last").attr('checked', true);

	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "school/getSchoolQuestion/" + schoolQuestionGuid,
				dataType : 'json',
				success : function(data) {
					$("#parent_question_title_row").show();
					$("#parent_question_guid").val(schoolQuestionGuid);
					$("#parent_question_answer_condition_row").show();

					$("#parent_question_title").html(data["questionTitle"]);

					var questionType = data["questionType"];
					var answerOptions = data["answerOptions"];
					var parentQuestionAnswerConditionHtml = "";
					if (questionType == "Yes/No") {
						parentQuestionAnswerConditionHtml = '<label class="radio-inline"><input type="radio" name="form-parent-question-answer-condition" value="Yes">Yes</label> <label class="radio-inline"><input type="radio" name="form-parent-question-answer-condition" value="No">No</label>';
						$("#parent_question_answer_condition_row_content")
								.html(parentQuestionAnswerConditionHtml);
						$(
								"input:radio[name=form-parent-question-answer-condition]:first")
								.attr('checked', true);

					} else if (questionType == "Drop Down") {
						parentQuestionAnswerConditionHtml = "<select id='form-parent-question-answer-condition' name='form-parent-question-answer-condition'><option value=''></option>";
						if (answerOptions.length > 0) {
							for (var i = 0; i < answerOptions.length; i++) {
								var eachRow = answerOptions[i];
								parentQuestionAnswerConditionHtml += '<option value="'
										+ eachRow['answer']
										+ '">"'
										+ eachRow['answer'] + '"</option>';
							}
							parentQuestionAnswerConditionHtml += "</select>";
						}
						$("#parent_question_answer_condition_row_content")
								.html(parentQuestionAnswerConditionHtml);
					}

				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Term.Please Try Again"
							});
				}
			});
}

function deleteQuestion(schoolQuestionGuid, schoolQuestionSecionGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Question ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "school/deleteSchoolQuestion",
									data : schoolQuestionGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Question Deleted Successfully"
												});
										loadSectionQuestions(schoolQuestionSecionGuid);
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Question.Please Try Again"
												});
									}
								});

					},
				}
			});

}

function editQuestion(schoolQuestionGuid) {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "school/getSchoolQuestion/" + schoolQuestionGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolQuestion(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Term.Please Try Again"
			});
		}
	});
}

function processSchoolQuestion(data) {
	degreeListSelectForQuestionSection();
	var questionType = "";
	$("#form-question-section").val(data["schoolQuestionSecionGuid"]);
	$("#form-hidden-question-guid").val(data["schoolQuestionGuid"]);
	$("#form-question-title").val(data["questionTitle"]);
	$("#form-question-type").val(data["questionType"]);
	$("#form-question-long-text").val(data["questionLongText"]);
	var degreeList = data["degreeList"];
	if (degreeList == "ALL" || degreeList == null) {
		$('#form-applicable-to-degrees').multiselect('selectAll', true);
	} else {
		var degreeListArray = degreeList.split(",");
		$("#form-applicable-to-degrees").val(degreeListArray);
	}
	questionType = data["questionType"];
	if (data["questionMandatory"] == "Yes"
			|| data["questionMandatory"] === "Yes") {
		$("input:radio[name=form-question-mandatory]:first").attr('checked',
				true);
	} else {
		$("input:radio[name=form-question-mandatory]:last").attr('checked',
				true);
	}
	if (data["questionInactive"] == "Yes" || data["questionInactive"] === "Yes") {
		$("input:radio[name=form-question-inactive]:first").attr('checked',
				true);
	} else {
		$("input:radio[name=form-question-inactive]:last")
				.attr('checked', true);
	}

	$("#form-question-seq-no").val(data["seqNo"]);
	var answerOptions = data["answerOptions"];
	if (questionType == "Drop Down") {
		$("#answer_options_row").show();
		if (answerOptions.length > 0) {
			$("#answer_options_row").empty();
			for (var i = 0; i < answerOptions.length; i++) {
				var eachRow = answerOptions[i];
				var answerOptionCount = i;
				var answerOptionHtml = '<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 answer_option" id="answer_option_div_'
						+ answerOptionCount
						+ '"><label for="form-question-seq-no"	class="control-label col-sm-3">Answer Option</label><div class="col-sm-9"><input type="text"  placeholder="Answer Option" class="form-control" required="required" value="'
						+ eachRow['answer']
						+ '" id="answer_option_text_'
						+ answerOptionCount
						+ '"/>&nbsp;<input type="text"  placeholder="Seq No" class="form-control" required="required" value='
						+ eachRow['seqNo']
						+ ' id="answer_option_seq_no_'
						+ answerOptionCount
						+ '"/><input type="hidden" value='
						+ eachRow['schoolQuestionAnswerOptionGuid']
						+ ' id="answer_option_guid_'
						+ answerOptionCount
						+ '"/></div></div>';
				$("#answer_options_row").append(answerOptionHtml);

			}
		}
		addAddMoreRowAnswerOptionButton();
	} else {
		$("#answer_options_row").empty();
	}
	$('#form-applicable-to-degrees').multiselect('updateButtonText');
	$("#form-applicable-to-degrees").multiselect('refresh');

}

function showAddAnswerOptions() {
	var questionType = $("#form-question-type").val();
	$("input:radio[name=form-question-mandatory]:first").prop('checked', true);
	$("input:radio[name=form-question-inactive]:last").prop('checked', true);
	if (questionType == "Information") {
		$("input:radio[name=form-question-mandatory]:last").prop('checked',
				true);
	}
	if (questionType == "Drop Down") {
		$("#answer_options_row").show();
		var answerOptionCount = $(".answer_option").length;
		if (answerOptionCount == 0) {
			addAnswerOption();
		}

		addAddMoreRowAnswerOptionButton();
	} else {
		$("#answer_options_row").hide();
	}
}
function addAnswerOption() {
	var answerOptionCount = $(".answer_option").length;
	var answerOptionHtml = '<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 answer_option" id="answer_option_div_'
			+ answerOptionCount
			+ '"><label for="form-question-seq-no"	class="control-label col-sm-3">Answer Option</label><div class="col-sm-9"><input type="text"  placeholder="Answer Option" class="form-control" required="required" value="" id="answer_option_text_'
			+ answerOptionCount
			+ '"/>&nbsp;<input type="text"  placeholder="Seq No" class="form-control" required="required" value="" id="answer_option_seq_no_'
			+ answerOptionCount
			+ '"/><input type="hidden" value="" id="answer_option_guid_'
			+ answerOptionCount + '"/></div></div>';
	$("#answer_options_row").append(answerOptionHtml);
}

function addAddMoreRowAnswerOption() {
	addAnswerOption();
	addAddMoreRowAnswerOptionButton();
}

function addAddMoreRowAnswerOptionButton() {
	$("#add_more_options_btn").empty();
	$("#add_more_options_btn").remove();
	var addMoreOptionsHtml = '<div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align:center" id="add_more_options_btn"><button type="button" class="btn btn-default" onclick="addAddMoreRowAnswerOption()">Add Another Answer Option</button></div>';
	$("#answer_options_row").append(addMoreOptionsHtml);
}

function addSchoolDegree() {
	var data = {};

	data["schoolDegreeGuid"] = $("#form-hidden-degree-guid").val();
	data["degreeOffered"] = $("#form-degree-offered").val();
	data["degreeDepartment"] = $("#form-degree-dept").val();
	data["schoolGuid"] = selectedSchoolGuid;
	data["contactPerson"] = $("#form-contact-person").val();
	data["emailAddress"] = $("#form-email").val();
	data["phoneNumber"] = $("#form-contact-no").val();
	data["departmentMinGREScore"] = $("#form-dept-min-gre-score").val();
	data["departmentMinTOEFLScore"] = $("#form-dept-min-toefl-score").val();
	data["departmentMinGPA"] = $("#form-dept-min-gpa").val();
	data["addressLineOne"] = $("#form-address-line-1").val();
	data["addressLineTwo"] = $("#form-address-line-2").val();
	data["addressLine3"] = $("#form-address-line-3").val();
	data["city"] = $("#form-city").val();
	data["state"] = $("#form-state").val();
	data["zipCode"] = $("#form-zipcode").val();
	data["requiresGMATScore"] = $("#form-dept-gmat-score-required").val();
	data["requiresGREScore"] = $("#form-dept-gre-score-required").val();
	data["deptMinGMATScore"] = $("#form-dept-min-gmat-score").val();
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveSchoolDegree",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$('#addShoolDegreeModal').modal('toggle');
			$.alert({
				content : "Degree Saved Succesffully"
			});
			getSchoolDegrees();
		},
		error : function(e) {
			$.alert({
				content : "Error While Saving Degree Data"
			});
		}
	});

}

function showAddTermModal() {
	$("#form-early-decision-deadline-date").datepicker({});

	$("#form-app-submission-start-date").datepicker({});

	$("#form-documents-deadline-date").datepicker({});

	$("#form-app-deadline-date").datepicker({});

}

function getSchoolTerms() {
	destroyDataTable('school_terms_list_tbl');
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "school/getSchoolTerms/" + selectedSchoolGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolTerms(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Terms.Please Try Again"
			});
		}
	});
}

function processSchoolTerms(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<tr id='tr_"
					+ eachRow['schoolTermGuid']
					+ "'><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachRow['termYear']
					+ "</td><td>"
					+ eachRow['term']
					+ "</td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#addShoolTermModal' onclick=\"editSchoolTerm('"
					+ eachRow["schoolTermGuid"]
					+ "')\"></a></td><td><a class='fa fa-trash edit-delete-icon' onclick=\"deleteSchoolTerm('"
					+ eachRow["schoolTermGuid"] + "')\"></a></td></tr>";
		}
		$("#schools_terms_list_tbody").html(allRows);
		$('#school_terms_list_tbl').DataTable({});
		$("#school_terms_list_tbl_length").empty();
		$("#school_terms_list_tbl_length").remove();
		$("#school_terms_list_tbl_filter").empty();
		$("#school_terms_list_tbl_filter").remove();
		$("#school_terms_list_tbl_info").empty();
		$("#school_terms_list_tbl_info").remove();
		$("#school_terms_list_tbl_paginate").empty();
		$("#school_terms_list_tbl_paginate").remove();
		$("#school_terms_tfoot_row_no_data").hide();
	} else {
		$("#school_terms_tfoot_row_no_data").show();
	}
}
function editSchoolTerm(schoolTermGuid) {

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "school/getSchoolTerm/" + schoolTermGuid,
		dataType : 'json',
		success : function(results) {
			processSchoolTerm(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading School Term.Please Try Again"
			});
		}
	});

}

function processSchoolTerm(data) {

	$("#form-early-decision-deadline-date").datepicker({});

	$("#form-app-submission-start-date").datepicker({});

	$("#form-documents-deadline-date").datepicker({});

	$("#form-app-deadline-date").datepicker({});

	$("#form-hidden-term-guid").val(data["schoolTermGuid"]);
	$("#form-term-year").val(data["termYear"]);
	$("#form-term-name").val(data["term"]);
	$("#form-early-decision-deadline-date").val(
			data["earlyDecisionDeadLineDate"]);
	$("#form-app-submission-start-date").val(
			data["applicationSubmissionStartDate"]);
	$("#form-documents-deadline-date").val(data["documentsDeadLineDate"]);
	$("#form-app-deadline-date").val(data["applicationDeadLineDate"]);

}

function deleteSchoolTerm(schoolTermGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Term ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "school/deleteSchoolTerm",
									data : schoolTermGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Term Deleted Successfully"
												});
										getSchoolTerms();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Term.Please Try Again"
												});
									}
								});
					},
				}
			});

}

function addSchoolTerm() {
	var data = {};
	data["schoolTermGuid"] = $("#form-hidden-term-guid").val();
	data["termYear"] = $("#form-term-year").val();
	data["term"] = $("#form-term-name").val();
	data["schoolGuid"] = selectedSchoolGuid;
	data["earlyDecisionDeadLineDate"] = $("#form-early-decision-deadline-date")
			.val();
	data["applicationSubmissionStartDate"] = $(
			"#form-app-submission-start-date").val();
	data["documentsDeadLineDate"] = $("#form-documents-deadline-date").val();
	data["applicationDeadLineDate"] = $("#form-app-deadline-date").val();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/saveSchoolTerm",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			$('#addShoolTermModal').modal('toggle');
			$.alert({
				content : "Term Saved Succesffully"
			});
			getSchoolTerms();
		},
		error : function(e) {
			$.alert({
				content : "Error While Saving Term Data"
			});
		}
	});

}

/*-----------------------------------------------------------*/
function getDegreeListForDocumentSection() {
	if (schoolDegrees.length > 0) {
		var eachDegreesHtml = "";
		for (var i = 0; i < schoolDegrees.length; i++) {
			var eachRow = schoolDegrees[i];
			eachDegreesHtml += '<option value="' + eachRow['schoolDegreeGuid']
					+ '">' + eachRow['degreeOffered'] + '</option>';
		}

	} else {
		$("#form-applicable-to-degrees-documents").html("")
	}
	$("#form-applicable-to-degrees-documents").empty().html(eachDegreesHtml);
	$('#form-applicable-to-degrees-documents').multiselect('rebuild');
	$('#form-applicable-to-degrees-documents').multiselect('selectAll', false)
	$('#form-applicable-to-degrees-documents').multiselect('updateButtonText');
}

function showAddDocumentModal() {
	getDegreeListForDocumentSection();
	$("#form-document-mandatory").val("NO");
}
function getSchoolDocuments() {
	destroyDataTable('school_documents_list_tbl');
	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "school/getSchoolDocuments/" + selectedSchoolGuid,
				dataType : 'json',
				success : function(results) {
					processSchoolDocuments(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Documents.Please Try Again"
							});
				}
			});
}

function processSchoolDocuments(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			var isMandatory = eachRow["isMandatory"];
			if (isMandatory == "" || isMandatory == null
					|| isMandatory == undefined) {
				isMandatory = "NO";
			}
			allRows += "<tr id='tr_"
					+ eachRow['schoolDocumentGuid']
					+ "'><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachRow['documentName']
					+ "</td><td>"
					+ eachRow['documentURL']
					+ "</td><td>"
					+ isMandatory
					+ "</td><td><a class='fa fa-file-pdf-o edit-delete-icon' onclick=\"openDocumentNewTab('"
					+ eachRow["documentPath"]
					+ "')\"></a></td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#addShoolDocumentModal' onclick=\"editSchoolDocument('"
					+ eachRow["schoolDocumentGuid"]
					+ "')\"></a></td><td><a class='fa fa-trash edit-delete-icon' onclick=\"deleteSchoolDocument('"
					+ eachRow["schoolDocumentGuid"] + "')\"></a></td></tr>";
		}
		$("#school_documents_list_tbody").show();
		$("#school_documents_list_tbody").html(allRows);
		$('#school_documents_list_tbl').DataTable({});
		$("#school_documents_list_tbl_length").empty();
		$("#school_documents_list_tbl_length").remove();
		$("#school_documents_list_tbl_filter").empty();
		$("#school_documents_list_tbl_filter").remove();
		$("#school_documents_list_tbl_info").empty();
		$("#school_documents_list_tbl_info").remove();
		$("#school_documents_list_tbl_paginate").empty();
		$("#school_documents_list_tbl_paginate").remove();
		$("#school_documents_tfoot_row_no_data").hide();
	} else {
		$("#school_documents_list_tbody").hide();
		$("#school_documents_tfoot_row_no_data").show();
	}
}
function editSchoolDocument(schoolDocumentGuid) {

	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "school/getSchoolDocument/" + schoolDocumentGuid,
				dataType : 'json',
				success : function(results) {
					$('#addShoolDocumentModal').modal('toggle');

					processSchoolDocument(results);

				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Document.Please Try Again"
							});
				}
			});

}

function processSchoolDocument(data) {
	getDegreeListForDocumentSection();
	$("#form_hidden_document_guid").val(data["schoolDocumentGuid"]);
	$("#form-document-name").val(data["documentName"]);
	$("#form-document-url").val(data["documentURL"]);
	var degreeList = data["degreeList"];
	if (degreeList == "ALL" || degreeList == null) {
		$('#form-applicable-to-degrees-documents').multiselect('selectAll',
				true);
	} else {
		var degreeListArray = degreeList.split(",");
		$("#form-applicable-to-degrees-documents").val(degreeListArray);
	}

	$('#form-applicable-to-degrees-documents').multiselect('updateButtonText');
	$("#form-applicable-to-degrees-documents").multiselect('refresh');

	var isMandatory = data["isMandatory"];
	if (isMandatory == "" || isMandatory == null || isMandatory == undefined) {
		isMandatory = "NO";
	}
	$("#form-document-mandatory").val(isMandatory);
}
function deleteSchoolDocument(schoolDocumentGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Document ?',
				buttons : {
					confirm : function() {

						$
								.ajax({
									type : "POST",
									url : "school/deleteSchoolDocument",
									data : schoolDocumentGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Document Deleted Successfully"
												});
										getSchoolDocuments();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Document.Please Try Again"
												});
									}
								});

					},
				}
			});

}

function addSchoolDocument() {
	var selectedDocVal = $("#form-applicable-to-degrees-documents").val();
	if (selectedDocVal != null) {
		if (selectedDocVal != null && selectedDocVal.length > 0) {
			var selectedDocvalues = "";
			if (schoolDegrees.length == selectedDocVal.length) {
				$('input[name="form-document-degree-list"]').attr('value',
						'ALL,');
			} else {
				for (var i = 0; i < selectedDocVal.length; i++) {
					selectedDocvalues += selectedDocVal[i] + ",";
				}
				$("#form-document-degree-list").val(selectedDocvalues);
			}
		}

	}
	if ($("#add_edit_document_form").valid()) {
		$.ajax(
				{
					type : "POST",
					contentType : "application/json",
					url : "school/saveSchoolDocument",
					data : new FormData(document
							.getElementById("add_edit_document_form")),
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false
				}).done(function(data) {
			$('#addShoolDocumentModal').modal('toggle');
			$.alert({
				content : "Document Saved Succesffully"
			});
			getSchoolDocuments();
		}).fail(function(jqXHR, textStatus) {
			$.alert({
				content : "Error While Uploading Document.Please Try Again"
			});
		});

	} else {
		$.alert({
			content : "Fill All Document Details.Please Try Again"
		});
	}
}

function openDocumentNewTab(documentPath) {
	window.open(documentPath, "Sample", 'width=1200,height=800,scrollbars=no');
	return false;
}

function destroyDataTable(selectedId) {
	var documentTable = $("#" + selectedId).DataTable({
		paging : false,
		searching : false,
		destroy : true,
		retrieve : true
	});
	documentTable.destroy();
}