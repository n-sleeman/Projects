import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameSetupScreen {

    public static JPanel screen = new JPanel();
    static JLabel errorMessage;

    static String difficulty = "JUNIOR";

    final Color BACKGROUND_COLOR = new Color(161, 124, 107);

    /**
     * Initializes the screen
     */
    public GameSetupScreen() {

        screen.setBackground(BACKGROUND_COLOR);
        screen.setLayout(null);
        //addComponents();

    }

    /**
     * Adds all components to the screen
     */
    public static void addComponents() {

        /* Helper for making components */
        ComponentMaker cm = new ComponentMaker();

        /* Bounds for the buttons */
        int width = 150;
        int height = 100;
        int xPos = Startup.WINDOW_WIDTH/2;
        int yPos = Startup.WINDOW_HEIGHT/7;

        /* Difficulty chooser label */
        JLabel chooseDifficulty = cm.createJLabel("CHOOSE A DIFFICULTY");
        chooseDifficulty.setBounds(xPos-150, 50, 500, height);
        screen.add(chooseDifficulty);


        /* Players selection */
        JPanel juniorPlayerSelection = cm.createJuniorPlayerSelection();
        juniorPlayerSelection.setBounds(800, 400, 400, 300);
        screen.add(juniorPlayerSelection);

        JPanel challengerPlayerSelection = cm.createChallengerPlayerSelection();
        challengerPlayerSelection.setBounds(800, 400, 400, 300);
        screen.add(challengerPlayerSelection);
        challengerPlayerSelection.setVisible(false);

        /* Adds the difficulty buttons */
        JLabel challengerButton = cm.createClickableJLabel("CHALLENGER");
        JLabel juniorButton = cm.createClickableJLabel("JUNIOR");
        juniorButton.setBounds(xPos-400, yPos+50, width+20, height);
        Border border = BorderFactory.createEtchedBorder(Color.YELLOW, Color.YELLOW);
        juniorButton.setBorder(border);
        juniorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                difficulty = "JUNIOR";
                Border border = BorderFactory.createEtchedBorder(Color.YELLOW, Color.YELLOW);
                juniorButton.setBorder(border);
                juniorButton.setForeground(Color.YELLOW);
                border = BorderFactory.createEtchedBorder();
                challengerButton.setBorder(border);

                juniorPlayerSelection.setVisible(true);
                challengerPlayerSelection.setVisible(false);
            }
        });
        screen.add(juniorButton);

        challengerButton.setBounds(xPos+200, yPos+50, 300, height);
        challengerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                difficulty = "CHALLENGER";
                Border border = BorderFactory.createEtchedBorder(Color.YELLOW, Color.YELLOW);
                challengerButton.setBorder(border);
                challengerButton.setForeground(Color.YELLOW);
                border = BorderFactory.createEtchedBorder();
                juniorButton.setBorder(border);

                juniorPlayerSelection.setVisible(false);
                challengerPlayerSelection.setVisible(true);
            }
        });
        screen.add(challengerButton);


        /* Start button */
        JLabel startButton = cm.createClickableJLabel("PLAY");
        startButton.setBounds(xPos-225, yPos+700, 500, height);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Board.reset();

                Startup s = new Startup();
                ArrayList<String> players;
                if(difficulty.equals("JUNIOR")) {
                    players = getPlayers(juniorPlayerSelection);
                } else {
                    players = getPlayers(challengerPlayerSelection);
                }

                //check input
                if(!checkInput(players)) return;


                GameScreen.addComponents(players, difficulty);
                difficulty = "JUNIOR";
                removeComponents();
                s.changeScreen("GAME");

            }
        });
        screen.add(startButton);

        /* Error message */
        errorMessage = cm.createJLabel("Please enter a name for every player");
        errorMessage.setBounds(770, 700, 600, 100);
        screen.add(errorMessage);
        errorMessage.setVisible(false);

        /* Back button */
        JLabel backButton = cm.createHomeButton("BACK", screen);
        backButton.setBounds(100, yPos+700, 300, 100);
        screen.add(backButton);

    }

    public static void removeComponents() {
        screen.removeAll();
    }

    private static ArrayList<String> getPlayers(JPanel playerSelection) {

        ArrayList<String> players = new ArrayList<>();

        for(Component c : playerSelection.getComponents()) {
            if (c instanceof JTextField) {
                if(!((JTextField) c).getText().equals("")) {
                    players.add(((JTextField) c).getText());
                }
            }
        }

        return players;
    }

    private static boolean checkInput(ArrayList<String> players) {

        if(players.size() < 2) {
            displayErrorMessage();
            return false;
        }


        return true;
    }

    private static void displayErrorMessage() {

        errorMessage.setVisible(true);

    }


}