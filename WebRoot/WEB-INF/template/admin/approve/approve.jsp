<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List<FixCustom> fixCustoms = (ArrayList<FixCustom>)request.getAttribute("fixCustoms");

String logname = (String)session.getAttribute("adminLogname");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'approve.jsp' starting page</title>
    
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
    
    <table width="100%">
    	<tr><td>习俗编号</td><td>申请修改内容</td><td>是否通过审批</td><td>申请人</td><td>审批状态</td><td>操作</td></tr>
    	<%
    	for(int i=0;i<fixCustoms.size();i++){
    		FixCustom fixCustom = fixCustoms.get(i);
    		//根据userId找到user信息
			User user = new User();
			Long id = fixCustom.getUserId();
			String name = user.getLognameById(id);
    	 %>
    	<tr><td><%= fixCustom.getCustomId() %></td><td><%= fixCustom.getFixContent() %></td>
    	<%if(fixCustom.isPass()){ %>
    	<td>已通过</td>
    	<%} else { %>
    	<td>未通过</td>
    	<%} 
    	%>
    	<td><%= name %></td>
    	<% if(fixCustom.isStatus()){ %>
    	<td>已审批</td>
    	<%}else{ %>
    	<td>未审批</td>
    	<%} %>
    	<td>
    	<% if(!fixCustom.isStatus()){ %>
    	<a href="toApproveServlet?flag=1&fixId=<%= fixCustom.getId()%>">采纳</a>&nbsp;
    	<a href="toApproveServlet?flag=0&fixId=<%= fixCustom.getId()%>">不采纳</a>&nbsp;
    	<% }%>
    	<a href="editServlet?customId=<%= fixCustom.getCustomId() %>">点击修改</a></td></tr>
    	<%
    	 }
    	 %>
    </table>
  </body>
</html>
