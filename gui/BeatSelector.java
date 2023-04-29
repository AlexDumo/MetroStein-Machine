package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import metronome.ClickMachine;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public class BeatSelector extends JButton implements ActionListener
{
  /**
   * Enum that keeps track of the State of a beatSelector.
   */
  public static enum State
  {
    ACCENT(0), NORMAL(1), OFF(-1);

    private final int clickValue;

    /**
     * 
     * @param clickValue the value of the State.
     */
    private State(final int clickValue)
    {
      this.clickValue = clickValue;
    }

    /**
     * @return theClickValue of this State.
     */
    public int getClickValue()
    {
      return clickValue;
    }
  }

  public static final String BEAT_COMMAND = "beat", BEAT_CHANGE_COMMAND = "beatChange";

  private static final Color ACCENT_COLOR = Color.MAGENTA, NORMAL_COLOR = Color.BLUE,
      OFF_COLOR = Color.GRAY, ACTIVE_COLOR = Color.GREEN;

  private State currentState;
  private boolean isActive;
  private Color currentColor;
  private int beatNumber;

  /**
   * Constructs a button with the NORMAL state.
   * 
   * @param beatNumber
   *          The beat this BeatSelector is assigned to.
   */
  public BeatSelector(final int beatNumber)
  {
    this(beatNumber, State.NORMAL);
  }

  /**
   * Constructs a button with a given beat number and State.
   * 
   * @param beatNumber
   * @param state
   */
  public BeatSelector(final int beatNumber, final State state)
  {
    super(Integer.toString(beatNumber));
    isActive = false;
    setState(state);
    this.beatNumber = beatNumber;

    // Set the button attributes
    updateActionCommand();
    setOpaque(true);
    addActionListener(this);
  }

  /**
   * Updates the action command of the beat selector based on the beatNumber and currentState.
   * Format is beat${beatNumber}${clickType}.
   */
  private void updateActionCommand()
  {
    // The action command is the next state that the button will be in.
    setActionCommand(String.format("%s%c%s%c%d%c", BEAT_COMMAND, Constants.DELIMITER, beatNumber,
        Constants.DELIMITER, stateToClickType(beatNumber, cycleState(false)), Constants.DELIMITER));
  }

  /**
   * Gets the appropriate click type based on the beat number and state.
   * 
   * @param beatNumber
   * @param buttonState
   * @return The integer value of the State.
   */
  public static int stateToClickType(final int beatNumber, final State buttonState)
  {
    int output = ClickMachine.CLICK_OFF;
    switch (buttonState)
    {
      case ACCENT:
        if (beatNumber == 1)
          output = ClickMachine.CLICK_ACCENT;
        else
          output = ClickMachine.CLICK_SECONDARY_ACCENT;
        break;
      case NORMAL:
        output = ClickMachine.CLICK_DEFAULT;
        break;
      case OFF:
        output = ClickMachine.CLICK_OFF;
        break;
      default:
        break;
    }
    return output;
  }

  /**
   * Gets the appropriate State based on the click type.
   * 
   * @param clickType
   * @return the State based on the click type.
   */
  public static State clickTypeToState(final int clickType)
  {
    State output = State.OFF;
    switch (clickType)
    {
      case ClickMachine.CLICK_ACCENT:
      case ClickMachine.CLICK_SECONDARY_ACCENT:
        output = State.ACCENT;
        break;
      case ClickMachine.CLICK_DEFAULT:
        output = State.NORMAL;
        break;
      case ClickMachine.CLICK_OFF:
        output = State.OFF;
        break;
      default:
        break;
    }
    return output;
  }

  /**
   * Cycles the current State in the order of ACCENT, NORMAL, and OFF. Also changes the color of the
   * this BeatSelector.
   * 
   * @param setState
   *          true if it should set the attribute, false if it should do nothing but return the next
   *          state.
   * @return the new State.
   */
  private State cycleState(final boolean setState)
  {
    State newState = State.NORMAL;
    switch (currentState)
    {
      case ACCENT:
        newState = State.NORMAL;
        break;
      case NORMAL:
        newState = State.OFF;
        break;
      case OFF:
        newState = State.ACCENT;
        break;
      default:
        break;
    }
    if (setState)
      setState(newState);
    return newState;
  }

  /**
   * Sets the current State. Also changes the color of the this BeatSelector.
   * 
   * @param newState the State to set to.
   * @return the new State.
   */
  private State setState(final State newState)
  {
    // System.out.println("Cycling state " + currentState + " " + newState);
    switch (newState)
    {
      case NORMAL:
        currentState = State.NORMAL;
        setColor(NORMAL_COLOR);
        break;
      case OFF:
        currentState = State.OFF;
        setColor(OFF_COLOR);
        break;
      case ACCENT:
        currentState = State.ACCENT;
        setColor(ACCENT_COLOR);
        break;
      default:
        break;
    }
    updateActionCommand();
    return currentState;
  }

  /**
   * Sets the BeatSelector foreground and background color.
   * 
   * @param color
   *          the Color to set to.
   */
  private void setColor(final Color color)
  {
    currentColor = color;
    setBackground(color);
    setForeground(color);
  }

  /**
   * @return the currentState
   */
  public State getCurrentState()
  {
    return currentState;
  }

  /**
   * Toggles the color of the button to be active/inactive.
   */
  public void toggleActive()
  {
    if (isActive)
      setInactive();
    else
      setActive();
  }

  /**
   * Sets the button to active.
   */
  public void setActive()
  {
    isActive = true;
    setBackground(ACTIVE_COLOR);
  }

  /**
   * Sets the button inactive.
   */
  public void setInactive()
  {
    isActive = false;
    setBackground(currentColor);
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    cycleState(true);
  }

}
