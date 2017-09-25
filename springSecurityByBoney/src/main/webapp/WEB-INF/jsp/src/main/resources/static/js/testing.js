$(document).ready(function() {
	$("#testForm").validate();
	$('#testingModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});

	$('#testingShowModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$("#testingModal").on("hidden.bs.modal", function() {
		closeTestingModal();
		hideAllScoreErrorField();
	});
	$("#testingShowModal").on("hidden.bs.modal", function() {
		closeTestingModal();
		hideAllScoreErrorField();
	});
	hideAllScoreFileds();
	hideAllScoreErrorField();
	getStudentTestings();
	$("#overall-band-score").attr('placeholder', "Total Score");
	$("#test-taken-on").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});

	$("#testForm").validate();
	var testType = "";
});

function hideAllScoreFileds() {
	$("#verbal-score-toggle").hide();
	$("#quant-score_toggle").hide();
	$("#analytical-score_toggle").hide();
	$("#reading-score_toggle").hide();
	$("#writing-score_toggle").hide();
	$("#listening-score_toggle").hide();
	$("#speaking-score_toggle").hide();
	$("#integrated-reasoning-score_toggle").hide();

}
function closeTestingModal() {
	$('#testForm')[0].reset();
	$("#studentTestingGuid").val("");
	$('#test_type').attr('disabled', false);
	hideAllScoreFileds();
	hideAllScoreErrorField();
	var validator = $('#testForm').validate();

	validator.resetForm();
}

function getStudentTestings() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentTestings",
		dataType : 'json',
		success : function(results) {
			processStudentTestings(results);
		},
		error : function(e) {

			$.alert({
				content : 'Error While Loading Tests.Please Try Again',
			});
		}
	});
}
var completedTestingSectionsCountIncremented = false;
var testsList = [];
function processStudentTestings(results) {
	testsList = results;
	$("#tests_list_tbody").empty();
	if (testsList.length > 0) {
		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#testing_completed").html(statusHtml);
		if (completedTestingSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedTestingSectionsCountIncremented = true;
		}

		var docsRows = "";
		for (var i = 0; i < testsList.length; i++) {
			var eachDoc = testsList[i];
			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["testType"]
					+ "</td><td>"
					+ eachDoc["testTakenOn"]
					+ "</td><td>"
					+ eachDoc["overAllBandScore"]
					+ "<a class='fa fa-eye edit-delete-icon' data-toggle='modal' data-target='#testingShowModal' onclick=\"viewStudentTesting('"
					+ i
					+ "')\"></a></td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#testingModal' onclick=\"editStudentTesting('"
					+ i
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentTesting('"
					+ eachDoc["studentTestingGuid"] + "')\"></a></td></tr>";
		}
		$("#tests_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#testing_completed").html(statusHtml);
		if (completedTestingSectionsCountIncremented == true) {
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedTestingSectionsCountIncremented = false;
		}
		$("#tests_list_tbody").append(
				"<tr><td colspan='5' align='center'>No Tests Found</td></tr>");
	}

}

function editStudentTesting(selectedRecordIndex) {
	var eachDoc = testsList[selectedRecordIndex];
	$('#test_type').attr('disabled', true);
	$("#studentTestingGuid").val(eachDoc["studentTestingGuid"]);
	$('#test_type').val(eachDoc["testType"]);
	$("#test-taken-on").val(eachDoc["testTakenOn"]);
	$("#verbal-score").val(eachDoc["verbalScore"]);
	$("#quant-score").val(eachDoc["quantScore"]);
	$("#analytical-score").val(eachDoc["analyticalScore"]);
	$("#reading-score").val(eachDoc["readingScore"]);
	$("#writing-score").val(eachDoc["writingScore"]);
	$("#listening-score").val(eachDoc["listeningScore"]);
	$("#speaking-score").val(eachDoc["speakingScore"]);
	$("#overall-band-score").val(eachDoc["overAllBandScore"]);
	$("#integrated-reasoning-score").val(eachDoc["integratedReasoningScore"]);
	testTypeChange();

}

function viewStudentTesting(selectedRecordIndex) {
	var eachDoc = testsList[selectedRecordIndex];
	var testType = eachDoc["testType"];
	$("#verbal-score-text").text(eachDoc["verbalScore"]);
	$("#quant-score-text").text(eachDoc["quantScore"]);
	$("#analytical-score-text").text(eachDoc["analyticalScore"]);
	$("#reading-score-text").text(eachDoc["readingScore"]);
	$("#writing-score-text").text(eachDoc["writingScore"]);
	$("#listening-score-text").text(eachDoc["listeningScore"]);
	$("#speaking-score-text").text(eachDoc["speakingScore"]);
	$("#reasoning-score-text").text(eachDoc["integratedReasoningScore"]);

	testTypeChangeView(testType);
}

function testTypeChangeView(testType) {

	if (testType == "GRE") {
		$("#verbal_score_view").show();
		$("#quantative_score_view").show();
		$("#analytical_score_view").show();
		$("#reading_score_view").hide();
		$("#writing_score_view").hide();
		$("#listening_score_view").hide();
		$("#speking_score_view").hide();
		$("#reasoning_score_view").hide();

	} else if (testType == "TOEFL") {
		$("#verbal_score_view").hide();
		$("#quantative_score_view").hide();
		$("#analytical_score_view").hide();
		$("#reading_score_view").show();
		$("#listening_score_view").show();
		$("#speking_score_view").show();
		$("#writing_score_view").show();
		$("#reasoning_score_view").hide();

	} else if (testType == "GMAT") {
		$("#verbal_score_view").show();
		$("#quantative_score_view").show();
		$("#analytical_score_view").show();
		$("#reading_score_view").hide();
		$("#writing_score_view").hide();
		$("#listening_score_view").hide();
		$("#speking_score_view").hide();
		$("#reasoning_score_view").show();
	}
}

function deleteStudentTesting(studentTestingGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Writing ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "student/deleteStudentTesting",
									data : studentTestingGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {

										$
												.alert({
													content : 'Test Deleted Successfully',
												});
										getStudentTestings();
									},
									error : function(e) {
										$
												.alert({
													content : 'Error While Deleting Test.Please Try Again',
												});

									}
								});
					},
				}
			});

}

function addTesting() {
	if ($("#testForm").valid() && validScore()) {
		$("#add_testing_btn").prop("disabled", true);
		var data = {};
		data["studentTestingGuid"] = $("#studentTestingGuid").val();
		data["testType"] = $("#test_type").val();
		data["testTakenOn"] = $("#test-taken-on").val();
		data["verbalScore"] = $("#verbal-score").val();
		data["quantScore"] = $("#quant-score").val();
		data["analyticalScore"] = $("#analytical-score").val();
		data["readingScore"] = $("#reading-score").val();
		data["writingScore"] = $("#writing-score").val();
		data["listeningScore"] = $("#listening-score").val();
		data["speakingScore"] = $("#speaking-score").val();
		data["integratedReasoningScore"] = $("#integrated-reasoning-score")
				.val();
		data["overAllBandScore"] = $("#overall-band-score").val();

		$.ajax({
			url : 'student/saveStudentTesting',
			type : "POST",
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(data),
		}).done(function(data) {
			closeTestingModal();
			$('#test_type').attr('disabled', false);
			$('#testingModal .close').click();
			$("#add_testing_btn").prop("disabled", false);
			$.alert({
				content : "Test Added Successfully",
			});

			getStudentTestings();
		}).fail(function(jqXHR, textStatus) {

			$.alert({
				content : "Error While Saving Test.Please Try Again",
			});

		});
	} else {
		$.alert({
			content : "Please Enter Valid Score",
		});
	}
}
function testTypeChange() {
	testType = $("#test_type").val();
	if (testType == "GRE") {
		$("#verbal-score-toggle").show();
		$("#verbal-score").attr("placeholder", "130 - 170");
		$("#quant-score_toggle").show();
		$("#quant-score").attr("placeholder", "130 - 170");
		$("#analytical-score_toggle").show();
		$("#reading-score_toggle").hide();
		$("#writing-score_toggle").hide();
		$("#listening-score_toggle").hide();
		$("#speaking-score_toggle").hide();
		$("#integrated-reasoning-score_toggle").hide();
		$("#overall-band-score").attr('placeholder', "260 - 340");
	} else if (testType == "TOEFL") {
		$("#verbal-score-toggle").hide();
		$("#quant-score_toggle").hide();
		$("#analytical-score_toggle").hide();
		$("#reading-score_toggle").show();
		$("#writing-score_toggle").show();
		$("#listening-score_toggle").show();
		$("#speaking-score_toggle").show();
		$("#integrated-reasoning-score_toggle").hide();
		$("#overall-band-score").attr("placeholder", "0 - 120");
	} else if (testType == "GMAT") {
		$("#verbal-score-toggle").show();
		$("#verbal-score").attr("placeholder", "0 - 60");
		$("#quant-score_toggle").show();
		$("#quant-score").attr("placeholder", "0 - 60");
		$("#analytical-score_toggle").show();
		$("#integrated-reasoning-score_toggle").show();
		$("#reading-score_toggle").hide();
		$("#writing-score_toggle").hide();
		$("#listening-score_toggle").hide();
		$("#speaking-score_toggle").hide();
		$("#overall-band-score").attr("placeholder", "200 - 800");

	}
}
function isNumber(event) {
	event = (event) ? event : window.event;
	var charCode = (event.which) ? event.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}
function isValidStudentScore(inputId) {
	if (testType == "GRE") {
		var verbalScore = "";
		if (inputId == "verbal-score") {
			verbalScore = $("#verbal-score").val();
			if ($.inArray(parseInt(verbalScore), getIntervalArray(130, 170, 1)) == -1) {
				$("#verbal-score-error-hidden").show();
			} else {
				$("#verbal-score-error-hidden").hide();
			}
		}
		if (inputId == "quant-score") {
			var quantitativeScore = "";
			quantitativeScore = $("#quant-score").val();
			if ($.inArray(parseInt(quantitativeScore), getIntervalArray(130,
					170, 1)) == -1) {
				$("#quant-score-error-hidden").show();
			} else {
				$("#quant-score-error-hidden").hide();

			}
		}
		if (inputId == "analytical-score") {
			var analyticalScore = $("#analytical-score").val();
			var isFind = $.inArray(parseFloat(analyticalScore),
					getIntervalArray(0.0, 6.0, 0.5)) == -1;
			if (isFind) {
				$("#analytical-score-error-hidden").show();
			} else {
				$("#analytical-score-error-hidden").hide();
			}
		}
		if (inputId == "overall-band-score") {
			var totalScore = $("#overall-band-score").val();
			if ($.inArray(parseInt(totalScore), getIntervalArray(260, 340, 1)) == -1) {
				$("#total-score-error").show();
			} else {
				$("#total-score-error").hide();
			}

		}

	} else if (testType == "GMAT") {
		if (inputId == "verbal-score") {
			var verbalScore = $("#verbal-score").val();
			if (verbalScore >= 0 && verbalScore <= 60) {
				$("#verbal-score-error-hidden").hide();

			} else {
				$("#verbal-score-error-hidden").show();
			}
		}
		if (inputId == "quant-score") {
			var quantitativeScore = $("#quant-score").val();
			if (quantitativeScore >= 0 && quantitativeScore <= 60) {
				$("#quant-score-error-hidden").hide();
			} else {
				$("#quant-score-error-hidden").show();
			}
		}
		if (inputId == "integrated-reasoning-score") {
			var integratedReasoningScore = $("#integrated-reasoning-score")
					.val();
			if (integratedReasoningScore >= 1 && integratedReasoningScore <= 8) {
				$("#reasoning-score-error").hide();
			} else {
				$("#reasoning-score-error").show();
			}
		}
		if (inputId == "analytical-score") {
			var analyticalScore = $("#analytical-score").val();
			var isFind = $.inArray(parseFloat(analyticalScore),
					getIntervalArray(0.0, 6.0, 0.5)) == -1;
			if (isFind) {
				$("#analytical-score-error-hidden").show();
			} else {
				$("#analytical-score-error-hidden").hide();
			}
		}
		if (inputId == "overall-band-score") {
			var totalScore = $("#overall-band-score").val();
			if ($.inArray(parseInt(totalScore), getIntervalArray(200, 800, 10)) == -1) {
				$("#total-score-error").show();
			} else {
				$("#total-score-error").hide();
			}
		}
	} else if (testType == "TOEFL") {
		if (inputId == "reading-score") {
			var readingScore = $("#reading-score").val();
			if (readingScore >= 0 && readingScore <= 30) {
				$("#reading-score-error-hidden").hide();
			} else {
				$("#reading-score-error-hidden").show();
			}
		}
		if (inputId == "writing-score") {
			var readingScore = $("#writing-score").val();
			if (readingScore >= 0 && readingScore <= 30) {
				$("#writing-score-error-hidden").hide();
			} else {
				$("#writing-score-error-hidden").show();
			}
		}
		if (inputId == "listening-score") {
			var listeningScore = $("#listening-score").val();
			if (listeningScore >= 0 && listeningScore <= 30) {
				$("#listening-score-error").hide();

			} else {
				$("#listening-score-error").show();
			}
		}
		if (inputId == "speaking-score") {
			var speakingScore = $("#speaking-score").val();
			if (speakingScore >= 0 && speakingScore <= 30) {
				$("#speaking-score-error-hidden").hide();

			} else {
				$("#speaking-score-error-hidden").show();
			}
		}
		if (inputId == "overall-band-score") {
			var totalScore = $("#overall-band-score").val();
			if ($.inArray(parseInt(totalScore), getIntervalArray(0, 120, 1)) == -1) {
				$("#total-score-error").show();
			} else {
				$("#total-score-error").hide();
			}
		}
	}
}
function hideAllScoreErrorField() {
	$("#verbal-score-error-hidden").hide();
	$("#analytical-score-error").hide();
	$("#quant-score-error-hidden").hide();
	$("#listening-score-error").hide();
	$("#reading-score-error-hidden").hide();
	$("#speaking-score-error-hidden").hide();
	$("#writing-score-error-hidden").hide();
	$("#reasoning-score-error").hide();
	$("#total-score-error").hide();
	$("#analytical-score-error-hidden").hide();
}

function getIntervalArray(min, max, interval) {
	var results = [];
	for (var i = min; min <= max; i++) {
		results.push(min);
		min = min + interval;
	}
	return results;
}

function validScore() {
	var isValid = false;
	if ($("#verbal-score-error-hidden").is(":visible")
			|| $("#analytical-score-error").is(":visible")
			|| $("#quant-score-error").is(":visible")
			|| $("#listening-score-error").is(":visible")
			|| $("#reading-score-error").is(":visible")
			|| $("#speaking-score-error-hidden").is(":visible")
			|| $("#writing-score-error-hidden").is(":visible")
			|| $("#reasoning-score-error").is(":visible")
			|| $("#total-score-error").is(":visible")
			|| $("#analytical-score-error-hidden").is(":visible")) {
		isValid = false;
	} else {
		isValid = true;
	}
	return isValid;
}
