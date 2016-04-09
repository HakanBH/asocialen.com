<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'	rel='stylesheet' type='text/css'>
<link href="css/post.css" rel="stylesheet">
<link href="css/lightbox.css" rel="stylesheet">
<link href="css/bootstrap.css"rel="stylesheet">
<link href="css/customStyle.css" rel="stylesheet" >
<link href="css/imageUpload.css"rel="stylesheet" >
<title>Asocialen.com</title>
</head>
<body>
	<jsp:include page="navbar.jsp"></jsp:include>
	<div id="main_container">

		<jsp:include page="prof-pics.jsp"></jsp:include>

		<div class="panel panel-white panel-shadow"
			style="width: 100%; padding-left: 20px;">
			<div id="image_space">
				<a href="#"><img id="prof_pic_post" src="images/pic.png"
					align="left"> </a>
			</div>
			<h1 class="text_title">${selectedAlbum.title}</h1>
		</div>
		<div class="pic_container2 panel panel-white panel-shadow">
			<c:forEach var="pic" items="${selectedPictures}">
				<div class="responsive">
					<div class="img">
						<a  id="basic" href= "images/${currentUser.email}/${selectedAlbum.title}/${pic.name}"
							data-lightbox="album" pic-id="${pic.id}"
							style="text-decoration: none; color: black;"> <img
							class="album_pic" src= "images/${currentUser.email}/${selectedAlbum.title}/${pic.name}"
							alt="${pic.name} Pic Not Found" width="300" height="200">
							<div class="desc">
								${pic.name}
							</div>
						</a>
					</div>
				</div>
			</c:forEach>



		</div>

		<div id="upload_pic" class="panel panel-white panel-shadow">
			<h3 style="font-weight: bold; margin-left: 20px;">Upload a
				picture</h3>
			<div class="input-group image-preview" align="left"
				style="margin-top: 20px; margin-left: 22px;">
				<span class="input-group-btn"> <!-- image-preview-clear button -->
					<button type="button" class="btn btn-default image-preview-clear"
						style="display: none;">
						<span class="glyphicon glyphicon-remove"></span> Clear
					</button> <!-- image-preview-input -->
					<div class="btn btn-default image-preview-input">
						<span class="glyphicon glyphicon-picture"></span> <span
							class="image-preview-input-title">Upload a picture</span> <input
							type="file" accept="image/png, image/jpeg, image/gif"
							name="picture" />
						<!-- rename it -->
					</div> <input class="btn btn-primary post image-preview-input"
					type="submit" value="Upload">
				</span>
			</div>

			<form class="upload_form" method="post" action="./ChangeName"
				name="albumName" style="margin-top: 50px;">
				<h3 style="font-weight: bold;">Rename your album</h3>
				<input id="country" type="text" name="newName" value="OldName"
					placeholder="New Name"> <input type="hidden"
					value="currentAlbum" name="currentAlbum">
				<button id="post_button2" type="submit">Rename!</button>
			</form>
			
			<form class="upload_form" method="post" action="./deleteAlbum"
				name="deleteAlbumForm" style="margin-top: 50px;">
				<h3 style="font-weight: bold;">Delete this album</h3>
				<input id="country" type="hidden" name="albumToDelete" value="${selectedAlbum.id}"
					placeholder="New Name"> 
				<button id="post_button2" type="submit">Delete Album!</button>
			</form>
		</div>

		<div class="corte"></div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.js"></script>
	<script src="js/validation.js"></script>
	<script src="js/bg-changer.js"></script>
	<script src="js/lightbox-plus-jquery.js"></script>
</body>
</html>