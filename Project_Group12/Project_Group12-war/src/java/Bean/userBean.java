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
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import ManagedBeans.Bean_ProductLocal;
import ManagedBeans.Bean_UserLocal;

@Named(value = "userBean")
@RequestScoped
public class userBean implements Serializable {

    @EJB
    private Bean_ProductLocal productBean;

    @EJB
    private Bean_UserLocal userbean;

    //attributes of user table
    private long uid; // userid
    private String username;//username
    private String password;//password
    private String address;//address
    private String usertype;//usertype
    private String message;//message
    
    //method to check if user exist
    public boolean isUserExist()
    {
     if(userbean.isCustomerExist())
     {
      return true;
     }
     else
         return false;
    }
   
    public void initialize()
    {
     if(!isUserExist())
     {
       int id;
       //adding hardcoded users-password 
       id = userbean.createUser("Gowtham","123","Castletroy, Limerick","customer","This is Gowtham");
       id = userbean.createUser("joe","1D10T?","Castletroy, Limerick","customer","This is joe");
       id = userbean.createUser("Arjun","1D10T?","Castletroy, Limerick","customer","This is Arjun");
       id = userbean.createUser("Vivek","1D10T?","Dublin City","customer","This is Vivek");
       id = userbean.createUser("Dakshin","1D10T?","BrunaGarden, Limerick","customer","This is Dakshin");
       id = userbean.createUser("Ruwaid","1D10T?","BrunaGarden, Limerick City","customer","This is Ruwaid");
       //adding hardcoded usera- admin
       id = userbean.createUser("toor","4uIdo0!","Limerick City","admin","This is admin Toor.");
       id = userbean.createUser("Gow","123","Limerick","admin","Gowtham Here.");
       productBean.addProduct1("Samsung TV", "50", "400");
     }
    }
    
    //getter for userid
    public long getUid() {
        return uid;
    }

    //setter for userid
    public void setUid(long uid) {
        this.uid = uid;
    }
      
    //getter for username
    public String getUsername() {
        return username;
    }

    //setter for username
    public void setUsername(String username) {
        this.username = username;
    }
    
    //getter for usertype
    public String getUsertype() {
        return usertype;
    }

    //setter for usertype
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
    //getter for password
    public String getPassword() {
        return password;
    }

    //setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    //getter for address
    public String getAddress() {
        return address;
    }

    //setter for address
    public void setAddress(String address) {
        this.address = address;
    }
    
    //getter for message
    public String getMessage() {
        return message;
    }
    
    //setter for userid
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void create() {
        uid = userbean.createUser(username,password,address,usertype,message);
    }
    
    //Creates a new instance of AddCustomerBean
    public userBean() {
    }

    //notifiaction when user added
    public String notification() {
        if (uid == 0) return "";
        else return "New user with id " + uid + " created!!!";
    }
    
    //method to call ejb bean getting All user detail
    public List<Usertable> getAllCustomerList()
    {
        List<Usertable> userList = userbean.getAllCustomer();
        return userList;
    }

    //method to call ejb bean getting user detail by name
    public List<Usertable> getCustomerListByName(String name)
    {
		List<Usertable> userList = userbean.getCustomerListByName(name);
		return userList;
    }
    
    //method to call ejb bean getting user detail by id
    public List<Usertable> getCustomerListByID(long id)
    {
		List<Usertable> userList = userbean.getCustomerListByID(id);
		return userList;
    }
        
    //method to call ejb bean getting current user detail by id
    public List<Usertable> getCurrentUserDetail(long uid)
    {
		List<Usertable> userList = userbean.getCurrentUserDetails(uid);
		return userList;
    }
   
    //method to call ejb bean to set user detail
    public void setCurrentUserDetail(long uid)
    {
      List<Usertable> userList = userbean.getCurrentUserDetails(uid);
      this.uid = userList.get(0).getUid();
      this.username = userList.get(0).getUsername();
      this.address = userList.get(0).getAddress();
      this.message = userList.get(0).getMessage();
    }
}
