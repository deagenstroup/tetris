package tetris;

public class TGrid {
    
    private int[][] grid;
    private int visibleHeight;
    
    public TGrid( int width, int height, int inVisibleHeight ) {
        grid = new int[width][height];
        visibleHeight = inVisibleHeight;
        this.setGrid();
    }
    public void setGrid() {
        for( int x = 0; x < grid.length; x++ ) {
            for( int y = 0; y < grid[0].length; y++ ) {
                grid[x][y] = -1;
            }
        }
    }
    
    public int getHeight() { return grid[0].length; }
    public int getWidth() { return grid.length; }
    public int getVisibleHeight() { return visibleHeight; }
    
    public int getPos( int x, int y ) { return grid[x-1][y-1]; }
    public void changePos( int x, int y, int inBlock ) { grid[x-1][y-1] = inBlock; }
    
    public boolean isPosOccupied( int x, int y ) { return ( grid[x-1][y-1] != -1 ); }
    
    //a coordinate is valid if it is unoccupied (-1) and within the bounds
    //of the grid
    public boolean isCoordValid( int x, int y ) {
        try {
            if( this.isPosOccupied( x, y ) )
                return false;
        }
        catch( Exception e ) {
            return false;
        }
        return true;
    }
    public boolean areCoordsValid( int[][] inCoords ) {
        for( int[] pair : inCoords ) {
            int x = pair[0];
            int y = pair[1];
            if( !this.isCoordValid( x, y ) )
                return false;
        }
        return true;
    }
    
    public int checkForClearedLines() {
        int numOfLinesCleared = 0;
        for( int y = 1; y < visibleHeight; y++ ) {
            boolean isClear = true;
            for( int x = 1; x <= this.getWidth(); x++ ) {
                if( !this.isPosOccupied( x, y ) ) {
                    isClear = false;
                }
            }
            if(isClear) {
                this.clearLine(y);
                numOfLinesCleared++;
                y--;
            }
        }
        return numOfLinesCleared;
    }
    
    public void clearLine( int lineY ) {
        for( int y = lineY; y < visibleHeight; y++ ) {
            for( int x = 1; x <= this.getWidth(); x++ ) {
                this.changePos( x, y, this.getPos(x,y+1) );
            }
        }
        for( int x = 1; x <= this.getWidth(); x++ ) {
            this.changePos( x, 20, -1 );
        }
    }
    
    //returns true if the first invisble line is occupied (the player loses)
    public boolean checkTopLine() {
        int topLine = this.getVisibleHeight() + 1;
        for( int x = 1; x <= this.getWidth(); x++ ) {
            if( this.isPosOccupied(x,topLine) )
                return true;
        }
        return false;
    }
}
