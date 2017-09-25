$(document).ready(function() {
	$(".nav_menu").removeClass("active");
	$("#college_search_menu").addClass("active");

	getAllSchools();

	$("#check_all_schools").click(function(event) {
		event.stopPropagation();
		var checked = $(this).is(':checked');
		if (checked) {
			$('input:checkbox').attr('checked', 'checked');
		} else {
			$('input:checkbox').removeAttr('checked');
		}
	});

});

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
			if (e.status == 408) {
				window.location.href = "/sessionTimeout";
			} else {
				$.alert({
					content : "Error While Loading Schools.Please Try Again"
				});
			}
		}
	});
}

function getSchoolsByCriteria() {

	var data = {};
	data["schoolName"] = $("#search_college").val();
	data["stateName"] = $("#search_state").val();
	data["cityName"] = $("#search_city").val();
	data["minGREScore"] = $("#search_min_gre_score").val();
	data["minTOEFLScore"] = $("#search_min_toefl_score").val();
	data["minGMATScore"] = $("#search_min_gmat_score").val();
	data["minGPAScore"] = $("#search_min_gpa_score").val();
	data["transcriptsRequired"] = $('input[name=requires_trancripts]:checked')
			.val();
	data["solvCertRequired"] = $('input[name=requires_solv_certficate]:checked')
			.val();
	data["recLettersRequired"] = $('input[name=requires_rec_letters]:checked')
			.val();

	console.log($('input[name=requires_trancripts]:checked').val());

	$.ajax({
		type : "POST",
		url : "school/getSchoolsByCriteria",
		dataType : 'json',
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(results) {
			console.log(results);
			getSchoolAllSchool(results);
		},
		error : function(e) {
			if (e.status == 408) {
				window.location.href = "/sessionTimeout";
			} else {
				$.alert({
					content : "Error While Loading Schools.Please Try Again"
				});
			}
		}
	});
}
function processAllSchools(results) {
	var statesArray = [];
	var stateNames = [];
	var allRows = "";
	var schoolsFound = 0;
	$("#no_of_schools_found").text(results.length);
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {

			var eachRow = results[i];
			var schoolGuid = eachRow['schoolGuid'];
			var schoolName = eachRow['schoolName'];
			var city = eachRow['city'];
			var state = eachRow['state'];
			var website = eachRow['website'];

			var locationName = "";

			if (city != undefined && city != null && city != '') {
				locationName = city;

				if (state != undefined && state != null && state != '') {
					locationName += "," + state;
				}
			} else {
				if (state != undefined && state != null && state != '') {
					locationName = state;
				}
			}

			var schoolLogoURL = eachRow['schoolLogoURL'];
			if (schoolLogoURL == null || schoolLogoURL == undefined) {
				schoolLogoURL = "images/Dummy_School_Logo.png";
			}
			allRows += "<div class='school_found col-lg-4 col-md-6 col-sm-12 col-xs-6'><div class='school_img' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"><img src='"
					+ schoolLogoURL
					+ "'/></div><div class='school_name_location'><div class='school_name' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"	>"
					+ schoolName
					+ "</div><div class='location_name'>"
					+ locationName
					+ "</div></div><div class='footbar row'><div class='col-xs-2' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"><i class='fa fa-info-circle more_info' aria-hidden='true' title='More Info'></i></div><div class='col-xs-8 add_college_col' id='add_added_college_"
					+ schoolGuid
					+ "'><span onclick=\"addCollegeToStudent('"
					+ schoolGuid
					+ "')\" class='add_college_btn' id='addCollege'><i class='fa fa-plus add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;Add College</span></div><div class='col-xs-2'><i class='fa fa-external-link goto_site' aria-hidden='true' id='Go To College Website'  onclick=\"window.open('https://"
					+ website + "')\"> </i></div></div></div>";

			statesArray.push(eachRow['state']);
		}

		$(".school-container-content").html(allRows);

		$.each(statesArray, function(i, el) {
			if ($.inArray(el, stateNames) === -1)
				stateNames.push(el);
		});

		for (var j = 0; j < stateNames.length; j++) {
			var eachStateOptionHtml = "<option value='" + stateNames[j] + "'>"
					+ stateNames[j] + "</option>";

			$("#search_state").append(eachStateOptionHtml);
		}
		var loggedInAccountGuid = $("#login_account_guid").val();
		if (loggedInAccountGuid != null && loggedInAccountGuid != undefined
				&& loggedInAccountGuid != "" && loggedInAccountGuid != "null") {
			getAllStudentSchools();
		}
	}

	$(".school-container-content").html(allRows);
}

function getSchoolAllSchool(results) {
	console.log(results);
	var statesArray = [];
	var stateNames = [];
	var allRows = "";
	var schoolsFound = 0;
	$("#no_of_schools_found").text(results.length);
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {

			var eachRow = results[i];

			console.log(eachRow);
			var schoolGuid = eachRow['schoolGuid'];
			var schoolName = eachRow['schoolName'];
			var city = eachRow['city'];
			var state = eachRow['state'];

			var website = eachRow['website'];
			var locationName = "";

			if (city != undefined && city != null && city != '') {
				locationName = city;

				if (state != undefined && state != null && state != '') {
					locationName += "," + state;
				}
			} else {
				if (state != undefined && state != null && state != '') {
					locationName = state;
				}
			}

			var schoolLogoURL = eachRow['schoolLogoURL'];
			if (schoolLogoURL == null || schoolLogoURL == undefined) {
				schoolLogoURL = "images/Dummy_School_Logo.png";
			}
			allRows += "<div class='school_found col-lg-4 col-md-6 col-sm-12 col-xs-6'><div class='school_img' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"><img src='"
					+ schoolLogoURL
					+ "'/></div><div class='school_name_location'><div class='school_name' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"	>"
					+ schoolName
					+ "</div><div class='location_name'>"
					+ locationName
					+ "</div></div><div class='footbar row'><div class='col-xs-2' onclick=\"gotoCollegeInfo('"
					+ schoolGuid
					+ "')\"><i class='fa fa-info-circle more_info' aria-hidden='true' title='More Info'></i></div><div class='col-xs-8 add_college_col' id='add_added_college_"
					+ schoolGuid
					+ "'><span onclick=\"addCollegeToStudent('"
					+ schoolGuid
					+ "')\" class='add_college_btn' id='addCollege'><i class='fa fa-plus add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;Add College</span></div><div class='col-xs-2'><i class='fa fa-external-link goto_site' aria-hidden='true' id='Go To College Website'  onclick=\"window.open('https://"
					+ website + "')\"> </i></div></div></div>";

			statesArray.push(eachRow['state']);
		}

		$(".school-container-content").html(allRows);
		var loggedInAccountGuid = $("#login_account_guid").val();
		if (loggedInAccountGuid != null && loggedInAccountGuid != undefined
				&& loggedInAccountGuid != "" && loggedInAccountGuid != "null") {
			getAllStudentSchools();
		}
	}

	$(".school-container-content").html(allRows);
}

function addCollegeToStudent(schoolGuid) {

	var loggedInAccountGuid = $("#login_account_guid").val();
	if (loggedInAccountGuid != null && loggedInAccountGuid != undefined
			&& loggedInAccountGuid != "" && loggedInAccountGuid != "null") {

		var school = {};
		school["schoolGuid"] = schoolGuid;

		$
				.ajax({
					type : "POST",
					contentType : "application/json",
					url : "school/addCollege",
					dataType : 'json',
					data : JSON.stringify(school),
					success : function(results) {
						$.alert({
							content : "College Added To Your List Succesfully"
						});
						var addedCollegeHtml = "<span class='add_college_btn' id='addCollege'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
						$("#add_added_college_" + schoolGuid).html(
								addedCollegeHtml);
						$("#add_added_college_" + schoolGuid).prop("disabled",
								true);
					},
					error : function(e) {
						$.alert({
							content : "College Added To Your List Succesfully"
						});
						var addedCollegeHtml = "<span class='add_college_btn' id='addCollege'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
						$("#add_added_college_" + schoolGuid).html(
								addedCollegeHtml);
						$("#add_added_college_" + schoolGuid).prop("disabled",
								true);
					}
				});
	} else {
		location.href = "/login";
	}

}

function addSelectedSchools() {

	var selectedSchools = [];
	$('#schools_list_tbody input:checkbox').each(function() {
		if (this.checked) {
			var checkedBoxId = $(this).attr("id");
			var schoolId = checkedBoxId.split("_")[1];
			var school = {};
			school["schoolGuid"] = schoolId;
			selectedSchools.push(school);
		}
	});

	var data = {};
	data["selectedSchools"] = selectedSchools;

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/addColleges",
		dataType : 'json',
		data : JSON.stringify(selectedSchools),
		success : function(results) {
			processAddedSchools(results);
		},
		error : function(e) {
			if (e.status == 408) {
				window.location.href = "/sessionTimeout";
			} else {
				$alert({
					content : "Error While Loading Schools.Please Try Again"
				});
			}
		}
	});

	if (selectedSchools.length == 0) {
		$.alert({
			content:"Select one school atleast to add"
		});
		return false;
	}
}

function processAddedSchools(results) {
	console.log(results);
	var addedSchoolHtml = "";
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var addedSchool = results[i];

			addedSchoolHtml += "<div class='each_added_school'><i class='fa fa-check green' aria-hidden='true'></i><span>"
					+ addedSchool + "</span></div>";
		}
		$(".modal-body").html(addedSchoolHtml);
		$('#addedSchoolsModal').modal('show');
	}
}

function getAllStudentSchools() {
	$.ajax({
		type : "GET",
		url : "school/getStudentSchools",
		success : function(results) {
			processAllStudentSchools(results);
		},
		error : function(e) {
			if (e.status == 408) {
				window.location.href = "/sessionTimeout";
			} else {
				$.alert({
					content:"Error While Loading Student Schools.Please Try Again"
				});
			}

		}
	});
}

function processAllStudentSchools(results) {
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			var schoolGuid = eachRow['schoolGuid'];
			var addedCollegeHtml = "<span class='add_college_btn' id='addCollege'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
			$("#add_added_college_" + schoolGuid).html(addedCollegeHtml);

			$("#add_added_college_" + schoolGuid).prop("disabled", true);

		}
	}
}

function gotoCollegeInfo(schoolGuid) {
	location.href = "schoolInfo?school=" + schoolGuid;
}
function removeDuplicates(originalArray, prop) {
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
function isNumber(event) {
	event = (event) ? event : window.event;
	var charCode = (event.which) ? event.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}
function isValidSearchScore(inputId) {
	if (inputId == "search_min_gre_score") {
		var greScore = $("#search_min_gre_score").val();
		console.log(greScore);
		if (greScore >= 260
				&& greScore <= 340
				&& $.inArray(parseInt(greScore), getIntervalArray(260, 340, 1)) != -1
				|| greScore == undefined || greScore == "") {
			getSchoolsByCriteria();
		} else {
			$.alert({
				content:"Invalid GRE Score"
			});
		}

	} else if (inputId == "search_min_toefl_score") {
		var toeflScore = $("#" + inputId).val();
		console.log(toeflScore);
		if (toeflScore >= 0
				&& toeflScore <= 120
				&& $.inArray(parseInt(toeflScore), getIntervalArray(0, 120, 1)) != -1
				|| toeflScore == undefined || toeflScore == "") {
			getSchoolsByCriteria();
		} else {
			$.alert({
				content:"Invalid TOEFL Score"
			});
		}

	} else if (inputId == "search_min_gmat_score") {
		var gmatScore = $("#" + inputId).val();
		if (gmatScore >= 200
				&& gmatScore <= 800
				&& $
						.inArray(parseInt(gmatScore), getIntervalArray(200,
								800, 1)) != -1 || gmatScore == undefined
				|| gmatScore == "") {
			getSchoolsByCriteria();

		} else {
			$.alert({
				content:"Invalid GMAT Score"
			});
		}

	} else if (inputId == "search_min_gpa_score") {
		var gpaScore = $("#" + inputId).val();
		if (gpaScore >= 0 && gpaScore <= 4.0 || gpaScore == undefined
				|| gpaScore == "") {
			getSchoolsByCriteria();
		} else {
			$.alert({
				content:"Invalid GPA Score"
			});
		}
	}

}
function getIntervalArray(min, max, interval) {
	var results = [];
	for (var i = min; min <= max; i++) {
		results.push(min);
		min = min + interval;
	}
	return results;
}
