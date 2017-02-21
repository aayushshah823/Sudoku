package csc143.sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * This class extends J Component
 * Creates board for sudoku
 * @author Aayush Shah
 * @version Standard and Partial Credit for challenge
 */
public class SudokuBoard extends javax.swing.JComponent
  implements SelectedCell, MouseListener,MouseMotionListener,KeyListener {
  
  // Final static variables
  public static final int CELL_SIZE = 50;
  private static final Color G = new Color(220,220,220);
  private int selectedRow;
  private int selectedColumn;
  
  // 2d array for grid
  private int[][] grid;
  
  // variable of type sudoku base
  private SudokuBase b;
  
  
  
  
  /**
   * Coustructor of this class
   * 
   * @param b base for the sudoku
   */
  public SudokuBoard(SudokuBase b) {
    // initializing the instance variable using this referance
    this.b = b;
    this.setPreferredSize(new Dimension(b.getSize()*CELL_SIZE+4, b.getSize()*CELL_SIZE+4));
    this.setBackground(Color.white);
    // Setting the grid to total number of rows and columns
    grid = new int[b.getRows()][b.getColumns()];
    
    selectedRow = selectedColumn = 0;
    
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
    
    
    // Validating the JComponent object
    this.validate();
    // Repainting
    this.repaint();
  }
  
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void mouseClicked(MouseEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */
  @Override
  public void mousePressed(MouseEvent e){}
  
  /**
   * Update x and y co-ordinates
   * Interface method
   * @param e The event that triggered the handler
   */
  @Override
  public void mouseReleased(MouseEvent e){
    
    int x = e.getX();
    
    int y = e.getY();
    int row = (y-3)/CELL_SIZE;
    int col = (x-3)/CELL_SIZE;
    
    if(row > b.getSize()){
      row = b.getSize();
    }
    if(col > b.getSize()){
      col = b.getSize();
    }
    setSelected(row,col);
    requestFocus();
  }
  
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void mouseEntered(MouseEvent e) {}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void mouseExited(MouseEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void mouseMoved(MouseEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void mouseDragged(MouseEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void keyTyped(KeyEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void keyPressed(KeyEvent e){}
  
  /**
   * Interface method
   * @param e The event that triggered the handler
   */ 
  @Override
  public void keyReleased(KeyEvent e){}
  
  /**
   * Set the selected cell to the given row and column.
   * @param row The indicated row
   * @param col The indicated column
   */
  public void setSelected(int row, int col)
  {
    selectedRow = row;
    selectedColumn = col;
    repaint();
  }
  
  /**
   * Retrive the row of the currently selected cell.
   * @return The row in which the selected cell is located.
   */
  public int getSelectedRow(){
    
    
    return selectedRow;
  }
  
  /**
   * Retrive the column of the currently selected cell.
   * @return The column in which the selected cell is located.
   */
  public int getSelectedColumn(){
    
    return selectedColumn;
  }    
  
  /**
   * Returns the base of the sudoku board
   * @return b -- base of sudoku board
   */ 
  SudokuBase getBase() {
    return b;
  }
  
  /**
   * Override method for paint component
   * @param g the graphics
   */ 
  @Override
  public void paintComponent(Graphics g){
    
    super.paintComponent(g);
    g.setColor(java.awt.Color.WHITE);
    
    g.fillRect(0,0, b.getSize() * CELL_SIZE+3, b.getSize() * CELL_SIZE+3);
    
    // Local Variables
    int w = b.getSize()*CELL_SIZE;
    int h = b.getSize()*CELL_SIZE;
    Color startColor = Color.white;
    
    // Using for loop to color the rectangles
    for(int i = 1; i < w; i+= CELL_SIZE*b.getColumns()){ 
      Color c = startColor;
      for(int j = 1; j < h; j+= CELL_SIZE*b.getRows()){ 
        
        // calling the mehtod
        drawSubB(c,g,i,j);
        
        
        // Checking conditions to draw the color
        if(c == Color.white || ((w % 2 == 0 && h%2!=0) || (w%2 !=0 && h%2 == 0))){
          
          c = G;
          
        }
        else if((c == G) && (h % 2 == 0)) {
          c = Color.white;
        }
        
        
      }
      
      if(startColor == Color.white){
        
        startColor = G;
      }
      else{
        startColor = Color.white;
      }
    }
    
    // setting the color for border
    g.setColor(Color.black);
    g.drawRect(0,0, b.getSize() * CELL_SIZE+3, b.getSize() * CELL_SIZE+3);
    
    // Selected Row and column for grids
    g.setColor(Color.yellow);
    g.fillRect(selectedColumn*CELL_SIZE+4, selectedRow*CELL_SIZE+4, CELL_SIZE-4,CELL_SIZE-4);
    
  }
  
  
  /**
   * private method Drawing rectangles
   * @param c -- The Color
   * @param g The graphics
   * @param x The x - ordinate
   * @parma y the y co-ordinate
   */ 
  private void drawSubB(Color c, Graphics g, int x , int y){
    
    for(int i = 0; i < b.getRows();i++){
      
      for(int j =0; j < b.getColumns();j++){
        
        // DRAWS BIG WHITE RECTANGLE
        g.setColor(c);
        g.fillRect(x + j * CELL_SIZE+1, y + i * CELL_SIZE+1, CELL_SIZE, CELL_SIZE);
        
        // SMALL RECTANGLE
        g.setColor(Color.black);
        g.drawRect(2+x + j * CELL_SIZE, 2+y + i * CELL_SIZE, CELL_SIZE-3, CELL_SIZE-3);
      }
    }
    
  }
  
}
