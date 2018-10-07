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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class Cart_Bean implements Cart_BeanLocal {
    //JMS resource Mapping
    @Resource(mappedName = "java:app/MsgQueue")
    private Queue java_appMsgQueue;
    //Default connection factory is used to log messages
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;


    private HashMap<String, Integer> items = new HashMap<>();
    
    //Persistence unitname from xml
    @PersistenceContext(unitName = "Project_Group12-ejbPU")
    //Creating Entity manager for persisting data
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(Cart_Bean.class.getName());
    private String userName;
    private long usid;
    
    @Override
    public HashMap<String, Integer> getCartItems() {
        return items;
    }
    //persist object
    public void persist(Object object) {
        em.persist(object);
    }
    
    /*
    Checkout method will initially check whether order is valid ,i.e either if quantity is zero or 
    if quantity exceeds database quantity then checkout will not be successfull.
    For a successfull checkout order quantity number will be reduced in database and will validate number not 
    to go below zero.For a successfull checkout ,Purchase order is created in database.
    
    */
     @Override
    public String checkout(String s) 
    {
        
        Bean_User nb = new Bean_User();
        this.userName=s;
        boolean chek;//Boolean check 
        
        if(ValidOrder())
        {
            Iterator it = items.entrySet().iterator();
             while (it.hasNext()) 
            {
                HashMap.Entry pair = (HashMap.Entry)it.next();
                decrement(pair.getKey().toString(), pair.getValue().toString());
                Query qry = em.createNamedQuery("Product.findByDescription");
                qry.setParameter("description", pair.getKey().toString());
                List <Product> isin = qry.getResultList();
                Product pro = isin.get(0);
                int pid = pro.getProductId();
                em.persist(pro);       
                try
        {
         Query qry1 = em.createNamedQuery("PurchaseOrder.getHighestPurchaseOrderID");
        int id=(int) qry1.getSingleResult()+1;
        short value = (int)pair.getValue() > Short.MAX_VALUE ? Short.MAX_VALUE : (int)pair.getValue() < Short.MIN_VALUE ? Short.MIN_VALUE : (short)(int)pair.getValue();
        PurchaseOrder por=new PurchaseOrder();
        Product pdt=new Product(pid);
        Date dt = new Date();
        por.setOrderNum((Integer)id);
        por.setProductId(pdt);
        por.setQuantity(value);
        por.setSalesDate(dt); 
        por.setUsername(s);
       
        em.persist(por);     
        
        System.out.println("Purchase order Created Successfully!");
        }
        catch(Exception ex)
        {
         System.out.println(ex.toString());
         System.out.println("purchase order not Created");
        }
            }
            msgLog(s, "Confirmed");
            chek=true;
        }
        else
        {
            chek= false;
        }
        
        if(chek==true)
        {
            String message = getItemList();
            items.clear();
            return message;
        }
        else
        {
            items.clear();
            return getItemList();
        }
    }

    @Override
    public void addItem(String id, int quantity) {
        // obtain current number of items in cart
        Integer orderQuantity = items.get(id);
        if (orderQuantity == null) {
            orderQuantity = 0;
        }
        // adjust quantity and put back to cart
        orderQuantity += quantity;
        items.put(id, orderQuantity);
        String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Added into the cart");   
        //message driven bean
        sendJMSMessageToMsgQueue(temp);
      
    }

    @Override
    public void removeItem(String id, int quantity) {
        // obtain current number of items in cart
        Integer orderQuantity = items.get(id);
        if (orderQuantity == null) {
            orderQuantity = 0;
        }
        // adjust quantity and put back to cart
        orderQuantity -= quantity;
        if (orderQuantity <= 0) {
            // final quantity less equal 0 - remove from cart
            items.remove(id);
            String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Remove Item From Cart");
            //message driven bean
            sendJMSMessageToMsgQueue(temp);
        } 
        else 
        {
            // final quantity > 0 - adjust quantity
            items.put(id, orderQuantity);
            String temp = String.format("%1$-20s %2$-20s %3$-20s", id, quantity, "Remove from cart");
            
            //message driven bean
            sendJMSMessageToMsgQueue(temp);
        }
        
         }

    @Override
    public void cancel(String s) 
    {
        msgLog(s, "Cancelled");
        items.clear();
    }
    
 
   
    @Override
    public String getItemList() 
    {
        if(!items.isEmpty())
        {
            System.out.println("Items not empty");
            String message = "";
            Set<String> keys = items.keySet();
            Iterator<String> it = keys.iterator();
            String k;
            while (it.hasNext()) {
                k = it.next();
                message += "Product: " + k + ", Quantity: " + items.get(k);
            }
            return message;
        }
        else
            return "No items selected or quantity execeeded!";
    }
    
    

    @Override
    public boolean ValidOrder()
    {        
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) 
        {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            Query q = em.createNamedQuery("Product.findByDescription");
            q.setParameter("description", pair.getKey());
            List<Product> isin = q.getResultList();
            if(isin.get(0).getDescription().equals(pair.getKey()))
            {
                int orderQty = Integer.parseInt(pair.getValue().toString());
                if((isin.get(0).getQuantityOnHand() < orderQty)|(orderQty<=0))
                {
                    return false;
                }
            }  
        }
        return true;
    }
    
   

    @Override
    public boolean decrement(String title, String amount)
    {      
        Query q = em.createNamedQuery("Product.findByDescription");
        q.setParameter("description", title);
        List <Product> isin = q.getResultList();
        Product prd = isin.get(0);
        int am = Integer.parseInt(amount);
        if(isin.isEmpty())
        {
            return false;
        }
        else
        { 
            if(prd.getQuantityOnHand() <= am)
            {
                prd.setQuantityOnHand(0);
                em.persist(prd);
                return true;
            }
            if(prd.getQuantityOnHand() > 0)
            {
                prd.setQuantityOnHand(prd.getQuantityOnHand() - am);
                em.persist(prd);
                return true;
            }
            return true;
        }
    }
   
    @Override
    public void msgLog(String user, String status)
    {
        //LOGGER.info("Logger Name:" + LOGGER.getName());
        String value = String.format("%1$-20s %2$-30s %3$-15s %4$-15s", "User", "Product", "Quantity", "Status");
        //message driven bean
        sendJMSMessageToMsgQueue(value);
        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String temp = String.format("%1$-20s %2$-30s %3$-15s %4$-15s", user,  pair.getKey(),  pair.getValue(),  status);
      
            //message driven bean
            sendJMSMessageToMsgQueue(temp);

            //LOGGER.info(temp);
        }
    }

    private void sendJMSMessageToMsgQueue(String messageData) {
        context.createProducer().send(java_appMsgQueue, messageData);
    }
    

}
