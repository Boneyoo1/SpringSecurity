<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/recommenders.js" crossorigin="anonymous"></script>
<body>
	<div id="documents-info">
		<div class="apply_form_caption">Recommender Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#recommenderModal">Add Recommender</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Name</th>
						<th>Organization</th>
						<th>Position</th>
						<th>Email</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="recommenders_list_tbody">
				</tbody>
			</table>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="recommenderModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Recommender</h4>
					</div>
					<div class="modal-body">
						<form name="recommenderForm" class="form-horizontal"
							id="recommenderForm" role="form" method="post">

							<input type="hidden" name="studentRecommenderGuid"
								id="studentRecommenderGuid" value="">


							<div class="form-group">
								<label for="recommender_name" class="control-label col-sm-4">Recommender
									Name </label>
								<div class="col-sm-8">
									<input type="text" name="recommender_name"
										placeholder="Recommender Name" class="form-control"
										id="recommender_name" required="required" value="">
								</div>
							</div>
							<div class="form-group">
								<label for="recommender_organization"
									class="control-label col-sm-4">Organization </label>
								<div class="col-sm-8">
									<input type="text" name="recommender_organization"
										placeholder="Organization" class="form-control"
										id="recommender_organization" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="recommender_position" class="control-label col-sm-4">Position
								</label>
								<div class="col-sm-8">
									<input type="text" name="recommender_position"
										placeholder="Position" class="form-control"
										id="recommender_position" required="required" value="">
								</div>
							</div>

							<div class="form-group">
								<label for="recommender_email" class="control-label col-sm-4">Email</label>
								<div class="col-sm-8">
									<input type="text" name="recommender_email" placeholder="Email"
										class="form-control" id="recommender_email"
										required="required" value="">
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							id="add_recommender_button" onclick="addRecommender()">Save</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>