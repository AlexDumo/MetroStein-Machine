package metronome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import event.Metronome;
import event.MetronomeListener;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         This class controls the ClickMachines by telling which one to activate and when. It has
 *         four types of clicks at its disposal.
 */
public class MetronomeController implements ActionListener, MetronomeListener
{
  private Metronome met;
  private boolean metIsRunning;
  private int tempo;
  private ClickMachine clicker;
  private TimeSignature timeSignature;
  private Map<Integer, Integer> clickTypes;
  private int currentBeat;

  /**
   * Default constructor. Sets tempo to 120bpm and time signature to 4/4.
   */
  public MetronomeController()
  {
    tempo = Constants.DEFAULT_TEMPO;
    metIsRunning = false;
    clicker = new ClickMachine();
    met = new Metronome(bpmToMilli(tempo), true);
    met.addListener(this);
    
    timeSignature = Constants.TIME_SIGNATURES[Constants.DEFAULT_TIME_SIGNATURE_INDEX];
    currentBeat = 1;
    clickTypes = new HashMap<>();

    clickTypes.put(1, 0);
    clickTypes.put(3, 2);
    clickTypes.put(-12, 123);
    clickTypes.put(4, -1);
  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid input, it will reset to
   * the default click (1).
   * 
   * @param beatNumber
   *          The beat to change the click emphasis on. Invalid numbers are ignored
   * @param clickToUse
   *          The click to use [-1 - 3] on the beat where -1 is silence. Default click is 1.
   */
  public void setClickType(final int beatNumber, final int clickToUse)
  {
    // Invalid beat
    if (beatNumber < 0 || beatNumber > timeSignature.getNumerator())
      return;

    // Remove current beat from the set if it exists
    // if (clickTypes.containsKey(beatNumber))
    // clickTypes.remove(beatNumber);

    if (clickToUse < ClickMachine.CLICK_MIN || clickToUse > ClickMachine.CLICK_MAX)
      clickTypes.put(beatNumber, 1); // Invalid number
    else
      clickTypes.put(beatNumber, clickToUse);
  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid input, it will reset to
   * the default click (1).
   * 
   * @param typeMap
   *          The Map<beatNumber, clickToUse> of the instructions. Invalid beat numbers are ignored.
   *          Invalid click types are set to the default.
   */
  public void setClickType(final Map<Integer, Integer> typeMap)
  {
    // Loops through the map
    for (int beatNumber : typeMap.keySet())
      for (int clickType : typeMap.values())
        setClickType(beatNumber, clickType);
  }

  /**
   * @return the tempo
   */
  public int getTempo()
  {
    return tempo;
  }

  /**
   * Currently only works when the metronome is off.
   * 
   * @param tempo
   *          the tempo to set
   */
  public void setTempo(int tempo)
  {
    this.tempo = tempo;
    System.out.println("Tempo " + tempo);
    
    met.stop();
    met = new Metronome(bpmToMilli(tempo));
    met.addListener(this);
    
    // Resumes the met if it was already going.
    if(metIsRunning)
      met.start();
  }

  /**
   * Sets the time signature.
   * 
   * @param numerator
   *          the numerator to use
   * @param denominator
   *          the denominator to use
   */
  public void setTimeSignature(final int numerator, final int denominator)
  {
    setTimeSignature(new TimeSignature(numerator, denominator));
  }

  /**
   * @param timeSignature the timeSignature to set
   */
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    this.timeSignature = timeSignature;
  }

  /**
   * @return an int[] with the time signature numerator [0] and the denominator [1].
   */
  public TimeSignature getTimeSignature()
  {
    return timeSignature;
  }

  /**
   * Action performed override.
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // Starts and stops the metronome
    switch (e.getActionCommand())
    {
      case Constants.START:
        clicker.click(0);
        currentBeat++;
        met.start();
        metIsRunning = true;
        break;
      case Constants.STOP:
        met.stop();
        metIsRunning = false;
        currentBeat = 1;
        break;
      default:
        break;

    }
  }

  // Overrides the click
  @Override
  public void handleTick(int millis)
  {
    // Reset to the start of the measure
    if (currentBeat > timeSignature.getNumerator())
      currentBeat = 1;

    clicker.click(clickTypes.containsKey(currentBeat) ? clickTypes.get(currentBeat) : 1);

    currentBeat++;
  }

  /*
   * Converts bpm (beats per minute) to milliseconds per tick.
   * 
   * @param bpm The desired bpm
   * 
   * @return The milliseconds per click
   */
  private static int bpmToMilli(int bpm)
  {
    int output = (int) ((60.0 / bpm) * 1000);
    return output;
  }
}
