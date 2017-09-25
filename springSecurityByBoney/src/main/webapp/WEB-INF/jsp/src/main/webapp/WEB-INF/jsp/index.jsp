<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
<head>
<link rel="stylesheet" href="css/index.css" crossorigin="anonymous">
<script src="js/index.js" crossorigin="anonymous"></script>
</head>
<body>
	<div class="container_fluid home_container">
		<div id="video-wrapper">
			<video autoplay id="video-file"> <source
				src="https://s3-us-west-2.amazonaws.com/s3-us-west-2-dev/static+assets/shutterstock_v964345_2.mp4"
				type="video/mp4"></source></video>
		</div>
	</div>
</body>
</html>