package Figures;
import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public class Knight extends Figure {

	public boolean hasBeenMoved;

	public Knight(Vector position, int color) {
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
		boolean outOfBorder;
		boolean hitOwnFigure;
		boolean side;
		for (int k=0; k<=1; k++) {
			moveSideDirection = (k==0) ? -1 : 1;
			for (int j=0; j<=1; j++) {
				moveStraightDirection = (j==0) ? -1 : 1;
				for (int i=0; i<=1; i++){
					hitOwnFigure = false;
					outOfBorder = false;
					side = (i!=0);
					checkablePosition = showKnightResult(position, moveStraightDirection, moveSideDirection, side);
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
		}
		return possibleMoves;
	}
	
	@Override
	public String getFigureType() {
		return "Knight";
	}
	
	@Override
	public Figure clone() {
		Knight clone = new Knight(this.position, this.color);
		return clone;
	}

	@Override
	public Figure opponentClone() {
		Knight clone = new Knight(this.position, this.color);
		clone.changeColor();
		return clone;
	}
}
