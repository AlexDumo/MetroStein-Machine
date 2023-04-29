package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
public class LeftPanel extends MetronomePanel implements MetronomeObserver
{
  private double tempo;
  private JTextArea dqnText, eigthText, halfText;

  /**
   * Default constructor.
   */
  public LeftPanel()
  {
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));

    JButton dqnButton, eigthButton, halfButton;

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.ipadx = 10;
    c.ipady = 10;
    c.fill = GridBagConstraints.HORIZONTAL;
    eigthButton = new JButton("Eigth");
    this.add(eigthButton, c);

    c.gridy = 1;
    c.ipadx = 10;
    dqnButton = new JButton("dqn");
    this.add(dqnButton, c);

    c.gridy = 2;
    c.ipadx = 10;
    halfButton = new JButton("half");
    this.add(halfButton, c);

    // c.gridy = 3;
    // c.ipadx = 10;
    // this.add(new JTextArea("16th"), c);

    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 4;
    // accentSlider = new JSlider(0, 127, 0);
    // accentSlider.setBounds(0, 0, 50, 0);
    eigthText = new JTextArea("");
    this.add(eigthText, c);

    c.gridy = 1;
    // eigthSlider = new JSlider(0, 127, 0);
    dqnText = new JTextArea("");
    this.add(dqnText, c);

    c.gridy = 2;
    // tripletSlider = new JSlider(0, 127, 0);
    halfText = new JTextArea("");
    this.add(halfText, c);

    setTempo(Constants.DEFAULT_TEMPO);

    // c.gridy = 3;
    // sixteenthSlider = new JSlider(0, 127, 0);
    // this.add(sixteenthSlider, c);
  }

  /**
   * Constructs the left panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController
   *          the MetronomeController to add to the listeners.
   */
  public LeftPanel(final MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }

  /**
   * Sets the text in the tempo switch buttons.
   * 
   * @param tempo
   */
  public void setTempo(final double tempo)
  {
    eigthText.setText(Double.toString(tempo * 2));
    dqnText.setText(Double.toString(tempo / 1.5));
    halfText.setText(Double.toString(tempo / 2));

  }

  @Override
  public void setMetronomeListeners(final MetronomeController metronomeController)
  {
  }

  @Override
  public void update(final MetronomeSubject metronomeSubject)
  {
    tempo = ((MetronomeController) metronomeSubject).getTempo();
  }

}
