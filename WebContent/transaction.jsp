<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>TALARIA</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="assets/css/main.css" rel="stylesheet">
    <link href="assets/css/loginmodal.css" rel="stylesheet">
    <link href="assets/css/register.css" rel="stylesheet">
    <link href="assets/css/productpage.css" rel="stylesheet">
	<link rel="stylesheet" href="assets/css/font-awesome-4.6.3/css/font-awesome.min.css">
	<!-- <link rel="stylesheet" id="font-awesome-css" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" type="text/css" media="screen"> -->
 

    <script src="assets/js/jquery.min.js"></script>
	<!-- <script src="assets/js/custom.js"></script> -->
	<!-- <script src="assets/js/pinterest.js"></script> -->

	
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="assets/js/html5shiv.js"></script>
      <script src="assets/js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body data-spy="scroll" data-offset="0">
	
	<!-- ========== HEADER SECTION ========== -->
	<div id="itemwrap">
		<div class="container">
			<br>	
			<div class="row">
				<div class="col-lg-6 col-lg-offset-3">
					<h1>TALARIA</h1>
					<h2>A Footwear Company</h2>
					<button id="shopbtn" data-toggle="modal" data-target="#login-modal" class = "btn btn-lg btn-header">START SHOPPING</button>
					<button id="regbtn" data-toggle="modal" data-target="#register-modal" class = "btn btn-lg btn-header">REGISTER NOW</button>
				</div>
			</div>
		</div><!-- /container -->
	</div><!-- /headerwrap -->

	<!-- NAVBAR -->
	<nav class="navbar navbar-default navbar-custom" role="navigation">
	  <div class="navbar-header">
	    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	      <span class="icon-bar"></span>
	      <span class="icon-bar"></span>
	      <span class="icon-bar"></span>
	    </button>    
	  </div>
	  <div class="">
	    <ul class="nav navbar-nav">
	    	<li><a href="#about" class="smoothScroll nav-item">Home</a></li>
	        <li><a href="#about" class="smoothScroll nav-item">Products</a></li>
	        <li><a href="#about" class="smoothScroll nav-item">About</a></li>
	        <li><a href="#about" class="smoothScroll nav-item">Contact</a></li>
	    </ul>
	  </div>
	</nav>
	
	
	<!-- ========== WHITE SECTION ========== -->
	<div id="w">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-lg-offset-2">
					<h3 class = "pull-left">
						PROVIDING <b>QUALITY</b> LOCALLY MADE PRODUCTS. STYLISH AND COMFORTABLE FOR YOUR EVERYDAY USE. 
					</h3>
				</div>
			</div>
		</div><!-- /container -->
	</div><!-- /w -->

	<!-- ========== CAROUSEL / DESCRIPTION - DARK GREY SECTION ========== -->
	<div id="w2">
		<div class="container">
			<div class = "cm-header">
				<span>TRANSACTION</span>
			</div>
		</div><!-- /container -->
		<br>

		<div class="container">
			<div class = "row">
				<div class = "col-lg-4 col-lg-offset-1">
					<img class="" src="assets\img\ub-coreblack.png" alt="">
					<div class ="row">
					<c:set var="pd" value="${pd}" scope="request"></c:set>
						<!-- <br> -->
						<h4>${pd.name}</h4>
						<h5>${pd.price}</h5>
						<!-- <hr> -->
					</div>
				</div>
				<div class = "col-lg-6 product-details vcenter">
					<form method="POST" action="Transaction">
						<input type="hidden" name="csrfPreventionSalt" value="${csrfPreventionSalt}"/>
						<div class = "row">
						<c:set var="n" value="${name}" scope="request"></c:set>
							<h6 class="text-space"><b>Full Name</b></h6>
							<input type="text" name="name" disabled="true" class="text-fld col-lg-10 disabled" value="${n}">
						</div>
						<div class = "row">
						<c:set var="s" value="${shipad}" scope="request"></c:set>
							<h6 class="text-space"><b>Shipping Address</b></h6>
							<input type="text" name="shipadd" disabled="true" class="text-fld col-lg-10 disabled" value="${s}">
						</div>
						<div class = "row">
						<c:set var="b" value="${billad}" scope="request"></c:set>
							<h6 class="text-space"><b>Billing Address</b></h6>
							<input type="text" name="billadd" disabled="true" class="text-fld col-lg-10 disabled" value="${b}">
						</div>
						<div class = "row">
							<h6 class="text-space"><b>Credit Card No.</b></h6>
							<input type="text" name="creditcard"= class="text-fld col-lg-10" placeholder="Credit Card No.">
						</div>
						<div class = "row">
							<h6 class="text-space"><b>Quantity</b></h6>
							<input type="number" name="quantity" class="text-fld col-lg-2" placeholder="Qty.">
							<div class="col-lg-3 col-lg-offset-1">
								<input type="submit" name="login" class="btn btn-lg transact-btn" value="CONFIRM">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

		<br>
		<div class="container">
			<div class = "cm-header">
				<span>TALARIA</span>
			</div>
		</div><!-- /container -->
		<br>
		<br>
		
		<div class="container">
			<div class = "row col-lg-5">
				<div class="col-lg-6 desc desc-a">
					<h4><i class="fa fa-bullseye"></i> THE GOAL</h4>
					<p>Etiam dictum gravida nibh at accum san. Quisque aliquam risus bibendum diam elementum, et tempus enim suscipit. Duis sit amet euismod.</p>
					<br>
					<br>
					<h4><i class="fa fa-desktop"></i> THE RESULT</h4>
					<p>Etiam dictum gravida nibh at accum san. Quisque aliquam risus bibendum diam elementum, et tempus enim suscipit. Duis sit amet euismod.</p>
				</div>
				
				<div class="col-lg-6 desc">
					<h4><i class="fa fa-bolt"></i> THE IDEA</h4>
					<p>Etiam dictum gravida nibh at accum san. Quisque aliquam risus bibendum diam elementum, et tempus enim suscipit. Duis sit amet euismod.</p>
					<br>
					<br>
					<h4><i class="fa fa-bar-chart"></i> THE STATICS</h4>
					<p>Etiam dictum gravida nibh at accum san. Quisque aliquam risus bibendum diam elementum, et tempus enim suscipit. Duis sit amet euismod.</p>
				</div>
			</div>
			<div class = "col-lg-offset-1 row col-lg-6">
				<div class="row">
					<div class = "cm-header">
						<span>SIGN UP</span>
					</div>
					</br>
					<form>
						<input type="hidden" name="csrfPreventionSalt" value="${csrfPreventionSalt}"/>
						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-8 col-lg-offset-2" placeholder="Full Name">
						</div>
						<div class = "row">
							<input type="email" name="email" class="text-fld col-lg-8 col-lg-offset-2" placeholder="Email">
						</div>
						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-8 col-lg-offset-2" placeholder="Username">
						</div>
						<div class = "row">
							<input type="password" name="pass" class="pass-fld col-lg-8 col-lg-offset-2" placeholder="Password">
						</div>
						<div class = "row">
							<input type="password" name="pass" class="pass-fld col-lg-8 col-lg-offset-2" placeholder="Confirm Password">
						</div>

						<br>
						<div class = "cm-header">
							<span>BILLING ADDRESS</span>
						</div>
						</br>

						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="House #">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Street">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Subdivision">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="City">
						</div>
						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-3 col-lg-offset-3" placeholder="Postal">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Country">
						</div>

						<br>
						<div class = "cm-header">
							<span>SHIPPING ADDRESS</span>
						</div>
						</br>

						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="House #">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Street">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Subdivision">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="City">
						</div>
						<div class = "row">
							<input type="text" name="user" class="text-fld col-lg-3 col-lg-offset-3" placeholder="Postal">
							<input type="text" name="user" class="text-fld col-lg-3" placeholder="Country">
						</div>
						<div class = "row">
						<br>
						<input type="submit" name="login" class="btn btn-lg register-btn" value="REGISTER">
						</div>
					</form>
				</div>
			</div>
		</div>

	</div><!-- /dg -->
	
	<!-- ========== SERVICES - GREY SECTION ========== -->
	<div id="g">
		<div class="container">
			<div class="row">
				<h3>PROJECT DETAILS</h3>
				<br>
				<br>
				<div class="col-lg-8 desc">
					<h4>About This Project</h4>
					<p>In order to reach a larger market, Talaria Footwear Company has decided to set up their own online store. With knowledge in developing secure web applications, the company anticipates to be able to construct their own e-commerce portal.</p>
				</div>
				<div class="col-lg-4 desc desc-b">
					<h4>Project Data</h4>
					<p>
						<i class="icon-tag"></i> Footwear<br/>
						<i class="icon-time"></i> 2016<br/>
						<i class="icon-globe"></i> www.talaria.com<br/>
					</p>
				</div>
			</div>
		</div><!-- /container -->
	</div><!-- /g -->
	
	<!-- ========== FOOTER SECTION ========== -->
	<div id="f">
		<div class="container">
			<div class="row">
					<h3><b>CONTACT US</b></h3>
					<br>
					<div class="col-lg-4">
						<h3><b>Send Us A Message:</b></h3>
						<h3>talaria.2016@gmail.com</h3>
						<br>
					</div>
					
					<div class="col-lg-4">	
						<h3><b>Call Us:</b></h3>
						<h3>+63 917-546-9169</h3>
						<br>
					</div>
					
					<div class="col-lg-4">
						<h3><b>We Are Social</b></h3>
						<p>
							<a href="index.html#"><i class="fa fa-facebook"></i></a>
							<a href="index.html#"><i class="fa fa-twitter"></i></a>
							<a href="index.html#"><i class="fa fa-envelope"></i></a>
						</p>
						<br>
					</div>
				</div>
			</div>
		</div><!-- /container -->
	</div><!-- /f -->
	
	<div id="c">
		<div class="container">
			<p>Created by <a href="#">Miguel Sietereales and Jason Sioco.</a></p>
		
		</div>
	</div>
	
	<div class="modal fade modal-custom" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	  	<div class="modal-dialog">
			<div class="loginmodal-container">
				<h1 class = "to-white"><b>TALARIA</b></h1><br>
			  	
			  	<form>
			  		<input type="hidden" name="csrfPreventionSalt" value="${csrfPreventionSalt}"/>
					<input type="text" name="user" placeholder="Username">
					<input type="password" name="pass" placeholder="Password">
					<input type="submit" name="login" class="login loginmodal-submit" value="LOGIN">
			  	</form>
				
			  	<div class="login-help centered">
					<a href="#">Register</a> - <a href="#">Forgot Password</a>
			  	</div>
			</div>
		</div>
	 </div>
	
	<div class="scroll-top-wrapper ">
		<span class="scroll-top-inner">
			<i class="fa fa-2x fa-arrow-circle-up"></i>
		</span>
	</div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- <script src="assets/js/bootstrap.min.js"></script> -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="assets/js/main.js"></script>
</body>
</html>