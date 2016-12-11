<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Weather App</title>

<!--    Font -->
<link href="https://fonts.googleapis.com/css?family=Roboto"
	rel="stylesheet">

<!-- Bootstrap Core CSS -->
<link
	href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Table CSS -->
<link
	href="${contextPath}/resources/css/table/dataTables.bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="${contextPath}/resources/css/full-slider.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/style.css" rel="stylesheet">

<!--    Animate.css-->
<link href="${contextPath}/resources/css/animate.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>

	<header id="myCarousel" class="carousel slide"> <!-- Wrapper for Slides -->
	<div class="carousel-inner animated fadeIn">
		<div class="item active animated ">
			<!-- Set the first background image using inline CSS below. -->
			<div class="fill"
				style="background-image: url('${contextPath}/resources/img/carousel/summer/summer1.jpg');"></div>
			<!--
            <div class="carousel-caption">
    <h2>Summer</h2>
</div>
-->
		</div>
		<div class="item animated ">
			<!-- Set the second background image using inline CSS below. -->
			<div class="fill"
				style="background-image: url('${contextPath}/resources/img/carousel/autumn/autumn1.jpg');"></div>
			<!--
                <div class="carousel-caption">
                    <h2>Autumn</h2>
                </div>
-->
		</div>
		<div class="item">
			<!-- Set the third background image using inline CSS below. -->
			<div class="fill"
				style="background-image: url('${contextPath}/resources/img/carousel/winter/winter1.jpg');"></div>
			<!--
                <div class="carousel-caption">
                    <h2>Winter</h2>
                </div>
-->
		</div>
		<div class="item">
			<!-- Set the third background image using inline CSS below. -->
			<div class="fill"
				style="background-image: url('${contextPath}/resources/img/carousel/spring/spring1.jpg');"></div>
			<!--
                <div class="carousel-caption">
                    <h2>Spring</h2>
                </div>
-->
		</div>
	</div>

	</header>

	<div class="title animated bounceIn">
		<div class="row ">
			<div
				class="col-lg-4 col-lg-offset-4 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 col-xs-10 col-xs-offset-1">
				<h1>Welcome to weather map</h1>
			</div>
		</div>
	</div>

	<!-- Page Content -->
	<div class="container-fluid ">
		<div class="addCity animated bounceIn">
			<div class="row">
				<div class="col-lg-12">
					<form action="#" th:action="@{/addCity/add}"
						th:object="${city}" method="post">
						<p>
							Name: <input type="text" th:field="*{name}"
								placeholder="City Name" />
						</p>

						
							<input type="submit" value="add"class="btn btn-default btn-lg btn-addCity" /> 
<!-- 							<input type="reset" -->
<!-- 								value="add" /> -->
						
					</form>
				</div>
			</div>





		</div>
		<div class="delimiter">
			<div class="row ">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<!--                        <img src="img/line.png" class="delimiter-img">-->
					<p></p>
				</div>
			</div>
		</div>
		<div class="table-responsive">
			<div class="row table-row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<table id="example"
						class="table table-bordered table-striped table-condensed table-hover"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>City</th>
								<th>Temperature</th>
								<th>Humidity</th>
								<th>Pressure</th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th>City</th>
								<th>Temperature</th>
								<th>Humidity</th>
								<th>Pressure</th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach items="${cities}" var="city">
								<tr>
									<td>${city.name}</td>
									<td>${city.temperature}</td>
									<td>${city.humidity}</td>
									<td>${city.pressure}</td>
								</tr>
							</c:forEach>    
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<hr>



	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer>
	<div class="row">
		<div class="col-lg-12">
			<p>Copyright &copy; Milos Davitkovic 2016</p>
		</div>
	</div>
	<!-- /.row --> </footer>

	<!-- jQuery -->
	<script src="${contextPath}/resources/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

	<!-- Table JavaScript -->
	<script
		src="${contextPath}/resources/js/table/dataTables.bootstrap.min.js"></script>
	<script
		src="${contextPath}/resources/js/table/jquery.dataTables.min.js"></script>

	<!-- Script to Activate the Carousel -->
	<script>
		$(document).ready(function() {
			$('#example').DataTable();
		});
		$('.carousel').carousel({
			interval : 3000
		//changes the speed
		})
	</script>





</body>
</html>