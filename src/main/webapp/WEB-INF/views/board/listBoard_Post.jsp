<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%>

<layoutTag:layout>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style type="text/css">
	ul {
		list-style: none;
	}
	h2{
		text-align:center;
	}
	#word{
		padding-botton: 50px;
	}
	.w3-image{
		height:200px;
	}
	#trth{
		background-color: black;
		color: white;
	}
	form { 
        margin: 0 auto; 
        width:250px;
    }
	@using (Html.BeginForm("Index", "Join", FormMethod.Post, new { @style = "margin: 0 auto; width: 250px" }))

/*
body {margin:0;padding:0;background-color:#fff;}
hr {display:none;clear:both;}
img,fieldset {border:0 none;}
h1,h2,h3,h4,h5,dl,dt,dd,ul,li,ol,th,td,p,blockquote,form,fieldset,legend {margin:0;padding:0;}
table {border-collapse:collapse;border:0 none;}
form, p {margin: 0px;}
button {margin:0;padding:0;border:0 none;background:transparent;*overflow:visible;cursor:pointer;}
button::-moz-focus-inner {padding:0;border:0;}
a:link {color:#3366cc;text-decoration:none;}
a:visited {color:#3366cc;text-decoration:none;}
a:active {color:#3366cc;text-decoration:none;}
a:hover {color:#2850a2;text-decoration:none;}
body,table,th,td,input,select,textarea,div {
font-family: "굴림","verdana";
font-size: 12px;
color: #333;
font-style: normal;
line-height:18px;
margin:0;
word-spacing:-1pt;
word-break:break-all;
} */

</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    var board_kinds = ${boardKinds};
   $(function() {
	   //레이어
	   var login_mem_no = ${login_mem_no};
		var divContainer = $("<div id='popup_layer' class='container'/>").css({
			'position':'absolute'
			, 'top':100
			, 'left':100
			, 'z-index':1
			, 'visibility':'hidden'
		});
		//버튼 그룹
		var divBtnGroup = $("<div id='btnGroup' class='btn-group-vertical'/>");
		//쪽지보내기 버튼
		var btnMsg = $("<button type='button' class='btn btn-default'/>").text("쪽지보내기");
		// 마이페이지 버튼
		var btnMypage = $("<button type='button' class='btn btn-default'/>").text("마이페이지");
		$(btnMsg).click(function(){
// 			window.open("/member/sendMessage.do", "_blank", "width=400, height=300, menubar=no, toolbar=no, status=no").focus();
			jQuery.noConflict();
			$("#modalMessage .modal-content")
				.load("/member/sendMessage.do?mem_no=" + $(this).parent().attr('mem_no') 
					+ "&mem_nickname=" + $(this).parent().attr('mem_nickname'), function(){
				$("#modalMessage").modal();
			});
		});
		$(btnMypage).click(function(){
			location.href = "/member/myPage.do?mem_no=" + $(this).parent().attr('mem_no');
		});
		
		$(divBtnGroup).append(btnMsg, btnMypage);
		$(divContainer).append(divBtnGroup);
		$('body').append(divContainer);
		$("#btnInsert").click(function() {
			location.href = "/board/insertBoard_Post.do?board_kinds=" + board_kinds;
		});
		//https://chobokkiri.tistory.com/67
		//화면 내 특정영역(닉네임)을 제외한 부분 클릭 시 레이어 숨김
		$('html').click(function(e){
			if(!$(e.target).hasClass('nickname')){
				$("#popup_layer").css({
					'visibility':'hidden'
				});
				$("#btnGroup").attr('mem_no', '');
			}
		});
		//https://zzznara2.tistory.com/621
		//닉네임 클릭 시 레이어 표시
		$(".nickname").click(function(e){
// 			var sWidth = window.innerWidth;
// 			var sHeight = window.innerHeight;
			if (login_mem_no == $(this).attr('mem_no')){
				return;
			}
			$("#btnGroup").attr('mem_no', $(this).attr('mem_no'));
			$("#btnGroup").attr('mem_nickname', $(this).text());
			var oWidth = $("#popup_layer").width();
			var oHeight = $("#popup_layer").height();
			
			var divLeft = e.pageX + 10;
			var divTop = e.pageY;
		
		
			$("#popup_layer").css({
			    "top":divTop
			    , "left":divLeft
			    , "position":"absolute"
				, 'visibility':'visible'
			});
		});
   });

   $(function() {
      $("#btnSelect").click(function() {
         var sel1 = document.getElementById("sel1").value;
         var sel2 = document.getElementById("sel2").value;
         var keyword = board_kinds + "@" + sel1 + "@" + sel2;
         location.href = "/board/listBoard_Post.do?str=" + encodeURI(keyword);
      });
   });

   // 이전페이지
   function moveLeft(curPage){
      if(curPage < 1){
         alert("페이지가 없습니다");
      }else{
         movePage(curPage);
      }
   }

	// 다음페이지
   function moveRight(curPage){
      var endChk = $('input[name=totalPage]').val();
      if(curPage > endChk){
         alert("페이지가 없습니다");
      }else{
         movePage(curPage);
      }            
   }   
   function movePage(i){
      var numberChk = i;
      var totalPage = $("#totalPage").val();
      var totalRecord = $('input[name=totalRecord]').val();
      var totalPage = $('input[name=totalPage]').val();
      var sel1 = document.getElementById("sel1").value;
//       var sel2 = document.getElementById("sel2").value;
      var keyword = board_kinds + "@" + sel1 + "@" + '${sel2}' + "@" + numberChk;
      location.href = "/board/listBoard_Post.do?str=" + encodeURI(keyword);
//      var totalPage = document.getElementById("totalPage").value;
//      var tdTT = totalPage.innerText | totalPage.textContent;
   }   
</script>
</head>
<body>
	<!-- <h2>${boardKinds_str }</h2> -->
	<header class="w3-display-container w3-content w3-wide" style="max-width:1600px;">
  		<img class="w3-image" src="/img/board.jpg" width="100%">
  		<div class="w3-display-middle w3-margin-top w3-center">
    		<h1 class="w3-xxlarge">
    		<span class="w3-hide-small w3-text-light-black" id="word">${boardKinds_str }</span></h1>
  		</div>
	</header>
	<br>
	
	

	<div class="container">
<!-- 		<form id="form" action="/board/listBoard_Info.do"> -->
			<div class="row">
				<div class="col-sm-2">
					<label for="sel1"></label> 
					<select id="sel1" class="form-control" style="width: 100%">
						<option value="1">전체</option>
						<option value="2">제목</option>
						<option value="3">내용</option>
					</select>
				</div>
		
				<div class="col-sm-8">
					<input type="text" size="20" id="sel2" class="form-control" style="width: 100%">
				</div>
				
				<div class="col-sm-2">	
					<button class="btn btn-default" id="btnSelect" type="submit">
						<i class="glyphicon glyphicon-search"></i> 검색
					</button>
				</div>
			</div>
<!-- 		</form> -->
	
		<table class="table table-hover">
			<tr id="trth">
				<th>글번호</th>
				<th>말머리</th>
				<th width="200">글제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
			<c:forEach var="v" items="${list}">
				<tr>
					<td>${v.board_no}</td>
					<td>${v.head_tag_name}</td>
					<td>
						<a href="detailBoard_Post.do?board_no=${v.board_no}">${v.board_title}</a>
						<c:if test="${v.comment_cnt > 0 }">
							<a style="color:blue;" href="detailBoard_Post.do?board_no=${v.board_no}#commentList">[${v.comment_cnt }]</a>
						</c:if>
					</td>
					<td><img src="${v.mem_img }" style="width:20px; height:20px;"><a class="nickname" mem_no="${v.mem_no }">${v.mem_nickname}</a></td>
					<td>${v.board_date}</td>
					<td>${v.board_hit}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<input type="hidden" name="totalPage" value=${totalPage}>
	<input type="hidden" name="totalRecord" value=${totalRecord}>
	<input type="hidden" name="curPage" value=&{curPage}>
	<input type="hidden" name="end" value=${end}>
    
	<div style='text-align: center'>
		<c:if test="${totalPage != null}">
			<div style='display: inline-block'>
				<ul class="pagination">
					<!--맨 첫페이지로 이동기능-->
					<li list-style-type:none-style><a href="#" onclick='moveLeft(1)'> <span> << </span></a> 
					
					<!--이전페이지 기능--> 
					<a onclick="moveLeft(${curPage-1})"> <span> < </span></a> 
					
					<!--페이지 선택--> 
					<c:forEach var="i" begin="${start}" end="${end}" step="1">
						<a onclick="movePage(${i})">${i}</a>
					</c:forEach> 
					
					<!--다음페이지 기능--> 
					<a href="#" onclick='moveRight(${curPage+1})'> <span> > </span></a> 
					<!--맨 마지막 페이지로 이동기능--> <a href="#" onclick='moveRight(${totalPage})'> <span> >> </span>
					</a></li>
				</ul>
			</div>
		</c:if>
		<br>
		<button type="button" class="btn btn-default btn-lg" id="btnInsert">
			<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 글작성
		</button>
	</div>
	<!-- https://badstorage.tistory.com/18 -->
	<div class="modal fade" id="modalMessage" tabindex="-1" role="dialog"
		aria-labelledby="historyModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl" role="document">
			<div class="modal-content"></div>
		</div>
	</div>
	
	<%@ include file="/WEB-INF/tags/footer.jsp" %>
</body>
</html>

</layoutTag:layout>