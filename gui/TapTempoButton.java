package gui;

import javax.swing.*;

import resources.Constants;

import java.awt.event.*;
import java.util.LinkedList;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public class TapTempoButton extends JButton
{
  private LinkedList<Long> pressTimes;
  private Timer resetTimer;

  /**
   * Constructs a TapTempoButton with the given label.
   * 
   * @param label
   */
  public TapTempoButton(final String label)
  {
    super(label);
    pressTimes = new LinkedList<Long>();
    resetTimer = new Timer(2000, new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        pressTimes.clear();
        setActionCommand("Tap");
      }
    });
    resetTimer.setRepeats(false);

    addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
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
          // System.out.println("\tLOG Tempo: " + avgTempo + " BPM");
        }
      }
    });
  }
}
