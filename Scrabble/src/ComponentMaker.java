import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Helps create modified JSwing components
 */
public class ComponentMaker {

    final Color BACKGROUND_COLOR = new Color(161, 124, 107);
    final Color BUTTON_COLOR = new Color(115, 175, 111);
    final Color RED_BUTTON_COLOR = new Color(200, 13, 55);
    final Color TILE_COLOR = new Color(155, 103, 90);
    final Color TRIPLE_WORD_SCORE_COLOR = RED_BUTTON_COLOR;
    final Color TRIPLE_LETTER_SCORE_COLOR = new Color(0, 124, 190);
    final Color DOUBLE_WORD_SCORE_COLOR = new Color(250, 152, 160);
    final Color DOUBLE_LETTER_SCORE_COLOR = new Color(166, 217, 247);

    final Font headerFont = new Font("Serif", Font.PLAIN, 30);

    final int JUNIOR_BOARD_SIZE = 11;
    final int CHALLENGER_BOARD_SIZE = 15;

    final int MAX_PLAYERS = 4;

    static boolean firstTurn = true;
    int currentPlayer = 0;
    int boardType = 0;

    public JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setBorderPainted(false);
        button.setFont(headerFont);
        button.setBackground(BUTTON_COLOR);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(MouseEvent me) {
                button.setBackground(button.getBackground().darker());
            }


        });

        return button;
    }

    /**
     * Creates a JLabel with a custom font/color
     *
     * @param text - Text displayed on the JLabel
     * @return - Newly created JLabel
     */
    public JLabel createJLabel(String text) {

        JLabel jLabel = new JLabel(text);

        jLabel.setLayout(null);
        jLabel.setFont(headerFont);
        jLabel.setForeground(Color.WHITE);

        return jLabel;
    }

    /**
     * Creates a scrabble game board
     *
     * @return - JPanel containing the game board
     */
    public JPanel createGameBoard(String difficulty) {

        /* Initializes the board */
        JPanel gameBoard = new JPanel();
        gameBoard.setBackground(Color.WHITE);
        gameBoard.setLayout(null);

        /* Initializes variables */
        firstTurn = true;

        /* Sets the size of the board depending on the difficulty */
        if(difficulty.equals("JUNIOR")) {
            boardType = 0;
            Board b = new Board(false);
            createJuniorBoard(gameBoard);
            gameBoard.setLocation(700, 125);
        } else {
            boardType = 1;
            Board b = new Board(true);
            createChallengerBoard(gameBoard);
            gameBoard.setLocation(600, 75);
        }


        return gameBoard;
    }

    private void createJuniorBoard(JPanel board) {

        board.setSize(549, 549);

        /* Adds all tiles to our board */
        for(int i = 0; i < JUNIOR_BOARD_SIZE; i++) {
            for(int j = 0; j < JUNIOR_BOARD_SIZE; j++) {
                JPanel tile;

                if(i == 5 && j == 5) {
                    tile = createGameTile("start", j, i);
                } else if(Board.isTW(j,i)) {
                    tile = createGameTile("tripleword", j, i);
                } else if (Board.isDW(j, i)) {
                    tile = createGameTile("doubleword", j, i);
                } else if(Board.isTL(j, i)) {
                    tile = createGameTile("tripleletter", j, i);
                } else if(Board.isDL(j, i)) {
                    tile = createGameTile("doubleletter", j, i);
                } else {
                    tile = createGameTile("", j, i);
                }

                tile.setBounds(i*50, j*50, 49, 49);
                board.add(tile);
            }
        }

    }

    private void createChallengerBoard(JPanel board) {

        board.setSize(749, 749);

        /* Adds all tiles to our board */
        for(int i = 0; i < CHALLENGER_BOARD_SIZE; i++) {
            for(int j = 0; j < CHALLENGER_BOARD_SIZE; j++) {

                JPanel tile;

                if(i == 7 && j == 7) {
                    tile = createGameTile("start", j, i);
                } else if((j == 0 || j == 7 || j == 14) && (i == 0 || i == 7 || i == 14)) {
                    tile = createGameTile("tripleword", j, i);
                } else if (Board.isDW(j, i)) {
                    tile = createGameTile("doubleword", j, i);
                } else if(Board.isTL(j, i)) {
                    tile = createGameTile("tripleletter", j, i);
                } else if(Board.isDL(j, i)) {
                    tile = createGameTile("doubleletter", j, i);
                } else {
                    tile = createGameTile("", j, i);
                }

                tile.setBounds(i*50, j*50, 49, 49);
                board.add(tile);
            }
        }

    }

    String currentLetter = "";

    public class Tile {

        JPanel tile;
        int row;
        int col;
        Color color;
        String text;

        public Tile(int row, int col) {
            tile = new JPanel();
            this.row = row;
            this.col = col;
        }
    }

    // VALIDATION DONE HERE
    ArrayList<JPanel> playedTiles = new ArrayList<>();
    ArrayList<Tile> boardPlayedTiles = new ArrayList<>();
    private JPanel createGameTile(String type, int row, int col) {

        //JPanel tile = new JPanel();
        Tile tile = new Tile(row, col);

        /* Text displayed on the tile */
        JLabel text = createJLabel("");

        if (type.equals("start")) {
            tile.tile.setBackground(DOUBLE_WORD_SCORE_COLOR);
            tile.color = DOUBLE_WORD_SCORE_COLOR;
            text.setText("*");
        } else if (type.equals("tripleword")) {
            tile.tile.setBackground(TRIPLE_WORD_SCORE_COLOR);
            tile.color = TRIPLE_WORD_SCORE_COLOR;
        } else if(type.equals("doubleword")) {
            tile.tile.setBackground(DOUBLE_WORD_SCORE_COLOR);
            tile.color = DOUBLE_WORD_SCORE_COLOR;
        } else if(type.equals("tripleletter")) {
            tile.tile.setBackground(TRIPLE_LETTER_SCORE_COLOR);
            tile.color = TRIPLE_LETTER_SCORE_COLOR;
        } else if(type.equals("doubleletter")) {
            tile.tile.setBackground(DOUBLE_LETTER_SCORE_COLOR);
            tile.color = DOUBLE_LETTER_SCORE_COLOR;
        } else {
            tile.tile.setBackground(TILE_COLOR);
            tile.color = TILE_COLOR;
        }

        tile.tile.add(text);
        tile.text = "";

        tile.tile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!currentLetter.equals("") && curTile != null && (tile.text.equals("") || tile.text.equals("*"))) {

                    if(boardType == 0 && playedTiles.size() >= 4) {
                        System.out.println("TOO MANY TILES");
                        return;
                    }

                    //VALIDATE HERE
                    if(!Board.isPlaceable(tile.row, tile.col)) {
                        System.out.println(firstTurn);
                        return;
                    }

                    Board.playTile(currentLetter.charAt(0), tile.row, tile.col);

                    //remove tile from hand
                    playedTiles.add(curTile); //tracks all played tiles in-case of reset
                    boardPlayedTiles.add(tile); //tracks for reset on board
                    curTile.setVisible(false);
                    curTile = null;

                    tile.tile.setBackground(TILE_COLOR.darker());
                    text.setText(currentLetter);
                    tile.text = currentLetter;
                }
            }
        });

        return tile.tile;
    }

    public JPanel createColorIndicator() {

        JPanel colorIndicator = new JPanel();
        colorIndicator.setBackground(BACKGROUND_COLOR);
        colorIndicator.setLayout(null);

        JPanel startIndicator = createStartColorIndicator();
        startIndicator.setBounds(10, 10, 430, 50);
        colorIndicator.add(startIndicator);

        JPanel tripleWordScore = createColorIndicator(TRIPLE_WORD_SCORE_COLOR, "TRIPLE WORD SCORE");
        tripleWordScore.setBounds(10, 70, 430, 50);
        colorIndicator.add(tripleWordScore);

        JPanel doubleWordScore = createColorIndicator(DOUBLE_WORD_SCORE_COLOR, "DOUBLE WORD SCORE");
        doubleWordScore.setBounds(10, 130, 430, 50);
        colorIndicator.add(doubleWordScore);

        JPanel tripleLetterScore = createColorIndicator(TRIPLE_LETTER_SCORE_COLOR, "TRIPLE LETTER SCORE");
        tripleLetterScore.setBounds(10, 190, 430, 50);
        colorIndicator.add(tripleLetterScore);

        JPanel doubleLetterScore = createColorIndicator(DOUBLE_LETTER_SCORE_COLOR, "DOUBLE LETTER SCORE");
        doubleLetterScore.setBounds(10, 250, 430, 50);
        colorIndicator.add(doubleLetterScore);

        return colorIndicator;
    }

    private JPanel createStartColorIndicator() {

        JPanel startIndicator = new JPanel();
        startIndicator.setBackground(BACKGROUND_COLOR);
        startIndicator.setLayout(null);

        /* Creates the tile */
        JPanel startColor = new JPanel();
        startColor.setBounds(0, 0 ,50, 50);
        startColor.setBackground(DOUBLE_WORD_SCORE_COLOR);
        JLabel text = createJLabel("*");
        startColor.add(text);

        /* Creates the explaining text */
        JLabel explainerText = createJLabel("START SQUARE");
        explainerText.setBounds(70, 0, 300, 50);

        startIndicator.add(startColor);
        startIndicator.add(explainerText);

        return startIndicator;
    }

    private JPanel createColorIndicator(Color tileColor, String label) {

        JPanel startIndicator = new JPanel();
        startIndicator.setBackground(BACKGROUND_COLOR);
        startIndicator.setLayout(null);

        /* Creates the tile */
        JPanel startColor = new JPanel();
        startColor.setBounds(0, 0 ,50, 50);
        startColor.setBackground(tileColor);

        /* Creates the explaining text */
        JLabel explainerText = createJLabel(label);
        explainerText.setBounds(70, 0, 400, 50);

        startIndicator.add(startColor);
        startIndicator.add(explainerText);

        return startIndicator;
    }


    JPanel curTile;
    public JPanel createPlayerTile(char letter) {

        JPanel tile = new JPanel();
        tile.setBackground(TILE_COLOR);

        /* Text displayed on the tile */
        //String curText = Board.drawTile();
        JLabel text = createJLabel(String.valueOf(letter));

        tile.add(text);

        tile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetDeckHighlights();
                tile.setBorder(new LineBorder(Color.YELLOW, 4, true));
                currentLetter = ((JLabel) tile.getComponent(0)).getText();
                curTile = tile;
            }
        });

        return tile;
    }

    private JPanel createScoreCard() {

        JPanel score = new JPanel();
        score.setBackground(BACKGROUND_COLOR.darker());
        score.setSize(200, 150);
        return score;

    }

    public JLabel createClickableJLabel(String text) {

        JLabel jLabel = new JLabel(text, SwingConstants.CENTER);

        jLabel.setLayout(null);
        jLabel.setFont(headerFont);
        jLabel.setForeground(Color.WHITE);

        Border border = BorderFactory.createEtchedBorder();
        jLabel.setBorder(border);
        jLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                jLabel.setForeground(Color.YELLOW);
            }
            public void mouseExited(MouseEvent me) {
                jLabel.setForeground(Color.WHITE);
            }

            public void mouseClicked(MouseEvent e) {
                Border border = BorderFactory.createEtchedBorder();
                jLabel.setBorder(border);
            }
        });

        return jLabel;
    }

    ArrayList<JPanel> scoreCard = new ArrayList<>();
    public JPanel createScoreBoard(ArrayList<String> players) {

        JPanel jPanel = new JPanel();
        jPanel.setBackground(BACKGROUND_COLOR);
        jPanel.setLayout(null);

        int numPlayers = players.size();

        for(int i = 0; i < numPlayers; i++) {
            //JPanel scoreCard = createScoreCard();
            scoreCard.add(createScoreCard());
            scoreCard.get(i).setLocation(0, i*175);
            JLabel player = createJLabel(players.get(i));
            scoreCard.get(i).add(player);
            JLabel score = createJLabel("SCORE: 0");
            scoreCard.get(i).add(score);

            jPanel.add(scoreCard.get(i));
            Board.addPlayer(players.get(i)); //adds player to the backend, maybe move to gamesetup
        }
        highlightCurrentPlayer(currentPlayer);

        return jPanel;
    }

    public JPanel createJuniorPlayerSelection() {

        JPanel playerSelection = new JPanel();
        playerSelection.setLayout(null);
        playerSelection.setBackground(BACKGROUND_COLOR);

        /* Player 1 info */
        JLabel playerOne = createJLabel("Player 1");
        playerOne.setBounds(0, 10, 100, 50);
        playerSelection.add(playerOne);
        JTextField playerOneInput = new JTextField();
        playerOneInput.setBounds(0 + 120, 10, 150, 50);
        playerOneInput.setFont(headerFont);
        playerSelection.add(playerOneInput);

        /* PLayer 2 info */
        JLabel playerTwo = createJLabel("Player 2");
        playerTwo.setBounds(0, 10+50, 100, 50);
        playerSelection.add(playerTwo);
        JTextField playerTwoInput = new JTextField();
        playerTwoInput.setBounds(0 + 120, 10+50, 150, 50);
        playerTwoInput.setFont(headerFont);
        playerSelection.add(playerTwoInput);

        return playerSelection;
    }

    /**
     * Creates the the player selection component
     *
     * @return - JPanel containing the player selection components
     */
    public JPanel createChallengerPlayerSelection() {

        JPanel playerSelection = new JPanel();
        playerSelection.setLayout(null);
        playerSelection.setBackground(BACKGROUND_COLOR);

        JLabel player[] = new JLabel[4];
        JTextField playerInput[] = new JTextField[4];
        JButton addPlayer[] = new JButton[4];
        JButton removePlayer[] = new JButton[4];
        for(int i = 0; i < MAX_PLAYERS; i++) {

            player[i] = createJLabel("Player " +(i+1));
            player[i].setBounds(0, 10+(i*50), 100, 50);
            playerSelection.add(player[i]);

            playerInput[i] = new JTextField();
            playerInput[i].setBounds(0 + 120, 10+(i*50), 150, 50);
            playerInput[i].setFont(headerFont);
            playerSelection.add(playerInput[i]);

            addPlayer[i] = createButton("+");
            addPlayer[i].setBounds(0+280, 10+(i*50), 52, 50);
            playerSelection.add(addPlayer[i]);

            removePlayer[i] = createButton("-");
            removePlayer[i].setBackground(RED_BUTTON_COLOR);
            removePlayer[i].setBounds(0+330, 10+(i*50), 50, 50);
            playerSelection.add(removePlayer[i]);

            if(i >= 2) {
                player[i].setVisible(false);
                playerInput[i].setVisible(false);
                addPlayer[i].setVisible(false);
                removePlayer[i].setVisible(false);
            }
        }




        //cleanup? removes + & - in wrong spots
        addPlayer[0].setVisible(false);
        removePlayer[0].setVisible(false);
        removePlayer[1].setVisible(false);

        /* Adds functionality to + & - buttons */
        addPlayer[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPlayer[1].setVisible(false);
                player[2].setVisible(true);
                playerInput[2].setVisible(true);
                addPlayer[2].setVisible(true);
                removePlayer[2].setVisible(true);
            }
        });
        addPlayer[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPlayer[2].setVisible(false);
                removePlayer[2].setVisible(false);
                player[3].setVisible(true);
                playerInput[3].setVisible(true);
                removePlayer[3].setVisible(true);
            }
        });
        removePlayer[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPlayer[2].setVisible(false);
                player[2].setVisible(false);
                playerInput[2].setVisible(false);
                addPlayer[2].setVisible(false);
                removePlayer[2].setVisible(false);
                addPlayer[1].setVisible(true);
                playerInput[2].setText("");
            }
        });
        removePlayer[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addPlayer[3].setVisible(false);
                player[3].setVisible(false);
                playerInput[3].setVisible(false);
                addPlayer[3].setVisible(false);
                removePlayer[3].setVisible(false);
                addPlayer[2].setVisible(true);
                removePlayer[2].setVisible(true);
                playerInput[3].setText("");
            }
        });

        return playerSelection;
    }

    private JPanel createCurrentDeck(int player) {

        JPanel currentDeck = new JPanel();
        currentDeck.setLayout(null);
        currentDeck.setBackground(Color.BLACK);
        currentDeck.setBounds(675, 25, 600, 100);

        char[] letters = Board.players.get(player).hand;
        for(int i = 0; i < 7; i++) {
            JPanel tile = createPlayerTile(letters[i]);
            tile.setBounds((i*80)+35, 25, 50, 50);
            currentDeck.add(tile);
        }

        return currentDeck;
    }

    ArrayList<JPanel> currentDeck = new ArrayList<>();
    public JPanel createFooter(int numPlayers, JPanel screen) {

        JPanel footer = new JPanel();
        footer.setBackground(BACKGROUND_COLOR);
        footer.setLayout(null);

        /* Current tiles in hand */
        //JPanel[] currentDeck = new JPanel[numPlayers];
        for(int i = 0; i < numPlayers; i++) {
            currentDeck.add(createCurrentDeck(i));
            footer.add(currentDeck.get(i));
            currentDeck.get(i).setVisible(false);
        }
        currentDeck.get(0).setVisible(true); //show first deck

        /* End turn */
        JLabel endTurn = createClickableJLabel("END TURN");
        endTurn.setBounds(1300, 25, 200, 50);
        endTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //check
                try {
                    System.out.println( Board.checkHelper() );
                    if(!Board.checkHelper()) {
                        displayInvalidPlacement();
                        resetDeck();
                        Board.resetTurn(firstTurn);
                        return;
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                firstTurn = false;

                //draw new tiles, is done in the addscore method

                /* Update score */
                int score = Board.scorePlay();
                System.out.println(score);
                setScore(String.valueOf(score));

                if(boardType == 0) {
                    //JPanel newPrevPlay = createPreviousPlayScore(Board.acrossLetters, Board.downLetters);
                    //GameScreen.screen.add(newPrevPlay);
                    //GameScreen.scoreInfo.setVisible(false);
                    updatePreviousPlayScore(Board.acrossLetters, Board.downLetters);
                    Board.downLetters.clear();
                    Board.acrossLetters.clear();
                }

                nextTurn(numPlayers);
                playedTiles.clear();
                boardPlayedTiles.clear();
            }
        });
        footer.add(endTurn);

        /* Pass turn */
        JLabel passTurn = createClickableJLabel("PASS TURN");
        passTurn.setBounds(1300, 75, 200, 50);
        passTurn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Reset hand if they placed tiles */
                resetDeck();

                nextTurn(numPlayers);
                Board.nextTurn();
            }
        });
        footer.add(passTurn);

        /* Quit */
        JLabel quitGame = createHomeButton("QUIT GAME", screen);
        quitGame.setBounds(100, 25, 300, 100);
        footer.add(quitGame);

        return footer;
    }

    JLabel[] previousValue = new JLabel[8];
    JLabel[] previousLetter = new JLabel[8];
    JLabel[] playRow = new JLabel[8];
    JPanel scores;
    public JPanel createPreviousPlayScore() {

        scores = new JPanel();
        //JPanel scores = GameScreen.scoreInfo;
        //GameScreen.screen.remove(GameScreen.scoreInfo);
        //scores.setBounds(500, 800, 200, 200);
        //GameScreen.scoreInfo = null;
        scores.setLayout(null);
        scores.removeAll();
        scores.setBackground(BACKGROUND_COLOR.darker());
        scores.setVisible(true);


        for(int i = 0 ; i < previousValue.length; i++) {
            playRow[i] = new JLabel();
            playRow[i].setBounds(20, 20+(40*i), 500, 90);

            previousValue[i] = createJLabel("ZZ");
            previousValue[i].setBounds(90, 50, 30, 30);
            //scores.add(previousValue[i]);

            previousLetter[i] = createJLabel("ZZ");
            previousLetter[i].setBounds(20, 50, 30, 30);
            //scores.add(previousLetter[i]);

            JLabel pointsLabel = createJLabel("point(s)");
            pointsLabel.setBounds(120, 50, 200, 30);
            //scores.add(pointsLabel);

            playRow[i].add(previousValue[i]);
            playRow[i].add(previousLetter[i]);
            playRow[i].add(pointsLabel);
            playRow[i].setVisible(false);

            scores.add(playRow[i]);
        }


        /* Last scores */
        JLabel lastPlay = createJLabel("LAST PLAY");
        lastPlay.setBounds(150, 10, 200, 30);
        scores.add(lastPlay);

        return scores;
    }

    private void updatePreviousPlayScore(ArrayList<String> acrossLetters, ArrayList<String> downLetters) {

        System.out.println(acrossLetters);
        System.out.println(downLetters);

        scores.setVisible(true);

        /* Turns offs all options first */
        for(int i = 0 ; i < playRow.length; i++) {
            playRow[i].setVisible(false);
        }

        for(int i = 0 ; i < acrossLetters.size(); i+=2) {
            if(i == 0) {
                previousValue[i].setText(acrossLetters.get(i));
                previousLetter[i].setText(acrossLetters.get(i+1));
            } else {
                previousValue[i/2].setText(acrossLetters.get(i));
                previousLetter[i/2].setText(acrossLetters.get(i+1));
            }
            playRow[i/2].setVisible(true);
        }

        for(int i = 0 ; i < downLetters.size(); i+=2) {
            if(i == 0) {
                previousValue[i].setText(downLetters.get(i));
                previousLetter[i].setText(downLetters.get(i+1));
            } else {
                previousValue[i/2].setText(downLetters.get(i));
                previousLetter[i/2].setText(downLetters.get(i+1));
            }
            playRow[i/2].setVisible(true);
        }
    }

    public JLabel createHomeButton(String text, JPanel screen) {

        JLabel homeButton = createClickableJLabel(text);
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                screen.removeAll();

                Startup s = new Startup();
                s.changeScreen("MAIN");
            }
        });

        return homeButton;
    }

    private void resetDeckHighlights() {
        if(curTile == null) return;
        curTile.setBorder(null);
    }

    private void resetDeck() {
        for(int i = 0; i < playedTiles.size(); i++) {
            playedTiles.get(i).setVisible(true); //resets deck
            playedTiles.get(i).setBorder(null);

            Tile tile = boardPlayedTiles.get(i);
            if((tile.row == 7 && tile.col == 7 && boardType == 1) || (tile.row == 5 && tile.col == 5 && boardType == 0)) {
                tile.text = "*";
            } else {
                tile.text = "";
            }
            tile.tile.setBackground(tile.color); //resets board
            ((JLabel) tile.tile.getComponent(0)).setText(tile.text);
        }
        playedTiles.clear();
        boardPlayedTiles.clear();
    }

    public JPanel createInvalidPlacement() {

        JPanel invalidPlacement = new JPanel();
        invalidPlacement.setBackground(Color.RED);
        invalidPlacement.setLayout(null);
        invalidPlacement.setVisible(false);

        JLabel notAWord = createJLabel("NOT A WORD");
        notAWord.setBounds(115, 50, 300, 100);
        invalidPlacement.add(notAWord);

        JLabel okButton = createClickableJLabel("OK");
        okButton.setBounds(145, 200, 100, 100);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                invalidPlacement.setVisible(false);
            }
        });
        invalidPlacement.add(okButton);

        return invalidPlacement;
    }

    private void displayInvalidPlacement() {
        GameScreen.invalidPlacement.setVisible(true);
    }

    private void highlightCurrentPlayer(int i) {
        scoreCard.get(i).setBorder(new LineBorder(Color.YELLOW, 4, true));
    }

    private void setScore(String score) {
        ((JLabel)scoreCard.get(currentPlayer).getComponent(1)).setText("SCORE: " +score);
    }

    private void nextTurn(int numPlayers) {

        JPanel curDeck = currentDeck.get(currentPlayer);

        /* Grabs new tiles */
        char[] letters = Board.players.get(currentPlayer).hand;
        //System.out.println(letters);
        for(int i = 0; i < curDeck.getComponentCount(); i++) {
            //JLabel curTile = ((JLabel) ((JPanel) curDeck.getComponent(i)).getComponent(0));
            //System.out.println(curTile.getText());
            for(JPanel playedTile : playedTiles ) {
                JPanel curTile = ((JPanel) curDeck.getComponent(i));
                /* Draw new cards */
                //        if(playedTile == curTile) {

                char newLetter = Board.players.get(currentPlayer).hand[i];
                String nLetter = String.valueOf(newLetter);

                String letter = ((JLabel)playedTile.getComponent(0)).getText();
                ((JLabel) ((JPanel) curDeck.getComponent(i)).getComponent(0)).setText(nLetter);
                curTile.setVisible(true);
                curTile.setBorder(null);

                //    }
            }
        }



        /* Updates the deck that's showing */
        scoreCard.get(currentPlayer).setBorder(null);
        curDeck.setVisible(false);
        currentPlayer++;
        if(currentPlayer >= numPlayers) currentPlayer = 0;
        currentDeck.get(currentPlayer).setVisible(true);

        /* Indicates whose turn it is on the scoreboard */
        highlightCurrentPlayer(currentPlayer);


    }

}