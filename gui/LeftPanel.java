package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JSlider;
import javax.swing.JTextArea;

import main.MetroSteinMachine;
import metronome.MetronomeController;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class LeftPanel extends MetronomePanel
{
  /**
   * Default constructor.
   */
  public LeftPanel()
  {
    super(new GridBagLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));

    JSlider accentSlider, eigthSlider, sixteenthSlider, tripletSlider;

    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.ipadx = 10;
    c.ipady = 10;
    c.fill = GridBagConstraints.HORIZONTAL;
    this.add(new JTextArea("AC"), c);

    c.gridy = 1;
    c.ipadx = 10;
    this.add(new JTextArea("8th"), c);

    c.gridy = 2;
    c.ipadx = 10;
    this.add(new JTextArea("trip"), c);

    c.gridy = 3;
    c.ipadx = 10;
    this.add(new JTextArea("16th"), c);

    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 4;
    accentSlider = new JSlider(0, 127, 0);
    accentSlider.setBounds(0, 0, 50, 0);
    this.add(accentSlider, c);

    c.gridy = 1;
    eigthSlider = new JSlider(0, 127, 0);
    this.add(eigthSlider, c);

    c.gridy = 2;
    tripletSlider = new JSlider(0, 127, 0);
    this.add(tripletSlider, c);

    c.gridy = 3;
    sixteenthSlider = new JSlider(0, 127, 0);
    this.add(sixteenthSlider, c);
  }

  /**
   * Constructs the left panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController
   *          the MetronomeController to add to the listeners.
   */
  public LeftPanel(MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }

  @Override
  public void setMetronomeListeners(MetronomeController metronomeController)
  {
  }

}
