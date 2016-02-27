package servlet.admin.custom;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Custom;

public class editUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		Long customKind = Long.parseLong(request.getParameter("customKind"));
		
		//编辑信息
		Custom custom = new Custom();
		custom.update(name, content, customKind, id);
		response.sendRedirect("customServlet");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
