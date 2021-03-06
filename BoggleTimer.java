//Daniel Alpert

public class BoggleTimer extends Thread {
  
  public int numSecs = 120;
  
  Interactions w;
  public BoggleTimer(Interactions window)
  {
    w = window;
  }
  
  public int getTimeLeft()
  {
    return numSecs;
  }
  
  public void run()
  {
    while (numSecs > 0)
    {
      try{
        Thread.sleep(1000);
      } 
      catch(InterruptedException e)
      {
        
      }
      numSecs--;
      w.updateTime(numSecs);
    }
    //call function stop when the timer hits zero
    w.stop();
  }
  
}