package Vector;

public class Vector {
	
	int dimension;
	int[] vector;

	public Vector(int dimension) {
		this.dimension = dimension;
		this.vector = new int[this.dimension];
		for (int i=0; i<this.dimension; i++) {
			this.vector[i] = 0;
		}
	}
	
	public Vector(int... values) {
		this.dimension = values.length;
		this.vector = new int[values.length];
		for(int i=0; i<this.dimension; i++) {
			this.vector[i] = values[i];
		}
	}
	
	public int getDimension() {
		return this.dimension;
	}

	public int getValue(int indicator) {
		return this.vector[indicator];
	}
	
	//TODO: exceptions
	public void setValue(int indicator, int value) {
		this.vector[indicator] = value;
	}
	
	public void setAllValues(int value) {
		for(int i=0; i<this.dimension; i++) {
			this.vector[i] = value;
		}
	}
//	public int[] getAsArray() {
//		return this.vector;
//	}
	
	public Vector scalar(int scalar) {
		Vector result = new Vector(this.dimension);
		for (int i=0; i<this.dimension; i++) {
			result.setValue(i, this.vector[i] * (-1));
		}
		return result;
	}
	
	//TODO: exceptions
	public Vector add(Vector toAddVector) {
		Vector result = new Vector(this.dimension);
		for (int i=0; i<this.dimension; i++) {
			result.setValue(i, this.vector[i]+ toAddVector.getValue(i));
		}
		return result;
	}
	
	public boolean equals(Vector checkableVector) {
		boolean equal = true;
		for (int i=0; i<this.dimension; i++) {
			if (this.vector[i] != checkableVector.getValue(i)) {
				equal = false;
			}
		}
		return equal;
	}
	
	public boolean inBounds(Vector lowerBounds, Vector upperBounds) {
		boolean inBounds = true;
		if (this.vector[0] < lowerBounds.getValue(0)) {
			inBounds = false;
		} else if (this.vector[0] >= upperBounds.getValue(0)) {
			inBounds = false;
		} else if (this.vector[1] < lowerBounds.getValue(1)) {
			inBounds = false;
		} else if (this.vector[1] >= upperBounds.getValue(1)) {
			inBounds = false;
		}
		return inBounds;
	}
	
	public int getMax() {
		int max = this.vector[0];
		for (int i=1; i<this.vector.length; i++) {
			max = Math.max(max, this.vector[i]);
		}
		return max;
	}
	
}
