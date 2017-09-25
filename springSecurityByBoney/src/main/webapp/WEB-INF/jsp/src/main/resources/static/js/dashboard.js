/**
 * 
 */
$(document).ready(function() {
	$(".nav_menu").removeClass("active");
	$("#dashboard_menu").addClass("active");
	$("#profile_completed_sections_count").text("0");
	$("#profile_total_sections_count").text("7");

	$("#schools_completed_sections_count").text("0");
	$("#schools_total_sections_count").text("0");

	getDashBoardProfileDetails();
	getAllStudentSchools();

});

function getDashBoardProfileDetails() {
	$
			.ajax({
				type : "GET",
				url : "student/getStudentDashBoardProfileDetails",
				success : function(results) {
					processDashBoardProfileDetails(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading Student Schools.Please Try Again"
							});
				}
			});
}
function processDashBoardProfileDetails(results) {

	var noOfCompleted = 0;

	var profileCompleted = results.profileCompleted;
	var educationCompleted = results.educationCompleted;
	var testingCompleted = results.testingCompleted;
	var writingCompleted = results.writingCompleted;
	var documentCompleted = results.documentCompleted;
	var employementCompleted = results.employementCompleted;
	var recommendersCompleted = results.recommendersCompleted;

	var statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";

	if (profileCompleted) {
		$("#profile_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (educationCompleted) {
		$("#education_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (testingCompleted) {
		$("#testing_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (writingCompleted) {
		$("#writing_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (documentCompleted) {
		$("#document_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (employementCompleted) {
		$("#employement_completed").html(statusHtml);
		noOfCompleted++;
	}

	if (recommendersCompleted) {
		$("#recommendors_completed").html(statusHtml);
		noOfCompleted++;
	}

	$("#profile_completed_sections_count").text(noOfCompleted);

}

function gotoProfile(tabName) {
	location.href = "profile?tab=" + tabName;
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
								content : "Error While Loading Student Schools.Please Try Again"
							});
				}
			});
}

function processAllStudentSchools(results) {
	var collegesApplied = 0;
	if (results.length > 0) {
		$("#schools_total_sections_count").text(results.length);
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];

			var statusHtml = "<i class='fa fa-check' aria-hidden='true'></i>";
			var applicationStatus = eachRow['applicationStatus']
			if (applicationStatus == "Pending") {
				statusHtml = "<i class='fa fa-exclamation-triangle red' aria-hidden='true'></i>";
			} else if (applicationStatus == "Completed") {
				statusHtml = "<i class='fa fa-check green' aria-hidden='true'></i>";
				collegesApplied++;
			}
			allRows += "<div class='row each_section' onclick=\"gotoMyColleges('"
					+ eachRow['schoolGuid']
					+ "')\"><div class='col-xs-10'>"
					+ eachRow['schoolName']
					+ "</div><div class='col-xs-2 school_app_status'>"
					+ statusHtml + "</div></div></div>";

		}
		$("#school_list").html(allRows);
	}

	$("#schools_completed_sections_count").text(collegesApplied);
}
function gotoMyColleges(schoolGuid) {
	location.href = "myColleges?school=" + schoolGuid;
}
function deleteStudentSchool(schoolGuid) {
	$
			.confirm({
				content : 'Are You Sure To Delete School From List?',
				buttons : {
					confirm : function() {

						$("#delete_a_" + schoolGuid).prop("disabled", true);
						$
								.ajax({
									type : "POST",
									url : "school/deleteStudentSchool",
									data : schoolGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "School Deleted From List"
												});
										$('#school_dashboard_' + schoolGuid)
												.empty();
										$('#school_dashboard_' + schoolGuid)
												.remove();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting School From List.Please Try Again"
												});
									}
								});

					},
				}
			});

}
