<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:useBean id="match" class="com.chuckcha.entity.MatchScore" scope="request"/>--%>
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
	<style><%@ include file="/css/style.css"%></style>
	<style><%@ include file="/js/app.js"%></style>
</head>
<body>
<header class="header">
	<section class="nav-header">
		<div class="brand">
			<div class="nav-toggle">
				<img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
			</div>
			<span class="logo-text">TennisScoreboard</span>
		</div>
		<div>
			<nav class="nav-links">
				<a class="nav-link" href="${pageContext.request.contextPath}/index">Home</a>
				<a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
			</nav>
		</div>
	</section>
</header>
<main>
	<div class="container">
		<h1>Match result</h1>
		<div class="current-match-image" style="background-image: url('${pageContext.request.contextPath}/images/places.png');">
		</div>
		<section class="score">
			<table class="table">
				<thead class="result">
				<tr>
					<th></th>
					<th class="table-text">Player</th>
					<c:forEach var="sets" begin="1" end="${requestScope.match.setsNumber}">
						<th class="table-text">${sets} Set</th>
					</c:forEach>
				</tr>
				</thead>
				<tbody>
				<tr class="player1">
					<c:choose>
						<c:when test="${requestScope.match.firstPlayer.id eq requestScope.match.winner.id}">
							<td class="centered-cell">
								<img class="cup-image" src="${pageContext.request.contextPath}/images/cup.png" alt="Cup">
							</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
					<td class="table-text">${requestScope.match.firstPlayer.name}</td>
					<c:forEach var="sets" begin="1" end="${requestScope.match.setsNumber}">
						<td class="table-text">${requestScope.match.getSetResults(sets,requestScope.match.firstPlayer.id)}</td>
					</c:forEach>
				</tr>
				<tr class="player2">
					<c:choose>
						<c:when test="${requestScope.match.secondPlayer.id eq requestScope.match.winner.id}">
							<td class="centered-cell">
								<img class="cup-image" src="${pageContext.request.contextPath}/images/cup.png" alt="Cup">
							</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
					<td class="table-text">${requestScope.match.secondPlayer.name}</td>
					<c:forEach var="sets" begin="1" end="${requestScope.match.setsNumber}">
						<td class="table-text">${requestScope.match.getSetResults(sets,requestScope.match.secondPlayer.id)}</td>
					</c:forEach>
				</tr>
				</tbody>
			</table>
		</section>
		<c:if test="${not empty requestScope.match.winner}">
			<h1 style="text-align: center;">Match is finished!</h1>
			<h2 style="text-align: center;">Winner of match: ${requestScope.match.winner.name}</h2>
			<form style="text-align: center;" action="${pageContext.request.contextPath}/finished-match" method="post">
				<input type="hidden" name="uuid" value="${requestScope.uuid}">
				<button type="submit" class="score-btn">Main menu</button>
			</form>
		</c:if>
	</div>
</main>
<footer>
	<div class="footer">
		<p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
	</div>
</footer>
</body>
</html>
