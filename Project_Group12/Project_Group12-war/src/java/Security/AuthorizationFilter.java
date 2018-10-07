
package Security;
import java.io.IOException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter
{
     public AuthorizationFilter() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest requestt = (HttpServletRequest) request;
			HttpServletResponse responsee = (HttpServletResponse) response;
			HttpSession sessionn = requestt.getSession(false);

			String requestURI = requestt.getRequestURI();
			if (requestURI.indexOf("/login.xhtml") >= 0
					|| (sessionn != null && sessionn.getAttribute("username") != null)
					|| requestURI.indexOf("/public/") >= 0
					|| requestURI.contains("javax.faces.resource"))
				chain.doFilter(request, response);
			else
				responsee.sendRedirect(requestt.getContextPath() + "/faces/login.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
        @Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	@Override
	public void destroy() {

	}
}
