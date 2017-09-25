<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SKOOLVILLE</title>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/vendor/jquery-confirm.min.css"
	crossorigin="anonymous">
<script src="js/vendor/jquery.dataTables.min.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/vendor/dataTables.bootstrap.min.css">
<script src="js/prospects.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/prospects.css">
</head>
<body>
	<div class="container page_container">
		<div class="school_admin_container">
			<div class="row school_admin_menu_container">
				<div
					class="col-lg-2 col-md-3 col-sm-4 col-xs-6 school_admin_menu_content">
					<jsp:include page="adminMenu.jsp" />
				</div>

				<div class="col-lg-10 col-md-9 col-sm-8 col-xs-6"
					id="school_admin_tab_content">
					<table id="school_prospects_list_tbl"
						class="table table-striped table-bordered table-responsive"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>S.No</th>
								<th>First name</th>
								<th>Last Name</th>
								<th>Email</th>
								<th>Contact Number</th>
							</tr>
						</thead>
						<tbody id="school_prospects_list_tbl_body">
						</tbody>
						<tfoot>
							<tr id="school_prospects_tfoot_row_no_data">
								<td colspan="5" align="center">No Prospects Found</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>