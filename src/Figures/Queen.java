package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class Queen extends Figure {

	public Queen(Vector position, int color) {
		super(position, color);
		this.figValue = 9;
	}
	
	
	@Override
	public PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions) {
		Vector fieldSizes = figurePositions.getSizes();	
		PossibleMovesField possibleStraightMoves = this.showAllPossibleStraightMoves(position, figurePositions, fieldSizes.getMax());
		PossibleMovesField possibleDiagonalMoves = this.showAllPossibleDiagonalMoves(position, figurePositions, fieldSizes.getMax());
		PossibleMovesField possibleMoves = possibleStraightMoves.addField(possibleDiagonalMoves);
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "Queen";
	}
	
	@Override
	public Figure clone() {
		Queen clone = new Queen(this.position, this.color);
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Queen clone = new Queen(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
