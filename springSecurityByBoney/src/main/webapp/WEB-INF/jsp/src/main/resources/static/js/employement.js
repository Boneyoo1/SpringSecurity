$(document).ready(function() {
	$('#employmentModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$("#employement-start-date").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#employement-end-date").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#employementForm").validate();
	$("#worked_state").hide();

	$("#employmentModal").on("hidden.bs.modal", function() {
		closeEmployeeModal();
	});
	getStudentEmployement();
	var completedSectionsCountIncremented = false;

	$("#supervisor_phone_number").intlTelInput({
		allowExtensions : true,
		autoFormat : false,
		autoHideDialCode : false,
		initialCountry : "auto",
		nationalMode : false,
		preventInvalidNumbers : true,
		utilsScript : "js/vendor/utils.js"
	});

});
var completedEmployeeSectionsCountIncremented = false;
function closeEmployeeModal() {
	$("#supervisor_phone_number").intlTelInput("setCountry", "");
	$("label.error").hide();
	$(".error").removeClass("error");
	$('#employementForm')[0].reset();
	$("#studentEmployementGuid").val("");
}

function getStudentEmployement() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentEmployement",
		dataType : 'json',
		success : function(results) {
			processStudentEmployement(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Employements. Please Try Again"
			});
		}
	});
}

var emploeyementsList = [];
function processStudentEmployement(results) {

	emploeyementsList = results;
	$("#employements_list_tbody").empty();
	if (emploeyementsList.length > 0) {
		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#employement_completed").html(statusHtml);
		if (completedEmployeeSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedEmployeeSectionsCountIncremented = true;
		}
		var docsRows = "";
		for (var i = 0; i < emploeyementsList.length; i++) {
			var eachDoc = emploeyementsList[i];
			if (eachDoc["employementCountryGuid"] == "95") {
				loadIndiaStates();
			} else if (eachDoc["employementCountryGuid"] == "215") {
				loadUSStates();
			}

			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["positionTitle"]
					+ "</td><td>"
					+ eachDoc["employer"]
					+ "</td>"
					+ "<td>"
					+ eachDoc["startDate"]
					+ "</td><td>"
					+ eachDoc["endDate"]
					+ "</td><td>"
					+ eachDoc["city"]
					+ "</td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#employmentModal' onclick=\"editStudentEmployment('"
					+ i
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentEmploeyement('"
					+ eachDoc["studentEmployementGuid"] + "')\"></a></td></tr>";
		}
		$("#employements_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#employement_completed").html(statusHtml);
		if (completedEmployeeSectionsCountIncremented == true) {
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedEmployeeSectionsCountIncremented = false;
		}
		$("#employements_list_tbody")
				.append(
						"<tr><td colspan='7' align='center'>No Employement Found</td></tr>");
	}
}

function editStudentEmployment(selectedRecordIndex) {

	var flag = "true";
	var data = emploeyementsList[selectedRecordIndex];
	$("#worked_country").val(data["employementCountryGuid"]);
	var workCountryGuid = data["employementCountryGuid"];
	if (workCountryGuid == "95") {
		$("#worked_state").show();
		$("#worked_state_input").hide();
		loadIndiaStates();
	} else if (workCountryGuid == "215") {
		$("#worked_state").show();
		$("#worked_state_input").hide();
		loadUSStates();
	} else {
		flag = "false";
		$("#worked_state").hide();
		$("#worked_state_input").show();
		$("#worked_state_input").val(data["state"]);
	}
	$("#employer_name").val(data["employer"]);
	$("#position").val(data["positionTitle"]);
	$("#employement-start-date").val(data["startDate"]);
	$("#employement-end-date").val(data["endDate"]);
	$("#job_category").val(data["jobCategory"]);
	$("#industry_category").val(data["industryCategory"]);
	$("#supervisor_name").val(data["supervisorName"]);
	$("#supervisor_phone_number").intlTelInput("setNumber",
			data["supervisorPhoneNumber"]);
	$("#supervisor_email").val(data["supervisorEmail"]);
	$("#worked_city").val(data["city"]);
	$("#worked_country").val(data["employementCountryGuid"]);
	$("#studentEmployementGuid").val(data["studentEmployementGuid"]);
	if (flag == "true") {
		setTimeout(function() {
			setState(data["state"]);
		}, 200);

	}
}
// @Author Indrajit
// To trigger click event after Loading page 1 Time
function setState(data) {
	$(function() {
		$('.worked_state').click(function() {
			var worked_state = data;
			$('select[name=worked_state]').val(data);
		});
		$('#worked_state').val(worked_state).trigger('click');
		$("#worked_state").unbind("click");
	});
}
function loadStatesByCountryGuid() {
	$("#worked_state_input").hide();
	$("#worked_state").show();
	var workCountryGuid = $("#worked_country").val();
	if (workCountryGuid == "95") {
		loadIndiaStates();
	} else if (workCountryGuid == "215") {
		loadUSStates();
	} else {
		$("#worked_state").hide();
		$("#worked_state_input").show();
	}
}
function loadUSStates() {
	$("#worked_state").empty().html("<option value=' ' >Select State</option>");
	$.getJSON("data/USStates.json", function(data) {
		var stateNameHtml = "";
		$.each(data,
				function(key, val) {
					stateNameHtml += "<option value='" + key + "'>" + val
							+ "</option>";
				});
		$("#worked_state").append(stateNameHtml);
	});
}
function loadIndiaStates() {
	$("#worked_state").empty().html("<option value=' ' >Select State</option>")
	$.getJSON("data/IndianStates.json", function(data) {
		var stateNameHtml = "";
		$.each(data,
				function(key, val) {
					stateNameHtml += "<option value='" + key + "'>" + val
							+ "</option>";
				});
		$("#worked_state").append(stateNameHtml);
	});
}
function deleteStudentEmploeyement(studentEmployementGuid) {
	$
			.confirm({
				content : 'Are You Sure To Delete This Employement ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "student/deleteStudentEmployement",
									data : studentEmployementGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Employement Deleted Successfully"
												});
										getStudentEmployement();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Education. Please Try Again"
												});
									}
								});
					},
				}
			});

}

function addEmployement() {
	if ($("#employementForm").valid()) {
		$("#add_employement_button").prop("disabled", true);
		var data = {};

		data["employer"] = $("#employer_name").val();
		data["positionTitle"] = $("#position").val();
		data["startDate"] = $("#employement-start-date").val();
		data["endDate"] = $("#employement-end-date").val();
		data["jobCategory"] = $("#job_category").val();
		data["industryCategory"] = $("#industry_category").val();
		data["supervisorName"] = $("#supervisor_name").val();
		data["supervisorPhoneNumber"] = $("#supervisor_phone_number").val();
		data["supervisorEmail"] = $("#supervisor_email").val();
		data["city"] = $("#worked_city").val();
		data["employementCountryGuid"] = $("#worked_country").val();
		if (data["employementCountryGuid"] == "95"
				|| data["employementCountryGuid"] == "215") {
			data["state"] = $("#worked_state").val();
		} else {
			data["state"] = $("#worked_state_input").val();
		}
		data["studentEmployementGuid"] = $("#studentEmployementGuid").val();

		$.ajax({
			url : 'student/saveStudentEmployement',
			type : "POST",
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(data),
		}).done(function(data) {
			$('#employmentModal').modal('toggle');
			closeEmployeeModal();
			$("#add_employement_button").prop("disabled", false);
			$.alert({
				content : "Employement Added Successfully"
			});
			$('#employmentModal .close').click();
			getStudentEmployement();
		}).fail(function(jqXHR, textStatus) {
			$.alert({
				content : 'Error While Saving Employement. Please Try Again'
			});
		});
	}
}
function supervisorPhoneNumberValidation(phoneNumberId) {
	var telInput = $("#" + phoneNumberId);
	telInput, errorMsg = $("#supervisor_phone_number-error-msg"),
			validMsg = $("#supervisor_phone_number-valid-msg");
	telInput.intlTelInput({
		utilsScript : "js/vendor/utils.js"
	});
	var reset = function() {
		errorMsg.addClass("hide");
		validMsg.addClass("hide");
	};
	telInput.blur(function() {
		reset();
		if ($.trim(telInput.val())) {
			if (telInput.intlTelInput("isValidNumber")) {
				validMsg.removeClass("hide");
			} else {

				errorMsg.removeClass("hide");
			}
		}
	});
}