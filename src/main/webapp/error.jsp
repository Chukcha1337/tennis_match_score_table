<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Tennis Scoreboard | Match Score</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
	<style>
		<%@ include file="/css/style.css" %>
	</style>
	<style>
		<%@ include file="/js/app.js" %>
	</style>
</head>
<body>
<header class="header">
	<section class="nav-header">
		<div class="brand">
			<div class="nav-toggle">
				<img src="${path}/images/menu.png" alt="Logo" class="logo">
			</div>
			<span class="logo-text">TennisScoreboard</span>
		</div>
		<div>
			<nav class="nav-links">
				<a class="nav-link" href="${path}/index">Home</a>
				<a class="nav-link" href="${path}/new-match">New match</a>
				<a class="nav-link" href="${path}/matches">Matches</a>
			</nav>
		</div>
	</section>
</header>
<main>
	<h1>Error with code ${requestScope.status} has acquired</h1>
	<section>
		<article>
			<div class="content">
				<h2>${requestScope.errorName}</h2>
				<div class="content-body">
					<h3>${requestScope.message}</h3>
					<div class="message">
						<c:forEach items="${requestScope.stackTrace}" var="line">
						<p style="color: red;">${line}</p>
					</c:forEach>
					</div>
				</div>
			</div>
		</article>
	</section>

</main>
<footer>
	<div class="footer">
		<p>&copy; Tennis Scoreboard, project from
			<a href="https://zhukovsd.github.io/java-backend-learning-course/">
				zhukovsd/java-backend-learning-course</a>roadmap.</p>
	</div>
</footer>
</body>

