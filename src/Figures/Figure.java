package Figures;
//import java.util.List;

import java.util.List;

import CheckField.CheckField;
import CheckField.PossibleMovesField;
import CheckField.ThreatField;
import Vector.Vector;

public abstract class Figure {
	
	public Vector position;
	public int xPos;
	public int yPos;
	public int figValue;
	public boolean beaten;
	public int color;
	public boolean hasBeenMoved;

	public Figure(Vector position, int color) {
		this.position = position;
		this.xPos = position.getValue(0);
		this.yPos = position.getValue(1);
		this.beaten = false;
		this.color = color;
		this.hasBeenMoved = false;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public int getXPosition() {
		return this.xPos;
	}
	
	public int getYPosition() {
		return this.yPos;
	}
	
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public boolean isBeaten() {
		return this.beaten;
	}
	
	public void changeColor() {
		if (this.color < 0) {
			this.color = 1;
		} else if (this.color > 0) {
			this.color = -1;
		} else {
			this.color = 0;
		}
	}
	
	public int getColor() {
		return this.color;
	}
	
	public int getFigValue() {
		return this.figValue;
	}
	
	public abstract String getFigureType();
	
	public abstract Figure clone();
	
	public abstract Figure opponentClone();
	
	public abstract PossibleMovesField showPossibleMoves(Vector position, CheckField figurePositions);
	
	public PossibleMovesField showNotCheckmatePossibleMoves(Vector position, CheckField figurePositions) {
		PossibleMovesField possibleMoves = this.showPossibleMoves(position, figurePositions);
		possibleMoves = this.checkPossibleMovesForCheck(possibleMoves, figurePositions);
		return possibleMoves;
	}
	
	public Vector showMoveResult(Vector position, Vector moveLength) {
		return position.add(moveLength);
	}
	
	//direction = left ? -1 : 1
	public Vector showDiagonalResult(Vector position, int moveLength, int straightDirection, int sideDirection) {
		Vector move = new Vector(2);
		move.setValue(0, moveLength * straightDirection);
		move.setValue(1, moveLength * sideDirection);
		return showMoveResult(position, move);
	}
	
	public Vector showStraightResult(Vector position, int moveLength, boolean side) {
		Vector move = new Vector(2);
		int indicator = side ? 1 : 0;
		move.setValue(indicator, moveLength);
		return showMoveResult(position, move);
	}
	
	public Vector showKnightResult(Vector position, int straightDirection, int sideDirection, boolean side) {
		int indicator = side ? 1 : 0;
		int directionLength = side ? sideDirection : straightDirection;
		Vector move = new Vector(2);
		Vector direction = new Vector(2);
		move.setValue(0, straightDirection);
		move.setValue(1, sideDirection);
		direction.setValue(indicator, directionLength);
		move = move.add(direction);
		return showMoveResult(position, move);
	}
	
	public boolean moveLeadsToCheck(Vector checkablePosition, CheckField figurePositions) {
		CheckField checkNewPositions = figurePositions.showMove(this.position, checkablePosition);
		boolean check = checkNewPositions.getCheck(this.color);
		return check;
	}
	
	public PossibleMovesField checkPossibleMovesForCheck(PossibleMovesField possibleMoves, CheckField figurePositions) {
		PossibleMovesField forCheckCheckedPossibleMoves = possibleMoves;
		int fieldXSize = forCheckCheckedPossibleMoves.getXSize();
		int fieldYSize = forCheckCheckedPossibleMoves.getYSize();
		for (int i=0; i < fieldXSize; i++) {
			for (int j=0; j < fieldYSize; j++) {
				position = new Vector(i,j);
				boolean leadsToCheck = false;
				if (forCheckCheckedPossibleMoves.getFieldValue(position)) {
					leadsToCheck = this.moveLeadsToCheck(position, figurePositions);
					forCheckCheckedPossibleMoves.setFieldValue(position, !leadsToCheck);
				}
			}
		}
		return forCheckCheckedPossibleMoves;
	}
	
	public PossibleMovesField showAllPossibleStraightMoves(Vector position, CheckField figurePositions, int maxMoveLength) {
		Vector fieldSizes = figurePositions.getSizes();	
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
				while(!hitFigure && moveLength < maxMoveLength) {
					checkablePosition = showStraightResult(position, moveLength * moveStraightDirection, side);
					outOfBorder = !checkablePosition.inBounds(new Vector(0,0), fieldSizes);
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
	
	public PossibleMovesField showAllPossibleDiagonalMoves(Vector position, CheckField figurePositions, int maxMoveLength) {
		Vector fieldSizes = figurePositions.getSizes();
		PossibleMovesField possibleMoves = new PossibleMovesField(fieldSizes);
		Vector checkablePosition;
		int moveSideDirection;
		int moveStraightDirection;
		int moveLength;
		boolean hitFigure;
		boolean outOfBorder;
		for (int k=0; k<=1; k++) {
			moveSideDirection = (k==0) ? -1 : 1;
			for (int j=0; j<=1; j++) {
				hitFigure = false;
				moveStraightDirection = (j==0) ? -1 : 1;
				moveLength = 1;
				while(!hitFigure && moveLength < maxMoveLength) {
					checkablePosition = showDiagonalResult(position, moveLength, moveStraightDirection, moveSideDirection);
					outOfBorder = !checkablePosition.inBounds(new Vector(0,0), fieldSizes);
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
	
	public int getOutThreats(Vector position, CheckField figurePositions) {
		int threatsCounter = 0;
		PossibleMovesField possibleMoves = this.showPossibleMoves(position, figurePositions);
		List<Vector> overlayPositions = possibleMoves.getOverlayPositions(figurePositions);
		for (Vector overlayPosition : overlayPositions) {
			if (possibleMoves.getFieldValue(overlayPosition)) {
				if(figurePositions.getFieldValue(overlayPosition).getColor() != this.color) {
					threatsCounter++;
				}
			}
		}
		return threatsCounter;
	}
	
	public int getCurrentInThreats(CheckField figurePositions) {
		ThreatField inThreats = figurePositions.getThreats(this.color*(-1));
		int threatsCounter = inThreats.getFieldValue(this.position);
		return threatsCounter;
	}
	
	public void moveTo(Vector targetPosition, CheckField figurePositions) {
		this.setPosition(targetPosition);
		Figure figureOnTarget = figurePositions.getFieldValue(targetPosition);
		figurePositions.beatenFigures.add(0, figureOnTarget);
		if (figureOnTarget != null) {
			figureOnTarget.remove();
		}
		this.hasBeenMoved = true;
	}
	
	public void moveBackTo(Vector startPosition, CheckField figurePositions) {
		Vector targetPosition = this.position;
		this.setPosition(startPosition);
		Figure figureOnTarget = figurePositions.beatenFigures.get(0);
		if (figureOnTarget != null) {
			figureOnTarget.getBack();
			figureOnTarget.setPosition(targetPosition);
		}
		//TODO: has been moved
	}
	
	public void getBack() {
		this.beaten = false;
	}

	public void remove() {
		this.beaten = true;
	}
	
}
