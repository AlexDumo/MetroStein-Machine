package metronome;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         A modified and (more useful) version of the Metronome from the Multimedia API. Some of
 *         the methods are direct copies.
 */
public class OldMetronome
{
  private static final int DEFAULT_DELAY = 500;

  private int delay;
  private volatile boolean running;
  private Timer timer;
  private ActionListener listener;
  private ArrayList<MetronomeListener> listeners;
  private MetronomeTickDispatcher dispatcher;
  private MetronomeListener[] copy;

  /**
   * constructor to set up the metronome with given delay.
   * 
   * @param delay
   *          The delay in milliseconds to use. 500 if < 1.
   */
  public OldMetronome(final int delay)
  {
    if (delay < 1) // Set to default tempo if invalid.
      this.delay = DEFAULT_DELAY;
    else
      this.delay = delay;

    running = false;
    dispatcher = new MetronomeTickDispatcher();
    listeners = new ArrayList<MetronomeListener>();
    copy = new MetronomeListener[0];
    listener = new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        notifyListeners();
      }
    };

    timer = new Timer(delay, listener);
  }

  /**
   * Default constructor with a 500ms delay.
   */
  public OldMetronome()
  {
    this(DEFAULT_DELAY);
  }

  /**
   * Start the Metronome.
   * 
   * @param click
   *          true if the Metronome should send a click on start.
   */
  public void start(final boolean click)
  {
    if (click)
      notifyListeners();

    timer.start();
    running = true;
  }

  /**
   * Stop the metronome.
   */
  public void stop()
  {
    timer.stop();
    running = false;
  }

  /**
   * Get the current delay.
   * 
   * @return The current delay
   */
  public int getDelay()
  {
    return delay;
  }

  /**
   * Sets the millisecond delay of this Metronome. Can be used while running. Ignores any values
   * less than 1.
   * 
   * @param newDelay
   */
  public void setDelay(final int newDelay)
  {
    if (newDelay < 1)
      return;

    boolean wasRunning = running;
    stop();

    delay = newDelay;

    timer = new Timer(newDelay, listener);

    if (wasRunning)
      start(false);
  }

  /**
   * @return The current BPM based on the millisecond delay.
   */
  public double getTempo()
  {
    return milliToBpm(delay);
  }

  /**
   * Sets the Metronome's tempo to the given bpm (beats per minute). Only accurate to the
   * millisecond. Can be used while the metronome is running. Ignores values that will evaluate to
   * less than 1ms.
   * 
   * @param bpm
   *          The bpm to use. Only accurate to the millisecond (rounds down).
   */
  public void setTempo(final double bpm)
  {
    setDelay(bpmToMilli(bpm));
  }

  /**
   * Add a MetronomeListener. Copied from the Multimedia API.
   * 
   * @param newListener
   *          The MetronomeListener to add
   */
  public synchronized void addListener(final MetronomeListener newListener)
  {

    listeners.add(newListener);
    copyListeners();
  }

  /**
   * Copy the collection of listeners. Used in the event dispatch thread to avoid concurrent
   * modification problems. Called infrequently so it does not cause many efficiency problems.
   * Copied from the Multimedia API.
   */
  private void copyListeners()
  {
    copy = new MetronomeListener[listeners.size()];
    listeners.toArray(copy);
  }

  /**
   * Get the number of listeners. Copied from the Multimedia API.
   * 
   * @return The number of listeners
   */
  public synchronized int getNumberOfListeners()
  {
    return listeners.size();
  }

  /**
   * Notify observers in the GUI/event-dispatch thread. Note: Listeners are notified in the REVERSE
   * order in which they are added. Copied from the Multimedia API.
   */
  protected synchronized void notifyListeners()
  {
    // Setup the state of the MetronomeTickDispatcher
    dispatcher.setup(copy);

    // Cause the run() method of the dispatcher to be
    // called in the GUI/event-dispatch thread
    EventQueue.invokeLater(dispatcher);
  }

  /**
   * Remove a MetronomeListener. Copied from the Multimedia API.
   *
   * @param ml
   *          The MetronomeListener to remove
   */
  public synchronized void removeListener(final MetronomeListener ml)
  {
    listeners.remove(ml);
    copyListeners();
  }

  /**
   * Converts bpm (beats per minute) to milliseconds per tick. Rounds down to the millisecond.
   * 
   * @param bpm
   *          The bpm to convert to milliseconds
   * @return The milliseconds per click.
   */
  public static int bpmToMilli(final double bpm)
  {
    return (int) (60000.0 / bpm);
  }

  /**
   * Converts millisecond delay into beats per minute (bpm).
   * 
   * @param milli
   *          The millisecond delay to convert to bpm
   * 
   * @return The bpm
   */
  public static double milliToBpm(final int milli)
  {
    return 60000.0 / milli;
  }

  /**
   * A MetronomeTickDispatcher is used by a Metronome to inform listeners of a tick. This class
   * implements Runnable because its run() method will be called in the GUI/event-dispatch thread.
   * 
   * Copied from the Multimedia API.
   */
  private class MetronomeTickDispatcher implements Runnable
  {
    private MetronomeListener[] listeners;

    /**
     * Code to be executed in the event-dispatch thread (required by Runnable).
     *
     * Specifically, notify the listeners
     */
    public void run()
    {
      int n;

      n = listeners.length;
      for (int i = n - 1; i >= 0; i--)
      {
        if (listeners[i] != null)
          listeners[i].handleTick(0);
      }
    }

    /**
     * Setup this MetronomeTickDispatcher for the next dispatch.
     *
     * @param newListeners
     *          The collection of MetronomeListener objects
     */
    public void setup(final MetronomeListener[] newListeners)
    {
      this.listeners = newListeners;
    }
  }
}
