package metronome;

import java.util.ArrayList;
import java.util.HashSet;

import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class SubdivisionController extends MetronomeController
{

  /**
   * 
   */
  public SubdivisionController()
  {
    super(true);
  }

  /**
   * Sets the time signature and puts the beats to the appropriate ones for subdivisions. Must be a
   * supported time signature, otherwise 4/4.
   * 
   * @param timeSignature
   *          the timeSignature to set
   */
  @Override
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    clickTypes.clear();
    clickTypes.ensureCapacity(timeSignature.getNumerator());

    for (int i = 0; i < timeSignature.getNumerator(); i++)
      clickTypes.add(i, ClickMachine.CLICK_SUBDIVISION);
    clickTypes.set(0, -1); // sets the first beat to silent
    this.timeSignature = timeSignature;
  }

  /**
   * Starts the metronome. Is always false as a subdivision
   */
  @Override
  public void start(boolean initialClick)
  {
    super.start(false);
  }

  // Overrides the click
  @Override
  public void handleTick(int millis)
  {
    // System.out.println("current beat " + currentBeat);
    clicker.click(clickTypes.get(currentBeat - 1));

    notifyFrequentObservers();

    // Stop the subdivision after every measure.
    if (super.currentBeat == timeSignature.getNumerator())
      this.stop();

    currentBeat++;

    // Reset to the start of the measure
    if (currentBeat > timeSignature.getNumerator())
      currentBeat = 1;
  }
}
