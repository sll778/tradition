package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author sll
 *
 */
public class loginFilter implements	Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		HttpSession session = request.getSession();
		String logname = (String)session.getAttribute("logname");
		if (logname!=null) {
			//已经登录，则放行
			arg2.doFilter(request, response);//放行
		} else {
			//如果未登录，则提醒请先登录（跳转到登录界面）
			response.sendRedirect("toWebLoginServlet");
		}
			
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
