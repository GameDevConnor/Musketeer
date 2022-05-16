package lab2.testing;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import lab2.Board;
import lab2.Cell;
import lab2.Coordinate;
import lab2.Move;

public class BoardTest {

    private Board board;

    
	Coordinate e1C = new Coordinate(0, 4);
	Coordinate d1C = new Coordinate(0, 3);
	Coordinate d3C = new Coordinate(2, 3);
	Coordinate e2C = new Coordinate(1, 4);
	Coordinate c1C = new Coordinate(0, 2);
	Coordinate c2C = new Coordinate(1, 2);
	Coordinate c3C = new Coordinate(2, 2);
	Coordinate c4C = new Coordinate(3, 2);
	Coordinate b3C = new Coordinate(2, 1);
	Coordinate a5C = new Coordinate(4, 0);
	Coordinate a4C = new Coordinate(3, 0);
	Coordinate b5C = new Coordinate(4, 1);
	
	Cell e1 = new Cell(e1C);
	Cell d1 = new Cell(d1C);
	Cell d3 = new Cell(d3C);
	Cell e2 = new Cell(e2C);
	Cell c1 = new Cell(c1C);
	Cell c2 = new Cell(c2C);
	Cell c3 = new Cell(c3C);
	Cell c4 = new Cell(c4C);
	Cell b3 = new Cell(b3C);
	Cell a5 = new Cell(a5C);
	Cell a4 = new Cell(a4C);
	Cell b5 = new Cell(b5C);
    
    
    
    @Before
    public void setup() {
        this.board = new Board();
    }

    @Test
    public void testGetCell() {
        Cell cell = board.getCell(new Coordinate(1, 4));
        Assert.assertNotNull(cell.getPiece());
    }
    
    @Test
    public void musketeerPossibleCells() {
    	    	
    	List<String> correctCells = new ArrayList<String>();
    	
    	board.move(new Move(e1, d1));
    	board.move(new Move(e2, e1));
    	
    	correctCells.add(board.getCell(new Coordinate(4, 0)).toString()); //A5
    	correctCells.add(board.getCell(new Coordinate(2, 2)).toString()); //C3
    	correctCells.add(board.getCell(new Coordinate(0, 3)).toString()); //D1    	
    	    	
    	Assert.assertEquals(board.getPossibleCells().toString(), correctCells.toString());

    }
    
    @Test
    public void guardPossibleCells() {
    	    	
    	List<String> correctCells = new ArrayList<String>();

    	
    	board.move(new Move(e1, d1));
    	board.move(new Move(e2, e1));
    	board.move(new Move(d1, c1));
    	
    	correctCells.add(board.getCell(new Coordinate(0, 4)).toString()); //E1
    	correctCells.add(board.getCell(new Coordinate(2, 4)).toString()); //E3  	
    	correctCells.add(board.getCell(new Coordinate(1, 3)).toString()); //D2   	

    	
    	Assert.assertEquals(board.getPossibleCells().toString(), correctCells.toString());

    }
    
    @Test
    public void musketeerPossibleDest() {
    	
    	Cell cells = board.getCell(new Coordinate(2, 2));
    	
    	Assert.assertEquals(board.getPossibleDestinations(cells).size(), 4);

    }
    
    @Test
    public void guardPossibleDest() {
    	board.move(new Move(e1, d1));
    	Cell cells = board.getCell(new Coordinate(1, 4));

    	Assert.assertEquals(board.getPossibleDestinations(cells).size(), 1);
    }
    
    @Test
    public void guardPossibleMoves() {
  		
    	List<Move> moves = new ArrayList<Move>();
    	
    	moves.add(new Move(e1,e2));
    	moves.add(new Move(e1,d1));

    	moves.add(new Move(c3,c4));
    	moves.add(new Move(c3,c2));
    	moves.add(new Move(c3,d3));
    	moves.add(new Move(c3,b3));
    	
    	moves.add(new Move(a5,a4));
    	moves.add(new Move(a5,b5));
    	    	
    	Assert.assertEquals(board.getPossibleMoves().toString(), moves.toString());

    }
  

}
