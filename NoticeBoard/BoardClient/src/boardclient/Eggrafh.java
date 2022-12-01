//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Eggrafh extends JFrame implements ActionListener {
    
    private String Username;
    private String Password;
    //Labels
    private JLabel UsernameLB;
    private JLabel PassLB;
    
    //Textfields
    private JTextField UsernameTF;
    private JTextField PassTF;
    
    //Buttons
    private JButton Submit;
    
    //Panels
    private JPanel PanelEggrafhs;
    
   public Eggrafh(){
       
       super("Εγγραφή");
        
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
      UsernameLB = new JLabel();
      UsernameLB.setText("Όνομα χρήστη :");
      UsernameTF = new JTextField(15);
      
      // Password Label
      PassLB = new JLabel();
      PassLB.setText("Κωδικός χρήστη :");
      PassTF = new JTextField(15);
      
      Submit = new JButton("Submit");
      Submit.addActionListener(this);
      
       PanelEggrafhs = new JPanel();
        BoxLayout box = new BoxLayout(PanelEggrafhs,BoxLayout.LINE_AXIS);
        
        //Dmiourgw to panel mou
        PanelEggrafhs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEggrafhs.add(UsernameLB);
        PanelEggrafhs.add(UsernameTF);
        PanelEggrafhs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEggrafhs.add(PassLB);
        PanelEggrafhs.add(PassTF);
        PanelEggrafhs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEggrafhs.add(Submit);
        
        this.add(PanelEggrafhs);
        this.setVisible(true);
       
   }

     public Eggrafh(String Username,String Password){
        this.Username=Username;
        this.Password=Password;
     }

    
   
   
   @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==Submit){
                                     
            try {
                Eggrafh sub=new Eggrafh(UsernameTF.getText(),PassTF.getText());
                
                
                //Kai arxizw th diadikasia me ta socket wste na steilw to antikeimeno mou ston server
                Socket sock = new Socket("localhost",5555);
               
                ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
                
                //Dhmiourgw antikeimeno Message kai tou dinw th timh Hello
                Message message = new Message();
                message.setMessage("START");               
                os.writeObject(message);  
                os.flush();
                
                //Diavazw thn apanthsh tou server kai an h apanthsh einai WAITING stelnw to aithma pou thelw              
                message = (Message) is.readObject();
                if(message.getMessage().equalsIgnoreCase("WAITING")){
                    //Dinw tupo aithmatos INSERT 
                    message.setMessage("SUBSCRIBE");
                    os.writeObject(message); 
                    os.flush();                                    
                    //kai stelnw to antikeimeno pou thelw na kanei insert
                    os.writeObject(sub); 
                    
                    os.flush(); 
                                       
                    
                    message = (Message) is.readObject();
                    
                    //Ama o server ekane th diadikasia INSERT epituxws pernw to OK 
                    if(message.getMessage().equalsIgnoreCase("OK")){
                        //Pernw to ananewmeno antikeimeno kai emfanizw to onoma xrhsth
                        sub = (Eggrafh) is.readObject();
                        
                        JOptionPane.showMessageDialog(this,"Η εγγραφή πραγματοποιήθηκε");
                        os.close();
                        is.close();
                        sock.close();
                        //kleinw to parathuro kai epistrefw sto arxiko parathuro
                        this.dispose();
                        Window aw = new Window();
                    }
                    //An o server den steilei to OK termatizw th diadikasia
                    else{
                        System.out.println("Den egine to insert epituxws client");
                        is.close();
                        os.close();
                        sock.close();
                        
                    }                                      
                }
                //An o server den steilei WAITING termatizw th diadikasia
                else{
                    System.out.println("Den hrthe waiting client");
                    os.close();
                    is.close();
                    sock.close();
                }  
                } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(WindowAnakoinwshs.class.getName()).log(Level.SEVERE, null, ex);
            } 
                
        }
    }
}


