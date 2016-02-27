package servlet.admin.custom;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.CustomKind;

public class beforeAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//得到所有的类别
		CustomKind customKind = new CustomKind();
		List<CustomKind> customKinds = customKind.getAllKindByCache();
		
		request.setAttribute("customKinds", customKinds);
		request.getRequestDispatcher("WEB-INF/template/admin/custom/add.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
