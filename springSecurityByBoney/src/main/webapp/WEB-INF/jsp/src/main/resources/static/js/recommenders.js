$(document).ready(function() {
	$('#recommenderModal').modal({
		backdrop : 'static',
		keyboard : false,
		show : false
	});
	$("#recommenderForm").validate();

	$("#recommenderModal").on("hidden.bs.modal", function() {
		closeRecommenderModal();
	});
	getStudentRecommenders();

});
var completedRecommendersSectionsCountIncremented = false;
function closeRecommenderModal() {
	$("label.error").hide();
	$(".error").removeClass("error");
	$('#recommenderForm')[0].reset();
	$("#studentRecommenderGuid").val("");
}

function getStudentRecommenders() {
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getStudentRecommenders",
		dataType : 'json',
		success : function(results) {
			processStudentRecommenders(results);
		},
		error : function(e) {
			$.alert({
				content : "Error While Loading Recommenders.Please Try Again"
			});
		}
	});
}
var recommendersList = [];
function processStudentRecommenders(results) {
	recommendersList = results;
	$("#recommenders_list_tbody").empty();
	var statusHtml = "<i class='fa fa-check ok' aria-hidden='true'></i>";
	$("#recommendors_completed").html(statusHtml);

	if (recommendersList.length > 0) {

		if (completedRecommendersSectionsCountIncremented == false) {
			completedSectionsCount++;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedRecommendersSectionsCountIncremented = true;
		}

		var docsRows = "";
		for (var i = 0; i < recommendersList.length; i++) {
			var eachDoc = recommendersList[i];
			docsRows += "<tr><td>"
					+ (i + 1)
					+ "</td><td>"
					+ eachDoc["recommenderName"]
					+ "</td><td>"
					+ eachDoc["organization"]
					+ "</td>"
					+ "<td>"
					+ eachDoc["position"]
					+ "</td><td>"
					+ eachDoc["emailAddress"]
					+ "</td><td><a class='fa fa-pencil edit-delete-icon' data-toggle='modal' data-target='#recommenderModal' onclick=\"editStudentRecommender('"
					+ i
					+ "')\"></a><a class='fa fa-trash edit-delete-icon' onclick=\"deleteStudentRecommender('"
					+ eachDoc["studentRecommenderGuid"] + "')\"></a></td></tr>";
		}
		$("#recommenders_list_tbody").append(docsRows);
	} else {
		var statusHtml = "<i class='fa fa-exclamation-triangle pending' aria-hidden='true'></i>";
		$("#recommendors_completed").html(statusHtml);

		if (completedRecommendersSectionsCountIncremented == true) {
			completedSectionsCount--;
			$("#profile_completed_sections_count").text(completedSectionsCount);
			completedRecommendersSectionsCountIncremented = false;
		}
		$("#recommenders_list_tbody")
				.append(
						"<tr><td colspan='6' align='center'>No Recommenders Found</td></tr>");
	}
}

function editStudentRecommender(selectedRecordIndex) {
	var data = recommendersList[selectedRecordIndex];

	$("#recommender_name").val(data["recommenderName"]);
	$("#recommender_organization").val(data["organization"]);
	$("#recommender_position").val(data["position"]);
	$("#recommender_email").val(data["emailAddress"]);

	$("#studentRecommenderGuid").val(data["studentRecommenderGuid"]);

}

function deleteStudentRecommender(studentRecommenderGuid) {

	$
			.confirm({
				content : 'Are You Sure To Delete This Recommender ?',
				buttons : {
					confirm : function() {
						$
								.ajax({
									type : "POST",
									url : "student/deleteStudentRecommender",
									data : studentRecommenderGuid,
									dataType : 'json',
									contentType : "application/json",
									success : function(results) {
										$
												.alert({
													content : "Recommender Deleted Successfully"
												});
										getStudentRecommenders();
									},
									error : function(e) {
										$
												.alert({
													content : "Error While Deleting Recommender.Please Try Again"
												});
									}
								});
					},
				}
			});

}

function addRecommender() {

	if ($("#recommenderForm").valid()) {
		$("#add_recommender_button").prop("disabled", true);
		var data = {};

		data["recommenderName"] = $("#recommender_name").val();
		data["organization"] = $("#recommender_organization").val();
		data["position"] = $("#recommender_position").val();
		data["emailAddress"] = $("#recommender_email").val();
		data["studentRecommenderGuid"] = $("#studentRecommenderGuid").val();

		$.ajax({
			url : 'student/saveStudentRecommender',
			type : "POST",
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(data),
		}).done(function(data) {
			$("#add_recommender_button").prop("disabled", false);
			$.alert({
				content : "Recommender Added Successfully"
			});
			$('#recommenderModal .close').click();
			getStudentRecommenders();
		}).fail(function(jqXHR, textStatus) {
			$.alert({
				content : 'Error While Saving Recommender.Please Try Again'
			});
		});
	}
}