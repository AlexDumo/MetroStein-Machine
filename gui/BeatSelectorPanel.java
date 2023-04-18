package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;

import event.MetronomeListener;
import metronome.FrequentMetronomeObserver;
import metronome.MetronomeController;
import metronome.MetronomeObserver;
import metronome.MetronomeSubject;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class BeatSelectorPanel extends JPanel
    implements MetronomeListener, ActionListener, MetronomeObserver, FrequentMetronomeObserver
{
  private ArrayList<BeatSelector> beatSelectors;
  private Set<ActionListener> actionListeners;

  /**
   * Creates and initializes a new BeatSelectorPanel of the given time signature with a grid layout.
   */
  public BeatSelectorPanel(TimeSignature timeSignature)
  {
    super(new GridLayout());

    beatSelectors = new ArrayList<BeatSelector>();
    actionListeners = new HashSet<ActionListener>();

    // Initialize default time signature
    // updateBeatSelectors(TimeSignature.getDefaultTimeSignature());
  }

  /**
   * Updates the accent selectors for beats.
   */
  protected void updateBeatSelectors(TimeSignature newTimeSignature, ArrayList<Integer> beatTypes)
  {
//    System.out.println("Updating beat selectors " + newTimeSignature.toString());
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
   */
  private void generateButtons(int beats, ArrayList<Integer> beatTypes)
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
  public void handleTick(int millis)
  {
    // System.out.println("Tick " + millis);
  }

  /**
   * @param metronomeController
   *          the metronomeController to set
   */
  public void addActionListener(ActionListener actionListener)
  {
    actionListeners.add(actionListener);
    for (JButton cur : beatSelectors)
      cur.addActionListener(actionListener);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
//    System.out.printf("action performed %s\n", e.getActionCommand());
    // TODO Auto-generated method stub

  }

  @Override
  public void update(MetronomeSubject metronomeSubject)
  {
    MetronomeController metronomeController = (MetronomeController) metronomeSubject;
    updateBeatSelectors(metronomeController.getTimeSignature(),
        metronomeController.getClickTypes());
    // TODO Auto-generated method stub

  }

  @Override
  public void frequentUpdate(MetronomeSubject metronomeSubject)
  {
    MetronomeController metronomeController = (MetronomeController) metronomeSubject;
    int currentBeat = metronomeController.getCurrentBeat();
//     System.out.printf("Frequent update %d\n", currentBeat);
    beatSelectors.get(currentBeat - 1).setActive();
    // Sets the previous beat inactive.
    beatSelectors.get((currentBeat + metronomeController.getTimeSignature().getNumerator() - 2)
        % metronomeController.getTimeSignature().getNumerator()).setInactive();
  }

}
