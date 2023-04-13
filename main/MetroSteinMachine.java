package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import app.JApplication;
import gui.MiddlePane;
import gui.PanelBuilder;
import metronome.MetronomeController;
import metronome.TimeSignature;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class MetroSteinMachine extends JApplication implements ActionListener, FocusListener
{
  public static final int WIDTH = 600;
  public static final int HEIGHT = 600;

  private MetronomeController metronomeController;
  private MiddlePane middlePane;

  private JButton startButton;
  private JTextArea tempoInput;

  /**
   * Default constructor. Creates a JApplication with default HEIGHT and WIDTH.
   * 
   * @param args
   *          the arguments to pass.
   */
  public MetroSteinMachine(final String[] args)
  {
    super(args, WIDTH, HEIGHT);
  }

  /**
   * Main method.
   * 
   * @param args
   *          Arguments to pass.
   */
  public static void main(String[] args)
  {
    JApplication app = new MetroSteinMachine(args);
    invokeInEventDispatchThread(app);
  }

  @Override
  public void init()
  {
    JPanel contentPane = (JPanel) getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Declare the met controller
    metronomeController = new MetronomeController();

    JTextArea title;

    // Add the title
    title = new JTextArea("MetroStein Machine");
    title.setEditable(false);
    title.setFont(new Font("Tahoma", Font.BOLD, 40));
    contentPane.add(title, BorderLayout.PAGE_START);
    contentPane.add(new JTextArea("120"), BorderLayout.CENTER);

    // ----------------- Left Pane -----------------
    contentPane.add(buildLeftPane(), BorderLayout.LINE_START);

    // ---------------- Middle Pane ----------------
//    middlePane = new MiddlePane();
//    middlePane.setAllActionListeners(this);
//    middlePane.getStartButton().addActionListener(metronomeController);
//    tempoInput.addFocusListener(this);
    contentPane.add(buildMiddlePane(), BorderLayout.CENTER);

    // ---------------- Right Pane -----------------
    contentPane.add(buildRightPane(), BorderLayout.LINE_END);

    // Right-side pane
  }

  /**
   * Constructs the left side of the metronome.
   * 
   * @return the JPanel with all of the components
   */
  public JPanel buildLeftPane()
  {
    JSlider accentSlider, eigthSlider, sixteenthSlider, tripletSlider;

    JPanel leftPanel = new JPanel(new GridBagLayout());
    leftPanel.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 30));
    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 1;
    c.ipadx = 10;
    c.ipady = 10;
    c.fill = GridBagConstraints.HORIZONTAL;
    leftPanel.add(new JTextArea("AC"), c);

    c.gridy = 1;
    c.ipadx = 10;
    leftPanel.add(new JTextArea("8th"), c);

    c.gridy = 2;
    c.ipadx = 10;
    leftPanel.add(new JTextArea("trip"), c);

    c.gridy = 3;
    c.ipadx = 10;
    leftPanel.add(new JTextArea("16th"), c);

    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 3;
    accentSlider = new JSlider(0, 127, 0);
    leftPanel.add(accentSlider, c);

    c.gridy = 1;
    eigthSlider = new JSlider(0, 127, 0);
    leftPanel.add(eigthSlider, c);

    c.gridy = 2;
    tripletSlider = new JSlider(0, 127, 0);
    leftPanel.add(tripletSlider, c);

    c.gridy = 3;
    sixteenthSlider = new JSlider(0, 127, 0);
    leftPanel.add(sixteenthSlider, c);

    return leftPanel;
  }

  /**
   * Constructs the middle of the metronome.
   * 
   * @return the JPanel with all of the components
   */
  public JPanel buildMiddlePane()
  {
    JPanel middlePanel = new JPanel(new GridBagLayout());
    middlePanel.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 30));
    GridBagConstraints c = new GridBagConstraints();

    // Tempo Input
    tempoInput = new JTextArea("120");
    tempoInput.addFocusListener(this);
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
    middlePanel.add(tempoInput, c);

    // Start Button
    startButton = new JButton(Constants.START);
    startButton.setBounds(50, 50, 100, 100);
    startButton.addActionListener(metronomeController);
    startButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.gridheight = 1;
    middlePanel.add(startButton, c);

    // Increase/Decrease tempo buttons
    JButton incrementButton, decrementButton, tapButton;
    decrementButton = new JButton(Constants.DECREMENT);
    decrementButton.addActionListener(this);
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 1;
    c.gridheight = 1;
    middlePanel.add(decrementButton, c);

    incrementButton = new JButton(Constants.INCREMENT);
    incrementButton.addActionListener(this);
    c.gridx = 1;
    middlePanel.add(incrementButton, c);

    return middlePanel;
  }

  /**
   * Constructs the middle of the metronome.
   * 
   * @return the JPanel with all of the components
   */
  public JPanel buildRightPane()
  {
    JPanel rightPanel = new JPanel(new GridBagLayout());
    rightPanel.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT - 30));
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    // Time Signature Selector
    TimeSignature timeSignature = metronomeController.getTimeSignature();
    JComboBox<TimeSignature> timeSignatureComboBox = new JComboBox<>(Constants.TIME_SIGNATURES);
    timeSignatureComboBox.setSelectedIndex(Constants.DEFAULT_TIME_SIGNATURE_INDEX);
    timeSignatureComboBox.setActionCommand(Constants.METER_CHANGE);
    timeSignatureComboBox.addActionListener(this);
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    rightPanel.add(timeSignatureComboBox, c);
    
    // Beat Type Selectors

    return rightPanel;
  }

  /**
   * Changes the text in the tempoInput JTextArea
   * 
   * @param newTempo
   *          the new tempo as a String.
   */
  public void setTempoText(final String newTempo)
  {
    tempoInput.setText(newTempo);
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
      case Constants.INCREMENT:
        metronomeController.setTempo(metronomeController.getTempo() + 1);
        tempoInput.setText("" + metronomeController.getTempo());
        break;
      case Constants.DECREMENT:
        metronomeController.setTempo(metronomeController.getTempo() - 1);
        tempoInput.setText("" + metronomeController.getTempo());
        break;
      case Constants.METER_CHANGE:
        JComboBox cb = (JComboBox) e.getSource();
        metronomeController.setTimeSignature((TimeSignature) cb.getSelectedItem());
        break;
      default:
        break;

    }
  }

  @Override
  public void focusGained(final FocusEvent e)
  {
    System.out.println("You're in Focus! " + ((JTextArea) e.getSource()).getText());

  }

  @Override
  public void focusLost(final FocusEvent e)
  {
    System.out.println("You're out of Focus! " + ((JTextArea) e.getSource()).getText());
    try
    {
      int newTempo = Integer.parseInt(((JTextArea) e.getSource()).getText());
      if (newTempo <= 0)
        newTempo = 60;
      else if (newTempo > 300)
        newTempo = 300;
      metronomeController.setTempo(newTempo);
    }
    catch (NumberFormatException exception)
    {
      System.out.println("Not a valid number");
    }
    // Sets the new tempo when an error is caught. If no error, this is the same anyway.
    ((JTextArea) e.getSource()).setText("" + metronomeController.getTempo());
  }

}
