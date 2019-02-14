package tetris;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.nio.file.Path;
import java.nio.file.Paths;
import static tetris.Controls.COMMAND_HOLD;
import static tetris.Controls.COMMAND_MOVELEFT;
import static tetris.Controls.COMMAND_MOVERIGHT;
import static tetris.Controls.COMMAND_PHASE;
import static tetris.Controls.COMMAND_RESTARTGAME;
import static tetris.Controls.COMMAND_ROTATECLOCKWISE;
import static tetris.Controls.COMMAND_ROTATECOUNTERCLOCKWISE;

public class TetrisInstance implements KeyEventDispatcher {
    
    public static final int J_BLOCK = 0;
    public static final int L_BLOCK = 1;
    public static final int S_BLOCK = 2;
    public static final int Z_BLOCK = 3;
    public static final int O_BLOCK = 4;
    public static final int I_BLOCK = 5;
    public static final int T_BLOCK = 6;
    
    public static final int[][] J_BLOCK_COORDS = { {5,21}, {6,21}, {7,21}, {5,22} };
    public static final int[][] L_BLOCK_COORDS = { {5,21}, {6,21}, {7,21}, {7,22} };
    public static final int[][] S_BLOCK_COORDS = { {5,21}, {6,21}, {6,22}, {7,22} };
    public static final int[][] Z_BLOCK_COORDS = { {5,22}, {6,22}, {6,21}, {7,21} };
    public static final int[][] O_BLOCK_COORDS = { {5,21}, {6,21}, {5,22}, {6,22} };
    public static final int[][] I_BLOCK_COORDS = { {5,21}, {5,22}, {5,23}, {5,24} };
    public static final int[][] T_BLOCK_COORDS = { {3,21}, {4,21}, {5,21}, {4,22} };
    
    private static String gameFolder = "./game_files";
    private static String imageFolder = gameFolder + "/" + "images";
    
    private MainFrame rootFrame;
    
    private Controls cp;
    
    private GridDisplay gridDisplay;
    private SideDisplay sideDisplay;
    
    private TGrid grid;
    private Block currentBlock;
    
    private boolean hasHold, 
                    
                    //if true, the loop controlling the placement of the current
                    //block will restart
                    blockLoopRestart, 
                    
                    //if true, the loop controlling the placement of the current
                    //block will be broken from, executing the statements after
                    //the loop
                    blockLoopBreak, 
            
                    //the main game loop will keep going while this is true
                    gameLoop,
                    
                    //if true, the loop will execute a sleep statement
                    wait;
    
    private int holdBlock, level, delayTime, linesCleared, score;
    
    public int getTotalWidth() { return gridDisplay.getWidth() + sideDisplay.getWidth(); }
    public int getTotalHeight() { return gridDisplay.getHeight(); }
    
    public Controls getControlCommands() { return cp; }
    
    //list of blocks which are up next to be played
    private ArrayList<Integer> blockQueue;
    
    public TetrisInstance( MainFrame inFrame, int inWidth ) {
        rootFrame = inFrame;
        
        grid = new TGrid( 10, 24, 20 );
        blockQueue = new ArrayList<Integer>();
        
        
        gridDisplay = new GridDisplay( inWidth, grid );
        sideDisplay = new SideDisplay( (int) ( (double)inWidth*(350.0/400.0) ) );
        gridDisplay.setBackground(Color.lightGray);
        sideDisplay.setBackground(Color.lightGray);
        rootFrame.add( gridDisplay );
        rootFrame.add( sideDisplay );
        
        cp = new Controls(7);
        cp.readControlsFromFile();
        //cp.debug();
        
        this.setVariables();
    }
    public void setVariables() {
        holdBlock = -1;
        level = 1;
        delayTime = 1000;
        linesCleared = 0;
        score = 0;
        blockQueue = new ArrayList<Integer>();
        sideDisplay.setVariables();
        for(int i = 0; i < 3; i++ ) {
            int block = generateRandomBlock();
            blockQueue.add(block);
            sideDisplay.addToImageQueue( block );
        }
    }
    
    public void breakFromGame() {
        this.breakFromOuterLoop();
        this.breakFromInnerLoop();
    }
    public void restartGame() {
        grid.setGrid();
        this.setVariables();
    }

    public void runGame() {
        try {
            this.executeGameLoop();
        }
        catch (InterruptedException e ) {
            System.out.println("ERROR: " + e.getMessage() );
        }
    }
    
    public void executeGameLoop() throws InterruptedException {
        gameLoop = true;
        while( gameLoop ) {
            this.updateInfoBox();
            hasHold = true;
            blockLoopRestart = false;
            blockLoopBreak = false;
            currentBlock = this.spawnFromQueue();
            
            do {
                blockLoopRestart = false;
                
                int placementScore = 100*(int)((double)level*1.25);
                while( !blockLoopBreak && !blockLoopRestart && currentBlock.moveDown() ) {
                    wait = true;
                    sideDisplay.repaint();
                    gridDisplay.repaint();
                    if( wait )
                        Thread.sleep(delayTime);
                    placementScore -= 5;
                }
                this.addScore(placementScore);
            } while( !blockLoopBreak && blockLoopRestart );
            int tempLinesCleared = grid.checkForClearedLines();
            linesCleared += tempLinesCleared;
            this.addScore(400*tempLinesCleared*(int)(level*1.25));
            if( linesCleared / 10 >= level ) 
                this.levelUp();
            if( grid.checkTopLine() )
                break;
        }
        JOptionPane.showMessageDialog( rootFrame, "Game Over\nFinal Score: " + score );
        rootFrame.recordScore(score);
        this.updateInfoBox();
    }
    public void restartInnerLoop() {
        blockLoopRestart = true;
    }
    public void breakFromInnerLoop() {
        blockLoopBreak = true;
    }
    public void breakFromOuterLoop() {
        gameLoop = false;
    }
    
    public void executeHold() {
        if( hasHold ) {
            if( holdBlock != -1 ) {
                int temp = holdBlock;
                holdBlock = currentBlock.getType();
                currentBlock.removeFromGrid();
                currentBlock = this.spawnBlock(temp);
            }
            else {
                holdBlock = currentBlock.getType();
                currentBlock.removeFromGrid();
                currentBlock = this.spawnFromQueue();
            }
            sideDisplay.updateHoldImage(holdBlock);
            sideDisplay.repaint();
            this.restartInnerLoop();
        }
        hasHold = false;
    }
    
    public void levelUp() {
        level++;
        int temp = 1000;
        for( int i = 1; i <= level; i++ ) {
            temp *= 0.75;
        }
        delayTime = temp;
        this.updateInfoBox();
    }
    
    public void addScore( int inScore ) { 
        score += inScore;
        this.updateInfoBox();
    }
    
    public Block spawnFromQueue() {
        Integer block = blockQueue.remove(0);
        int randomBlock = generateRandomBlock();
        blockQueue.add( randomBlock );
        sideDisplay.removeFromQueue();
        sideDisplay.addToImageQueue( randomBlock );
        return spawnBlock(block);
    }
    
    public Block spawnBlock( int block ) {
        switch (block) {
            case J_BLOCK:
                return new Block( J_BLOCK_COORDS, grid, block );
            case L_BLOCK:
                return new Block( L_BLOCK_COORDS, grid, block );
            case S_BLOCK:
                return new Block( S_BLOCK_COORDS, grid, block );
            case Z_BLOCK:
                return new Block( Z_BLOCK_COORDS, grid, block );
            case O_BLOCK:
                return new Block( O_BLOCK_COORDS, grid, block );
            case I_BLOCK:
                return new Block( I_BLOCK_COORDS, grid, block );
            case T_BLOCK:
                return new Block( T_BLOCK_COORDS, grid, block );
            default:
                return null;
        }
    }
    
    public static int generateRandomBlock() {
        return (int) (Math.random()*7);
    }
    
    public void updateInfoBox() {
        sideDisplay.updateLevel( level );
        sideDisplay.updateLines( linesCleared );
        sideDisplay.updateScore( score );
        sideDisplay.repaint();
    }
    
    public boolean dispatchKeyEvent( KeyEvent e ) {
        if( e.getID() == KeyEvent.KEY_PRESSED ) {
            int keyPressed = e.getKeyCode();
            String keyStr = KeyEvent.getKeyText(keyPressed);
            int command = cp.getCommand( keyStr );
            switch (command) {
                case COMMAND_MOVERIGHT:
                    currentBlock.moveRight();
                    break;
                case COMMAND_MOVELEFT:
                    currentBlock.moveLeft();
                    break;
                case COMMAND_PHASE:
                    currentBlock.phaseDown();
                    break;
                case COMMAND_ROTATECLOCKWISE:
                    currentBlock.rotate( Block.CLOCKWISE );
                    break;
                case COMMAND_ROTATECOUNTERCLOCKWISE:
                    currentBlock.rotate( Block.COUNTER_CLOCKWISE );
                    break;
                case COMMAND_HOLD:
                    this.executeHold();
                    break;
                case COMMAND_RESTARTGAME:
                    this.breakFromGame();
                    break;
            }
        }
        gridDisplay.repaint();
        return false;
    }
    
    public static Path getGameFolderPath() {
    	return Paths.get(gameFolder);
    }
    
    public static Path getImageFolderPath() {
    	return Paths.get(imageFolder);
    }
    
}
