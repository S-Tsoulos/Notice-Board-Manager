//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener{
     private JPanel panel;
    private JButton ΕggrafhB,AnazhthshB,KataxwrhshB,AkurwshB;
    
    public Window(){
        
        super("Επιλογές");
        
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Arxikopoihsh metavlitwn
        ΕggrafhB = new JButton("Εγγραφή Χρήστη");
        ΕggrafhB.addActionListener(this);
        AnazhthshB = new JButton("Αναζήτηση Ανακοινώσεων");
        AnazhthshB.addActionListener(this);
        KataxwrhshB = new JButton("Καταχώρηση Ανακοινώσεων");
        KataxwrhshB.addActionListener(this);
        AkurwshB = new JButton("Διαγραφη Ανακοινώσεων");
        AkurwshB.addActionListener(this);
        
        
        panel = new JPanel();
        BoxLayout box = new BoxLayout(panel,BoxLayout.LINE_AXIS);
        
        
        panel.add(Box.createRigidArea(new Dimension(450,50)));
        panel.add(ΕggrafhB);
        panel.add(Box.createRigidArea(new Dimension(450,50)));
        panel.add(AnazhthshB);
        panel.add(Box.createRigidArea(new Dimension(450,50)));
        panel.add(KataxwrhshB);
        panel.add(Box.createRigidArea(new Dimension(450,50)));
        panel.add(AkurwshB);
        panel.add(Box.createRigidArea(new Dimension(450,50)));
        
        this.add(panel);
        
        this.setVisible(true);
    }
//Elegxos gia to ti tha dialeksei o xrhsths na kanei
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==ΕggrafhB){
            this.dispose();
            Eggrafh wak = new Eggrafh();
        }
        
        if (e.getSource()==AnazhthshB){
            this.dispose();
            WindowAnazhthshs wa = new WindowAnazhthshs();
        }
        
        if(e.getSource()==KataxwrhshB){
            this.dispose();
            WindowAnakoinwshs wk = new WindowAnakoinwshs();
        }
        
        if(e.getSource()==AkurwshB){
            this.dispose();
            WindowDiagrafhs wak = new WindowDiagrafhs();
        }
    }
    
    
    
}
