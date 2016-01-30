<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String customId = request.getAttribute("customId").toString();

String logname = (String)session.getAttribute("logname");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'webApplyDetail.jsp' starting page</title>
    
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
  <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;
  
  <%if(logname!=null){ %>
  <a href="webMessageServlet">我的消息</a>&nbsp;
  <%} %>
  
  <%
  	if(logname!=null){
  	%>
  	欢迎来到习俗网，<%= logname %>~
  	<% 
  	} else {
   %>
  <a href="webLogin.jsp">登录</a>&nbsp;
  <%
  	}
   %>
   <%if(logname==null){ %>
  <a href="webRegister.jsp">注册</a></div> 
  <%} %>
  <form action="webApplySaveServlet" method="post">
  	<input type="hidden" value=<%=customId%> name="customId"/>
    申请修改的信息：<input type="text" name="fixContent" />
    <input type="submit" value="确定" />
  </form>
  </body>
</html>
