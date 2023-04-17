package metronome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

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
public class MetronomeController implements ActionListener, MetronomeListener, FocusListener
{
  private Metronome met;
  private boolean metIsRunning;
  private int tempo;
  private ClickMachine clicker;
  private TimeSignature timeSignature;
  private ArrayList<Integer> clickTypesArrayList;
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

    clickTypesArrayList = new ArrayList<Integer>();
    setTimeSignature(TimeSignature.TIME_SIGNATURES[TimeSignature.DEFAULT_TIME_SIGNATURE_INDEX]);
    currentBeat = 1;
    clickTypesArrayList.set(0, 0);
    clickTypesArrayList.set(1, 987);
    clickTypesArrayList.set(2, 2);
    clickTypesArrayList.set(3, -1);
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

    if (clickToUse < ClickMachine.CLICK_MIN || clickToUse > ClickMachine.CLICK_MAX)
      clickTypesArrayList.add(beatNumber - 1, 1); // Invalid click number
    else
      clickTypesArrayList.add(beatNumber - 1, clickToUse);
  }

  /**
   * Sets the click emphasis on a given beat. Beats cannot have more than one click type, so if it
   * already has a click type, it will overwrite it. If there is an invalid input, it will reset to
   * the default click (1).
   * 
   * @param typeArray
   *          The ArrayList<Integer> of the instructions. Invalid beat numbers are ignored.
   *          Invalid click types are set to the default.
   */
  public void setClickTypes(final ArrayList<Integer> typeArray)
  {
    // Loops through the map
    for (int i = 0; i < typeArray.size(); i ++)
       setClickType(i + 1, typeArray.get(i));
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
    if (metIsRunning)
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
   * @param timeSignature
   *          the timeSignature to set
   */
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    clickTypesArrayList.clear();
    clickTypesArrayList.ensureCapacity(timeSignature.getNumerator());
    
    for(int i = 0; i < timeSignature.getNumerator(); i ++)
      clickTypesArrayList.add(i, Constants.DEFAULT_CLICK);
    clickTypesArrayList.set(0, 0);
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
   * Adds the given MetronomeListener to the owning Metronome.
   * 
   * @param metronomeListener the MetronomeListener to add
   */
  public void addMetronomeListener(MetronomeListener metronomeListener)
  {
    if(metronomeListener != null)
      met.addListener(metronomeListener);
  }
  
  // ---------- Override Methods ----------

  /**
   * Action performed override.
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    // Starts, and increments the metronome
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
      case Constants.INCREMENT:
        setTempo(tempo + 1);
        break;
      case Constants.DECREMENT:
        setTempo(tempo - 1);
        break;
      case Constants.METER_CHANGE:
        setTimeSignature((TimeSignature) ((JComboBox<TimeSignature>) e.getSource()).getSelectedItem());
        break;
      default:
        System.out.println(e.getActionCommand());
        break;

    }
  }
  
  public static int[] getBeatActionCommand(String ac)
  {
    return null;
  }

  // Overrides the click
  @Override
  public void handleTick(int millis)
  {
    // Reset to the start of the measure
    if (currentBeat > timeSignature.getNumerator())
      currentBeat = 1;

    System.out.println(clickTypesArrayList);
    clicker.click(clickTypesArrayList.get(currentBeat - 1));

    currentBeat++;
  }

  @Override
  public void focusGained(FocusEvent e)
  {
  }

  @Override
  public void focusLost(FocusEvent e)
  {
    try
    {
      int newTempo = Integer.parseInt(((JTextArea) e.getSource()).getText());
      if (newTempo <= 0)
        newTempo = 60;
      else if (newTempo > 300)
        newTempo = 300;
      setTempo(newTempo);
    }
    catch (NumberFormatException exception)
    {
    }
  }

  // ---------- Static Methods ----------
  
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
