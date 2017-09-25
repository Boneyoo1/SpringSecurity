

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class AccountSecurityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (request.getInputStream() != null) {

			System.out.println(request.getInputStream().read());
			System.out.println(request.getAttribute("X-Auth-form-username"));
			System.out.println(request.getServletContext().getAttribute("X-Auth-form-password"));

			System.out.println("bb".hashCode());

		}
		Optional<String> username = Optional.ofNullable(request.getParameter("X-Auth-form-username"));
		Optional<String> password = Optional.ofNullable(request.getParameter("j_form-password ="));
		Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-Token"));
		System.out.println(username + "@@@");
		System.out.println(password + "@@@");
		chain.doFilter(request, response);
	}

}
