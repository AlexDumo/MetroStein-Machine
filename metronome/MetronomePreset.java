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
   * Sets the time signature and changes clicks to default. Null values ignored
   * 
   * @param timeSignature
   *          the timeSignature to set
   */
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    if(timeSignature == null)
      return;

    clickTypes.clear();
    clickTypes.ensureCapacity(timeSignature.getNumerator());

    for (int i = 0; i < timeSignature.getNumerator(); i++)
      clickTypes.add(i, Constants.DEFAULT_CLICK);
    clickTypes.set(0, 0);
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
   * already has a click type, it will overwrite it. If there is an invalid click type, it will set
   * to the default click (1). If there is an invalid beat number, the method returns immediately.
   * 
   * @param beatNumber
   *          The beat to change the click emphasis on. Invalid numbers are ignored.
   * @param clickToUse
   *          The click to use [-1 - 3] on the beat where -1 is silence. Default click is 1.
   */
  public void setClickType(final int beatNumber, final int clickToUse)
  {
    // Invalid beat
    if (beatNumber < 1 || beatNumber > timeSignature.getNumerator())
      return;

    if (clickToUse < ClickMachine.CLICK_MIN || clickToUse > ClickMachine.CLICK_MAX)
      clickTypes.set(beatNumber - 1, 1); // Invalid click number
    else
      clickTypes.set(beatNumber - 1, clickToUse);
  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid input, it will reset to
   * the default click (1). If there is not enough in the array to populate the current time
   * signature, it will fill the rest with the default click. If there is too much in the array, any
   * information past the time signature numerator length will be ignored.
   * 
   * @param typeArray
   *          The ArrayList<Integer> of the instructions. Invalid beat numbers are ignored. Invalid
   *          click types are set to the default.
   */
  public void setClickTypes(final ArrayList<Integer> typeArray)
  {
    if(typeArray == null)
      return;
    
    Integer cur;
    for (int i = 0; i < timeSignature.getNumerator(); i++)
    {
      if (typeArray.size() <= i)
        cur = ClickMachine.CLICK_DEFAULT;
      else
        cur = typeArray.get(i);
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
      clickTypes.add(ClickMachine.CLICK_DEFAULT);

    clickTypes.set(0, ClickMachine.CLICK_ACCENT);
  }

}
