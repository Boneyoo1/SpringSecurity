
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/documents.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/documents.css"
	crossorigin="anonymous">
<body>
	<div id="documents-info">
		<div class="apply_form_caption">Documents Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#addDocumentModel">Add Document</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Doc Type</th>
						<th>Doc Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="docs_list_tbody">
				</tbody>
			</table>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="addDocumentModel" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Document</h4>
					</div>
					<div class="modal-body">
						<form name="documentForm" id="documentForm" method="post">
							<div class="row">
								<div class="col-xs-6">Document Type</div>
								<div class="col-xs-6">
									<select name="document_type" id="document_type"
										required="required">
										<option value="">Select Document Type</option>
										<option value="GRE SCORE">GRE SCORE</option>
										<option value="TOEFL SCORE">TOEFL SCORE</option>
										<option value="TRANSCRIPTS">TRANSCRIPTS</option>
										<option value="RECOMMENDATION LETTER">RECOMMENDATION
											LETTER</option>
										<option value="SOLVENCY CERTIFICATE">SOLVENCY
											CERTIFICATE</option>
										<option value="RESUME">RESUME</option>
										<option value="PASSPORT">PASSPORT</option>
										<option value="F1-VISA">F1-VISA</option>
										<option value="I-20">I-20</option>
										<option value="EAD">EAD</option>
										<option value="GMAT SCORE">GMAT SCORE</option>
									</select> <input type="hidden" name="document_guid" id="document_guid"
										value="">
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6">Choose Document</div>
								<div class="col-xs-6">
									<input type="file" name="document_file" id="document_file"
										required="required" accept='.jpg,.png,.jpeg,.pdf' />
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-default"
							onclick="uploadDocument()" 
							id="doc-upload-btn">Upload</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>