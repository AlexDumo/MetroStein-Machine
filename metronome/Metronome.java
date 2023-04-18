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
public class Metronome
{
  private int delay; // milliseconds between clicks
  private volatile boolean running;
  private Timer timer; // swing timer object
  private ActionListener listener; // action listener for the timer
  private ArrayList<MetronomeListener> listeners;
  private MetronomeTickDispatcher dispatcher;
  private MetronomeListener[] copy; // listener for beat events

  // constructor to set up the metronome
  public Metronome(int delay)
  {
    this.delay = delay;
    running = false;
    dispatcher = new MetronomeTickDispatcher();
    listeners = new ArrayList<MetronomeListener>();
    copy = new MetronomeListener[0];
    listener = new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        notifyListeners();
      }
    };

    timer = new Timer(delay, listener);
  }
  
  /**
   * Default constructor with a 500ms delay.
   */
  public Metronome()
  {
    this(500);
  }

  /**
   * Start the Metronome.
   * 
   * @param click
   *          true if the Metronome should send a click on start.
   */
  public void start(boolean click)
  {
    if (click)
      notifyListeners();

    timer.start();
    running = true;
  }

  // stop the metronome
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
   * Sets the millisecond delay of this Metronome. Can be used while running
   * 
   * @param newDelay
   */
  public void setDelay(final int newDelay)
  {
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
   * millisecond. Can be used while the metronome is running.
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
   * @param listener
   *          The MetronomeListener to add
   */
  public synchronized void addListener(final MetronomeListener listener)
  {

    listeners.add(listener);
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
  public synchronized void removeListener(MetronomeListener ml)
  {
    listeners.remove(ml);
    copyListeners();
  }

  /*
   * Converts bpm (beats per minute) to milliseconds per tick. Rounds down to the millisecond.
   * 
   * @param bpm The bpm to convert to milliseconds
   * 
   * @return The milliseconds per click.
   */
  protected static int bpmToMilli(double bpm)
  {
    return (int) (60000 / bpm);
  }

  /*
   * Converts millisecond delay into beats per minute (bpm).
   * 
   * @param bpm The millisecond delay to convert to bpm
   * 
   * @return The bpm
   */
  protected static double milliToBpm(int milli)
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
     * Code to be executed in the event-dispatch thread (required by Runnable)
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
     * @param listeners
     *          The collection of MetronomeListener objects
     * @param time
     *          The (relative) time of the tick
     */
    public void setup(MetronomeListener[] listeners)
    {
      this.listeners = listeners;
    }
  }
}
