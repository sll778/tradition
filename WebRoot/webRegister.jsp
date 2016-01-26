<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'webRegister.jsp' starting page</title>
    
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
    <div><a href="webCustomServlet">传统习俗</a>&nbsp;<a href="webLogin.jsp">登录</a>&nbsp;<a href="webRegister.jsp">注册</a></div> 
    <form action="webRegisterServlet" method="post">
    	登录名<input type="text" name="logname" />
    	姓名<input type="text" name="name"/>
    	密码<input type="text" name="password"/>
    	邮箱<input type="text" name="email"/>
    	<input type="submit" value="注册">
    </form>
  </body>
</html>
