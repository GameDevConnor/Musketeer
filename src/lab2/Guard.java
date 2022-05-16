package lab2;

public class Guard extends Piece {

    public Guard() {
        super("O", Type.GUARD);
    }

    /**
     * Returns true if the Guard can move onto the given cell.
     * @param cell Cell to check if the Guard can move onto
     * @return True, if Guard can move onto given cell, false otherwise
     */
    @Override
    public boolean canMoveOnto(Cell cell) { // TODO
        
    	// Checks if the cell's piece is null
    	// If it fulfills that conditions, a guard can move onto that space
    	if (cell.getPiece() == null) {
    		
    		return true;

		}
    	else {
			return false;
		}
    	
    	
    }
}
