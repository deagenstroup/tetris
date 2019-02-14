package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
    
    private static final int numOfButtons = 4;
    public static final double heightRatio = 0.35;
    
    private MainFrame rootFrame;
    private Dimension dimen;
    private JButton playButton, scoresButton, controlsButton, optionsButton;
    
    public Menu( MainFrame inFrame, int inWidth ) {
        this.setBorder( BorderFactory.createLineBorder(Color.black) );
        this.setPreferredSize(dimen);
        this.setLayout( new GridLayout( numOfButtons,1 ) );
        
        dimen = new Dimension();
        dimen.setSize( inWidth, inWidth*heightRatio );
        
        rootFrame = inFrame;
        //rootFrame.setSize( inWidth+50, (int)((double)inWidth*heightRatio*(double)numOfButtons)+50 );
        
        Font buttonFont = new Font( "Times New Roman", Font.PLAIN, (int)((double)inWidth*(32.0/200.0)) );
        Dimension buttonDimen = new Dimension( inWidth, (int)((double)inWidth*heightRatio) );
        playButton = new JButton("Play");
        scoresButton = new JButton("High Scores");
        controlsButton = new JButton("Controls");
        optionsButton = new JButton("Options");
        playButton.setPreferredSize( buttonDimen );
        scoresButton.setPreferredSize( buttonDimen );
        controlsButton.setPreferredSize( buttonDimen );
        optionsButton.setPreferredSize( buttonDimen );
        playButton.setFont(buttonFont);
        scoresButton.setFont(buttonFont);
        controlsButton.setFont(buttonFont);
        optionsButton.setFont(buttonFont);
        playButton.addActionListener(rootFrame);
        scoresButton.addActionListener(rootFrame);
        controlsButton.addActionListener(rootFrame);
        optionsButton.addActionListener(rootFrame);
        this.add(playButton);
        this.add(scoresButton);
        this.add(controlsButton);
        this.add(optionsButton);
    }
}
