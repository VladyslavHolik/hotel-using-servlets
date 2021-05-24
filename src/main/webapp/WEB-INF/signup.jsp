<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class="h-100">
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
    <title>Sign up</title>
</head>
<body class="h-100">
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <div class="col-10 col-md-8 col-lg-6">

            <form action="" method="post">
                <input type="hidden" name="command" value="signup"/>
                <div class="mb-3">
                    <label class="form-label"><fmt:message
                            key='signup.firstname'/></label> <input type="text"
                                                                    class="form-control" name="first_name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label"><fmt:message
                            key='signup.lastname'/></label> <input type="text"
                                                                   class="form-control" name="last_name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label"><fmt:message key='signup.phone'/></label>
                    <input type="tel" class="form-control" name="phone"
                           pattern="[0-9]{1,3} [0-9]{2} [0-9]{3} [0-9]{4}"
                           placeholder="380 99 123 4567" required>
                </div>
                <div class="mb-3">
                    <label class="form-label"><fmt:message key='signup.email'/></label>
                    <input type="email" class="form-control" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label"><fmt:message
                            key='signup.password'/></label> <input type="password"
                                                                   class="form-control" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">
                    <fmt:message key='signup.signup'/>
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>