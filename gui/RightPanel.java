package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
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
  private BeatSelectorPanel beatSelectorPanel;
  private JComboBox<TimeSignature> timeSignatureComboBox;
  
  /**
   * Constructs the right panel of the metronome.
   */
  public RightPanel()
  {
    super(new BorderLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));
    
    // Time Signature Selector
    timeSignatureComboBox = new JComboBox<>(TimeSignature.TIME_SIGNATURES);
    timeSignatureComboBox.setSelectedIndex(TimeSignature.DEFAULT_TIME_SIGNATURE_INDEX);
    timeSignatureComboBox.setActionCommand(Constants.METER_CHANGE);
    timeSignatureComboBox.addActionListener(this);
    this.add(timeSignatureComboBox, BorderLayout.PAGE_START);

    // Beat Type Selectors
    beatSelectorPanel = new BeatSelectorPanel(TimeSignature.TIME_SIGNATURES[TimeSignature.DEFAULT_TIME_SIGNATURE_INDEX]);
    this.add(beatSelectorPanel, BorderLayout.CENTER);  
  }

  /**
   * Constructs the right panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController the MetronomeController to add to the listeners.
   */
  public RightPanel(MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }

  @Override
  public void setMetronomeListeners(MetronomeController metronomeController)
  {
    beatSelectorPanel.addActionListener(metronomeController);
    timeSignatureComboBox.addActionListener(metronomeController);
    metronomeController.addMetronomeListener(beatSelectorPanel);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    beatSelectorPanel.updateBeatSelectors((TimeSignature) ((JComboBox<TimeSignature>) e.getSource()).getSelectedItem());
  }

}
