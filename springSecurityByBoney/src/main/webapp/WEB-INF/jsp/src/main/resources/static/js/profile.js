$(document).ready(function() {

	$('body').css({
		'marginRight' : '-17px'
	});
	$("#form-user-mailing-state-input").hide();
	$("#form-user-permanent-state-input").hide();
	$("#form-user-emergency-state-input").hide();
	$('#form-user-dob').datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#form-user-home-contact-no").intlTelInput({
		allowExtensions : true,
		autoFormat : false,
		autoHideDialCode : false,
		initialCountry : "auto",
		nationalMode : false,
		preventInvalidNumbers : true,
		utilsScript : "js/vendor/utils.js"
	});
	$("#form-user-work-contact-no").intlTelInput({
		allowExtensions : true,
		autoFormat : false,
		autoHideDialCode : false,
		initialCountry : "auto",
		nationalMode : false,
		preventInvalidNumbers : true,
		utilsScript : "js/vendor/utils.js"
	});
	$("#form-emergency-contact-no").intlTelInput({
		allowExtensions : true,
		autoFormat : false,
		autoHideDialCode : false,
		initialCountry : "auto",
		nationalMode : false,
		preventInvalidNumbers : true,
		utilsScript : "js/vendor/utils.js"
	});

	showPersonalInfo();
	$("#profile-form").validate();
	$("input:radio[name=form-user-gender]:first").attr('checked', true);
	$("input:radio[name=addressradio]:last").attr('checked', true);
	$("input:radio[name=uscitizen]:last").attr('checked', true);
	$("input:radio[name=emergencyaddressradio]:last").attr('checked', true);
	getStudentProfile();

});
var completedProfileSectionsCountIncremented = false;
function loadBaseData() {
	getAllCountries();
	getAllLanguages();
	getAllReliegions();
	getAllVisaTypes();
}

function loadAllStates(data) {

}
function getStudentProfile() {
	$
			.ajax({
				type : "POST",
				contentType : "application/json",
				url : "student/getStudentProfile",
				dataType : 'json',
				success : function(results) {
					processStudentProfile(results);
					loadBaseData();
					closeLoadingContainer();
					loadAllStates(results);
				},
				error : function(e) {
					$
							.alert({
								content : 'Error While Loading Student Profile. Please Try Again',
								btnClass : 'btn-blue'
							});
				}
			});
}
var citizenShipCountryGuid;
var countryOfBirthGuid;
var mailingAddressCountryGuid;
var permanentAddressCountryGuid;
var firstLanguageGuid;
var religiousGuid;
var visaTypeRequiredGuid;
var emergencyAddressCountryGuid;
var secondLanguageGuid;
var thirdLanguageGuid;
var permanentAddressState;
var mailingAddressState;
var emergencyAddressState;
function processStudentProfile(data) {
	citizenShipCountryGuid = data["citizenShipCountryGuid"];
	countryOfBirthGuid = data["countryOfBirthGuid"];
	mailingAddressCountryGuid = data["mailingAddressCountryGuid"];
	permanentAddressCountryGuid = data["permanentAddressCountryGuid"];
	firstLanguageGuid = data["firstLanguageGuid"];
	religiousGuid = data["religiousGuid"];
	visaTypeRequiredGuid = data["visaTypeRequiredGuid"];
	emergencyAddressCountryGuid = data["emergencyAddressCountryGuid"];
	secondLanguageGuid = data["secondLanguageGuid"];
	thirdLanguageGuid = data["thirdLanguageGuid"];
	permanentAddressState = data["permanentAddressState"];
	mailingAddressState = data["mailingAddressState"];
	emergencyAddressState = data["emergencyAddressState"];
	var profileCompleted = data["profileCompleted"];

	if (completedProfileSectionsCountIncremented == false
			&& profileCompleted == true) {
		completedSectionsCount++;
		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#profile_completed").html(statusHtml);
		$("#profile_completed_sections_count").text(completedSectionsCount);
		completedProfileSectionsCountIncremented = true;
	} else {
		if (completedProfileSectionsCountIncremented == true
				&& profileCompleted == false) {
			var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
			$("#profile_completed").html(statusHtml);
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedProfileSectionsCountIncremented = false;
		}
	}

	$("#student_profile_id").val(data["studentProfileGuid"]);
	$("#form-user-firstname").val(data["firstName"]);
	$("#form-user-middlename").val(data["middleName"]);
	$("#form-user-lastname").val(data["lastName"]);

	if (data["gender"] == "Female" || data["gender"] === "Female") {
		$("input:radio[name=form-user-gender]:last").attr('checked', true);
	} else {
		$("input:radio[name=form-user-gender]:first").attr('checked', true);
	}
	$("#form-user-dob").val(data["dateOfBirth"]);
	$("#form-user-permanent-address-line-1").val(data["permanentAddressLine1"]);
	$("#form-user-permanent-address-line-2").val(data["permanentAddressLine2"]);
	$("#form-user-permanent-address-line-3").val(data["permanentAddressLine3"]);
	$("#form-user-permanent-city").val(data["permanentAddressCity"]);
	$("#form-user-permanent-country").val(data["permanentAddressCountryGuid"]);

	if (permanentAddressCountryGuid == "95") {
		getIndiaStates('form-user-permanent-state');
		setTimeout(function() {
			setAllState("form-user-permanent-state", permanentAddressState);
		}, 200);
	} else if (permanentAddressCountryGuid == "215") {
		getUSStates('form-user-permanent-state');
		setTimeout(function() {
			setAllState("form-user-permanent-state", permanentAddressState);
		}, 200);
	} else {
		$("#form-user-permanent-state").hide();
		$("#form-user-permanent-state-input").show();
		$("#form-user-permanent-state-input").val(permanentAddressState);

	}

	$("#form-user-permanent-zip-code").val(data["permanentAddressZipCode"]);
	if (data["mailingAddressSameAsPermanent"] == "Yes"
			|| data["mailingAddressSameAsPermanent"] === "Yes") {
		$("input:radio[name=addressradio]:first").attr('checked', true);
	} else {
		$("input:radio[name=addressradio]:last").attr('checked', true);
	}

	$("#form-user-mailing-address-line-1").val(data["mailingAddressLine1"]);
	$("#form-user-mailing-address-line-2").val(data["mailingAddressLine2"]);
	$("#form-user-mailing-address-line-3").val(data["mailingAddressLine3"]);
	$("#form-user-mailing-city").val(data["mailingAddressCity"]);
	$("#form-user-mailing-country").val(data["mailingAddressCountryGuid"]);
	$("#form-user-mailing-zip-code").val(data["mailingAddressZipCode"]);

	if (mailingAddressCountryGuid == "95") {
		getIndiaStates('form-user-mailing-state');
		setTimeout(function() {
			setAllState("form-user-mailing-state", mailingAddressState);
		}, 200);
	} else if (mailingAddressCountryGuid == "215") {
		getUSStates('form-user-mailing-state');
		setTimeout(function() {
			setAllState("form-user-mailing-state", mailingAddressState);
		}, 200);
	} else {
		$("#form-user-mailing-state").hide();
		$("#form-user-mailing-state-input").show();
		$("#form-user-mailing-state-input").val(mailingAddressState);
	}
	$("#form-user-email").val(data["emailAddress"]);
	var homeContactNo = data["homeContactNo"];
	if (homeContactNo == null) {
		$("#form-user-home-contact-no").val(data["homeContactNo"]);
	} else {
		$("#form-user-home-contact-no").intlTelInput("setNumber",
				data["homeContactNo"]);
	}
	var workContactNo = data["workContactNo"];
	if (workContactNo == null) {
		$("#form-user-work-contact-no").val(data["homeContactNo"]);
	} else {
		$("#form-user-work-contact-no").intlTelInput("setNumber",
				data["workContactNo"]);

	}
	$("#user-country-of-birth").val(data["countryOfBirthGuid"]);
	$("#form-user-cob").val(data["cityOfBirth"]);
	$("#user-religious").val(data["religiousGuid"]);
	$("#form-user-ethnicity").val(data["ethnicity"]);
	$("#user-citizenship-visa-type").val(data["visaTypeRequiredGuid"]);
	if (data["usCitizen"] == "Yes" || data["gender"] === "Yes") {
		if (data["usCitizen"] == "Yes") {
			$("input:radio[name=uscitizen]:first").attr('checked', true);
			var countryRows = "<option value='" + 215 + "'>" + "United States"
					+ "</option>";
			$("#user-citizenship-country").html(countryRows);
			var visaTypeRows = "<option value='" + "" + "'>" + "N/A"
					+ "</option>";
			$("#user-citizenship-visa-type").html(visaTypeRows);

		}
	} else {
		$("input:radio[name=uscitizen]:last").attr('checked', true);
	}

	$("#user-citizenship-country").val(data["citizenShipCountryGuid"]);
	$("#user-primary-language").val(data["primaryLanguageGuid"]);

	$("#form-emergency-contact-name").val(data["emergencyContactName"]);
	$("#form-emergency-contact-no").val(data["emergencyContactNo"]);

	var emergencyContactNo = data["emergencyContactNo"];
	if (emergencyContactNo == null) {
		$("#form-emergency-contact-no").val(data["emergencyContactNo"]);
	} else {
		$("#form-emergency-contact-no").intlTelInput("setNumber",
				data["emergencyContactNo"]);
	}

	$("#form-emergency-contact-relationship").val(
			data["emergencyContactRelation"]);

	$("#form-user-suffix").val(data["suffix"]);
	$("#form-user-marital-status").val(data["maritalStatus"]);

	$("#form-user-emergency-address-line-1").val(data["emergencyAddressLine1"]);
	$("#form-user-emergency-address-line-2").val(data["emergencyAddressLine2"]);
	$("#form-user-emergency-address-line-3").val(data["emergencyAddressLine3"]);
	$("#form-user-emergency-city").val(data["emergencyAddressCity"]);
	$("#form-user-emergency-zip-code").val(data["emergencyAddressZipCode"]);

	if (data["emergencyAddressSameAsPermanent"] == "Yes"
			|| data["emergencyAddressSameAsPermanent"] === "Yes") {
		$("input:radio[name=emergencyaddressradio]:first")
				.attr('checked', true);
	} else {
		$("input:radio[name=emergencyaddressradio]:last").attr('checked', true);
	}

	if (emergencyAddressCountryGuid == "95") {
		getIndiaStates('form-user-emergency-state');
		setTimeout(function() {
			setAllState("form-user-emergency-state", emergencyAddressState);
		}, 200);
	} else if (emergencyAddressCountryGuid == "215") {
		getUSStates('form-user-emergency-state');
		setTimeout(function() {
			setAllState("form-user-emergency-state", emergencyAddressState);
		}, 200);

	} else {
		$("#form-user-emergency-state").hide();
		$("#form-user-emergency-state-input").show();
		$("#form-user-emergency-state-input").val(emergencyAddressState);
	}

	var userEmail = $("#email_address_hidden").val();
	$("#form-user-firstname").val(data["firstName"]);
	$("#form-user-lastname").val(data["lastName"]);
	$("#form-user-email").val(userEmail);
}

function showPersonalInfo() {
	$(".profile-info-content").hide();
	$("#personal-info").show();
	$('.profile_tab_personal_info').css("background-color", "ghostwhite");
	$('.profile_tab_permanent_address').css("background-color", "#ffffff");
	$('.profile_tab_mailing_address').css("background-color", "#ffffff");
	$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
	$('.profile_tab_geographic_info').css("background-color", "#ffffff");
	$('.profile_tab_contact_info').css("background-color", "#ffffff");
	$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

}
function gotoPermanentAddressInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#permanent-address-info").show();
		$('.profile_tab_permanent_address').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_geographic_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoMailingInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#mailing-address-info").show();
		$('.profile_tab_mailing_address').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_geographic_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoContactInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#contact-info").show();
		$('.profile_tab_contact_info').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_geographic_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoDemographicsInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#demographic-info").show();
		$('.profile_tab_geographic_info').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoGeographicInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#geographic-info").show();
		$('.profile_tab_geographic_info').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoCitizenshipInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#citizenship-info").show();
		$('.profile_tab_citizenship_info').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_geographic_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");
		$('.profile_tab_emergency_contact').css("background-color", "#ffffff");

	}
}
function gotoEmergencyContactInfo() {
	if ($("#profile-form").valid()) {
		$(".profile-info-content").hide();
		$("#emergency-contact-info").show();
		$('.profile_tab_emergency_contact').css("background-color", "ghostwhite");
		$('.profile_tab_personal_info').css("background-color", "#ffffff");
		$('.profile_tab_permanent_address').css("background-color", "#ffffff");
		$('.profile_tab_mailing_address').css("background-color", "#ffffff");
		$('.profile_tab_citizenship_info').css("background-color", "#ffffff");
		$('.profile_tab_geographic_info').css("background-color", "#ffffff");
		$('.profile_tab_contact_info').css("background-color", "#ffffff");

	}
}
var mailingAddressLine1 = $("#form-user-mailing-address-line-1").val();
var mailingAddressLine2 = $("#form-user-mailing-address-line-2").val();
var mailingAddressLine3 = $("#form-user-mailing-address-line-3").val();
var mailingAddressCity = $("#form-user-mailing-city").val();
var mailingAddressState = $("#form-user-mailing-state").val();
var mailingAddressCountry = $("#form-user-mailing-country").val();
var mailingAddressZipCode = $("#form-user-mailing-zip-code").val();

function makeMailingAddressInfo() {
	var mailingAddressSameAsPermanent = $('input[name=addressradio]:checked',
			'#profile-form').val();
	if (mailingAddressSameAsPermanent == "Yes"
			|| mailingAddressSameAsPermanent === "Yes") {

		$("#form-user-mailing-address-line-1").val(
				$("#form-user-permanent-address-line-1").val());
		$("#form-user-mailing-address-line-2").val(
				$("#form-user-permanent-address-line-2").val());
		$("#form-user-mailing-address-line-3").val(
				$("#form-user-permanent-address-line-3").val());
		$("#form-user-mailing-city").val($("#form-user-permanent-city").val());
		$("#form-user-mailing-country").val(
				$("#form-user-permanent-country").val());
		$("#form-user-mailing-zip-code").val(
				$("#form-user-permanent-zip-code").val());
		if ($("#form-user-permanent-country").val() == "95") {
			getIndiaStates('form-user-mailing-state');
		} else if ($("#form-user-permanent-country").val() == "215") {
			getUSStates('form-user-mailing-state');
		} else {
			$("#form-user-mailing-state").hide();
			$("#form-user-mailing-state-input").show();
			$("#form-user-mailing-state-input").val(
					$("#form-user-permanent-state-input").val());
		}
		setTimeout(function() {
			setAllState("form-user-mailing-state", $(
					"#form-user-permanent-state").val());
		}, 200);
	} else {
		getIndiaStates('form-user-mailing-state');
		$("#form-user-mailing-address-line-1").val(mailingAddressLine1);
		$("#form-user-mailing-address-line-2").val(mailingAddressLine2);
		$("#form-user-mailing-address-line-3").val(mailingAddressLine3);
		$("#form-user-mailing-city").val(mailingAddressCity);
		$("#form-user-mailing-state").val(mailingAddressState);
		$("#form-user-mailing-country").val(mailingAddressCountry);
		$("#form-user-mailing-zip-code").val(mailingAddressZipCode);

	}
}

var emergencyAddressLine1 = $("#form-user-emergency-address-line-1").val();
var emergencyAddressLine2 = $("#form-user-emergency-address-line-2").val();
var emergencyAddressLine3 = $("#form-user-emergency-address-line-3").val();
var emergencyAddressCity = $("#form-user-emergency-city").val();
var emergencyAddressState = $("#form-user-emergency-state").val();
var emergencyAddressCountry = $("#form-user-emergency-country").val();
var emergencyAddressZipCode = $("#form-user-emergency-zip-code").val();
function makeEmergencyAddressInfo() {
	var emergencyAddressSameAsPermanent = $(
			'input[name=emergencyaddressradio]:checked', '#profile-form').val();

	if (emergencyAddressSameAsPermanent == "Yes"
			|| emergencyAddressSameAsPermanent === "Yes") {

		$("#form-user-emergency-address-line-1").val(
				$("#form-user-permanent-address-line-1").val());
		$("#form-user-emergency-address-line-2").val(
				$("#form-user-permanent-address-line-2").val());
		$("#form-user-emergency-address-line-3").val(
				$("#form-user-permanent-address-line-3").val());
		$("#form-user-emergency-city")
				.val($("#form-user-permanent-city").val());
		$("#form-user-emergency-country").val(
				$("#form-user-permanent-country").val());
		$("#form-user-emergency-zip-code").val(
				$("#form-user-permanent-zip-code").val());
		if ($("#form-user-permanent-country").val() == "95") {
			getIndiaStates('form-user-emergency-state');
		} else if ($("#form-user-permanent-country").val() == "215") {
			getUSStates('form-user-emergency-state');

		} else {
			$("#form-user-emergency-state").hide();
			$("#form-user-emergency-state-input").show();
			$("#form-user-emergency-state-input").val(
					$("#form-user-permanent-state-input").val());
		}
		setTimeout(function() {
			setAllState("form-user-emergency-state", $(
					"#form-user-permanent-state").val());
		}, 200);

	} else {
		getIndiaStates('form-user-emergency-state');
		$("#form-user-emergency-address-line-1").val(emergencyAddressLine1);
		$("#form-user-emergency-address-line-2").val(emergencyAddressLine2);
		$("#form-user-emergency-address-line-3").val(emergencyAddressLine3);
		$("#form-user-emergency-city").val(emergencyAddressCity);
		$("#form-user-emergency-state").val(emergencyAddressState);
		$("#form-user-emergency-country").val(emergencyAddressCountry);
		$("#form-user-emergency-zip-code").val(emergencyAddressZipCode);

	}
}

function isUSCitizen(radioId) {
	if (radioId == "uscitizen") {
		var countryRows = "<option value='" + 215 + "'>" + "United States"
				+ "</option>";
		$("#user-citizenship-country").html(countryRows);
		var visaTypeRows = "<option value='" + 0 + "'>" + "N/A" + "</option>";
		$("#user-citizenship-visa-type").html(visaTypeRows);
	} else {
		if (allContries.length > 0) {
			var allRows = "<option value=''>Select Citizenship Country</option>";
			for (var i = 0; i < allContries.length; i++) {
				var eachRow = allContries[i];
				allRows += "<option value='" + eachRow["guid"] + "'>"
						+ eachRow["countryName"] + "</option>";
			}
			$("#user-citizenship-country").empty().append(allRows);
			getAllVisaTypes();

		}
	}
}
// To trigger click event after Loading page 1 Time
// unbind click event
function setAllState(selectForm, value) {
	var selectId = selectForm;
	var data = value;
	$(function() {
		$("#" + selectId).click(function() {
			var stateName = data;
			$("select[name=" + selectId + "]").val(stateName);
		});
		$("#" + selectId).val(data).trigger('click');
		$("#" + selectId).unbind("click");
	});
}

function loadStatesByCountry(selectId) {
	var countryGuid = "";
	if (selectId == "form-user-emergency-state") {
		countryGuid = $("#form-user-emergency-country").val();
		if (countryGuid == "95") {
			getIndiaStates(selectId);
		} else if (countryGuid == "215") {
			getUSStates(selectId);
		} else {
			$("#form-user-emergency-state").hide();
			$("#form-user-emergency-state-input").show();
		}

	} else if (selectId == "form-user-permanent-state") {
		countryGuid = $("#form-user-permanent-country").val();
		if (countryGuid == "95") {
			getIndiaStates(selectId);
		} else if (countryGuid == "215") {
			getUSStates(selectId);
		} else {
			$("#form-user-permanent-state").hide();
			$("#form-user-permanent-state-input").show();
		}

	} else if (selectId == "form-user-mailing-state") {
		countryGuid = $("#form-user-mailing-country").val();
		if (countryGuid == "95") {
			getIndiaStates(selectId);
		} else if (countryGuid == "215") {
			getUSStates(selectId);
		} else {
			$("#form-user-mailing-state").hide();
			$("#form-user-mailing-state-input").show();
		}

	}
}
function getIndiaStates(selectId) {
	$("#" + selectId + "-input").hide();
	$("#" + selectId).show();
	$("#" + selectId).empty().html("<option value=' '>Select State</option>");
	$.getJSON("data/IndianStates.json", function(data) {
		var stateNameHtml = "";
		$.each(data,
				function(key, val) {
					stateNameHtml += "<option value='" + key + "'>" + val
							+ "</option>";
				});
		$("#" + selectId).append(stateNameHtml);

	});
}
function getUSStates(selectId) {
	$("#" + selectId + "-input").hide();
	$("#" + selectId).show();
	$("#" + selectId).empty().html("<option value=' '>Select State</option>");
	$.getJSON("data/USStates.json", function(data) {
		var stateNameHtml = "";
		$.each(data,
				function(key, val) {
					stateNameHtml += "<option value='" + key + "'>" + val
							+ "</option>";
				});
		$("#" + selectId).append(stateNameHtml);
	});
}

function savePersonalInfo(buttonId) {
	if ($("#profile-form").valid()) {
		if (buttonId == "save-emergency-info") {
			saveStudentPersonaInfo(buttonId);
			getStudentProfile();
		} else {
			saveStudentPersonaInfo(buttonId);
		}
	}
}

function saveStudentPersonaInfo(buttonId) {

	var data = {};

	data["studentProfileGuid"] = $("#student_profile_id").val();
	data["firstName"] = $("#form-user-firstname").val();
	data["middleName"] = $("#form-user-middlename").val();
	data["lastName"] = $("#form-user-lastname").val();
	data["gender"] = $('input[name=form-user-gender]:checked', '#profile-form')
			.val();
	data["dateOfBirth"] = $("#form-user-dob").val();

	data["permanentAddressLine1"] = $("#form-user-permanent-address-line-1")
			.val();
	data["permanentAddressLine2"] = $("#form-user-permanent-address-line-2")
			.val();
	data["permanentAddressLine3"] = $("#form-user-permanent-address-line-3")
			.val();
	data["permanentAddressCity"] = $("#form-user-permanent-city").val();

	data["permanentAddressCountryGuid"] = $("#form-user-permanent-country")
			.val();
	var permanentAddressCountryGuid = data["permanentAddressCountryGuid"];
	if (permanentAddressCountryGuid == "95"
			|| permanentAddressCountryGuid == "215") {
		data["permanentAddressState"] = $("#form-user-permanent-state").val();
	} else {
		data["permanentAddressState"] = $("#form-user-permanent-state-input")
				.val();

	}
	data["permanentAddressZipCode"] = $("#form-user-permanent-zip-code").val();

	data["mailingAddressSameAsPermanent"] = $(
			'input[name=addressradio]:checked', '#profile-form').val();

	data["mailingAddressLine1"] = $("#form-user-mailing-address-line-1").val();
	data["mailingAddressLine2"] = $("#form-user-mailing-address-line-2").val();
	data["mailingAddressLine3"] = $("#form-user-mailing-address-line-3").val();
	data["mailingAddressCity"] = $("#form-user-mailing-city").val();

	data["mailingAddressCountryGuid"] = $("#form-user-mailing-country").val();

	if (data["mailingAddressCountryGuid"] == "95"
			|| data["mailingAddressCountryGuid"] == "215") {
		data["mailingAddressState"] = $("#form-user-mailing-state").val();
	} else {
		data["mailingAddressState"] = $("#form-user-mailing-state-input").val();
	}
	data["mailingAddressZipCode"] = $("#form-user-mailing-zip-code").val();

	data["emailAddress"] = $("#form-user-email").val();
	data["homeContactNo"] = $("#form-user-home-contact-no").intlTelInput(
			"getNumber");
	data["workContactNo"] = $("#form-user-work-contact-no").intlTelInput(
			"getNumber");

	data["countryOfBirthGuid"] = $("#user-country-of-birth").val();
	data["cityOfBirth"] = $("#form-user-cob").val();
	data["religiousGuid"] = $("#user-religious").val();
	data["ethnicity"] = $("#form-user-ethnicity").val();

	data["usCitizen"] = $('input[name=uscitizen]:checked', '#profile-form')
			.val();
	data["citizenShipCountryGuid"] = $("#user-citizenship-country").val();
	if (data["usCitizen"] == "Yes") {
		data["visaTypeRequiredGuid"] = " ";
	} else if ($("#user-citizenship-visa-type").val() == null
			&& $("#user-citizenship-visa-type").val() == ""
			&& $("#user-citizenship-visa-type").val() == undefined) {
		data["visaTypeRequiredGuid"] = 0;
	}

	data["firstLanguageGuid"] = $("#user-primary-language").val();

	data["emergencyContactName"] = $("#form-emergency-contact-name").val();
	data["emergencyContactNo"] = $("#form-emergency-contact-no").intlTelInput(
			"getNumber");
	data["emergencyContactRelation"] = $("#form-emergency-contact-relationship")
			.val();

	data["suffix"] = $("#form-user-suffix").val();
	data["maritalStatus"] = $("#form-user-marital-status").val();

	data["emergencyAddressSameAsPermanent"] = $(
			'input[name=emergencyaddressradio]:checked', '#profile-form').val();
	data["emergencyAddressLine1"] = $("#form-user-emergency-address-line-1")
			.val();
	data["emergencyAddressLine2"] = $("#form-user-emergency-address-line-2")
			.val();
	data["emergencyAddressLine3"] = $("#form-user-emergency-address-line-3")
			.val();
	data["emergencyAddressCity"] = $("#form-user-emergency-city").val();

	data["emergencyAddressCountryGuid"] = $("#form-user-emergency-country")
			.val();

	if (data["emergencyAddressCountryGuid"] == "95"
			|| data["emergencyAddressCountryGuid"] == "215") {
		data["emergencyAddressState"] = $("#form-user-emergency-state").val();
	} else {
		data["emergencyAddressState"] = $("#form-user-emergency-state-input")
				.val();

	}
	data["emergencyAddressZipCode"] = $("#form-user-emergency-zip-code").val();

	data["secondLanguageGuid"] = $("#user-second-language").val();
	data["thirdLanguageGuid"] = $("#user-third-language").val();

	$("#" + buttonId).prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "student/saveUpdateStudentProfile",
		data : JSON.stringify(data),
		async : true,
		dataType : 'json',
		success : function(data) {
			getStudentProfile();
			$("#student_profile_id").val(data);
			$("#" + buttonId).prop("disabled", false);

			$.alert({
				title : '',
				content : 'Student Profile Data Saved Successfully',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

			});

		},
		error : function(e) {

			$.alert({
				title : '',
				content : 'Error While Saving Student Profile Data',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

			});
			$("#" + buttonId).prop("disabled", false);
		}
	});
}

var allContries = "";
function getAllCountries() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllCountries",
		dataType : 'json',
		success : function(results) {
			allContries = results;
			processAllCountries(results);
		},
		error : function(e) {
			$.alert({
				title : '',
				content : 'Error While Loading Countries. Please Try Again',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

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
		$("#form-user-permanent-country").append(allRows);
		$("#form-user-mailing-country").append(allRows);
		$("#user-country-of-birth").append(allRows);
		$("#user-citizenship-country").append(allRows);
		$("#form-user-emergency-country").append(allRows);
		$("#worked_country").append(allRows);
		$("#form-user-permanent-country").val(permanentAddressCountryGuid);
		$("#form-user-mailing-country").val(mailingAddressCountryGuid);
		$("#user-country-of-birth").val(countryOfBirthGuid);
		$("#user-citizenship-country").val(citizenShipCountryGuid);
		$("#form-user-emergency-country").val(emergencyAddressCountryGuid);

	}
}

function getAllLanguages() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllLanguages",
		dataType : 'json',
		success : function(results) {
			processAllLanguages(results);
		},
		error : function(e) {
			$.alert({
				title : '',
				content : 'Error While Loading Languages. Please Try Again',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

			});
		}
	});
}
function processAllLanguages(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<option value='" + eachRow["guid"] + "'>"
					+ eachRow["languageName"] + "</option>";
		}
		$("#user-primary-language").append(allRows);
		$("#user-primary-language").val(firstLanguageGuid);

		$("#user-second-language").append(allRows);
		$("#user-second-language").val(secondLanguageGuid);

		$("#user-third-language").append(allRows);
		$("#user-third-language").val(thirdLanguageGuid);

	}
}
function getAllReliegions() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllReliegions",
		dataType : 'json',
		success : function(results) {
			processAllRelegions(results);
		},
		error : function(e) {
			$.alert({
				title : '',
				content : 'Error While Loading Religions. Please Try Again',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

			});

		}
	});
}

function processAllRelegions(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<option value='" + eachRow["guid"] + "'>"
					+ eachRow["religiousName"] + "</option>";
		}
		$("#user-religious").append(allRows);
		$("#user-religious").val(religiousGuid);
	}
}

function getAllVisaTypes() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllVisaTypes",
		dataType : 'json',
		success : function(results) {
			processAllVisaTypes(results);
		},
		error : function(e) {
			$.alert({
				title : '',
				content : 'Error While Loading Visa Types. Please Try Again',
				closeIcon : true,
				closeIconClass : 'fa fa-close',
				btnClass : 'btn',
				columnClass : 'col-md-5 col-md-offset-4',

			});
		}
	});
}

function processAllVisaTypes(results) {
	$("#user-citizenship-visa-type").empty().html(
			"<option value=''>F-1</option>");
	if (results.length > 0) {
		var allRows = "";
		for (var i = 1; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<option value='" + eachRow["guid"] + "'>"
					+ eachRow["visaTypeName"] + "</option>";
		}
		$("#user-citizenship-visa-type").append(allRows);
		$("#user-citizenship-visa-type").val(visaTypeRequiredGuid);
	}
}
function phoneNumberValidation(phoneNumberFieldId) {
	console.log("Control comes");
	if (phoneNumberFieldId != "", phoneNumberFieldId != undefined,
			phoneNumberFieldId != null) {
		if (phoneNumberFieldId == "form-user-home-contact-no") {
			var telInput = $("#form-user-home-contact-no");
			telInput, errorMsg = $("#form-user-home-error-msg"),
					validMsg = $("#form-user-home-valid-msg");
		}
		if (phoneNumberFieldId == "form-user-work-contact-no") {
			var telInput = $("#form-user-work-contact-no");
			telInput, errorMsg = $("#form-user-work-error-msg"),
					validMsg = $("#form-user-work-valid-msg");
		}
		if (phoneNumberFieldId == "form-emergency-contact-no") {
			var telInput = $("#form-emergency-contact-no");
			telInput, errorMsg = $("#form-emergency-error-msg"),
					validMsg = $("#form-emergency-valid-msg");
		}
		errorMsg.hide();
		validMsg.hide();
		telInput.intlTelInput({
			utilsScript : "js/vendor/utils.js"
		});
		var reset = function() {
			errorMsg.show();
			validMsg.show();
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
}

function isNumber(event) {

	event = (event) ? event : window.event;
	var charCode = (event.which) ? event.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else {
		return true;

	}

}
function removeErrorClass(elementId) {
	console.log("Control comes");
	$("#form-user-home-valid-msg").hide();

}
