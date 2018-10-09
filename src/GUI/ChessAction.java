package GUI;

import java.util.List;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import CheckField.*;
import Figures.Figure;
import Vector.Vector;
import KI.KI;

public class ChessAction implements MouseListener {
	
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
            
            
        	if (panelFigure != null) {
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
        	
        	if (ChessFrame.possibleMovesShowing()) {
            	toMoveFigurePossibleMoves = toMoveFigure.showPossibleMoves(toMoveFigure.getPosition(), chessField);
            	if (toMoveFigurePossibleMoves.getFieldValue(panelPosition)) {
            		chessField = chessField.moveFigure(toMoveFigure.getPosition(), panelPosition);
            		int newPlayer = ChessFrame.getPlayer()*(-1);
            		ChessFrame.init2D(chessField, newPlayer);
            		if (chessField.getCheckMate()) {
            			System.out.println("----------");
            			System.out.println("CHECKMATE!");
            			System.out.println("----------");
            			ResultFrame.initResultFrame(newPlayer*(-1));
            			return;
            		}
            		Vector[] kiMove = KI.calculateNext(chessField, newPlayer, 2);
            		Vector kiMoveStart = kiMove[0];
            		Vector kiMoveTarget = kiMove[1];
            		chessField = chessField.moveFigure(kiMoveStart, kiMoveTarget);
            		newPlayer = newPlayer * (-1);
            		System.out.println("KI moving:");
            		System.out.println("startPosition: " + kiMoveStart.getValue(0) + ", " + kiMoveStart.getValue(1));
            		System.out.println("targetPosition: " + kiMoveTarget.getValue(0) + ", " + kiMoveTarget.getValue(1));
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
