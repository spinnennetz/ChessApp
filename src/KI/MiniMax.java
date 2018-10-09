package KI;


import java.util.LinkedList;
import java.util.List;

import CheckField.*;
import Figures.Figure;
import Vector.Vector;

public class MiniMax {
	
	public static int iterations;
	public static CheckField checkfield;
	public static CheckField safetyCheckfield;
	public static Vector[] savedMove;
	
	public static int max(int color, int depth) {
		List<Vector[]> allPossibleMoves = checkfield.getAllPossibleMoves(color);
		if(depth == 0 || allPossibleMoves.isEmpty()) {
			return rate(color);
		}
		int maxValue = Integer.MIN_VALUE;
		while (!allPossibleMoves.isEmpty()) {
			Vector[] move = allPossibleMoves.get(0);
			//checkfield.print();
			checkfield.moveFigure(move[0], move[1]);
			//checkfield.print();
			int value = min(color*(-1), depth - 1);
			//checkfield.moveBackFigure(move[0], move[1]);
			checkfield = safetyCheckfield.copyCheckField();
			if (value > maxValue) {
				maxValue = value;
				if (depth == iterations) {
					savedMove = move;
				}
			}
			allPossibleMoves.remove(0);
		}
		return maxValue;
		
	}
	
	public static int min(int color, int depth) {
		List<Vector[]> allPossibleMoves = checkfield.getAllPossibleMoves(color);
		if(depth == 0 || allPossibleMoves.isEmpty()) {
			return rate(color);
		}
		int minValue = Integer.MAX_VALUE;
		while (!allPossibleMoves.isEmpty()) {
			Vector[] move = allPossibleMoves.get(0);
			//checkfield.print();
			checkfield.moveFigure(move[0], move[1]);
			//checkfield.print();
			int value = max(color*(-1), depth - 1);
			//checkfield.moveBackFigure(move[0], move[1]);
			checkfield = safetyCheckfield.copyCheckField();
			if (value < minValue) {
				minValue = value;
				if (depth == iterations) {
					savedMove = move;
				}
			}
			allPossibleMoves.remove(0);
		}
		return minValue;
		
	}
	
	public static Vector[] init(CheckField figurePositions, int color, int depth) {
		iterations = depth;
		checkfield = figurePositions.copyCheckField();
		safetyCheckfield = figurePositions.copyCheckField();
		savedMove = null;
		int rating = max(color, iterations);
		System.out.println("rating: " + rating);
		return savedMove;
	}
	
	
	public static int rate(int color) {
		int leftFigsRating = 0;
		int threatRating = 0;
		List<Figure> figureList = checkfield.getCurrentFigures();
		for (Figure figure : figureList) {
			Vector figPosition = figure.getPosition();
			int figColor = figure.getColor();
			leftFigsRating += figure.getFigValue() * figColor/color;
			threatRating += checkfield.getThreatsAt(figPosition, figColor) * figColor/color;
		}
		int rating = threatRating - leftFigsRating;
		return rating;
	}

}
