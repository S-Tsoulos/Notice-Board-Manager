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

public class WindowAnakoinwshs extends JFrame implements ActionListener {
    
    //Labels
    private JLabel SurnameLB;
    private JLabel NameLB;
    private JLabel TitlosLB;
    private JLabel TextLB;
    
    
    
    //Textfields
    private JTextField SurnameTF;
    private JTextField NameTF;
    private JTextField TitlosTF;
    private JTextField TextTF;
     
   
    
    //Buttons
    private JButton Submit;
    
    //Panels
    private JPanel PanelEisagwghs;
    
   

    public WindowAnakoinwshs() {
        super("Ανακοινωση");
        
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Arxikopoihsh metavlitwn
        
        //Labels
        SurnameLB = new JLabel("Δώστε επώνυμο:");
        NameLB = new JLabel("Δώστε όνομα:");
        TitlosLB = new JLabel("Δώστε τον τίτλο:");
        TextLB = new JLabel("Δώστε την ανακoίνωση:");
        
        
        //Textfields
        SurnameTF = new JTextField(15);
        NameTF = new JTextField(15);
        TitlosTF = new JTextField(15);
        TextTF = new JTextField(35);
        
        
        //Buttons
        Submit = new JButton("Submit");
        Submit.addActionListener(this);
        
        //Dhmiourgia panel kai layout
        PanelEisagwghs = new JPanel();
        BoxLayout box = new BoxLayout(PanelEisagwghs,BoxLayout.LINE_AXIS);
        
        //Dmiourgw to panel mou
        PanelEisagwghs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEisagwghs.add(NameLB);
        PanelEisagwghs.add(NameTF);
        PanelEisagwghs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEisagwghs.add(SurnameLB);
        PanelEisagwghs.add(SurnameTF);
        PanelEisagwghs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEisagwghs.add(TitlosLB);
        PanelEisagwghs.add(TitlosTF);
        PanelEisagwghs.add(Box.createRigidArea(new Dimension(800,20)));
        PanelEisagwghs.add(TextLB);
        PanelEisagwghs.add(TextTF);
        PanelEisagwghs.add(Box.createRigidArea(new Dimension(600,10)));
        PanelEisagwghs.add(Submit);
        
        this.add(PanelEisagwghs);
        this.setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==Submit){
                                     
            try {
                  
                //Kai dhmiourgw to antikeimeno               
                Anakoinwsh notice = new Anakoinwsh(SurnameTF.getText(), NameTF.getText(), TitlosTF.getText(),TextTF.getText());
            
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
                    message.setMessage("INSERT");
                    os.writeObject(message); 
                    os.flush();                                    
                    //kai stelnw to antikeimeno pou thelw na kanei insert
                    os.writeObject(notice); 
                    os.flush();                     
                    
                    message = (Message) is.readObject();
                    
                    //Ama o server ekane th diadikasia INSERT epituxws pernw to OK 
                    if(message.getMessage().equalsIgnoreCase("OK")){
                        //Pernw to ananewmeno antikeimeno kai emfanizw Id 
                        notice = (Anakoinwsh) is.readObject();
                        JOptionPane.showMessageDialog(this,"Το id είναι:" + notice.getID());
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
