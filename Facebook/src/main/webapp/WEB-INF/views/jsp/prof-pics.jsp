<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asocialen.com</title>
<link rel="stylesheet" href="css/customStyle.css">
</head>
<body>
	<div id="user_prof"
		style="background-image: url('images/backgrounds/background-2.jpg');">
		<div id="pic_name_block">
			<a href="#"><img id="profpicturemain"
				src="${currentUser.profilePath}" align="left"> </img></a>
			<p id="user_name">${currentUser.firstName}
				${currentUser.lastName}</p>
		</div>
	</div>
</body>
</html>