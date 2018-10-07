
package Bean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean 
{
    //return current session 
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
    }

    //return current request 
    public static HttpServletRequest getRequest() {
	return (HttpServletRequest) FacesContext.getCurrentInstance()
		.getExternalContext().getRequest();
    }

    //return token created
    public static String getToken(){
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);
	return session.getAttribute("token").toString();
    }
    
    //return current username
    public static String getUserName() {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(false);
	return session.getAttribute("username").toString();
    }

    //return current userid
    public static String getUserId() {
	HttpSession session = getSession();
            if (session != null)
		return (String) session.getAttribute("userid");
            else
		return null;
           
    }   
        
    //give the user type- customer/admin
    public static String getUserType(){
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
		.getExternalContext().getSession(true);
	return session.getAttribute("type").toString();
    }
}
