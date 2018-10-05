package Figures;
//import java.util.List;

import java.util.List;

import CheckField.CheckField;
import CheckField.PossibleMovesField;
import Vector.Vector;

public abstract class Figure {
	
	public Vector position;
	public int xPos;
	public int yPos;
	public int figValue;
	public boolean beaten;
	public int color;

	public Figure(Vector position, int color) {
		this.position = position;
		this.xPos = position.getValue(0);
		this.yPos = position.getValue(1);
		this.beaten = false;
		this.color = color;
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
	
	public int getOutThreats(Vector position, CheckField figurePositions) {
		int threatsCounter = 0;
		PossibleMovesField possibleMoves = this.showPossibleMoves(position, figurePositions);
		List<Vector> overlayPositions = possibleMoves.getOverlayPositions(figurePositions);
		for (Vector overlayPosition : overlayPositions) {
			if (possibleMoves.getFieldValue(overlayPosition)) {
				threatsCounter++;
			}
		}
		return threatsCounter;
	}
	
	public void moveTo(Vector targetPosition, CheckField figurePositions) {
		PossibleMovesField possibleMoves = this.showPossibleMoves(this.position, figurePositions);
		if (possibleMoves.getFieldValue(targetPosition)) {
			this.setPosition(targetPosition);
		}
		Figure figureOnTarget = figurePositions.getFieldValue(targetPosition);
		if (figureOnTarget != null) {
			figureOnTarget.remove();
		}
	}

	public void remove() {
		this.beaten = true;
	}
	
}
