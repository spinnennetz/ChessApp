package CheckField;
import java.util.LinkedList;
import java.util.List;
import Vector.Vector;


/**
 * @author johannatengler
 *
 */
public abstract class GridField<Type> {
	
	public Vector sizes;
	public int xSize;
	public int ySize;
	public int dimension;
	public Object gridfield;

	/**
	 * 
	 */
	public GridField(Vector sizes) {
		this.sizes = sizes;
		this.xSize = sizes.getValue(0);
		this.ySize = sizes.getValue(1);
		this.dimension = sizes.getDimension(); //here always 2
		this.gridfield = new Object[this.xSize][this.ySize];
	}
	
	public int getDimension() {
		return this.dimension;
	}
	
	public Vector getSizes() {
		return this.sizes;
	}
	
	public int getXSize() {
		return this.xSize;
	}
	
	public int getYSize() {
		return this.ySize;
	}

	abstract public void setFieldValue(Vector position, Type value);
	
	abstract public Type getFieldValue(Vector position);
	
	public List<Vector> getOverlayPositions(GridField<?> field) {
		List<Vector> positions = new LinkedList<Vector>();
		Vector currentPos;
		for (int i=0; i<this.xSize; i++) {
			for (int j=0; j<this.ySize; j++) {
				currentPos = new Vector(i,j);
				if (this.getFieldValue(currentPos) != null && field.getFieldValue(currentPos) != null) {
					positions.add(currentPos);
				}
			}
		}
		return positions;
	}

}
