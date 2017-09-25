<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Spring Security Example</title>
</head>
<body>

	<form method="post" action="/login">
		userName:<input type="text" name="form-username"></br> </br> </br> password:<input
			type="text" name="form-password"> <input type="submit"
			value="Submit" onclick=""> <input type="hidden"
			name="${_csrf.parameterName}" value="${_csrf.token}">


	</form>
</body>

</html>