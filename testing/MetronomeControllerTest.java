package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.BeatSelector;
import metronome.MetronomeController;
import metronome.Subdivision;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
class MetronomeControllerTest
{
  private static final double ERROR_MARGIN = .001;
  private MetronomeController controller;
  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception
  {
    controller = new MetronomeController();
  }

  /**
   * Test method for {@link metronome.MetronomeController#setTempo(double)}.
   */
  @Test
  final void testSetTempo()
  {
    assertEquals(Constants.DEFAULT_TEMPO, controller.getTempo(), ERROR_MARGIN);

    double newTempo = 100.0;
    controller.setTempo(newTempo);
    assertEquals(newTempo, controller.getTempo(), ERROR_MARGIN);

    newTempo = 100.0;
    controller.setTempo(newTempo);
    assertEquals(newTempo, controller.getTempo(), ERROR_MARGIN);

    newTempo = 256.898;
    controller.setTempo(newTempo);
    assertEquals(newTempo, controller.getTempo(), ERROR_MARGIN);

    newTempo = 20500.4;
    controller.setTempo(newTempo);
    assertEquals(newTempo, controller.getTempo(), ERROR_MARGIN);

    newTempo = 0.54;
    controller.setTempo(newTempo);
    assertEquals(newTempo, controller.getTempo(), ERROR_MARGIN);

    double oldTempo = newTempo;
    newTempo = 0.0;
    controller.setTempo(newTempo);
    assertEquals(oldTempo, controller.getTempo(), ERROR_MARGIN);

    newTempo = -30.0;
    controller.setTempo(newTempo);
    assertEquals(oldTempo, controller.getTempo(), ERROR_MARGIN);
  }

  /**
   * Test method for {@link metronome.MetronomeController#setSubdivision(metronome.Subdivision)}.
   */
  @Test
  final void testSetSubdivision()
  {
    Subdivision expectedSubdivision = Subdivision.None;
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    expectedSubdivision = Subdivision.Eigths;
    controller.setSubdivision(expectedSubdivision);
    assertEquals(expectedSubdivision, controller.getSubdivision());
//    double expectedTempo = controller.getTempo() * expectedSubdivision.getBeats();
//    assertEquals(expectedTempo, null);
    
    expectedSubdivision = Subdivision.Triplets;
    controller.setSubdivision(expectedSubdivision);
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    expectedSubdivision = Subdivision.Sixteenths;
    controller.setSubdivision(expectedSubdivision);
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    
    expectedSubdivision = Subdivision.Quintuplets;
    controller.setSubdivision(expectedSubdivision);
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    expectedSubdivision = Subdivision.None;
    controller.setSubdivision(expectedSubdivision);
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    controller.setSubdivision(null);
    assertEquals(expectedSubdivision, controller.getSubdivision());
  }

  /**
   * Test method for {@link metronome.MetronomeController#getCurrentBeat()}.
   * @throws InterruptedException 
   */
  @Test
  final void testGetCurrentBeat() throws InterruptedException
  {
    int expectedCurrentBeat = 1;
    assertEquals(expectedCurrentBeat, controller.getCurrentBeat());
    
    expectedCurrentBeat = 2;
    controller.start(true);
    Thread.sleep(250);
    assertEquals(expectedCurrentBeat, controller.getCurrentBeat());
    
    expectedCurrentBeat = 3;
    controller.start(true);
    Thread.sleep(100);
    assertEquals(expectedCurrentBeat, controller.getCurrentBeat());
    
    expectedCurrentBeat = 1;
    controller.stop();
    assertEquals(expectedCurrentBeat, controller.getCurrentBeat());
    
  }

  /**
   * Test method for {@link metronome.MetronomeController#setDelay(int)}.
   */
  @Test
  final void testSetDelay()
  {
    int newDelay = 1000;
    controller.setDelay(newDelay);
    assertEquals(newDelay, controller.getDelay(), ERROR_MARGIN);

    newDelay = 1000;
    controller.setDelay(newDelay);
    assertEquals(newDelay, controller.getDelay(), ERROR_MARGIN);

    newDelay = 2569;
    controller.setDelay(newDelay);
    assertEquals(newDelay, controller.getDelay(), ERROR_MARGIN);

    newDelay = 205004;
    controller.setDelay(newDelay);
    assertEquals(newDelay, controller.getDelay(), ERROR_MARGIN);

    newDelay = 32;
    controller.setDelay(newDelay);
    assertEquals(newDelay, controller.getDelay(), ERROR_MARGIN);

    int oldDelay = newDelay;
    newDelay = 0;
    controller.setDelay(newDelay);
    assertEquals(oldDelay, controller.getDelay(), ERROR_MARGIN);

    newDelay = -500;
    controller.setDelay(newDelay);
    assertEquals(oldDelay, controller.getDelay(), ERROR_MARGIN);
  }

  /**
   * Test method for {@link metronome.MetronomeController#start(boolean)}.
   * @throws InterruptedException 
   */
  @Test
  final void testStartAndStop() throws InterruptedException
  {
    assertFalse(controller.isRunning());
    controller.start(false);
    assertTrue(controller.isRunning());
    controller.stop();
    assertFalse(controller.isRunning());
    
    controller.start(true);
    assertTrue(controller.isRunning());
    controller.stop();
    assertFalse(controller.isRunning());
    
    controller.setSubdivision(Subdivision.Eigths);
    controller.start(true);
    assertTrue(controller.isRunning());
    Thread.sleep(2500);
    controller.stop();
    assertFalse(controller.isRunning());    
  }
  
  @Test
  final void testActionPerformed()
  {
    char d = Constants.DELIMITER;
    
    // Start the met
    String command = Constants.START;
    ActionEvent ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertTrue(controller.isRunning());
    
    // Stop the met
    command = Constants.STOP;
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertFalse(controller.isRunning());
    
    // Increment met
    double expectedTempo = controller.getTempo() + 1;
    command = Constants.INCREMENT;
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedTempo, controller.getTempo(), .1);
    
    // Decrement met
    expectedTempo = controller.getTempo() - 1;
    command = Constants.DECREMENT;
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedTempo, controller.getTempo(), .1);
    
    // Meter Change met
    JComboBox<TimeSignature> timeSignatureBox = new JComboBox<>();
    TimeSignature expectedTimeSignature = TimeSignature.getTimeSignature(3, 4);
    timeSignatureBox.addItem(expectedTimeSignature);
    timeSignatureBox.setSelectedItem(expectedTimeSignature);
    command = Constants.METER_CHANGE;
    ev = new ActionEvent(timeSignatureBox, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedTimeSignature, controller.getTimeSignature());
    
    // Beat Click Type Change
    command = String.format("%s%c%s%c%s%c", BeatSelector.BEAT_COMMAND, d, 2, d, 2, d);
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertEquals(2, controller.getClickTypes().get(1));
    
    // Subdivision Change
    assertEquals(Subdivision.None, controller.getSubdivision());
    JComboBox<Subdivision> subdivisionBox = new JComboBox<>();
    Subdivision expectedSubdivision = Subdivision.Triplets;
    subdivisionBox.addItem(expectedSubdivision);
    subdivisionBox.setSelectedItem(expectedSubdivision);
    command = Constants.SUBDIVISION_CHANGE;
    ev = new ActionEvent(subdivisionBox, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedSubdivision, controller.getSubdivision());
    
    // Tempo Change
    assertEquals(120.0, controller.getTempo(), .1);
    expectedTempo = 152.0;
    command = String.format("%s%c%s%c", Constants.TEMPO_CHANGE, d, expectedTempo, d);
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedTempo, controller.getTempo(), .1);
    
    // Default
    command = "Bad String";
    ev = new ActionEvent(command, 0, command);
    controller.actionPerformed(ev);
    assertEquals(expectedTempo, controller.getTempo(), .1);
  }

}
