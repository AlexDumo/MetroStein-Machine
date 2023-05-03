package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import metronome.MetronomeListener;
import metronome.Metronome;
import metronome.MetronomeController;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
class MetronomeTest
{
  private static final int DEFAULT_DELAY = 500;
  private static final double DEFAULT_TEMPO = 120.0;

  private Metronome metronome;

  /**
   * Test method for {@link metronome.Metronome#Metronome(int)}.
   */
  @Test
  final void testMetronomeInt()
  {
    metronome = new Metronome();
    assertEquals(DEFAULT_DELAY, metronome.getDelay());
    assertEquals(DEFAULT_TEMPO, metronome.getTempo());

    int newDelay = 250;
    metronome = new Metronome(newDelay);
    assertEquals(newDelay, metronome.getDelay());

    newDelay = 1000;
    metronome = new Metronome(newDelay);
    assertEquals(newDelay, metronome.getDelay());

    newDelay = -1;
    metronome = new Metronome(newDelay);
    assertEquals(DEFAULT_DELAY, metronome.getDelay());
  }

  /**
   * Test method for {@link metronome.Metronome#bpmToMilli(double)}.
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
   * Test method for {@link metronome.Metronome#milliToBpm(int)}.
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
   * @throws InterruptedException 
   */
  @Test
  final void testSetDelay() throws InterruptedException
  {
    metronome = new Metronome();
    assertEquals(DEFAULT_DELAY, metronome.getDelay());

    int newDelay = 250;
    metronome.setDelay(newDelay);
    assertEquals(newDelay, metronome.getDelay());

    newDelay = 1000;
    metronome.setDelay(newDelay);
    assertEquals(newDelay, metronome.getDelay());

    int oldDelay = newDelay;
    newDelay = -1;
    metronome.setDelay(newDelay);
    assertEquals(oldDelay, metronome.getDelay());

    metronome.start(false);
    newDelay = 1000;
    metronome.setDelay(newDelay);
    assertEquals(newDelay, metronome.getDelay());
    metronome.stop();

    metronome.start(true);
    Thread.sleep(600);
    newDelay = 500;
    metronome.setDelay(newDelay);
    assertEquals(newDelay, metronome.getDelay());
    metronome.stop();
  }

  /**
   * Test method for {@link metronome.Metronome#setTempo(double)}.
   */
  @Test
  final void testSetTempo()
  {
    metronome = new Metronome();
    assertEquals(DEFAULT_TEMPO, metronome.getTempo());

    double newTempo = 120.5;
    metronome.setTempo(newTempo);
    assertEquals(newTempo, metronome.getTempo(), .5);
    assertEquals(Metronome.bpmToMilli(newTempo), metronome.getDelay());
    assertEquals(newTempo, Metronome.milliToBpm(metronome.getDelay()), .5);

    newTempo = 1000;
    metronome.setTempo(newTempo);
    assertEquals(newTempo, metronome.getTempo(), .5);
    assertEquals(Metronome.bpmToMilli(newTempo), metronome.getDelay());
    assertEquals(newTempo, Metronome.milliToBpm(metronome.getDelay()), .5);

    double oldTempo = newTempo;
    newTempo = -1;
    metronome.setTempo(newTempo);
    assertEquals(oldTempo, metronome.getTempo(), .5);
  }

  /**
   * Test method for {@link metronome.Metronome#addListener(metronome.MetronomeListener)}.
   */
  @Test
  final void testListener()
  {
    metronome = new Metronome();

    assertEquals(metronome.getNumberOfListeners(), 0);

    MetronomeListener ml = new MetronomeController();
    metronome.addListener(ml);
    assertEquals(metronome.getNumberOfListeners(), 1);

    metronome.removeListener(null);
    assertEquals(metronome.getNumberOfListeners(), 1);

    metronome.removeListener(ml);
    assertEquals(metronome.getNumberOfListeners(), 0);
  }
  
  /**
   * Tests the start and stop method when metronome listeners are there.
   * @throws InterruptedException 
   */
  @Test
  final void testStartAndStopWithListeners() throws InterruptedException
  {
    metronome = new Metronome();
    assertEquals(metronome.getNumberOfListeners(), 0);

    MetronomeListener ml = new MetronomeController();
    metronome.addListener(ml);
    assertEquals(metronome.getNumberOfListeners(), 1);

    MetronomeListener ml2 = new MetronomeController();
    metronome.addListener(ml2);
    assertEquals(metronome.getNumberOfListeners(), 2);
    
    metronome.start(false);
    Thread.sleep(700);
    metronome.stop();
    assertEquals(metronome.getNumberOfListeners(), 2);
    
    metronome.addListener(null);
    metronome.start(true);
    Thread.sleep(700);
    metronome.stop();
    assertEquals(metronome.getNumberOfListeners(), 3);
    
    metronome.addListener((MetronomeListener) null);
    metronome.start(true);
    Thread.sleep(700);
    metronome.stop();
    assertEquals(metronome.getNumberOfListeners(), 4);
  }

}
