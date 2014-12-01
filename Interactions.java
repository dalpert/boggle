//Daniel Alpert

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class Interactions extends JFrame
{
  private Board board;
  JTextField input;
  JTextArea words;
  JTextArea score;
  JTextArea timer;
  JScrollPane pane;
  JTextPane tPane;
  BoggleTimer clock;
  private boolean canStillPlay = true;
  
  public boolean gameOver()
  {
    if (clock.getTimeLeft() <= 0)
      return true;
    return false;
  }
  
  public Interactions(Board b)
  {  
    board = b;
    setBounds(1000,400,240,400);
    Container contentPane = getContentPane();
    setResizable(false);
    tPane = new JTextPane();
    contentPane.setLayout(new FlowLayout());
    input = new JTextField(15);
    score = new JTextArea(4,4);
    timer = new JTextArea(4,12);
    //timer.setBackground(Color.BLACK);
    //timer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    score.append("Score:\n");
    contentPane.add(input);
    input.addActionListener(new Action());
    pane = new JScrollPane(tPane);
    pane.setBackground(Color.BLACK);
    //setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    pane.setPreferredSize(new Dimension(220, 260));
    pane.createVerticalScrollBar(); 
    contentPane.add(pane);
    contentPane.add(score);
    contentPane.add(timer);
    show();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private class Action implements ActionListener 
  {
    public void actionPerformed(ActionEvent a)
    {
      board.processBoard(a.getActionCommand());
      if (canStillPlay)
      {
      appendToPane(tPane, a.getActionCommand()+board.getMessage(), board.getColor());
      if (board.getBonus() != 0)
      {
        for (int i = 0; i < 20 - a.getActionCommand().length(); i++) {
          appendToPane(tPane," ",board.getColor());
        }
        appendToPane(tPane,""+board.getBonus(), board.getColor());
      }
      appendToPane(tPane,"\n", board.getColor());
      input.setText("");
      score.setText("");
      score.append("Score:\n");
      score.append(""+board.getScore());
      }
    }
  }
  
  private void appendToPane(JTextPane tp, String msg, Color c)
  {
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
    
    aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
    aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
    
    int len = tp.getDocument().getLength();
    tp.setCaretPosition(len);
    tp.setCharacterAttributes(aset, false);
    tp.replaceSelection(msg);
  }
  
  public void endGame()
  {
    
  }
  
  public void updateTime(int time)
  {
    timer.setText("");
    if ((time%60) < 10) 
    {
      timer.append(""+(time/60)+":0"+(time%60));
    }
    else 
    {
      timer.append(""+(time/60)+":"+(time%60));
    }
  }
  
  public void stop()
  {
    appendToPane(tPane,"GAME OVER",Color.BLUE);
    appendToPane(tPane,"\n",board.getColor());
    appendToPane(tPane,"FINAL SCORE IS ", Color.BLUE);
    appendToPane(tPane, ""+board.getScore(), Color.BLUE);
    canStillPlay = false;
    input.setEditable(false);
  }
}