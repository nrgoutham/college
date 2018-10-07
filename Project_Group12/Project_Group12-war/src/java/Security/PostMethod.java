
package Security;

import Security.RequestFilter;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PostMethod 
{
    static String token;

    public static String getToken() {
        return token;
    }
    
    public static void postRedirect(String url){
       
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectUrl = url;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        try {
                ec.redirect(redirectUrl);
            }catch (IOException e) {
                e.printStackTrace();
                FacesMessage message = new FacesMessage(e.getMessage());
                context.addMessage(null, message);
            }   
    }
    
    public static void generateToken(){
       PostMethod.token= (int) (Math.random())+"";
    }
    
    public static String getHidden(String filed){
	   String value = FacesContext.getCurrentInstance().
		getExternalContext().getRequestParameterMap().get(filed);
          return value;	
    }
    
    public static String FetchSessionAttributes(String attribut){
         HttpSession session = RequestFilter.getSession(); 
         String atribute = (String) session.getAttribute("token");
         return atribute;     
    }
    
    public  String getHiddenFromSession(){
          return null;	
    }
    
    
}
