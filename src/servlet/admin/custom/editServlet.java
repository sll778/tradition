package servlet.admin.custom;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Custom;
import bean.CustomKind;

public class editServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		//可跳转到编辑界面
		Long customId = Long.parseLong(request.getParameter("customId"));
		Custom custom = new Custom();
		custom = custom.viewByCache(customId);
		CustomKind customKind = new CustomKind();
		List<CustomKind> customKinds = customKind.getAllKindByCache();
		
		request.setAttribute("customKinds", customKinds);
		request.setAttribute("custom", custom);
		request.getRequestDispatcher("WEB-INF/template/admin/custom/edit.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
