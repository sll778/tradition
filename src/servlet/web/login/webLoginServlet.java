package servlet.web.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;

public class webLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String logname = request.getParameter("logname");
		String password = request.getParameter("password");
		
		//根据用户名，找到密码看是否匹配
		User user = new User();
		Boolean isLoginSuccess = user.login(logname, password,1);
		//如果匹配，则加入session
		if(isLoginSuccess){
			HttpSession session = request.getSession();
			session.setAttribute("logname", logname);
			request.getRequestDispatcher("webCustomServlet").forward(request, response);
		}else{
			//密码错误
			response.sendRedirect("WEB-INF/template/web/webLogin.jsp?error=1");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
