
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<script src="js/testing.js" crossorigin="anonymous"></script>
<body>
	<div id="testing-info">
		<div class="apply_form_caption">Testing Information</div>
		<div class="row">
			<div class="col-xs-12 add-header">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#testingModal">Add Test</button>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>S.No</th>
						<th>Test Type</th>
						<th>Test Taken On</th>
						<th>Total Score</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="tests_list_tbody">
				</tbody>
			</table>
		</div>


		<div class="modal fade" id="testingShowModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Detailed Score</h4>
					</div>
					<div class="modal-body">
						<form role="form" class="form-horizontal">

							<div class="form-group" id="verbal_score_view">
								<label class="control-label col-sm-3" for="verbal-score-text">Verbal
									Score </label>
								<div class="col-sm-9">
									<label id="verbal-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="quantative_score_view">
								<label class="control-label col-sm-3" for="quant-score-text">Quantitative
									Score </label>
								<div class="col-sm-9">
									<label id="quant-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="analytical_score_view">
								<label class="control-label col-sm-3"
									for="analytical-score-text">Analytical Score </label>
								<div class="col-sm-9">
									<label id="analytical-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="reading_score_view">
								<label class="control-label col-sm-3" for="reading-score-text">Reading
									Score </label>
								<div class="col-sm-9">
									<label id="reading-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="writing_score_view">
								<label class="control-label col-sm-3" for="writing-score-text">Writing
									Score </label>
								<div class="col-sm-9">
									<label id="writing-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="listening_score_view">
								<label class="control-label col-sm-3" for="listening-score-text">Listening
									Score </label>
								<div class="col-sm-9">
									<label id="listening-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="speking_score_view">
								<label class="control-label col-sm-3" for="speaking-score-text">Speaking
									Score </label>
								<div class="col-sm-9">
									<label id="speaking-score-text"> </label>
								</div>
							</div>
							<div class="form-group" id="reasoning_score_view">
								<label class="control-label col-sm-3" for="speaking-score-text">Reasoning
									Score </label>
								<div class="col-sm-9">
									<label id="reasoning-score-text"> </label>
								</div>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>

		<div class="modal fade" id="testingModal" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closeTestingModal()">&times;</button>
						<h4 class="modal-title">Add Testing</h4>
					</div>
					<div class="modal-body">
						<form role="form" class="form-horizontal" name="testForm"
							id="testForm" method="post">

							<input type="hidden" name="studentTestingGuid"
								id="studentTestingGuid" value="">

							<div class="form-group">
								<label class="control-label col-sm-3" for="test_type">Test
									Type </label>
								<div class="col-sm-9">
									<select name="test_type" id="test_type" required="required"
										onchange="testTypeChange()">
										<option value="">Select Test Type</option>
										<option value="GRE">GRE</option>
										<option value="TOEFL">TOEFL</option>
										<option value="GMAT">GMAT</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="test-taken-on" class="control-label col-sm-3">Test
									Taken On </label>
								<div class="col-sm-9">
									<input type="text" name="test-taken-on"
										placeholder="Test Taken On" class="form-control"
										id="test-taken-on" ""
										required="required" value="">

								</div>
							</div>
							<div class="form-group" id="verbal-score-toggle">
								<label class="control-label col-sm-3" for="verbal-score">Verbal
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="verbal-score" placeholder="130 - 170"
										class="form-control" onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" id="verbal-score"
										required="required" value="" />
								</div>
								<span id="verbal-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Verbal Score</span>
							</div>
							<div class="form-group" id="quant-score_toggle">
								<label class="control-label col-sm-3" for="quant-score">Quantitative
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="quant-score" placeholder="130 - 170"
										class="form-control" onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" id="quant-score"
										required="required" value="" />
								</div>
								<span id="quant-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Quantitative Score</span>

							</div>

							<div class="form-group" id="analytical-score_toggle">
								<label class="control-label col-sm-3" for="analytical-score">Analytical
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="analytical-score"
										id="analytical-score" class="form-control" required="required"
										oninput="isValidStudentScore(this.id);"
										placeholder="0.0 - 6.0" />
								</div>
								<span id="analytical-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Analytical Score</span>

							</div>

							<div class="form-group" id="reading-score_toggle">
								<label class="control-label col-sm-3" for="reading-score">Reading
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="reading-score" placeholder="0 - 30"
										class="form-control" id="reading-score" required="required"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" value="" />
								</div>
								<span id="reading-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Reading Score</span>

							</div>

							<div class="form-group" id="listening-score_toggle">
								<label class="control-label col-sm-3" for="listening-score">Listening
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="listening-score" placeholder="0 - 30"
										class="form-control" id="listening-score" required="required"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" value="" />
								</div>
								<span id="listening-score-error" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Listening Score</span>

							</div>

							<div class="form-group" id="speaking-score_toggle">
								<label class="control-label col-sm-3" for="speaking-score">Speaking
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="speaking-score" placeholder="0 - 30"
										class="form-control" id="speaking-score" required="required"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" value="" />
								</div>
								<span id="speaking-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Speaking Score</span>

							</div>

							<div class="form-group" id="writing-score_toggle">
								<label class="control-label col-sm-3" for="writing-score">Writing
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="writing-score" placeholder="0 - 30"
										class="form-control" id="writing-score" required="required"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" value="" />
								</div>
								<span id="writing-score-error-hidden" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Writing Score</span>

							</div>

							<div class="form-group" id="integrated-reasoning-score_toggle">
								<label class="control-label col-sm-3" for="writing-score">Reasoning
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="integrated-reasoning-score"
										placeholder="1 - 8" class="form-control"
										id="integrated-reasoning-score" required="required"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" value="" />
								</div>
								<span id="reasoning-score-error" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Reasoning Score</span>

							</div>

							<div class="form-group">
								<label class="control-label col-sm-3" for="overall-band-score">Total
									Score </label>
								<div class="col-sm-9">
									<input type="text" name="overall-band-score"
										id="overall-band-score" class="form-control"
										onkeypress="return isNumber(event);"
										oninput="isValidStudentScore(this.id);" required="required"
										value="">
								</div>
								<span id="total-score-error" hidden="hidden"
									style="color: red; display: inline-block;"> Invalid
									Total Score</span>

							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"
							onclick="closeTestingModal()">Close</button>
						<button type="button" class="btn btn-default"
							onclick="addTesting()" id="add_testing_btn">Save</button>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>