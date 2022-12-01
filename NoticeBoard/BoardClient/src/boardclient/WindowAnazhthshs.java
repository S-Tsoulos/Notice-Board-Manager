//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class WindowAnazhthshs extends JFrame implements ActionListener {
    
    private JLabel OnomaLB,EponumoLB,TitlosLB,IdLB;
    private JTextField OnomaTF,EponumoTF,TitlosTF,IdTF;
    private JPanel panel;
    private JButton Search;
    
    private ArrayList <Anakoinwsh> SearchResults;
    
    public WindowAnazhthshs(){
        super("Αναζήτηση Ανακοίνωσης");
        
        setSize(600,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Arxikopoihsh metavlitwn
        OnomaLB = new JLabel("Δώσε Όνομα:");
        EponumoLB = new JLabel("Δώσε Επώνυμο:");
        TitlosLB = new JLabel("Δώσε Tίτλο Ανακοίνωσης:");
        IdLB = new JLabel("Δώσε Id:");
        
        OnomaTF = new JTextField(15);
        EponumoTF = new JTextField(15);
        TitlosTF = new JTextField(25);
       IdTF = new JTextField(15);
        
        Search = new JButton("Search");
        Search.addActionListener(this);
        
        //panel kai layout
        panel = new JPanel();
        BoxLayout box = new BoxLayout(panel,BoxLayout.LINE_AXIS);
        
        panel.add(Box.createRigidArea(new Dimension(600,10)));
        panel.add(OnomaLB);
        panel.add(OnomaTF);
        panel.add(Box.createRigidArea(new Dimension(600,10)));
        panel.add(EponumoLB);
        panel.add(EponumoTF);
        panel.add(Box.createRigidArea(new Dimension(600,10)));
        panel.add(TitlosLB);
        panel.add(TitlosTF);
        panel.add(Box.createRigidArea(new Dimension(600,10)));
        panel.add(IdLB);
        panel.add(IdTF);
        panel.add(Box.createRigidArea(new Dimension(600,10)));
        panel.add(Search);
        
        
        this.add(panel);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==Search){
   
            try {
                
                SearchResults = new ArrayList();
                String Titlos;
                Anakoinwsh notice = new Anakoinwsh();
                
                 
                
                notice.setId(Integer.valueOf(IdTF.getText()));
                notice.setTitlos(TitlosTF.getText());
                notice.setName(OnomaTF.getText());
                notice.setSurname(EponumoTF.getText());
                
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
                    message.setMessage("SEARCH");
                    //Grafw To search wste o client na kanei tis katallhlew energeies
                    os.writeObject(message);
                    os.flush();       
                    
                    //kai stelnw to antikeimeno me ta dedomena pou thelw na psaksw ston xrhsth
                    os.writeObject(notice);
                    os.flush();
                    
                    //Diavazw to arraylist me ta search results
                    SearchResults = (ArrayList<Anakoinwsh>) is.readObject();
                 
                    message = (Message) is.readObject();
                    //Ama parw ok termatizw th diadikasia
                    if(message.getMessage().equalsIgnoreCase("OK")){
                        System.out.println("Phra ok apo ton server");
                        //Termatizw th sundesh
                        os.close();
                        is.close();
                        sock.close();
                        //Emfanise ta search results
                        this.dispose();
                        WindowsApotelesmatwn srw = new WindowsApotelesmatwn(SearchResults);
                        
                    }
                    //Ama den parw OK
                    else{
                        System.out.println("Den phra teliko Ok apo Server");
                        os.close();
                        is.close();
                        sock.close();
                    }
          
                }
                
                os.flush();
                os.close();
                is.close();
                sock.close();
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(WindowAnazhthshs.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
}
