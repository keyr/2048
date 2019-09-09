import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {


    public boolean playing = false; // whether the game is running
    public boolean won = false;
    private JLabel status; // Current status text, i.e. "Running..."
    private int[][] board;
    private LinkedList<int[][]> moves;
    private LinkedList<Integer> scores;
    private int score = 0;
    private Random rand = new Random();
    private static final int SIDE = 4;

    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 600;

    private static final Color BOARD_COLOR = Color.black;
    private static final Color COLOR_0 = Color.white;
    private static final Color COLOR_2 = Color.getHSBColor(0,130,127);
    private static final Color COLOR_4 = Color.getHSBColor(226,88,34);
    private static final Color COLOR_8 = Color.getHSBColor(159,0,197);
    private static final Color COLOR_16 = Color.getHSBColor(233, 150, 122);
    private static final Color COLOR_32 = Color.getHSBColor(252, 247, 94);
    private static final Color COLOR_64 = Color.getHSBColor(137, 207, 240);
    private static final Color COLOR_128 = Color.getHSBColor(250, 146, 88);
    private static final Color COLOR_256 = Color.getHSBColor(50, 205, 50);
    private static final Color COLOR_512 = Color.getHSBColor(37, 53 ,41);
    private static final Color COLOR_1024 = Color.getHSBColor(113, 112, 110);
    private static final Color COLOR_2048 = Color.getHSBColor(254, 65, 100);




    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
            addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        if (!won) {
                            if (left()) {
                                newTile();
                                moves.add(copyOf(board));
                                scores.add(score);
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        if (!won) {
                            if (right()) {
                                newTile();
                                moves.add(copyOf(board));
                                scores.add(score);
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        if (!won) {
                            if (down()) {
                                newTile();
                                moves.add(copyOf(board));
                                scores.add(score);
                            }
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        if (!won) {
                            if (up()) {
                                newTile();
                                moves.add(copyOf(board));
                                scores.add(score);
                            }
                        }
                    }
                    repaint();
                }
            });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        playing = true;
        won = false;
        startGame();

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public void undo(){
        if (moves.size() <= 1){
            status.setText("YOU CAN'T UNDO SOMETHING YOU HAVEN'T STARTED");
        }
        else {
            moves.removeLast();
            board = copyOf(moves.getLast());
            scores.removeLast();
            score = scores.getLast();
            repaint();
        }

        requestFocusInWindow();
    }

    public void startGame(){
        board = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                board[i][j] = 0;
            }
        }
        newTile();
        newTile();
        moves = new LinkedList<>();
        moves.add(copyOf(board));
        score = 0;
        scores = new LinkedList<>();
        scores.add(score);
        repaint();
    }

    public void newTile() {
        int i = rand.nextInt(4);
        int j = rand.nextInt(4);
        while (board[i][j] != 0){
            i = rand.nextInt(4);
            j = rand.nextInt(4);
        }
        int k = rand.nextInt(10);
        if (k == 0){
            board[i][j] = 4;
        }
        else{
            board[i][j] = 2;
        }

    }

    public void drawBoard(Graphics g){
        if (playing){
            for (int i = 0; i < SIDE; ++i){
                for (int j = 0; j < SIDE; ++j){
                    switch (board[i][j]){
                        case 0:
                            g.setColor(COLOR_0);
                            break;
                        case 2:
                            g.setColor(COLOR_2);
                            break;
                        case 4:
                            g.setColor(COLOR_4);
                            break;
                        case 8:
                            g.setColor(COLOR_8);
                            break;
                        case 16:
                            g.setColor(COLOR_16);
                            break;
                        case 32:
                            g.setColor(COLOR_32);
                            break;
                        case 64:
                            g.setColor(COLOR_64);
                            break;
                        case 128:
                            g.setColor(COLOR_128);
                            break;
                        case 256:
                            g.setColor(COLOR_256);
                            break;
                        case 512:
                            g.setColor(COLOR_512);
                            break;
                        case 1024:
                            g.setColor(COLOR_1024);
                            break;
                        case 2048:
                            g.setColor(COLOR_2048);
                            break;

                    }
                    g.fillRoundRect(50 + 100 * i, 350 - 100 * j, 100, 100, 5, 5);
                    g.setColor(BOARD_COLOR);
                    g.setFont(new Font("Tahoma", Font.ITALIC, 20));
                    g.drawRect(50 + 100 * i, 350 - 100 * j, 100, 100);
                    if (board[i][j] == 2 || board[i][j] == 4 || board[i][j] == 8){
                        g.drawString(String.valueOf(board[i][j]), 95 + 100 * i, 400 - 100 * j);
                    }
                    else if (board[i][j] == 16 || board[i][j] == 32 || board[i][j] == 64){
                        g.drawString(String.valueOf(board[i][j]), 92 + 100 * i, 400 - 100 * j);
                    }
                    else if (board[i][j] == 128 || board[i][j] == 256 || board[i][j] == 512){
                        g.drawString(String.valueOf(board[i][j]), 88 + 100 * i, 400 - 100 * j);
                    }
                    else if (board[i][j] == 1024 || board[i][j] == 2048){
                        g.drawString(String.valueOf(board[i][j]), 85 + 100 * i, 400 - 100 * j);
                    }
                }
            }
            g.setColor(BOARD_COLOR);
            g.fillRect(0, 500, 500, 100);
            g.setColor(COLOR_0);
            g.drawString("score: " + String.valueOf(score), 200, 560);
            if (isWon()){
                status.setText("YOU HAVE WON");
                won = true;
            }
            else if (isLost()){
                status.setText("YOU HAVE LOST");
            }
            else if (!isLost() || !isWon()){
                status.setText("Running...");
            }
        }


    }

    public boolean up(){
        if (moveUp()){
            mergeUp();
            moveUp();
            return true;
        }
        else if (mergeUp()){
            moveUp();
            return true;
        }
        return false;

    }

    public boolean down(){
        if (moveDown()){
            mergeDown();
            moveDown();
            return true;
        }
        else if (mergeDown()){
            moveDown();
            return true;
        }
        return false;
    }

    public boolean right(){
        if (moveRight()){
            mergeRight();
            moveRight();
            return true;
        }
        else if (mergeRight()){
            moveRight();
            return true;
        }
        return false;


    }

    public boolean left(){
        if (moveLeft()){
            mergeLeft();
            moveLeft();
            return true;
        }
        else if (mergeLeft()){
            moveLeft();
            return true;
        }
        return false;

    }

    public boolean moveUp(){
        boolean moved = false;
        for (int i = 0; i < SIDE; ++i){
            for (int j = SIDE - 2; j >= 0; --j){
                if (board[i][j] != 0 && board[i][j + 1] == 0){
                    moved = true;
                    int k = j + 1;
                    while (k < SIDE && board[i][k] == 0){
                        ++k;
                    }
                    int temp = board[i][j];
                    board[i][k - 1] = temp;
                    board[i][j] = 0;
                }
            }
        }
        return moved;
    }

    public boolean moveDown(){
        boolean moved = false;
        for (int i = 0; i < SIDE; ++i){
            for (int j = 1; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i][j - 1] == 0){
                    moved = true;
                    int k = j - 1;
                    while (k >= 0 && board[i][k] == 0){
                        --k;
                    }
                    int temp = board[i][j];
                    board[i][k + 1] = temp;
                    board[i][j] = 0;
                }
            }
        }
        return moved;
    }

    public boolean moveRight(){
        boolean moved = false;
        for (int i = SIDE - 2; i >= 0; --i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i + 1][j] == 0){
                    moved = true;
                    int k = i + 1;
                    while (k < SIDE && board[k][j] == 0){
                        ++k;
                    }
                    int temp = board[i][j];
                    board[k - 1][j] = temp;
                    board[i][j] = 0;
                }
            }
        }
        return moved;
    }

    public boolean moveLeft(){

        boolean moved = false;
        for (int i = 1; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i - 1][j] == 0){
                    moved = true;
                    int k = i - 1;
                    while (k >= 0 && board[k][j] == 0){
                        --k;
                    }
                    int temp = board[i][j];
                    board[k + 1][j] = temp;
                    board[i][j] = 0;
                }
            }
        }
        return moved;

    }

    public boolean mergeUp(){
        boolean merged = false;
        for (int i = 0; i < SIDE; ++i){
            for (int j = SIDE - 2; j >= 0; --j){
                if (board[i][j] != 0 && board[i][j + 1] == board[i][j]){
                    board[i][j + 1] = 2 * board[i][j + 1];
                    score += board[i][j + 1];
                    board[i][j] = 0;
                    merged = true;
                }
            }
        }
        return merged;
    }

    public boolean mergeDown(){
        boolean merged = false;
        for (int i = 0; i < SIDE; ++i){
            for (int j = 1; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i][j - 1] == board[i][j]){
                    board[i][j - 1] = 2 * board[i][j - 1];
                    score += board[i][j - 1];
                    board[i][j] = 0;
                    merged = true;
                }
            }
        }
        return merged;
    }

    public boolean mergeRight(){
        boolean merged = false;
        for (int i = SIDE - 2; i >= 0; --i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i + 1][j] == board[i][j]){
                    board[i + 1][j] = 2 * board[i + 1][j];
                    score += board[i + 1][j];
                    board[i][j] = 0;
                    merged = true;
                }
            }
        }
        return merged;
    }

    public boolean mergeLeft(){
        boolean merged = false;
        for (int i = 1; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] != 0 && board[i - 1][j] == board[i][j]){
                    board[i - 1][j] = 2 * board[i - 1][j];
                    score += board[i - 1][j];
                    board[i][j] = 0;
                    merged = true;
                }
            }
        }
        return merged;
    }

    public boolean isWon(){
        for (int i = 0; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] == 2048){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLost(){
        for (int i = 0; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] == 0){
                    return false;
                }
            }
        }
        for (int i = 0; i < SIDE; ++i){
            for (int j = 0; j < SIDE - 1; ++j){
                if (board[i][j] == board[i][j + 1]){
                    return false;
                }
            }
        }
        for (int i = 0; i < SIDE - 1; ++i){
            for (int j = 0; j < SIDE; ++j){
                if (board[i][j] == board[i + 1][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] copyOf(int [][] copy){
        int[][] copier = new int[SIDE][SIDE];
        for (int i = 0; i < SIDE; ++i){
            for (int j = 0; j < SIDE; ++j){
                copier[i][j] = copy[i][j];
            }
        }
        return copier;
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }


    //for tests
    public GameCourt(int x1, int x2, int x3, int x4,
                     int x5, int x6, int x7, int x8,
                     int x9, int x10, int x11, int x12,
                     int x13, int x14, int x15, int x16){
        board = new int[SIDE][SIDE];
        board[0][0] = x1;
        board[0][1] = x2;
        board[0][2] = x3;
        board[0][3] = x4;
        board[1][0] = x5;
        board[1][1] = x6;
        board[1][2] = x7;
        board[1][3] = x8;
        board[2][0] = x9;
        board[2][1] = x10;
        board[2][2] = x11;
        board[2][3] = x12;
        board[3][0] = x13;
        board[3][1] = x14;
        board[3][2] = x15;
        board[3][3] = x16;

    }

    public int sumOfTiles(){
        int sum = 0;
        for (int i = 0; i < SIDE; ++i) {
            for (int j = 0; j < SIDE; ++j) {
                sum += board[i][j];
            }
        }
        return sum;
    }

    public int[][] getCourt(){
        return copyOf(board);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int s){
        score = s;
    }

    public void simulateUp(){
        up();
        newTile();
        moves.add(copyOf(board));
        scores.add(score);
    }

    //File IO stuff

    public void saveGame(Writer out) throws IOException {
        try {
            new SaveFile(board, score, out);
        } catch (IOException e){
            //this should never happen
        }
    }

    public void loadGame(Reader r){
        try {
            LoadFile load = new LoadFile(r);
            board = load.getBoard();
            score = load.getScore();
            moves = new LinkedList<>();
            scores = new LinkedList<>();
            moves.add(board);
            scores.add(score);
            repaint();
        } catch (IOException e){
            //this should never happen
        } catch(LoadFile.FormatException x){
            status.setText("numbers don't work");
        }
    }

    public void focus(){
        requestFocusInWindow();
    }

    public void setStatus(String s){
        status.setText(s);
    }

    public class LoadFile {
        private int[][] newBoard;
        private int newScore;

        public class FormatException extends Exception {
            public FormatException(String msg) {
                super(msg);
            }
        }

        public boolean validNumber(int i){
            return (i == 0 || i == 2 || i == 4 || i == 8 || i == 16|| i == 32|| i == 64 || i == 128 || i == 256 ||
                    i == 512 || i == 1024 || i == 2048);
        }

        public LoadFile(Reader r) throws IOException, FormatException, NullPointerException {
            try {
                if (r == null) {
                    throw new IllegalArgumentException();
                }
                newBoard = new int[4][4];
                newScore = 0;
                int[] numbers = new int[16];
                int x = 0;
                BufferedReader buf = new BufferedReader(r);
                String next = buf.readLine();

                while (x < 16) {
                    if (next == null) {
                        status.setText("incorrect file format");
                        throw new FormatException("incorrect file format");
                    }
                    String s1 = "";
                    for (char c : next.toCharArray()) {
                        if (!Character.isDigit(c)) {
                            break;
                        } else {
                            s1 += c;
                        }
                    }
                    if (validNumber(Integer.parseInt(s1))) {
                        numbers[x] = Integer.parseInt(s1);
                    } else {
                        status.setText("numbers don't work");
                        throw new FormatException("numbers don't work");
                    }
                    ++x;
                    next = buf.readLine();
                }
                String s2 = "";
                for (char c : next.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        break;
                    } else {
                        s2 += c;
                    }
                }


                newBoard[0][0] = numbers[0];
                newBoard[0][1] = numbers[1];
                newBoard[0][2] = numbers[2];
                newBoard[0][3] = numbers[3];
                newBoard[1][0] = numbers[4];
                newBoard[1][1] = numbers[5];
                newBoard[1][2] = numbers[6];
                newBoard[1][3] = numbers[7];
                newBoard[2][0] = numbers[8];
                newBoard[2][1] = numbers[9];
                newBoard[2][2] = numbers[10];
                newBoard[2][3] = numbers[11];
                newBoard[3][0] = numbers[12];
                newBoard[3][1] = numbers[13];
                newBoard[3][2] = numbers[14];
                newBoard[3][3] = numbers[15];
                newScore = Integer.parseInt(s2);
            } catch (NullPointerException n){
                status.setText("invalid file format");
            }

        }
        public int[][] getBoard(){
            int [][] board = new int[4][4];
            for (int i = 0; i < 4; ++i){
                for (int j = 0; j < 4; ++j){
                    board[i][j] = newBoard[i][j];
                }
            }
            return board;
        }

        public int getScore(){
            return newScore;
        }
    }

    public class SaveFile {
        public SaveFile(int[][] board, int score, Writer out) throws IOException{
            for (int i = 0; i < 4; ++i){
                for (int j = 0; j < 4; ++j){
                    out.write(String.valueOf(board[i][j]) + "\n");
                }
            }
            out.write(String.valueOf(score));
            out.flush();

        }
    }

}