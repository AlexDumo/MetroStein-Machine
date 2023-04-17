package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import event.MetronomeListener;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class BeatSelectorPanel extends JPanel implements MetronomeListener
{
  private ArrayList<JButton> beatButtons;
  private Set<ActionListener> actionListeners;

  /**
   * Creates and initializes a new BeatSelectorPanel of the given time signature with a grid layout.
   */
  public BeatSelectorPanel(TimeSignature timeSignature)
  {
    super(new GridLayout());

    beatButtons = new ArrayList<JButton>();
    actionListeners = new HashSet<ActionListener>();

    // Initialize default time signature
    updateBeatSelectors(TimeSignature.TIME_SIGNATURES[TimeSignature.DEFAULT_TIME_SIGNATURE_INDEX]);
  }

  /**
   * Updates the accent selectors for beats.
   */
  protected void updateBeatSelectors(TimeSignature newTimeSignature)
  {
    System.out.println("Updating beat selectors " + newTimeSignature.toString());
    beatButtons.clear();
    this.removeAll();
    this.revalidate();
    generateButtons(newTimeSignature.getNumerator());
    this.repaint();
  }

  /**
   * Generates an amount of buttons for the given amount of beats and adds them to the beatButtons
   * ArrayList, and adds them to this' JPanel.
   * 
   * @param beats
   *          the amount of beat buttons to add.
   */
  private void generateButtons(int beats)
  {
    int rows = 1 + (beats - 1) / 5;
    int cols = (beats % 5 == 0) ? 5 : 4;
    System.out.printf("Rows: %d\nColumns: %d\n", rows, cols);
    JPanel buttonPanel = new JPanel(new GridLayout(rows, cols));
    
    for (int i = 0; i < beats; i++)
    {
      BeatSelector curButton = new BeatSelector((Integer.toString(i + 1)));
//      curButton.setBackground(Constants.ACCENT_COLOR);
      beatButtons.add(i, curButton);
      buttonPanel.add(curButton);
      
      // Add the action listeners to each button
//      curButton
      for(ActionListener curListener : actionListeners)
        curButton.addActionListener(curListener);
      
    }
    
    this.add(buttonPanel);
  }

  @Override
  public void handleTick(int millis)
  {
    System.out.println("Tick " + millis);

  }

  /**
   * @param metronomeController
   *          the metronomeController to set
   */
  public void addActionListener(ActionListener actionListener)
  {
    actionListeners.add(actionListener);
  }

}
