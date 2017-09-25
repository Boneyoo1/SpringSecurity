$(document)
		.ready(
				function() {
					$("#documentForm").validate();

					$('#addDocumentModel').modal({
						backdrop : 'static',
						keyboard : false,
						show : false
					});

					$("#addDocumentModel").on("hidden.bs.modal", function() {
						clearAddDocumentModal();
					});
					// This Function is used for If student is Transfer Then
					// trigger
					// click event
					// chnage dropdown value of Add Document
					/*
					 * $("#add_document_model").on("click", function() {
					 * addDocumentModel(); });
					 */
					$('#document_file')
							.change(
									function() {
										var filename = $(this).val();
										var fileExtension = [ 'pdf', 'jpg',
												'jpeg', 'png' ];
										if ($.inArray(filename.split('.').pop()
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
					getStudentDocuments();
				});
var completedDocumentsSectionsCountIncremented = false;
function clearAddDocumentModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$("#documentForm")[0].reset();
	$("#document_guid").val("");
}

// This Function Change Dropdown Value of Add Document if student Is Transfer
/*
 * function addDocumentModel() { $ .ajax({ type : "GET", contentType :
 * "application/json", url : "student/getStudentApplication", dataType : 'json',
 * success : function(results) { if (results) { var eachSelectHTML = "<option
 * value=''>Select Document Type</option><option value='I-20'>I-20</option><option
 * value='F1 VISA'>F1 VISA</option>";
 * $("#document_type").empty().append(eachSelectHTML); } }, }); }
 */
function getStudentDocuments() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentDocuments",
		dataType : 'json',
		success : function(results) {
			processStudentDocuments(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Documents.Please Try Again"
			});
		}
	});
}
function processStudentDocuments(results) {
	$("#docs_list_tbody").empty();
	if (results.length > 0) {
		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#document_completed").html(statusHtml);
		if (completedDocumentsSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedDocumentsSectionsCountIncremented = true;
		}

		var docsRows = "";
		for (var i = 0; i < results.length; i++) {
			var eachDoc = results[i];
			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["documentType"]
					+ "</td><td>"
					+ eachDoc["documentName"]
					+ "</td><td><i href='' class='fa fa-file-pdf-o edit-delete-icon' onclick=\"downloadDocument('"
					+ eachDoc["viewDocumentUrl"]
					+ "')\"></i><a href='"
					+ eachDoc["documentPath"]
					+ "'  download='"
					+ eachDoc["documentName"]
					+ "' class='fa fa-download edit-delete-icon' download></a><a class='fa fa-edit edit-delete-icon' data-toggle='modal' data-target='#addDocumentModel' onclick=\"editStudentDocument('"
					+ eachDoc["studentDocumentGuid"]
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentDocument('"
					+ eachDoc["studentDocumentGuid"] + "')\"></a></td></tr>";
		}
		$("#docs_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#document_completed").html(statusHtml);
		if (completedDocumentsSectionsCountIncremented == true) {

			completedSectionsCount--;

			$("#profile_completed_sections_count").text(completedSectionsCount);

			completedDocumentsSectionsCountIncremented = false;

		}
		$("#docs_list_tbody")
				.append(
						"<tr><td colspan='4' align='center'>No Documents Found</td></tr>");
	}
}
function uploadDocument() {
	if ($("#documentForm").valid()) {
		$("#doc-upload-btn").prop("disabled", true);
		$.ajax({
			url : 'student/uploadStudentDocument',
			type : "POST",
			data : new FormData(document.getElementById("documentForm")),
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false
		}).done(function(data) {
			$('#addDocumentModel').modal('toggle');
			clearAddDocumentModal();
			$.alert({
				content : "Document Uploaded Successfully"
			});
			$("#doc-upload-btn").prop("disabled", false);
			$('#documentForm')[0].reset();
			$('#addDocumentModel .close').click();
			getStudentDocuments();
		}).fail(function(jqXHR, textStatus) {
			$.alert({
				content : "Error While Uploading Document.Please Try Again"
			});
		});
	}
}
function editStudentDocument(studentDocumentGuid) {
	$
			.ajax({
				type : "GET",
				contentType : "application/json",
				url : "student/getStudentDocument/" + studentDocumentGuid,
				dataType : 'json',
				success : function(results) {
					processStudentDocument(results);
				},
				error : function(e) {
					$
							.alert({
								content : "Error While Loading Student Document.Please Try Again"
							});
				}
			});
}

function processStudentDocument(data) {

	$("#document_guid").val(data["studentDocumentGuid"]);
	$("#document_type").val(data["documentType"]);
	$("#document_file").val(data["documentName"]);
}

function deleteStudentDocument(studentDocumentGuid) {
	$
			.confirm({
				content : 'Are You Sure To Delete This Document?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									contentType : "application/json",
									url : "student/deleteStudentDocument",
									data : studentDocumentGuid,
									dataType : 'json',
									success : function(results) {
										$.alert("Document Deleted Succesfully");
										getStudentDocuments();
									},
									error : function(e) {
										$
												.alert("Error While Deleting Document.Please Try Again");
									}

								});

					},
				}
			});

}
function downloadDocument(documentPath) {
	window
			.open(
					documentPath,
					'winname',
					'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no');
}
/*
 * function downloadStudentDocument(studentDocumentGuid) { $.ajax({ type :
 * "POST", contentType : "application/json; charset=utf-8", url :
 * "student/downloadStudentDocument/", data : studentDocumentGuid, success :
 * function(data) { console.log(data); var base64 =
 * window.btoa(encodeURIComponent(data)); var pdfAsDataUri =
 * "data:application/pdf;base64," + data; // shortened var pdfAsArray =
 * convertDataURIToBinaryFF(pdfAsDataUri); PDFJS.getDocument(pdfAsArray);
 * 
 * 
 * var file = new Blob([data], { type: 'application/pdf' }); var fileURL =
 * URL.createObjectURL(file); window.open(fileURL, "sample.pdf"); }, error :
 * function(e) { alert("Error While Downloading Student Document.Please Try
 * Again"); } }); }
 */