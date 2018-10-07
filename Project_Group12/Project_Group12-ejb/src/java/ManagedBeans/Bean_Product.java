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
import Entity.PurchaseOrder;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class Bean_Product implements Bean_ProductLocal
{

    @Resource(mappedName = "java:app/MsgQueue")
    private Queue java_appMsgQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    
    @PersistenceContext(unitName = "Project_Group12-ejbPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(Bean_Product.class.getName());
          
    //used to persist objects
    public void persist(Object object) {
        em.persist(object);
    }
    String pname,qty;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

        
    //Product increment method ,only admin can perform this action
     @Override
    public boolean increment(String title,String amount) 
    {
        Query qry= em.createNamedQuery("Product.findByDescription");//searching product by description
        qry.setParameter("description", title);//setting title of the product to field Description
        List <Product> isin=qry.getResultList();
	//If the product doesn't exist then return false
        if(isin.isEmpty())
        {
           return false;
        }
	//increment the product with the desired amount
        else
        {  
            int am=Integer.parseInt(amount);
            int currentAm=isin.get(0).getQuantityOnHand();
            Product p=isin.get(0);
            p.setQuantityOnHand(currentAm + am);
            em.persist(p);
          
          return true;
        }
        
    }
    
    //Product decrement method ,only admin can perform this action
    @Override
    public boolean decrement(String title,String amount)
    {      
        Query q= em.createNamedQuery("Product.findByDescription");//searching product by description
        q.setParameter("description", title);//setting title of the product to field Description
        List <Product> isin=q.getResultList();
        Product p=isin.get(0);
        int am=Integer.parseInt(amount);
	//If the product doesn't exist then return false
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
            //Product quantity is decreased only if this amount is greater than amount of the product set in DB
            if(p.getQuantityOnHand()<= am)
            {
                p.setQuantityOnHand(0);
                em.persist(p);
                return true;
            }		
            
            if(p.getQuantityOnHand()>0)
            {
                p.setQuantityOnHand(p.getQuantityOnHand()- am);
                em.persist(p);
                return true;
            }
            return true;
        }
    }
     //Initial method execution to add 1 product
    @Override
    public void addProduct1(String title, String amount, String cost)
    {
      
        int id=1;
        Product pr=new Product();   
        pr.setDescription(title);
        pr.setProductId(id);
        pr.setQuantityOnHand(Integer.parseInt(amount));
        pr.setPurchaseCost(Integer.parseInt(cost));
        pr.setAvailable("yes");
        em.persist(pr); 
        Product pdt=new Product(id);
        PurchaseOrder po=new PurchaseOrder();
        po.setOrderNum(1);
        po.setQuantity(Short.parseShort(amount));
        po.setProductId(pdt);
        po.setUsername("joe");
        em.persist(po);
    }  
    
    //method to add products from shopping site
    @Override
    public void addProduct(String title, String amount, String cost)
    {
        this.pname=title;
        this.qty=amount;
        Query q= em.createNamedQuery("Product.getHighestProductId");
        int id=(int) q.getSingleResult()+1;
        Product pr=new Product();   
        pr.setDescription(title);
        pr.setProductId(id);
        pr.setQuantityOnHand(Integer.parseInt(amount));
        pr.setPurchaseCost(Integer.parseInt(cost));
        pr.setAvailable("yes");
        msgLog("admin", "added", pname, qty);
        em.persist(pr);  
    }
    
    //Admin Remove Product Functionality
    @Override
    public boolean removeProduct(String title) 
    {
        Query qry= em.createNamedQuery("Product.findByDescription");
	//query by the description of the product
        qry.setParameter("description", title);		
	//return false if it doesn't exist
        List <Product> isin=qry.getResultList();
        if(isin.isEmpty()){
            return false;
        }
	//remove the item
        else{  
            msgLog("admin", "Removed", title, "ALL");
            em.remove(isin.get(0));
            return true;
        }
    }

    @Override
    public void msgLog(String user, String status, String productName, String quantity)
    {
        String value = String.format("%1$-20s %2$-30s %3$-15s %4$-15s","User", "Product", "Quantity", "Status");
        //message driven bean
        sendJMSMessageToMsgQueue(value);
        String temp = String.format("%1$-20s %2$-30s %3$-15s %4$-15s",user, productName, quantity, status);
        //message driven bean
        sendJMSMessageToMsgQueue(temp);
    }
    
   
    //Method to get product by name
    @Override
    public List<Product> getProductByName(String productname) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByDescription")
                .setParameter("description", productname);
        // return query result
        return query.getResultList();
    }

    //Method to get product by Id
    @Override
    public List<Product> getProductByID(int productId) {

        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findByProductId")
                .setParameter("productId", productId);
        // return query result
        return query.getResultList();
    }
    
    //Method to get All product
    @Override
    public List<Product> getAllProducts() {
        // create named query and set parameter
        Query query = em.createNamedQuery("Product.findAll");
        // return query result
        return query.getResultList();
    }

    private void sendJMSMessageToMsgQueue(String messageData) {
        context.createProducer().send(java_appMsgQueue, messageData);
    }

}
