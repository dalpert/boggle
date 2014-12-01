//Daniel Alpert

import java.awt.*;
import javax.swing.*;

public class DisplayBoard extends JFrame
{
 public DisplayBoard(Board board)
 {
  JButton[][] buttons = new JButton[4][4];
  
  for (int i = 0; i < 4; i++) {
    for (int j = 0; j < 4; j++) {
      buttons[i][j] = new JButton(new ImageIcon(board.get(i,j).getTopLetter()+".png"));
    }
  }
  
  
   setBounds(1000,0,350,365);
  Container contentPane = getContentPane();

  
  contentPane.setLayout(new GridLayout(4,4));
  //add all buttons
  for (int i = 0; i < buttons.length; i++) {
    for (int j = 0; j < buttons[0].length; j++) {
      contentPane.add(buttons[i][j]);
    }
  }

  show();
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 }
}