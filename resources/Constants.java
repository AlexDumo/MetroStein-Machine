package resources;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 * 
 * Contains constants for the MetroSteinMachine to use across all classes.
 */
public class Constants
{  
  public static final int WIDTH = 600, HEIGHT = 300;
  
  public static final char DELIMITER = '$';
  
  public static final String 
      INCREMENT = "+", DECREMENT = "-", 
      START = "▶ Start", STOP = "■ Stop",
      METER_CHANGE = "meterChange",
      SUBDIVISION_CHANGE = "subChange",
      TEMPO_CHANGE = "tempo";
  
  

  public static final int 
      DEFAULT_TIME_SIGNATURE_INDEX = 3, 
      DEFAULT_TEMPO = 120, 
      DEFAULT_CLICK = 1,
      MAX_TEMPO = 500, DEFAULT_SLOW = 60;
  
  
}
