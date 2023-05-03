package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JTextArea;

import metronome.MetronomeController;
import metronome.MetronomeObserver;
import metronome.MetronomeSubject;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public class MiddlePanel extends MetronomePanel
    implements ActionListener, FocusListener, MetronomeObserver
{
  private JButton startButton, incrementButton, decrementButton, tapButton;
  private JTextArea tempoInput;
  private double tempo; // This is just for form error corrections

  /**
   * Constructs the middle panel of the metronome.
   */
  public MiddlePanel()
  {
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));
    GridBagConstraints c = new GridBagConstraints();

    // Tempo Input
    tempoInput = new JTextArea(Integer.toString(Constants.DEFAULT_TEMPO));
    tempo = Constants.DEFAULT_TEMPO;
    tempoInput.addFocusListener(this);
    // Transfers focus when enter or space is pressed so the tempo updates
    tempoInput.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(final KeyEvent e)
      {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE)
        {
          e.consume();
          tempoInput.transferFocus();
        }
      }
    });
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.gridheight = 2;
    c.fill = GridBagConstraints.BOTH;
    this.add(tempoInput, c);

    // Start Button
    startButton = new JButton(Constants.START);
    startButton.setBounds(50, 50, 100, 100);
    startButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    this.add(startButton, c);

    // Increase/Decrease tempo buttons
    decrementButton = new JButton(Constants.DECREMENT);
    decrementButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(decrementButton, c);

    incrementButton = new JButton(Constants.INCREMENT);
    incrementButton.addActionListener(this);
    c.gridx = 1;
    this.add(incrementButton, c);

    // Tap tempo Button
    tapButton = new TapTempoButton("Tap");
    tapButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    c.gridheight = 2;
    this.add(tapButton, c);
  }

  /**
   * Constructs the middle panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController
   *          the MetronomeController to add to the listeners.
   */
  public MiddlePanel(final MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }

  /**
   * @return the startButton
   */
  public JButton getStartButton()
  {
    return startButton;
  }

  /**
   * @return the tempoInput
   */
  public JTextArea getTempoInput()
  {
    return tempoInput;
  }

  /**
   * For use by the update method for the observer pattern. Updates the JTextArea.
   * 
   * @param newTemo
   */
  private void setTempo(final double newTemo)
  {
    tempo = newTemo;
    DecimalFormat df = new DecimalFormat("#.##");
    tempoInput.setText(df.format(newTemo));
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    switch (e.getActionCommand())
    {
      case Constants.START:
        startButton.setActionCommand(Constants.STOP);
        startButton.setText(Constants.STOP);
        break;
      case Constants.STOP:
        startButton.setActionCommand(Constants.START);
        startButton.setText(Constants.START);
        break;
//      case Constants.INCREMENT:
//        tempo = (int) (tempo + 1);
//        setTempo((double) tempo);
//        break;
//      case Constants.DECREMENT:
//        tempo = (int) (tempo - 1);
//        setTempo((double) tempo);
//        break;
      default:
        break;

    }
  }

  @Override
  public void focusGained(final FocusEvent e)
  {
    // Does Nothing
    // System.out.println("You're in Focus! " + ((JTextArea) e.getSource()).getText());

  }

  @Override
  public void focusLost(final FocusEvent e)
  {
    // System.out.println("You're out of Focus! " + ((JTextArea) e.getSource()).getText());
    try
    {
      double newTempo = Double.parseDouble(((JTextArea) e.getSource()).getText());
      if (newTempo <= 0)
        newTempo = Constants.DEFAULT_SLOW;
      else if (newTempo > Constants.MAX_TEMPO)
        newTempo = Constants.MAX_TEMPO;
      tempo = newTempo;
    }
    catch (NumberFormatException exception)
    {
      System.out.println("Not a valid number");
    }
    // Sets the new tempo when an error is caught. If no error, this is the same anyway.
    setTempo(tempo);
  }

  @Override
  public void setMetronomeListeners(final MetronomeController metronomeController)
  {
    startButton.addActionListener(metronomeController);
    incrementButton.addActionListener(metronomeController);
    decrementButton.addActionListener(metronomeController);
    tempoInput.addFocusListener(metronomeController);
    tapButton.addActionListener(metronomeController);

    metronomeController.addObserver(this);
  }

  @Override
  public void update(final MetronomeSubject metronomeSubject)
  {
    setTempo(((MetronomeController) metronomeSubject).getTempo());
  }

}
