/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("2048!");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { court.undo(); }
        });
        control_panel.add(undo);

        final JButton instructions = new JButton ("Instructions");
        instructions.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Welcome to 2048!\n" +
                        "In this game, you move the tiles up, down, right, or left (with the arrow keys)\n" +
                        "Whenever two tiles with the same number touch during a move\n" +
                        "they will merge into one tile with the sum of the two numbers.\n" +
                        "Your goal in this game is to reach the number 2048!\n" +
                        "Good luck have fun.", "Instructions!", JOptionPane.INFORMATION_MESSAGE);
                court.focus();
            }
        });

        control_panel.add(instructions);

        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFileChooser save = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    save.setDialogTitle("Save Your Game");
                    int returnValue = save.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File file = save.getSelectedFile();
                        court.saveGame(new FileWriter(file));
                    }
                } catch (IOException x){
                    court.setStatus("invalid file has been chosen");
                } finally {
                    court.focus();
                }
            }
        });

        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final JFileChooser load = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    load.setDialogTitle("Load Your Game");
                    int returnValue = load.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File file = load.getSelectedFile();
                        court.loadGame(new FileReader(file));

                    }
                } catch (FileNotFoundException x){
                    court.setStatus("file has not been found");
                } finally {
                    court.focus();
                }
            }
        });


        control_panel.add(saveButton);
        control_panel.add(loadButton);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}