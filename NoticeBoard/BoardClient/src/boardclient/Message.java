//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.io.Serializable;



    public class Message implements Serializable{
    
    private String Message;
    
    public Message(){
        
    }
    
    public String getMessage(){
        return Message;
    }
    
    public void setMessage(String Message){
        this.Message=Message;
    }
    
}
    

