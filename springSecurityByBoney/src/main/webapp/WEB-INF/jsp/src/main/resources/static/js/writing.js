$(document).ready(function() {
	$("#writingForm").validate();

	$('#writingModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$('#writingShowModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$("#writingModal").on("hidden.bs.modal", function() {
		clearAddWritingModal();
	});
	$("#writingShowModal").on("hidden.bs.modal", function() {
		clearAddWritingModal();
	});
	/*
	 * 
	 * this Functon is count char using editor
	 * 
	 */
	var text_max = 5000;
	$('#count_message').html('0 / ' + text_max);
	$('#text').keyup(function() {
		var text_length = $('#text').val().length;
		var text_remaining = text_max - text_length;

		$('#count_message').html(text_length + ' / ' + text_max);
	});
	getStudentWritings();
});
var completedWritingSectionsCountIncremented = false;
function showWritingModel() {
}

function clearAddWritingModal() {
	$("#writingForm")[0].reset();
	$("#studentWritingGuid").val("");
}
function getStudentWritings() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentWritings",
		dataType : 'json',
		success : function(results) {
			processStudentWritings(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Writings. Please Try Again"
			});
		}
	});
}

var wrintingsList = [];
function processStudentWritings(results) {
	wrintingsList = results;
	$("#writings_list_tbody").empty();
	$("#writingModalbutton").prop("disabled", false);
	if (wrintingsList.length > 0) {
		$("#writingModalbutton").prop("disabled", true);
		var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
		$("#writing_completed").html(statusHtml);
		if (completedWritingSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedWritingSectionsCountIncremented = true;
		}
		var docsRows = "";
		for (var i = 0; i < wrintingsList.length; i++) {
			var eachDoc = wrintingsList[i];

			$("#student_writing_guid").val(eachDoc["studentWritingGuid"]);
			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["writingType"]
					+ "</td><td><a class='fa fa-eye edit-delete-icon' data-toggle='modal' data-target='#writingShowModal' onclick=\"viewWriting('"
					+ i
					+ "')\"></a></td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#writingModal' onclick=\"editStudentWriting('"
					+ i
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentWriting('"
					+ eachDoc["studentWritingGuid"] + "')\"></a></td></tr>";
		}
		$("#writings_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#writing_completed").html(statusHtml);
		if (completedWritingSectionsCountIncremented == true) {
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedWritingSectionsCountIncremented = false;
		}
		$("#writings_list_tbody")
				.append(
						"<tr><td colspan='4' align='center'>No Writings Found</td></tr>");
	}
}

function editStudentWriting(selectedRecordIndex) {

	var eachDoc = wrintingsList[selectedRecordIndex];
	$("#text").text(eachDoc["writingText"]);
	$("#studentWritingGuid").val(eachDoc["studentWritingGuid"]);
	$('#writing_type').val(eachDoc["writingType"]);
	charCount();

}

function viewWriting(selectedRecordIndex) {
	var eachDoc = wrintingsList[selectedRecordIndex];
	$('#writing_type').val(eachDoc["writingType"]);
	$("#writing_text_text").text(eachDoc["writingText"]);

}

function addWriting() {
	var str = $("#count_message").text();
	str = str.split("/");
	if (str[0] > 1) {
		if ($("#writingForm").valid()) {
			$("#add_writing_button").prop("disabled", true);
			var data = {};
			data["writingType"] = "PERSONAL ESSAY";
			data["writingText"] = $("#text").val();
			data["studentWritingGuid"] = $("#student_writing_guid").val();
			$.ajax({
				url : 'student/saveStudentWriting',
				type : "POST",
				dataType : 'json',
				contentType : "application/json",
				data : JSON.stringify(data),
			}).done(function(data) {
				$("#add_writing_button").prop("disabled", false);
				$.alert({
					content : "Writing Added Successfully"
				});
				$('#writingModal .close').click();
				$('#writingForm')[0].reset();
				getStudentWritings();

			}).fail(function(jqXHR, textStatus) {
				$.alert({
					content : 'Error While Saving Writing. Please Try Again'
				});
			});
		}
	} else {
		$.alert({
			content : 'Please fill out this field '
		});
	}
}
function deleteStudentWriting(studentWritingGuid) {
	$
			.confirm({
				content : 'Are You Sure To Delete This Writing ?',
				buttons : {
					confirm : function() {

						$
								.ajax({
									type : "POST",
									url : "student/deleteStudentWriting",
									data : studentWritingGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Writing Deleted Successfully"
												});
										$('#count_message').html(
												'0 / ' + '5000');
										$('#text').text("");
										getStudentWritings();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Writing. Please Try Again"
												});
									}
								});
					},
				}
			});

}
function closeWritingModal() {
	clearAddWritingModal();
	$('#writingModal').modal('toggle');
	$('#writingForm')[0].reset();

}
function closeWritingShowModal() {
	clearAddWritingModal();
	$('#writingForm')[0].reset();
	$('#writingShowModal').modal('toggle');

}
function charCount() {
	var text_max = 5000;
	var text_length = $('#text').val().length;
	var text_remaining = text_max - text_length;
	$('#count_message').html(text_length + ' / ' + text_max);
}
