$(document).ready(function() {
	getSchoolApplicants();
});

function getSchoolApplicants() {
	$
			.ajax({
				type : "GET",
				url : "adminDashBoard/getSchoolApplicants",
				success : function(results) {
					processSchoolApplicants(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading School Applicants.Please Try Again"
							});
				}
			});
}
function processSchoolApplicants(results) {
	if (results.length > 0) {
		var allRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			allRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachRow['firstName']
					+ "</td><td>"
					+ eachRow['lastName']
					+ "</td><td>"
					+ eachRow['dateOfBirth']
					+ "</td><td>"
					+ eachRow['term']
					+ "</td><td>"
					+ eachRow['year']
					+ "</td><td>"
					+ eachRow['degreeApplied']
					+ "</td><td>"
					+ eachRow['applicationStatus']
					+ "</td><td>"
					+ eachRow['applicationSubmittedDate']
					+ "</td><td class='pdfurl'><a class='fa fa-file-pdf-o edit-delete-icon' onclick=\"viewPDF('"
					+ eachRow['viewPdfURL']
					+ "')\"></a></td><td class='pdfurl'><a href='"
					+ eachRow['downloadpdfURL']
					+ "' class='fa fa-download edit-delete-icon' onclick=\"downloadPDF('"
					+ eachRow['downloadpdfURL']
					+ "')\" download></a></td></tr>";
		}
		$("#school_applicants_list_tbl_body").html(allRows);
		$('#school_applicants_list_tbl').DataTable({});
		$("#school_applicants_list_tbl_length").empty();
		$("#school_applicants_list_tbl_length").remove();
		$("#school_applicants_tfoot_row_no_data").hide();
		$(".download_applicants_data_content").show();
	} else {
		$("#school_applicants_tfoot_row_no_data").show();
	}
}
function viewPDF(pdfURL) {
	if (pdfURL == null || pdfURL == "" || pdfURL == " " || pdfURL == undefined) {
		$.alert({
			content : "No URL Found"
		});
	} else {

		window.open(pdfURL, '_blank');
		return false;
	}
}
function downloadPDF(pdfURL) {
	// var pdfdownlod = "";
	if (pdfURL == null || pdfURL == "" || pdfURL == " " || pdfURL == undefined) {
		$.alert({
			content : "No URL Found"
		});
	} else {

		$.alert({
			content : "File Downloaded Successfully"
		});

		// window
		// .open(
		// pdfURL,
		// 'winname',
		// 'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no');

		// $.ajax({
		// type : "POST",
		// data : encodeURIComponent(pdfURL),
		// url : "adminDashBoard/downloadStudentApplicationData",
		// success : function(results) {
		// alert(results);
		// },
		// error : function(e) {
		// alert("Error While Downloading URL.Please Try Again");
		// }
		// });

	}
}

function downloadApplicationData() {
	$.ajax({
		type : "POST",
		url : "adminDashBoard/downloadApplicationData",
		success : function(results) {

			window.open(results);
			$.alert({
				content:"Application Data Downloaded Successfully.."
			});
			// alert(results);
		},
		error : function(e) {
			$.alert({
				content:"Error While Downloading Application Data.Please Try Again"
			});
		}
	});
}