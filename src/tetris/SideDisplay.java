package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SideDisplay extends JPanel {
    
    private static final double marginMultiplier = (double)1/64;
    
    private int xMargin;
    
    private int width;
    
    private Image jBlockImage, lBlockImage, sBlockImage, zBlockImage,
                         oBlockImage, iBlockImage, tBlockImage;
    
    private JLabel score, level, lines;
    
    private Dimension dimen;
    private Image holdImage;
    private ArrayList<Image> queueImages;
    
    public SideDisplay( int inWidth ) {
        dimen = new Dimension( inWidth, (int)(inWidth*1.25) );
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setPreferredSize(dimen);
        this.setLayout(null);
        
        xMargin = (int)((double)dimen.getWidth() * marginMultiplier);
        width = (int) dimen.getWidth()/2 - (int) xMargin*2;
        
        score = new JLabel();
        level = new JLabel();
        lines = new JLabel();
        this.add(score);
        this.add(level);
        this.add(lines);
        score.setSize( (int)(4.0/3.0*(double)inWidth) ,inWidth/3);
        level.setSize((int)(4.0/3.0*(double)inWidth),inWidth/3);
        lines.setSize((int)(4.0/3.0*(double)inWidth),inWidth/3);
        Font f = new Font("Times New Roman", Font.PLAIN, (int)(0.11428*(double)inWidth) );
        score.setFont( f );
        level.setFont( f );
        lines.setFont( f );
        int y = (int) ( dimen.getHeight() * (double)1/3 );
        level.setLocation( xMargin*3, y );
        lines.setLocation( xMargin*3, y + (int)(0.2*(double)inWidth) );
        score.setLocation( xMargin*3, y + (int)(0.2*(double)inWidth)*2 );
        
        this.setVariables();
        
        try {
        	Path imagePath = TetrisInstance.getImageFolderPath();
            jBlockImage = ImageIO.read( new File(imagePath.resolve("JBlock.png").toString() ) );
            lBlockImage = ImageIO.read( new File(imagePath.resolve("LBlock.png").toString() ) );
            sBlockImage = ImageIO.read( new File(imagePath.resolve("SBlock.png").toString() ) );
            zBlockImage = ImageIO.read( new File(imagePath.resolve("ZBlock.png").toString() ) );
            oBlockImage = ImageIO.read( new File(imagePath.resolve("OBlock.png").toString() ) );
            iBlockImage = ImageIO.read( new File(imagePath.resolve("IBlock.png").toString() ) );
            tBlockImage = ImageIO.read( new File(imagePath.resolve("TBlock.png").toString() ) );
        }
        catch( Exception e ) {
            System.out.println("Error");
        }
    }
    
    public int getWidth() { return (int)dimen.getWidth(); }
    public int getHeight() { return (int)dimen.getHeight(); }
    
    public void setVariables() { queueImages = new ArrayList<Image>(); }
    
    public ArrayList<Image> getImageQueue() { return queueImages; }
    
    public void updateScore( int inScore ) {
        score.setText("Score: " + inScore);
    }
    public void updateLevel( int inLevel ) {
        level.setText("Level: " + inLevel );
    }
    public void updateLines( int inLines ) {
        lines.setText("Lines: " + inLines );
    }
    
    public void addToImageQueue( int block ) {
        queueImages.add( getImage(block) );
    }
    
    public void removeFromQueue() { queueImages.remove(0); }
    
    public void updateHoldImage( int block ) {
        holdImage = getImage( block );
    }
    
    public void paint( Graphics g ) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        int height;
        
        if( holdImage != null ) {
            height = (int) ( ( (double)width / (double)holdImage.getWidth(null) ) * (double) holdImage.getHeight(null) );
            g2d.drawImage( holdImage, xMargin, xMargin, width, height, null );
        }
        
        int y = xMargin;
        for( int i = 0; i < queueImages.size(); i++ ) {
            Image tempImage = queueImages.get(i);
            height = (int) ( ( (double)width / (double)tempImage.getWidth(null) ) * (double) tempImage.getHeight(null) );
            g2d.drawImage( tempImage, (int)dimen.getWidth()/2 + xMargin, y, width, height, null );
            y += height;
        }
    }
    
    public Image getImage( int blockType ) {
        switch( blockType ) {
            case TetrisInstance.J_BLOCK:
                return jBlockImage;
            case TetrisInstance.L_BLOCK:
                return lBlockImage;
            case TetrisInstance.S_BLOCK:
                return sBlockImage;
            case TetrisInstance.Z_BLOCK:
                return zBlockImage;
            case TetrisInstance.O_BLOCK:
                return oBlockImage;
            case TetrisInstance.I_BLOCK:
                return iBlockImage;
            case TetrisInstance.T_BLOCK:
                return tBlockImage;
            default:
                return null;
        }
    }
}
