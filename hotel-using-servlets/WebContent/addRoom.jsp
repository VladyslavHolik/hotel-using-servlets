<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Hotel room</title>
</head>
<body class="h-100">
	<div class="container h-100">
		<div class="row h-100 justify-content-center align-items-center">
			<div class="col-10 col-md-8 col-lg-6">
				<form action="addRoom" method="post">
					<div class="mb-3">
						<label class="form-label">Number</label> <input type="text"
							class="form-control" name="number">
					</div>
					<div class="mb-3">
						<label class="form-label">Price</label> <input type="number"
							class="form-control" name="price">
					</div>
					<div class="mb-3">
						<label class="form-label">Space for quantity of people</label> <input
							type="number" class="form-control" name="space">
					</div>
					<div class="mb-3">
						<label class="form-label">Class of room</label> <input type="text"
							class="form-control" name="class">
					</div>
					<div class="mb-3">
						<label class="form-label">Room photos</label> <input type="file"
							class="form-control-file" name="file">
					</div>
					<button type="submit" class="btn btn-primary">Upload room
						data</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>