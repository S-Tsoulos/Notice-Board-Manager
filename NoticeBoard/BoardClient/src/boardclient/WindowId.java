//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 

public class WindowId extends JFrame{
    
    private JPanel panel;
    private JLabel MonadikoId;
    
    public WindowId(Anakoinwsh anakoinwseis){
        
        super("Id");
        
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Arxikopoihsh metavlitwn
        panel = new JPanel();
        BoxLayout box = new BoxLayout(panel,BoxLayout.LINE_AXIS);
        
        MonadikoId = new JLabel("To id είναι: " + String.valueOf(anakoinwseis.getID()));
        
        
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        panel.add(MonadikoId);
        panel.add(Box.createRigidArea(new Dimension(400,50)));
        
        
        this.add(panel);
        this.setVisible(true);
        
        
    }
    
}
