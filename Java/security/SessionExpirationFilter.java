package security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionExpirationFilter implements Filter {

	public void init(FilterConfig config) {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hReq = (HttpServletRequest) req;
	
		HttpSession session = hReq.getSession(false);
		if(session==null || !hReq.isRequestedSessionIdValid() ) {
			//comes here when session is invalid.
			req.getRequestDispatcher("index.jsp").forward(hReq, resp);
			return;
		}
		
		chain.doFilter(req, resp);
	}

	public void destroy() {
	}
}
