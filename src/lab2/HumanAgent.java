package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HumanAgent extends Agent {

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() { // TODO
    	
    	Move move; // The move that will be returned
    	
    	List<Cell> pieces = board.getPossibleCells(); // All of the possible cells that can be moved this turn, dependent on whose turn it is
    	
    	List<String> coordinateStrings = new ArrayList<>();
    	
    	// For loop copies the string values of the possible cells into a string ArrayList
    	for (Cell cell : pieces) {
    		coordinateStrings.add(cell.getCoordinate().toString());
		}
    	
    	// Uses scanner to check the user's input
    	System.out.println("Choose a piece to move: " + coordinateStrings.toString());
    	Scanner whichPiece = new Scanner(System.in);
    	    	
    	String piece;
    	
    	// Checks if the input is in the format of "Letter A to E" and "Number 1 to 5"
    	while (!whichPiece.hasNext("[ABCDEabcde]" + "[12345]")) {
			System.out.println("Invalid option. Select a coordinate: ");
	    	piece = whichPiece.nextLine().toUpperCase();
	    	
		}
    	
    	piece = whichPiece.nextLine().toUpperCase();
    	
    	
    	// Checks to see if the user's input is contained within the possible cells ArrayList
    	while (!coordinateStrings.contains(piece)) {
			System.out.println("Invalid option. Select a coordinate: ");
	    	piece = whichPiece.nextLine().toUpperCase();
		}
    	

    	// Breaks the user input into two separate pieces, the letter and the number
    	String columnRow = piece.substring(0, 1);
    	String columnChar = piece.substring(1);
    	int column = Integer.parseInt(columnChar);
    	
    	// Gets the possible destinations from the cell that corresponds to the user's input
    	List<Cell> toCells = board.getPossibleDestinations(new Cell(new Coordinate(column - 1, convertString(columnRow))));
    	List<String> cellStrings = new ArrayList<>();
    	// Converts the possible destinations to strings
    	for (Cell possibleMoves : toCells) {
    		cellStrings.add(possibleMoves.getCoordinate().toString());
		}
    	System.out.println("Here are your possible moves, enter a coordinate: " + cellStrings.toString());
    	
    	String moveTo;
    	
    	// Checks if the destination input is in the correct format
    	while (!whichPiece.hasNext("[ABCDEabcde]" + "[12345]")) {
			System.out.println("Invalid option. Select a coordinate: ");
	    	moveTo = whichPiece.nextLine().toUpperCase();
	    	
		}
    	
    	moveTo = whichPiece.nextLine().toUpperCase();
    	
    	// Checks if the input is within the destination ArrayList
    	while (!cellStrings.contains(moveTo)) {
			System.out.println("Invalid option. Select a coordinate: ");
	    	moveTo = whichPiece.nextLine().toUpperCase();
		}
    	
    	// Makes a substring of the coordinate letter and number
       	String coordRow = moveTo.substring(0, 1);
    	String coordCol = moveTo.substring(1);
    	int coordColumn = Integer.parseInt(coordCol);
    	
    	// Makes a fromCell and toCell that creates the move we end up returning
    	Cell fromCell = new Cell(new Coordinate(column - 1, convertString(columnRow)));
    	fromCell.setPiece(board.getCell(fromCell.getCoordinate()).getPiece());
    	Cell toCell = new Cell(new Coordinate(coordColumn - 1, convertString(coordRow)));	
    	toCell.setPiece(board.getCell(toCell.getCoordinate()).getPiece());

    	move = new Move(fromCell, toCell);
    	
    	return move;
    }
    
    /**
     * Gets the column number that corresponds to the letter on the board
     * @param letter The letter coordinate that the user inputs
     * @return A number that corresponds the the coordinate letter that the user input
     */
    
    private int convertString(String letter) {
		
    	int number;
    	
    	switch (letter) {
		case "A":
			number = 0;
			break;
		case "B":
			number = 1;
			break;
		case "C":
			number = 2;
			break;
		case "D":
			number = 3;
			break;
		case "E":
			number = 4;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + letter);
		}
    	
    	return number;
	}
}
