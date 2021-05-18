<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<title>Make application</title>
</head>
<body class="h-100">
	<div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-10 col-md-8 col-lg-6">

				<form action="controller" method="post">
					<input type="hidden" name="command" value="application" />
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='application.space' /></label> <input type="number" min="1" max="6"
							class="form-control" name="space" required>
					</div>
					<div class="mb-3">
					<label class="form-label"><fmt:message
								key='application.roomclass' /></label> 
					<select
						class="form-select" name="roomClass" required>
						<option selected value="1">Apartment</option>
						<option value="2">Balcony</option>
						<option value="3">Connected rooms</option>
						<option value="4">Business</option>
						<option value="5">Bedroom</option>
						<option value="6">De luxe</option>
						<option value="7">Duplex</option>
						<option value="8">Family room</option>
						<option value="9">Honeymoon room</option>
						<option value="10">President</option>
						<option value="11">Standard</option>
					</select>
					</div>
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='application.arrival' /></label> <input type="datetime-local" min="${minTime}"
							class="form-control" name="arrival" required>
					</div>
					<div class="mb-3">
						<label class="form-label"><fmt:message
								key='application.leaving' /></label> <input type="datetime-local" min="${minTime}"
							class="form-control" name="leaving" required>
					</div>

					<button type="submit" class="btn btn-primary">
						<fmt:message key='application.submit' />
					</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>