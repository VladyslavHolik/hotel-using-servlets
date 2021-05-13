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
	<%
		boolean userIsLogged = !(session.getAttribute("user_id") == null);
	%>
	<nav style="width: 100%; margin: auto;"
		class="navbar navbar-expand-sm navbar-light bg-light">
		<div class="container-fluid">
			<span class="navbar-brand">Hotel</span> <span><a
				class="nav-link" href="controller?command=language&lang=ru">🇷🇺</a>
			</span> <span><a class="nav-link"
				href="controller?command=language&lang=en">🇬🇧</a> </span>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="controller?command=home"><fmt:message key='rooms.home' /></a></li>
					<c:choose>
						<c:when test="<%=userIsLogged%>">
							<li class='nav-item'><a class='nav-link'
								href='controller?command=logout'> <fmt:message
										key='index.logout' /></a></li>
						</c:when>

						<c:otherwise>
							<li class='nav-item'><a class='nav-link'
								href='controller?command=getlogin'><fmt:message
										key='index.signin' /></a></li>
							<li class='nav-item'><a class='nav-link'
								href='controller?command=getsignup'><fmt:message
										key='index.signup' /></a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<form action="controller" method="post">
			<div class="row justify-content-center p-2">
				<div class="col-auto">
					<input type="hidden" name="command" value="sorting"> <select
						class="form-select" name="sortingMethod">
						<option selected value="class"><fmt:message
								key='rooms.sortby' /></option>
						<option value="price"><fmt:message key='rooms.price' /></option>
						<option value="space"><fmt:message key='rooms.space' /></option>
						<option value="class"><fmt:message key='rooms.class' /></option>
						<option value="status"><fmt:message key='rooms.status' /></option>
					</select>
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-primary mb-3">
						<fmt:message key='rooms.sort' />
					</button>
				</div>
			</div>
		</form>
		<c:forEach items="${roomsContent.getRooms()}" var="room">
			<div class="row" style="padding: 10px;">
				<div class="col-3">
					<img class="w-100" src="${room.getPreview()}" />
				</div>
				<div class="col-9">
					<a href="controller?command=room&id=${room.getId()}">Room</a>
					<h1>Room Class: ${room.getRoomClass()} Room space:
						${room.getSpace()} Room price: ${room.getPrice()}$ p/h</h1>
				</div>
			</div>
		</c:forEach>
	</div>
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<c:forEach items="${roomsContent.getPages()}" var="page">
				<li class="${page.getPageClass()}">
					<form method="get" action="controller">
						<input type="hidden" name="command" value="rooms"> <input
							type="hidden" name="page" value="${page.getName()}">
						<button type="submit" class="page-link">${page.getName()}</button>
					</form>
				</li>
			</c:forEach>
		</ul>
	</nav>
</body>
</html>