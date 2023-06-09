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
  protected ClickMachine clicker;
  protected int currentBeat;
  private Metronome met;
  private SubdivisionController subdivisionController;
  private double subdivisionMultiplier;

  private boolean isSubdivision, // If this is an instance of a SubdivisionController
      subdivisionOn, // If the subdivision is activated
      isRunning; // The MetronomeController is running

  private Set<MetronomeObserver> metronomeObservers;
  private Set<FrequentMetronomeObserver> frequentObservers;

  /**
   * Default constructor. Sets tempo to 120bpm and time signature to 4/4.
   * 
   * @param isSubdivision
   *          true is this is a SubdivisionController. Kind of a messy solution.
   */
  public MetronomeController(final boolean isSubdivision)
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
      subdivisionController = new SubdivisionController();
  }

  /**
   * Default constructor. Not a subdivision.
   */
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
   * Sets the tempo of the controller. Works when the metronome is on. Values less than or equal to
   * 0 are ignored.
   * 
   * @param tempo
   *          the tempo to set
   */
  public void setTempo(final double tempo)
  {
    if (tempo <= 0.0)
      return;
    super.setTempo(tempo);
    met.setTempo(tempo);
    if (subdivisionController != null)
      subdivisionController.setTempo(getTempo() * subdivisionMultiplier);
  }

  /**
   * A more precise way to change the tempo. Specify millisecond delay. Values less than
   * 1 are ignored.
   * 
   * @param delay
   *          the delay to set
   */
  public void setDelay(final int delay)
  {
    if (delay < 1)
      return;
    // System.out.printf("Old delay %d, New Delay: %d\n", Metronome.bpmToMilli(getTempo()), delay);
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
   * @return true if running, false if not.
   */
  public boolean isRunning()
  {
    return isRunning;
  }

  /**
   * Sets the subdivision of the metronome controller. Ignores null input;
   * 
   * @param subdivision
   */
  public void setSubdivision(final Subdivision subdivision)
  {
    if(subdivision == null)
      return;
    
    subdivisionController.stop();
    subdivisionMultiplier = subdivision.getBeats();

    if (subdivisionMultiplier == 1)
      subdivisionOn = false;
    else
    {
      subdivisionController
          .setTimeSignature(TimeSignature.getTimeSignature(subdivision.getBeats(), 4));
      subdivisionController.setDelay((getDelay() / subdivision.getBeats()));
      subdivisionOn = true;
    }

    super.setSubdivision(subdivision);
  }

  // ---------- Override Methods ----------

  /**
   * Action performed override.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void actionPerformed(final ActionEvent e)
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

      // System.out.println(acArgs);
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
      default:
        System.out.println(e.getActionCommand());
        break;
    }

    if (notify)
      notifyObservers();
  }

  @Override
  public void handleTick(final int millis)
  {
    if (subdivisionOn)
      subdivisionController.start(false);
    // Reset to the start of the measure
    if (currentBeat > getTimeSignature().getNumerator())
    {
      System.gc();
      currentBeat = 1;
    }

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
      notifyObservers();
    }
    catch (NumberFormatException exception)
    {
    }
  }

  // ---------- Observer Methods ----------

  @Override
  public void addObserver(final MetronomeObserver observer)
  {
    metronomeObservers.add(observer);
  }

  @Override
  public void removeObserver(final MetronomeObserver observer)
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
  public void addFrequentObserver(final FrequentMetronomeObserver frequentObserver)
  {
    frequentObservers.add(frequentObserver);
  }

  @Override
  public void notifyFrequentObservers()
  {
    for (FrequentMetronomeObserver cur : frequentObservers)
      cur.frequentUpdate(this);
  }
}
