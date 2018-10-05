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
		int fieldXSize = fieldSizes.getValue(0);
		int fieldYSize = fieldSizes.getValue(1);
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		int moveSideDirection;
		int moveStraightDirection;
		int moveLength;
		boolean side;
		boolean hitFigure;
		boolean outOfBorder;
		for (int k=0; k<=1; k++) {
			moveSideDirection = (k==0) ? -1 : 1;
			for (int j=0; j<=1; j++) {
				hitFigure = false;
				outOfBorder = false;
				moveStraightDirection = (j==0) ? -1 : 1;
				moveLength = 1;
				while(!hitFigure && moveLength < Math.max(fieldXSize, fieldYSize)) {
					System.out.println(Math.max(fieldXSize, fieldYSize));
					System.out.println(moveLength);
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
		for (int k=0; k<=1; k++) {
			side = (k==0) ? false : true;
			for (int j=0; j<=1; j++) {
				hitFigure = false;
				moveStraightDirection = (j==0) ? -1 : 1;
				moveLength = 1;
				while(!hitFigure && moveLength < Math.max(fieldXSize, fieldYSize)) {
					outOfBorder = false;
					checkablePosition = showStraightResult(position, moveLength * moveStraightDirection, side);
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
