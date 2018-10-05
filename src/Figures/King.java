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
		Vector fieldSizes = figurePositions.getSizes();
		int fieldXSize = fieldSizes.getValue(0);
		int fieldYSize = fieldSizes.getValue(1);
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		int moveSideDirection;
		int moveStraightDirection;
		boolean outOfBorder;
		boolean hitOwnFigure;
		boolean side;
		for (int j=0; j<=1; j++) {
			moveSideDirection = (j==0) ? -1 : 1;
			for (int i=0; i<=1; i++) {
				hitOwnFigure = false;
				outOfBorder = false;
				moveStraightDirection = (i==0) ? -1 : 1;
				checkablePosition = showDiagonalResult(position, 1, moveStraightDirection, moveSideDirection);
				if (checkablePosition.getValue(0) < 0) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(0) >= fieldXSize) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(1) < 0) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(1) >= fieldYSize) {
					outOfBorder = true;
				}
				if (!outOfBorder) {
					Figure resultFieldFigure = figurePositions.getFieldValue(checkablePosition);
					if (resultFieldFigure != null && resultFieldFigure.getColor() == this.color) {
						hitOwnFigure = true;
					}
					if(!hitOwnFigure) {
						possibleMoves.setFieldValue(checkablePosition, true);
					}
				}
			}
		}
		for (int j=0; j<=1; j++) {
			side = (j==0) ? false : true;
			for (int i=0; i<=1; i++) {
				hitOwnFigure = false;
				outOfBorder = false;
				moveStraightDirection = (i==0) ? -1 : 1;
				checkablePosition = showStraightResult(position, moveStraightDirection, side);
				if (checkablePosition.getValue(0) < 0) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(0) > 7) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(1) < 0) {
					outOfBorder = true;
				} else if (checkablePosition.getValue(1) > 7) {
					outOfBorder = true;
				}
				if (!outOfBorder) {
					Figure resultFieldFigure = figurePositions.getFieldValue(checkablePosition);
					if (resultFieldFigure != null && resultFieldFigure.getColor() == this.color) {
						hitOwnFigure = true;
					}
					if(!hitOwnFigure) {
						possibleMoves.setFieldValue(checkablePosition, true);
					}
				}
			}
		}
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "King";
	}

	@Override
	public Figure clone() {
		King clone = new King(this.position, this.color);
		return clone;
	}

	@Override
	public Figure opponentClone() {
		King clone = new King(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
