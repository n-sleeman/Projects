import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen {


    public static JPanel selectedTile;
    public static JPanel invalidPlacement;
    public static JPanel scoreInfo;

    public static JPanel screen = new JPanel();

    final static Color BACKGROUND_COLOR = new Color(161, 124, 107);

    /**
     * Initializes the screen
     */
    public GameScreen() {

        screen.setBackground(BACKGROUND_COLOR);
        screen.setLayout(null);
        //addComponents(); should add components when this is called, not immediately

    }

    public static void addComponents(ArrayList<String> players, String difficulty) {

        /* Helper for making components */
        ComponentMaker cm = new ComponentMaker();

        /* Adds the invalid placement message */
        invalidPlacement = cm.createInvalidPlacement();
        invalidPlacement.setBounds(775, 200, 400, 400);
        screen.add(invalidPlacement);

        /* Adds the game board */
        JPanel gameBoard = cm.createGameBoard(difficulty);
        screen.add(gameBoard);

        /* Adds the scores */
        JPanel scores = cm.createScoreBoard(players);
        scores.setBounds(200, 100, 200, 700);
        screen.add(scores);

        /* Adds the footer */
        JPanel footer = cm.createFooter(players.size(), screen);
        footer.setBounds(0, 850, 1920, 150);
        screen.add(footer);

        /* Adds some helpful info */
        JPanel helpInfo = cm.createColorIndicator();
        helpInfo.setBounds(1400, 75, 450, 300);
        screen.add(helpInfo);

        /* Adds additional score information */
        JPanel scoreInfo = cm.createPreviousPlayScore();
        scoreInfo.setBounds(1400, 450, 450, 400);
        scoreInfo.setVisible(false);
        screen.add(scoreInfo);

    }

}