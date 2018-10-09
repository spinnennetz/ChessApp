package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class Bishop extends Figure {

	public boolean hasBeenMoved;

	public Bishop(Vector position, int color) {
		super(position, color);
		this.figValue = 3;
		this.hasBeenMoved = false;
	}
	
	
	@Override
	public PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions) {
		Vector fieldSizes = figurePositions.getSizes();	
		PossibleMovesField possibleMoves = this.showAllPossibleDiagonalMoves(position, figurePositions, fieldSizes.getMax());
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "Bishop";
	}
	
	@Override
	public Figure clone() {
		Bishop clone = new Bishop(this.position, this.color);
		clone.hasBeenMoved = this.hasBeenMoved;
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Bishop clone = new Bishop(this.position, this.color);
		clone.changeColor();
		return clone;
	}



}
