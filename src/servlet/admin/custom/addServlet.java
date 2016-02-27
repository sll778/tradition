package servlet.admin.custom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Custom;

public class addServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//保存增加信息
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		Long customKind = Long.parseLong(request.getParameter("customKind"));
		
		//创建对象
		Custom custom = new Custom();
		//调用add方法（增加习俗记录）
		custom.add(name, content, customKind);
		response.sendRedirect("customServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
