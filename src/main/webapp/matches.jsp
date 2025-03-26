<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="pageNumber" value="${requestScope.pageNumber}" />
<c:set var="totalPagesNumber" value="${requestScope.currentPage.pagesNumber()}" />
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Tennis Scoreboard | Finished Matches</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
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
				<img src="images/menu.png" alt="Logo" class="logo">
			</div>
			<span class="logo-text">TennisScoreboard</span>
		</div>
		<div>
			<nav class="nav-links">
				<a class="nav-link" href="${path}/index">Home</a>
				<a class="nav-link" href="${path}/matches">Matches</a>
			</nav>
		</div>
	</section>
</header>
<main>
	<div class="container">
		<h1>Matches</h1>
		<div class="input-container">
			<form action="${path}/matches">
				<input class="input-filter" placeholder="Filter by name" name="filter_by_player_name" type="text"/>
			</form>
			<div>
				<a href="${path}/matches">
					<button class="btn-filter">Reset Filter</button>
				</a>
			</div>
		</div>

		<table class="table-matches">
			<tr>
				<th>Player One</th>
				<th>Player Two</th>
				<th>Winner</th>
			</tr>
			<c:forEach var="match" items="${requestScope.currentPage.matches()}">
				<tr>
					<td>${match.firstPlayerName()}</td>
					<td>${match.secondPlayerName()}</td>
					<td><span class="winner-name-td">${match.winnerName()}</span></td>
				</tr>
			</c:forEach>
		</table>
		<div class="pagination">
			<c:choose>
				<c:when test="${empty requestScope.name}">
					<c:set var="filter" value=""/>
				</c:when>
				<c:otherwise>
					<c:set var="filter" value="&filter_by_player_name=${requestScope.name}"/>
				</c:otherwise>
			</c:choose>
			<c:if test="${pageNumber > 1}">
				<c:if test="${pageNumber > 2}">
					<a class="num-page" href="${path}/matches?page=1${filter}"> first page&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</c:if>
				<a class="prev"
				   href="${path}/matches?page=${pageNumber - 1}${filter}"> < </a>
				<a class="num-page"
				   href="${path}/matches?page=${pageNumber - 1}${filter}">${pageNumber - 1}</a>
			</c:if>
			<a class="num-page current">${pageNumber}</a>
			<c:if test="${pageNumber < totalPagesNumber}">
				<a class="num-page"
				   href="${path}/matches?page=${pageNumber + 1}${filter}">${pageNumber + 1}</a>
				<a class="next"
				   href="${path}/matches?page=${pageNumber + 1}${filter}"> > </a>
				<c:if test="${pageNumber < totalPagesNumber - 1}">
					<a class="num-page"
					   href="${path}/matches?page=${totalPagesNumber}${filter}"> last page </a>
				</c:if>
			</c:if>
		</div>
	</div>
</main>
<footer>
	<div class="footer">
		<p>&copy; Tennis Scoreboard, project from
			<a href="https://zhukovsd.github.io/java-backend-learning-course/">
				zhukovsd/java-backend-learning-course</a>roadmap.</p>
	</div>
</footer>
</body>
</html>
