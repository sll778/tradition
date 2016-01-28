package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FixCustom;
import bean.Message;

public class approveUpdateServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		Long fixId = Long.parseLong(request.getParameter("fixId"));
		//根据fixId找到UserId
		FixCustom fixCustom = new FixCustom();
		Long userId = fixCustom.getUserIdById(fixId);
		
		//审批
		if(flag.equals("1")){
			//将isPass置为1
			fixCustom.applyPass(fixId);
		}
		//将status置为1
		fixCustom.isApprove(fixId);
		
		//发送通知给会员
		Message message = new Message();
		message.add(userId, fixId);
		response.sendRedirect("approveServlet");
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
