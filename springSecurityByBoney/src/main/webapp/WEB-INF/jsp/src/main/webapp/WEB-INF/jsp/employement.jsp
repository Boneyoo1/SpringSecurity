<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<script src="js/employement.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/intlTelInput.css"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/employment.css">
<script src="js/vendor/intlTelInput.js" charset="UTF-8"
	crossorigin="anonymous"></script>

<body>
	<div id="documents-info">
		<div class="apply_form_caption">Employment Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#employmentModal">Add Employment</button>
			</div>
		</div>

		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Position</th>
						<th>Employer</th>
						<th>From</th>
						<th>To</th>
						<th>City</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="employements_list_tbody">
				</tbody>
			</table>
		</div>


		<!-- Modal -->
		<div class="modal fade" id="employmentModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Employment</h4>
					</div>
					<div class="modal-body">
						<form name="employementForm" class="form-horizontal"
							id="employementForm" role="form" method="post">

							<input type="hidden" name="studentEmployementGuid"
								id="studentEmployementGuid" value="">
							<div class="form-group">
								<label for="position" class="control-label col-sm-4">Position
									Title </label>
								<div class="col-sm-8">
									<input type="text" name="
										position"
										placeholder="Position Title" class="form-control"
										id="position" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="employer_name" class="control-label col-sm-4">Employer
									Name </label>
								<div class="col-sm-8">
									<input type="text" name="employer_name"
										placeholder="Employer Name" class="form-control"
										id="employer_name" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="employement-start-date"
									class="control-label col-sm-4"> Start Date </label>
								<div class="col-sm-8">
									<input type="text" name="employement-start-date"
										placeholder=" Start Date" class="form-control"
										id="employement-start-date" data-date-end-date="0d"
										required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="employement-end-date" class="control-label col-sm-4">
									End Date </label>
								<div class="col-sm-8">
									<input type="text" name="employement-end-date"
										placeholder="End Date" class="form-control"
										id="employement-end-date" data-date-end-date="0d"
										required="required" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="job_category" class="control-label col-sm-4">Job
									Category</label>
								<div class="col-sm-8">
									<input type="text" name="job_category"
										placeholder="Job Category" class="form-control"
										id="job_category" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="industry_category" class="control-label col-sm-4">
									Industry Category</label>
								<div class="col-sm-8">
									<input type="text" name="industry_category"
										placeholder="Industry Category" class="form-control"
										id="industry_category" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="supervisor_name" class="control-label col-sm-4">
									Supervisor Name</label>


								<div class="col-sm-8">
									<input type="text" name="supervisor_name"
										placeholder="Supervisor Name" class="form-control "
										id="supervisor_name" required="required" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="supervisor_phone_number"
									class="control-label col-sm-4"> Supervisor Phone </label>

								<div class="col-sm-8">
									<input type="tel" name="supervisor_phone_number"
										placeholder="Supervisor Phone Number " class="form-control"
										id="supervisor_phone_number" required="required" value=""
										onkeypress="return isNumber(event);"
										onchange="supervisorPhoneNumberValidation(this.id);"><span
										id="supervisor_phone_number-valid-msg"
										class="hide phoneNumberError"><i
										class="fa fa-check green" style='color: green;'
										aria-hidden="true"></i></span><span
										id="supervisor_phone_number-error-msg"
										class="hide phoneNumberError"><i
										class="fa fa-times red" aria-hidden="true"></i></span>
								</div>

							</div>

							<div class="form-group">
								<label for="supervisor_email" class="control-label col-sm-4">
									Supervisor Email</label>
								<div class="col-sm-8">
									<input type="text" name="supervisor_name"
										placeholder="Supervisor Email" class="form-control"
										id="supervisor_email" required="required" value="">
								</div>
							</div>


							<div class="form-group">
								<label for="worked_city" class="control-label col-sm-4">
									City</label>
								<div class="col-sm-8">
									<input type="text" name="worked_city" placeholder="City"
										class="form-control" id="worked_city" required="required"
										value="">
								</div>
							</div>

							<div class="form-group">
								<label for="worked_state" class="control-label col-sm-4">
									State</label>
								<div class="col-sm-8">
									<select name="worked_state" id="worked_state"
										placeholder="Select State" aria-hidden="true"
										class="worked_state">
									</select> <input id="worked_state_input" class="form-control"
										placeHolder="Enter State" type="text" aria-hidden="true" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-sm-4" for="worked_country">Country
								</label>
								<div class="col-sm-8">
									<select name="
										worked_country"
										placeholder="Select Country" id="worked_country"
										required="required"
										onchange="loadStatesByCountryGuid(this.id);">
										<option value="">Select Country</option>
									</select>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							id="add_employement_button" onclick="addEmployement()">Save</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>