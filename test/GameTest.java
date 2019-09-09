import org.junit.Test;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;


public class GameTest {
    @Test
    public void basicUpTest(){
        GameCourt court = new GameCourt(2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        court.up();
        int[][] newCourt = court.getCourt();
        assertEquals(2, newCourt[0][3]);
    }

    @Test
    public void basicDownTest(){
        GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2);
        court.down();
        int[][] newCourt = court.getCourt();
        assertEquals(2, newCourt[3][0]);
    }

    @Test
    public void basicRightTest(){
        GameCourt court = new GameCourt(2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        court.right();
        int[][] newCourt = court.getCourt();
        assertEquals(2, newCourt[3][0]);
    }

    @Test
    public void basicLeftTest(){
        GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2);
        court.left();
        int[][] newCourt = court.getCourt();
        assertEquals(2, newCourt[0][3]);
    }

    @Test
    public void basicMergeUpTest(){
        GameCourt court = new GameCourt(2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        court.up();
        int[][] newCourt = court.getCourt();
        assertEquals(4, newCourt[0][3]);
    }

    @Test
    public void basicMergeDownTest(){
        GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2);
        court.down();
        int[][] newCourt = court.getCourt();
        assertEquals(4, newCourt[3][0]);
    }

    @Test
    public void basicMergeRightTest(){
        GameCourt court = new GameCourt(2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        court.right();
        int[][] newCourt = court.getCourt();
        assertEquals(4, newCourt[3][0]);
    }

    @Test
    public void basicMergeLeftTest(){
        GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2);
        court.left();
        int[][] newCourt = court.getCourt();
        assertEquals(4, newCourt[0][3]);
    }

    @Test
    public void lostTest(){
        GameCourt court = new GameCourt(2, 4, 8, 16, 4, 8, 16, 32, 8, 16, 32, 64, 16, 32, 64, 128);
        assertTrue(court.isLost());
    }

    @Test
    public void advancedUpTest(){
        GameCourt court1 = new GameCourt(2, 2, 2, 2, 2, 2, 4, 4, 2, 0, 2, 2, 4, 8, 16, 32);
        court1.up();
        int[][] newCourt1 = court1.getCourt();
        GameCourt court2 = new GameCourt(0, 0, 4, 4, 0, 0, 4, 8, 0, 0, 2, 4, 4, 8, 16, 32);
        int[][] newCourt2 = court2.getCourt();
        assertArrayEquals(newCourt1, newCourt2);
    }

    @Test
    public void advancedDownTest(){
        GameCourt court1 = new GameCourt(2, 2, 2, 2, 2, 2, 4, 4, 2, 0, 2, 2, 4, 8, 16, 32);
        court1.down();
        int[][] newCourt1 = court1.getCourt();
        GameCourt court2 = new GameCourt(4, 4, 0, 0, 4, 8, 0, 0, 4, 2, 0, 0, 4, 8, 16, 32);
        int[][] newCourt2 = court2.getCourt();
        assertArrayEquals(newCourt1, newCourt2);
    }

    @Test
    public void advancedRightTest(){
        GameCourt court1 = new GameCourt(2, 2, 2, 4, 2, 2, 0, 8, 2, 4, 2, 16, 2, 4, 2, 32);
        court1.right();
        int[][] newCourt1 = court1.getCourt();
        GameCourt court2 = new GameCourt(0, 0, 0, 4, 0, 0, 0, 8, 4, 4, 2, 16, 4, 8, 4, 32);
        int[][] newCourt2 = court2.getCourt();
        assertArrayEquals(newCourt1, newCourt2);
    }

    @Test
    public void advancedLeftTest(){
        GameCourt court1 = new GameCourt(2, 2, 2, 4, 2, 2, 0, 8, 2, 4, 2, 16, 2, 4, 2, 32);
        court1.left();
        int[][] newCourt1 = court1.getCourt();
        GameCourt court2 = new GameCourt(4, 4, 4, 4, 4, 8, 2, 8, 0, 0, 0, 16, 0, 0, 0, 32);
        int[][] newCourt2 = court2.getCourt();
        assertArrayEquals(newCourt1, newCourt2);
    }

    @Test
    public void newTilesTest(){
        GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        court.newTile();
        assertTrue(court.sumOfTiles() == 2 || court.sumOfTiles() == 4);
    }

    @Test
    public void winTest(){
        GameCourt court = new GameCourt(2048, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        assertTrue(court.isWon());
    }

    @Test
    public void basicLoadTest(){
        try {
            GameCourt court1 = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            court1.loadGame(new FileReader("files/test1.txt"));
            GameCourt court2 = new GameCourt(2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
            court2.setScore(100);
            assertArrayEquals(court1.getCourt(), court2.getCourt());
            assertEquals(court1.getScore(), court2.getScore());
        } catch (FileNotFoundException e){

        }
    }

    @Test(expected = NullPointerException.class)
    public void emptyFileTest(){
        try {
            GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            court.loadGame(new FileReader("files/test2.txt"));

        } catch (FileNotFoundException e){

        }
    }

    @Test(expected = NullPointerException.class)
    public void invalidFileFormatTest(){
        try {
            GameCourt court = new GameCourt(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            court.loadGame(new FileReader("files/test3.txt"));
        } catch (FileNotFoundException e){

        }
    }

    @Test
    public void simpleUndoTest(){
        JLabel status = new JLabel();
        GameCourt court = new GameCourt(status);
        court.reset();
        int[][] court1 = court.getCourt();
        court.simulateUp();
        court.undo();
        int[][] court2 = court.getCourt();
        assertArrayEquals(court1, court2);
    }

    @Test
    public void advancedUndoTest(){
        JLabel status = new JLabel();
        GameCourt court = new GameCourt(status);
        court.reset();
        int[][] court1 = court.getCourt();
        court.simulateUp();
        court.simulateUp();
        court.simulateUp();
        court.undo();
        court.undo();
        court.undo();
        int[][] court2 = court.getCourt();
        assertArrayEquals(court1, court2);
    }


}
