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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;




@Stateless
public class Bean_User implements Bean_UserLocal {
    @PersistenceContext(unitName = "Project_Group12-ejbPU")
    private EntityManager em;
    //User name 
    private String userName;
    //user ID
    private long id;
    //Getter for id
    public long getId() {
        return this.id;
    }
    
    //validate user exists
    @Override
    public boolean validate(String user,String pwd)
    {
        // creating named query and set parameter used for username and password
        Query query = em.createNamedQuery("Usertable.loginValidate");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    } 

    //Adds a new customer to the database with given name, password, address, usertype, message
    @Override
    public int createUser(String username,String password,String address,String usertype,String message)
    {
        //user table object
        Usertable usr = new Usertable();
     
        //making it persistence to autogenerate id before adding details
        persist(usr);
        // setting username, password, address, usertype, message
        usr.setUsername(username);
        usr.setPassword(password);
        usr.setAddress(address);
        usr.setUsertype(usertype);
        usr.setMessage(message);

        // return id of new customer
        return 1;
    }
    
    //setting username
    @Override
    public void setUserName(String username)
    {
        this.userName = username;
    }
    
    //getting username
    @Override
    public String getUserName()
    {
        return this.userName;
    }

    //Returns list of customer with a given name
    @Override
    public boolean isCustomerExist() {
        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.isUserExists").setParameter("username", "joe");
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    }
    
    //getting userID by passing username and password
    @Override
    public long getUserID(String user,String pwd)
    {
        //create Query
        Query query = em.createNamedQuery("Usertable.getUserID");
        query.setParameter("username", user);
        query.setParameter("password", pwd);
        //Execute Query
        long id = (long)query.getSingleResult();
        //setting current id 
        this.id = id;
        return id;
    }
	
    //Check the users rights	
    @Override
    public boolean checkRights(String user,String pwd)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.loginValidate2");
        query.setParameter("username", user);
		query.setParameter("password", pwd);
        query.setParameter("usertype", "admin");
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         return true;
        }
        return false;
    } 
	
    //used to persist objects
    public void persist(Object object) {
        em.persist(object);
    }
    	//return entire list of customers
    @Override
    public List<Usertable> getAllCustomer()
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.findAllCustomer");
        query.setParameter("usertype", "customer");
        //execute query
        List<Usertable> result = query.getResultList();
        return result;
    }
    
    //getting user details by passing id
    @Override
    public List<Usertable> getCurrentUserDetails(long id) {

        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.findByUserId")
                .setParameter("uid", id);
        //Query execute
        List<Usertable> result = query.getResultList();
        return result;
    }
    
    //getting customer list by name
    @Override
    public List<Usertable> getCustomerListByName(String name)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.findByCustomerName");
        query.setParameter("username", name);
        query.setParameter("usertype", "customer");
        //Execute query
        List<Usertable> result = query.getResultList();
        return result;
    }
	
    //get customer by id
    @Override
    public List<Usertable> getCustomerListByID(long id)
    {
        // create named query and set parameter
        Query query = em.createNamedQuery("Usertable.findByCustomerId");
        query.setParameter("uid", id);
        query.setParameter("usertype", "customer");
        //execute query
        List<Usertable> result = query.getResultList();
        return result;
    }
    //update user with id, username, address, message
        @Override
    public boolean update(long id, String username, String address, String message)
    {
        //create named query and set parameter
        Query qry= em.createNamedQuery("Usertable.findByUserId");
        qry.setParameter("uid", id);
        //execute query
        List <Usertable> isin=qry.getResultList();
        //checking if user in the list
        if(isin.isEmpty())
        {
         return false;
        }
         else
        {  
            //Setting user details
            Usertable usr=isin.get(0);
            usr.setUsername(username);
            usr.setAddress(address);
            usr.setMessage(message);
            em.persist(usr);
          
          return true;
        }
    }
}
