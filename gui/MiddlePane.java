package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.MetroSteinMachine;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 */
public class MiddlePane extends JPanel
{
  private JButton startButton, incrementButton, decrementButton;
  private JTextArea tempoInput;

  /**
   * Constructs the middle of the metronome.
   * 
   * @return the JPanel with all of the components
   */
  public MiddlePane()
  {    
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(MetroSteinMachine.WIDTH / 3, MetroSteinMachine.HEIGHT - 30));
    GridBagConstraints c = new GridBagConstraints();

    // Tempo Input
    tempoInput = new JTextArea("120");
//    tempoInput.addFocusListener(metroSteinMachine);
    // Transfers focus when enter or space is pressed so the tempo updates
    tempoInput.addKeyListener(new KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
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
//    startButton.addActionListener(metronomeController);
//    startButton.addActionListener(metroSteinMachine);
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    this.add(startButton, c);

    // Increase/Decrease tempo buttons
    JButton incrementButton, decrementButton, tapButton;
    decrementButton = new JButton(Constants.DECREMENT);
//    decrementButton.addActionListener(metroSteinMachine);
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 1;
    this.add(decrementButton, c);

    incrementButton = new JButton(Constants.INCREMENT);
//    incrementButton.addActionListener(metroSteinMachine);
    c.gridx = 1;
    this.add(incrementButton, c);
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
   * Adds the given ActionListener to all of the JButtons.
   * 
   * @param al The ActionListener to add.
   */
  public void setAllActionListeners(ActionListener al)
  {
    startButton.addActionListener(al);
    incrementButton.addActionListener(al);
    decrementButton.addActionListener(al);
  }
}
