$(document).ready(function() {
	$('#educationModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$("#degree-start-date").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#degree-end-date").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#degree-rcieved-on").datepicker({
		format : 'mm/dd/yyyy',
		useCurrent : true,
		autoclose : true,
		defaultDate : new Date(),
		keyboardNavigation : true,
		orientation : 'bottom left'
	});
	$("#educationForm").validate();
	getStudentEducations();

	getAllDegrees();
});
var completedEducationSectionsCountIncremented = false;
$("tbody tr").click(function() {
	$.alert('In');
	$(this).toggleClass("selected-school-row");
});

function getAllDegrees() {

	// alert("getAllDegrees");
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "common/getAllDegrees",
		dataType : 'json',
		success : function(results) {
			processAllDegrees(results);
		},
		error : function(e) {
			$.alert('Error While Loading Degrees. Please Try Again');
		}
	});
}

function processAllDegrees(results) {

	// alert("processAllDegrees quickly");
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<option value='" + eachRow["degreeName"] + "'>"
					+ eachRow["degreeName"] + "</option>";
		}
		// alert(allRows);
		$("#degree-obtained").append(allRows);

	}
}
function getStudentEducations() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentEducations",
		dataType : 'json',
		success : function(results) {
			processStudentEducations(results);
		},
		error : function(e) {

			$.alert('Error While Loading Educations.Please Try Again');
		}
	});
}

var educationsList = [];
function processStudentEducations(results) {
	educationsList = results;
	$("#educations_list_tbody").empty();
	if (educationsList.length > 0) {

		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#education_completed").html(statusHtml);

		if (completedEducationSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedEducationSectionsCountIncremented = true;
		}
		var docsRows = "";
		for (var i = 0; i < educationsList.length; i++) {
			var eachDoc = educationsList[i];
			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["degreeObtained"]
					+ "</td><td>"
					+ eachDoc["major"]
					+ "</td>"
					+ "<td>"
					+ eachDoc["schoolName"]
					+ "</td><td>"
					+ eachDoc["cgpaObtained"]
					+ "</td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#educationModal' onclick=\"editStudentEducation('"
					+ i
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentEducation('"
					+ eachDoc["studentEducationGuid"] + "')\"></a></td></tr>";
		}
		$("#educations_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#education_completed").html(statusHtml);
		if (completedEducationSectionsCountIncremented == true) {
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedEducationSectionsCountIncremented = false;
		}
		$("#educations_list_tbody")
				.append(
						"<tr><td colspan='6' align='center'>No Educations Found</td></tr>");
	}
}
function editStudentEducation(studentEducationGuid) {
	var data = educationsList[studentEducationGuid];
	$("#degree-start-date").val(data["startDate"]);
	$("#degree-end-date").val(data["endDate"]);
	$("#degree-rcieved-on").val(data["degreeReceivedOn"]);
	$("#degree-obtained-cgpa").val(data["cgpaObtained"]);
	$("#schoolGuid").val(data["degreeSchoolGuid"]);
	$("#degree-obtained").val(data["degreeObtained"]);
	$("#major").val(data["major"]);
	$("#schoolCity").val(data["schoolCity"]);
	$("#degree-scool").val(data["schoolName"]);
	$("#schoolCountry").val(data["schoolCountry"]);
	$("#studentEducationGuid").val(data["studentEducationGuid"]);

}

function deleteStudentEducation(studentEducationGuid) {
	$
			.confirm({
				content : 'Are You Sure To Delete This Education ?',
				buttons : {
					confirm : function() {

						$
								.ajax({
									type : "POST",
									url : "student/deleteStudentEducation",
									data : studentEducationGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert('Education Deleted Successfully');

										getStudentEducations();
									},
									error : function(e) {
										$
												.alert('Error While Deleting Education.Please Try Again');

									}
								});

					},
				}
			});

}

function addEducation() {

	if ($("#educationForm").valid()) {
		$("#add_education_button").prop("disabled", true);
		var data = {};
		data["startDate"] = $("#degree-start-date").val();
		data["endDate"] = $("#degree-end-date").val();
		data["degreeReceivedOn"] = $("#degree-rcieved-on").val();
		data["cgpaObtained"] = $("#degree-obtained-cgpa").val();
		data["degreeSchoolGuid"] = $("#schoolGuid").val();
		data["degreeObtained"] = $("#degree-obtained").val();
		data["major"] = $("#major").val();
		data["schoolCity"] = $("#schoolCity").val();
		data["schoolName"] = $("#degree-scool").val();
		data["schoolCountry"] = $("#schoolCountry").val();
		data["studentEducationGuid"] = $("#studentEducationGuid").val();

		$.ajax({
			url : 'student/saveStudentEducation',
			type : "POST",
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(data),
		}).done(function(data) {
			$("#add_education_button").prop("disabled", false);
			$.alert("Education Added Successfully");
			$('#educationModal .close').click();
			closeEducationModal();
			$("#schoolCity").val("");
			$("#schoolCountry").val("");
			$("#schoolGuid").val("");
			getStudentEducations();
		}).fail(function(jqXHR, textStatus) {
			$.alert('Error While Saving Writing.Please Try Again');
		});
	}
}
function closeEducationModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$('#educationForm')[0].reset();
}

function showSchoolsModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$('#educationModal').hide();
	getAllSchools();
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
			$.alert('Error While Loading Schools.Please Try Again');
		}
	});
}

function processAllSchools(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<tr id='tr_"
					+ eachRow['schoolGuid']
					+ "'><td><input type='radio' name='selected-school-radio' value="
					+ eachRow['schoolGuid']
					+ "></td><td id='selected_school_name_"
					+ eachRow['schoolGuid'] + "'>" + eachRow['schoolName']
					+ "</td><td id='selected_school_city_"
					+ eachRow['schoolGuid'] + "'>" + eachRow['city']
					+ "</td><td>" + eachRow['state']
					+ "</td><td id='selected_school_country_"
					+ eachRow['schoolGuid'] + "'>" + eachRow['schoolCountry']
					+ "</td></tr>";
		}
		$("#schools_list_tbody").append(allRows);
		$('#schools_list_tbl').DataTable({});
		$("#schools_list_tbl_length").empty();
		$("#schools_list_tbl_length").remove();
	}
}

var selectedSchoolName;
var selectedSchoolGuid;
var selectedSchoolCity;
var selectedSchoolCountry;

function selectShool() {
	selectedSchoolGuid = $('input[name=selected-school-radio]:checked').val();
	selectedSchoolName = $('#selected_school_name_' + selectedSchoolGuid)
			.html();
	selectedSchoolCity = $('#selected_school_city_' + selectedSchoolGuid)
			.html();
	selectedSchoolCountry = $('#selected_school_country_' + selectedSchoolGuid)
			.html();
	$('#educationModal').show();

	$("#schoolCity").val(selectedSchoolCity);
	$("#degree-scool").val(selectedSchoolName);
	$("#schoolCountry").val(selectedSchoolCountry);
	$("#schoolGuid").val(selectedSchoolGuid);

	$('#schools_list_tbl').DataTable().destroy();
}

function closeSchoolModal() {
	$('#educationModal').show();
	$('#schools_list_tbl').DataTable().destroy();
}

function isValidCGPA() {
	var degreeObtainedCGPA = $("#degree-obtained-cgpa").val();
	if (degreeObtainedCGPA >= 0 && degreeObtainedCGPA <= 4.0) {

	} else {

		$.alert({
			title : '',
			content : 'Invalid CGPA',
			closeIcon : true,
			closeIconClass : 'fa fa-close',
			btnClass : 'btn',
			columnClass : 'col-md-5 col-md-offset-4',

		});
	}
}
