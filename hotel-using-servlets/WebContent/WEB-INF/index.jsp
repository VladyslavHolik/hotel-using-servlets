<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="holik.hotel.servlet.model.Role"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hotel</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link
	href="https://fonts.googleapis.com/css2?family=Crimson+Pro:wght@500&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="/portal_content/css/index.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
	crossorigin="anonymous"></script>
</head>
<body>
	<nav style="width: 96%; margin: auto;"
		class="navbar navbar-expand-sm navbar-light bg-light">
		<div class="container-fluid">
			<span class="navbar-brand">Hotel</span> <span><a
				class="nav-link" href="language?lang=ru">🇷🇺</a> </span> <span><a
				class="nav-link" href="language?lang=en">🇬🇧</a> </span>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="rooms"> <fmt:message key="index.rooms" /></a></li>
					<c:choose>
						<c:when test="${userRole != null}">
							<li class='nav-item'><a class='nav-link' href='logout'>
									<fmt:message key='index.logout' />
							</a></li>
							<c:choose>
								<c:when test="${!Role.MANAGER.equals(userRole)}">
									<li class='nav-item'><a class='nav-link'
										href='getapplication'> <fmt:message
												key='index.getapplication' /></a></li>
								</c:when>
								<c:otherwise>
									<li class='nav-item'><a class='nav-link'
										href='applications'> <fmt:message
												key='index.applications' /></a></li>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:otherwise>
							<li class='nav-item'><a class='nav-link' href='getlogin'><fmt:message
										key='index.signin' /></a></li>
							<li class='nav-item'><a class='nav-link' href='getsignup'><fmt:message
										key='index.signup' /></a></li>
						</c:otherwise>
					</c:choose>

				</ul>
			</div>
		</div>
	</nav>
	<img src="/portal_content/images/main/5.jpg" class="d-block w-100">
	<p class="text-center">
		<fmt:message key='index.slogo.first' />
	</p>
	<p class="text-center">
		<fmt:message key='index.slogo.second' />
	</p>

</body>
</html>