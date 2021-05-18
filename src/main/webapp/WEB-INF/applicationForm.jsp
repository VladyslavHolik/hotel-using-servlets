<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="h-100">
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
	crossorigin="anonymous"></script>
<title><fmt:message key='applicationform.form' /></title>
</head>
<body class="h-100">
	<div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-10 col-md-8 col-lg-6">

				<form action="" method="post">
					<input type="hidden" name="command" value="processapplication" />
					<input type="hidden" name="applicationId" value="${application.id}"/>
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='applicationform.name' /> ${user.firstName}
							${user.lastName} </label>
					</div>
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='applicationform.room.class' /> ${application.roomClass} </label>
					</div>
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='applicationform.room.space' /> ${application.space} </label>
					</div>
					<div class="mb-3">
						<div class="mb-3">
							<label class="form-label"><fmt:message key='applicationform.choice' /></label> 
							<select class="form-select" name="choice" required>
							<option selected value="decline"><fmt:message key='applicationform.decline' /></option>
										
							<c:forEach items="${rooms}" var="room">
								<option value="${room.id}"><fmt:message key='applicationform.room' /> ${room.number}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<button type="submit" class="btn btn-primary">
						<fmt:message key='applicationform.submit' />
					</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>