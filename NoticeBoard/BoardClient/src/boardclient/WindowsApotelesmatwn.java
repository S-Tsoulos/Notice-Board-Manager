//icsd13190 Tsoulos Sotiris katanemhmena2
package boardclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class WindowsApotelesmatwn extends JFrame implements ActionListener{
     private JPanel PanelResults;
    private JButton Arxiko;
    
    public WindowsApotelesmatwn(ArrayList <Anakoinwsh> SearchResults){
        super("Search Results");
        
        
        
        setSize(1000,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Panel kai layout
        PanelResults = new JPanel();
        BoxLayout box = new BoxLayout(PanelResults,BoxLayout.LINE_AXIS);
        
        //kai vazw ta apotelesmata sto panel mou
        for(int i=0;i<SearchResults.size();i++){
            
            PanelResults.add(Box.createRigidArea(new Dimension(1000,10)));
            PanelResults.add(new JLabel("Name: " + SearchResults.get(i).getName()));
            PanelResults.add(Box.createRigidArea(new Dimension(1000,10)));
            PanelResults.add(new JLabel("Surname: " + SearchResults.get(i).getSurname()));
            PanelResults.add(Box.createRigidArea(new Dimension(1000,10)));
            PanelResults.add(new JLabel("Title: " + SearchResults.get(i).getTitlos()));
            PanelResults.add(Box.createRigidArea(new Dimension(1000,10)));
            PanelResults.add(new JLabel("Id: " + SearchResults.get(i).getID()));
            
        }
        
        Arxiko = new JButton("Επιστροφή");
        Arxiko.addActionListener((ActionListener) this);
        PanelResults.add(Arxiko);
        this.add(PanelResults);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Arxiko){
            
            this.dispose();
            Window aw = new Window();
        }
    }

}