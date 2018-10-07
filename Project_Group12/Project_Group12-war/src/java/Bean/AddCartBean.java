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

import java.io.Serializable;
import java.util.HashMap;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import ManagedBeans.Cart_BeanLocal;


@Named(value = "addCartBean")
@SessionScoped
public class AddCartBean implements Serializable {
    private int quantityVar = 0;
    private int quantityVar1 = 0;
    private String order = "";

    @EJB //EJB annotation for injection
    private Cart_BeanLocal cartbean;
    
    //getter for quantityVar
    public int getQuantityVar() {
        return quantityVar;
    }

    //setter for quantityVar
    public void setQuantityVar(int quantityVar) {
        this.quantityVar = quantityVar;
    }
       
    //getter for quantityVar1
    public int getQuantityVar1() {
        return quantityVar1;
    }

    //setter for quantityVar1
    public void setQuantityVar1(int quantityVar1) {
        this.quantityVar1 = quantityVar1;
    }
    
    //getter for order
    public String getOrder() {
        return order;
    }   
    //Default Constructor
    public AddCartBean() {
    }
     

    //Hash map list to retreive cart item 
    public HashMap<String, Integer> getCartItems()
    {
        //calling getCartItems method in Cart_BeanLocal interface using EJB
        return cartbean.getCartItems();
    }    
    
    
    public void addToBasket(String pName, int quantityVar) {
       
         //calling addItem method in Cart_BeanLocal interface using EJB
        cartbean.addItem(pName, quantityVar);  
       
        //reset quantityVar 
        this.quantityVar = 0;
    }
    
    public void removeFromBasket(String pName, int quantityVar) {
       
        //calling removeItem method in Cart_BeanLocal interface using EJB
        cartbean.removeItem(pName, quantityVar);
        
        //reset quantityVar1
        this.quantityVar1 = 0;
    }
   
 
    public String checkout()
    {   
        String s1=SessionBean.getUserName();
        //System.out.println("username is :"+s1);
        //calling checkout method in Cart_BeanLocal interface using EJB
        order = cartbean.checkout(s1);
        //return value for view to render next page
        return "checkout";
      
    }
         
    
    public String cancel() {
        String s2=SessionBean.getUserName();
        //calling cancel method in Cart_BeanLocal interface using EJB
        cartbean.cancel(s2);
        //return value for view to render next page
        return "cancel";
    }

    public String getItemList() {
        //calling getItemList method in Cart_BeanLocal interface using EJB
        String content = cartbean.getItemList();
        return content.replace("<br>", "");
    }

    public String index() {     
        // Destroys current session  
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        return "landing";
    }

    
}
