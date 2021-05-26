<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700" rel="stylesheet">
<link rel="icon" href="data:,">
<style>
    body {
        padding: 0;
        margin: 0;
        font-family: "Oxygen", sans-serif;
    }

    .error-wall {
        width: 100%;
        height: 100%;
        position: fixed;
        text-align: center;
    }
    .error-wall.load-error {
        background-color: #f3785e;
    }
    .error-wall.matinence {
        background-color: #a473b1;
    }
    .error-wall.missing-page {
        background-color: #00bbc6;
    }
    .error-wall .error-container {
        display: block;
        width: 100%;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        -webkit-transform: translate(-50%, -50%);
        -moz-transform: translate(-50%, -50%);
    }
    .error-wall .error-container h1 {
        color: #fff;
        font-size: 80px;
        margin: 0;
    }
    @media (max-width: 850px) {
        .error-wall .error-container h1 {
            font-size: 65px;
        }
    }
    .error-wall .error-container h3 {
        color: #464444;
        font-size: 34px;
        margin: 0;
    }
    @media (max-width: 850px) {
        .error-wall .error-container h3 {
            font-size: 25px;
        }
    }
    .error-wall .error-container h4 {
        margin: 0;
        color: #fff;
        font-size: 40px;
    }
    @media (max-width: 850px) {
        .error-wall .error-container h4 {
            font-size: 35px;
        }
    }
    .error-wall .error-container p {
        font-size: 15px;
    }
    .error-wall .error-container p:first-of-type {
        color: #464444;
        font-weight: lighter;
    }
    .error-wall .error-container p:nth-of-type(2) {
        color: #464444;
        font-weight: bold;
    }
    .error-wall .error-container p.type-white {
        color: #fff;
    }
    @media (max-width: 850px) {
        .error-wall .error-container p {
            font-size: 12px;
        }
    }
    @media (max-width: 390px) {
        .error-wall .error-container p {
            font-size: 10px;
        }
    }
</style>
<div class="error-wall load-error">
    <div class="error-container">
        <c:choose>
            <c:when test="${not empty errorMessage}">
                <h4>${errorMessage}</h4>
            </c:when>
            <c:otherwise>
                <h1>oh no...</h1>
                <h3>We have had an error</h3>
                <h4>Error 500</h4>
                <p>Sorry...please check back in just a moment.</p>
            </c:otherwise>
        </c:choose>
        <br/>
        <a href="/">Go Home</a>
    </div>
</div>
