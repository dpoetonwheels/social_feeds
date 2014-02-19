<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="<c:url value="/resources/js/jquery-1.11.0.js" />"></script>
</head>
<body>
<h1>
	Social Feeds
</h1>

<P>  The time on the server is ${serverTime}. </P>

<p><strong>Twitter Feeds from a user with hashtag search</strong></p>
<p>
<c:forEach items="${tweets}" var="tweet">
	<tr>
		${tweet.getText()}
	</tr>
</c:forEach>
</p>

<p> <strong>Instagram posts from specific user.</strong></p>
<p>
<tr>
	Instagram Picture - ${instauser.getFullName()}
</tr>
</p>

<script type="text/javascript">
	$(document).ready(function() {
		alert('jquery ready');
	});
</script>

</body>
</html>
