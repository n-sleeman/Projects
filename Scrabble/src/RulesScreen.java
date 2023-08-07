import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RulesScreen {

    public static JPanel screen = new JPanel();

    final Color BACKGROUND_COLOR = new Color(161, 124, 107);

    /**
     * Initializes the screen
     */
    public RulesScreen() throws IOException {

        screen.setLayout(null);
        screen.setBackground(BACKGROUND_COLOR);

    }

    public static void addComponents() throws IOException {

        /* Helper for making components */
        ComponentMaker cm = new ComponentMaker();

        /* Rules screen label */
        JLabel rulesLabel = cm.createJLabel("RULES");
        rulesLabel.setBounds(900, 25, 200, 50);
        screen.add(rulesLabel);

        /* Adds the color information info */
        JPanel helpInfo = cm.createColorIndicator();
        helpInfo.setBounds(1000, 500, 450, 500);
        screen.add(helpInfo);

        /* Back button */
        JLabel backButton = cm.createHomeButton("BACK", screen);
        backButton.setBounds(100, 800, 300, 100);
        screen.add(backButton);

        /* Adds the bag info */
        BufferedImage myPicture = ImageIO.read(new File("Capture.png"));
        JLabel bagPic = new JLabel(new ImageIcon(myPicture));
        bagPic.setBounds(1000, 50, 900, 400);
        screen.add(bagPic);

        /* Adds the written rules */
        JLabel writtenRules = cm.createJLabel("<html>When playing Scrabble, anywhere from two to four players will enjoy the game. The object when playing is to score more points than other players. As words are placed on the game board, points are collected and each letter that is used in the game will have a different point value. The main strategy is to play words that have the highest possible score based on the combination of letters.");
        writtenRules.setBounds(100, 50, 500, 500);
        screen.add(writtenRules);

    }

}