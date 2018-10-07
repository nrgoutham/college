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

import Entity.Product;
import java.util.List;
import javax.ejb.Local;

@Local
public interface Bean_ProductLocal {
    //method to add products from shopping site
    public void addProduct(String title,String amount,String cost);
    //Initial method execution to add 1 product
    public void addProduct1(String title,String amount,String cost);
	//method to remove products from shopping site
    public boolean removeProduct(String title);
	//method to increase number of products in DB
    public boolean increment(String title,String amount);
	//method to decrease number of products in DB
    public boolean decrement(String title,String amount);
	//msgLog method used for JMS messages written to the log file
    public void msgLog(String user, String status, String productName, String quantity);
	//get product by  name
    public List<Product> getProductByName(String name);
	//get product by product id number
    public List<Product> getProductByID(int id);
	//get all products in the database
    public List<Product> getAllProducts();
}
