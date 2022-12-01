//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Anakoinwsh implements Serializable{
    
     private String Surname;
    private String Name;
    private String Titlos;
    private String Text;
    private int id;
    
    
    //Default Constructor
    public Anakoinwsh(){
        
    }
    
    //Constructor ths klashs Anakoinwsh 
    public Anakoinwsh(String Surname,String Name,String Titlos,String Text){
        this.Surname=Surname;
        this.Name=Name;
        this.Titlos=Titlos;
        this.Text=Text;
              
    }
    
    public void setSurname(String surname){
        this.Surname=surname;
    }
    
    public void setName(String Name){
        this.Name=Name;
    }
    public void setTitlos(String Titlos){
        this.Titlos=Titlos;
    }
    public void setText(String Text){
        this.Text=Text;
    }
    
    //Methodoi get
    public String getName(){
        return Name;
    }
    
    public String getSurname(){
        return Surname;
    }
    
    public String getTitlos(){
        return Titlos;
    }
    public String getText(){
        return Text;
    }
    
    public int getID(){
        return id;
    }
   
//    //Ftiaxnw monadiko id kai to epistrefw

    
    public void setId(int id){
        this.id=id;
    }
    
    
}
            
    
    

