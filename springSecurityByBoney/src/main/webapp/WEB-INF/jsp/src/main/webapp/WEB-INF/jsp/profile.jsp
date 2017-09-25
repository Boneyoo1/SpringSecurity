<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="com.denkensol.universaladmission.entity.Account"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SKOOLVILLE</title>
<link rel="stylesheet" href="css/vendor/intlTelInput.css"
	crossorigin="anonymous">
<script src="js/vendor/intlTelInput.js" charset="UTF-8"
	crossorigin="anonymous"></script>
<script src="js/profile.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/profile.css">
<script src="js/vendor/bootstrap-datepicker.min.js" charset="UTF-8"></script>

<link rel="stylesheet" href="css/vendor/bootstrap-datepicker3.min.css"
	crossorigin="anonymous"></link>
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
</head>
<body id="profilrScreen">

	<form role="form" class="profile-form form-horizontal" method="post"
		id="profile-form" name="profile-form">
		<input type="hidden" id="student_profile_id" value="" /> <input
			type="hidden" id="email_address_hidden" value="<%=userEmail%>" />
		<div class="profile-info-content" id="personal-info">
			<div class="apply_form_caption">Personal Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					id="profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-firstname"
						class="control-label col-sm-3 required">First Name </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-firstname"
							placeholder="First Name" class="form-control"
							id="form-user-firstname" required="required" value="" />
					</div>
				</div>
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 ">
					<label for="form-user-middlename" class="control-label col-sm-3">Middle
						Name</label>
					<div class="col-sm-9">
						<input type="text" name="form-user-middlename"
							placeholder="Middle Name" class="form-control"
							id="form-user-middlename" value="" />
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-lastname"
						class="control-label col-sm-3 required">Last Name </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-lastname"
							placeholder="Last Name" class="form-control"
							id="form-user-lastname" required="required" value="" />
					</div>
				</div>
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3" for="form-user-suffix">Suffix
					</label>
					<div class="col-sm-9">
						<select name="form-user-suffix" id="form-user-suffix">
							<option value="">Select Suffix</option>
							<option value="Jr.">Jr.</option>
							<option value="Sr.">Sr.</option>
							<option value="II">II</option>
							<option value="III">III</option>
							<option value="IV">IV</option>
							<option value="V">V</option>
							<option value="VI">VI</option>
							<option value="VII">VII</option>
							<option value="VIII">VIII</option>
							<option value="IX">IX</option>
							<option value="X">X</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label for="form-user-gender" class="control-label col-sm-3">Gender</label>
					<div class="col-sm-9">
						<label class="radio-inline"><input type="radio"
							name="form-user-gender" value="Male">Male</label> <label
							class="radio-inline"><input type="radio"
							name="form-user-gender" value="Female">Female</label>
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-dob" class="control-label col-sm-3 required">Date
						Of Birth </label>

					<div class="col-sm-9 date">
						<input class="form-control hasDatepicker" type="text"
							name="form-user-dob" id="form-user-dob"
							placeholder="Date Of Birth" required="required"
							data-date-end-date="0d" />
					</div>


				</div>
			</div>
			<div class="row">
				<div
					class=" form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-marital-status">Marital Status</label>
					<div class="col-sm-9">
						<select name="form-user-marital-status"
							id="form-user-marital-status" required="required">
							<option value="">Select Marital Status</option>
							<option value="Married">Married</option>
							<option value="Single">Single</option>
							<option value="Widowed">Widowed</option>
							<option value="Divorced">Divorced</option>
						</select>
					</div>
				</div>
			</div>
			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="save-personal-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="permanent-address-info-btn"
					onclick="gotoPermanentAddressInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Permanent Address</abbr>
				</button>
			</div>
		</div>
		<div class="profile-info-content" id="permanent-address-info">
			<div class="apply_form_caption">Permanent Address Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-permanent-address-line-1"> Address Line 1</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-permanent-address-line-1"
							name="form-user-permanent-address-line-1"
							placeholder="Address Line 1" required="required"></textarea>
					</div>
				</div>

				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 ">
					<label class="control-label col-sm-3"
						for="form-user-permanent-address-line-2"> Address Line 2</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-permanent-address-line-2"
							name="form-user-permanent-address-line-2"
							placeholder="Address Line 2"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-permanent-address-line-3"> Address Line 3</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-permanent-address-line-3"
							name="form-user-permanent-address-line-3"
							placeholder="Address Line 3"></textarea>
					</div>
				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-permanent-city"
						class="control-label col-sm-3 required">City </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-permanent-city"
							placeholder="City" class="form-control"
							id="form-user-permanent-city" required="required" value="" />
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-permanent-state"
						class="control-label col-sm-3 required">State </label>
					<div class="col-sm-9">
						<input id="form-user-permanent-state-input" class="form-control"
							placeHolder="Enter State" type="text" aria-hidden="true" /> <select
							name="form-user-permanent-state" id="form-user-permanent-state"
							aria-hidden="true">
						</select>


					</div>
				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-permanent-country">Country </label>

					<div class="col-sm-9">
						<select name="form-user-permanent-country"
							placeholder="Select Country" id="form-user-permanent-country"
							required="required"
							onchange="loadStatesByCountry('form-user-permanent-state');">
							<option value="">Select Country</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-permanent-zip-code"
						class="control-label col-sm-3 required">Zip Code </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-permanent-zip-code"
							placeholder="Zip Code" class="form-control"
							id="form-user-permanent-zip-code" required="required" value="" />
					</div>
				</div>
			</div>
			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="personal-info-btn"
					onclick="showPersonalInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Personal Info</abbr>
				</button>
				<button type="button" class="btn" id="save-address-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="mailing-address-info-btn"
					onclick="gotoMailingInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Mailing Address</abbr>
				</button>
			</div>
		</div>
		<div class="profile-info-content" id="mailing-address-info">
			<div class="apply_form_caption">Mailing Address Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
					style="text-align: left; letter-spacing: 1px">
					<label class="control-label col-sm-7">Mailing Address Same
						As Permanent&nbsp;:&nbsp;</label>
					<div class="col-sm-5">
						<label class="radio-inline"><input type="radio"
							name="addressradio" value="Yes"
							onclick="makeMailingAddressInfo();">Yes</label> <label
							class="radio-inline"><input type="radio"
							name="addressradio" value="No" onclick="makeMailingAddressInfo()">No</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-mailing-address-line-1"> Address Line 1</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-mailing-address-line-1"
							name="form-user-mailing-address-line-1"
							placeholder="Address Line 1" required="required"></textarea>
					</div>
				</div>

				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-mailing-address-line-2"> Address Line 2</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-mailing-address-line-2"
							name="form-user-mailing-address-line-2"
							placeholder="Address Line 2"></textarea>
					</div>
				</div>
			</div>
			<div class="row">

				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-mailing-address-line-3"> Address Line 3</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-mailing-address-line-3"
							name="form-user-mailing-address-line-3"
							placeholder="Address Line 3"></textarea>
					</div>
				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-mailing-city"
						class="control-label col-sm-3 required">City </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-mailing-city"
							placeholder="City" class="form-control"
							id="form-user-mailing-city" value="" required="required" />
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-mailing-state"
						class="control-label col-sm-3 required">State </label>
					<div class="col-sm-9">
						<input id="form-user-mailing-state-input" class="form-control"
							placeHolder="Enter State" type="text" aria-hidden="true" /> <select
							name="form-user-mailing-state" id="form-user-mailing-state"
							aria-hidden="true">

						</select>


					</div>
				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-mailing-country">Country </label>
					<div class="col-sm-9">
						<select name="form-user-mailing-country"
							placeholder="Select Country" id="form-user-mailing-country"
							required="required"
							onchange="loadStatesByCountry('form-user-mailing-state');"
							aria-hidden="true">
							<option value="">Select Country</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-mailing-zip-code"
						class="control-label col-sm-3 required">Zip Code </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-mailing-zip-code"
							placeholder="Zip Code" class="form-control"
							id="form-user-mailing-zip-code" required="required" value="" />
					</div>
				</div>
			</div>

			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="permanent-address-info-btn"
					onclick="gotoPermanentAddressInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Permanent Address</abbr>
				</button>
				<button type="button" class="btn" id="save-address-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="contact-info-btn"
					onclick="gotoContactInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Contact Info</abbr>
				</button>

			</div>
		</div>
		<div class="profile-info-content" id="contact-info">
			<div class="apply_form_caption">Contact Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-email">Email Address </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-email"
							placeholder="Email Address" class="form-user-email"
							id="form-user-email" required="required" value="">
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-home-contact-no">Home Phone </label>
					<div class="col-sm-9">
						<input type="tel" name="form-user-home-contact-no"
							placeholder="Home Phone Number" class="form-control"
							name="form-user-home-contact-no" id="form-user-home-contact-no"
							onkeypress="return isNumber(event);"
							onkeydown="phoneNumberValidation('form-user-home-contact-no');"
							value="" required="required"> <span
							id="form-user-home-valid-msg" class="hide phoneNumberError"><i
							class="fa fa-check green" style='color: green;'
							aria-hidden="true"></i></span><span id="form-user-home-error-msg"
							class="hide phoneNumberError"><i class="fa fa-times red"
							aria-hidden="true"></i></span>
					</div>

				</div>
			</div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-work-contact-no">Work Phone</label>
					<div class="col-sm-9">
						<input type="tel" name="form-user-work-contact-no"
							placeholder="Work Phone Number" class="form-control"
							id="form-user-work-contact-no" value=""
							onchange="phoneNumberValidation('form-user-work-contact-no');"
							onkeypress="return isNumber(event);"> <span
							id="form-user-work-valid-msg" class="hide phoneNumberError"><i
							class="fa fa-check green" style='color: green;'
							aria-hidden="true"></i></span> <span id="form-user-work-error-msg"
							class="hide phoneNumberError"><i class="fa fa-times red"
							aria-hidden="true"></i></span>

					</div>
				</div>
			</div>

			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="mailing-address-info-btn"
					onclick="gotoMailingInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Mailing Address</abbr>
				</button>
				<button type="button" class="btn" id="save-contact-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="geographic-info-btn"
					onclick="gotoGeographicInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Geographic Info</abbr>
				</button>

			</div>
		</div>
		<div class="profile-info-content" id="geographic-info">
			<div class="apply_form_caption">Geographic Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="user-country-of-birth">Country Of Birth</label>
					<div class="control-label col-sm-9 ">
						<select name="user-country-of-birth" placeholder="Select Country"
							id="user-country-of-birth" required="required">
							<option value="">Select Country</option>
						</select>
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required" for="form-user-cob">City
						Of Birth </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-cob"
							placeholder="City Of Birth" class="form-user-cob form-control"
							id="form-user-cob" required="required">
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required" for="user-religious">Religious
						Preference</label>
					<div class="col-sm-9">
						<select name="user-religious" placeholder="Select Ethnicity"
							class="user-religious" id="user-religious" required="required">
							<option value="">Select Religion</option>
						</select>
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-ethnicity">Ethnicity </label>
					<div class="col-sm-9">
						<select name="form-user-ethnicity" placeholder="Select Ethnicity"
							id="form-user-ethnicity" required="required">
							<option value="">Select Ethnicity</option>
							<option value="American Indian or Alaska Native">American
								Indian or Alaska Native</option>
							<option value="Asian">Asian</option>
							<option value="Black or African American">Black or
								African American</option>
							<option value="Native Hawaiian or Other Pacific Islander">Native
								Hawaiian or Other Pacific Islander</option>
							<option value="White">White</option>
						</select>
					</div>
				</div>
			</div>
			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="contact-info-btn"
					onclick="gotoContactInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Contact Info</abbr>
				</button>
				<button type="button" class="btn" id="save-geographic-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="goto-address-info-btn"
					onclick="gotoCitizenshipInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Citizenship Info</abbr>
				</button>

			</div>
		</div>

		<div class="profile-info-content" id="citizenship-info">
			<div class="apply_form_caption">Citizenship Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12"
					style="text-align: left; letter-spacing: 1px">
					<label class="control-label col-sm-7">Are you a US Citizen
						? &nbsp;&nbsp; </label>
					<div class="col-sm-5">
						<label class="radio-inline"><input type="radio"
							name="uscitizen" value="Yes" id="uscitizen"
							onclick="isUSCitizen(this.id);">Yes</label> <label
							class="radio-inline"><input type="radio" name="uscitizen"
							value="No" id="not-uscitizen" onclick="isUSCitizen(this.id);">No</label>
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="user-citizenship-country">Citizenship Country</label>
					<div class="col-sm-9">
						<select name="user-citizenship-country"
							placeholder="Select Citizenship Country"
							id="user-citizenship-country" required="required">
							<option value="">Select Citizenship Country</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="user-citizenship-visa-type">Required Visa Type </label>
					<div class="col-sm-9">
						<select name="user-citizenship-visa-type"
							placeholder="Select Visa Type" id="user-citizenship-visa-type">
							<option id="" value="">F-1</option>
						</select>
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="user-primary-language">First Language </label>
					<div class="col-sm-9">
						<select name="user-primary-language" id="user-primary-language"
							required="required">
							<option value="">Select First Language</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12c">
					<label class="control-label col-sm-3" for="user-second-language">Second
						Language </label>
					<div class="col-sm-9">
						<select name="user-second-language" id="user-second-language">
							<option value="">Select Second Language</option>
						</select>
					</div>
				</div>
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3" for="user-third-language">Third
						Language</label>
					<div class="col-sm-9">
						<select name="user-third-language" id="user-third-language">
							<option value="">Select Third Language</option>
						</select>
					</div>
				</div>
			</div>
			<div class="btn-group apply-btn-group">
				<button type="button" class="btn" id="geographic-info-btn"
					onclick="gotoGeographicInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Geographic Info</abbr>
				</button>
				<button type="button" class="btn" id="save-citizenship-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
				<button type="button" class="btn" id="emergency-contact-info-btn"
					onclick="gotoEmergencyContactInfo()">
					<i class="fa fa-fast-forward"></i>&nbsp;&nbsp;Emergency Info</abbr>
				</button>

			</div>
		</div>
		<div class="profile-info-content " id="emergency-contact-info">
			<div class="apply_form_caption ">Emergency Contact Information</div>
			<div class="row profile_tabs">
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_personal_info"
					onclick="showPersonalInfo()">Personal Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_permanent_address"
					onclick="gotoPermanentAddressInfo()">Permanent Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_mailing_address"
					onclick="gotoMailingInfo()">Mailing Address</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_contact_info"
					onclick="gotoContactInfo()">Contact Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_geographic_info"
					onclick="gotoGeographicInfo()">Geographic Info</div>

				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_citizenship_info"
					onclick="gotoCitizenshipInfo()">Citizenship Info</div>
				<div
					class="col-lg-2 col-md-3 col-sm-6 col-xs-12 profile_tab_emergency_contact"
					onclick="gotoEmergencyContactInfo()">Emergency Contact</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-emergency-contact-name"> Contact Name </label>
					<div class="col-sm-9">
						<input type="text" name="form-emergency-contact-name"
							placeholder="Emergency Contact Name"
							id="form-emergency-contact-name" required="required" value="">
					</div>
				</div>
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-emergency-contact-no"> Contact Number</label>
					<div class="col-sm-9">
						<input type="text" name="form-emergency-contact-no"
							placeholder="Emergency Contact Number" name="phoneNumber"
							id="form-emergency-contact-no" required="required" value=""
							onkeypress="return isNumber(event,this.id);"
							onkeydown="phoneNumberValidation('form-emergency-contact-no');"><span
							id="form-emergency-valid-msg" class="hide phoneNumberError"><i
							class="fa fa-check green" style='color: green;'
							aria-hidden="true"></i></span><span id="form-emergency-error-msg"
							class="hide phoneNumberError"><i class="fa fa-times red"
							aria-hidden="true"></i></span>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-emergency-contact-relationship"> Contact
						Relationship </label>
					<div class="col-sm-9">
						<select name="form-emergency-contact-relationship"
							placeholder="Emergency Contact Relationship"
							id="form-emergency-contact-relationship" required="required"
							value="">
							<option value="">Select RelationShip</option>
							<option value="FATHER">Father</option>
							<option value="MOTHER">Mother</option>
							<option value="SPOUSE">Spouse</option>
							<option value="BROTHER">Brother</option>
							<option value="SISTER">Sister</option>
							<option value="FRIEND">Friend</option>
							<option value="OTHER">Other</option>

						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div class=" form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 "
					style="text-align: left; letter-spacing: 1px">
					<label class="control-label col-sm-7">Emergency Address
						Same As Permanent&nbsp;:&nbsp;</label>
					<div class="col-sm-5">
						<label class="radio-inline"><input type="radio"
							name="emergencyaddressradio" value="Yes"
							onclick="makeEmergencyAddressInfo()">Yes</label> <label
							class="radio-inline"><input type="radio"
							name="emergencyaddressradio" value="No"
							onclick="makeEmergencyAddressInfo()">No</label>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-emergency-address-line-1 required"> Address
						Line 1</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-emergency-address-line-1"
							name="form-user-emergency-address-line-1"
							placeholder="Address Line 1" required="required"></textarea>
					</div>
				</div>

				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-emergency-address-line-2"> Address Line 2</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-emergency-address-line-2"
							name="form-user-emergency-address-line-2"
							placeholder="Address Line 2"></textarea>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
					<label class="control-label col-sm-3"
						for="form-user-emergency-address-line-3"> Address Line 3</label>
					<div class="col-sm-9">
						<textarea class="form-control address-textarea" rows="2"
							id="form-user-emergency-address-line-3"
							name="form-user-emergency-address-line-3"
							placeholder="Address Line 3"></textarea>
					</div>
				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-emergency-city"
						class="control-label col-sm-3 required">City </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-emergency-city"
							placeholder="City" class="form-control"
							id="form-user-emergency-city" value="" required="required" />
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-emergency-state"
						class="control-label col-sm-3 required">State </label>

					<div class="col-sm-9">
						<input id="form-user-emergency-state-input" class="form-control"
							placeHolder="Enter State" type="text" aria-hidden="true" /> <select
							name="form-user-emergency-state" id="form-user-emergency-state"
							aria-hidden="true">

						</select>

					</div>

				</div>

				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label class="control-label col-sm-3 required"
						for="form-user-emergency-country">Country </label>
					<div class="col-sm-9" id="form-user-emergency-country-div">
						<select name="form-user-emergency-country"
							placeholder="Select Country" id="form-user-emergency-country"
							required="required"
							onchange="loadStatesByCountry('form-user-emergency-state');">
							<option value="">Select Country</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row">
				<div
					class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12 required">
					<label for="form-user-emergency-zip-code"
						class="control-label col-sm-3 required">Zip Code </label>
					<div class="col-sm-9">
						<input type="text" name="form-user-emergency-zip-code"
							placeholder="Zip Code" class="form-control"
							id="form-user-emergency-zip-code" required="required" value="" />
					</div>
				</div>
			</div>
			<div class="btn-group apply-btn-group" id="apply-btn-group">

				<button type="button" class="btn" id="goto-address-info-btn"
					onclick="gotoCitizenshipInfo()">
					<i class="fa fa-fast-backward"></i>&nbsp;&nbsp;Citizenship Info</abbr>
				</button>

				<button type="button" class="btn" id="save-emergency-info"
					onclick="savePersonalInfo(this.id)">
					<i class="fa fa-cloud"></i>&nbsp;&nbsp;Save</abbr>
				</button>
			</div>
		</div>
	</form>
	<script>
		
	</script>
</body>

</html>