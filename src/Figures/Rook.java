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
		int fieldXSize = fieldSizes.getValue(0);
		int fieldYSize = fieldSizes.getValue(1);		
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		int moveStraightDirection;
		int moveLength;
		boolean side;
		boolean hitFigure;
		boolean outOfBorder;
		for (int k=0; k<=1; k++) {
			side = (k!=0);
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
		return "Rook";
	}
	
	@Override
	public Figure clone() {
		Rook clone = new Rook(this.position, this.color);
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Rook clone = new Rook(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
