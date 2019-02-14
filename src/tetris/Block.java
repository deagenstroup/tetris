package tetris;

public class Block {
    
    public static final boolean CLOCKWISE = true;
    public static final boolean COUNTER_CLOCKWISE = false;
    
    private int type;
    private int[][] coords;
    TGrid grid;
    public Block( int[][] inCoords, TGrid inGrid, int inType ) {
        coords = inCoords;
        grid = inGrid;
        type = inType;
    }
    
    public int getType() { return type; }
    
    //returns true upon success
    public boolean addToGrid() {
        if( !grid.areCoordsValid( coords ) )
            return false;
        for( int[] pair : coords ) {
            int x = pair[0];
            int y = pair[1];
            grid.changePos(x, y, type);
        }
        return true;
    }
    //precondition: block is already in grid
    public Block removeFromGrid() {
        for( int[] pair : coords ) {
            int x = pair[0];
            int y = pair[1];
            grid.changePos(x, y, -1);
        }
        return this;
    }
    //moves block down as far as it can go down
    public void phaseDown() {
        while( this.moveDown() ) {}
    }
    
    //moves down one square in specified direction
    public boolean moveDown() {
        return this.move( 1 );
    }
    public boolean moveRight() {
        return this.move( 2 );
    }
    public boolean moveLeft() {
        return this.move( 3 );
    }
    private boolean move( int direction ) {
        this.removeFromGrid();
        int[][] possibleCoords = copyArray( coords );
        for( int[] pair: possibleCoords ) {
            if( direction == 1 )
                pair[1] -= 1;
            else if( direction == 2 )
                pair[0] += 1;
            else if( direction == 3)
                pair[0] -= 1;
        }
        if( !grid.areCoordsValid( possibleCoords ) ) {
            this.addToGrid();
            return false;
        }
        coords = possibleCoords;
        this.addToGrid();
        return true;
    }
    
    //returns x and y coordinate of the center of the block, rounded to the
    //nearest point
    public int[] calculateCenterPoint() {
        double xSum = 0;
        double ySum = 0;
        for( int[] pair: coords ) {
            int x = pair[0];
            int y = pair[1];
            xSum += x;
            ySum += y;
        }
        int[] centerCoord = new int[2];
        centerCoord[0] = roundDouble( xSum / 4 );
        centerCoord[1] = roundDouble( ySum / 4 );
        return centerCoord;
    }
    
    //rotates the block about its center if it is valid to do so
    public boolean rotate( boolean clockWise ) {
        this.removeFromGrid();
        int[][] possibleCoords = copyArray(coords);
        int[] center = this.calculateCenterPoint();
        for( int[] pair : possibleCoords ) {
            int[] newCoords;
            if( clockWise )
                newCoords = rotateAroundPoint( pair, center, clockWise );
            else
                newCoords = rotateAroundPoint( pair, center, clockWise );
            pair[0] = newCoords[0];
            pair[1] = newCoords[1];
        }
        if( grid.areCoordsValid( possibleCoords ) ) {
            coords = copyArray(possibleCoords);
            this.addToGrid();
            return true;
        }
        this.addToGrid();
        return false;
    }
    
    public static int[] rotateAroundPoint( int[] originalCoord, int[] center, boolean clockWise ) {
        int x = originalCoord[0];
        int y = originalCoord[1];
        double centerX = center[0];
        double centerY = center[1];
        
        double angle;
        if( clockWise )
            angle = Math.toRadians(-90);
        else
            angle = Math.toRadians(90);
        
        x -= centerX;
        y -= centerY;
        
        double newX = x;
        double newY = y;
        
        newX = x*Math.cos( angle ) - 
               y*Math.sin( angle );
        newY = y*Math.cos( angle ) +
               x*Math.sin( angle );
        
        newX += centerX;
        newY += centerY;
        
        int[] returnCoord = { roundDouble(newX), roundDouble(newY) };
        return returnCoord;
    }
    
    public String toString() {
        String str = "";
        for( int[] arr : coords ) {
            for( int x : arr ) {
                str += x + ","; 
            }            
            str += " ";
        }
        return str;
    }
    
    //rounds a double up or down to an int
    public static int roundDouble( double input ) {
        int wholeNums = (int) input;
        double decimals = input - wholeNums;
        if( decimals > 0.5 )
            return wholeNums + 1; 
        else
            return wholeNums;
    }
    public static int[][] copyArray( int[][] inArry ) {
        int[][] newArry = new int[inArry.length][inArry[0].length];
        for( int r = 0; r < inArry.length; r++ ) {
            for( int c = 0; c < inArry[0].length; c++ ) {
                newArry[r][c] = inArry[r][c];
            }
        }
        return newArry;
    }
}
