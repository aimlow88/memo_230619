<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
		<div class="h-100 d-flex align-items-center justify-content-between">
			<div>
				<h2 class="font-weight-bold">메모 게시판</h2>
			</div>
			
			<div>
				<c:if test="${!empty userName}">
					<span>${userName}님 안녕하세요</span>
					<a href="/user/sign-out">로그아웃</a>
				</c:if>
			</div>
		</div>