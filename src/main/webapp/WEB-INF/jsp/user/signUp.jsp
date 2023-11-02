<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="sign-up-box container">
		<h1 class="mb-4">회원가입</h1>
		<form id="signUpForm" method="post" action="/user/sign-up">
			<table class="sign-up-table table table-bordered">
				<tr>
					<th>*아이디(4자 이상)<br></th>
					<td>
						<div class="d-flex">
							<input type="text" id="loginId" name="loginId" class="form-control col-9" placeholder="아이디를 입력하세요.">
							<button type="button" id="loginIdCheckBtn" class="btn btn-success">중복확인</button><br>
						</div>
						
						<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력헤 주세요.</div>
						<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
						<div id="idCheckOk" class="small text-success d-none">사용가능한 ID입니다.</div>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요.">
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="비밀번호를 입력하세요.">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" id="name" name="name" class="form-control" placeholder="이름을 입력하세요.">
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="text" id="email" name="email" class="form-control" placeholder="이메일을 입력하세요.">
					</td>
				</tr>
			</table>
			
			<button type="submit" id="signUpBtn" class="btn btn-primary float-right">회원가입</button>
		
		</form>
	</div>
	
	<script>
		
		$(document).ready(function(){
			
			$('#loginIdCheckBtn').on('click', function(){
				
				$('#idCheckLength').addClass("d-none");
				$('#idCheckDuplicated').addClass("d-none");
				$('#idCheckOk').addClass("d-none");
				
				let loginId = $('#loginId').val().trim();
				
				if (loginId.length < 4 ){
					$('#idCheckLength').removeClass("d-none");
				} else {
					$('#idCheckLength').addClass("d-none");
				}
				
				$.ajax({
					// type:"get"
					 url:"/user/check-duplicated-id"
					, data:{"loginId":loginId}
					, success: function(data){
						if (data.isDuplicated){
							$('#idCheckOk').addClass("d-none");
						} else {
							$('#idCheckOk').removeClass("d-none");
						}
					}
					, error: function(request, status, error){
						
					}
				})
				
			});
			
			$('#signUpForm').on('submit', function(e){
				e.preventDefault();  // submit 기능 막음
				
				//alert("submit 막음");
				//valiadtion 
				let loginId = $('#loginId').val().trim();
				let password = $('#password').val();
				let confirmPassword = $('#confirmPassword').val();
				let name = $('#name').val().trim();
				let email = $('#email').val().trim();
				
				if (loginId == ''){
					alert("아이디를 입력하세요");
					return false;
				}
				
				if (!password || !confirmPassword){
					alert("비밀번호를 확인하세요")
					return false;
				}
				
				if (password != confirmPassword){
					alert("비밀번호가 틀렸습니다.")
					return false;
				}
				
				if (!name){
					alert("이름을 입력하세요");
					return false;
				}
				
				if (!email){
					alert("이메일을 입력하세요");
					return false;
				}
				
				//
				if ($('#idCheckOk').hasClass('d-none')){
					alert("아이디 중복확인을 다시 해주세요.");
					return false;
				}
				
				// 서버로 보내는 방법 두가지
				//1) submit을 자바스크립트로 동작 시킴
				//$(this)[0].submit();  //
				
				// 2) AJAX = 응답값이 JSON
				let url = $(this).attr('action');
				//
				let params = $(this).serialize();  //폼태그에 있는 name속성의 값으로 파라미터 구성
				console.log(params)
				
				$.post(url, params)
				.done(function(data){
					if (data.code == 200){ // 성공
						alert("가입을 환영합니다. 로그인을 해주세요!!!");
						location.href = "/user/sign-in-view";   // 로그인화면으로 이동
					} else {
						// 로직 실패
						alert("data.errorMessage");
					}
				});
				
		
			});
			
		});
	
	</script>
</div>