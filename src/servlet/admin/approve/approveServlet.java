package servlet.admin.approve;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FixCustom;

public class approveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//得到审批信息，展示
		FixCustom fixCustom = new FixCustom();
		List<FixCustom> fixCustoms = fixCustom.getAllByCache();
		
		request.setAttribute("fixCustoms", fixCustoms);
		request.getRequestDispatcher("WEB-INF/template/admin/approve/approve.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
