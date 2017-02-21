 package csc143.sudoku;

/**
 * This class extends java.util.Observable
 * @author Aayush Shah
 * @version Minimal
 */ 
public abstract class SudokuBase extends java.util.Observable {
  
  // Declaring final ints
  private final int rows;
  private final int columns;
  private final int size;
  
  public enum State {COMPLETE, INCOMPLETE, ERROR};
  
  /**
   * Constructor for this class
   * @param layoutRows the rows for the board
   * @parma layoutColumns the cols for the board
   */ 
  public SudokuBase(int layoutRows, int layoutColumns) {
    
    // Initializing global variables
    rows = layoutRows;
    columns = layoutColumns;
    size = columns * rows;
  }
  
  /**
   * Getter for the rows
   * @return rows -- number of rows
   */ 
  public int getRows() {
    return rows;
  }
  
  /**
   * Getter for the cols
   * @return columns -- number of columns
   */ 
  public int getColumns() {
    return columns;
  }
  
  /**
   * Getter for the size which is row times col
   * @return size
   */ 
  public int getSize() {
    return size;
  }
  
  /**
   * values of given row/column
   * @param row The row of the value
   * @param col The column of the value
   * @return The value at the row/column
   */
  public abstract int getValue(int row, int col);
  
  
  /**
   * Seting the value of row and col
   * @param row The row
   * @param col The column
   * @param value The value to set
   */
  public abstract void setValue(int row, int col, int value);
  
  /**
   * checks the value of row and col is given or not (hopefully)
   * @param row The row
   * @param col The column
   * @return True if yes
   */
  public abstract boolean isGiven(int row, int col);
  
  /**
   * Fix the cells
   */ 
  public abstract void fixGivens();
  
  public abstract State getRowState(int n);
  
  public abstract State getColumnState(int n);
  
  public abstract State getRegionState(int n);
  
}
