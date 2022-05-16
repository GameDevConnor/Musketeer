package lab2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import lab2.Piece.Type;

public class Board {
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;
    
    
    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { // TODO
        return board[coordinate.row][coordinate.col];
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }
    
    
    /**
     * Checks whether or not the input value is contained within the board
     * @param i integer that we are checking
     * @return whether or not the integer is between 0 and 4
     */
    private boolean withinBoundary(int i) {
    	if ((0 <= i && i < size)) {
			return true;
		}
    	else {
			return false;
		}
    }


    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() { // TODO
        
    	List<Cell> musketeerCells = new ArrayList<Cell>();
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) { // Iterates through the entire 2D array
    			
    			if (!(board[i][j].getPiece() == null)) { // Makes sure no null pieces are selected, because if they are, trying to get the type will result in an error
    				
        			if (board[i][j].getPiece().getType() == Type.MUSKETEER) {
        				musketeerCells.add(board[i][j]); // If the piece is a musketeer, add it to the ArrayList
        			}
    				
    			}

    		}
    	}
    	return musketeerCells;
    }

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { // TODO
    	List<Cell> guardCells = new ArrayList<Cell>();
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) { // Iterates through the entire array
    			if (!(board[i][j].getPiece() == null)) { // Makes sure no null pieces are selected, because if they are, trying to get the type will result in an error
					
    				if (board[i][j].getPiece().getType() == Type.GUARD) {
    					guardCells.add(board[i][j]); // If the piece is a guard, add it to the ArrayList
    				}
    			}
    		}
    	}
    	return guardCells;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) { // TODO
    	    	
    	if (this.turn == Type.GUARD) {
    		move.toCell.setPiece(new Guard()); // If you are moving on the guard's turn, set the space you're going to as a guard
    	}
    	else {
    		move.toCell.setPiece(new Musketeer()); // If you are moving on the musketeer's turn, set the space you're going to as a musketeer
    	}
    	
    	move.fromCell.removePiece(); // Set the original space as empty
    	
    	
    	// Makes a change to the board itself
    	this.board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].setPiece(move.fromCell.getPiece());
    	this.board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(move.toCell.getPiece());

    	// Changes turns
    	if (this.turn == Type.GUARD) {
    		this.turn = Type.MUSKETEER;
    	}
    	else {
			this.turn = Type.GUARD;
		}

    }

    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { // TODO
    	// If it's the musketeer's turn, undo the guard's last turn turn by setting original space back to a guard and the original destination back to being empty
    	if (this.turn == Type.MUSKETEER) {
    		move.fromCell.setPiece(new Guard());
    		move.toCell.setPiece(null);
    		this.turn = Type.GUARD;
    	}
    	// If it's the guard's turn, undo the musketeer's last turn turn by setting original space back to a musketeer and the original destination back to a guard
    	else {
    		move.fromCell.setPiece(new Musketeer());
    		move.toCell.setPiece(new Guard());
			this.turn = Type.MUSKETEER;
		}
    	// Makes a change to the board itself
    	this.board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].setPiece(move.fromCell.getPiece());
    	this.board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col].setPiece(move.toCell.getPiece());
    }

    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) { // TODO
        
    	// Integers that represent one space away from the fromCell is all 4 orthogonal directions
    	
    	int fromCoordinateRow = move.fromCell.getCoordinate().row;
    	int fromCoordinateCol = move.fromCell.getCoordinate().col;

    	int toCoordinateRow = move.toCell.getCoordinate().row;
    	int toCoordinateCol = move.toCell.getCoordinate().col;
    	
    	// Checks if the toCoordinate cell is orthogonal to the fromCoordinate cell, and is within the board
    	boolean horizontal = ((toCoordinateRow == fromCoordinateRow + 1 && withinBoundary(toCoordinateRow)) || (toCoordinateRow == fromCoordinateRow - 1 && withinBoundary(toCoordinateRow))) && toCoordinateCol == fromCoordinateCol;
    	boolean vertical = ((toCoordinateCol == fromCoordinateCol + 1 && withinBoundary(toCoordinateCol)) || (toCoordinateCol == fromCoordinateCol - 1 && withinBoundary(toCoordinateCol))) && toCoordinateRow == fromCoordinateRow;
    	
    	// If the space is within boundaries and is null on the guard's turn, it is a valid move. If not, it isn't valid
    	if (this.turn == Type.GUARD) {
    		if ((vertical || horizontal) && move.toCell.getPiece() == null) {
				return true;
			}
    		else {
				return false;
			}
    	}
    	// If the space is within boundaries and is a guard on the musketeer's turn, it is a valid move. If not, it isn't valid
    	else {
    		if (move.toCell.getPiece() != null && (vertical || horizontal) && move.toCell.getPiece().getType() == Type.GUARD) {
				return true;
			}
    		else {
				return false;
			}
		}

    	
    	
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return      Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() { // TODO
    	
    	List<Cell> cells = new ArrayList<Cell>();
    	
    	
    	for (int i = 0; i < size; i++) {
    		for (int j = 0; j < size; j++) { // Iterate through the whole 2D array
    	    	if (this.turn == Type.GUARD) {

    	    		if (!(board[i][j].getPiece() == null)) { // Make sure we don't try to get the piece type of a null space, which would cause an error
    	    		
    	    			Cell horizontalPos;
    	    	    	Cell horizontalNeg;
    	    	    	Cell verticalPos;
    	    	    	Cell verticalNeg;
	    			
    	    	    	// Making sure that if a cell is on an edge that the program does not try to access an index outside of the board
    	    			if (i == 0) {
    	    				horizontalPos = board[i + 1][j];
        	    	    	horizontalNeg = board[i][j];
						}
    	    			else if (i == 4) {
    	    				horizontalPos = board[i][j];
        	    	    	horizontalNeg = board[i - 1][j];
						}
    	    			else {
    	    				horizontalPos = board[i + 1][j];
        	    	    	horizontalNeg = board[i - 1][j];
						}
    	    			
    	    			if (j == 0) {
    	    				verticalPos = board[i][j + 1];
        	    	    	verticalNeg = board[i][j];
						}
    	    			else if (j == 4) {
    	    				verticalPos = board[i][j];
        	    	    	verticalNeg = board[i][j - 1];
						}
    	    			else {
    	    				verticalPos = board[i][j + 1];
        	    	    	verticalNeg = board[i][j - 1];
						}
    	    	    	
    	    			// If any of the spaces surrounding the cell are empty and within the boundaries of the array, then the guard can move, and it returns true
    	    	    	if ((horizontalNeg.getPiece() == null && withinBoundary(horizontalNeg.getCoordinate().col) || horizontalPos.getPiece() == null && withinBoundary(horizontalPos.getCoordinate().col) || verticalPos.getPiece() == null && withinBoundary(verticalPos.getCoordinate().row) || verticalNeg.getPiece() == null) && withinBoundary(verticalNeg.getCoordinate().col)) {
    	    	    		if (board[i][j].getPiece().getType() == Type.GUARD) {
    	    	    			cells.add(board[i][j]);
    	    	    		}
    	    	    	}
					}
    	    	}
    	    	else {
    	    		
    	    		if (!(board[i][j].getPiece() == null)) {
    	    	    	
    	    			
    	    			Cell horizontalPos;
    	    	    	Cell horizontalNeg;
    	    	    	Cell verticalPos;
    	    	    	Cell verticalNeg;
    	    			
    	    			
    	    			if (i == 0) {
    	    				horizontalPos = board[i + 1][j];
        	    	    	horizontalNeg = board[i][j];
						}
    	    			else if (i == 4) {
    	    				horizontalPos = board[i][j];
        	    	    	horizontalNeg = board[i - 1][j];
						}
    	    			else {
    	    				horizontalPos = board[i + 1][j];
        	    	    	horizontalNeg = board[i - 1][j];
						}
    	    			
    	    			if (j == 0) {
    	    				verticalPos = board[i][j + 1];
        	    	    	verticalNeg = board[i][j];
						}
    	    			else if (j == 4) {
    	    				verticalPos = board[i][j];
        	    	    	verticalNeg = board[i][j - 1];
						}
    	    			else {
    	    				verticalPos = board[i][j + 1];
        	    	    	verticalNeg = board[i][j - 1];
						}
    	    						
    	    			// If any of the spaces surrounding the cell are guards and within the boundaries of the array, then the musketeer can move, and it returns true
        	    	    	if ((!(horizontalNeg.getPiece() == null) && horizontalNeg.getPiece().getType() == Type.GUARD) || !(horizontalPos.getPiece() == null) && horizontalPos.getPiece().getType() == Type.GUARD || !(verticalPos.getPiece() == null) && verticalPos.getPiece().getType() == Type.GUARD || !(verticalNeg.getPiece() == null) && verticalNeg.getPiece().getType() == Type.GUARD) {

    	    	    			if (board[i][j].getPiece().getType() == Type.MUSKETEER) {
    	    	    				cells.add(board[i][j]);
    	    	    			}
    	    	    		}

					}
    			}
    		}
    	} 
    	
    	return cells;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { // TODO
        
    	List<Cell> orthogonal = new ArrayList<Cell>();
    	List<Cell> destinations = new ArrayList<Cell>();
        
    	// Making sure that if a cell is on an edge that the program does not try to access an index outside of the board
    	// Add all possible options for orthogonal cells into an ArrayList
    	if (fromCell.getCoordinate().row == 4) {
        	orthogonal.add(board[fromCell.getCoordinate().row - 1][fromCell.getCoordinate().col]);
		}
    	else if (fromCell.getCoordinate().row == 0) {
        	orthogonal.add(board[fromCell.getCoordinate().row + 1][fromCell.getCoordinate().col]);
		}
    	else {
        	orthogonal.add(board[fromCell.getCoordinate().row + 1][fromCell.getCoordinate().col]);
        	orthogonal.add(board[fromCell.getCoordinate().row - 1][fromCell.getCoordinate().col]);

		}
    	
    	if (fromCell.getCoordinate().col == 4) {
        	orthogonal.add(board[fromCell.getCoordinate().row][fromCell.getCoordinate().col - 1]);
		}
    	else if (fromCell.getCoordinate().col == 0) {
        	orthogonal.add(board[fromCell.getCoordinate().row][fromCell.getCoordinate().col + 1]);
		}
    	else {
        	orthogonal.add(board[fromCell.getCoordinate().row][fromCell.getCoordinate().col + 1]);
        	orthogonal.add(board[fromCell.getCoordinate().row][fromCell.getCoordinate().col - 1]);
		}
    	
    	// If it is a valid move to go from the fromCell to the orthogonal cell for each cell in the orthogonal ArrayList, then add it to the destinations ArrayList
    	for (Cell cell : orthogonal) {
    		if (this.turn == Type.GUARD) {
				if (isValidMove(new Move(fromCell, cell))) {
					destinations.add(cell);
				}
			}
    		else {
    			if (isValidMove(new Move(fromCell, cell))) {
    				destinations.add(cell);
    			}
    		}
    	}
        
        return destinations;
    }

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() { // TODO
        
    	List<Move> moves = new ArrayList<Move>();
    	
    	for (Cell fromCell : this.getPossibleCells()) {
			// For each possible cell, make a move object from the cell to each of its possible destinations
    		for (Cell destCell : this.getPossibleDestinations(fromCell)) {
				Move move = new Move(fromCell, destCell);
				moves.add(move);
			}
			
		}
    	
    	return moves;
    }

    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { // TODO
            	
    	List<Cell> musketeerCells = getMusketeerCells();
    	
    	// Boolean variables that check whether the musketeers are in the same row or column
    	boolean equalRow = musketeerCells.get(0).getCoordinate().row == musketeerCells.get(1).getCoordinate().row && musketeerCells.get(1).getCoordinate().row == musketeerCells.get(2).getCoordinate().row;
    	boolean equalCol = musketeerCells.get(0).getCoordinate().col == musketeerCells.get(1).getCoordinate().col && musketeerCells.get(1).getCoordinate().col == musketeerCells.get(2).getCoordinate().col;
    	    
    	// If musketeers are in the same row or column, then the guards win
    	if ((equalCol || equalRow)) {
    		this.winner = Type.GUARD;
    		return true;
    	}
    	
    	// If there are no possible cells that can move when it is the musketeers' turn, the musketeers win
    	if (getPossibleCells().isEmpty() && this.turn == Type.MUSKETEER) {
			this.winner = Type.MUSKETEER;
			return true;
		}
    	
    	return false;
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
}
