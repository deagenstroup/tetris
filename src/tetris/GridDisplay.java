package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.nio.file.Path;

public class GridDisplay extends JPanel {
    private Dimension dim;
    private TGrid grid;
    private int boxSideLength;
    private Image darkBlueSquare, orangeSquare, greenSquare, redSquare,
                  yellowSquare, blueSquare, purpleSquare;
    
    public GridDisplay( int w, TGrid g ) {
        grid = g;
        boxSideLength = w/g.getWidth();
        dim = new Dimension( w, boxSideLength*g.getVisibleHeight() );
        this.setPreferredSize(dim);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        try {
        	Path imagePath = TetrisInstance.getImageFolderPath();
            darkBlueSquare = ImageIO.read( new File(imagePath.resolve("darkbluesquare.jpg").toString() ) );
            orangeSquare = ImageIO.read( new File(imagePath.resolve("orangesquare.jpg").toString() ) );
            greenSquare = ImageIO.read( new File(imagePath.resolve("greensquare.jpg").toString() ) );
            redSquare = ImageIO.read( new File(imagePath.resolve("redsquare.jpg").toString() ) );
            yellowSquare = ImageIO.read( new File(imagePath.resolve("yellowsquare.jpg").toString() ) );
            blueSquare = ImageIO.read( new File(imagePath.resolve("bluesquare.jpg").toString() ) );
            purpleSquare = ImageIO.read( new File(imagePath.resolve("purplesquare.jpg").toString() ) );
        }
        catch( Exception e ) {
            System.out.println("Error");
        }
    }
    
    public int getWidth() { return (int)dim.getWidth(); }
    public int getHeight() { return (int)dim.getHeight(); }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for( int x = 1; x <= grid.getWidth(); x++ ) {
            for( int y = 1; y <= grid.getVisibleHeight(); y++ ) {
                Image img = this.getImage( grid.getPos(x,y) );
                if( grid.isPosOccupied(x,y) )
                    g2d.drawImage( img, (x-1)*boxSideLength, (grid.getVisibleHeight()-y)*boxSideLength, boxSideLength, boxSideLength, null );
                else 
                    g2d.drawRect( (x-1)*boxSideLength, (grid.getVisibleHeight()-y)*boxSideLength, boxSideLength, boxSideLength);
            }
        }
    }
    
    public Image getImage( int blockType ) {
        switch( blockType ) {
            case TetrisInstance.J_BLOCK:
                return darkBlueSquare;
            case TetrisInstance.L_BLOCK:
                return orangeSquare;
            case TetrisInstance.S_BLOCK:
                return greenSquare;
            case TetrisInstance.Z_BLOCK:
                return redSquare;
            case TetrisInstance.O_BLOCK:
                return yellowSquare;
            case TetrisInstance.I_BLOCK:
                return blueSquare;
            case TetrisInstance.T_BLOCK:
                return purpleSquare;
            default:
                return null;
        }
    }
}
