<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- -- JSTL을 사용하려면 아래의 태그를 붙여 넣는다. -->--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- AJAX사용하려면 jquey 원본 사용 slim사용 안됨 -->
	  	<link rel="stylesheet" href="/static/script/maxcdn.bootstrapcdn.com_bootstrap_4.0.0_css_bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<script src="/static/script/code.jquery.com_jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
 		<script src="/static/script/cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
 		<script src="/static/script/maxcdn.bootstrapcdn.com_bootstrap_4.0.0_js_bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

 		<!-- date picker 사용을 위한 스크립트 -->
 		<link rel="stylesheet" href="/static/script/code.jquery.com_ui_1.13.2_themes_base_jquery-ui.css">
		<script src="/static/script/code.jquery.com_ui_1.13.2_jquery-ui.js"></script>
		
		<link rel="stylesheet" type="text/css" href="/static/css/style.css">

		<meta charset="UTF-8">
		<title>메모 게시판</title>
	</head>
	<body>
		<div id="wrap" class="container">
			<header class="bg-warning">
				<jsp:include page="../include/header.jsp" />
			</header>
			<br>
			<section>
				<jsp:include page="../${viewName}.jsp" />
			</section>
			<br>
			<footer class="bg-warning">
				<jsp:include page="../include/footer.jsp" />
			</footer>
		</div>

	
	</body>
</html>