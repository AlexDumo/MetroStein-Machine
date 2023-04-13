package resources;

import metronome.TimeSignature;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 * 
 * Contains constants for the MetroSteinMachine to use across all classes.
 */
public class Constants
{
  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;
  
  public static final String 
      INCREMENT = "+", DECREMENT = "-", 
      START = "start", STOP = "stop",
      METER_CHANGE = "meterChange";

  public static final int DEFAULT_TIME_SIGNATURE_INDEX = 3, DEFAULT_TEMPO = 120;
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
}
