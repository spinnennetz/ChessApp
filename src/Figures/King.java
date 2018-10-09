package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class King extends Figure {

	public boolean hasBeenMoved;

	public King(Vector position, int color) {
		super(position, color);
		this.figValue = 500;
		this.hasBeenMoved = false;
	}
	
	
	@Override
	public PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions) {
		PossibleMovesField possibleStraightMoves = this.showAllPossibleStraightMoves(position, figurePositions, 2);
		PossibleMovesField possibleDiagonalMoves = this.showAllPossibleDiagonalMoves(position, figurePositions, 2);
		PossibleMovesField possibleMoves = possibleStraightMoves.addField(possibleDiagonalMoves);
		return possibleMoves;
	}

	@Override
	public String getFigureType() {
		return "King";
	}

	@Override
	public Figure clone() {
		King clone = new King(this.position, this.color);
		clone.hasBeenMoved = this.hasBeenMoved;
		return clone;
	}

	@Override
	public Figure opponentClone() {
		King clone = new King(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
