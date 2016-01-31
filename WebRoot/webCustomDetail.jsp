<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Custom custom = (Custom)request.getAttribute("custom");
String logname = (String)session.getAttribute("logname");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'webCustomDetail.jsp' starting page</title>
    
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
  	欢迎来到习俗网，<%= logname %>~<a href="logoutServlet">退出登录</a>
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
  <div><p>习俗名称：<%=custom.getName() %> </p></div>
  <div><p>习俗内容：<%=custom.getContent() %></p></div>
  <div><a href="webApplyServlet?customId=<%=custom.getId() %>">申请完善</a></div>
  </body>
</html>
