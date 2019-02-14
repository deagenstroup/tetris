package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static tetris.Controls.*;

public class ControlsPanel extends JPanel implements ActionListener, KeyListener {
    
    private MainFrame rootFrame;
    private Dimension dimen;
    
    private Controls controls;
    private SpecialButton tempButton;
    
    private int location;
    private boolean getKey = false;
    
    public ControlsPanel( int inWidth, MainFrame inRootFrame ) {
        rootFrame = inRootFrame;
        //rootFrame.setSize(inWidth + 50, inWidth + 50);
        dimen = new Dimension( inWidth, inWidth );
        
        this.setBorder( BorderFactory.createLineBorder(Color.black) );
        this.setPreferredSize(dimen);
        
        this.setLayout( new GridLayout(9,2) );
        
        this.addComponants();
    }
    
    public void addComponants() {
        Font ft = new Font("Times New Roman", Font.BOLD, (int)((double)(dimen.getWidth())*(26.0/500.0)) );
        JLabel commandsTitle = new JLabel("Commands");
        JLabel keysTitle = new JLabel("Keys");
        commandsTitle.setHorizontalAlignment(JLabel.CENTER);
        keysTitle.setHorizontalAlignment(JLabel.CENTER);
        commandsTitle.setFont(ft);
        keysTitle.setFont(ft);
        this.add(commandsTitle);
        this.add(keysTitle);
        
        controls = new Controls(7);
        controls.readControlsFromFile();
        
        this.add( new JLabel("Move Right", JLabel.CENTER) );
        SpecialButton rightButton = new SpecialButton(controls.getKey(COMMAND_MOVERIGHT), controls.getLocation(COMMAND_MOVERIGHT) );
        rightButton.addActionListener(this);
        this.add( rightButton );
        
        this.add( new JLabel("Move Left", JLabel.CENTER) );
        SpecialButton leftButton = new SpecialButton(controls.getKey(COMMAND_MOVELEFT), controls.getLocation(COMMAND_MOVELEFT));
        leftButton.addActionListener(this);
        this.add( leftButton );
        
        this.add( new JLabel("Rotate Clockwise", JLabel.CENTER) );
        SpecialButton rotateButton = new SpecialButton(controls.getKey(COMMAND_ROTATECLOCKWISE), controls.getLocation(COMMAND_ROTATECLOCKWISE));
        rotateButton.addActionListener(this);
        this.add( rotateButton );
        
        this.add( new JLabel("Rotate Counterclockwise", JLabel.CENTER) );
        SpecialButton rotateButton1 = new SpecialButton(controls.getKey(COMMAND_ROTATECOUNTERCLOCKWISE), controls.getLocation(COMMAND_ROTATECOUNTERCLOCKWISE));
        rotateButton1.addActionListener(this);
        this.add( rotateButton1 );
        
        this.add( new JLabel("Phase Down", JLabel.CENTER) );
        SpecialButton phaseButton = new SpecialButton(controls.getKey(COMMAND_PHASE), controls.getLocation(COMMAND_PHASE));
        phaseButton.addActionListener(this);
        this.add( phaseButton );
        
        this.add( new JLabel("Hold", JLabel.CENTER) );
        SpecialButton holdButton = new SpecialButton(controls.getKey(COMMAND_HOLD), controls.getLocation(COMMAND_HOLD));
        holdButton.addActionListener(this);
        this.add( holdButton );
        
        this.add( new JLabel("Stop Game", JLabel.CENTER) );
        SpecialButton stopButton = new SpecialButton(controls.getKey(COMMAND_RESTARTGAME), controls.getLocation(COMMAND_RESTARTGAME));
        stopButton.addActionListener(this);
        this.add( stopButton );
        
        JButton jb = new JButton("Back");
        jb.addActionListener(rootFrame);
        this.add( jb );
    }
    
    public void actionPerformed( ActionEvent e ) {
        tempButton = (SpecialButton)e.getSource();
        tempButton.addKeyListener(this);
        tempButton.setFocusable(true);
        location = tempButton.getID();
        tempButton.setText("-");
        getKey = true;
    }
    
    public void keyPressed( KeyEvent e ) {
        if( getKey ) {
            int keyPressed = e.getKeyCode();
            String keyStr = KeyEvent.getKeyText(keyPressed);
            controls.controlKeys[location] = keyStr;
            tempButton.setText(keyStr);
            controls.writeControlsToFile();
            getKey = false;
        }
    }
    
    public void keyTyped( KeyEvent e ) {
    }
    public void keyReleased( KeyEvent e ) {
    }
}
