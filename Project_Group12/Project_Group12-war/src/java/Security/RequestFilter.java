
package Security;

import java.io.IOException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



@WebFilter("/RequestFilter")
public class RequestFilter implements Filter {
private static ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();

//Default constructor. 
    public RequestFilter() {
    }

    public static HttpServletRequest getRequest() {
        System.out.println("Fetching Request!");
        return localRequest.get();
    }

    public static HttpSession getSession() {
        System.out.println("Fetching Session!");
        HttpServletRequest request = localRequest.get();
        return (request != null) ? request.getSession() : null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // pass the request along the filter chain
        chain.doFilter(request, response);

        if (request instanceof HttpServletRequest) {
            localRequest.set((HttpServletRequest) request);
        }

        try {
            chain.doFilter(request, response);
        }
        finally {
            localRequest.remove();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("This is Not supported");
    }

    public void destroy(){
    } 
}
