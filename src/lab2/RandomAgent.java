package lab2;

import java.util.List;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() { // TODO
        Move move;
        
        List<Cell> pieces = board.getPossibleCells();
        
        // Choose a random index from the ArrayList of possible cells
        Cell randomCell = pieces.get((int) (Math.random() * pieces.size()));
        
        // Get all the destinations from the random element in the pieces ArrayList
    	List<Cell> toCells = board.getPossibleDestinations(randomCell);
    	
    	// Select a random destination from the random cell
    	Cell randomDestination = toCells.get((int) (Math.random() * toCells.size()));
        
    	// Make a move from the random fromCell and random toCell
    	move = new Move(randomCell, randomDestination);
        
    	// Show the player what move was made
    	System.out.println(move.toString());
    	
        return move;
    }
}
