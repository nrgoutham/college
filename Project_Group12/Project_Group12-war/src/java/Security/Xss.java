/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.owasp.validator.html.*;

public class Xss extends HttpServletRequestWrapper {
	HttpServletRequest orgRequest = null;

	public Xss(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}
       
         public static String clean(String s) {
           String cleanText = null;
           try {
               Policy policy = Policy.getInstance("antisamy-slashdot-1.4.4.xml");
               AntiSamy as = new AntiSamy();
               CleanResults cr = as.scan(s, policy);
               cleanText = cr.getCleanHTML();
           } catch (PolicyException pe) {
               cleanText = "PolicyException";
           } catch (ScanException se) {
               cleanText = "ScanException";
           } catch (Exception e) {
               cleanText = "Exception";
           }
           return cleanText;
       }
         public static String cleanstring(String s){
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]{5,}");
        Matcher m = p.matcher(s);
        boolean b = m.find();
        if (b == false)
        {
        return s;
        }
//        if(s.contains("<"))
//       {
//       s = s.replaceAll("<", "0");
//           s = s.replaceAll(">", "0");
//       }
        else{
            FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Invalid Input,Enter only alphabets and numbers",
							"Please enter valid input"));
       return "0";
        }
    } 
}