package metronome;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import auditory.sampled.BoomBox;
import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import io.ResourceFinder;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 * 
 *         All this class does is click.
 */
public class ClickMachine
{
  public static final int CLICK_MIN = -1, CLICK_MAX = 3, CLICK_DEFAULT = 1, CLICK_OFF = -1,
      CLICK_ACCENT = 0, CLICK_SECONDARY_ACCENT = 2, CLICK_SUBDIVISION = 3;
  public static final String[] CLICKER_NAMES = {"0.wav", "1.wav", "2.wav", "3.wav"};

  private BoomBox[] boomboxes;
  private BufferedSound[] clickSounds;

  /**
   * Default constructor.
   */
  public ClickMachine()
  {
    clickSounds = new BufferedSound[CLICKER_NAMES.length];
    boomboxes = new BoomBox[CLICKER_NAMES.length];
    for (int i = 0; i < CLICKER_NAMES.length; i++)
    {
      clickSounds[i] = loadSound(CLICKER_NAMES[i]);
      boomboxes[i] = new BoomBox(clickSounds[i]);
    }
  }

  /**
   * Loads the sound with the given filename.
   * 
   * @param filename
   *          the sound file to load
   * 
   * @return BufferedSound with the audio in the file. Sine wave if not found.
   * 
   * @throws Exception
   *           The file couldn't be found in the resources package.
   */
  protected static BufferedSound loadSound(final String filename)
  {
    BufferedSound sound = null;
    BufferedSoundFactory factory;
    ResourceFinder finder;

    finder = ResourceFinder.createInstance(resources.Marker.class);
    factory = new BufferedSoundFactory(finder);

    try
    {
      sound = factory.createBufferedSound(filename);
    }
    catch (final IOException e)
    {
      System.out.println("File not found!");
      sound = factory.createBufferedSound(200, // frequency
          250000, // length
          4000.0f, // sampling rate
          1000.0f // amplitude
      );
    }
    catch (UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }

    return sound;
  }

  /**
   * Plays a click from strong (0) to weak (3).
   * 
   * @param clickNum
   *          the type of click to use [0-3]. 1 is the default.
   */
  public void click(final int clickNum)
  {
    // System.out.println("Click " + clickNum);
    try
    {
      if (clickNum < CLICK_MIN || clickNum > CLICK_MAX)
        boomboxes[CLICK_DEFAULT].start(false);
      else if (clickNum != -1)
      {
        boomboxes[clickNum].start(false);
      }
        
    }
    catch (LineUnavailableException e)
    {
      System.out.println("Can't play!");
      e.printStackTrace();
    }
  }
}
