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
<title><fmt:message key='applications.applications' /></title>
</head>
<body>
	<%@ include file="/WEB-INF/header/header.jsp"%>
	<div class="container p-2">
		<div class="row">
			<div class="col-1"></div>
			<div class="col-10">
				<div class="list-group">

					<c:forEach items="${applications}" var="application">
						<a href="getapplicationform?id=${application.id}" class="list-group-item list-group-item-action">
							<div class="d-flex w-100 justify-content-between">
								<h5 class="mb-1"><fmt:message key='applications.number' /> №${application.id}</h5>
								<small class="text-muted">3 days ago</small>
							</div>
							<p class="mb-1"><fmt:message key='applications.class' />: ${application.roomClass}</p>
							<p class="mb-1"><fmt:message key='applications.space' />: ${application.space} </p>
							<p class="mb-1"><fmt:message key='applications.arrival' />: ${application.datetimeOfArrival} </p>
							<p class="mb-1"><fmt:message key='applications.leaving' />: ${application.datetimeOfLeaving}</p>
						</a>
					</c:forEach>
				</div>
			</div>
			<div class="col-1"></div>
		</div>
	</div>
</body>
</html>