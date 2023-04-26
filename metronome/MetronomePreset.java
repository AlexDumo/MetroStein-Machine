package metronome;

import java.util.ArrayList;

import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         An object to store data for a Metronome preset.
 */
public class MetronomePreset
{
  private double tempo;
  private Subdivision subdivision;
  private TimeSignature timeSignature;
  private ArrayList<Integer> clickTypes;

  /**
   * Default constructor which uses the default values defined by the Constants, TimeSignature, and
   * Subdivision classes and enums.
   */
  public MetronomePreset()
  {
    this(Constants.DEFAULT_TEMPO, Subdivision.None, TimeSignature.getDefaultTimeSignature(),
        new ArrayList<>());
    setClicksToDefault();
  }

  /**
   * Parameterized constructor.
   * 
   * @param tempo
   * @param subdivision
   * @param timeSignature
   * @param clickTypes
   */
  public MetronomePreset(final double tempo, final Subdivision subdivision,
      final TimeSignature timeSignature, final ArrayList<Integer> clickTypes)
  {
    super();
    this.tempo = tempo;
    this.subdivision = subdivision;
    this.timeSignature = timeSignature;
    this.clickTypes = clickTypes;
  }

  /**
   * @return the tempo
   */
  public double getTempo()
  {
    return tempo;
  }

  /**
   * @param tempo
   *          the tempo to set
   */
  public void setTempo(final double tempo)
  {
    this.tempo = tempo;
  }

  /**
   * @return the subdivision
   */
  public Subdivision getSubdivision()
  {
    return subdivision;
  }

  /**
   * @param subdivision
   *          the subdivision to set
   */
  public void setSubdivision(final Subdivision subdivision)
  {
    this.subdivision = subdivision;
  }

  /**
   * @return the timeSignature
   */
  public TimeSignature getTimeSignature()
  {
    return timeSignature;
  }

  /**
   * @param timeSignature
   *          the timeSignature to set
   */
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    this.timeSignature = timeSignature;
  }

  /**
   * @return the clickTypes
   */
  public ArrayList<Integer> getClickTypes()
  {
    return clickTypes;
  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid click type, it will set to
   * the default click (1). If there is an invalid beat number, the method returns immediately. 
   * 
   * @param beatNumber
   *          The beat to change the click emphasis on. Invalid numbers are ignored.
   * @param clickToUse
   *          The click to use [-1 - 3] on the beat where -1 is silence. Default click is 1.
   */
  public void setClickType(final int beatNumber, final int clickToUse)
  {
    // Invalid beat
    if (beatNumber < 0 || beatNumber > timeSignature.getNumerator())
      return;

    if (clickToUse < ClickMachine.CLICK_MIN || clickToUse > ClickMachine.CLICK_MAX)
      clickTypes.set(beatNumber - 1, 1); // Invalid click number
    else
      clickTypes.set(beatNumber - 1, clickToUse);

  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid input, it will reset to
   * the default click (1).
   * 
   * @param typeArray
   *          The ArrayList<Integer> of the instructions. Invalid beat numbers are ignored. Invalid
   *          click types are set to the default.
   */
  public void setClickTypes(final ArrayList<Integer> typeArray)
  {
    // Loops through the Arraylist
    // TODO this line is bad because it modifies a parameter
    typeArray.ensureCapacity(timeSignature.getNumerator()); // ensures array is long enough for
                                                            // current TimeSignature
    Integer cur;
    for (int i = 0; i < timeSignature.getNumerator(); i++)
    {
      cur = typeArray.get(i);
      if (cur == null)
        cur = ClickMachine.CLICK_DEFAULT;
      setClickType(i + 1, cur);
    }
  }

  /**
   * Sets every beat's click to the default, except the first beat which is set to ACCENT.
   */
  public void setClicksToDefault()
  {
    clickTypes.clear();
    clickTypes.ensureCapacity(timeSignature.getNumerator());
    for (int i = 0; i < timeSignature.getNumerator(); i++)
      clickTypes.set(i, ClickMachine.CLICK_DEFAULT);

    clickTypes.set(0, ClickMachine.CLICK_ACCENT);
  }

}
