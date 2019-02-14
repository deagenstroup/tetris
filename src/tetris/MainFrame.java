package tetris;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame implements ActionListener {
    
    public static void main(String[] args) {
        (new MainFrame()).menu();
    }
    
    private int width;
    
    Menu menu;
    private boolean launchGame = false, 
                    highscoresMenu = false, 
                    controlsMenu = false,
                    optionsMenu = false,
                    mainMenu = false;
    
    private TetrisInstance tetrisInstance;
    
    public MainFrame() {
        super("Tetris");
        this.setVisible(true);
        
        width = Options.readFromFile();
        //width = 500;
        this.setSize(width, (int)((double)width*(900.0/950.0)) );
        
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout( new GridBagLayout() );
        this.getContentPane().setBackground(Color.black);
    }
    
    public void menu() {
        this.addMenu();
        while( true ) {
            System.out.println("");
            if( launchGame ) {
                launchGame();
                this.addMenu();
            }
            else if( highscoresMenu ) {
                highScores();
            }
            else if( controlsMenu ) {
                addControlsMenu();
            }
            else if( optionsMenu ) {
                addOptionsMenu();
            }
            else if( mainMenu ) {
                this.addMenu();
            }
        }
    }
    
    public void launchGame() {
        this.getContentPane().removeAll();
        TetrisInstance game = new TetrisInstance(this, (int)((double)width*(400.0/950.0)) );
        System.out.println("width: " + game.getTotalWidth() + " height: " + game.getTotalHeight() );
        //this.setSize( game.getTotalWidth() + 50, game.getTotalHeight() + 50 );
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher( game );
        try {
            game.executeGameLoop();
        }
        catch (InterruptedException a ) {
            System.out.println("ERROR: " + a.getMessage() );
        }
        launchGame = false;
    }
    public void highScores() {
        this.getContentPane().removeAll();
        this.add( new HighScoresPanel((int)((double)width*(400.0/950.0)), this) );
        highscoresMenu = false;
        this.revalidate();
        this.repaint();
    }
    public void addControlsMenu() {
        this.getContentPane().removeAll();
        this.add( new ControlsPanel((int)((double)width*(500.0/950.0)), this) );
        controlsMenu = false;
        this.revalidate();
        this.repaint();
    }
    public void addOptionsMenu() {
        this.getContentPane().removeAll();
        this.add( new OptionsPanel((int)((double)width*(500.0/950.0)), this) );
        optionsMenu = false;
        this.revalidate();
        this.repaint();
    }
    
    public void addMenu() {
        this.getContentPane().removeAll();
        menu = new Menu( this, (int)((double)width*(300.0/950.0)) );
        this.add(menu);
        mainMenu = false;
        this.revalidate();
        this.repaint();
    }
    
    
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        if( buttonName.equals("Play") ) {
            launchGame = true;
            System.out.println("test");
        }
        else if( buttonName.equals("High Scores") ) {
            highscoresMenu = true;
        }
        else if( buttonName.equals("Back") ) {
            mainMenu = true;
        }
        else if( buttonName.equals("Controls") ) {
            controlsMenu = true;
        }
        else if( buttonName.equals("Options") ) {
            optionsMenu = true;
            System.out.println("You have clicked options.");
        }
    }
    
    public void recordScore( int score ) {
        String name;
        name = JOptionPane.showInputDialog("Enter Name: ");
        
        if( name.equals("") )
            name = "null";
        
        try {
            FileReader fr = new FileReader("highscores.txt");
            BufferedReader br = new BufferedReader(fr);
            
            ArrayList<String> highscores = new ArrayList<String>();
            int tempScore, line = 0, previousScore = Integer.MAX_VALUE, placementLine = -1;
            String temp;
            
            while( (temp = br.readLine()) != null ) {
                highscores.add(temp);
                
                tempScore = Integer.parseInt( temp.substring(temp.indexOf(" ")+1) );
                
                if( score > tempScore && score < previousScore && placementLine == -1 )
                    placementLine = line;
                
                previousScore = tempScore;
                line++;
            }
            br.close();
            
            if( placementLine == -1 ) {
                if( highscores.size() < 10 )
                    highscores.add( (name + " " + score) );
            }
            else
                highscores.add( placementLine, (name + " " + score) );
            
            FileWriter fw = new FileWriter("highscores.txt");
            PrintWriter pw = new PrintWriter(fw);
            for( int i = 0; i < 10; i++ ) {
                try {
                    pw.println(highscores.remove(0));
                }
                catch( Exception e ) { break; }
            }
            pw.close();
        }
        catch( IOException e ) {
            System.out.println("ERROR: recording score");
        }
    }
}
