<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

		<div class="d-flex justify-content-center align-items-center">
			<div>
				<form id="signInForm" class="form-group" method="post" action="/user/sign-in">
					<div class="input-group flex-nowrap mt-5">
						<div class="input-group-prepend">
							<span class="input-group-text" id="addon-wrapping">👤</span>
						</div>
						<input type="text" id="loginId" name="loginId" class="form-control" placeholder="ID를 입력하세요.">
					</div>
					<div class="input-group flex-nowrap mt-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="addon-wrapping">🔑</span>
						</div>
						<input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요.">
					</div>
											
					<div id="idCheckLength" class="small text-danger d-none">ID는 4자 이상입니다.</div>
					<div id="loginIdNotExsist" class="small text-danger d-none">ID가 존재하지 않습니다. 가입해 주세요.</div>
					<div id="signInFailure" class="small text-danger d-none">로그인에 실패하였습니다.</div>
					<div id="signInOk" class="small text-success d-none">로그인 성공</div>
					
					<input type="submit" name="signInBtn" class="btn btn-success form-control col-12 mt-4" value="로그인">
				</form>
				<a href="/user/sign-up-view">
					<input type="button" name="signUpBtn" class="btn btn-dark form-control col-12 mt-4" value="회원가입">
				</a>
			</div>
		</div>
					
		<script>
			
			$(document).ready(function(){
				
				$('#signInForm').on('submit', function(e){
					e.preventDefault();
					
					$('#idCheckLength').addClass("d-none");
					$('#loginIdNotExsist').addClass("d-none");
					$('#signInFailure').addClass("d-none");
					$('#signInOk').addClass("d-none");
					
					let loginId = $('#loginId').val().trim();
					let password = $('#password').val();
					
					if (loginId.length < 4){
						$('#idCheckLength').removeClass("d-none");
						return false;
					}
					
					if (!loginId){
						alert("ID를 입력해 주세요");
						return false;
					}
					
					if (!password){
						alert("비밀번호를 입력해 주세요");
						return false;
					}
					
					let url = $(this).attr('action');
					let params = $(this).serialize();  // name속성을 반드시 가져야 한다.
					
					$.post(url, params)
					.done(function(data){
						if (data.code == 200){
							//$('#signInOk').removeClass("d-none");
							// rmf ahrfhrdmfh qhsoa
							location.href = "/post/post-list-view"; 
						} else {
							alert("data.errorMessage"); 
						}
					});
					
					
					
				});
				
			});
		
		</script>
