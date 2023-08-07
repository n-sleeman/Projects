import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {

    public static char [][] board;
    public static boolean isReg;
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Character> bag = new ArrayList<>();
    public static int numPlayers = 0;
    //update after a turn
    public static int curPlayer = 0;
    public static int [][] curPlay;
    private static int curTile = 0;
    static boolean firstTurn = true;

    public Board(boolean type) {
        reset();
        isReg = type;
        if(isReg) {
            board = new char [15][15];
            curPlay = new int [2][15];
        }else {
            board = new char [11][11];
            curPlay = new int [2][4];
        }
        getInDaBag();
    }

    private static void reset() {
        players = new ArrayList<>();
        bag = new ArrayList<>();
        numPlayers = 0;
        curPlayer = 0;
        curTile = 0;
        firstTurn = true;
    }

    public static boolean isPlaceable(int row, int col) {

        /* Makes sure the tile is placed on the center square for the first turn */
        if(firstTurn) {
            if((row != 7 || col != 7) && isReg) {
                return false;
            } else if((row != 5 || col != 5) && !isReg) {
                return false;
            } else {
                firstTurn = false;
                return true;
            }
        }

        /* Checks that there are adjacent tiles */
        if(!isAdjacent(row, col)) {
            return false;
        }

        return true;
    }

    private static boolean isAdjacent(int row, int col) {

        if(board[row][col-1] != 0) {
            return true;
        }
        if(board[row][col+1] != 0) {
            return true;
        }
        if(board[row-1][col] != 0) {
            return true;
        }
        if(board[row+1][col] != 0) {
            return true;
        }

        return false;
    }

    public static boolean playTile(char c, int row, int col) {
        board[row][col] = c;
        curPlay[0][curTile] = row;
        curPlay[1][curTile] = col;
        curTile++;

        return true;
    }

    //validate play method using curPlay and check each tile placed.
    public static boolean checkHelper() throws FileNotFoundException {
        boolean rere = false;
        int numRes=0;
        for(int i = 0; i < curPlay[0].length; i++) {
            int curRow = curPlay[0][i];
            int curCol = curPlay[1][i];
            if(curRow == 0 && curCol == 0) break;
            numRes++;
            rere = check(curRow, curCol);
        }

        if(rere){
            for(int i = 0; i < numRes; i++) {
                int curRow = curPlay[0][i];
                int curCol = curPlay[1][i];
                players.get(curPlayer).updateHand(board[curRow][curCol]);
            }
            return rere;
        }

        for(int i = 0; i < curPlay[0].length; i++) {
            int curRow = curPlay[0][i];
            int curCol = curPlay[1][i];
            board[curRow][curCol] = 0;
        }

        return rere;
    }

    public static boolean check(int row, int col) throws FileNotFoundException {
        int curRow = row+1;
        int curCol = col+1;
        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        while(curRow < board.length && board[curRow][col] != 0) {
            str1 = str1 + board[curRow][col];
            curRow++;

        }
        curRow = row;
        while(curRow >= 0 && board[curRow][col] != 0) {
            str2 = board[curRow][col] + str2;
            curRow--;
        }
        while(curCol < board.length && board[row][curCol] != 0){
            str3 += board[row][curCol];
            curCol++;
        }
        curCol = col;
        while(curCol >= 0 && board[row][curCol] != 0){
            str4 = board[row][curCol] + str4;
            curCol--;
        }
        String vertical = str2 + str1;
        String horizontal = str4 + str3;

        if(vertical.length() == 1){
            return checkDict(horizontal);
        }else if( (horizontal.length() == 1)) {
            return checkDict(vertical);
        }else {
            return (checkDict(vertical)&&checkDict(horizontal));
        }

    }

    private static boolean checkDict(String s) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("OWL-new.txt"));
        while(scan.hasNext()){
            String curStr = scan.nextLine();
            if(s.equals(curStr)){
                return true;
            }
            if(s.length()<curStr.length()){
                break;
            }
        }
        return false;
    }

    public static int scorePlay() {

        int totalScore = 0;
        boolean scoreDownOnce = false;

        if(curPlay[1][0] ==curPlay[1][1]) scoreDownOnce = true;

        int doubleWord = 0;
        int tripleWord =0;
        for(int i = 0; i<curPlay[0].length;i++){
            int curRow = curPlay[0][i];
            int curCol = curPlay[1][i];

            if(curRow == 0 && curCol == 0) break;

            if ((doubleWord == 1 || doubleWord == 0) && isDW(curRow, curCol)) {
                doubleWord++;
            } else if ((tripleWord == 0 || tripleWord == 1) && isTW(curRow, curCol)) {
                tripleWord++;
            }

            if(curRow+1 < board.length && curRow-1 >= 0 && curCol+1 < board.length && curCol-1 >= 0) {
                if (scoreDownOnce && (board[curRow][curCol+1] != 0 || board[curRow][curCol-1] != 0)) {
                    totalScore += scoreWordAcross(curRow, curCol);
                } else if (!scoreDownOnce && (board[curRow+1][curCol] != 0 || board[curRow-1][curCol] != 0)) {
                    totalScore += scoreWordDown(curRow, curCol);
                }
            }

        }

        if(scoreDownOnce){
            totalScore += scoreWordDown(curPlay[0][0], curPlay[1][0]);
        }else{
            totalScore += scoreWordAcross(curPlay[0][0], curPlay[1][0]);
        }

        if(doubleWord > 0 && tripleWord > 0){
            totalScore *= (tripleWord*3)+(doubleWord*2);
        }else if(doubleWord > 0){
            totalScore *= doubleWord*2;
        }else if(tripleWord>0){
            totalScore *= doubleWord*2;
        }

        players.get(curPlayer).updateScore(totalScore);
        int score = players.get(curPlayer).score;

        nextTurn();

        return score;
    }

    //static Map downLetters = new HashMap();
    static ArrayList<String> downLetters = new ArrayList<>();
    private static int scoreWordDown(int row, int col){
        int curRow = row+1;
        int total = 0;

        int tester;
        while(curRow < board.length && board[curRow][col] != 0) {
            tester = 0;

            if(isDL(curRow,col)) {
                tester += scoreLetter(board[curRow][col]) * 2;
                total += tester;
            } else if(isTL(curRow,col)) {
                tester += scoreLetter(board[curRow][col])*3;
                total += tester;
            } else {
                tester += scoreLetter(board[curRow][col]);
                total += tester;
            }

            System.out.println("DOWN SCORE IS " +tester +" with char " +board[curRow][col]);
            downLetters.add(String.valueOf(tester));
            downLetters.add(String.valueOf(board[curRow][col]));
            curRow++;
        }

        curRow = row;
        while(curRow >= 0 && board[curRow][col] != 0) {
            tester = 0;

            if(isDL(curRow,col)) {
                tester += scoreLetter(board[curRow][col]) * 2;
                total += tester;
            } else if(isTL(curRow,col)) {
                tester += scoreLetter(board[curRow][col])*3;
                total += tester;
            } else {
                tester += scoreLetter(board[curRow][col]);
                total += tester;
            }

            System.out.println("DOWN SCORE IS " +tester +" with char " +board[curRow][col]);
            downLetters.add(String.valueOf(tester));
            downLetters.add(String.valueOf(board[curRow][col]));
            curRow--;
        }

        System.out.println("DOWN WORD");

        return total;
    }

    static ArrayList<String> acrossLetters = new ArrayList<>();
    private static int scoreWordAcross(int row, int col){
        int curCol = col+1;
        int total = 0;

        int tester;
        while(curCol < board.length && board[row][curCol] != 0) {
            tester = 0;

            if(isDL(row,curCol)) {
                tester += scoreLetter(board[row][curCol]) * 2;
                total += tester;
            } else if(isTL(row,curCol)) {
                tester += scoreLetter(board[row][curCol])*3;
                total += tester;
            } else {
                tester +=scoreLetter(board[row][curCol]);
                total += tester;
            }

            System.out.println("Across SCORE IS " +tester +" with char " +board[row][curCol]);
            acrossLetters.add(String.valueOf(tester));
            acrossLetters.add(String.valueOf(board[row][curCol]));
            curCol++;
        }

        curCol = col;
        while(curCol >= 0 && board[row][curCol] != 0) {
            tester = 0;

            if(isDL(row,curCol)) {
                tester += scoreLetter(board[row][curCol]) * 2;
                total += tester;
            } else if(isTL(row,curCol)) {
                tester += scoreLetter(board[row][curCol])*3;
                total += tester;
            } else {
                tester +=scoreLetter(board[row][curCol]);
                total += tester;
            }

            System.out.println("Across SCORE IS " +tester +" with char " +board[row][curCol]);
            acrossLetters.add(String.valueOf(tester));
            acrossLetters.add(String.valueOf(board[row][curCol]));
            curCol--;
        }

        System.out.println("ACROSS WORD");

        return total;
    }

    public static boolean isDW(int row, int col){
        if(isReg){
            //if((row == 1 || row ==2 || row==3 || row == 4 || row == 10 || row == 11 || row == 12 || row ==13)&& (col == 1 || col ==2 || col==3 || col == 4 || col == 10 || col == 11 || col == 12 || col ==13) ){
            if((row == 1 && col == 1) || (row == 2 && col == 2) || (row == 3 && col == 3) || (row == 4 && col == 4) ||
                    (row == 1 && col == 13) || (row == 2 && col == 12) || (row == 3 && col == 11) || (row == 4 && col == 10) ||
                    (row == 10 && col == 4) || (row == 11 && col == 3) || (row == 12 && col == 2) || (row == 13 && col == 1) ||
                    (row == 10 && col == 10) || (row == 11 && col == 11) || (row == 12 && col == 12) || (row == 13 && col == 13)) {
                return true;
            } else if( row==7 && col == 7){
                return true;
            }
            return false;
        }else{
            if((row == 1 && col == 1) || (row == 2 && col == 2) || (row == 3 && col == 3) || (row == 4 && col == 4) ||
                    (row == 1 && col == 9) || (row == 2 && col == 8) || (row == 3 && col == 7) || (row == 4 && col == 6) ||
                    (row == 6 && col == 4) || (row == 7 && col == 3) || (row == 8 && col == 2) || (row == 9 && col == 1) ||
                    (row == 6 && col == 6) || (row == 7 && col == 7) || (row == 8 && col == 8) || (row == 9 && col == 9)){
                return true;
            }
            return false;
        }
    }

    public static boolean isTW(int row, int col){
        if(isReg){
            if((row == 0 || row == 14)&&(col == 0 || col == 14)){
                return true;
            }else if((row == 7 && col == 0)||(row == 0 && col == 7) ||(row == 14 && col ==7) || (row ==7&& col == 14)){
                return true;
            }
            return false;
        }else{
            if((row == 0 || row == 10)&&(col == 0 || col == 10)){
                return true;
            }else if((row == 5 && col == 0)||(row == 0 && col == 5) ||(row == 10 && col ==5) || (row ==5&& col == 10)){
                return true;
            }
            return false;
        }
    }

    public static boolean isDL(int row, int col){
        if(isReg){
            if(row==0 && (col == 3 || col == 11)){
                return true;
            }else if(row == 2 && ( col == 6 || col == 8)){
                return true;
            }else if (row == 3 && (col == 0 || col == 7 || col == 14)){
                return true;
            }else if (row == 6 && (col == 2 || col == 6 || col == 8 || col ==12)){
                return true;
            }else if( row == 7 && ( col == 3 || col == 11)){
                return true;
            }else if(row == 8 && (col == 2 || col == 6 || col == 8 || col ==12)){
                return true;
            }else if(row == 11 && (col == 0 || col == 7 || col == 14)){
                return true;
            }else if (row == 12 && ( col == 6 || col == 8)){
                return true;
            }else if (row == 14 && (col == 3 || col == 11)) {
                return true;
            }
            return false;
        }else{
            if( (row == 8 && col == 5)|| (row == 2 && col == 5) || (row == 5 && col == 2) || (row == 5 && col == 8)){
                return true;
            }
            return false;
        }

    }

    public static boolean isTL(int row, int col){
        if(isReg){
            if(row == 5 && (col == 1 || col == 5 || col == 9 || col == 13)){
                return true;
            }else if ( row == 1 && (col == 5 || col == 9)){
                return true;
            }else if (row == 9 && (col == 1 || col == 5 || col == 9 || col ==13)){
                return true;
            }else if(row == 13&& (col == 5 || col == 9)){
                return true;
            }
            return false;
        }else{
            if(row == 1 && (col == 3 || col == 7)){
                return true;
            }else if( row == 3&& (col == 1 || col == 9)){
                return true;
            }else if(row == 7 && (col == 1 || col == 9)){
                return true;
            }else if(row == 9&& (col == 3 || col == 7)){
                return true;
            }
            return false;
        }
    }

    private static int scoreLetter(char c){
        if(c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' || c == 'L' || c == 'N' || c == 'S' || c == 'T'|| c == 'R'){
            return 1;
        }else if(c == 'F' || c == 'H' || c == 'V' || c == 'W' || c == 'Y'){
            return 4;
        }else if(c == 'B' || c == 'C' || c == 'M' || c == 'P'){
            return 3;
        }else if(c == 'K'){
            return 5;
        }else if (c == 'D'|| c == 'G'){
            return 2;
        }else if(c == 'J' || c == 'X'){
            return 8;
        }else if (c == ' '){
            return 0;
        }else {
            return 10;
        }
    }

    public static void addPlayer(String n) {
        players.add(new Player(n));
        numPlayers++;
    }

    public static void nextTurn(){
        curPlayer++;
        if(curPlayer == numPlayers) {
            curPlayer = 0;
        }
        if(isReg) {
            curPlay = new int [2][7];
        }else {
            curPlay = new int [2][4];
        }
        curTile = 0;

    }

    public static void resetTurn(boolean ft){

        firstTurn = ft;

        if(isReg) {
            curPlay = new int [2][7];
        }else {
            curPlay = new int [2][4];
        }
        curTile = 0;

    }

    private static char drawTile() {
        //if no more tiles left return null. starts filling hand with null.
        if(bag.isEmpty()) {
            return 0;
        }
        int ran = (int) (Math.random()*bag.size());
        char re = bag.remove(ran);
        return re;
    }

    public static class Player{
        public String name;
        public int score;
        public char[] hand;

        public Player(String name) {
            this.name = name;
            score=0;
            hand = new char [7];
            for(int i = 0; i<hand.length; i++) {
                hand[i] = drawTile();
            }
        }

        public void updateScore(int n) {
            score+=n;
        }

        //for when a player plays a valid word, update each letter.
        public void updateHand(char c) {
            for(int i = 0; i<hand.length; i++) {
                if(hand[i]==c) {
                    hand[i]=drawTile();
                    break;
                }
            }
        }

    }



    private void getInDaBag() {
        bag(9,'A');
        bag(2,'B');
        bag(2,'C');
        bag(4,'D');
        bag(12,'E');
        bag(2,'F');
        bag(3,'G');
        bag(2,'H');
        bag(9,'I');
        bag(1,'J');
        bag(1,'K');
        bag(4,'L');
        bag(2,'M');
        bag(6,'N');
        bag(8,'O');
        bag(2,'P');
        bag(1,'Q');
        bag(6,'R');
        bag(4,'S');
        bag(6,'T');
        bag(4,'U');
        bag(2,'V');
        bag(2,'W');
        bag(1,'X');
        bag(2,'Y');
        bag(1,'Z');
        // bag(2,' ');
    }

    private void bag(int count, char letter) {
        for(int i = 0; i<count; i++) {
            bag.add(letter);
        }
    }

}