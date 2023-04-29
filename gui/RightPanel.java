package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import metronome.MetronomeController;
import metronome.MetronomeObserver;
import metronome.MetronomeSubject;
import metronome.Subdivision;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public class RightPanel extends MetronomePanel implements ActionListener, MetronomeObserver
{
  private BeatSelectorPanel beatSelectorPanel;
  private JComboBox<TimeSignature> timeSignatureComboBox;
  private JComboBox<Subdivision> subdivisionComboBox;

  /**
   * Constructs the right panel of the metronome.
   */
  public RightPanel()
  {
    super(new BorderLayout());
    this.setPreferredSize(new Dimension(Constants.WIDTH / 3, Constants.HEIGHT - 30));

    // Time Signature Selector
    timeSignatureComboBox = new JComboBox<>(TimeSignature.TIME_SIGNATURES_4);
    timeSignatureComboBox.setSelectedItem(TimeSignature.getDefaultTimeSignature());
    timeSignatureComboBox.setActionCommand(Constants.METER_CHANGE);
    timeSignatureComboBox.addActionListener(this);
    this.add(timeSignatureComboBox, BorderLayout.PAGE_START);

    // Subdivision Selector
    subdivisionComboBox = new JComboBox<Subdivision>(Subdivision.values());
    subdivisionComboBox.setSelectedItem(Subdivision.None);
    subdivisionComboBox.setActionCommand(Constants.SUBDIVISION_CHANGE);
    timeSignatureComboBox.addActionListener(this);
    this.add(subdivisionComboBox, BorderLayout.SOUTH);

    // Beat Type Selectors
    beatSelectorPanel = new BeatSelectorPanel(TimeSignature.getDefaultTimeSignature());
    this.add(beatSelectorPanel, BorderLayout.CENTER);
  }

  /**
   * Constructs the right panel of the metronome and adds a MetronomeController to the necessary
   * listeners.
   * 
   * @param metronomeController
   *          the MetronomeController to add to the listeners.
   */
  public RightPanel(final MetronomeController metronomeController)
  {
    this();
    setMetronomeListeners(metronomeController);
  }

  @Override
  public void setMetronomeListeners(final MetronomeController metronomeController)
  {
    beatSelectorPanel.addActionListener(metronomeController);
    timeSignatureComboBox.addActionListener(metronomeController);
    subdivisionComboBox.addActionListener(metronomeController);

    metronomeController.addObserver(beatSelectorPanel);
    metronomeController.addObserver(this);
    metronomeController.addMetronomeListener(beatSelectorPanel);
    metronomeController.addFrequentObserver(beatSelectorPanel);

  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // beatSelectorPanel.updateBeatSelectors((TimeSignature) ((JComboBox<TimeSignature>)
    // e.getSource()).getSelectedItem());
  }

  @Override
  public void update(final MetronomeSubject metronomeSubject)
  {
    MetronomeController metronomeController = (MetronomeController) metronomeSubject;
    // Check if the time signature has changed.
    if (!timeSignatureComboBox.getSelectedItem().equals(metronomeController.getTimeSignature()))
      timeSignatureComboBox
          .setSelectedItem(((MetronomeController) metronomeSubject).getTimeSignature());
  }

}
