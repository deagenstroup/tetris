package tetris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;

public class Options {
	private static Path optionsFilePath;
	
    public static int readFromFile() {
    	optionsFilePath = TetrisInstance.getGameFolderPath().resolve("options.txt");
        int width = 300;
        try {
            FileReader fr = new FileReader(optionsFilePath.toString());
            BufferedReader br = new BufferedReader(fr);
            
            width = Integer.parseInt( br.readLine() );
            
            br.close();
        }
        catch( Exception e ) {
            System.out.println("ERROR: reading from options");
        }
        System.out.println("width: " + width );
        return width;
    }
    public static void writeToFile( int width ) {
        try {
            FileWriter fw = new FileWriter(optionsFilePath.toString());
            PrintWriter pw = new PrintWriter(fw);
            
            pw.println( width );
            
            pw.close();
        }
        catch( Exception e ) {
            System.out.println("ERROR: reading from options");
        }
    }
}
