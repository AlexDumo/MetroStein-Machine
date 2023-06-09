package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import metronome.FrequentMetronomeObserver;
import metronome.MetronomeController;
import metronome.MetronomeListener;
import metronome.MetronomeObserver;
import metronome.MetronomeSubject;
import metronome.TimeSignature;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public class BeatSelectorPanel extends JPanel
    implements MetronomeListener, MetronomeObserver, FrequentMetronomeObserver
{
  private ArrayList<BeatSelector> beatSelectors;
  private Set<ActionListener> actionListeners;

  /**
   * Creates and initializes a new BeatSelectorPanel with a grid layout.
   */
  public BeatSelectorPanel()
  {
    super(new GridLayout());
    
    beatSelectors = new ArrayList<BeatSelector>();
    actionListeners = new HashSet<ActionListener>();
  }

  /**
   * Updates the accent selectors for beats.
   * 
   * @param newTimeSignature
   * @param beatTypes
   */
  protected void updateBeatSelectors(final TimeSignature newTimeSignature,
      final ArrayList<Integer> beatTypes)
  {
    // System.out.println("Updating beat selectors " + newTimeSignature.toString());
    beatSelectors.clear();
    this.removeAll();
    this.revalidate();
    generateButtons(newTimeSignature.getNumerator(), beatTypes);
    this.repaint();
  }

  /**
   * Generates an amount of buttons for the given amount of beats and adds them to the beatButtons
   * ArrayList, and adds them to this' JPanel.
   * 
   * @param beats
   *          the amount of beat buttons to add.
   * @param beatTypes
   *          an ArrayList<Integer> with the types of beats to add.
   */
  private void generateButtons(final int beats, final ArrayList<Integer> beatTypes)
  {
    int rows = 1 + (beats - 1) / 5;
    int cols = (beats % 5 == 0) ? 5 : 4;
    // System.out.printf("Rows: %d\nColumns: %d\n", rows, cols);
    JPanel buttonPanel = new JPanel(new GridLayout(rows, cols));

    for (int i = 0; i < beats; i++)
    {
      BeatSelector curButton = new BeatSelector(i + 1,
          BeatSelector.clickTypeToState(beatTypes.get(i)));
      // curButton.setBackground(Constants.ACCENT_COLOR);
      beatSelectors.add(i, curButton);
      buttonPanel.add(curButton);

      // Add the action listeners to each button
      for (ActionListener curListener : actionListeners)
        curButton.addActionListener(curListener);

    }

    this.add(buttonPanel);
  }

  @Override
  public void handleTick(final int millis)
  {
    // Does nothing
  }

  /**
   * @param actionListener
   *          the actionListener to set
   */
  public void addActionListener(final ActionListener actionListener)
  {
    actionListeners.add(actionListener);
    for (JButton cur : beatSelectors)
      cur.addActionListener(actionListener);
  }

  @Override
  public void update(final MetronomeSubject metronomeSubject)
  {
    MetronomeController metronomeController = (MetronomeController) metronomeSubject;
    updateBeatSelectors(metronomeController.getTimeSignature(),
        metronomeController.getClickTypes());

  }

  @Override
  public void frequentUpdate(final MetronomeSubject metronomeSubject)
  {
    MetronomeController metronomeController = (MetronomeController) metronomeSubject;

    if (metronomeController.isRunning()) // Sets the current beatSelector active and turns off the
                                         // previous one
    {
      int currentBeat = metronomeController.getCurrentBeat();
      beatSelectors.get(currentBeat - 1).setActive();
      beatSelectors.get((currentBeat + metronomeController.getTimeSignature().getNumerator() - 2)
          % metronomeController.getTimeSignature().getNumerator()).setInactive();
    }
    else // Sets all the selectors to inactive if it is off.
    {
      for (BeatSelector cur : beatSelectors)
        cur.setInactive();
    }
  }

}
