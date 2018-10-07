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

import java.util.HashMap;
import javax.ejb.Local;

@Local
public interface Cart_BeanLocal
{
    // adds a number items from the shopping cart. 
    public void addItem(String id, int quantity);
    // Removes items from the shopping cart. If quantity exceeds the number of present items, number is set to 0.
    public void removeItem(String id, int quantity);
	// Checkout will returns success/failure message to user and creates Purchase order for successfull order
    public String checkout(String s);
    // Cancels the current ordering process. Cancel will terminate the current session for the EJB.
    public void cancel(String s);
    // Content of the shopping cart is sent as string
    public String getItemList();  
    // Returns a Hash-map of the Checkout cart items
    public HashMap<String, Integer> getCartItems();
    // JMS log sender
    public void msgLog(String user, String status);
    // Returns a boolean if the order valid/invalid
    public boolean ValidOrder();
    // Decrements the product table
    public boolean decrement(String title, String amount);

}
