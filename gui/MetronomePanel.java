package gui;

import java.awt.LayoutManager;
import javax.swing.JPanel;

import metronome.MetronomeController;

/**
 * @author Alexander Dumouchelle
 *
 *         This work complies with the JMU Honor Code.
 */
@SuppressWarnings("serial")
public abstract class MetronomePanel extends JPanel
{

  /**
   * Creates a new <code>MetronomePanel</code> with a double buffer and a flow layout.
   */
  public MetronomePanel()
  {
    super();
  }

  /**
   * Creates a new <code>MetronomePanel</code> with <code>FlowLayout</code> and the specified
   * buffering strategy. If <code>isDoubleBuffered</code> is true, the <code>MetronomePanel</code>
   * will use a double buffer.
   *
   * @param isDoubleBuffered
   *          a boolean, true for double-buffering, which uses additional memory space to achieve
   *          fast, flicker-free updates
   */
  public MetronomePanel(final boolean isDoubleBuffered)
  {
    super(isDoubleBuffered);
  }

  /**
   * Creates a new MetronomePanel with the specified layout manager and buffering strategy.
   *
   * @param layout
   *          the LayoutManager to use
   * @param isDoubleBuffered
   *          a boolean, true for double-buffering, which uses additional memory space to achieve
   *          fast, flicker-free updates
   */
  public MetronomePanel(final LayoutManager layout, final boolean isDoubleBuffered)
  {
    super(layout, isDoubleBuffered);
  }

  /**
   * Create a new buffered MetronomePanel with the specified layout manager.
   *
   * @param layout
   *          the LayoutManager to use
   */
  public MetronomePanel(final LayoutManager layout)
  {
    super(layout);
  }

  /**
   * Adds the given MetronomeController as an action/focus listener to all of the necessary
   * components.
   * 
   * @param metronomeController
   */
  public abstract void setMetronomeListeners(MetronomeController metronomeController);

}
