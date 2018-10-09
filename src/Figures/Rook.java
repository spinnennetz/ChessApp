package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class Rook extends Figure {

	public boolean hasBeenMoved;

	public Rook(Vector position, int color) {
		super(position, color);
		this.figValue = 5;
		this.hasBeenMoved = false;
	}
	
	
	@Override
	public PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions) {
		Vector fieldSizes = figurePositions.getSizes();	
		PossibleMovesField possibleMoves = this.showAllPossibleStraightMoves(position, figurePositions, fieldSizes.getMax());
		return possibleMoves;
	}

	@Override
	public String getFigureType() {
		return "Rook";
	}
	
	@Override
	public Figure clone() {
		Rook clone = new Rook(this.position, this.color);
		clone.hasBeenMoved = this.hasBeenMoved;
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Rook clone = new Rook(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
