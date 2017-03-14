# Sudoku Game

#### Disclaimer: 
##### Below are sets of ideas / instructions I followed to make this game. 
##### If you want to run, please let me know, I would be more than happy to provide you test cases.

Below are some screen shots of what game looks like.
![screen shot 2017-02-20 at 8 31 58 pm](https://cloud.githubusercontent.com/assets/11382116/23183420/cc188570-f830-11e6-9b89-d476666de557.png)

![screen shot 2017-02-20 at 8 34 05 pm](https://cloud.githubusercontent.com/assets/11382116/23183421/cc2c7166-f830-11e6-96f2-8f998e482542.png)


------------------------- IDEAS BELOW -----------------
## Objectives 

## Minimal option
The core of the Minimal option in "Normal Play". The model shall be initialized with a set game, for
example the one that we have been using. The graphic buttons and selected cell will support the user
updating the non given cells. When the user tries to update a given cell, the system beep will sound.

#### Normal Play mode
  The core of the controller is a number of graphic "buttons" for giving a value to the selected cell. These
  graphic "buttons" can (should) "leverage" the drawing code that you created to draw the symbols within the
  view. That is, use an instance of the class that implements SymbolRenderer to draw the "buttons".
  To be able to have Normal Play be functional, the application must begin with a game. If you implement the
  Standard version, the user will have the option to setup
  their own game. However, in the mean time, you can
  use the following code to create a startingpoint
  game for the application.
  
#### Starting-Point Game
  You can use the following as a startingpoint
  game, a 2x3 board.
  
  ![screen shot 2017-02-21 at 12 13 40 pm](https://cloud.githubusercontent.com/assets/11382116/23183136/e0ec7fd4-f82f-11e6-9378-7392271eb2c8.png)

  
  The following method calls the model to create this board.
  
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
    
#### Controller Buttons
One of the easiest way to collect input from the user is to use "buttons". (There is an challenge option to
support keyboard input as well.) The "buttons" needs to include a "blank" button to insert a zero. Also, the
display shall be limited to the symbols that "belong" with the current SudokuBase. For example, if getSize
returns 4, there shall be 5 graphic "buttons" available. So, the number of graphic "buttons" will be limited to
the "size" of the board + 1 (for zero / blank).
The word "button" has been in quotes in this discussion because the controller object does not have to extend
java.awt.Button or javax.swing.JButton. In fact, it will be easier if the controller object (graphic "button") is
like SudokuView, that is, a JComponent of some form, but not necessarily a JButton. For the rest of this
discussion, the quotation marks around the word button shall be dropped, but this change does not imply that
the controller object now IsA
JButton.

The graphic button shall respond to mouse over and mouse out. (Not the MouseListener names, obviously.)
With mouse over (when the mouse is "hovering" above the graphic button), the background color of the
graphic button shall change to the highlight color. With mouse out (when the mouse is no longer "hovering"
above the graphic button), the background color of the graphic button shall revert to the default color. When
the mouse button is depressed within a graphic button, the background color of that graphic button shall
change to the active color. This will remain as long as the mouse button is depressed and the mouse pointer is
within the same graphic button as when the mouse button was pressed. If the mouse pointer leaves the
graphic button while the mouse button was depressed, the background color shall revert to the default
background color. (Note: This is a little tricky.) If the mouse button is released within the same graphic
button as where the mouse button was depressed, that is, if the mouse button is clicked, the value of the
selected cell in the view shall be set to the numerical value associated symbol on the graphic button; also the
background color of the graphic button shall revert to the highlight color. If the cell selected in the view is
given, the system beep shall be played, rather than attempting to update the value of the selected cell. Your
code shall check isGiven for the selected cell to avoid the IllegalStateException that SudokuCore throws,
rather than handling it.

## Integration
The other coding part of this assignment is integrating all of the pieces created by the other parts of this
project into a single application. A good deal of the integration work was started when you incorporated the
view and the controller into subsystems that support normal play.
### State information
Currently, there is no way to display state information, the work from SudokuBoard. In the real game, it
would probably make the most sense to include this as part of the view. In the interests of simplicity, we can
implement this using three separate small graphic displays to show the state of the rows, columns, and
regions. These can be simple graphics using red, yellow, green for State.ERROR, State.INCOMPLETE, and
State.COMPLETE, respectively. Then the rows, columns, and regions can be shown by coloring in the cells
corresponding to that area with the corresponding color indicating the state. This will need three different
displays, then, one for rows, one for columns, and one for regions.

Here is an example of how this simple display would appear. For demonstration purposes only, the are two of
each state in these sample displays. In this example, each cell is 5x5 pixels.

![state5x5](https://cloud.githubusercontent.com/assets/11382116/23183558/57784cea-f831-11e6-9ab1-2c7d0360d214.png)

Here is another example. This time the cell indicators are 10x10 pixels.

![state10x10](https://cloud.githubusercontent.com/assets/11382116/23183561/5796fe6a-f831-11e6-8caf-237f2639e1a5.png)

## Standard option
The Standard option adds support for SetUp
mode. Now, this completes the basic functionality for creating
and playing Sudoku games.
SetUp mode should leverage the components used in Normal Play mode. This takes advantage of the
component nature of the view and controller.
### Set-Up Mode
The user will request "SetUp"
mode by selecting from New Game command. This can be a button, or a
menu item, for example in the File menu.
There are two steps to setting up a new game:
1. Creating a new board.
2. Setting the givens within the board.


## Challenge Part
The challenge option includes two further enhancements:
1. keyboard support user input
2. SetUp mode using a dialog box
#### Keyboard Input
As you know, each of the symbols is associated with a number. This challenge feature adds a keyboard
controller. If the selected cell is not a given, pressing the keyboard equivalent will update the value of the
selected cell with the corresponding symbol. This should flash the active color briefly (~200 ms) on the
corresponding graphic button. If the selected cell is a given, the system beep shall sound instead. The alphanumeric
equivalent shall appear "next to" (or in the corner or …) the corresponding graphic button to
indicate which key corresponds to that value.
For the numerical values between 1 and 9, inclusive, the keyboard equivalent should be obvious. It would
make sense to accept either the QWERTY key or the numeric keypad key. For the values 10, 11, and 12, use
A, B, and C, respectively. (The capitalization shall not matter. In fact, both the upperand
lowercase
letters
use the same keyboard key.) For blanking out the selected cell, you could use either zero (0) or backspace, or
both. If an unacceptable key is pressed, the system beep shall sound; this would include numerical values
which are out of range for the current model.

#### Dialog Box for Set-Up Mode
When Create Board is selected, a dialog box appears to get the desired size for the new game. and the input
passes validation, the dialog box is dismissed and a second modal dialog appears with the view, graphic
buttons, and two "regular" buttons: Set Givens and Cancel. When Set Givens is clicked, the model is
updated, the dialog box is dismissed, and the model in the main window is updated to the new game. The
view and graphic buttons in the main window may need to be adjusted if the size is different. If the Cancel
button is clicked in either dialog, the dialog is dismissed; no change is made in the main window. There's
nothing to restore, since the setup
view didn't replace the view for the "old" model in the main window.
