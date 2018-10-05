package CheckField;
import Vector.Vector;
import CheckField.GridField;
import Figures.Figure;
import java.util.LinkedList;
import java.util.List;

public class CheckField extends GridField<Figure> {
	
	public Figure[][] checkField;

	public CheckField(Vector sizes) {
		super(sizes);
		this.checkField = new Figure[sizes.getValue(0)][sizes.getValue(1)];
		this.gridfield = this.checkField;
	}

	@Override
	public void setFieldValue(Vector position, Figure value) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		this.checkField[xPos][yPos] = value;
		this.gridfield = this.checkField;
	};

	@Override
	public Figure getFieldValue(Vector position) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		return this.checkField[xPos][yPos];
	}
	
	public List<Figure> getCurrentFigures() {
		List<Figure> figureList = new LinkedList<Figure>();
		Vector currentPosition;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				currentPosition = new Vector(i,j);
				if(this.getFieldValue(currentPosition) != null) {
					figureList.add(this.getFieldValue(currentPosition));
				}
			}
		}
		return figureList;
	}
	
	public ThreatField getThreats() {
		ThreatField result = new ThreatField(this.sizes);
		PossibleMovesField possibleMoves = new PossibleMovesField(this.sizes);
		List<Figure> figureList = this.getCurrentFigures();
		for (Figure figure : figureList) {
			possibleMoves = figure.showPossibleMoves(figure.getPosition(), this);
			result = result.addField(possibleMoves.toThreat());
		}
		return result;
	}
	
	public CheckField moveFigure(Vector startPosition, Vector targetPosition) {
		CheckField newCheckField = this;
		Figure movingFigure = newCheckField.getFieldValue(startPosition);
		movingFigure.moveTo(targetPosition, newCheckField);
		newCheckField.setFieldValue(startPosition, null);
		newCheckField.setFieldValue(targetPosition, movingFigure);
		System.out.println("startPosition: (" + startPosition.getValue(0) + ", " + startPosition.getValue(1) + ")");
		System.out.println("startPositionFieldValue: " + newCheckField.getFieldValue(startPosition));
		System.out.println("targetPosition: (" + targetPosition.getValue(0) + ", " + targetPosition.getValue(1) + ")");
		System.out.println("targetPositionFieldValue: " + newCheckField.getFieldValue(targetPosition));
		return newCheckField;
	}
	
	public CheckField invertField() {
		CheckField invertedField = new CheckField(this.sizes);
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize/2; j++) {
				Vector position1 = new Vector(i,j);
				Vector position2 = new Vector(this.xSize-i-1, this.ySize-j-1);
			    Figure swap1 = this.getFieldValue(position1);
			    if (swap1 != null) {
			    	swap1 = swap1.opponentClone();
			    	swap1.setPosition(position2);
			    }
			    Figure swap2 = this.getFieldValue(position2);
			    if (swap2 != null) {
			    	swap2 = swap2.opponentClone();
			    	swap2.setPosition(position1);
			    }
			    invertedField.setFieldValue(position1, swap2);
			    invertedField.setFieldValue(position2, swap1);
			}
		}
		return invertedField;
	}
	
	public CheckField addCheckField(CheckField toAddCheckField) {
		CheckField sumCheckField = this;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				Vector position = new Vector(i,j);
				Figure oldValue = this.getFieldValue(position);
				Figure newValue = toAddCheckField.getFieldValue(position);
				if (oldValue != null) {
					if (newValue != null) {
						sumCheckField.setFieldValue(position, newValue);
					}
				} else {
					if (newValue != null) {
						sumCheckField.setFieldValue(position, newValue);
					}
				}
			}
		}
		return sumCheckField;
	}
}
