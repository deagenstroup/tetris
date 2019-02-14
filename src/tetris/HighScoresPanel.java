package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScoresPanel extends JPanel {
    
    private final double heightRatio = 0.15;
    
    private Path scoresFilePath;
    
    private Dimension dimen;
    private MainFrame rootFrame;
    
    private ArrayList<String> highscores;
    
    public HighScoresPanel( int inWidth, MainFrame inRootFrame ) {
        rootFrame = inRootFrame;
        dimen = new Dimension( inWidth, (int)((double)inWidth*heightRatio*11) );
        
        //rootFrame.setSize( inWidth + 50, (int)((double)inWidth*heightRatio*11) + 50 ); 
        
        this.setBorder( BorderFactory.createLineBorder(Color.black) );
        this.setPreferredSize(dimen);
        this.setLayout( new GridLayout( 11, 1) );
        
        highscores = new ArrayList<>();
        
        scoresFilePath = TetrisInstance.getGameFolderPath().resolve("highscores.txt");
        
        this.readHighScores();
        
        this.addLabels();
        
        JButton jb = new JButton("Back");
        jb.addActionListener(rootFrame);
        this.add( jb );
    }
    
    public void readHighScores() {
        try {
            FileReader fr = new FileReader(scoresFilePath.toString());
            BufferedReader br = new BufferedReader(fr);
            
            String temp;
            int i = 1;
            while( (temp = br.readLine()) != null ) {
                highscores.add( "" + i + ". " + temp );
                i++;
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println("ERROR: reading from highscores list, code: " + e.getCause());
        }
    }
    
    public void addLabels() {
        for( String str : highscores ) {
            JLabel tempLabel = new JLabel(str);
            tempLabel.setFont( new Font("Times New Roman", Font.PLAIN,(int)(dimen.getWidth()*(26.0/400.0)) ) );
            this.add( tempLabel );
        }
    }
    
}
