package metronome;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 * 
 * A MetronomeSubject controls a metronome and notifies observers of changes.
 */
public interface MetronomeSubject
{
  /**
   * Add an observer to the list of observers.
   * 
   * @param observer
   */
  public void addObserver(MetronomeObserver observer);

  /**
   * Remove an observer from the list of observers.
   * 
   * @param observer
   */
  public void removeObserver(MetronomeObserver observer);
  
  /**
   * Adds a frequent observer to the subject. 
   * A frequent Observer is notified every metronome click.
   */
  public void addFrequentObserver(FrequentMetronomeObserver frequentObserver);

  /**
   * Notify all observers that the subject's state has changed.
   */
  public void notifyObservers();
  
  /**
   * Notify all observers that the subject's state has changed.
   * A frequent Observer is notified every metronome click.
   */
  public void notifyFrequentObservers();
}
