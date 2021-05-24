<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title><fmt:message key='myapplications.myapplications'/></title>
</head>
<body>
<%@ include file="/WEB-INF/header/header.jsp" %>
<div class="container p-2">
    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <div class="list-group">

                <c:forEach items="${applications}" var="application">
                    <div class="list-group-item ">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">
                                <fmt:message key='myapplications.number'/>
                                №${application.id}
                            </h5>
                            <small class="text-muted">3 days ago</small>
                        </div>
                        <p class="mb-1">
                            <fmt:message key='myapplications.class'/>
                            : ${application.roomClass}
                        </p>
                        <p class="mb-1">
                            <fmt:message key='myapplications.space'/>
                            : ${application.space}
                        </p>

                        <p class="mb-1">
                            <fmt:message key='myapplications.arrival'/>
                            : ${application.datetimeOfArrival}
                        </p>
                        <p class="mb-1">
                            <fmt:message key='myapplications.leaving'/>
                            : ${application.datetimeOfLeaving}
                        </p>
                        <p class="mb-1">
                            <a href="room?id=${application.roomId}"
                               class="list-group-item-action "> <fmt:message
                                    key='myapplications.room'/>
                            </a>
                        </p>
                        <form action="" method="post">
                            <input type="hidden" name="command" value="book"/> <input
                                type="hidden" name="id" value="${application.id}"/>
                            <button type="submit" class="btn btn-primary">
                                <fmt:message key='myapplications.submit'/>
                            </button>
                        </form>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${applications.size() == 0}">
                <p class="text-center p-5">
                    <fmt:message key='myapplications.noapplications'/>
                </p>
            </c:if>
        </div>
        <div class="col-1"></div>
    </div>
</div>
</body>
</html>