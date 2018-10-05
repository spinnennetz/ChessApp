package CheckField;
import Vector.Vector;
import CheckField.GridField;

public class ThreatField extends GridField<Integer> {
	
	public int[][] threatField;

	public ThreatField(Vector sizes) {
		super(sizes);
		this.threatField = new int[sizes.getValue(0)][sizes.getValue(1)];
		this.gridfield = this.threatField;
	}

	@Override
	public void setFieldValue(Vector position, Integer value) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		this.threatField[xPos][yPos] = value;
		this.gridfield = this.threatField;
	}

	@Override
	public Integer getFieldValue(Vector position) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		return this.threatField[xPos][yPos];
	}
	
	public ThreatField addField(ThreatField field) {
		ThreatField result = new ThreatField(this.sizes);
		Vector position;
		int value;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				position = new Vector(i,j);
				value = this.getFieldValue(position) + field.getFieldValue(position);
				result.setFieldValue(position, value);
			}
		}
		return result;
	}

}
