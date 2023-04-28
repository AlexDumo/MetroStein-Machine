package gui;

import javax.swing.*;

import resources.Constants;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class TapTempoButton extends JButton
{
  // private long lastTapTime;
  // private int tapCount;
  // private Timer timer;
  //
  // public TapTempoButton(String label)
  // {
  // super(label);
  // tapCount = 0;
  // addActionListener(new ActionListener()
  // {
  // public void actionPerformed(ActionEvent e)
  // {
  // long currentTime = System.currentTimeMillis();
  // // if (tapCount == 0 || currentTime - lastTapTime > 2000)
  // if (tapCount == 0)
  // {
  // // If this is the first tap or the previous taps were too far apart,
  // // reset the tap count and start a new timer.
  // tapCount = 1;
  // timer = new Timer(2000, new ActionListener()
  // {
  // public void actionPerformed(ActionEvent e)
  // {
  // // The timer has elapsed, so calculate the tempo and reset the tap count.
  // double tempo = (double) tapCount / 2.0 * 60.0
  // / ((double) System.currentTimeMillis() - (double) lastTapTime) * 1000.0;
  // String actionCommand = "Tempo: " + tempo + " BPM";
  // setActionCommand(actionCommand);
  // tapCount = 0;
  // }
  // });
  // timer.setRepeats(false);
  // timer.start();
  // // }
  // // else
  // // {
  // // // Add the current tap to the count and restart the timer.
  // // tapCount++;
  // // timer.restart();
  // }
  // lastTapTime = currentTime;
  // }
  // });
  // }
  private LinkedList<Long> pressTimes;
  private Timer resetTimer;

  public TapTempoButton(final String label)
  {
    super(label);
    pressTimes = new LinkedList<Long>();
    resetTimer = new Timer(2000, new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        pressTimes.clear(); 
        setActionCommand("Tap");
      }
    });
    resetTimer.setRepeats(false);
    

    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {        
        // add the current time to the pressTimes list
        pressTimes.add(System.currentTimeMillis());
        if (pressTimes.size() > 5)
          pressTimes.remove(0);
        resetTimer.restart();

        if (pressTimes.size() > 1)
        {
          double tempoSum = 0, avgTempo = 0;
          for (int i = 0; i < pressTimes.size(); i++)
          {
            long currPressTime = pressTimes.get(i);
            if (i > 0)
            {
              long prevPressTime = pressTimes.get(i - 1);
              double currTempo = 60.0 / ((currPressTime - prevPressTime) / 1000.0);
              tempoSum += currTempo;
            }
            avgTempo = tempoSum / (pressTimes.size() - 1);

          }
          char del = Constants.DELIMITER;
          setActionCommand(String.format("%s%c%f%c", Constants.TEMPO_CHANGE, del, avgTempo, del));
//          System.out.println("\tLOG Tempo: " + avgTempo + " BPM");
        }
      }
    });
  }
}
