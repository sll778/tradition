<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ArrayList<Message> messages = (ArrayList<Message>)request.getAttribute("messages");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'message.jsp' starting page</title>
    
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
  <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;<a href="webMessageServlet">我的消息</a>&nbsp;
  <%
  	String logname = (String)session.getAttribute("logname");
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
  <table width="100%">
  <%for(int i=0;i<messages.size();i++){ 
  	Message message = messages.get(i);
  	Long fixId = message.getFixId();
  	FixCustom fixCustom = new FixCustom();
  	String content = fixCustom.getContentById(fixId);%>
  	<tr><td><%= i+1 %></td><td>你好，您的意见“<%= content %>”，已被处理</td></tr>
  	<%}
  	 %>
  </table>
    
  </body>
</html>