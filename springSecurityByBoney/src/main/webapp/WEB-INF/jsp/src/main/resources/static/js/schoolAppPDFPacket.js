/**
 * 
 */
$(document).ready(function() {
	generatePDFPacket();
});

function generatePDFPacket() {

	var content = $("#school_app_doc_content").html();
	var data = {};
	var schoolAppDocContent = "<html lang=\"en\">\n<head>\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\" />\n<meta charset=\"utf-8\" />\n</head><body>"
			+ content.trim() + "</body></html>";
	data["schoolAppDocContent"] = schoolAppDocContent;
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "school/generateSchoolAppPDF",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			
		},
		error : function(e) {
			alert("Error While Saving Student Profile Data");

		}
	});

}
