<%--주석gfjgpfjgpoj--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<nav class="navbar navbar-expand-sm bg-light">
   <div class="container">
      <ul class="navbar-nav me-auto">
         <li class="nav-item">
            <a class="nav-link" href="/">홈</a>
         </li>
       </ul>
      
       
     <sec:authorize access="isAuthenticated()">
	       <span class="navbar-text me-3">
	      	 <strong><sec:authentication property="principal.name"/></strong>님 환영합니다 
	       </span>
      </sec:authorize>
      
       <ul class="navbar-nav">
         <%--  <sec:authorize access="isAnonymous"> 익명인경우 로그인을 하지않았으면 로그인, 회원가입이 표시된다. --%>
         <sec:authorize access="isAnonymous">
	         <li class="nav-item">
	            <a class="nav-link" href="/login">로그인</a>
	         </li>
	         <li class="nav-item">
	            <a class="nav-link" href="/register">회원가입</a>
	         </li>
         </sec:authorize>
         <!-- sec:authorize access인증이 완료되었을떄 true 
         		로그인이 되었으면 로그인 창이 보이지 않고 로그아웃창만보이가-->
         <sec:authorize access="isAuthenticated()">
	         <li class="nav-item">
	            <a class="nav-link" href="/user/me">내정보</a>
	          </li>
	          <li>
	            <a class="nav-link" href="/logout" onclick="logout(event)">로그아웃</a>
	         </li>
         </sec:authorize>
      </ul>
      
   </div>
</nav>
<%-- 로그아웃 --%>
<%--
	스프링 시큐리티에서 로그아웃 요청은 POST방식으로요청해야한다.
	스프링 시큐리티에서 post 요청을 보낼때는 CSRF토큰을 같이 보내야한다.
	따라서, 로그아웃 링클를 클릭하면, POST 방식으로 폼이 제출되게 해야한다.
 --%>
<form id="form-logout" method="post" action ="/logout"><sec:csrfInput/></form>
<script>
	function logout(event){
		event.preventDefault();
		document.getElementById("form-logout").submit();
	}
<%--로그아웃 클릭시 로인그인 페이지로 이동하게됨 POST방식으로!!요청해야한다............--%>
</script>