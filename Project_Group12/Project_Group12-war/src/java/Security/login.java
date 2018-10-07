/*
 *
 * Arjun Ramesh                 16044215 
 * Dakshina Murthy              16064879
 * Gowtham NR                   16029143
 * Ruwaid Khateeb               16055314
 * Vivek Padmanaban chinnaraj   15113582
 *
 */
package Security;
import Bean.SessionBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import ManagedBeans.Bean_UserLocal;

@ManagedBean
@SessionScoped
public class login implements Serializable {
    String sessionToken;
    String passedToken;
    //making ejb newuserbean object
    @EJB
    private Bean_UserLocal loginbean;
	
    //Creates a new instance of login
    public login() 
    {
    }
    
    //required attributes
    private String pwd;//passwword
    private String msg;//message
    private String user;//user
    private String token;//token
        
    //getter for token
    public String getToken() {
        return token;
    }
    
    //gettter for username
	public String getUser() {
		return user;
	}
 
    //setter for username
	public void setUser(String user) {
		this.user = user;
	}
        
    //getter for password
	public String getPwd() {
            return pwd;
	}

    //setter for password
	public void setPwd(String pwd) {
	this.pwd = pwd;
	}

    //gettter for message
	public String getMsg() {
		return msg;
	}

    //setter for message
	public void setMsg(String msg) {
		this.msg = msg;
	}
    //return userID by passing username and password
    public long userID()
   {
        long id = loginbean.getUserID(user, pwd);
        return id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getPassedToken() {
        return passedToken;
    }

    public void setPassedToken(String passedToken) {
        this.passedToken = passedToken;
    }
        
	//validate login
	public String validateUsernamePassword() 
        {
               
              
                //return true or false by validating username and password 
		boolean valid = loginbean.validate(user, pwd);
                //return true or false on wether the user has the correct rights
		boolean rights = loginbean.checkRights(user, pwd);
		if (valid) {
                 
                HttpSession session = SessionBean.getSession();
                loginbean.setUserName(user);
		if(rights){
                          PostMethod.generateToken();//generets a token 
                          this.token=PostMethod.getToken();//gets the generated token
                          session.setAttribute("username", user);
                          session.setAttribute("type","admin");//assign the user type to the session as needs to be checked ,no user should have access to admin panel
                          session.setAttribute("token",PostMethod.getToken());
                          session.setMaxInactiveInterval(1800);//Check for 30 Seconds
						  return "landing";//redirect to correct user page"admin panel
                      }
                else
                {
                          PostMethod.generateToken();//generate token
                          this.token=PostMethod.getToken(); //get the generated token
                          session.setAttribute("username", user);
                          session.setAttribute("type","user");//assign the usertype
                          session.setAttribute("token",PostMethod.getToken());
                          session.setMaxInactiveInterval(1800);
                     
                          return "landing";//redirect to normal user start page
                }
			
                	
		}
               else {//message if validate failed
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Password",
							"Please enter correct username and Password"));
			return "login";
		}
             
	}
        
	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "login";
	}
}
