package metronome;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         A FrequentMetronomeObserver updates every metronome click based on the state of a
 *         MetronomeSubject.
 */
public interface FrequentMetronomeObserver
{
  /**
   * Updates the owning frequent observer based on the given MetronomeSubject.
   * 
   * @param metronomeSubject
   */
  public void frequentUpdate(MetronomeSubject metronomeSubject);
}
