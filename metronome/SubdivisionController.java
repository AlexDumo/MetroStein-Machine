package metronome;

import java.util.ArrayList;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         A SubdivisionController is a specialized MetronomeController meant only for use in
 *         subdivisions.
 */
public class SubdivisionController extends MetronomeController
{

  /**
   * Default constructor for a SubdivisionController.
   */
  public SubdivisionController()
  {
    super(true);
    setTimeSignature(TimeSignature.getTimeSignature(2, 4));
  }

  /**
   * Sets the time signature and puts the beats to the appropriate ones for subdivisions. Must be a
   * supported time signature, otherwise 4/4. Ignores null input.
   * 
   * @param timeSignature
   *          the timeSignature to set
   */
  @Override
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    if(timeSignature == null)
      return;
    
    stop();
    ArrayList<Integer> clickTypes = getClickTypes();
    super.setTimeSignature(timeSignature);
    clickTypes.clear();
    clickTypes.ensureCapacity(timeSignature.getNumerator());

    for (int i = 0; i < timeSignature.getNumerator(); i++)
      clickTypes.add(i, ClickMachine.CLICK_SUBDIVISION);
    clickTypes.set(0, -1); // sets the first beat to silent
    currentBeat = 1;
  }

  /**
   * Starts the metronome.
   * 
   * @param initialClick
   *          Whether or not the subdivision should have an initial click
   */
  @Override
  public void start(final boolean initialClick)
  {
    super.start(initialClick);
  }

  // Overrides the click
  @Override
  public void handleTick(final int millis)
  {
    clicker.click(getClickTypes().get(currentBeat - 1));

    notifyFrequentObservers();

    // Stop the subdivision after every measure.
    if (super.currentBeat >= getTimeSignature().getNumerator())
      stop();

    currentBeat++;

    // Reset to the start of the measure
//    if (currentBeat > getTimeSignature().getNumerator())
//      currentBeat = 1;
  }
}
