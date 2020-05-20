<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$(function(){
	console.log("A");
// 	console.log("_csrf.parameterName : " + ${_csrf.parameterName});
// 	console.log("_csrf.token : " + "${_csrf.token}");
//로그인 id /pwd 유효성 검사
// 	$.ajaxPrefilter(function (options){
// 		var headerName = "${_csrf.headerName}";
// 		var token = "${_csrf.token}";
// 		console.log(headerName);
// 		console.log(token);
// 		console.log(options.method);
// 		console.log(options);
// 		alert(options);
// 		if(options.type === "post"){
// 			options.headers = options.headers || {};
// 			options.headers[headerName] = token;
// 			console.log(options.headers[headerName]);
// 			alert(options.headers[headerName]);
// 		}
// 	});
// 	$.ajaxPrefilter(function(options, originalOptions, jqXHR){
// 		var token = "${_csrf.token}";
// 		jqXHR.setRequestHeader('X-CSRF-Token', token);
// 	});
// 	$('#loginBtn').click(function() {
// 		var id = $('#username').val();
// 		var pwd = $('#password').val();
// 		var remember_us = $('#remember_us').is(':checked');
// 		$.ajax({
// 			type : 'post', // 비밀번호의 값이 url에 표시되면 안 되기 때문에 보안을 위해 POST 방법으로 값을 보낸다
// 			url : '${pageContext.request.contextPath}/login.do',
// 			data : {
// 				mem_id : id,
// 				mem_pwd : pwd,
// 				remember_memId : remember_us
// 			},
// 			success : function(data) {
// 				console.log(data);
// 				if (data == 0) { //로그인 실패시
// 					console.log(data);
// 					$('#spanLoginCheck').text('로그인 정보를 정확히 입력해주세요.');					
// 				}else if (data == -2) { // 아이디가 사용중이라면?
// 				  console.log(data);
// 				  location.href = '${pageContext.request.contextPath}/redundant.do?mem_id=' + id + '&mem_pwd=' + pwd + '&remember_memId=' + remember_us;						
// 				}else { //로그인 성공시
// 					console.log("로그인 성공");
// 					alert("로그인 성공")
// 					//location.href = '${pageContext.request.contextPath}/user/storeChoice';
// 				}
// 			}
// 		});
// 	});
})
</script>
</head>
<body>
<!-- Cookie가 비어있지 않을 때 checked 속성을 줌 -->
	<c:if test="${not empty cookie.user_check}">
		<c:set value="checked" var="checked"/>
	</c:if>

	<form action="/member/login.do" method="post">
		<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">
		<div class="container">
			<div class="area_inputs wow fadeIn">
				<div class="form-group">
					<label class="font-weight-bold text-white" for="inputId">아이디</label>
					<div>
						<input type="text" class="form-control" id="mem_id" name="mem_id" value="${cookie.user_check.value}" placeholder="아이디">
					</div>
				</div>
				<div class="form-group">
					<label class="font-weight-bold text-white" for="inputPassword">비밀번호</label>
					<div>
						<input type="password" class="form-control" id="mem_pwd" name="mem_pwd" placeholder="비밀번호">
					</div>
				</div>
				<div class="form-group">
					<span class="font-weight-bold text-white bg-dark" id="spanLoginCheck"></span>
				</div>
				<div class="form-group">
					<label class="font-weight-bold text-white"> 
						<input type="checkbox" id="remember_us" name="remember_memId" ${checked}> 아이디 기억하기
					</label>
					 <div class="interval_height a_none">
						<a href="${pageContext.request.contextPath}/user/userSearch">&nbsp; 계정찾기</a>
					</div>  
					<div>
						<button id="loginBtn" type="submit"	class="btn btn-primary btn-block">로그인</button>
					</div>
				</div>
				<div class="form-group">
					<a class="btn btn-deep-orange btn-block" href="${pageContext.request.contextPath}/member/joinForm.do">회원가입</a>
				</div>
				<%--스프링 시큐리티 제공 최근 발생한 인증 오류 메시지--%>
				<%--https://postitforhooney.tistory.com/entry/SpringSecurity-%EC%B4%88%EB%B3%B4%EC%9E%90%EA%B0%80-%EC%9D%B4%ED%95%B4%ED%95%98%EB%8A%94-Spring-Security-%ED%8D%BC%EC%98%B4 --%>
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
					<span class="text-danger">
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message }"/>
						<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
					</span>
				</c:if>
			</div>
		</div>
	</form>
</body>
</html>