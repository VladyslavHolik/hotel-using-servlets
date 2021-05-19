<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
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
<title>Hotel rooms</title>
</head>
<body>
	<%@ include file="/WEB-INF/header/header.jsp"%>
	<div class="container">
		<form action="" method="post">
			<div class="row justify-content-center p-2">
				<div class="col-auto">
					<input type="hidden" name="command" value="sorting"> <select
						class="form-select" name="sortingMethod">
						<option selected value="class"><fmt:message
								key='rooms.sortby' /></option>
						<option value="price"><fmt:message key='rooms.sort.price' /></option>
						<option value="space"><fmt:message key='rooms.sort.space' /></option>
						<option value="class"><fmt:message key='rooms.sort.class' /></option>
						<option value="status"><fmt:message
								key='rooms.sort.status' /></option>
					</select>
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-primary mb-3">
						<fmt:message key='rooms.sort' />
					</button>
				</div>
			</div>
		</form>
		<div class="container">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="list-group">

						<c:forEach items="${roomsContent.rooms}" var="room">
							<a href="room?id=${room.id}" class="list-group-item list-group-item-action">
								<div class="d-flex w-100 justify-content-between">
									<h5 class="mb-1"><fmt:message key="rooms.room" /> №${room.number}</h5>
								</div>
								<div class="row" style="padding: 10px;">
									<div class="col-5">
										<img class="w-100" src="${room.preview}" />
									</div>
									<div class="col-7">
										<p>
											<fmt:message key="rooms.class" />
											: ${room.roomClass}
										</p>
										<p>
											<fmt:message key="rooms.space" />
											: ${room.space}
										</p>
										<p>
											<fmt:message key="rooms.price" />
											${room.price}$ p/h <br />
										</p>
										<p>
											<fmt:message key="rooms.status" />
											: ${room.availability.toString().toLowerCase()}
										</p>
									</div>
								</div>
							</a>
						</c:forEach>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
		</div>
	</div>
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center p-3">
			<c:forEach items="${roomsContent.pages}" var="page">
				<li class="${page.pageClass}">
					<form method="post" action="">
						<input type="hidden" name="command" value="rooms"> <input
							type="hidden" name="page" value="${page.name}">
						<button type="submit" class="page-link">${page.name}</button>
					</form>
				</li>
			</c:forEach>
		</ul>
	</nav>
</body>
</html>