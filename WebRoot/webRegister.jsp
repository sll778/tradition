<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    <div><a href="">习俗网</a>&nbsp;<a href="webCustomServlet">传统习俗</a>&nbsp;
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
