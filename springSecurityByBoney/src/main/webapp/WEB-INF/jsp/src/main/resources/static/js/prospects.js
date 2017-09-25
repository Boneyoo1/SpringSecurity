$(document).ready(function() {
	getSchoolProspects();
});

function getSchoolProspects() {
	$
			.ajax({
				type : "GET",
				url : "adminDashBoard/getSchoolProspects",
				success : function(results) {
					processSchoolProspects(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Prospects.Please Try Again"
							});
				}
			});
}
function processSchoolProspects(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<tr><td>" + (i + 1) + "</td><td>"
					+ eachRow['firstName'] + "</td><td>" + eachRow['lastName']
					+ "</td><td>" + eachRow['emailAddress'] + "</td><td>"
					+ eachRow['phoneNumber'] + "</td></tr>";
		}
		$("#school_prospects_list_tbl_body").html(allRows);
		$('#school_prospects_list_tbl').DataTable({});
		$("#school_prospects_list_tbl_length").empty();
		$("#school_prospects_list_tbl_length").remove();
		$("#school_prospects_tfoot_row_no_data").hide();
	} else {
		$("#school_prospects_tfoot_row_no_data").show();
	}
}