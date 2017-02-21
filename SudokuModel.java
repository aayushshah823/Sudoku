package csc143.sudoku;

/**
 * The main functionality for the sudoku game is performed in this class.
 * @author Aayush
 * @version Standard
 */ 
public class SudokuModel extends SudokuCore{
  
  /**
   * Creates a sudolu model
   * @param r rows of regions
   * @param c column of regions
   */
  public SudokuModel(int r, int c) {
    super(r, c);
  }
  
  /**
   * Determines the row and check whether it is complete, incomplete, error
   * @param row row index
   * @return state of row
   */ 
  public State getRowState(int row) {
    
    
    for(int value = 1; value <= getSize(); value++){
      
      // reseting counter everytime
      int count = 0;
      // checking if value repeats more than one times
      for(int col = 0; col < getSize(); col++){
        if(getValue(row,col) == value){
          count++;
        }
        
      }
      
      // Checking for error
      if(count>1){
        // returning state
        return State.ERROR;
      } 
      
    }
    
    // CHECKING incomplete
    for(int col = 0; col < getSize(); col++){
      if(getValue(row,col) == 0){
        // returning state
        return State.INCOMPLETE;
        
      }
      
    }
    // returning state
    return State.COMPLETE;
  }
  
  
  /**
   * Determines the columns and check whether it is complete, incomplete, error
   * @param n column index
   * @return state of row
   */ 
  public State getColumnState(int n) {
    
    
    for(int value = 1; value <= getSize(); value++){
      
      // reseting counter everytime
      int count = 0;
      // checking if value repeats more than one times
      for(int col = 0; col < getSize(); col++){
        if(getValue(col,n) == value){
          count++;
        }
        
      }
      
      // CHECKING error
      if(count>1){
        // returning state
        return State.ERROR;
      } 
      
    }
    
    // CHECKING incomplete
    for(int col = 0; col < getSize(); col++){
      if(getValue(n,col) == 0){
        // returning state
        return State.INCOMPLETE;
        
      }
      
    }
    
    // returning state
    return State.COMPLETE;
  }
  
  /**
   * Determines if a given region is complete, incomplete, or in an error state.
   * 
   * @param n The region index
   * @return The state of the region
   */
  public State getRegionState(int n){
    
    // Setting variables for keeping track of starting row, col in specific region
    int startCol = (n % getRows()) * getColumns();
    int startRow = (n / getRows()) * getRows();
    
    // Using nested fo loops because it I need to check every row and columns of specific region
    for(int value = 1; value <= getSize(); value++){
      int count = 0;
      for(int row = 0; row < getRows(); row++){
        
        for(int col = 0; col< getColumns(); col++){
          if(getValue(startRow + row,startCol + col) == value){
            count++;
          }
          
          // Checking for error
          if(count>1){
            
            // returning state
            return State.ERROR;
          } 
          
          
        }
      }
    }
    
    // Cjhecking for in complete.
    for(int row = 0; row < getRows(); row++){
      
      for(int col = 0; col< getColumns(); col++){
        if(getValue(startRow + row,startCol+col) == 0){
          // returning state
          return State.INCOMPLETE;
          
        }
      }
    }
    
    // returning state
    return State.COMPLETE;
  }
}