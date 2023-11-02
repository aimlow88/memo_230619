<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="d-flex justify-content-center">
	<div class="col-10">
		<div class="d-flex justify-content-center">
			<h1>글 쓰기</h1>
		</div>		
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력하세요.">
		<textarea id="content" class="form-control" rows="10" placeholder="내용을 입력하세요."></textarea>
		
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, jpeg, .gif, .png">
		</div>
		
		<div class="d-flex justify-content-between">
			<button type="button" id="postListBtn" class="btn btn-dark">목록</button>
			
			<div>
				<button type="button" id="clearBtn" class="btn btn-secondary">모두 지우기</button>
				<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
			</div>
		</div>
	</div>
	
	<br><br>
	
	<script>
		$(document).ready(function(){
			
			$('#postListBtn').on('click', function(){
				location.href="/post/post-list-view";
			});
			
			$('#clearBtn').on('click', function(){
				$('#subject').val("");
				$('#content').val("");
			});
			
			$('#saveBtn').on('click', function(){
				
				let subject = $('#subject').val().trim();
				let content = $('#content').val().trim();
				let fileName = $('#file').val();
				
				// alert(file);
				
				if (!subject){
					alert("제목을 입력하세요.");
					return false;
				}
				
				if (!content){
					alert("내용을 입력하세요.");
					return false;
				}
				
				// request param들을 구성
				// 이미지나 파일을 업로드할 때는 무조건 form tag로 해야 한다.
				let formData = new FormData();
				formData.append("subject", subject);   // key는 form태그의 name속성과 같고, Request parameter의 명과 같다.
				formData.append("content", content);   // key는 form태그의 name속성과 같고, Request parameter의 명과 같다.
				formData.append("file", $('#file')[0].files[0]);
				
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
				
				$.ajax({
					type:"post"
					, url:"/post/create"
					, data:formData
					, enctype:"multipart/form-data"  // 파일 업로드를 위한 필수 설정
					, processData:false  // 파일 업로드를 위한 필수 설정
					, contentType:false  // 파일 업로드를 위한 필수 설정
					
					// reponse
					, success:function(data){
						if (data.result == "성공"){
							alert("메모가 저장되었습니다.");
							location.href="/post/post-list-view";
						} else {
							alert(data.errorMessage)
						}
					}
					, error:function(request, status, error){
						alert("글을 작성하는데 실패했습니다.")
					}
				})
			})
		})
	
	</script>
</div>