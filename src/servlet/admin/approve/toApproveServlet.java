package servlet.admin.approve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class toApproveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String flag = request.getParameter("flag");
		String fixId = request.getParameter("fixId");
		
		request.setAttribute("flag", flag);
		request.setAttribute("fixId", fixId);
		
		//跳转到填写审批原因界面
		request.getRequestDispatcher("WEB-INF/template/admin/approve/approveReason.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
