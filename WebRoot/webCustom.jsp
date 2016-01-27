<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Custom custom = null;
ArrayList<Custom> customs = (ArrayList<Custom>)request.getAttribute("customs");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'webCustom.jsp' starting page</title>
    
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
  <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;<a href="webLogin.jsp">登录</a>&nbsp;<a href="webRegister.jsp">注册</a></div> 
  <div align="right">
	  <form action="searchServlet" method="post">
	  	<input type="text" name="keyword"/>
	  	<button type="submit">搜索</button>
	  </form>
  </div>
  <table width="100%">
    <tr><td>习俗</td><td>内容</td><td>类别</td><td>操作</td></tr>
    <%
    for(int i=0;i<10&&i<customs.size();i++){
    	custom = customs.get(i);
    	Long customId = custom.getKindId();
    	CustomKind customKind = new CustomKind();
    	customKind = customKind.getById(customId);
     %>
    <tr><td><%= custom.getName() %></td><td><%= custom.getContent() %></td><td><%=customKind.getName() %></td><td><a href="webViewServlet?customId=<%=custom.getId() %>">查看</a></td></tr>
    <%
    }
     %>
    </table>
  </body>
</html>
