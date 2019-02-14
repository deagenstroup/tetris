package tetris;

import javax.swing.JButton;

public class SpecialButton extends JButton {
    private int id;
    public SpecialButton( String str, int i ) {
        super(str);
        id = i;
    }
    public int getID() { return id; }
    public String toString() { return ""+id; };
}
