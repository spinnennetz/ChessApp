package GUI;

import java.util.List;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import CheckField.*;
import Figures.Figure;
import Vector.Vector;

public class ChessAction implements MouseListener {
     //where initialization occurs:
     //Register for mouse events on blankArea and the panel.
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
    public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		
		if(source instanceof ChessPanel){
            ChessPanel panelPressed = (ChessPanel) source;
        	CheckField chessField = panelPressed.getCheckField();
            Vector panelPosition = panelPressed.getPosition();
            Figure panelFigure = chessField.getFieldValue(panelPosition);
        	Figure toMoveFigure = ChessFrame.getToMoveFigure();
        	PossibleMovesField toMoveFigurePossibleMoves;
            System.out.println("panelFigure: " + panelFigure);
            System.out.println("toMoveFigure: " + toMoveFigure);
            
            
        	if (panelFigure != null) {
        		System.out.println("out threats: " + panelFigure.getOutThreats(panelPosition, chessField));
                System.out.println(panelFigure.getPosition().getValue(0));
                System.out.println(panelFigure.getPosition().getValue(1));
                if (panelFigure.getColor() == ChessFrame.getPlayer()) {
            		ChessFrame.resetPossibleMoveFields();
                	PossibleMovesField possibleMoves = panelFigure.showPossibleMoves(panelFigure.getPosition(), chessField);
                    List<Vector> possibleMovesList = possibleMoves.getTrueOverlayPositions(possibleMoves);
                    for (Vector possibleMove : possibleMovesList) {
                    	ChessFrame.showPossibleMove(possibleMove);
                    }
                	ChessFrame.setAsToMoveFigure(panelFigure);
                    ChessFrame.showCheckField();
                    return;
                } 
            }
        	
        	System.out.println("possibleMovesShowing: " + ChessFrame.possibleMovesShowing());
        	if (ChessFrame.possibleMovesShowing()) {
            	toMoveFigurePossibleMoves = toMoveFigure.showPossibleMoves(toMoveFigure.getPosition(), chessField);
            	if (toMoveFigurePossibleMoves.getFieldValue(panelPosition)) {
            		System.out.println("movable");
            		chessField = chessField.moveFigure(toMoveFigure.getPosition(), panelPosition);
            		int newPlayer = ChessFrame.getPlayer()*(-1);
            		ChessFrame.init2D(chessField, newPlayer);
            		return;
            	} else {
                	ChessFrame.setAsToMoveFigure(null);
                	ChessFrame.resetPossibleMoveFields();
                	ChessFrame.showCheckField();
                	return;
                }
            } 
        }
    }


}
