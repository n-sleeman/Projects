import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuScreen {

    public JPanel screen = new JPanel();

    final Color BACKGROUND_COLOR = new Color(161, 124, 107);

    /**
     * Initializes the screen
     */
    public MainMenuScreen() throws IOException {

        screen.setLayout(null);
        screen.setBackground(BACKGROUND_COLOR);
        addComponents();

    }

    /**
     * Adds all components to the screen
     */
    private void addComponents() throws IOException {

        /* Helper for making components */
        ComponentMaker cm = new ComponentMaker();

        /* Bounds for the buttons */
        int width = Startup.WINDOW_WIDTH/15;
        int height = Startup.WINDOW_WIDTH/15;
        int xPos = Startup.WINDOW_WIDTH/2;
        int yPos = (Startup.WINDOW_HEIGHT/10)+90;

        /* Adds the scrabble logo */
        JLabel scrabbleLogo = cm.createJLabel("SCRABBLE");
        scrabbleLogo.setFont(new Font("Serif", Font.ITALIC, 100));
        scrabbleLogo.setBounds(xPos-200, 0, 600, 200);
        screen.add(scrabbleLogo);

        /* Adds the start button */
        JLabel startButton = cm.createClickableJLabel("START");
        startButton.setBounds(xPos-100, yPos, width*2, height);
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Startup s = new Startup();
                GameSetupScreen.addComponents();
                s.changeScreen("GAMESETUP");
            }
        });
        screen.add(startButton);

        /* Adds the rule button */
        JLabel rulesButton = cm.createClickableJLabel("RULES");
        rulesButton.setBounds(xPos-100, yPos + 200, width*2, height);
        rulesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Startup s = new Startup();
                try {
                    RulesScreen.addComponents();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                s.changeScreen("RULES");
            }
        });
        screen.add(rulesButton);

        /* Adds the quit button */
        JLabel quitButton = cm.createClickableJLabel("QUIT");
        quitButton.setBounds(xPos-100, yPos + 400, width*2, height);
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        screen.add(quitButton);

    }

}