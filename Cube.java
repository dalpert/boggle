import java.util.ArrayList;

public class Cube {
  
  Cube[] dice = new Cube[16];
  private String letters;
  private int letterOnTop;
  
  //Constructor
  public Cube (String s) {
    letters = s;
    roll();
    
  }
  
  public char getTopLetter() {
    return letters.charAt(letterOnTop);
  }
  
  public String toString() {
    return letters;
  }
  
  private void roll() {
    letterOnTop = (int)(Math.random() * letters.length());
  }

}