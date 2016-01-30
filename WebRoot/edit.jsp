<%@ page language="java" import="java.util.*,bean.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Custom custom = (Custom)request.getAttribute("custom");
ArrayList<CustomKind> customKinds = (ArrayList<CustomKind>)request.getAttribute("customKinds");


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'edit.jsp' starting page</title>
    
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
    <div style="background-color:#cf9e9e;width:100%;height:35px"><a href="main.jsp">习俗网后台系统</a>&nbsp;<a href="customServlet">传统习俗</a>&nbsp;<a href="userManageServlet">会员管理</a>&nbsp;<a href="approveServlet">申请审批</a></div>
	  <form action="editUpdateServlet" method="post">  
	  				 <input type="hidden" name="id" value="<%= custom.getId() %>">
	    	<div>习俗名称：<input type="text" name="name" value="<%=custom.getName() %>"/></div>
	    	<div>习俗类别：<select name="customKind" value="<%=custom.getKindId() %>">
			    	<%
			    	for(int i=0;i<customKinds.size();i++){
			    		CustomKind customKind = customKinds.get(i);
			    	 %>
			    	 	<option value=<%=customKind.getId() %>><%=customKind.getName() %></option>
			    	<%
			    	}
			    	 %>
	    			</select>
	    	</div>
	    	<div>
	    	习俗内容：<textarea rows="3" cols="20" name="content" value="<%=custom.getContent() %>"/></textarea>
	    	</div>
	    	<input type="submit" name="submit">
	    </form>
  </body>
</html>
