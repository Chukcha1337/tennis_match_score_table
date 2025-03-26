<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="match" value="${requestScope.match}" />
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
    <style><%@ include file="/css/style.css"%></style>
    <style><%@ include file="/js/app.js"%></style>
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
                <a class="nav-link" href="${path}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <c:if test="${match.tieBreak == true}">
            <div class="current-match-image" style="background-image: url('${path}/images/frame_matches_tiebreak.png');">
            </div>
        </c:if>
        <c:if test="${match.tieBreak == false}">
        <div class="current-match-image" style="background-image: url('${path}/images/frame_matches.png');">
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
                    <td class="table-text">${match.firstPlayerScore.playerName}</td>
                    <td class="table-text">${match.getPlayerSets(match.firstPlayerId)}</td>
                    <td class="table-text">${match.getPlayerGames(match.firstPlayerId)}</td>
                    <c:if test="${match.tieBreak == true}">
                        <td class="table-text">${match.getPlayerPoints(match.firstPlayerId)}</td>
                    </c:if>
                    <c:if test="${match.tieBreak == false}">
                        <td class="table-text">${match.getNormalPoints(match.firstPlayerId)}</td>
                    </c:if>
                    <td class="table-text">
						<c:if test="${match.gameFinished == false}">
                        	<form action="${path}/match-score" method="post">
                            <input type="hidden" name="pointWinnerId" value="${match.firstPlayerId}">
                            <input type="hidden" name="uuid" value="${requestScope.uuid}">
                            <button type="submit" class="score-btn">Score</button>
                        	</form>
						</c:if>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${match.secondPlayerScore.playerName}</td>
                    <td class="table-text">${match.getPlayerSets(match.secondPlayerId)}</td>
                    <td class="table-text">${match.getPlayerGames(match.secondPlayerId)}</td>
                    <c:if test="${match.tieBreak == true}">
                        <td class="table-text">${match.getPlayerPoints(match.secondPlayerId)}</td>
                    </c:if>
                    <c:if test="${match.tieBreak == false}">
                        <td class="table-text">${match.getNormalPoints(match.secondPlayerId)}</td>
                    </c:if>
                    <td class="table-text">
						<c:if test="${match.gameFinished == false}">
                        	<form action="${path}/match-score" method="post">
                            <input type="hidden" name="pointWinnerId" value="${match.secondPlayerId}">
                            <input type="hidden" name="uuid" value="${requestScope.uuid}">
                            <button type="submit" class="score-btn">Score</button>
                        	</form>
						</c:if>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
        <c:if test="${match.tieBreak == true}">
            <h1 style="text-align: center;">TieBreak</h1>
        </c:if>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from
            <a href="https://zhukovsd.github.io/java-backend-learning-course/">
                zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
