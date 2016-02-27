<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String logname = (String)session.getAttribute("logname");
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
    <!-- 导航栏 -->
  <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;
	  <c:if test="${logname!=null}">
	  	<a href="webMessageServlet">我的消息</a>&nbsp;
	  </c:if>
	  
	  <c:choose>
	   <c:when test="${logname!=null}">   
	   		欢迎来到习俗网，<%= logname %>~<a href="logoutServlet">退出登录</a> 
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
    
    
    <form action="webRegisterServlet" onsubmit="return validateForm()" method="post">
    	*登录名<input type="text" name="logname" id="logname"/>
    	*姓名<input type="text" name="name" id="name"/>
    	*密码<input type="password" name="password" id="password"/>
    	*重复密码<input type="password" name="repassword" id="repassword"/>
    	*邮箱<input type="text" name="email" id="email"/>
    	<input type="submit" value="注册">
    </form>
    
    <script>
function validate(value,text){
	if(value==null||value==""){
		alert(text);
		return false
	}else {
		return true
	}
}
function validateEmail(value){
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
	if(!pattern.test(value)){
		alert("请输入正确的邮箱");
		return false
	}else{
		return true
	}

}

function validateForm(){
	var logname = document.getElementById("logname").value;
	var name = document.getElementById("name").value;
	var password = document.getElementById("password").value;
	var repassword = document.getElementById("repassword").value;
	var email = document.getElementById("email").value;
	if(validate(logname,"登录名不能为空！")==false){
		return false
	}else if(validate(name,"姓名不能为空")==false){
		return false
	}else if(validate(password,"密码不能为空")==false){
		return false
	}else if(validate(repassword,"重复密码不能为空")==false){
		return false
	}else if(validate(email,"邮箱不能为空")==false){
		return false
	}else if(validateEmail(email)==false) {
		return false
	}
	
	
}


</script>
  </body>
</html>
