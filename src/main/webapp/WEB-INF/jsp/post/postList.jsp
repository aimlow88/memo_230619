<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="d-flex justify-content-center">
	<div class="col-10">
		<div class="d-flex justify-content-center">
			<h1>글 목록</h1>
		</div>
		
		<table class="table text-center">
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>수정일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${postList}" var="post">
					<tr>
						<td>${post.id}</td>
						<td><a href="/post/post-detail-view?postId=${post.id}">${post.subject}</a></td>
						<td>
							<%-- zonedDateTime -> Date -> String  --%>
							<fmt:formatDate value="${post.createdAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
						</td>
						<td>
							<fmt:formatDate value="${post.updatedAt}" pattern="yyyy년 M월 d일 HH:mm:ss" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="d-flex justify-content-end">
			<a href="/post/post-create-view" class="btn btn-warning">글쓰기</a>
		</div>
	</div>
</div>