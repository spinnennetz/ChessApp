package GUI;

import javax.swing.JPanel;

import CheckField.CheckField;
import Figures.Figure;
import Vector.Vector;

public class ChessPanel extends JPanel {
	
	public Figure figure;
	public Vector position;
	public CheckField chessField;

	public ChessPanel(CheckField chessField, Vector position) {
		super();
		this.chessField = chessField;
		this.figure = chessField.getFieldValue(position);
		this.position = position;
	}
	
	public Figure getFigure() {
		return this.figure;
	}
	
	public Vector getPosition() {
		return this.position;
	}
	
	public CheckField getCheckField() {
		return this.chessField;
	}

}
