package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metronome.MetronomeListener;
import metronome.Metronome;
import metronome.MetronomeController;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 */
class MetronomeTest
{
  private Metronome testMetronome;
  
  private static final int DEFAULT_DELAY = 500;
  private static final double DEFAULT_TEMPO = 120.0;

  /**
   * Test method for {@link metronome.Metronome#Metronome(int)}.
   */
  @Test
  final void testMetronomeInt()
  {
    testMetronome = new Metronome();
    assertEquals(DEFAULT_DELAY, testMetronome.getDelay());
    assertEquals(DEFAULT_TEMPO, testMetronome.getTempo());

    int newDelay = 250;
    testMetronome = new Metronome(newDelay);
    assertEquals(newDelay, testMetronome.getDelay());
    
    newDelay = 1000;
    testMetronome = new Metronome(newDelay);
    assertEquals(newDelay, testMetronome.getDelay());
    
    newDelay = -1;
    testMetronome = new Metronome(newDelay);
    assertEquals(DEFAULT_DELAY, testMetronome.getDelay());
  }


  /**
   * Test method for bpmToMilli.
   */
  @Test
  final void testBpmToMilli()
  {
    double bpm = 120.0;
    assertEquals((int) (60000 / bpm), Metronome.bpmToMilli(bpm));
    
    bpm = -5.0;
    assertEquals((int) (60000 / bpm), Metronome.bpmToMilli(bpm));
    
    bpm = 2000.65115616;
    assertEquals((int) (60000 / bpm), Metronome.bpmToMilli(bpm));
    
    bpm = 0.0;
    assertEquals((int) (60000 / bpm), Metronome.bpmToMilli(bpm));
  }

  /**
   * Test method for milliToBpm.
   */
  @Test
  final void testMilliToBpm()
  {
    int milli = 500;
    assertEquals(60000.0 / milli, Metronome.milliToBpm(milli));
    
    milli = -5;
    assertEquals(60000.0 / milli, Metronome.milliToBpm(milli));
    
    milli = 2000;
    assertEquals(60000.0 / milli, Metronome.milliToBpm(milli));
    
    milli = 0;
    assertEquals(60000.0 / milli, Metronome.milliToBpm(milli));
  }

  /**
   * Test method for {@link metronome.Metronome#setDelay(int)}.
   */
  @Test
  final void testSetDelay()
  {
    testMetronome = new Metronome();
    assertEquals(DEFAULT_DELAY, testMetronome.getDelay());
    
    int newDelay = 250;
    testMetronome.setDelay(newDelay);
    assertEquals(newDelay, testMetronome.getDelay());
    
    newDelay = 1000;
    testMetronome.setDelay(newDelay);
    assertEquals(newDelay, testMetronome.getDelay());
    
    int oldDelay = newDelay;
    newDelay = -1;
    testMetronome.setDelay(newDelay);
    assertEquals(oldDelay, testMetronome.getDelay());
  }

  /**
   * Test method for {@link metronome.Metronome#setTempo(double)}.
   */
  @Test
  final void testSetTempo()
  {
    testMetronome = new Metronome();
    assertEquals(DEFAULT_TEMPO, testMetronome.getTempo());
    
    double newTempo = 120.5;
    testMetronome.setTempo(newTempo);
    assertEquals(newTempo, testMetronome.getTempo(), .5);
    assertEquals(Metronome.bpmToMilli(newTempo), testMetronome.getDelay());
    assertEquals(newTempo, Metronome.milliToBpm(testMetronome.getDelay()), .5);
    
    newTempo = 1000;
    testMetronome.setTempo(newTempo);
    assertEquals(newTempo, testMetronome.getTempo(), .5);
    assertEquals(Metronome.bpmToMilli(newTempo), testMetronome.getDelay());
    assertEquals(newTempo, Metronome.milliToBpm(testMetronome.getDelay()), .5);
    
    double oldTempo = newTempo;
    newTempo = -1;
    testMetronome.setTempo(newTempo);
    assertEquals(oldTempo, testMetronome.getTempo(), .5);
  }

  /**
   * Test method for {@link metronome.Metronome#addListener(metronome.MetronomeListener)}.
   */
  @Test
  final void testListener()
  {
    testMetronome = new Metronome();
    
    assertEquals(testMetronome.getNumberOfListeners(), 0);
    
    MetronomeListener ml = new MetronomeController();
    testMetronome.addListener(ml);
    assertEquals(testMetronome.getNumberOfListeners(), 1);
    
    testMetronome.removeListener(null);
    assertEquals(testMetronome.getNumberOfListeners(), 1);
    
    testMetronome.removeListener(ml);
    assertEquals(testMetronome.getNumberOfListeners(), 0);
  }

}
