package metronome;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         A TimeSignature is a simple object that has a numerator and a denominator.
 */
public class TimeSignature
{
  public static final TimeSignature[] TIME_SIGNATURES_4 = {new TimeSignature(1, 4),
      new TimeSignature(2, 4), new TimeSignature(3, 4), new TimeSignature(4, 4),
      new TimeSignature(5, 4), new TimeSignature(6, 4), new TimeSignature(7, 4),
      new TimeSignature(8, 4), new TimeSignature(9, 4), new TimeSignature(10, 4),
      new TimeSignature(11, 4), new TimeSignature(12, 4), new TimeSignature(13, 4),
      new TimeSignature(14, 4), new TimeSignature(15, 4), new TimeSignature(16, 4),
      new TimeSignature(3, 8), new TimeSignature(4, 8), new TimeSignature(5, 8),
      new TimeSignature(6, 8), new TimeSignature(7, 8), new TimeSignature(8, 8),
      new TimeSignature(9, 8), new TimeSignature(10, 8), new TimeSignature(11, 8),
      new TimeSignature(12, 8)};
  private static final int DEFAULT_TIME_SIGNATURE_INDEX = 3;

  private int numerator;
  private int denominator;

  /**
   * @param numerator
   * @param denominator
   */
  private TimeSignature(final int numerator, final int denominator)
  {
    this.numerator = numerator;
    this.denominator = denominator;
  }

  /**
   * Gets the default time signature (4/4).
   * 
   * @return the default TimeSignature.
   */
  public static TimeSignature getDefaultTimeSignature()
  {
    return TIME_SIGNATURES_4[DEFAULT_TIME_SIGNATURE_INDEX];
  }

  /**
   * Gets the time signature object with the given numerator and denominator. 4/4 if not supported.
   * 
   * @param numerator
   * @param denominator
   * @return The time signature object with the given numerator and denominator. 4/4 if not
   *         supported.
   */
  public static TimeSignature getTimeSignature(final int numerator, final int denominator)
  {
    TimeSignature output = getDefaultTimeSignature();
    switch (denominator)
    {
      case 4:
        if (numerator > 0 && numerator <= TIME_SIGNATURES_4.length)
          output = TIME_SIGNATURES_4[numerator - 1];
        break;
      default:
        break;
    }
    return output;
  }

  /**
   * @return the numerator
   */
  public int getNumerator()
  {
    return numerator;
  }

  /**
   * @return the denominator
   */
  public int getDenominator()
  {
    return denominator;
  }

  @Override
  public String toString()
  {
    return numerator + "/" + denominator;
  }

  /**
   * Equals method for two TimeSignatures. Two TimeSignatures are equal if they have the same
   * numerator and denominator.
   * 
   * @param other
   * @return true if both TimeSignatures have the same numerator and denominator.
   */
  @Override
  public boolean equals(final Object other)
  {
    TimeSignature otherTimeSignature = (TimeSignature) other;
    if (otherTimeSignature == null)
      return false;
    return other.hashCode() == hashCode();
  }
  
  @Override
  public int hashCode()
  {
    return toString().hashCode();
  }

}
