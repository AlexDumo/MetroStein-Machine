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
public class MetronomeController extends MetronomePreset
    implements ActionListener, MetronomeListener, FocusListener, MetronomeSubject
{
  private Metronome met;
  private SubdivisionController subdivisionController;
  private double subdivisionMultiplier;
  protected ClickMachine clicker;
  protected int currentBeat;

  private boolean isSubdivision, subdivisionOn, isRunning;

  private Set<MetronomeObserver> metronomeObservers;
  private Set<FrequentMetronomeObserver> frequentObservers;

  /**
   * Default constructor. Sets tempo to 120bpm and time signature to 4/4.
   */
  public MetronomeController(boolean isSubdivision)
  {
    super();
    metronomeObservers = new HashSet<>();
    frequentObservers = new HashSet<>();
    clicker = new ClickMachine();
    met = new metronome.Metronome();
    met.setTempo(getTempo());
    met.addListener(this);
    this.isSubdivision = isSubdivision;
    subdivisionOn = false;
    subdivisionMultiplier = 1;

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
  }

  /**
   * Currently only works when the metronome is off.
   * 
   * @param tempo
   *          the tempo to set
   */
  public void setDelay(int delay)
  {
    System.out.printf("Old delay %d, New Delay: %d\n", Metronome.bpmToMilli(getTempo()), delay);
    super.setTempo(Metronome.milliToBpm(delay));
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
    if (!isSubdivision && subdivisionOn)
      subdivisionController.start(true);
    met.start(initialClick);
    isRunning = true;
  }

  /**
   * Stops the metronome.
   */
  public void stop()
  {
    met.stop();
    currentBeat = 1;
    isRunning = false;
    
    notifyFrequentObservers(); // make sure that all they know the met is off.
  }
  
  /**
   * Returns true if the controller is running, false if not.
   * 
   * @return
   */
  public boolean isRunning() {
    return isRunning;
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

//    setTempo(tempo);
    setTimeSignature(metronomePreset.getTimeSignature());
    setSubdivision(metronomePreset.getSubdivision());
//    setClickTypes(clickTypes);
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

    boolean notify = true;
    // Starts, and increments the metronome
    switch (acString)
    {
      case Constants.START:
        notify = false;
        start(true);
        break;
      case Constants.STOP:
        notify = false;
        stop();
        break;
      case Constants.INCREMENT:
        setTempo(getTempo() + 1);
        break;
      case Constants.DECREMENT:
        setTempo(getTempo() - 1);
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
      case Constants.TEMPO_CHANGE:
        setTempo(Double.parseDouble(acArgs.get(1)));
        break;
      case "test":
        break;
      default:
        System.out.println(e.getActionCommand());
        break;
    }

    if(notify)
      notifyObservers();
  }

  // Overrides the click
  @Override
  public void handleTick(int millis)
  {
    if (subdivisionOn)
      subdivisionController.start(false);
    // Reset to the start of the measure
    if (currentBeat > getTimeSignature().getNumerator())
      currentBeat = 1;

    // System.out.println("current beat " + currentBeat);
    clicker.click(getClickTypes().get(currentBeat - 1));

    notifyFrequentObservers();
    currentBeat++;
  }

  /**
   * Does nothing.
   */
  @Override
  public void focusGained(final FocusEvent e)
  {
    // Does nothing
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
