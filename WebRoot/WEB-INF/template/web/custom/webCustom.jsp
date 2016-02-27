<%@ page language="java" import="java.util.*,bean.*,javax.servlet.http.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Custom custom = null;
ArrayList<Custom> customs = (ArrayList<Custom>)request.getAttribute("customs");
String logname = (String)session.getAttribute("logname");
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
  
  
  <div align="right">
	  <form action="webSearchServlet" method="post">
	  	<input type="text" name="keyword"/>
	  	<button type="submit">搜索</button>
	  </form>
  </div>
  
  
  
  <table width="100%">
    <tr><td>习俗</td><td>内容</td><td>类别</td><td>操作</td></tr>
    <%
    for(int i=0;i<10&&i<customs.size();i++){
    	custom = customs.get(i);
    	//得到种类的编号
    	Long customId = custom.getKindId();
    	CustomKind customKind = new CustomKind();
    	//得到种类的名称
    	customKind = customKind.getByIdByCache(customId);
     %>
    <tr><td><%= custom.getName() %></td><td><%= custom.getContent() %></td><td><%=customKind.getName() %></td><td><a href="webViewServlet?customId=<%=custom.getId() %>">查看</a></td></tr>
    <%
    }
    
    if(customs.size()==0){
    %>
    	<tr><td>未搜索到相关数据</td></tr>
    <% 
    	}
    %>
    </table>
  </body>
</html>
