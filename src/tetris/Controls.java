package tetris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class Controls {
    public static final int COMMAND_MOVERIGHT = 100;
    public static final int COMMAND_MOVELEFT = 101;
    public static final int COMMAND_PHASE = 102;
    public static final int COMMAND_ROTATECLOCKWISE = 103;
    public static final int COMMAND_ROTATECOUNTERCLOCKWISE = 104;
    public static final int COMMAND_HOLD = 105;
    public static final int COMMAND_RESTARTGAME = 106;
    
    public String[] controlKeys;
    public int[] controlCommands;
    
    public int size;
    
    private Path controlsFilePath;
    
    public Controls( int inNum ) {
        controlKeys = new String[inNum];
        controlCommands = new int[inNum];
        size = inNum;
        controlsFilePath = TetrisInstance.getGameFolderPath().resolve("controls.txt");
    }
    
    public boolean readControlsFromFile() {
        try {
            FileReader fr = new FileReader(controlsFilePath.toString());
            BufferedReader br = new BufferedReader(fr);
            
            String temp;
            for( int i = 0; i < size; i++ ) {
                temp = br.readLine();
                controlCommands[i] = resolveStringCommand( temp.substring(0, temp.indexOf(" ")) );
                controlKeys[i] = temp.substring(temp.indexOf(" ")+1);
            }
            br.close();
        }
        catch( IOException e ) {
            System.out.println("ERROR: reading controls file");
            return false;
        }
        return true;
    }
    
    public boolean writeControlsToFile() {
        try {
            FileWriter fw = new FileWriter(controlsFilePath.toString() );
            PrintWriter pw = new PrintWriter(fw);
            
            for( int i = 0; i < size; i++ ) {
                pw.println(resolveStringCommand(controlCommands[i]) + " " + controlKeys[i]);
            }
            pw.close();
        }
        catch( IOException e ) {
            System.out.println("ERROR: reading controls file");
            return false;
        }
        return true;
    }
    
    public String getKey( int inCommand ) {
        for( int i = 0; i < size; i++ ) {
            if( inCommand == controlCommands[i] )
                return controlKeys[i];
        }
        return "null";
    }
    
    public int getCommand( String inKey ) {
        for( int i = 0; i < size; i++ ) {
            if( inKey.equals(controlKeys[i]) )
                return controlCommands[i];
        }
        return -1;
    }
    
    public int getLocation( int inCommand ) {
        for( int i = 0; i < size; i++ ) {
            if( inCommand == controlCommands[i] )
                return i;
        }
        return -1;
    }
    
    public void debug() {
        for( int i = 0; i < size; i++ ) {
            System.out.println("[" + controlCommands[i] + ", " + controlKeys[i] + "]");
        }
    }
    
    public static int resolveStringCommand( String s ) {
        if( s.equals("moveright") ) {
            return COMMAND_MOVERIGHT;
        } 
        else if( s.equals("moveleft") ) {
            return COMMAND_MOVELEFT;
        } 
        else if( s.equals("phase") ) {
            return COMMAND_PHASE;
        } 
        else if( s.equals("rotateclockwise") ) {
            return COMMAND_ROTATECLOCKWISE;
        } 
        else if( s.equals("rotatecounterclockwise") ) {
            return COMMAND_ROTATECOUNTERCLOCKWISE;
        } 
        else if( s.equals("hold") ) {
            return COMMAND_HOLD;
        } 
        else if( s.equals("restartgame") ) {
            return COMMAND_RESTARTGAME;
        }
        else {
            System.out.println("ERROR: resolving keys");
            return -1;
        }
    }
    public static String resolveStringCommand( int command ) {
        if( command == COMMAND_MOVERIGHT ) {
            return "moveright";
        } 
        else if( command == COMMAND_MOVELEFT ) {
            return "moveleft";
        } 
        else if( command == COMMAND_PHASE ) {
            return "phase";
        } 
        else if( command == COMMAND_ROTATECLOCKWISE ) {
            return "rotateclockwise";
        } 
        else if( command == COMMAND_ROTATECOUNTERCLOCKWISE ) {
            return "rotatecounterclockwise";
        } 
        else if( command == COMMAND_HOLD ) {
            return "hold";
        } 
        else if(command == COMMAND_RESTARTGAME ) {
            return "restartgame";
        }
        else {
            System.out.println("ERROR: resolving keys");
            return "null";
        }
    }
}
