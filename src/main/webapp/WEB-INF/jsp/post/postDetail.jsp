<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<div class="d-flex justify-content-center">
	<div class="col-10">
		<div class="d-flex justify-content-center">
			<h1>글 상세</h1>
		</div>		
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요." value="${post.subject}">
		<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요.">${post.content}</textarea>
		
		<!-- 이미지가 있을 때에만 이미지 영역 추가 -->
		<c:if test="${!empty post.imagePath}">
			<div>
				<img src="${post.imagePath}" alt="업로된 이미지" width="300">
			</div>
		</c:if>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, jpeg, .gif, .png">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="deleteBtn" class="btn btn-secodary" data-del-post-id=${post.id}>삭제</button>
			
			<div>
				<a href="/post/post-list-view" class="btn btn-dark">목록</a>
				<button type="button" id="saveBtn" class="btn btn-warning" data-post-id=${post.id}>수정</button>
			</div>
		</div>
		
		<script>
			$(document).ready(function(){
				
				$('#saveBtn').on('click', function(){
					
					let postId = $(this).data("post-id");
					let subject = $('#subject').val().trim();
					let content = $('#content').val().trim();
					let fileName = $('#file').val();
					
				
					
					if (!subject){
						alert("제목을 입력하세요.");
						return false;
					}
					
					if (!content){
						alert("내용을 입력하세요.");
						return false;
					}
					
					// 파일이 업로드 된 경우에만 확장자 체크
					if (fileName){
						// alert("파일이 있다.")
						// 확장자만 추출 후 소문자로 변경
						let ext = fileName.split(".").pop().toLowerCase();
						
						if ($.inArray(ext, ['jpg','jpeg', 'png', 'gif']) == -1){
							alert("이미지 파일만 업로드 할 수 있습니다.");
							$('#file').val("");      // 파일명을 지운다..
							return;
						}
					}
					
					// request param들을 구성
					// 이미지나 파일을 업로드할 때는 무조건 form tag로 해야 한다.
					let formData = new FormData();
					formData.append("postId", postId);   // key는 form태그의 name속성과 같고, Request parameter의 명과 같다.
					formData.append("subject", subject);   // key는 form태그의 name속성과 같고, Request parameter의 명과 같다.
					formData.append("content", content);   // key는 form태그의 name속성과 같고, Request parameter의 명과 같다.
					formData.append("file", $('#file')[0].files[0]);
				
					$.ajax({
						type:"put"
						, url:"/post/update"
						, data:formData
						, enctype:"multipart/form-data"  // 파일 업로드를 위한 필수 설정
						, processData:false  // 파일 업로드를 위한 필수 설정
						, contentType:false  // 파일 업로드를 위한 필수 설정
						
						// reponse
						, success:function(data){
							if (data.result == "성공"){
								alert("메모가 저장되었습니다.");
								location.reload(true);
							} else {
								alert(data.errorMessage)
							}
						}
						, error:function(request, status, error){
							alert("글을 작성하는데 실패했습니다.")
						}
					});
				});
				
				
				$('#deleteBtn').on('click', function(){
					
					let postId = $(this).data("del-post-id");
					alert(postId);
					
					$.ajax({
						type:"DELETE"
						, url:"/post/delete"
						, data:{"id":postId}
						, success:function(data){
							if (data.code == 200) {
								alert("삭제되었습니다.");
								location.href="/post/post-list-view"
							} else {
								alert(data.errorMessage);
							}
						}
						, error:function(request, status, error){
							alert("삭제 실패 - 관리자 문의")
						}
					})

					
				});
				
				
			});
		</script>
	</div>
	
	<br><br>