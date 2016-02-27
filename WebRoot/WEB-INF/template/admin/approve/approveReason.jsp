<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String flag = (String)request.getAttribute("flag");
String fixId = (String)request.getAttribute("fixId");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'approveReason.jsp' starting page</title>
    
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
    <form action="approveUpdateServlet" method="post">
    	<input type="hidden" name="flag" value="<%= flag %>"/>
    	<input type="hidden" name="fixId" value="<%= fixId %>" />
    	审批意见：<input type="text" name="reason" />
    	<input type="submit" value="提交"/>
    </form>
  </body>
</html>
