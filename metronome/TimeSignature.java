package metronome;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class TimeSignature
{
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
