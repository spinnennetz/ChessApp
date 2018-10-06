package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultFrame {
	
	public static void initResultFrame(int playerNumb) {
		ChessFrame.quitFrame();
		
		//Creating the Frame
		JFrame frame = new JFrame("ResultFrame");
		JPanel panel = new JPanel();
		String spieler = "SCHWARZ";
		if (playerNumb < 0) {
			spieler = "WEISS";
		}
		JLabel youWonLabel = new JLabel("Glückwunsch, " + spieler + "!");
		JLabel youWonLabel2 = new JLabel("Du hast gewonnen!");
		youWonLabel.setFont(new Font("Verdana",1,40));
		youWonLabel2.setFont(new Font("Verdana",1,30));
		panel.add(youWonLabel);
		panel.add(youWonLabel2);
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	

}
