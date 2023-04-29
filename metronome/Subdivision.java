package metronome;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public enum Subdivision
{
  None(1), Eigths(2), Triplets(3), Sixteenths(4), Quintuplets(5);

  private final int beats;

  private Subdivision(final int beats)
  {
    this.beats = beats;
  }

  /**
   * @return the amnount of beats in this Subdivision.
   */
  public int getBeats()
  {
    return beats;
  }
}
