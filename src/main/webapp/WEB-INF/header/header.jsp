<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav style="width: 96%; margin: auto;"
	class="navbar navbar-expand-sm navbar-light bg-light">
	<div class="container-fluid">
		<span class="navbar-brand">Hotel</span> <span><a
			class="nav-link" href="language?lang=ru">🇷🇺</a>
		</span> <span><a class="nav-link"
			href="language?lang=en">🇬🇧</a> </span>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" aria-current="page"
					href="/"><fmt:message key='rooms.home' /></a></li>
				<c:choose>
					<c:when test="${userRole != null}">
						<li class='nav-item'><a class='nav-link'
							href='logout'> <fmt:message
									key='index.logout' /></a></li>
					</c:when>

					<c:otherwise>
						<li class='nav-item'><a class='nav-link'
							href='signin'><fmt:message
									key='index.signin' /></a></li>
						<li class='nav-item'><a class='nav-link'
							href='signup'><fmt:message
									key='index.signup' /></a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>