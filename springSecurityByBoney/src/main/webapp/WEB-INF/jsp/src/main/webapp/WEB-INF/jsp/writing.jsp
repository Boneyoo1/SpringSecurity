
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/writing.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/writing.css">
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/vendor/jquery-te-1.4.0.min.js" crossorigin="anonymous"></script>
</head>
<body>
	<div id="documents-info">
		<div class="apply_form_caption">Personal Essay Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" id="writingModalbutton"
					class="btn btn-primary" data-toggle="modal"
					data-target="#writingModal">Add Personal Essay</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Writing Text</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="writings_list_tbody">
				</tbody>
			</table>
		</div>


		<div class="modal fade" id="writingShowModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Working Text</h4>
					</div>
					<div class="modal-body">
						<form role="form" class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-12">
									<div class="row">
										<label class="sr-only" for="student_writing_guid"
											id="student_writing_guid"></label>
										<div class="col-xs-20">
											<textarea id="writing_text_text" class="form-control"
												row="10" maxlength="5000" name="writing_text_text" required
												readonly></textarea>


										</div>


									</div>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							data-backdrop="static" data-keyboard="false"
							onclick="closeWritingShowModal();">Close</button>
					</div>
				</div>
			</div>
		</div>


		<!-- Modal -->
		<div class="modal fade" id="writingModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeWritingModal()">&times;</button>
						<h4 class="modal-title">Add Personal Essay</h4>
					</div>
					<div class="modal-body">
						<form name="writingForm" id="writingForm" role="form"
							method="post" class="form-horizontal" accept-charset="UTF-8"
							method="POST">
							<div class="row">
								<div class="col-xs-20 writing-add-lbl">Writing Text</div>
							</div>
							<div class="row">
								<div class="col-xs-20">

									<textarea class="form-control" id="text" name="text"
										maxlength="5000" placeholder="" rows="10"></textarea>
								</div>
								<span class="pull-right label label-default" id="count_message"
									style="background-color: #999;"></span>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							data-backdrop="static" data-keyboard="false"
							onclick="closeWritingModal()">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addWriting()" id="add_writing_button">Save</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>