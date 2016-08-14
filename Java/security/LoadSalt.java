package security;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class LoadSalt implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Assume its HTTP
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		if (excludeFromFilter(httpReq.getServletPath())) {
			chain.doFilter(request, response);
			return;
		}

		// Check the user session for the salt cache, if none is present we
		// create one
		Cache<String, Boolean> csrfPreventionSaltCache = CacheBuilder.newBuilder().maximumSize(5000)
				.expireAfterWrite(20, TimeUnit.MINUTES).build();
		System.out.println("new cache");

		httpReq.getSession().setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);

		// Generate the salt and store it in the users cache
		String salt = RandomStringUtils.random(20, 0, 0, true, true, null, new SecureRandom());
		System.out.println("load salt: " + salt);
		csrfPreventionSaltCache.put(salt, Boolean.TRUE);

		// Add the salt to the current request so it can be used
		// by the page rendered in this request
		httpReq.setAttribute("csrfPreventionSalt", salt);

		chain.doFilter(request, response);
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
			
		return path.matches(".*\\.(css|jpg|png|gif|js|svg|ttf|woff|woff2)$");
	}
}