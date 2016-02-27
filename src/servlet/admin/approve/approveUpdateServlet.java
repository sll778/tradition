package servlet.admin.approve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FixCustom;
import bean.Message;

public class approveUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		String reason = request.getParameter("reason");
		Long fixId = Long.parseLong(request.getParameter("fixId"));
		//根据fixId找到UserId
		FixCustom fixCustom = new FixCustom();
		Long userId = fixCustom.getUserIdById(fixId);
		
		//审批是否通过信息的更改
		if(flag.equals("1")){
			//先跳转到填写信息界面，再修改isPass
			
			//将isPass置为1
			fixCustom.applyPass(fixId);
		}
		//将status置为1，且保存审批意见
		fixCustom.isApprove(fixId);
		
		//发送通知给会员，保存审批意见
		Message message = new Message();
		message.add(userId, fixId, reason);
		response.sendRedirect("approveServlet");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
