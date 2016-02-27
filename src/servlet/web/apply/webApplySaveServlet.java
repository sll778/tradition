package servlet.web.apply;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.FixCustom;
import bean.User;

public class webApplySaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String fixContent = request.getParameter("fixContent");
		Long customId = Long.parseLong(request.getParameter("customId"));
		
		//保存申请修改的信息
		FixCustom fixCustom = new FixCustom();
		HttpSession session = request.getSession();
		String logname = (String)session.getAttribute("logname");
		//根据logname查询id
		User user = new User();
		Long userId = user.getIdByLognameByCache(logname);
		
		fixCustom.addFixInfo(fixContent, customId, userId);
		
		response.sendRedirect("webCustomServlet");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
