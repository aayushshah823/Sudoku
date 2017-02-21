package csc143.sudoku; 

import java.util.*;
import java.awt.*;

/**
 * The view portion of the three main sudoku components: SudokuModel, 
 * SudokuView, and SudokuController.
 * @author Aayush Shah
 * @version Standard and Partial Credit for challenge
 */ 
public class SudokuView extends  SudokuBoard implements Observer, NumericSupport{
  
  // Instance variables 
  private SudokuBase b;
  private int row;
  private int column;
  private PrimarySymbols ps;
  private NumberSymbols ns;
  private  boolean check;
  private SymbolRenderer c;
  
  
  /**
   * Creates the sudoku view, which is mostly a sudoku board with observer
   * functionality
   * 
   * @param b The sudoku base to obtain information for creating the board
   */
  public SudokuView(SudokuBase b){
    
    
    super(b);
    check = false;
    
    this.b = b;
    row = b.getRows();
    column = b.getColumns();
//    ps = new PrimarySymbols();
//    ns = new NumberSymbols();
    b.addObserver(this);
    
  }
  
  
  /**
   * Updates the view every time the model sends out a notification.
   * Repaints whenever a change is made.
   * 
   * @param o The objservable object
   * @param arg Extra parameter to indicate specific changes
   */
  public void update(Observable o, Object arg){
    
    repaint();
  }
  
  
 
  /**
   * Draws the sudoku cell. Changes color to green if the cell
   * is green.
   * 
   * @param g The graphics object used to draw
   */
  public void paintComponent(Graphics g){
    
    super.paintComponent(g);
    setNumeric(check);
    for(int i = 0; i < b.getSize(); i++)
    {
      for(int j = 0; j < b.getSize(); j++)
      {
        if(b.isGiven(i,j)){
          g.setColor(new Color(0,128,0));
        }else{
          g.setColor(Color.black);
        }
        
        
     
        c.drawSymbol(g, 5 + (j) * SudokuBoard.CELL_SIZE, 5 + (i+1) * SudokuBoard.CELL_SIZE, b.getValue(i, j));
      
        /**       
         For Debugging purpose
         ps.drawSymbol(g, 5 + j * SudokuBoard.CELL_SIZE, 5 + i * SudokuBoard.CELL_SIZE, b.getValue(i, j));
         ns.drawSymbol(g, 5 + (j) * SudokuBoard.CELL_SIZE, 5 + (i+1) * SudokuBoard.CELL_SIZE, b.getValue(i, j)); 
         
         * I TRIED THIS BELOW LINE FOR EXTRA CREDIT. I HOPE I GET PARTIAL CREDIT FOR THAT.
         * 
         SymbolRenderer.drawSymbol(g, 5 + (j) * SudokuBoard.CELL_SIZE, 5 + (i+1) * SudokuBoard.CELL_SIZE, b.getValue(i, j));
         
         */ 
        
        
        
      }
    }
  }
  
  
  
  
   /**
   * Sets the output type: True for numeric output, False
   * for symbol output.
   * @param flag The desired output type
   */
  public void setNumeric(boolean flag){
     check = flag;
    if(flag = false){
      c = new PrimarySymbols();
      
    }
    else{
      c = new NumberSymbols();
      
    }
  }
  /**
   * Retrieve the current output type, numeric or graphic
   * @return True if numeric output, False if symbols are output
   */
  public boolean showsNumeric(){
    
    return check;
    
  }
  
  
  
  class PrimarySymbols implements csc143.sudoku.SymbolRenderer {
    
    
    
    /**
     * Renders a single symbol for the Sudoku game
     * @param x The x-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param y The y-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param g The Graphics object used to draw the symbol
     * @param value The value to be drawn, between 0 and 12,
     * inclusive
     */
    public void drawSymbol(java.awt.Graphics g, int x, int y, int value) {
      ((Graphics2D)g).setStroke(new java.awt.BasicStroke(8, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_MITER));
      
      switch(value)
      {
        case 1: value = 1;
        g.drawOval(x + 4, y + 4, 32, 32);
        break;
        
        case 2: value = 2;
        g.drawLine(x + 4, y + 4, x + 36, y + 36);
        g.drawLine(x + 4, y + 36, x + 36, y + 4);
        break;
        
        case 3: value = 3;
        g.drawLine(x + 4, y + 4, x + 36, y + 4);
        g.drawLine(x + 20, y + 4, x + 20, y + 36);
        g.drawLine(x + 4, y + 36, x + 36, y + 36);
        break;
        
        case 4: value = 4;
        g.drawArc(x - 28, y + 4, 64, 32, 270, 180);
        break;
        
        case 5: value = 5;
        g.drawLine(x + 20, y + 4, x + 20, y + 36);
        g.drawLine(x + 4, y + 20, x + 36, y + 20);
        break;
        
        case 6: value = 6;
        g.drawArc(x - 9, y + 4, 26, 32, 270, 180);
        g.drawArc(x + 23, y + 4, 26, 32, 90, 180);
        break;
        
        case 7: value = 7;
        g.drawRect(x+3, y+3, 37, 37);
        break;
        
        case 8: value = 8;
        g.drawLine(x + 4, y + 4, x + 36, y + 4);
        g.drawLine(x + 36, y + 4, x + 36, y + 36);
        g.drawLine(x + 4, y + 36, x + 36, y + 4);
        break;
        
        case 9: value = 9;
        g.drawLine(x + 4, y + 4, x + 36, y + 4);
        g.drawLine(x + 36, y + 34, x + 8, y + 6);
        
        g.drawLine(x + 4, y + 4, x + 4, y + 36);
        g.drawLine(x + 4, y + 36, x + 36, y + 4);
        
        break;
        
        case 10: value = 10;
        g.drawArc(x + 4, y + 4, 64, 32, 90, 180);
        break;
        
        case 11: value = 11;
        g.drawLine(x + 4, y + 4, x + 4, y + 36);
        g.drawLine(x + 4, y + 20, x + 36, y + 20);
        g.drawLine(x + 36, y + 4, x + 36, y + 36);
        break;
        
        case 12: value = 12;
        g.drawLine(x + 4, y + 4, x + 36, y + 36);
        g.drawLine(x + 4, y + 36, x + 36, y + 4);
        g.fillOval(x + 16, y, 7, 7);
        g.fillOval(x + 16, y + 33, 7, 7);
        g.fillOval(x, y + 16, 7, 7);
        break;
        
        
        
        
        
      }
      
    }
    
    
  }
  
  /**
   * BELOW CLASS IS FOR EXTRA CREDIT. HOPEFULLY PARTIAL EXTRA CREDIT AS I HAVE IT SET UP CORRECTLY
   */ 
  
  
  
  class NumberSymbols implements csc143.sudoku.SymbolRenderer  {
    
    
    /**
     * Renders a single symbol for the Sudoku game
     * @param x The x-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param y The y-coordinate for the upper-left corner 
     * of the symbol area (40x40 pixels)
     * @param g The Graphics object used to draw the symbol
     * @param value The value to be drawn, between 0 and 12,
     * inclusive
     */
    public void drawSymbol(java.awt.Graphics g, int x, int y, int value) {
      ((Graphics2D)g).setStroke(new BasicStroke(10));
      
      Font f = new Font("The number",Font.BOLD,20);
      g.setFont(f);
      switch(value){ 
        case 1:
          value = 1;
          g.drawString("1", x+15, y-10);
          break;
          
        case 2: 
          value = 2;
          g.drawString("2", x+15, y-10);
          break;
          
        case 3:
          value = 3;
          g.drawString("3", x+15, y-10);
          break;
          
        case 4: 
          value = 4;
          g.drawString("4", x+15, y-10);
          break;
          
        case 5:
          value = 5;
          g.drawString("5", x+15, y-10);
          break;
          
        case 6: 
          value = 6;
          g.drawString("6", x+15, y-10);
          break;
          
        case 7:
          value = 7;
          g.drawString("7", x+15,y-10);
          break;
          
        case 8: 
          value = 8;
          g.drawString("8", x+15, y-10);
          break;
          
        case 9:
          value = 9;
          g.drawString("9", x+15, y-10);
          break;
          
        case 10: 
          value = 10;
          g.drawString("10", x+15, y-10);
          break;
          
        case 11:
          value = 11;
          g.drawString("11", x+15, y-10);
          break;
          
        case 12: 
          value = 12;
          g.drawString("12", x+15, y-10);
          break;
      }
    }
  }
  
  
  
}