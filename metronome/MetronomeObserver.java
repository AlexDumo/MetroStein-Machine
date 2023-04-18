package metronome;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 * 
 * A MetronomeObserver updates based on the state of a MetronomeSubject.
 */
public interface MetronomeObserver
{
  /**
   * Updates the owning observer based on the given MetronomeSubject.
   * 
   * @param metronomeSubject
   */
  public void update(MetronomeSubject metronomeSubject);
}
