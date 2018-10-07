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


import Entity.Product;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ManagedBeans.Bean_ProductLocal;


@Named(value = "showProduct")
@RequestScoped
public class ProductBean {
   
    //persistence for product
    @PersistenceContext(unitName = "Project_Group12-warPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction usertrn;
    
    //attributes
    private String productName;
    private int productId;
    
   
    @EJB    //EJB annotation for injection
    private Bean_ProductLocal productBean;
        
    public void persist(Object object) {
        try {
            usertrn.begin();
            em.persist(object);
            usertrn.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    //getter for product id
    public int getProductId() {
        return productId;
    }

    //setter for product id
    public void setProductId(int productId) {
        this.productId = productId;
    }
        
    //getter for product name
    public String getProductName() {
        return productName;
    }

    //setter for product name
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    //Method to get product by Id
    public List<Product> getProductByID() {
        List<Product> listProduct= productBean.getProductByID(productId);
        return listProduct;
    }
    
    //Method to get product by name
    public List<Product> getProductByName() {
        
        List<Product> listProduct= productBean.getProductByName(productName);
        return listProduct;
    }
    
    //Method to get All product
    public List<Product> getAllProducts() {
        List<Product> listProduct= productBean.getAllProducts();
        return listProduct;
    }
}

   
    


