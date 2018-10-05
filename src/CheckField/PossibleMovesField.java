package CheckField;
import Vector.Vector;

import java.util.LinkedList;
import java.util.List;

import CheckField.GridField;

public class PossibleMovesField extends GridField<Boolean> {
	
	public boolean[][] movesField;

	public PossibleMovesField(Vector sizes) {
		super(sizes);
		this.movesField = new boolean[sizes.getValue(0)][sizes.getValue(1)];
		this.gridfield = this.movesField;
	}

	@Override
	public void setFieldValue(Vector position, Boolean value) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		this.movesField[xPos][yPos] = value;
		this.gridfield = this.movesField;
	};
	
	@Override
	public Boolean getFieldValue(Vector position) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		return this.movesField[xPos][yPos];
	}
	
	public PossibleMovesField addField(PossibleMovesField toAddField) {
		PossibleMovesField result = this;
		Vector position;
		for (int i=0; i<result.getXSize(); i++) {
			for (int j=0; j<result.getYSize(); j++) {
				position = new Vector(i,j);
				if (result.getFieldValue(position) || toAddField.getFieldValue(position)) {
					result.setFieldValue(position, true);
				}
			}
		}
		return result;
	}
	
	public List<Vector> getTrueOverlayPositions(PossibleMovesField field) {
		List<Vector> positions = new LinkedList<Vector>();
		Vector currentPos;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				currentPos = new Vector(i,j);
				if (this.getFieldValue(currentPos) || field.getFieldValue(currentPos)) {
					positions.add(currentPos);
				}
			}
		}
		return positions;
	}
	
	public ThreatField toThreat() {
		ThreatField threatField = new ThreatField(this.sizes);
		Vector position;
		int value;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				position = new Vector(i,j);
				value = this.getFieldValue(position) ? 1 : 0;
				threatField.setFieldValue(position, value);
			}
		}
		return threatField;
	}
}
