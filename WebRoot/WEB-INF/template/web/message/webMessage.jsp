<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ArrayList<Message> messages = (ArrayList<Message>)request.getAttribute("messages");
String logname = (String)session.getAttribute("logname");
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
  
  
  <table width="100%">
  <%for(int i=0;i<messages.size();i++){ 
	  	Message message = messages.get(i);
	  	//找到fixId，
	  	Long fixId = message.getFixId();
	  	String reason = message.getReason();
	  	FixCustom fixCustom = new FixCustom();
	  	fixCustom = fixCustom.getByIdByCache(fixId);%>
	  	<tr><td><%=i+1%></td><td>你好，您的意见“<%=fixCustom.getFixContent()%>”
	  	
	  	<c:choose>
		   <c:when test="${fixCustom.isPass()}">   
		   		已通过审批
		   </c:when>
		   <c:otherwise> 
		   		未通过审批
		   </c:otherwise>
		</c:choose>
	  	
	  	，审批意见为“<%=reason%>”</td></tr>
  	<%
  	}
  	 %>
  	 
  	 <c:if test="${messages.size()==0}">
  	 	<p>未收到任何消息</p>
  	 </c:if>
  	 
  </table>
    
  </body>
</html>
