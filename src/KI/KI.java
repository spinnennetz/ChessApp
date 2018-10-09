package KI;

import Vector.Vector;

import java.util.List;

import CheckField.CheckField;
import Figures.Figure;

public class KI {
	
	public static Vector[] calculateNext(CheckField checkfield, int color, int iterations) {
		return MiniMax.init(checkfield, color, iterations);
	}

}
