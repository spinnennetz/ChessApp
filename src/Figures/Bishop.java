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
		int fieldXSize = fieldSizes.getValue(0);
		int fieldYSize = fieldSizes.getValue(1);
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		int moveSideDirection;
		int moveStraightDirection;
		int moveLength;
		boolean outOfBorder;
		boolean hitFigure;
		for (int k=0; k<=1; k++) {
			moveSideDirection = (k==0) ? -1 : 1;
			for (int j=0; j<=1; j++) {
				hitFigure = false;
				moveStraightDirection = (j==0) ? -1 : 1;
				moveLength = 1;
				while(!hitFigure && moveLength < Math.max(fieldXSize, fieldYSize)) {
					outOfBorder = false;
					checkablePosition = showDiagonalResult(position, moveLength, moveStraightDirection, moveSideDirection);
					if (checkablePosition.getValue(0) < 0) {
						outOfBorder = true;
					} else if (checkablePosition.getValue(0) >= fieldXSize) {
						outOfBorder = true;
					} else if (checkablePosition.getValue(1) < 0) {
						outOfBorder = true;
					} else if (checkablePosition.getValue(1) >= fieldYSize) {
						outOfBorder = true;
					}
					if(!outOfBorder) {
						if(figurePositions.getFieldValue(checkablePosition) != null) {
							hitFigure = true;
						};
						if (!hitFigure || figurePositions.getFieldValue(checkablePosition).color != this.color) {
							possibleMoves.setFieldValue(checkablePosition, true);
						}
					}
					moveLength++;
				}
			}
		}
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "Bishop";
	}
	
	@Override
	public Figure clone() {
		Bishop clone = new Bishop(this.position, this.color);
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Bishop clone = new Bishop(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
