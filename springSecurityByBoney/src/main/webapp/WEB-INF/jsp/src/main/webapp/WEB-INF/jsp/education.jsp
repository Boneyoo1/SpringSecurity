
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/vendor/jquery.dataTables.min.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="css/education.css">
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/education.js" crossorigin="anonymous"></script>
</head>
<body>
	<div id="documents-info">
		<div class="apply_form_caption">Education Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#educationModal">Add Education</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Degree</th>
						<th>Major</th>
						<th>School</th>
						<th>CGPA</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="educations_list_tbody">
				</tbody>
			</table>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="schoolsModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeSchoolModal()">&times;</button>
						<h4 class="modal-title">Choose School/College</h4>
					</div>
					<div class="modal-body">
						<table id="schools_list_tbl"
							class="table table-striped table-bordered table-responsive"
							cellspacing="0" width="100%">
							<thead>
								<tr>
									<th>&nbsp;</th>
									<th>School name</th>
									<th>City</th>
									<th>State</th>
									<th>Country</th>
								</tr>
							</thead>
							<tbody id="schools_list_tbody">
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"
							onclick="closeSchoolModal()">Close</button>
						<button type="button" class="btn btn-default"
							onclick="selectShool()" data-dismiss="modal">Select</button>
					</div>
				</div>
			</div>
		</div>

		<!-- Modal -->
		<div class="modal fade" id="educationModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeEducationModal()">&times;</button>
						<h4 class="modal-title">Add Education</h4>
					</div>
					<div class="modal-body">
						<form name="educationForm" class="form-horizontal"
							id="educationForm" role="form" method="post">

							<input type="hidden" name="studentEducationGuid"
								id="studentEducationGuid" value=""> <input type="hidden"
								name="schoolGuid" id="schoolGuid" value=""> <input
								type="hidden" name="schoolDegreeGuid" id="schoolDegreeGuid"
								value=""> <input type="hidden" name="schoolCity"
								id="schoolCity" value=""><input type="hidden"
								name="schoolCountry" id="schoolCountry" value="">
							<div class="form-group">
								<label for="degree-scool" class="control-label col-sm-4">School/College
								</label>
								<div class="col-sm-7">
									<input type="text" name="degree-scool"
										placeholder="School/College" class="form-control"
										id="degree-scool" required="required" value="">
								</div>
								<div class="col-sm-1 search-icon-add-modal" data-toggle="modal"
									data-target="#schoolsModal" onclick="showSchoolsModal()">
									<i class="fa fa-search" aria-hidden="true"></i>
								</div>
							</div>



							<div class="form-group">
								<label for="degree-obtained" class="control-label col-sm-4">Degree
								</label>
								<div class="col-sm-7">
									<!-- 	<input type="text" name="degree-obtained"
										placeholder="Degree Obtained" class="form-control"
										id="degree-obtained" required="required" value=""> -->

									<select name="degree-obtained"
										placeholder="Select Degree Obtained" id="degree-obtained"
										required="required" aria-hidden="true">
										<option value="">Select Degree</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="degree-obtained" class="control-label col-sm-4">Major
								</label>
								<div class="col-sm-8">
									<input type="text" name="major" placeholder="Major"
										class="form-control" id="major" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="degree-start-date" class="control-label col-sm-4">Degree
									Start Date </label>
								<div class="col-sm-8">
									<input type="text" name="degree-start-date"
										placeholder="Degree Start Date" class="form-control"
										id="degree-start-date" data-date-end-date="0d"
										required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="degree-end-date" class="control-label col-sm-4">Degree
									End Date </label>
								<div class="col-sm-8">
									<input type="text" name="degree-end-date"
										placeholder="Degree End Date" class="form-control"
										id="degree-end-date" data-date-end-date="0d"
										required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="degree-rcieved-on" class="control-label col-sm-4">Degree
									Received On </label>
								<div class="col-sm-8">
									<input type="text" name="degree-rcieved-on"
										placeholder="Degree Recieved On" class="form-control"
										id="degree-rcieved-on" data-date-end-date="0d"
										required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="degree-obtained-cgpa" class="control-label col-sm-4">CGPA
									Obtained</label>
								<div class="col-sm-8">
									<input type="text" name="degree-obtained-cgpa"
										placeholder="0 - 4.0" class="form-control"
										id="degree-obtained-cgpa" required="required" value=""
										onchange="isValidCGPA()" />
								</div>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"
							onclick="closeEducationModal()">Close</button>
						<button type="button" class="btn btn-default"
							id="add_education_button" onclick="addEducation()">Save</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>