package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import metronome.MetronomeController;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 * This work complies with the JMU Honor Code.
 */
public class RightPanel extends MetronomePanel implements ActionListener
{    
  private JPanel beatSelectorPanel;
  private JComboBox<TimeSignature> timeSignatureComboBox;
  private TimeSignature timeSignature;
  private ArrayList<JButton> beatButtons;
  
  /**
   * Constructs the right panel of the metronome.
   */
  public RightPanel()
  {
    super(new BorderLayout());
    
    timeSignature = Constants.TIME_SIGNATURES[Constants.DEFAULT_TIME_SIGNATURE_INDEX];
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));
//    GridBagConstraints c = new GridBagConstraints();
//    c.fill = GridBagConstraints.HORIZONTAL;

    // Time Signature Selector
    timeSignatureComboBox = new JComboBox<>(Constants.TIME_SIGNATURES);
    timeSignatureComboBox.setSelectedIndex(Constants.DEFAULT_TIME_SIGNATURE_INDEX);
    timeSignatureComboBox.setActionCommand(Constants.METER_CHANGE);
    timeSignatureComboBox.addActionListener(this);
//    c.gridx = 0;
//    c.gridy = 0;
//    c.gridwidth = 2;
    this.add(timeSignatureComboBox, BorderLayout.PAGE_START);

    // Beat Type Selectors
//    c.gridy = 1;
//    c.gridwidth = 4;
    beatSelectorPanel = new JPanel(new GridLayout());
    this.add(beatSelectorPanel, BorderLayout.CENTER);
    updateBeatSelectors();
  }

  /**
   * Constructs the left panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController the MetronomeController to add to the listeners.
   */
  public RightPanel(MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }
  
  /**
   * Updates the accent selectors for beats.
   */
  private void updateBeatSelectors()
  {
    System.out.println("Updating beat selectors " + timeSignature.toString());
//    beatSelectorPanel.add(new JButton("1"));
    beatSelectorPanel.removeAll();
    beatSelectorPanel.revalidate();
    for(int i = 0; i < timeSignature.getNumerator(); i ++)
    {
      beatSelectorPanel.add(new JButton((Integer.toString(i + 1))));
    }
    beatSelectorPanel.repaint();
  }

  @Override
  public void setMetronomeListeners(MetronomeController metronomeController)
  {
    timeSignatureComboBox.addActionListener(metronomeController);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    timeSignature = (TimeSignature) ((JComboBox<TimeSignature>) e.getSource()).getSelectedItem();
    updateBeatSelectors();
  }

}
