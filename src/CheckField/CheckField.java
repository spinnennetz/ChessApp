package CheckField;
import Vector.Vector;
import CheckField.GridField;
import Figures.Figure;
import java.util.LinkedList;
import java.util.List;

public class CheckField extends GridField<Figure> {
	
	public Figure[][] checkField;
	public boolean[] check;
	public boolean checkMate;
	public List<Figure> beatenFigures;

	public CheckField(Vector sizes) {
		super(sizes);
		this.checkField = new Figure[sizes.getValue(0)][sizes.getValue(1)];
		this.gridfield = this.checkField;
		this.check = new boolean[2];
		this.checkMate = false;
		this.beatenFigures = new LinkedList<Figure>();
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
	
	public boolean getCheck(int color) {
		if (color < 0) {
			return this.check[0];
		} else if (color > 0) {
			return this.check[1];
		}
		return false;
	}
	
	public boolean getCheckMate() {
		return this.checkMate;
	}
	
	public void setCheck(int color, boolean check) {
		if (color < 0) {
			this.check[0] = check;
		} else if (color > 0) {
			this.check[1] = check;
		}
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
	
	public List<Vector[]> getAllPossibleMoves(int color) {
		List<Figure> figureList = this.getCurrentFigures();
		List<Vector[]> allPossibleMoves = new LinkedList<Vector[]>();
		for (Figure figure : figureList) {
			if (figure.getColor() == color) {
				Vector[] move = new Vector[2];
				Vector figurePos = figure.getPosition();
				move[0] = figurePos;
				PossibleMovesField possibleMoves = figure.showPossibleMoves(figurePos, this);
				List<Vector> possibleTargets = possibleMoves.getTrueOverlayPositions(possibleMoves);
				for (Vector possibleTarget : possibleTargets) {
					move[1] = possibleTarget;
					allPossibleMoves.add(move);
				}
			}
			
		}
		return allPossibleMoves;
	}
	
	public ThreatField getThreats(int color) {
		ThreatField result = new ThreatField(this.sizes);
		PossibleMovesField possibleMoves = new PossibleMovesField(this.sizes);
		List<Figure> figureList = this.getCurrentFigures();
		for (Figure figure : figureList) {
			if(figure.getColor() == color) {
				possibleMoves = figure.showPossibleMoves(figure.getPosition(), this);
				result = result.addField(possibleMoves.toThreat());
			}
		}
		return result;
	}
	
	public int getThreatsAt(Vector position, int color) {
		ThreatField threats = this.getThreats(color);
		int threatNumber = threats.getFieldValue(position);
		return threatNumber;
	}
	
	public Figure getKing(int color) {
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				Figure checkableFigure = this.getFieldValue(new Vector(i,j));
				if(checkableFigure != null) {
					if (checkableFigure.getFigureType() == "King") {
						if (checkableFigure.getColor() == color) {
							return checkableFigure;
						}
					}
				}
			}
		}
		return null;
	}
	
	public boolean checkCheck(int color) {
		if (this.getKing(color) == null) {
			this.checkMate = true;
			return true;
		} else {
			return this.getKing(color).getCurrentInThreats(this) > 0;
		}
	}
	
	public CheckField moveFigure(Vector startPosition, Vector targetPosition) {
		CheckField newCheckField = this;
		Figure movingFigure = this.getFieldValue(startPosition);
		newCheckField = this.showMove(startPosition, targetPosition);
		if(movingFigure != null) {
			movingFigure.moveTo(targetPosition, this);
		}
		return newCheckField;
	}
	
	public CheckField moveBackFigure(Vector startPosition, Vector targetPosition) {
		CheckField newCheckField = this;
		Figure movingFigure = this.getFieldValue(targetPosition);
		newCheckField = this.showMove(targetPosition, startPosition);
		
		if(movingFigure != null) {
			movingFigure.moveBackTo(startPosition, this);
		}
		Figure lastTargetFigure = this.beatenFigures.get(0);
		this.setFieldValue(lastTargetFigure.getPosition(), lastTargetFigure);
		return newCheckField;
	}
	
	public CheckField showMove(Vector startPosition, Vector targetPosition) {
		CheckField newCheckField = this;
		Figure movingFigure = newCheckField.getFieldValue(startPosition);
		newCheckField.setFieldValue(startPosition, null);
		newCheckField.setFieldValue(targetPosition, movingFigure);
		newCheckField.setCheck(-1, newCheckField.checkCheck(-1));
		newCheckField.setCheck(1, newCheckField.checkCheck(1));
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
	
	public CheckField copyCheckField() {
		CheckField copiedCheckField = new CheckField(this.sizes);
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				Figure figure = this.checkField[i][j];
				Figure clonedFigure;
				if (figure != null) {
					clonedFigure = figure.clone();
				} else {
					clonedFigure = null;
				}
				copiedCheckField.setFieldValue(new Vector(i,j), clonedFigure);
			}
		}
		return copiedCheckField;
	}
}
