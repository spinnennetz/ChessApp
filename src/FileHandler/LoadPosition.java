package FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import CheckField.CheckField;
import Figures.*;
import Vector.Vector;

public class LoadPosition {

	public static CheckField loadPosition(int xSize, int ySize, String filePath) {
		Vector sizes = new Vector(xSize,ySize);
		CheckField startPositions = new CheckField(sizes);
		
		Vector position;
		Figure figure;
		String figureCode;
        
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			Object[] streamArray = stream.toArray();
			
	        for (int i=0; i<xSize; i++) {
	        	for (int j=0; j<ySize; j++) {
	        		position = new Vector(i,j);
	        		figureCode = streamArray[j+(i*ySize)].toString().replaceAll("\\D+","");
	        		figure = createFigOutOfString(figureCode, position);
	        		startPositions.setFieldValue(position, figure);
	        		//System.out.println("i: "+ i + ", j: " + j + ", value: " + startPositions.getFieldValue(position));
	        	}
	        }
	        return startPositions;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private static Figure createFigOutOfString(String string, Vector position) {
		int figureCode = Integer.parseInt(string);
		if (figureCode==1) {
			return new Pawn(position, 1);
		} else if (figureCode==2) {
			return new Knight(position, 1);
		} else if (figureCode==3) {
			return new Bishop(position, 1);
		} else if (figureCode==4) {
			return new Rook(position, 1);
		} else if (figureCode==5) {
			return new Queen(position, 1);
		} else if (figureCode==6) {
			return new King(position, 1);
		} else {
			return null;
		}
	}

}
