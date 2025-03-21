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
        <h1>Current match</h1>
        <c:if test="${requestScope.match.tieBreak == true}">
            <div class="current-match-image" style="background-image: url('${pageContext.request.contextPath}/images/frame_matches_tiebreak.png');">
            </div>
        </c:if>
        <c:if test="${requestScope.match.tieBreak == false}">
        <div class="current-match-image" style="background-image: url('${pageContext.request.contextPath}/images/frame_matches.png');">
        </div>
        </c:if>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${requestScope.match.firstPlayer.name}</td>
                    <td class="table-text">${requestScope.match.getPlayerSets(requestScope.match.firstPlayer.id)}</td>
                    <td class="table-text">${requestScope.match.getPlayerGames(requestScope.match.firstPlayer.id)}</td>
                    <c:if test="${requestScope.match.tieBreak == true}">
                        <td class="table-text">${requestScope.match.getPlayerPoints(requestScope.match.firstPlayer.id)}</td>
                    </c:if>
                    <c:if test="${requestScope.match.tieBreak == false}">
                        <td class="table-text">${requestScope.match.getNormalPoints(requestScope.match.firstPlayer.id)}</td>
                    </c:if>
                    <td class="table-text">
						<c:if test="${empty requestScope.match.winner}">
                        	<form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="pointWinnerId" value="${requestScope.match.firstPlayer.id}">
                            <input type="hidden" name="uuid" value="${requestScope.uuid}">
                            <button type="submit" class="score-btn">Score</button>
                        	</form>
						</c:if>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${requestScope.match.secondPlayer.name}</td>
                    <td class="table-text">${requestScope.match.getPlayerSets(requestScope.match.secondPlayer.id)}</td>
                    <td class="table-text">${requestScope.match.getPlayerGames(requestScope.match.secondPlayer.id)}</td>
                    <c:if test="${requestScope.match.tieBreak == true}">
                        <td class="table-text">${requestScope.match.getPlayerPoints(requestScope.match.secondPlayer.id)}</td>
                    </c:if>
                    <c:if test="${requestScope.match.tieBreak == false}">
                        <td class="table-text">${requestScope.match.getNormalPoints(requestScope.match.secondPlayer.id)}</td>
                    </c:if>
                    <td class="table-text">
						<c:if test="${empty requestScope.match.winner}">
                        	<form action="${pageContext.request.contextPath}/match-score" method="post">
                            <input type="hidden" name="pointWinnerId" value="${requestScope.match.secondPlayer.id}">
                            <input type="hidden" name="uuid" value="${requestScope.uuid}">
                            <button type="submit" class="score-btn">Score</button>
                        	</form>
						</c:if>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <c:if test="${requestScope.match.tieBreak == true}">
            <h1 style="text-align: center;">TieBreak</h1>
        </c:if>
<%--        <c:if test="${not empty requestScope.match.winner}">--%>
<%--            <h2>Match is finished!</h2>--%>
<%--            <h3>Winner of match: ${requestScope.match.winner.name}</h3>--%>
<%--			<form action="${pageContext.request.contextPath}/finished-match.jsp" method="post">--%>
<%--				<input type="hidden" name="uuid" value="${requestScope.uuid}">--%>
<%--				<button type="submit" class="score-btn">Main menu</button>--%>
<%--			</form>--%>
<%--		</c:if>--%>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
