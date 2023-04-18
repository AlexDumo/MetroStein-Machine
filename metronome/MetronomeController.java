package metronome;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import gui.BeatSelector;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         This class controls the ClickMachines by telling which one to activate and when. It has
 *         four types of clicks at its disposal.
 */
public class MetronomeController
    implements ActionListener, MetronomeListener, FocusListener, MetronomeSubject
{
//  private Metronome met;
  private Metronome met;
  private boolean metIsRunning;
  private double tempo;
  private ClickMachine clicker;
  private TimeSignature timeSignature;
  private ArrayList<Integer> clickTypes;
  private int currentBeat;

  private Set<MetronomeObserver> metronomeObservers; 
  private Set<FrequentMetronomeObserver> frequentObservers;

  /**
   * Default constructor. Sets tempo to 120bpm and time signature to 4/4.
   */
  public MetronomeController()
  {
    metronomeObservers = new HashSet<>();
    frequentObservers = new HashSet<>();
    tempo = Constants.DEFAULT_TEMPO;
    metIsRunning = false;
    clicker = new ClickMachine();
    met = new metronome.Metronome();
    met.setTempo(tempo);
    met.addListener(this);
//    met = new Metronome(bpmToMilli(tempo), true);
//    met.addListener(this);

    clickTypes = new ArrayList<Integer>();
    setTimeSignature(TimeSignature.getDefaultTimeSignature());
    currentBeat = 1;
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
    // Loops through the map
    for (int i = 0; i < typeArray.size(); i++)
      setClickType(i + 1, typeArray.get(i));
  }

  /**
   * @return The array of click types for each beat. Note that the beat numbers start at 1 but the
   *         indexes still start at 0.
   */
  public ArrayList<Integer> getClickTypes()
  {
    return clickTypes;
  }

  /**
   * @return the currentBeat
   */
  public int getCurrentBeat()
  {
    return currentBeat;
  }

  /**
   * @return the tempo
   */
  public double getTempo()
  {
    return tempo;
  }

  /**
   * Currently only works when the metronome is off.
   * 
   * @param tempo
   *          the tempo to set
   */
  public void setTempo(double tempo)
  {
    this.tempo = tempo;
    System.out.println("Tempo " + tempo);

    met.setTempo(tempo);
//    met.stop();
////    met = new Metronome(bpmToMilli(tempo));
//    met = new metronome.Metronome(bpmToMilli(tempo));
//    met.addListener(this);
//
//    // Resumes the met if it was already going.
//    if (metIsRunning)
//      met.start(false);

  }

  /**
   * Sets the time signature. Must be a supported time signature, otherwise 4/4.
   * 
   * @param timeSignature
   *          the timeSignature to set
   */
  public void setTimeSignature(final TimeSignature timeSignature)
  {
    clickTypes.clear();
    clickTypes.ensureCapacity(timeSignature.getNumerator());

    for (int i = 0; i < timeSignature.getNumerator(); i++)
      clickTypes.add(i, Constants.DEFAULT_CLICK);
    clickTypes.set(0, 0);
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
   * @param metronomeListener
   *          the MetronomeListener to add
   */
  public void addMetronomeListener(MetronomeListener metronomeListener)
  {
    if (metronomeListener != null)
      met.addListener(metronomeListener);
  }

  // ---------- Override Methods ----------

  /**
   * Action performed override.
   */
  @Override
  public void actionPerformed(ActionEvent e)
  {
    String acString = e.getActionCommand();
    List<String> acArgs = new ArrayList<>();

    // Tokenizes first part of string if it has a DELIMETER.
    if (acString.indexOf(Constants.DELIMITER) >= 0)
    {
      acString = acString.substring(0, acString.indexOf(Constants.DELIMITER));

      // System.out.println(args);
      StringTokenizer tokenizer = new StringTokenizer(e.getActionCommand(),
          Character.toString(Constants.DELIMITER));
      while (tokenizer.hasMoreTokens())
        acArgs.add(tokenizer.nextToken());

      System.out.println(acArgs);
    }

    // Starts, and increments the metronome
    switch (acString)
    {
      case Constants.START:
//        clicker.click(clickTypes.get(0));
        met.start(true);
//        met2.start();
        metIsRunning = true;
//        notifyFrequentObservers();
//        currentBeat++;
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
        setTimeSignature(
            (TimeSignature) ((JComboBox<TimeSignature>) e.getSource()).getSelectedItem());
        break;
      case BeatSelector.BEAT_COMMAND:
        setClickType(Integer.parseInt(acArgs.get(1)), Integer.parseInt(acArgs.get(2)));
        break;
      case "test":
        break;
      default:
        System.out.println(e.getActionCommand());
        break;
    }

    notifyObservers();
  }

  // Overrides the click
  @Override
  public void handleTick(int millis)
  {
    // Reset to the start of the measure
    if (currentBeat > timeSignature.getNumerator())
      currentBeat = 1;

//    System.out.println("current beat " + currentBeat);
    clicker.click(clickTypes.get(currentBeat - 1));

    notifyFrequentObservers();
    currentBeat++;
  }

  /**
   * Does nothing.
   */
  @Override
  public void focusGained(final FocusEvent e)
  {
  }

  /**
   * Sets the tempo from the tempo input box.
   */
  @Override
  public void focusLost(final FocusEvent e)
  {
    try
    {
      int newTempo = Integer.parseInt(((JTextArea) e.getSource()).getText());
      if (newTempo <= 0)
        newTempo = Constants.DEFAULT_SLOW;
      else if (newTempo > Constants.MAX_TEMPO)
        newTempo = Constants.MAX_TEMPO;
      setTempo(newTempo);
    }
    catch (NumberFormatException exception)
    {
    }
  }

  // ---------- Static Methods ----------



  @Override
  public void addObserver(MetronomeObserver observer)
  {
    metronomeObservers.add(observer);
  }

  @Override
  public void removeObserver(MetronomeObserver observer)
  {
    metronomeObservers.remove(observer);
  }

  @Override
  public void notifyObservers()
  {
    for (MetronomeObserver cur : metronomeObservers)
      cur.update(this);
  }

  @Override
  public void addFrequentObserver(FrequentMetronomeObserver frequentObserver)
  {
    frequentObservers.add(frequentObserver);
  }

  @Override
  public void notifyFrequentObservers()
  {
    for (FrequentMetronomeObserver cur : frequentObservers)
      cur.frequentUpdate(this);;
  }
}
