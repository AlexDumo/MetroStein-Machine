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
  private Metronome met;
  private MetronomePreset currentPreset;
  private SubdivisionController subdivisionController;
  private double subdivisionMultiplier;
  private double tempo;
  protected ClickMachine clicker;
  protected TimeSignature timeSignature;
  protected ArrayList<Integer> clickTypes;
  protected int currentBeat;

  private boolean isSubdivision, subdivisionOn;

  private Set<MetronomeObserver> metronomeObservers;
  private Set<FrequentMetronomeObserver> frequentObservers;

  /**
   * Default constructor. Sets tempo to 120bpm and time signature to 4/4.
   */
  public MetronomeController(boolean isSubdivision)
  {
    metronomeObservers = new HashSet<>();
    frequentObservers = new HashSet<>();
    tempo = Constants.DEFAULT_TEMPO;
    clicker = new ClickMachine();
    met = new metronome.Metronome();
    met.setTempo(tempo);
    met.addListener(this);
    this.isSubdivision = isSubdivision;
    subdivisionOn = false;
    subdivisionMultiplier = 1;

    clickTypes = new ArrayList<Integer>();
    setTimeSignature(TimeSignature.getDefaultTimeSignature());
    currentBeat = 1;

    if (!isSubdivision)
    {
      subdivisionController = new SubdivisionController();
      // subdivisionController.setDelay((int) (met.getDelay() / subdivisionMultiplier));
      // subdivisionController.setTimeSignature(TimeSignature.getTimeSignature(3, 4));
    }
  }

  public MetronomeController()
  {
    this(false);
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
    currentPreset.setClickType(beatNumber, clickToUse);
    // Invalid beat
//    if (beatNumber < 0 || beatNumber > timeSignature.getNumerator())
//      return;
//
//    if (clickToUse < ClickMachine.CLICK_MIN || clickToUse > ClickMachine.CLICK_MAX)
//      clickTypes.set(beatNumber - 1, 1); // Invalid click number
//    else
//      clickTypes.set(beatNumber - 1, clickToUse);
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
    currentPreset.setClickTypes(typeArray);
    // Loops through the Arraylist
    // TODO this line is bad because it modifies a parameter
//    ArrayList<Integer> typeArrayCopy = (ArrayList<Integer>) typeArray.clone();
//    typeArrayCopy.ensureCapacity(timeSignature.getNumerator()); // ensures array is long enough for
//                                                            // current TimeSignature
//    Integer cur;
//    for (int i = 0; i < timeSignature.getNumerator(); i++)
//    {
//      cur = typeArrayCopy.get(i);
//      if (cur == null)
//        cur = ClickMachine.CLICK_DEFAULT;
//      setClickType(i + 1, cur);
//    }
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
   * Sets the tempo of the controller. Works when the metronome is on.
   * 
   * @param tempo
   *          the tempo to set
   */
  public void setTempo(double tempo)
  {
    System.out.println("Tempo " + tempo);
    setDelay(Metronome.bpmToMilli(tempo));
    ;
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
  public void setDelay(int delay)
  {
    System.out.printf("Old delay %d, New Delay: %d\n", Metronome.bpmToMilli(tempo), delay);
    this.tempo = Metronome.milliToBpm(delay);
    met.setDelay(delay);
    if (subdivisionController != null)
      subdivisionController.setDelay((int) (getDelay() / subdivisionMultiplier));
  }

  /**
   * @return the Metronome's delay.
   */
  public int getDelay()
  {
    return met.getDelay();
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
   * @return the TimeSignature of the owning controller.
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
  public void addMetronomeListener(final MetronomeListener metronomeListener)
  {
    if (metronomeListener != null)
      met.addListener(metronomeListener);
  }

  /**
   * Starts the metronome.
   * 
   * @param initialClick
   *          true if the metronome should click on start. False if it should wait for the first
   *          delay.
   */
  public void start(final boolean initialClick)
  {
    met.start(initialClick);
    if (subdivisionOn && !isSubdivision)
      subdivisionController.start(true);
  }

  /**
   * Stops the metronome.
   */
  public void stop()
  {
    met.stop();
    currentBeat = 1;

    // subdivisionController.stop();
  }

  /**
   * Sets the subdivision of the metronome controller.
   * 
   * @param subdivision
   */
  public void setSubdivision(final Subdivision subdivision)
  {
    subdivisionController.stop();
    subdivisionMultiplier = subdivision.beats;

    if (subdivisionMultiplier == 1)
      subdivisionOn = false;
    else
    {
      subdivisionController.setTimeSignature(TimeSignature.getTimeSignature(subdivision.beats, 4));
      subdivisionController.setDelay((getDelay() / subdivision.beats));
      subdivisionOn = true;
    }
    
    // TODO maybe this should start again?
  }

  /**
   * Sets all the attributes of the owning MetronomeController based on the attributes of the given
   * MetronomePreset.
   * 
   * @param metronomePreset
   */
  public void setFromMetronomePreset(final MetronomePreset metronomePreset)
  {
    if(metronomePreset == null)
      return;
    
    stop(); // Stop the metronome

    setTempo(tempo);
    setTimeSignature(metronomePreset.getTimeSignature());
    setSubdivision(metronomePreset.getSubdivision());
    setClickTypes(clickTypes);
  }

  // ---------- Override Methods ----------

  /**
   * Action performed override.
   */
  @SuppressWarnings("unchecked")
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
        start(true);
        break;
      case Constants.STOP:
        stop();
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
      case Constants.SUBDIVISION_CHANGE:
        setSubdivision((Subdivision) ((JComboBox<Subdivision>) e.getSource()).getSelectedItem());
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
    if (subdivisionOn)
      subdivisionController.start(false);
    // Reset to the start of the measure
    if (currentBeat > timeSignature.getNumerator())
      currentBeat = 1;

    // System.out.println("current beat " + currentBeat);
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
      cur.frequentUpdate(this);
    ;
  }
}
