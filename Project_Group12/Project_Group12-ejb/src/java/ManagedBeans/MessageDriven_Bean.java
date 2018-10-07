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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//JMS Destination resource is declared which is required during operation
@JMSDestinationDefinition(name = "java:app/MsgQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "MsgQueue")
//Message listener interface
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/MsgQueue")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageDriven_Bean implements MessageListener {
    
    public MessageDriven_Bean() {
    }
    
    //Message Listener method onMessage will printout the log sent from different methods
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println(msg.getText());
        } catch (JMSException ex) {
            Logger.getLogger(MessageDriven_Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
