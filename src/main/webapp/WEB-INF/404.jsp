<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,700" rel="stylesheet">
<style>
    html,
    body {
        height: 100%;
        margin: 0;
    }

    body {
        background: linear-gradient(#111, #333, #111);
        background-repeat: no-repeat;
        background-size: cover;
        color: #eee;
        position: relative;
        font-family: 'Roboto', sans-serif;
    }

    .message {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        text-align: center;
    }

    h1, h2, h3 {
        margin: 0;
        line-height: .8;
    }

    h2, h3 {
        font-weight: 300;
        color: #C8FFF4;
    }

    h1 {
        font-weight: 700;
        color: #03DAC6;
        font-size: 8em;
    }

    h2 {
        margin: 30px 0;
    }

    h3 {
        font-size: 2.5em;
    }

    h4 {
        display: inline-block;
        margin: 0 15px;
    }

    button {
        background: transparent;
        border: 2px solid #C8FFF4;
        color: #C8FFF4;
        padding: 5px 15px;
        font-size: 1.25em;
        transition: all 0.15s ease;
        border-radius: 3px;
    }

    button:hover {
        background: #03DAC6;
        border: 2px solid #03DAC6;
        color: #111;
        cursor: pointer;
        transform: scale(1.05);
    }
</style>
<div class="message">
    <h1>404</h1>
    <h3>Page not found</h3>
    <h2>Uh oh! It seems that you are lost</h2>
    <a href="/">
        <button>Go Home</button>
    </a>
</div>
