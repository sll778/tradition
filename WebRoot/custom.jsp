<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Custom custom = null;
ArrayList<Custom> customs = (ArrayList<Custom>)request.getAttribute("customs");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML  4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'custom.jsp' starting page</title>
    
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
    <div style="background-color:#cf9e9e;width:100%;height:35px"><a href="main.jsp">习俗网后台系统</a>&nbsp;<a href="customServlet">传统习俗</a>&nbsp;<a href="userManageServlet">会员管理</a></div>
    <a href="beforeAddServlet">增加</a>
    <table width="100%">
    <tr><td>习俗</td><td>内容</td><td>类别</td><td>操作</td></tr>
    <%
    for(int i=0;i<10&&i<customs.size();i++){
    	custom = customs.get(i);
    	Long customId = custom.getKindId();
    	CustomKind customKind = new CustomKind();
    	customKind = customKind.getById(customId);
     %>
    <tr><td><%= custom.getName() %></td><td><%= custom.getContent() %></td><td><%= customKind.getName() %></td><td><a href="deleteServlet?customId=<%=custom.getId() %>">删除</a>&nbsp;<a href="editServlet?customId=<%=custom.getId() %>">编辑</a></td></tr>
    <%
    }
     %>
    </table>
  </body>
</html>
