package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel implements ActionListener, PropertyChangeListener {
    
    private MainFrame rootFrame;
    private Dimension dimen;
    JFormattedTextField formattedTextField;
    
    public OptionsPanel( int inWidth, MainFrame inRootFrame ) {
        rootFrame = inRootFrame;
        dimen = new Dimension( inWidth, inWidth );
        
        this.setBorder( BorderFactory.createLineBorder(Color.black) );
        this.setPreferredSize(dimen);
        
        this.setLayout(new GridLayout(3,1));
        
        this.addComponants();
    }
    
    public void addComponants() {
        JPanel nestedPanel = new JPanel();
        nestedPanel.setLayout( new GridLayout(1,2) );
        this.add( nestedPanel );
        
        formattedTextField = new JFormattedTextField( NumberFormat.getNumberInstance());
        formattedTextField.setColumns(20);
        formattedTextField.setValue(new Integer(0));
        formattedTextField.addPropertyChangeListener( "value", this );
        nestedPanel.add( formattedTextField, JLabel.CENTER );
        
        nestedPanel.add( new JLabel("Width:"), JLabel.CENTER );
        
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(this);
        this.add( applyButton );
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener( rootFrame );
        this.add( backButton );
    }
    
    public void actionPerformed( ActionEvent e ) {
        System.out.println("Test");
        Options.writeToFile( ((Long)formattedTextField.getValue()).intValue() );
    }
    
    public void propertyChange( PropertyChangeEvent evt ) {
        
    }
    
}
