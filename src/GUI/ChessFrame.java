package GUI;
import CheckField.*;
import Figures.*;
import javax.swing.*;
import java.awt.*;
import Vector.*;

public class ChessFrame {
	
	public static ChessPanel[] chessPanels;
	public static String[] chessFieldLabels;
	public static int[] chessFieldColors;
	public static boolean[] possibleMoveFields;
	public static Vector sizes;
	public static CheckField checkField;
	public static JFrame frame;
	public static JPanel chessFieldPanel;
	public static boolean possibleMovesShowing;
	public static int player;
	public static Figure toMoveFigure;

	
	public static void initChessFrame(CheckField startPosition, int playerNumb) {
		//Creating the Frame
		frame = new JFrame("ChessFrame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
        frame.setVisible(true);
        init2D(startPosition, playerNumb);
	}
	
	public static void init2D(CheckField startPosition, int playerNumb) {
		

        checkField = startPosition;
		
		player = playerNumb;
		toMoveFigure = null;
		
		int xSize = startPosition.getXSize();
		int ySize = startPosition.getYSize();
		
		for (int i=0; i<xSize; i++) {
			for (int j=0; j<ySize; j++) {
				Vector position = new Vector(i,j);
				System.out.println("position: (" + i + ", " + j + ")");
				System.out.println("value: " + startPosition.getFieldValue(position));
			}
		}
		
        //Creating the BasePanel
        JPanel basePanel = new JPanel();
        basePanel.setBackground(Color.BLUE);
        
        chessFieldPanel = new JPanel();
        chessFieldPanel.setPreferredSize(new Dimension(500,500));
        chessFieldPanel.setLayout( new GridBagLayout() );

        String figureString;
        Figure startPositionFieldValue;
        sizes = new Vector(xSize, ySize);
        

        
        resetPossibleMoveFields();
         
        chessFieldLabels = new String[xSize*ySize];
        chessFieldColors = new int[xSize*ySize];
        
	    for(int i=0; i<xSize*ySize; i++) {
	    	chessFieldLabels[i] = "";
	    }
        
        for ( int i = 0; i<xSize; i++ ) {
        	for ( int j = 0; j<ySize; j++ ) {
        		startPositionFieldValue = checkField.getFieldValue(new Vector(i,j));
        		System.out.println(startPositionFieldValue);
        		if(startPositionFieldValue == null) {
        			figureString = "";
        		} else {
        			figureString = startPositionFieldValue.getFigureType();
        		}
        		chessFieldLabels[i+j*xSize] = figureString;
        		if (startPositionFieldValue != null) {
            		chessFieldColors[i+j*xSize] = startPositionFieldValue.getColor();
        		}
        	}
        }
        
        basePanel.add(chessFieldPanel);
        
        
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.CENTER, basePanel);
        frame.setVisible(true);
        
        showCheckField();
        
    }
	
	public static void setAsToMoveFigure (Figure figure) {
		toMoveFigure = figure;
	}
	
	public static Figure getToMoveFigure() {
		return toMoveFigure;
	}
	
	public static void resetPossibleMoveFields () {
		possibleMoveFields = new boolean[sizes.getValue(0) * sizes.getValue(1)];
		possibleMovesShowing = false;
	}
	
	public static void showPossibleMove (Vector position) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		int ySize = sizes.getValue(1);
		if(xPos * ySize + yPos < chessPanels.length) {
			possibleMoveFields[xPos * ySize + yPos] = true;
		}
		possibleMovesShowing = true;
	}
	
	public static boolean possibleMovesShowing() {
		return possibleMovesShowing;
	}
	
	public static void showCheckField () {
		
		int xSize = sizes.getValue(0);
		int ySize = sizes.getValue(1);
		

        
        chessPanels = new ChessPanel[xSize*ySize];
        


        JLabel chessFieldLabel;
        ChessAction chessAction = new ChessAction();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        
        
		for ( int i = 0; i<xSize; i++ ) {
        	for ( int j = 0; j<ySize; j++ ) {
        		chessFieldLabel = new JLabel(chessFieldLabels[i*ySize + j]);
        		chessFieldLabel.setFont(new Font("Verdana",1,15));
        		
        		chessPanels[i+j*xSize] = new ChessPanel(checkField, new Vector(j,i));
        		chessPanels[i+j*xSize].add(chessFieldLabel);
        		chessPanels[i+j*xSize].setBackground(Color.LIGHT_GRAY);
        		
        		if (i%2==0 &&(j+1)%2==0) {
        			chessPanels[i+j*xSize].setBackground(Color.DARK_GRAY);
        		} else if ((i+1)%2==0 &&j%2==0) {
        			chessPanels[i+j*xSize].setBackground(Color.DARK_GRAY);
        		}
        		
        		if (chessFieldColors[i*ySize + j] == 1) {
            		chessFieldLabel.setForeground(Color.BLACK);
        		} else if (chessFieldColors[i*ySize + j] == -1) {
            		chessFieldLabel.setForeground(Color.WHITE);
        		}
        		
        		if (possibleMoveFields[i+j*xSize] == true) {
        			chessFieldLabel.setForeground(Color.BLUE);
        			chessPanels[i+j*xSize].setBackground(Color.MAGENTA);
        		}

        		
        		chessPanels[i+j*xSize].addMouseListener(chessAction);
        		chessPanels[i+j*xSize].setPreferredSize(new Dimension(500/ySize,500/xSize));
	        	constraints.gridx = i;
	        	constraints.gridy = j;
	        	chessFieldPanel.add(chessPanels[i+j*xSize], constraints);
        	}
        }
		
        frame.setVisible(true);

	}
	
	public static ChessPanel getChessPanel(Vector position) {
		int xPos = position.getValue(0);
		int yPos = position.getValue(1);
		int xSize = sizes.getValue(0);
		if(xPos + yPos*xSize < chessPanels.length) {
			return chessPanels[xPos + yPos*xSize];
		}
		return null;
	}
	
	public static int getPlayer() {
		return player;
	}
	
	public static void changePlayer() {
		player *= (-1);
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public void moveFigure (Figure figure, Vector position) {
		
	}

}

 