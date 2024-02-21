<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%//서블릿과 같은 환경-jsp 
    
      //서버사이드렌더링
      //검색 엔진 최적화 + cors에 걸리지 않음
      //csl은 검색에 취약하다.
      //jstl JSP 페이지에서 자주 사용되는 일련의 태그들의 모음
    	//스크립트릿
    	List<String> names = List.of("조성민","조성이","조성삼");
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>hello</h1>
	<ul>
		<%
			//자바코드와 html코드와 혼용가능
			for(String name : names) {
		%>
			<li><%=name%></li>
		<%
			
			}
		%>
	</ul>
</body>
</html>