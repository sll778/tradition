<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

ArrayList<CustomKind> customKinds = (ArrayList<CustomKind>)request.getAttribute("customKinds");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
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
    <form action="addServlet" method="post">  
    	习俗名称：<input type="text" name="name"/>
    	习俗类别：<select name="customKind">
		    	<%
		    	for(int i=0;i<customKinds.size();i++){
		    		CustomKind customKind = customKinds.get(i);
		    	 %>
		    	 	<option value=<%=customKind.getId() %>><%=customKind.getName() %></option>
		    	<%
		    	}
		    	 %>
    			</select>
    	习俗内容：<input type="text" name="content"/>
    	<input type="submit" name="submit">
    </form>
  </body>
</html>
