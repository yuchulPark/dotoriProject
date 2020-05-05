<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pt.place_type_name } 정보 게시판</title>
<meta name="viewport" content="width=device-width" initial-scale="1" minimum-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/coding.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=d24b45a0cda3b0e29fc1ea1a3fa5d8f1"></script>
<style type="text/css">
		h1{
			text-align:center;
		}
		.row{
			padding:30px;
		}
		b{
			font-size:15px;
		}
		p{
			color:grey;
			font-size:11px;
		}
	/* Hide the carousel text when the screen is less than 600 pixels wide */
		@media (max-width: 600px) {
		    .carousel-caption {
		      	display: none; 
		    }
		}/*
		.carousel-inner img {
      		width: 100%; 
      		margin: auto;
      		height:200px;
  		}*/
  		.pagination{
  			display:block;
			text-align: center;
  		}
  		.pagination > li > a{
  			float: none;
  			margin-left: -5px;
  		}
  		form { 
        	margin: 0 auto; 
       		width:250px;
    	}    
		@using (Html.BeginForm("Index", "Join", FormMethod.Post, new { @style = "margin: 0 auto; width: 250px" }))
	</style>
</head>
<script type="text/javascript">
$(function(){
	$.ajax({url:"/listPlace_Theme", type:"get", dataType:"json", success:function(result){
		var ul = $('<ul class="pagination pagination-lg"/>');
		
		$.each(result, function(idx, theme){
			var tab = $("<a/>").attr("href", "/listPlace_Info?place_type=" + theme["place_type"]).
				text(theme["place_type_name"]);
			var li = $("<li/>").append(tab);
			$(ul).append(li);
		});
		$("body").prepend(ul);
	}});
	var params;
	//쿼리스트링에서 특정 매개변수 값 반환하는 함수
	function getParamVal(queryStr, paramKey){
		if(queryStr.indexOf("?") >= 0){
			params = queryStr.substr(1).split("&");
			for(var i = 0; i < params.length; i++){
				p = params[i];
				if(p.split("=")[0] == paramKey){
					return p;
				}
			}
		}else{
			return "";
		}
	}
	$("#form").on("submit", function(event){
		event.preventDefault();
		var searchCondition = $("#form").serialize();
		var paramPlacetype = getParamVal(location.search, "place_type");
		if(paramPlacetype != ""){
			paramPlacetype = paramPlacetype + "&";
		}
		if(searchCondition["keyword"] == ""){
			searchCondition = "";
		}
		console.log(searchCondition);
		location.href = "/listPlace_Info?" + paramPlacetype + searchCondition;
	});
});
</script>
<body>
	<!-- 내용  -->
	<h1><strong>${pt.place_type_name }</strong></h1>
	<br>
	<img src="images/main.jpg" width="100%" height="500">
	<br><br><br><br>

	<div class="row">
		<c:forEach var="p" items="${list }">
			<div class="col-md-3">
				<div class="thumbnail">
					<a href="detailPlace_Info?place_no=${p.place_no}">
						<img src="/img/${fn:split(p.place_img, '|')[0]}" width="300" height="300"> 
						<div class="caption">
							<b>${p.place_name}</b>
							<br>
							<p>
								${p.place_detail }
							</p>
						</div>
					</a>
				</div>
			</div>
		</c:forEach>
	</div>
	
	
	<!-- 페이징 처리 -->
	<ul class="pagination pagination-lg">
		<c:if test="${startPage > 1}">
			<li>
				<a href="listPlace_Info?place_type=${place_type }&pageNUM=${startPage-1 }${searchColumn}${keyword}" aria-label="이전">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
		</c:if>
		
		<c:forEach var="i" begin="${startPage }" end="${endPage }">
			<li><a href="listPlace_Info?place_type=${place_type }&pageNUM=${i }${searchColumn}${keyword}">${i }</a></li>
		</c:forEach>
		
		<c:if test="${endPage < totalPage }">
			<li>
				<a href="listPlace_Info?place_type=${place_type }&pageNUM=${endPage+1 }${searchColumn}${keyword}" aria-label="다음">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</c:if>
	</ul>	


	<!-- 검색창 구현 -->
	<form id="form" action="/listPlace_Info" method="post">
		<div class="input-group">
			<div class="form-group">
				<label for="sel"></label>
				<select class="form-control" name="searchColumn" id="sel">
					<option value="place_name">장소명</option>
					<option value="place_detail">내용</option>
				</select>
			</div>
			<input type="text" class="form-control" name="keyword" placeholder="검색어를 입력해주세요.">
			<div class="input-group-btn">
				<button class="btn btn-default" type="submit">
					<i class="glyphicon glyphicon-search"></i>
				</button>
			</div>
		</div>
	</form>
	<br><br>
</body>
</html>