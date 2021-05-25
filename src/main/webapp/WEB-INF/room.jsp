<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="holik.hotel.servlet.repository.model.Role" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="data:,">
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0"
            crossorigin="anonymous">
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
            crossorigin="anonymous"></script>
    <title>Room ${room.number}</title>
</head>
<body>
<%@ include file="/WEB-INF/header/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-1"></div>
        <div class="col-10 p-5">
            <img
                    src="${room.preview}" class="img-fluid"/>
            <c:if test="${Role.USER.equals(userRole)}">
                <div class="d-flex justify-content-center p-4">
                    <a class="btn btn-primary" href="/order?id=${room.id}" role="button"><fmt:message key='room.book'/></a>
                </div>
            </c:if>
        </div>
        <div class="col-1"></div>
    </div>
</div>
</body>
</html>