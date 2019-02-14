/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Deagen
 */
public class Tetris {
    static TGrid grid;
    static GridDisplay gridDisplay;
    public static void main(String[] args) throws InterruptedException {
        /*
        JFrame window = new JFrame("Tetris");
        window.setVisible(true);
        window.setSize(600,600);
        window.setResizable(true);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setLayout( new FlowLayout() );
        
        grid = new TGrid( 10, 20 );
        gridDisplay = new GridDisplay( 200, grid );
        window.add( gridDisplay );
        
        int[][] coords = { {3,19}, {4,19}, {5,19}, {4,20} };
        Block tblock = new Block( coords, grid );
        
        grid.changePos( 2, 19, true);
        
        System.out.println( tblock.addToGrid() );
        gridDisplay.repaint();
        
        Thread.sleep(2000);
        
        System.out.println( tblock.moveLeft() );
        gridDisplay.repaint();
        
        /*
        Thread.sleep(2000);
        
        tblock.moveRight();
        gridDisplay.repaint();
        */
        
        /*
        //DO NOT DELETE
        //  basic game loop
        while( tblock.moveDown() ) {
            gridDisplay.repaint();
            Thread.sleep(800);
        }
        System.out.println("Block Would Solidify here");
        */
        
        //grid.changePos( 3, 3, true);
        //System.out.println( grid.areCoordsValid(coords) );\
        /*
        System.out.println( grid.isCoordValid( 2, 5 ) );
        System.out.println( grid.isCoordValid( 3, 3 ) );
        System.out.println( grid.isCoordValid( 12, 8 ) );
        System.out.println( grid.isCoordValid( 9, 40 ) );
        */
        /*
        int[] center = tblock.calculateCenter();
        System.out.println(center[0]);
        System.out.println(center[1]);
        */
        
        /*
        System.out.println( Block.roundDouble( 5.6 ) );
        System.out.println( Block.roundDouble( 1.3 ) );
        System.out.println( Block.roundDouble( 20.5 ) );
        System.out.println( Block.roundDouble( 0.3 ) );
        System.out.println( Block.roundDouble( 0.6 ) );
        */
        
        /*
        System.out.println( grid.areCoordsOccupied(coords) );
        System.out.println( grid.getPos( 3, 3) );
        System.out.println( grid.getPos( 4, 3) );
        System.out.println( grid.getPos( 5, 3) );
        System.out.println( grid.getPos( 4, 4) );
        */
        //grid.changePos( 5, 3, true );
        /*
        while( z.getY() > 2 ) {
            if( z.step() )
                System.out.println("success");
            else
                System.out.println("error");
            gridDisplay.repaint();
            Thread.sleep(100);
        }
        */
    }
    
}
