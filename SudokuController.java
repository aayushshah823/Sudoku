package csc143.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  Runs the sudoku game by putting together the model and view and adding in the
 * controller.
 * @author Aayush Shah
 * @version Final Sudoku
 */
public class SudokuController implements ActionListener
{
  
  // Instance variables used
  private SudokuView sudokuView;
  private SudokuBase sudokuBase;
  private int size;
  private JButton newWindowButton;
  private JFrame win;
  private JFrame newFrameWin;
  private JLabel error;
  private JButton button;
  private JButton fixGiven;
  
  /**
   * Constructor for objects of class SudokuController
   * @param view SudokuView
   * @param b SudokuBase
   */
  public SudokuController(SudokuView view, SudokuBase b)
  {
    // Initialize instance variables
    error = new JLabel();
    final Toolkit tk = view.getToolkit();   
    
    sudokuView = view;
    sudokuBase = b;
    size = sudokuBase.getSize()+1;
    
    
    // Adding buttons to the j panel
    JPanel bpanel  = new JPanel(new GridLayout(size,1,0,5));
    
    // Adding to window
    win = new JFrame("Sudoku");
    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    win.add(view, BorderLayout.CENTER);
    
    // Creating new button for new game (Standard version)
    newWindowButton = new JButton("New Game");
    newWindowButton.addActionListener(this);
    newWindowButton.setFocusable(false);
    sudokuView.setFocusable(true);
    
    // Fix Given Valuees button for std. version
    fixGiven = new JButton("Fix The Given & Start Playing?");
    fixGiven.addActionListener(this);
    
    // Extrapanel with new game and fix given button
    JPanel extraButtonPanel = new JPanel(new GridLayout(2,1));
    extraButtonPanel.add(newWindowButton, JPanel.CENTER_ALIGNMENT);
    
    extraButtonPanel.add(fixGiven, JPanel.BOTTOM_ALIGNMENT);
    extraButtonPanel.setBackground(Color.orange);
    fixGiven.setVisible(false);
    
    // Adding it to window.
    win.add(extraButtonPanel, BorderLayout.EAST);
    sudokuView.addKeyListener(new MyKeyListener(sudokuView));
    
    // Using for loop to add buttons to window
    for(int i = 0; i < size; i++)
    {
      button = (new JButton("" + i));
      button.setBackground(Color.gray);
      button.addActionListener(this);
      button.addMouseListener(new MyMouseListener(button));
      button.setFocusable(false);
      bpanel.add(button);
      
    }
    // Addinng panel of button
    win.add(bpanel, BorderLayout.WEST);
    
    // mini displays for showing the state of the game
    JPanel stateDisplay = new JPanel();
    
    // Calling the state displayers in order to report when duplicate found
    RowStateDisplayer rows = new RowStateDisplayer(sudokuBase);
    ColumnStateDisplayer col = new ColumnStateDisplayer(sudokuBase);
    RegionStateDisplayer reg = new RegionStateDisplayer(sudokuBase);
    
    // Adding it to display
    stateDisplay.add(rows);
    stateDisplay.add(col);
    stateDisplay.add(reg);
    stateDisplay.setBackground(Color.orange);
    
    
    // Adding it to window
    win.add(stateDisplay, BorderLayout.SOUTH);
    bpanel.setBackground(Color.orange);
    
    win.repaint();
    win.pack();
    win.setVisible(true);
  }
  
  /**
   * Action Performed Method
   * @param e Action Event e
   */
  public void actionPerformed(ActionEvent e)
  {
    String name = e.getActionCommand();
    // Creating temp jbutton for getting text
    JButton temp = (JButton)(e.getSource());
    // Using try catch blcok to check name to do accordingly
    try{
      if(temp.getText().equals("New Game")){
        // Setting up new Window
        CreateSetUpFrame setUpFrame = new CreateSetUpFrame();
        win.setEnabled(false);
      }
      if(name.equals("Fix The Given & Start Playing?"))
      {
        // If this pressed than setting new window button to false;
        newWindowButton.setEnabled(false);
        fixGiven.setEnabled(false);
        // Setting the fix givens to true
        win.setEnabled(true);
        sudokuBase.fixGivens();
      }
      
      // If person clicks fix given then beeps the sound
      if(sudokuBase.isGiven(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn()))
      {
        Toolkit.getDefaultToolkit().beep();
        return;
      }
      
      // Setting the value given by user
      sudokuBase.setValue(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn(), Integer.valueOf(name));
      
    }
    // Catch block to catch the exception
    catch (NumberFormatException ex) {
      error.setForeground(java.awt.Color.red);
      error.setText("Not Valid Input " + ex.getMessage());
    } catch(IllegalArgumentException ex) {
      error.setForeground(java.awt.Color.red);
      error.setText("Error: " + ex.getMessage());
    }
    
  }
  
  /**
   * Runs the sudoku game by putting together the model and view and adding in the
   * controller. This runs the game in a 2x3 arrangement with a pre-set board However there is standard version belwo.
   *
   * @param args The command-line arguments
   */
  public static void main(String[] args) {
    // Sudoku components
    SudokuView view = new SudokuView(makeBoard());
    SudokuBase base = view.getBase();
    SudokuController sc = new SudokuController(view,base);
  }
  
  
  
  /**
   * Creates a basic 2x3 board with set given values.
   * 
   * @return The sudoku board that was made
   */
  public static SudokuBase makeBoard() {
    SudokuBase board = new SudokuModel(2, 3);
    board.setValue(0, 3, 6);
    board.setValue(0, 5, 1);
    board.setValue(1, 2, 4);
    board.setValue(1, 4, 5);
    board.setValue(1, 5, 3);
    board.setValue(2, 3, 3);
    board.setValue(3, 2, 6);
    board.setValue(4, 0, 2);
    board.setValue(4, 1, 3);
    board.setValue(4, 3, 1);
    board.setValue(5, 0, 6);
    board.setValue(5, 2, 1);
    board.fixGivens();
    return board;
  }
  
  
  /**
   * Private class for column state displayer yellow box
   * Implements Observer
   * Using paint component to draw the yellow board and setting all colors using swtich case in it
   */ 
  private class ColumnStateDisplayer extends JPanel implements java.util.Observer{
    // Instance of sudokubase
    SudokuBase base;
    
    /**
     * Constructor for this private class
     * @param base Sudokubase base
     */ 
    public ColumnStateDisplayer(SudokuBase base) {
      setBackground(Color.orange);
      this.base = base;
      setPreferredSize(new Dimension(40,40));
      this.validate();
      base.addObserver(this);
      repaint();
    }
    
    /**
     * Updates the displayer every time the model sends out a notification.
     * Repaints whenever a change is made.
     * 
     * @param o The observable object
     * @param arg Extra parameter to indicate specific changes
     */
    public void update(java.util.Observable o, Object arg) {
      repaint();
    }
    
    /**
     * Draws the state displayer.
     * 
     * @param g The graphics object used to draw
     */
    @Override
    public void paintComponent(Graphics g) {
      
      
      super.paintComponent(g);
      int width = getWidth()/base.getSize();
      int height = getWidth();
      for(int i = 0; i < base.getSize(); i++){
        SudokuBase.State state = base.getColumnState(i);
        switch(state){
          case ERROR:
            g.setColor(Color.RED);
            break;
            
          case COMPLETE:
            g.setColor(Color.GREEN);
            break;
            
          case INCOMPLETE:
            g.setColor(Color.YELLOW);
            break;
        }
        
        g.fillRect(i*width, 0, width, height);
      }
    }
  }   
  
  
  
  
  /**
   * Private class for row state displayer yellow box
   * Implements Observer
   * Using paint component to draw the yellow board and setting all colors using swtich case in it
   */ 
  private class RowStateDisplayer extends JPanel implements java.util.Observer{
    // Instance of sudokubase
    SudokuBase base;
    
    /**
     * Constructor for this private class
     * @param base Sudokubase base
     */ 
    public RowStateDisplayer(SudokuBase base) {
      setBackground(Color.orange);
      this.base = base;
      setPreferredSize(new Dimension(40,40));
      this.validate();
      base.addObserver(this);
      repaint();
    }
    
    /**
     * Updates the displayer every time the model sends out a notification.
     * Repaints whenever a change is made.
     * 
     * @param o The objservable object
     * @param arg Extra parameter to indicate specific changes
     */
    public void update(java.util.Observable o, Object arg) {
      repaint();
    }
    
    /**
     * Draws the state displayer.
     * 
     * @param g The graphics object used to draw
     */
    @Override
    public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      int width = getWidth()/base.getSize();
      int height = getWidth();
      
      for(int i = 0; i < base.getSize(); i++){
        SudokuBase.State state = base.getRowState(i);
        switch(state){
          case ERROR:
            g.setColor(Color.RED);
            break;
            
          case COMPLETE:
            g.setColor(Color.GREEN);
            break;
            
          case INCOMPLETE:
            g.setColor(Color.YELLOW);
            break;
        }
        
        g.fillRect(0,i*width, width + 30, height);
        
        
      }
    }
  }   
  
  
  
  /**
   * Private class for region state displayer yellow box
   * Implements Observer
   * Using paint component to draw the yellow board and setting all colors using swtich case in it
   */ 
  private class RegionStateDisplayer extends JPanel implements java.util.Observer{
    // Instance of sudokubase
    SudokuBase base;
    
    /**
     * Constructor for this private class
     * @param base Sudokubase base
     */ 
    public RegionStateDisplayer(SudokuBase base) {
      this.base = base;
      setPreferredSize(new Dimension(40,40));
      this.validate();
      base.addObserver(this);
      repaint();
    }
    
    /**
     * Updates the displayer every time the model sends out a notification.
     * Repaints whenever a change is made.
     * 
     * @param o The observable object
     * @param arg Extra parameter to indicate specific changes
     */
    public void update(java.util.Observable o, Object arg) {
      repaint();
    }
    
    /**
     * Draws the state displayer.
     * 
     * @param g The graphics object used to draw
     */
    @Override
    public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      int width = getWidth()/base.getRows();
      int height = getHeight()/base.getColumns();
      
      for(int i = 0; i < base.getColumns(); i++){
        for(int j = 0; j < base.getRows(); j++){
          
          SudokuBase.State state = base.getRegionState(i*base.getRows()+j);
          
          switch(state){
            case ERROR:
              g.setColor(Color.RED);
              break;
              
            case COMPLETE:
              g.setColor(Color.GREEN);
              break;
              
            case INCOMPLETE:
              g.setColor(Color.YELLOW);
              break;
          }
          
          g.fillRect(j*width,i*height, width, height);
          
          
        } 
      }
    }
    
  }
  
  /**
   * Private class for creating frame
   * which implements action listener
   * This method creates new window when create new ga,me button is pressed
   */ 
  private class CreateSetUpFrame extends JFrame implements ActionListener
  {
    //Instance variables.
    JTextField rowVal;
    JTextField colVal;
    JLabel rowText;
    JLabel colText;
    JButton createButton;
    JButton cancelButton;
    
    // BASICALLY DIALOG BOX WHICH APPEARS ON PRESSING BUTTON
    JFrame frame;
    
    /**
     * Constructor for this method
     * Creates window and perfoems task as noted
     */ 
    public CreateSetUpFrame()
    {
      // Setting the layyout
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLayout(new FlowLayout());
      
      //Setting test, layout, size e of frame
      frame = new JFrame("Set Up Mode");
      frame.setSize(600,80);
      frame.setLayout(new FlowLayout());
      
      
      // Creating button for new game and adding action listener to it.
      createButton = new JButton("Create New Game");
      cancelButton = new JButton("Cancel");
      createButton.addActionListener(this);
      cancelButton.addActionListener(this);
      
      // Adding everything to frame
      frame.add(createButton);
      frame.add(cancelButton);
      
      // Creating JTest Field and adding to window
      rowText = new JLabel("Row Value: ");
      rowVal = new JTextField(5);
      colText = new JLabel("Col Value: ");
      colVal = new JTextField(5);
      frame.add(rowText);
      frame.add(rowVal);
      
      frame.add(colText);
      frame.add(colVal);
      frame.setVisible(true);
    }
    
    /** Action performed method for this class
      * 
      */ 
    public void actionPerformed(ActionEvent e)
    {
      String newGame = e.getActionCommand();
      JFrame setUpFrame = new JFrame();
      
      // Checking name of text button and changin it accordingly
      if(newGame.equals("Cancel"))
      {
        frame.setVisible(false);
        fixGiven.setEnabled(false);
        win.setEnabled(true);
        return;
      }
      
      else if(newGame.equals("Create New Game"))
      {
        
        try
        {
          int rowValue = Integer.parseInt(rowVal.getText());
          int colValue = Integer.parseInt(colVal.getText());
          
          if(rowValue * colValue > 12)
          {
            JOptionPane.showMessageDialog(frame,"Row * Col !>= 12");
            
          }
          
          else{
            SudokuBase newBoard = new SudokuModel(rowValue, colValue);
            SudokuView view = new SudokuView(newBoard);
            SudokuController sc = new SudokuController(view, newBoard);
            win.setVisible(false);
            frame.setVisible(false);
            sc.fixGiven.setVisible(true);
            sudokuBase.setValue(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn(), Integer.valueOf(newGame));
          }
        }catch(NumberFormatException ex) {
          error.setForeground(java.awt.Color.red);
          error.setText("Non-integer input: " + ex.getMessage());
        } catch(IllegalArgumentException ex) {
          error.setForeground(java.awt.Color.red);
          error.setText("Error: " + ex.getMessage());
        }
        
      }
      
      
    }
  }
  
  /**
   * Private method for mouse listener
   */ 
  private class MyMouseListener extends MouseAdapter{
    
    private JButton button;
    /**
     * Constructor for this class
     * @param b JButton
     */ 
    public MyMouseListener(JButton b){
      button = b; 
    }
    
    // Over ride methhos from mouse listener and changing colro accordingly
    @Override
    public void mouseEntered(MouseEvent e) {
      button.setBackground(Color.RED);
      
    }
    @Override
    public void mouseExited(MouseEvent e) {
      button.setBackground(Color.gray);
    }
    @Override
    public void mousePressed(MouseEvent e) {
      button.setBackground(Color.gray);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
      
      if(sudokuBase.isGiven(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn())) {
        Toolkit.getDefaultToolkit().beep();
        button.setBackground(Color.gray);
      }
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
  }
  
  /**
   * Private class for my ket listener efor number inputs
   * Challenge ption
   */ 
  private class MyKeyListener extends KeyAdapter{
    private SudokuView sv;
    
    /**
     * Constructor for this
     * @param sv Sudokuview for the ket
     */ 
    public MyKeyListener(SudokuView sv){
      this.sv = sv;
    }
    
    // Over ride methods for Key Adapter
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e){}
    
    
    /**
     * Key pressed methods for key board inputs
     */ 
    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
      
      int row = sudokuView.getSelectedRow();
      int column = sudokuView.getSelectedColumn();
      
      // Checking the key code and they doing stuff accordingly
      if(e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
        // If the user tries to go out of bounds, play a beep sound
        if(column == 0) {
          Toolkit.getDefaultToolkit().beep();
        }
        else {
          sudokuView.setSelected(row-1,column);
        }
      }
      
      // Checking the key code and they doing stuff accordingly
      if(e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
        if(column == sudokuBase.getSize() - 1) {
          Toolkit.getDefaultToolkit().beep();
        }
        else {
          sudokuView.setSelected(row+1, column);
        }
      }
      
      // Checking the key code and they doing stuff accordingly
      if(e.getKeyCode() == java.awt.event.KeyEvent.VK_LEFT) {
        if(row == 0) {
          Toolkit.getDefaultToolkit().beep();
        }
        else {
          sudokuView.setSelected(row, column-1);
        }
      }
      
      // Checking the key code and they doing stuff accordingly
      if(e.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
        if(row == sudokuBase.getSize() - 1) {
          Toolkit.getDefaultToolkit().beep();
        }
        else {
          sudokuView.setSelected(row, column+1);
        }
      }
      
      // Local variable for getting key code
      int a = e.getExtendedKeyCode();
      // Checking the key code and they doing stuff accordingly
      if((a >= java.awt.event.KeyEvent.VK_0 && a <= java.awt.event.KeyEvent.VK_9)){
        int num = a - java.awt.event.KeyEvent.VK_0;
        if(sudokuBase.isGiven(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn()))
        {
          Toolkit.getDefaultToolkit().beep();
          return;
        }
        sudokuBase.setValue(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn(), num);
        
      }
      
      // Checking the key code and they doing stuff accordingly
      if((a >= java.awt.event.KeyEvent.VK_NUMPAD0 && a <= java.awt.event.KeyEvent.VK_NUMPAD9)){
        int num = a - java.awt.event.KeyEvent.VK_NUMPAD0;
        if(sudokuBase.isGiven(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn()))
        {
          Toolkit.getDefaultToolkit().beep();
          return;
        }
        sudokuBase.setValue(sudokuView.getSelectedRow(), sudokuView.getSelectedColumn(), num);
      }
    }
  }
}