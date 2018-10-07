/*
 *
 * Arjun Ramesh                 16044215 
 * Dakshina Murthy              16064879
 * Gowtham NR                   16029143
 * Ruwaid Khateeb               16055314
 * Vivek Padmanaban chinnaraj   15113582
 *
 */
package ManagedBeans;


import Entity.Usertable;
import java.util.List;
import javax.ejb.Local;

@Local
public interface Bean_UserLocal {
    
    //method to add users
    public int createUser(String username,String password,String address,String usertype,String message);
    //checking if customer exist
    public boolean isCustomerExist();
    //check is username and password validate
    public boolean validate(String user,String pwd);
    //check is user rights
    public boolean checkRights(String user,String pwd);
    //method to get details of current user
    public List<Usertable> getCurrentUserDetails(long id);
    //getting current userId
    public long getUserID(String user,String pwd);
    //profile editing method
    public boolean update(long id, String username, String address, String message);
    //customer list by name
    public List<Usertable> getCustomerListByName(String name);
    //customer list by ID
    public List<Usertable> getCustomerListByID(long id);
    //setting user name
    public void setUserName(String userName);
    //getting username
    public String getUserName();
    //All customer list 
    public List<Usertable> getAllCustomer();
}
