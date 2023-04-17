package metronome;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 *         
 * A TimeSignature is a simple object that has a numerator and a denominator.
 */
public class TimeSignature
{
  public static final TimeSignature[] TIME_SIGNATURES = {
      new TimeSignature(1, 4),
      new TimeSignature(2, 4),
      new TimeSignature(3, 4),
      new TimeSignature(4, 4),
      new TimeSignature(5, 4),
      new TimeSignature(6, 4),
      new TimeSignature(7, 4),
      new TimeSignature(8, 4),
      new TimeSignature(9, 4),
      new TimeSignature(10, 4),
      new TimeSignature(11, 4),
      new TimeSignature(12, 4),
      new TimeSignature(13, 4),
      new TimeSignature(14, 4),
      new TimeSignature(15, 4),
      new TimeSignature(16, 4)
  };
  public static final int DEFAULT_TIME_SIGNATURE_INDEX = 3;
  
  private int numerator;
  private int denominator;

  /**
   * @param numerator
   * @param denominator
   */
  public TimeSignature(int numerator, int denominator)
  {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  /**
   * @return the numerator
   */
  public int getNumerator()
  {
    return numerator;
  }

  /**
   * @param numerator
   *          the numerator to set
   */
  public void setNumerator(int numerator)
  {
    this.numerator = numerator;
  }

  /**
   * @return the denominator
   */
  public int getDenominator()
  {
    return denominator;
  }

  /**
   * @param denominator
   *          the denominator to set
   */
  public void setDenominator(int denominator)
  {
    this.denominator = denominator;
  }

  @Override
  public String toString()
  {
    return numerator + "/" + denominator;
  }

}
