package gui;

import javax.swing.*;
import java.awt.event.*;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class TapTempoButton extends JButton
{
  private long lastTapTime;
  private int tapCount;
  private Timer timer;

  public TapTempoButton(String label)
  {
    super(label);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        long currentTime = System.currentTimeMillis();
//        long currentTime = System.currentTimeMillis();
//        if (tapCount == 0 || currentTime - lastTapTime > 2000)
//        {
//          // If this is the first tap or the previous taps were too far apart,
//          // reset the tap count and start a new timer.
//          tapCount = 1;
//          timer = new Timer(2000, new ActionListener()
//          {
//            public void actionPerformed(ActionEvent e)
//            {
//              // The timer has elapsed, so calculate the tempo and reset the tap count.
//              double tempo = (double) tapCount / 2.0 * 60.0
//                  / ((double) System.currentTimeMillis() - (double) lastTapTime) * 1000.0;
//              String actionCommand = "Tempo: " + tempo + " BPM";
//              setActionCommand(actionCommand);
//              tapCount = 0;
//            }
//          });
//          timer.setRepeats(false);
//          timer.start();
//        }
//        else
//        {
//          // Add the current tap to the count and restart the timer.
//          tapCount++;
//          timer.restart();
//        }
//        lastTapTime = currentTime;
      }
    });
  }
}
