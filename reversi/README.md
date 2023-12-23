# Reversi

## Changes for Part IV
- Created various adapters to implement our providers' strategy and view.
- Updated the PlayerFactory to create our providers' players if desired.
- Updated the ViewFactory to create our providers' views if desired.

### New Classes for Part IV

- **cs3500.reversi.adapter.ModelAdapter** - Our adapter to the provider's model.
- **cs3500.reversi.adapter.ViewAdapter** - Our adapter to the provider's view.
- **cs3500.reversi.adapter.HexCellAdapter** - Our adapter to the provider's cell.
- **cs3500.reversi.adapter.HumanPlayerAdapter** - Our adapter to the provider's human player.
- **cs3500.reversi.adapter.AIPlayerAdapter** - Our adapter to the provider's AI player.
- And obviously, all of our provider's classes and interfaces.

### Using Our Providers' View & Strategy
Reference our PROVIDERVIEW.md. In our main file, we have created an example game with two
players: a human player and the AI player. The human player is using our view. The AI player is
using our providers' view and our providers' strategy.

## Changes for Part III
- Moved the previously separate class for the key component (i.e. handling key inputs) to the view.
- Created a mock player, mock controllers, and an additional mock model for testing purposes.
- Created a view factory and a player factory to create views and AI players, respectively.
- Added features interfaces for both the model and the view so the controller can listen to events
coming from both the view and the model (i.e. turn changes, moves, etc.).
- Updated our main file to handling command line arguments, which are outlined below.
- Refactored resizablility of the view for our board.

### New Classes for Part III
- **cs3500.reversi.controller.ReversiController** - This interface is used to represent a controller.
- **cs3500.reversi.player.Player** - Represents a generic player.
- **cs3500.reversi.player.AIPlayer** - Represents an AI player.
- **cs3500.reversi.player.HumanPlayer** - Represents a human player.
- **cs3500.reversi.factory.PlayerFactory** - Used to create players.
- **cs3500.reversi.factory.ViewFactory** - Used to create views.
- **cs3500.reversi.view.ViewFeatures** - Represents all the features that the view has and listens to.
- All of our mock classes.

### Command Line Arguments

Here is how you can create custom games with the command line:
- **Usage: java -jar Reversi.jar <number-of-rows> <player1> <player2>**
- **number-of-rows** - The number of rows in the game. Must be an odd number greater than or equal to 5.
- **player1** - The type of player for player one. Must be one of: "human", "easyai", "mediumai", "hardai".
- **player2** - The type of player for player two. Must be one of: "human", "easyai", "mediumai", "hardai".

## Changes for Part II
- Our playMove() and pass() methods now take in a Player, and inside the methods check whether or
not the pieceColor is attempting to move or pass on a turn that is not theirs. If they are, then an
IllegalStateException is thrown.
- Added a method getStateAt(int x, int y) to the model, which returns the state of the cell at the
given x and y coordinates. This is used by the view to determine if a move is valid or not.
- getScore() now takes in a pieceColor. This is used by the view to determine the score of each pieceColor.
- Added a method getTurn() to the model, which returns the current turn of the game.
- Instead of storing a list of states, each cell is now mapped to its state using the 
ReversiCellPair class.

### New Classes for Part II
- **cs3500.reversi.model.ReversiCellPair** - This class is used to map each cell to its state.
- **cs3500.reversi.model.ReversiPlayer** - This class is used to represent each pieceColor.
- **cs3500.reversi.model.ReversiStrategy** - This interface is used to represent a strategy for
playing the game. It has one method, which is used to determine the next move to make.
- **cs3500.reversi.model.AvoidCellsNextToCorners** - This strategy is used to avoid playing cells
next to corners, as they are easy to capture.
- **cs3500.reversi.model.ChooseCornerCell** - This strategy is used to choose a corner cell to play
on, as they are hard to capture and cannot be flipped.
- **cs3500.reversi.model.FlipMostCells** - This strategy is used to flip the most cells possible.
- **cs3500.reversi.model.StrategyWithFallbacks** - This strategy is used to combine multiple
strategies together, and to use the first strategy that returns a valid move.
- **cs3500.reversi.view.components.Hexagon** - This class is used to represent each hexagon on the board.
- **cs3500.reversi.view.components.KeyComponent** - This class is used to handle which keys perform
which actions inside the view.
- **cs3500.reversi.view.components.ReversiPanel** - This class is used to represent the panel that
the board is drawn on, handles clicking, etc.
- **cs3500.reversi.view.GraphicsView** - This interface holds all the public methods in the view.
- **cs3500.reversi.view.ViewFeatures** - Represents all the features that the view has.
- **cs3500.reversi.view.ReversiGraphicsView** - Represents the frame the view is drawn on.

We have also created a variety of mock models to test our strategies, making sure they check all
the moves they are supposed to.

## Overview
Reversi is a two-pieceColor game where the objective is to have the most tiles at the end. Each pieceColor
starts out having 3 colors in the center, alternating to make a ring of six colors around the 
center. The game then begins with the first pieceColor placing a color on the board. The color must be
placed in a way that it surrounds at least one of the other pieceColor's colors. If the color is placed
in a way that it surrounds one of the other pieceColor's colors, then all of the colors that were
surrounded by the color that was just placed, including neighboring colors, will be flipped to the 
color of the pieceColor who just placed the color. The game ends when there are no more possible moves. 
The pieceColor with the most tiles wins.

The coordinate system used is cubic internally, keeping track of each hexagonal cells' q, r, and s
state, however the perceived coordinate system that the players interact with uses a row and column 
system, with an 'x' and 'y' value. We chose to do this for the pieceColor as we feel it is easier for 
the pieceColor to understand the game in this way, rather than having to work with a three-dimensional
coordinate system, having to calculate q, r, and s, relative to the center of the board.

## Quick Start
For starting a game with five rows, this is how you would do it:
```java
ReversiModel model = new HexagonalReversi(5);
ReversiTextualView view = new TextualReversi(model);
// For pieceColor one (O) to play the move at (1, 0), capturing the other pieceColor's 'X' at (1, 1):
model.playMove(1, 0);
view.render();
// For pieceColor two (X) to to play the move at (0, 1), capturing the other pieceColor's 'O' at (1, 2):
model.playMove(0, 1);
view.render();

// You can also pass your turn:
model.pass();

// You can also create a new model without specifying the number of rows, and it will default to 5:
ReversiModel model2 = new HexagonalReversi();
```
You can also run the main file, as it has this example internally. 

## Keyboard Components
- **enter/return** - To make a move when a cell is selected.
- **p** - Pass your turn.
- **q** - Quit the game.

You can select a cell by simply clicking on it.

## Key Components
### Model
- The model handles all of the game logic, such as playing moves, flipping pieces, and determining
if the game is over or not. It also ties every cell with its cell state (empty, white, or black)
using a mapping of every cell to such. The model uses a cubic coordinate system internally, keeping
track of each cell's q, r, and s values, however it allows players to interact using a row and 
column system, with an 'x' and 'y' values instead for ease-of-use. 
### View 
- The view is used to render the current state of the board for each pieceColor. It does this by 
iterating through every cell on the board, and checking if it is empty, or if it has a piece on it. 
If it is empty, it will print a dash. If it has a piece on it, it will print the piece. 
It will also print the current score of the game, and whose turn it is.
### Cells
- The cells are used to represent each "hexagon" on the board. They keep track of their positional
coordinate, so that the model can determine its neighboring cells, which is used for flipping 
adjacent cells' colors.

## Subcomponents
- The model keeps track of various things, such as the current turn of the game, which is directly
tied to whichever pieceColor's turn it is, as every odd-turn is white, and every even-turn is black. 
It also keeps track of the number of rows, which is used to determine the size of the internal 
arrays that store the cells. It also must keep track of if players pass, as if we encounter a 
situation where both players "pass" in a row, the game ends. 
- In order to keep track of the state of each cell, the model maps each cell to an Enum that 
corresponds to either empty, black, or white. This is used by the view as well to render the board
correctly with each pieceColor's pieces, and to determine if a requested move is valid or not.

## Other Subcomponents
- **ReversiCell** - This is the cell that is used to represent each cell on the board. It keeps
track of its state, and its q, r, and s values. 
- **ReversiCellState** - This enum is used to represent the state of each cell. It can be either
empty, white, or black. The model ties the state of the cells with the cells themselves via a HashMap. 

## Source Organization
- **cs3500.reversi.model.HexagonalReversi** - This is the model; the heart of the program, and what 
handles all the logic for making moves. 
- **cs3500.reversi.model.types.HexCell** - This is the class that represents each cell on the board.
It keeps track of its q, r, and s values, which is used by the model for neighbor checking.
- **cs3500.reversi.model.types.HexCellState** - This is the enum that represents the state of each
cell. It can be either empty, white, or black.
- **cs3500.reversi.model.types.Direction** - This is an enum that makes direction-checking easier by 
representing each direction as its cardinal direction, such as "East", "South", rather than an int.
- **cs3500.reversi.view.TextualReversi** - This is the view that renders the board for the players.
It allows each pieceColor to know whose turn it is, and what the current score is, so that the players
can act accordingly. 
- **cs3500.reversi.Main** - This is the main class that is used to run the program. It is used to
start the game, and to output and handle any exceptions that may occur.

## Invariants
- The number of cells with pieces placed on them is equal to the score of white plus the score of black.
- The turn number is always greater than or equal to 1.
- The number of rows is always greater than or equal to 5, and is always odd. 
- The number of empty cells is equal to the number of total cells minus the number of black and white cells.
- White always plays on on odd turns, and black always plays on even turns.
- When the number of turns passed is equal to 2, the game is immediately over.