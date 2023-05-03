package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metronome.SubdivisionController;
import metronome.TimeSignature;

class SubdivisionControllerTest
{
  private SubdivisionController subdivisionController;
  private ArrayList<Integer> defaultClickTypes;

  @BeforeEach
  void setUp() throws Exception
  {
    subdivisionController = new SubdivisionController();
    defaultClickTypes = new ArrayList<>();
    defaultClickTypes.add(-1);
    defaultClickTypes.add(3);
  }

  @Test
  final void testSetTimeSignature()
  {
    assertEquals(defaultClickTypes, subdivisionController.getClickTypes());

    defaultClickTypes.add(3);
    subdivisionController.setTimeSignature(TimeSignature.getTimeSignature(3, 4));
    assertEquals(defaultClickTypes, subdivisionController.getClickTypes());

    subdivisionController.setTimeSignature(null);
    assertEquals(defaultClickTypes, subdivisionController.getClickTypes());
  }

  @Test
  final void testStart() throws InterruptedException
  {
    assertFalse(subdivisionController.isRunning());
    
    subdivisionController.start(false);
    assertTrue(subdivisionController.isRunning());
    
    subdivisionController.stop();
    assertFalse(subdivisionController.isRunning());
  }

}
