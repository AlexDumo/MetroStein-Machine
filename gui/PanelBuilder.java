package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import main.MetroSteinMachine;
import metronome.MetronomeController;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 */
public class PanelBuilder
{
  /**
//   * Constructs the left side of the metronome.
//   * 
//   * @return the JPanel with all of the components
//   */
//  public static JPanel buildLeftPane(MetroSteinMachine metroSteinMachine)
//  {
//    JSlider accentSlider, eigthSlider, sixteenthSlider, tripletSlider;
//
//    JPanel leftPanel = new JPanel(new GridBagLayout());
//    leftPanel.setPreferredSize(new Dimension(metroSteinMachine.WIDTH / 3, metroSteinMachine.HEIGHT - 30));
//    GridBagConstraints c = new GridBagConstraints();
//
//    c.gridx = 0;
//    c.gridy = 0;
//    c.gridwidth = 1;
//    c.ipadx = 10;
//    c.ipady = 10;
//    c.fill = GridBagConstraints.HORIZONTAL;
//    leftPanel.add(new JTextArea("AC"), c);
//
//    c.gridy = 1;
//    c.ipadx = 10;
//    leftPanel.add(new JTextArea("8th"), c);
//
//    c.gridy = 2;
//    c.ipadx = 10;
//    leftPanel.add(new JTextArea("trip"), c);
//
//    c.gridy = 3;
//    c.ipadx = 10;
//    leftPanel.add(new JTextArea("16th"), c);
//
//    c.gridx = 1;
//    c.gridy = 0;
//    c.gridwidth = 3;
//    accentSlider = new JSlider(0, 127, 0);
//    leftPanel.add(accentSlider, c);
//
//    c.gridy = 1;
//    eigthSlider = new JSlider(0, 127, 0);
//    leftPanel.add(eigthSlider, c);
//
//    c.gridy = 2;
//    tripletSlider = new JSlider(0, 127, 0);
//    leftPanel.add(tripletSlider, c);
//
//    c.gridy = 3;
//    sixteenthSlider = new JSlider(0, 127, 0);
//    leftPanel.add(sixteenthSlider, c);
//
//    return leftPanel;
//  }
//  
//  
//
//  /**
//   * Constructs the middle of the metronome.
//   * 
//   * @return the JPanel with all of the components
//   */
//  public static JPanel buildRightPane()
//  {
//    JPanel rightPanel = new JPanel(new GridBagLayout());
//    rightPanel.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 30));
//    GridBagConstraints c = new GridBagConstraints();
//    c.fill = GridBagConstraints.HORIZONTAL;
//
//    // Time Signature Selector
//    TimeSignature timeSignature = metronomeController.getTimeSignature();
//    JComboBox<TimeSignature> timeSignatureComboBox = new JComboBox<>(Constants.TIME_SIGNATURES);
//    timeSignatureComboBox.setSelectedIndex(Constants.DEFAULT_TIME_SIGNATURE_INDEX);
//    timeSignatureComboBox.setActionCommand(Constants.METER_CHANGE);
//    timeSignatureComboBox.addActionListener(this);
//    c.gridx = 0;
//    c.gridy = 0;
//    c.gridwidth = 2;
//    rightPanel.add(timeSignatureComboBox, c);
//    
//    // Beat Type Selectors
//
//    return rightPanel;
//  }
}
