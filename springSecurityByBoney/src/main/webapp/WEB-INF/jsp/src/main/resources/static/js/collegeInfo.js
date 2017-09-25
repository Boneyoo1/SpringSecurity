var eachRow="";
var collegeinfoDocumentList="";
$(document).ready(function() {
	var schoolGuid = $("#college_info_guid").val();
	getCollegeInfoDocuments(schoolGuid);
//		alert(collegeinfoDocumentList);
	getSchoolInfo(schoolGuid);
});
function getCollegeInfoDocuments(schoolGuid) {

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "student/getSchoolDocuments/" + schoolGuid,
		dataType : 'json',
		success : function(results) {

			processCollegeInfoDocuments(results);
		},
		error : function(e) {
			$.alert({
				content:"Error While Loading School Documents.Please Try Again"
			});
		}
	});
}

function processCollegeInfoDocuments(results) {
	
	collegeinfoDocumentList="";
	var schoolDegreeGuid = $("#form-preferred_start_degree").val();

	var eachSchoolDegreeDocuments = [];
	
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachSchoolDegreeDocument = results[i];
		
			var degreeList = eachSchoolDegreeDocument["degreeList"];
			if (schoolDegreeGuid == "") {
				
				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			} else {
				if (degreeList != null || degreeList != null) {
					if (degreeList.indexOf(schoolDegreeGuid) > -1) {
						eachSchoolDegreeDocuments
								.push(eachSchoolDegreeDocument);
					}
				}
				if (degreeList == "ALL") {
					eachSchoolDegreeDocuments.push(eachSchoolDegreeDocument);
				}
			}
		}
	}
	if (eachSchoolDegreeDocuments.length > 0) {
		
		for (var i = 0; i < eachSchoolDegreeDocuments.length; i++) {
			
			eachRow = eachSchoolDegreeDocuments[i];
//			collegeinfoDocumentList += "<li><label>"+eachRow["documentName"]+":</label><label><i class='fa fa-external-link' aria-hidden='true'></i><a href='"
//			+ eachRow["documentPath"] + "' target='_blank'>"+eachRow["isMandatory"]+"</a></label> </li>";
			collegeinfoDocumentList += "<li><label><a href='"
				+ eachRow["documentPath"] + "' target='_blank'>"+eachRow["documentName"]+"</a>&nbsp;:&nbsp;</label><label>"+eachRow["isMandatory"]+"</label></li>";
		
		}
		$("#collegeDocuments").html(collegeinfoDocumentList);
	}

}

function getSchoolInfo(schoolGuid) {
	$.ajax({
		type : "GET",
		url : "school/getCollegeInfo/" + schoolGuid,
		success : function(results) {
			processSchoolInfo(results);
			console.log(results);
		},
		error : function(e) {
			$.alert({
				content:"Error While Loading School Info.Please Try Again"
			});
		}
	});
}

function processSchoolInfo(schoolData) {

	$("#school_img").html(
			"<img src='images/dummy_school.jpg' class='img-responsive'/>");

	var schoolName = schoolData["schoolName"];
	$("#school_name_p").html(schoolName);

	var schoolLogoURL = schoolData['schoolLogoURL'];
	if (schoolLogoURL == null || schoolLogoURL == undefined) {
		schoolLogoURL = "images/Dummy_School_Logo.png";
	}

	$("#school_logo").attr("src", schoolLogoURL);

	if (schoolData["schoolInfo"] != "" && schoolData["schoolInfo"] != undefined
			&& schoolData["schoolInfo"] != null) {
		$("#college_info_text").html(schoolData["schoolInfo"]);

	} else {
		$("#college_info_text").hide();
	}
	if (addressLineOne == undefined || addressLineOne == null
			|| addressLineOne == 'null') {
		addressLineOne = "";
	}

	if (addressLineTwo == undefined || addressLineTwo == null
			|| addressLineTwo == 'null') {
		addressLineTwo = "";
	}
	var schoolGuid = schoolData['schoolGuid'];
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

	var schoolLogoURL = schoolData['schoolLogoURL'];
	if (schoolLogoURL == null || schoolLogoURL == undefined) {
		schoolLogoURL = "images/Dummy_School_Logo.png";
	}
	var eachSchoolData = "<div class='college_add_btn' id='add_added_college_"
			+ schoolGuid
			+ "'><span onclick=\"addCollegeToStudent('"
			+ schoolGuid
			+ "')\" class='college_add_btn'><i class='fa fa-plus add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;Add College</span></div>"
			+ " <div class='college_logo'>"
			+ "<img src='"
			+ schoolLogoURL
			+ "'class='img-responsive' id='school_logo'/></div>"
			+ "<div class='college_details'><div><label> Website: </label>"
			+ " <a  id='college_website'  target='_blank'> </a></div>"
			+ "<div></div>"
			+ "<div><label> Admissions Office</label></div>"
			+ "<div><label> Email: </label> <label id='college_email'> </label></div>"
			+ "<div><label> Phone: </label> <label id='college_phone'> </label></div>"
			+ "<div id='college_address'></div></div>";

	var phoneNumber = (schoolData["phoneNumber"] == null) ? "NA"
			: schoolData["phoneNumber"];
	var emailAddress = (schoolData["emailAddress"] == null) ? "NA"
			: schoolData["emailAddress"];
	;
	var website = (schoolData["website"] == null) ? "NA"
			: schoolData["website"];

	var addressLineOne = (schoolData["addressLineOne"] == null) ? ""
			: schoolData["addressLineOne"];
	var addressLineTwo = (schoolData["addressLineTwo"] == null) ? ""
			: schoolData["addressLineTwo"];
	var city = (schoolData["city"] == null) ? "" : schoolData["city"];
	console.log(schoolData["city"]);
	console.log(schoolData);
	var state = (schoolData["state"] == null) ? "" : schoolData["state"];
	var zipCode = (schoolData["zipCode"] == null) ? "" : schoolData["zipCode"];
	$(".college_tab_contains_").html(eachSchoolData);
	var addressHtml = "<p>" + addressLineOne + "</p><p>" + addressLineTwo
			+ " </p><p>" + city + ", " + state + " - " + zipCode + "</p>";
	$("#college_address").html(addressHtml);
	$("#college_website").attr("href", "http://" + website);

	var internationAppFees = (schoolData["internationAppFees"] == null) ? "NA"
			: "$" + schoolData["internationAppFees"];
	var internationCredentialEvalFees = (schoolData["internationCredentialEvalFees"] == null) ? "NA"
			: "$" + schoolData["internationCredentialEvalFees"];
	var mailingFee = (schoolData["mailingFee"] == null) ? "NA" : "$"
			+ schoolData["mailingFee"];
	var transcriptsRequires = (schoolData["transcriptsRequires"] == null) ? "NA"
			: schoolData["transcriptsRequires"];
	var recLettersRequires = (schoolData["recLettersRequires"] == null) ? "NA"
			: schoolData["recLettersRequires"];
	var sopRequires = (schoolData["sopRequires"] == null) ? "NA"
			: schoolData["sopRequires"];
	var solvCERTRequires = (schoolData["solvCERTRequires"] == null) ? "NA"
			: schoolData["solvCERTRequires"];
	var resumeRequires = (schoolData["resumeRequires"] == null) ? "NA"
			: schoolData["resumeRequires"];
	var requiresPassport = (schoolData["requiresPassport"] == null) ? "NA"
			: schoolData["requiresPassport"];
	var minimumGREScore = (schoolData["minimumGREScore"] == null) ? "NA"
			: schoolData["minimumGREScore"];
	var minimumGMATcore = (schoolData["minimumGMATcore"] == null) ? "NA"
			: schoolData["minimumGMATcore"];
	var minimumTOEFLScore = (schoolData["minimumTOEFLScore"] == null) ? "NA"
			: schoolData["minimumTOEFLScore"];
	var minimumGPA = (schoolData["minimumGPA"] == null) ? "NA"
			: schoolData["minimumGPA"];
	var greScoreSubmissionSchoolCode = (schoolData["greScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["greScoreSubmissionSchoolCode"];
	var gmatScoreSubmissionSchoolCode = (schoolData["gmatScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["gmatScoreSubmissionSchoolCode"];
	var toeflScoreSubmissionSchoolCode = (schoolData["toeflScoreSubmissionSchoolCode"] == null) ? "NA"
			: schoolData["toeflScoreSubmissionSchoolCode"];
	var schoolCode = (schoolData["schoolId"] == null) ? "NA"
			: schoolData["schoolId"];
	var applicationCheckListLink = (schoolData["applicationCheckListLink"] == null) ? "NA"
			: schoolData["applicationCheckListLink"];

	var requiredTOEFL = (schoolData["requiresTOFELScore"] == null) ? "NA"
			: schoolData["requiresTOFELScore"];
	var requiredI20 = (schoolData["requiresI20"] == null) ? "NA"
			: schoolData["requiresI20"];
	var requiredEAD = (schoolData["requiresEAD"] == null) ? "NA"
			: schoolData["requiresEAD"];
	var requiredF1Visa = (schoolData["requiresF1Visa"] == null) ? "NA"
			: schoolData["requiresF1Visa"];

	var applicationCheckListLinkHtml = "<a href='"
			+ applicationCheckListLink + "' target='_blank'>Application CheckList Link</a>";
	$("#applicationCheckListLink").html(applicationCheckListLinkHtml);
	var gradCreditsRequired = (schoolData["gradCreditsRequired"] == null) ? "NA"
			: schoolData["gradCreditsRequired"];
	var gradTuitionFeePerCredit = (schoolData["gradTuitionFeePerCredit"] == null) ? "NA"
			: "$" + schoolData["gradTuitionFeePerCredit"];
	$("#gradTuitionFeePerCredit").text(gradTuitionFeePerCredit);
	$("#gradCreditsRequired").text(gradCreditsRequired);
	console.log(schoolData["tuitionFeesLink"]);
	console.log(schoolData);
	var tuitionFeesLink = (schoolData["tuitionFeesLink"] == null) ? "NA"
			: schoolData["tuitionFeesLink"];
	$("#tuitionFeesLink").text(tuitionFeesLink);
	var tuitionFeesLinkHtml = "<a href='"
			+ tuitionFeesLink + "' target='_blank'>Tuition & Fees Link</a>";
	$("#tuitionFeesLink").html(tuitionFeesLinkHtml);
	$("#mailingFee").text(mailingFee);
	$("#college_website").text(website);
	$("#college_phone").text(phoneNumber);
	$("#college_email").text(emailAddress);
	$("#internationAppFees").text(internationAppFees);
	$("#internationCredentialEvalFees").text(internationCredentialEvalFees);
	$("#mailingFee").text(mailingFee);
	$("#transcriptsRequires").text(transcriptsRequires);
	$("#recLettersRequires").text(recLettersRequires);
	$("#sopRequires").text(sopRequires);
	$("#solvCERTRequires").text(solvCERTRequires);
	$("#resumeRequires").text(resumeRequires);
	$("#requiresPassport").text(requiresPassport);
	$("#minimumGREScore").text(minimumGREScore);
	$("#minimumGMATScore").text(minimumGMATcore);
	$("#minimumTOEFLScore").text(minimumTOEFLScore);
	$("#minimumGPA").text(minimumGPA);
	$("#greScoreSubmissionSchoolCode").text(greScoreSubmissionSchoolCode);
	$("#gmatScoreSubmissionSchoolCode").text(gmatScoreSubmissionSchoolCode);
	$("#toeflScoreSubmissionSchoolCode").text(toeflScoreSubmissionSchoolCode);
	$("#schoolCode").text(schoolCode);
	$("#requiredTOEFL").text(requiredTOEFL);
	$("#requiredEAD").text(requiredEAD);
	$("#requiredI20").text(requiredI20);
	$("#requiredF1VISA").text(requiredF1Visa);
//	$("#collegeDocuments").html(collegeinfoDocumentList);
	var schoolTerms = schoolData["schoolTerms"];
	schoolTerms = removeDuplicatesSchoolTerms(schoolTerms);
	var eachTermTableHtml = '';
	if (schoolTerms.length > 0) {
		for (var i = 0; i < schoolTerms.length; i++) {

			var eachTerm = schoolTerms[i];

			var termYear = eachTerm["termYear"];
			var term = eachTerm["term"];
			var applicationDeadLineDate = convertDate(eachTerm["applicationDeadLineDate"]);
			var applicationSubmissionStartDate = convertDate(eachTerm["applicationSubmissionStartDate"]);
			var documentsDeadLineDate = convertDate(eachTerm["documentsDeadLineDate"]);
			var earlyDecisionDeadLineDate = convertDate(eachTerm["earlyDecisionDeadLineDate"]);
			var schoolTermGuid = eachTerm["schoolTermGuid"];

			eachTermTableHtml += ("<tr><td>" + termYear + "</td><td >" + term
					+ "</td>" + "<td >" + applicationDeadLineDate
					+ "</td><td >" + applicationSubmissionStartDate
					+ "</td><td >" + documentsDeadLineDate + "</td><td >"
					+ earlyDecisionDeadLineDate + "</td></tr>");
		}
		$("#college_term_info")
				.html(
						"<table><tr><th >Term Year</th><th >Term</th><th >Application End Date</th><th > Application Submission Start Date</th><th >Document End Date</th><th >Early Decision End Date</th></tr>"
								+ eachTermTableHtml + "<table>");

	}

	var schoolDegrees = schoolData["schoolDegrees"];
	schoolDegrees = removeDuplicatesSchoolDegrees(schoolDegrees,
			"degreeOffered");
	if (schoolDegrees.length > 0) {
		for (var i = 0; i < schoolDegrees.length; i++) {
			var eachDegree = schoolDegrees[i];
			var degreeOffered = (eachDegree["degreeOffered"] == null) ? "NA"
					: eachDegree["degreeOffered"];
			var schoolDegreeGuid = (eachDegree["schoolDegreeGuid"] == null) ? "NA"
					: eachDegree["schoolDegreeGuid"];

			var eachDegreeHtml = "<li><lable style='font-weight: normal ;' value='"
					+ schoolDegreeGuid + "'>" + degreeOffered + "</lable></li>";
			$("#college_degree_info").append(eachDegreeHtml);
		}
	}
	var loggedInAccountGuid = $("#login_account_guid").val();
	if (loggedInAccountGuid != null && loggedInAccountGuid != undefined
			&& loggedInAccountGuid != "" && loggedInAccountGuid != "null") {

		getAllStudentSchools();
	} else {
		var addedCollegeHtml = "<i class='fa fa-plus add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;Add College</span></div>";
		$("#add_added_college_").html(addedCollegeHtml);
		$("#add_added_college_").prop("disabled", false);
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
			$.alert({
				content:"Error While Loading Student Schools.Please Try Again"
			});
		}
	});
}

function processAllStudentSchools(results) {
	if (results.length > 0) {
		for (var i = 0; i < results.length; i++) {
			var eachRow = results[i];
			var schoolGuid = eachRow['schoolGuid'];
			var addedCollegeHtml = "<span class='add_college_btn'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
			$("#add_added_college_" + schoolGuid).html(addedCollegeHtml);
			$("#add_added_college_" + schoolGuid).prop("disabled", true);

		}
	}
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
							content:"College Added To Your List Succesfully"
						});
						var addedCollegeHtml = "<span class='add_college_btn'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
						$("#add_added_college_" + schoolGuid).html(
								addedCollegeHtml);
						$("#add_added_college_" + schoolGuid).prop("disabled",
								true);
					},
					error : function(e) {
						$.alert({
							content:"College Added To Your List Succesfully"
						});
						var addedCollegeHtml = "<span class='add_college_btn'><i class='fa fa-check add_college_icon' aria-hidden='true' title='Add College'></i> &nbsp;College Added</span></div>";
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

function removeDuplicatesSchoolTerms(arr) {
	return arr.reduce(function(p, c) {

		var id = [ c.termYear, c.term ].join('|');
		if (p.temp.indexOf(id) === -1) {
			p.out.push(c);
			p.temp.push(id);
		}
		return p;
	}, {
		temp : [],
		out : []
	}).out;
}
function removeDuplicatesSchoolDegrees(originalArray, prop) {
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

function convertDate(inputFormat) {
	if (inputFormat != null) {
		function pad(s) {
			return (s < 10) ? '0' + s : s;
		}
		var d = new Date(inputFormat);
		return [ pad(d.getMonth() + 1), pad(d.getDate()), d.getFullYear() ]
				.join('/');
	} else {
		return "NA";
	}

}
