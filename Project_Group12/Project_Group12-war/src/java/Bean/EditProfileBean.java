/*
 *
 * Arjun Ramesh                 16044215 
 * Dakshina Murthy              16064879
 * Gowtham NR                   16029143
 * Ruwaid Khateeb               16055314
 * Vivek Padmanaban chinnaraj   15113582
 *
 */
package Bean;

import Entity.Usertable;
import Security.Xss;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import ManagedBeans.Bean_UserLocal;
import Security.PostMethod;

@Named(value = "editUserBean")
@SessionScoped
public class EditProfileBean implements Serializable {

    //bean to edit user profile
     @EJB  //EJB annotation for injection
    private Bean_UserLocal edituserbean;
    

    //Default constructor with Id value as 1
    public EditProfileBean() {
        id = 1;
    }
    
    //required attributes
    private long id; //user id
    private String username; //username
    private String address; // address
    private String message; //message
    private Usertable user; //user
    String actionMessage; //action message for facelets
    
    //getter method for id 
    public long getId() {
        return id;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    //setter method for id
    public void setId(long id) {
        this.id = id;
    }

    //getter to get username
    public String getUsername() {
        return username;
    }

    //setter to set username
    public void setUsername(String username) {
        this.username = username;
    }

    //getter to get address
    public String getAddress() {
        return address;
    }

    //setter to set address
    public void setAddress(String address) {
        this.address = address;
    }

    //getter to get message
    public String getMessage() {
        return message;
    }

    //setter to set message
    public void setMessage(String message) {
        this.message = message;
    }

    //getter to get user
    public Usertable getUser() {
        return user;
    }

    //setter to set user
    public void setUser(Usertable user) {
        this.user = user;
    }
    
    //cleans user details upon received from xhtml and calling ejb bean to change user profile details
    public void changeUserDetail(long id,String username,String address,String message)
    {
        String cleanaddress =Xss.cleanstring(address);
        String cleanmessage = Xss.cleanstring(message);
        String cleanusername = Xss.clean(username);
        edituserbean.update(id, username, cleanaddress, message);
        this.actionMessage="Edit Successfull";
    }
}
