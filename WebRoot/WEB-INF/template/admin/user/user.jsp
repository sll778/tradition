<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ArrayList<User> users = (ArrayList<User>)request.getAttribute("users");

String logname = (String)session.getAttribute("adminLogname");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'user.jsp' starting page</title>
    
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
  	<div style="background-color:#cf9e9e;width:100%;height:35px"><a href="main.jsp">习俗网后台系统</a>&nbsp;
  
  	<c:choose>
	   <c:when test="${logname==null}">   
	   		<a href="toLoginServlet">登录</a>
	   </c:when>
	   
	   <c:otherwise> 
	   		<a href="customServlet">传统习俗</a>&nbsp;<a href="userManageServlet">会员管理</a>&nbsp;<a href="approveServlet">申请审批</a>&nbsp;欢迎您，<%=logname %><a href="adminLogoutServlet">退出</a>
	   </c:otherwise>
	</c:choose>
	</div>
	<!-- 导航栏 -->
    
  	<div>
  	<table width="100%" >
  		<tr><td>登录名</td><td>姓名</td><td>邮箱</td><td>操作</td><tr>
	<%
  		for(int i=0;i<users.size();i++){
  			User user = users.get(i);
  	 %>
  	 	<tr><td><%= user.getLogname() %></td><td><%= user.getName() %></td><td><%= user.getEmail() %></td><td><a href="resetPassServlet?userId=<%=user.getId() %>">重置密码</a></td></tr>
  	 <%
  	 }
  	  %>
  	</table>
  	</div>
  </body>
</html>
