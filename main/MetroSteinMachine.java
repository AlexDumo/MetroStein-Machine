package main;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import app.JApplication;
import gui.LeftPanel;
import gui.MetronomePanel;
import gui.MiddlePanel;
import gui.RightPanel;
import metronome.MetronomeController;
import resources.Constants;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
public class MetroSteinMachine extends JApplication
{
  private MetronomeController metronomeController;
  private MetronomePanel leftPanel, middlePanel, rightPanel;

  // private JButton middlePane.getStartButton();
  // private JTextArea middlePane.getTempoInput();

  /**
   * Default constructor. Creates a JApplication with default HEIGHT and WIDTH.
   * 
   * @param args
   *          the arguments to pass.
   */
  public MetroSteinMachine(final String[] args)
  {
    super(args, Constants.WIDTH, Constants.HEIGHT);
  }

  /**
   * Main method.
   * 
   * @param args
   *          Arguments to pass.
   */
  public static void main(final String[] args)
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
    
//    JButton testButton = new JButton("test");
//    testButton.addActionListener(metronomeController);
//    contentPane.add(testButton, BorderLayout.SOUTH);

    // Add the title
    title = new JTextArea("MetroStein Machine");
    title.setEditable(false);
    title.setFont(new Font("Tahoma", Font.BOLD, 40));
    contentPane.add(title, BorderLayout.PAGE_START);
    //    contentPane.add(new JTextArea("120"), BorderLayout.CENTER);

    // ----------------- Left Pane -----------------
    leftPanel = new LeftPanel(metronomeController);
    contentPane.add(leftPanel, BorderLayout.LINE_START);

    // ---------------- Middle Pane ----------------
    middlePanel = new MiddlePanel(metronomeController);
    contentPane.add(middlePanel, BorderLayout.CENTER);
    // contentPane.add(buildMiddlePane(), BorderLayout.CENTER);

    // ---------------- Right Pane -----------------
    rightPanel = new RightPanel(metronomeController);
    contentPane.add(rightPanel, BorderLayout.LINE_END);
    
    metronomeController.notifyObservers();
  }

}
