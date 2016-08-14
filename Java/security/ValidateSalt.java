package security;

import com.google.common.cache.Cache;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class ValidateSalt implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Assume its HTTP
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		if (excludeFromFilter(httpReq.getServletPath())) {
			chain.doFilter(request, response);
			return;
		}

		// Get the salt sent with the request
		String salt = (String) httpReq.getParameter("csrfPreventionSalt");

		// Validate that the salt is in the cache
		Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) httpReq.getSession()
				.getAttribute("csrfPreventionSaltCache");
System.out.println(csrfPreventionSaltCache.asMap());
			System.out.println("validate saltttt: " + salt);
		if (csrfPreventionSaltCache != null && salt != null && csrfPreventionSaltCache.getIfPresent(salt) != null) {
			csrfPreventionSaltCache.invalidate(salt);
			// If the salt is in the cache, we move on
			chain.doFilter(request, response);
		} else {
			// Otherwise we throw an exception aborting the request flow
			System.out.println("Potential CSRF.");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	private boolean excludeFromFilter(String path) {
		if(path == null) return false;
		
		if(!path.matches("/StartServlet|.*\\.(css|jpg|png|gif|js|svg|ttf|woff|woff2)$")) {
			System.out.println("path: " + path);
			System.out.println(path.matches("/StartServlet|.*\\.(css|jpg|png|gif|js|svg|ttf|woff|woff2)$"));
		}
			
		return path.matches("/StartServlet|.*\\.(css|jpg|png|gif|js|svg|ttf|woff|woff2)$");
	}
}