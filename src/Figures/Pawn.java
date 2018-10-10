package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class Pawn extends Figure {
	
	public boolean walkedThrough;

	public Pawn(Vector position, int color) {
		super(position, color);
		this.figValue = 1;
		this.walkedThrough = false;
	}
	
	@Override
	public PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions) {
		Vector fieldSizes = figurePositions.getSizes();
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		boolean outOfBorder;
		boolean hitFigure;
		int straightDirection = 1;
		if (this.color == -1) {
			straightDirection = -1;
		}
		int moveLength = walkedThrough ? -1 : 1;
		moveLength *= hasBeenMoved ? 1 : 2;
		for (int i=-1; i<=1; i++){
			for (int j=1; j<=moveLength; j++) {
				hitFigure = false;
				if (i==0) {
					checkablePosition = showStraightResult(position, j * straightDirection, false);
				} else {
					checkablePosition = showDiagonalResult(position, j/j, straightDirection, i);
				}
				outOfBorder = !checkablePosition.inBounds(new Vector(0,0), fieldSizes);
				if(!outOfBorder) {
					Figure resultFieldFigure = figurePositions.getFieldValue(checkablePosition);
					possibleMoves.setFieldValue(checkablePosition, true);
					if (resultFieldFigure != null && resultFieldFigure.getColor() != this.color) {
						hitFigure = true;
					}
					if(!hitFigure && i!=0) {
						possibleMoves.setFieldValue(checkablePosition, false);
					}
					if (resultFieldFigure != null && i==0) {
						possibleMoves.setFieldValue(checkablePosition, false);
					}
				}
			}
		}
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "Pawn";
	}
	
	@Override
	public Figure clone() {
		Pawn clone = new Pawn(this.position, this.color);
		clone.hasBeenMoved = this.hasBeenMoved;
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Pawn clone = new Pawn(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
