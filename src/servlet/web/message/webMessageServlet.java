package servlet.web.message;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Message;
import bean.User;

public class webMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Message message = new Message();
		
		HttpSession session = request.getSession();
		String logname = (String)session.getAttribute("logname");
		User user = new User();
		Long userId = user.getIdByLognameByCache(logname);
		
		List<Message> messages = message.getAllMessageByCache(userId);
		request.setAttribute("messages", messages);
		
		request.getRequestDispatcher("WEB-INF/template/web/message/webMessage.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
