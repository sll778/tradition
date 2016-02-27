<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String logname = (String)session.getAttribute("logname");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
  <!-- 导航栏 -->
  <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;
	  <c:if test="${logname!=null}">
	  	<a href="webMessageServlet">我的消息</a>&nbsp;
	  </c:if>
	  
	  <c:choose>
	   <c:when test="${logname!=null}">   
	   		欢迎来到习俗网，${logname}~<a href="logoutServlet">退出登录</a> 
	   </c:when>
	   
	   <c:otherwise> 
	   		<a href="toWebLoginServlet">登录</a>&nbsp;
	   </c:otherwise>
	  </c:choose>
	  <c:if test="${logname==null}" >
	  	<a href="toWebRegisterServlet">注册</a>
	  </c:if>
  </div> 
  <!-- 导航栏 -->
  
  <div align="center">欢迎来到中华传统习俗网，这里有最全面、最有用的习俗信息，让中华民族的传统在我们的手中代代传承！</div>
  <div align="center"><img src="images/welcome.jpg"></div>
  </body>
</html>
