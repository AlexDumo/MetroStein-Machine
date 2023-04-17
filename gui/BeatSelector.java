package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class BeatSelector extends JButton implements ActionListener
{
  public static enum State
  {
    ACCENT, NORMAL, OFF;
  }
  public static final String BEAT_COMMAND = "beat";
  
  private static final Color 
  ACCENT_COLOR = Color.MAGENTA,
  NORMAL_COLOR = Color.BLUE,
  OFF_COLOR = Color.GRAY,
  ACTIVE_COLOR = Color.GREEN;

  private State currentState;
  private boolean isActive;
  private Color currentColor;

  /**
   * Constructs a button with the NORMAL state.
   * 
   * @param text
   *          the text to add in the button.
   */
  public BeatSelector(final String text)
  {
    this(text, State.NORMAL);
  }

  /**
   * Constructs a button with the given state.
   * 
   * @param text
   *          the text to add in the button.
   */
  public BeatSelector(final String text, final State state)
  {
    super(text);
    isActive = false;
    
    // Set the button attributes
    setActionCommand(BEAT_COMMAND + text);
    setOpaque(true);
    SetState(state);
    addActionListener(this);
  }

  /**
   * Cycles the current State in the order of ACCENT, NORMAL, and OFF. Also changes the color of the
   * this BeatSelector.
   * 
   * @return the new State.
   */
  private State CycleState()
  {
    switch (currentState)
    {
      case ACCENT:
        SetState(State.NORMAL);
        break;
      case NORMAL:
        SetState(State.OFF);
        break;
      case OFF:
        SetState(State.ACCENT);
        break;
    }
    return currentState;
  }
  
  /**
   * Sets the current State. Also changes the color of the
   * this BeatSelector.
   * 
   * @return the new State.
   */
  private State SetState(final State newState)
  {
    System.out.println("Cycling state " + currentState);
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
    }
    return currentState;
  }
  
  /**
   * Sets the BeatSelector foreground and background color.
   * @param color the Color to set to.
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
    if(isActive)
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
    CycleState();
//    toggleActive();
//    this.se
  }

}
