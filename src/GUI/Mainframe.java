package GUI;
import CheckField.*;
import FileHandler.LoadPosition;

public class Mainframe {

	public static void main(String[] args) {
		CheckField startPosition = LoadPosition.loadPosition(8, 8, "/Users/johannatengler/eclipse-workspace/ChessApp/src/Main/startPosition");
        CheckField invertedStartPosition = startPosition.invertField();
        CheckField initField = startPosition.addCheckField(invertedStartPosition);
		ChessFrame.initChessFrame(initField, -1);
	}

}
