package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
public class LeftPanel extends MetronomePanel implements MetronomeObserver
{
  private JTextArea dqnText, eigthText, halfText;
  private JButton dqnButton, eigthButton, halfButton;

  /**
   * Default constructor.
   */
  public LeftPanel()
  {
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.ipadx = 10;
    c.ipady = 10;
    c.fill = GridBagConstraints.HORIZONTAL;
    eigthButton = new JButton("eigth");
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
   * @param newTempo
   */
  public void setTempo(final double newTempo)
  {
    DecimalFormat df = new DecimalFormat("#.##");
    String cmdFormat = "%s%c%s%c", tempoCmd = Constants.TEMPO_CHANGE;
    char del = Constants.DELIMITER;
    double multTempo;

    multTempo = newTempo * 2;
    eigthText.setText(df.format(multTempo));
    eigthButton.setActionCommand(String.format(cmdFormat, tempoCmd, del, multTempo, del));

    multTempo = newTempo / 1.5;
    dqnText.setText(df.format(multTempo));
    dqnButton.setActionCommand(String.format(cmdFormat, tempoCmd, del, multTempo, del));

    multTempo = newTempo / 2;
    halfText.setText(df.format(multTempo));
    halfButton.setActionCommand(String.format(cmdFormat, tempoCmd, del, multTempo, del));

  }

  @Override
  public void setMetronomeListeners(final MetronomeController metronomeController)
  {
    dqnButton.addActionListener(metronomeController);
    eigthButton.addActionListener(metronomeController);
    halfButton.addActionListener(metronomeController);

    metronomeController.addObserver(this);
  }

  @Override
  public void update(final MetronomeSubject metronomeSubject)
  {
    setTempo(((MetronomeController) metronomeSubject).getTempo());
  }

}
