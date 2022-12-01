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



public class WindowDiagrafhs extends JFrame implements ActionListener{
    
    private JPanel panel;
    private JButton DiagrafhsB;
    private JTextField IdTF;
    private JLabel IdLB;
    
    public WindowDiagrafhs(){
        
        super("Διαγραφή Ανακοίνωσης");
        
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Arxikopoihsh metavlitwn
        IdLB = new JLabel("Δώσε το id της ανακοίνωσης που θέλετε να διαγραφεί:");
        IdTF = new JTextField(10);
        DiagrafhsB = new JButton("Διαγραφή ανακίνωσης");
        DiagrafhsB.addActionListener(this);
        panel = new JPanel();
        
        BoxLayout box = new BoxLayout(panel,BoxLayout.LINE_AXIS);
        
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        panel.add(IdLB);
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        panel.add(IdTF);
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        panel.add(DiagrafhsB);
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        
        this.add(panel);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==DiagrafhsB){
            
            //Kai dinw to id pou mou edwse o xrhsths sto antikeimeno mou
            Anakoinwsh notice = new Anakoinwsh();
            notice.setId(Integer.valueOf(IdTF.getText()));
            
            
            try {
                //Kai arxizw th diadikasia me ta socket wste na steilw to antikeimeno mou ston server
                Socket sock = new Socket("localhost",5555);
               
                ObjectOutputStream os;
                os = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
                
                //Dhmiourgw antikeimeno Messages kai tou dinw th timh Hello
                Message message = new Message();
                message.setMessage("START");               
                os.writeObject(message);  
                os.flush();
                
                
                //Diavazw thn apanthsh tou server kai an h apanthsh einai WAITING stelnw to aithma pou thelw              
                message = (Message) is.readObject();
                
                if(message.getMessage().equalsIgnoreCase("WAITING")){
                     message.setMessage("DELETE");
                    //Grafw To DELETE wste o client na kanei tis katallhlew energeies
                    os.writeObject(message);
                    os.flush();                  
                    //kai stelnw to antikeimeno me ta dedomena pou thelw na psaksw ston xrhsth
                    os.writeObject(notice);
                    os.flush();
                    
                    
                    message = (Message) is.readObject();
                    // diavazw ama diagrafhke epituxws to antikeimeno h oxi kai emfanizw sxetiko munhma
                    if(message.getMessage().equalsIgnoreCase("DELETE OK")){
                        JOptionPane.showMessageDialog(this,"Η ανακοίνωση διαγράφτηκε επιτυχώς");
                    }
                    else if(message.getMessage().equalsIgnoreCase("DELETE FAIL")){
                        JOptionPane.showMessageDialog(this,"Η ανακοίνωση δέν βρέθηκε");
                        this.dispose();
                        Window aw = new Window();
                    }
                    
                    message = (Message) is.readObject();
                    //diavazw to ok apo ton server
                    if(message.getMessage().equalsIgnoreCase("OK")){
                        System.out.println("Phra ok");
                        is.close();
                        os.close();
                        sock.close();
                    }
                    else{
                        System.out.println("Den Phra ok");
                        is.close();
                        os.close();
                        sock.close();
                    }
                    Window aw = new Window();
                }
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(WindowDiagrafhs.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(WindowDiagrafhs.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
                 
            
        }
    }
    
    
}
