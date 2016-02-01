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

public class AuthorityFilter implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		//判断权限
		HttpSession session = request.getSession();
		String adminLogname = (String)session.getAttribute("adminLogname");
		if(adminLogname == null){
			response.sendError(401,"对不起，您没有访问的权限~^_^"); 
		}else{
			chain.doFilter(request, response);//放行
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
