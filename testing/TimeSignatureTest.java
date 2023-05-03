package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import metronome.TimeSignature;

class TimeSignatureTest
{

  /**
   * Test method for {@link metronome.TimeSignature#getDefaultTimeSignature()}.
   */
  @Test
  public void testGetDefaultTimeSignature()
  {
    TimeSignature defaultTS = TimeSignature.getDefaultTimeSignature();
    assertEquals(4, defaultTS.getNumerator());
    assertEquals(4, defaultTS.getDenominator());
  }

  /**
   * Test method for {@link metronome.TimeSignature#getTimeSignature(int, int)}.
   */
  @Test
  public void testGetTimeSignature()
  {
    // Test supported time signatures
    TimeSignature timeSignature = TimeSignature.getTimeSignature(1, 4);
    assertEquals(1, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
    timeSignature = TimeSignature.getTimeSignature(8, 4);
    assertEquals(8, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());

    // Test unsupported time signature
    timeSignature = TimeSignature.getTimeSignature(3, 8);
    assertEquals(4, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
    
    timeSignature = TimeSignature.getTimeSignature(-4, 4);
    assertEquals(4, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
    
    timeSignature = TimeSignature.getTimeSignature(1, -1);
    assertEquals(4, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
    
    timeSignature = TimeSignature.getTimeSignature(0, 4);
    assertEquals(4, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
    
    timeSignature = TimeSignature.getTimeSignature(4, 0);
    assertEquals(4, timeSignature.getNumerator());
    assertEquals(4, timeSignature.getDenominator());
  }

  /**
   * Test method for {@link metronome.TimeSignature#getNumerator()}.
   */
  @Test
  public void testGetNumerator()
  {
    TimeSignature timeSignature = TimeSignature.getTimeSignature(3, 4);
    assertEquals(3, timeSignature.getNumerator());
  }

  /**
   * Test method for {@link metronome.TimeSignature#getDenominator()}.
   */
  @Test
  public void testGetDenominator()
  {
    TimeSignature timeSignature = TimeSignature.getTimeSignature(3, 4);
    assertEquals(4, timeSignature.getDenominator());
  }

  /**
   * Test method for {@link metronome.TimeSignature#toString()}.
   */
  @Test
  public void testToString()
  {
    TimeSignature timeSignature = TimeSignature.getTimeSignature(3, 4);
    assertEquals("3/4", timeSignature.toString());
  }

  /**
   * Test method for {@link metronome.TimeSignature#equals(java.lang.Object)}.
   */
  @Test
  public void testEquals()
  {
    TimeSignature ts1 = TimeSignature.getTimeSignature(3, 4);
    TimeSignature ts2 = TimeSignature.getTimeSignature(3, 4);
    TimeSignature ts3 = TimeSignature.getTimeSignature(4, 4);
    
    // Test with equal TimeSignatures
    assertTrue(ts1.equals(ts2));

    // Test with different TimeSignatures
    assertFalse(ts1.equals(ts3));

    // Test with null input
    assertFalse(ts1.equals(null));
  }

  /**
   * Test method for {@link metronome.TimeSignature#hashCode()}.
   */
  @Test
  public void testHashCode()
  {
    TimeSignature ts1 = TimeSignature.getTimeSignature(3, 4);
    TimeSignature ts2 = TimeSignature.getTimeSignature(3, 4);

    // Test with equal TimeSignatures
    assertEquals(ts1.hashCode(), ts2.hashCode());
  }
}
