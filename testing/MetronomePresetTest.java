package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metronome.MetronomePreset;
import metronome.Subdivision;
import metronome.TimeSignature;

class MetronomePresetTest
{
  private MetronomePreset preset;
  private ArrayList<Integer> defaultClicks;

  @BeforeEach
  void setUp()
  {
    preset = new MetronomePreset();

    defaultClicks = new ArrayList<>();
    defaultClicks.add(0);
    defaultClicks.add(1);
    defaultClicks.add(1);
    defaultClicks.add(1);
  }

  @Test
  void testDefaultConstructor()
  {
    assertEquals(120, preset.getTempo());
    assertEquals(Subdivision.None, preset.getSubdivision());
    assertEquals(TimeSignature.getDefaultTimeSignature(), preset.getTimeSignature());
    assertEquals(defaultClicks, preset.getClickTypes());
  }

  @Test
  void testParameterizedConstructor()
  {
    ArrayList<Integer> clickTypes = new ArrayList<>();
    clickTypes.add(0);
    clickTypes.add(2);
    clickTypes.add(1);
    MetronomePreset newPreset = new MetronomePreset(80.0, Subdivision.Triplets,
        TimeSignature.getTimeSignature(3, 4), clickTypes);
    assertEquals(80.0, newPreset.getTempo());
    assertEquals(Subdivision.Triplets, newPreset.getSubdivision());
    assertEquals(TimeSignature.getTimeSignature(3, 4), newPreset.getTimeSignature());
    assertEquals(clickTypes, newPreset.getClickTypes());
  }

  @Test
  void testSetTempo()
  {
    preset.setTempo(90);
    assertEquals(90, preset.getTempo());
  }

  @Test
  void testSetSubdivision()
  {
    preset.setSubdivision(Subdivision.Quintuplets);
    assertEquals(Subdivision.Quintuplets, preset.getSubdivision());
  }

  @Test
  void testSetTimeSignature()
  {
    preset.setTimeSignature(TimeSignature.getTimeSignature(5, 4));
    assertEquals(TimeSignature.getTimeSignature(5, 4), preset.getTimeSignature());
    
    preset.setTimeSignature(null);
    assertEquals(TimeSignature.getTimeSignature(5, 4), preset.getTimeSignature());
  }

  @Test
  void testSetClickType()
  {
    ArrayList<Integer> clickTypes = new ArrayList<>();
    clickTypes.add(0);
    clickTypes.add(2);
    clickTypes.add(1);
    preset.setClickTypes(clickTypes);

    preset.setClickType(2, 3);
    assertEquals(3, preset.getClickTypes().get(1));

    preset.setClickType(1, -1);
    assertEquals(-1, preset.getClickTypes().get(0));

    // Invalid Click Type
    preset.setClickType(4, 4);
    assertEquals(1, preset.getClickTypes().get(3));

    preset.setClickType(4, 0); // Change to 0 to make sure it's changing to default (1).
    assertEquals(0, preset.getClickTypes().get(3));

    preset.setClickType(4, -5);
    assertEquals(1, preset.getClickTypes().get(3));

    // Invalid Beat. Make sure click types don't change
    preset.setClicksToDefault();
    assertEquals(defaultClicks, preset.getClickTypes());
    
    preset.setClickType(0, 2);
    assertEquals(defaultClicks, preset.getClickTypes());

    preset.setClickType(5, 2);
    assertEquals(defaultClicks, preset.getClickTypes());
  }

  @Test
  void testSetClickTypes()
  {
    ArrayList<Integer> clickTypes = new ArrayList<>();
    clickTypes.add(0);
    clickTypes.add(2);
    clickTypes.add(1);
    clickTypes.add(1);
    preset.setClickTypes(clickTypes);
    assertEquals(clickTypes, preset.getClickTypes());

    preset.setClickTypes(null);
    assertEquals(clickTypes, preset.getClickTypes());

    clickTypes.set(3, 3);
    preset.setClickTypes(clickTypes);
    assertEquals(clickTypes, preset.getClickTypes());
    
    // Test not enough info
    clickTypes = new ArrayList<>();
    clickTypes.add(0);
    clickTypes.add(2);
    preset.setClickTypes(clickTypes);
    clickTypes.add(1);
    clickTypes.add(1);
    assertEquals(clickTypes, preset.getClickTypes());
  }
}
