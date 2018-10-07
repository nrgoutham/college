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

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import Security.PostMethod;
import ManagedBeans.Bean_ProductLocal;
import Security.Xss;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Admin implements Serializable{
    String productTitle;
    String productQuantity;
    String cost;
    String actionMessage;
    String sessionToken;
    String passedToken;

    @EJB   //EJB annotation for injection
    private Bean_ProductLocal adminproductBean;

	//getter for actionMessage
     public String getActionMessage() {
        return actionMessage;
    }
        //setter for actionMessage
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }
        //getter for cost
    public String getCost() {
        return cost;
    }
        //setter for cost
    public void setCost(String cost) {
        this.cost = cost;
    }
        //getter for actionMessage
    public String getProductTitle() {
        return productTitle;
    }
        //setter for productTitle
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
        //getter for productquantity
    public String getProductQuantity() {
        return productQuantity;
    }
        //setter for productquantity
    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
	
   public void storeProduct()
   {
       this.sessionToken=SessionBean.getToken();//gets the random generated token from the session
       this.passedToken=PostMethod.getHidden("tokenPass");//gets the generated token from hidden form field
       String userType=SessionBean.getUserType();//gets the usertype from the sessionBean
       //Executed If condition only if the tokens are mathed and usertype is admin 
       if(sessionToken.equalsIgnoreCase(passedToken) && userType.equalsIgnoreCase("admin") )
       {
            //callimg xss cleanstring method to validate input entered by user
            //this.productTitle= Xss.cleanstring(this.productTitle);
            this.productQuantity=Xss.cleanstring(this.productQuantity);
            this.cost=Xss.cleanstring(this.cost);
            adminproductBean.addProduct(this.productTitle, this.productQuantity,this.cost); 
            this.actionMessage="Product: "+this.productTitle+" Quantity:"+this.productQuantity+" Succsefuly added to Shopping site";
       }
       else 
           //request from a mirror site beacause of token mismatch or wrong usertype trying to access admin page
           PostMethod.postRedirect("./error.xhtml");//redirected to Error Page
   }
   
   /*
   Calls removeproduct method to Bean_ProductLocal interface using EJB.
   */
   public void removeProduct(){
        this.sessionToken=SessionBean.getToken();
        this.passedToken=PostMethod.getHidden("tokenPass");
        if(sessionToken.equalsIgnoreCase(passedToken)){
            boolean remove;
            remove= adminproductBean.removeProduct(this.productTitle);
            if(remove){
                this.actionMessage="Product: "+this.productTitle+" Succsefuly removed from Shopping site";
            }
            else
            {
		this.actionMessage="Product: "+this.productTitle+" Not Found";
            }
       }
      else{
           PostMethod.postRedirect("./error.xhtml");   
      }     
   }
      //Calls incrementQ method to Bean_ProductLocal interface using EJB.
   public void incrementQ()
   { 
        this.sessionToken=SessionBean.getToken();
        this.passedToken=PostMethod.getHidden("tokenPass");
        if(sessionToken.equalsIgnoreCase(passedToken))
        {
            boolean inc;
            inc= adminproductBean.increment(this.productTitle,this.productQuantity);
            if(inc){
		this.actionMessage="Product: "+this.productTitle+" Quantity has been incremeted by"+this.productQuantity ;
            }
            else{
		this.actionMessage="Product: "+this.productTitle+" Not Found";
            }
	}
       else{
            PostMethod.postRedirect("./error.xhtml"); 
            }
   }
   
      //Calls decrementQ method to Bean_ProductLocal interface using EJB.
   public void decrementQ(){
    this.sessionToken=SessionBean.getToken();
    this.passedToken=PostMethod.getHidden("tokenPass");
    if(sessionToken.equalsIgnoreCase(passedToken)){ 
	boolean dec;
	dec = adminproductBean.decrement(this.productTitle,this.productQuantity);
	if(dec){
            this.actionMessage="Product: "+this.productTitle+" Quantity has been decremented by"+this.productQuantity ;
	}
        else{
            this.actionMessage="Product: "+this.productTitle+" Not Found";
	}
       }
       else{
            PostMethod.postRedirect("./error.xhtml");
	}
   }
     public Admin(){}
     
     //If the usertype is not admin then error page is rendered
    public void adminAcsses()
    {
        String userType=SessionBean.getUserType();
        if(!userType.equalsIgnoreCase("admin")){
            PostMethod.postRedirect("./error.xhtml");
        }
    }
  
}
