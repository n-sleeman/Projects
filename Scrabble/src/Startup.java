import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Startup {

    static CardLayout cards;
    static JPanel rootPanel;

    /* Window size */
    final static int WINDOW_WIDTH = 1920;
    final static int WINDOW_HEIGHT = 1080;

    /* Screens */
    final static String MAIN_MENU_SCREEN_NAME = "MAIN";
    final static String GAME_SETUP_SCREEN_NAME = "GAMESETUP";
    final static String GAME_SCREEN_NAME = "GAME";
    final static String RULES_SCREEN_NAME = "RULES";

    public static void main(String[] args) throws IOException {

        /* Sets up the window */
        JFrame homeFrame = new JFrame( "Scrabble" );
        homeFrame.setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
        homeFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        homeFrame.setVisible( true );
        homeFrame.setExtendedState(homeFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        /* Sets the card layout to our root panel */
        /* Card layout is needed to switch between panels/screens */
        CardLayout cL = new CardLayout();
        rootPanel = new JPanel();
        rootPanel.setLayout(cL);

        /* Adds all of our screens to the card layout */
        initializeScreens(homeFrame);

        /* Sets the initial screen */
        cards = (CardLayout) rootPanel.getLayout();
        cards.show(rootPanel, MAIN_MENU_SCREEN_NAME);

        /* Refreshes the window with all valid screens */
        homeFrame.validate();

    }

    /**
     * Adds all screens to the window
     *
     * @param homeFrame - Window
     */
    private static void initializeScreens(JFrame homeFrame) throws IOException {

        /* Initializes screens */
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        GameSetupScreen gameSetupScreen = new GameSetupScreen();
        GameScreen gameScreen = new GameScreen();
        RulesScreen rulesScreen = new RulesScreen();

        /* Adds every screen to the root panel */
        rootPanel.add(mainMenuScreen.screen, MAIN_MENU_SCREEN_NAME);
        rootPanel.add(gameSetupScreen.screen, GAME_SETUP_SCREEN_NAME);
        rootPanel.add(gameScreen.screen, GAME_SCREEN_NAME);
        rootPanel.add(rulesScreen.screen, RULES_SCREEN_NAME);

        /* Adds the root panel to the window */
        homeFrame.add(rootPanel, null);

    }

    /**
     * Changes the screen to the input screen
     *
     * @param screenName - Name of the screen being changed to
     */
    public void changeScreen( String screenName ) {
        cards.show(rootPanel, screenName);
    }


}