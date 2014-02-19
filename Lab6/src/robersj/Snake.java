package robersj;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Snake {
	/**
	 * The container of the application.
	 */
	private JFrame frame;
	
	/**
	 * Button that starts the game/resets the game.
	 */
	static JButton btnStart;
	
	/**
	 * The button responsible for pausing and resuming game.
	 */
	private JButton btnPause;
	
	/**
	 * Reports the score of the game.
	 */
	private JTextArea scoreReport;
	
	/**
	 * Holds the buttons and score
	 */
	private JPanel south;
	
	/**
	 * Holds the game
	 */
	private JPanel center;
	
	/**
	 * Object responsible for creating a new game of snake.
	 */
	private Game game;
	
	/**
	 * The listener for start and pause buttons.
	 */
	private GameButtonListener gameButtonListener;
	
	/**
	 * Timer responsible for keeping the score updated and checks for collisions.
	 */
	private Timer timer;
	
	/**
	 * Updater is the task for the timer.
	 */
	private Updater task;
	
	/**
	 * The high score of the user.
	 */
	private int highScore;
	
	/**
	 * Creates a new Snake objet that constructs the Swing components.
	 */
	public Snake() {
		this.instantiate();
		this.addComponents();
		this.configureComponents();
		this.setSizes();
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	/**
	 * Instantiates all of the instance variables,
	 */
	private void instantiate() {
		timer = new Timer();
		task = new Updater();
		frame = new JFrame();
		btnStart = new JButton("Start");
		btnPause = new JButton("Pause");
		scoreReport = new JTextArea();
		south = new JPanel();
		center = new JPanel();
		game = new Game(Constants.GAME_SIZE, Constants.GAME_SIZE);
		gameButtonListener = new GameButtonListener();
		try {
			highScore = FileManagement.readScore();
		} catch (FileNotFoundException e) {
			System.err.println("Error reading file.");
			e.printStackTrace();
		}
	}	
	
	/**
	 * Adds the components to the frame.
	 */
	private void addComponents() {
		south.add(btnStart);
		south.add(btnPause);
		south.add(scoreReport);
		south.setLayout(new GridLayout(1, 3));
		center.setLayout(new FlowLayout());
		center.add(game);
		frame.add(south, BorderLayout.SOUTH);
		frame.add(center, BorderLayout.CENTER);	
	}	
	
	/**
	 * Configures game related components.
	 */
	private void configureComponents() {
		timer.scheduleAtFixedRate(task, 100, 100);
		btnPause.setEnabled(false);
		scoreReport.setEditable(false);
		scoreReport.setText("Score: \nHigh Score:");
		game.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == 'w' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'd')
					Constants.moveDirection = e.getKeyChar();
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		btnStart.addActionListener(gameButtonListener);
		btnPause.addActionListener(gameButtonListener);
	}
	
	/**
	 * Adjusts the sizes of the swing objects so they fit the frame.
	 */
	private void setSizes() {
		btnStart.setPreferredSize(new Dimension(100, 50));
		btnPause.setPreferredSize(new Dimension(100, 50));
		scoreReport.setPreferredSize(new Dimension(100, 50));
		game.setPreferredSize(new Dimension(Constants.GAME_SIZE, Constants.GAME_SIZE));
	}
	
	/**
	 * GameButtonListener is responsible for listening to the pause and start buttons.
	 * @author robersj
	 *
	 */
	private class GameButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btnStart)) {
				game.requestFocus();
				btnStart.setEnabled(false);
				
				game.reset();
				game.run();
				btnPause.setEnabled(true);
			}
			if(e.getSource().equals(btnPause)) {
				if(game.isPaused()) {
					btnPause.setText("Pause");
					game.resume();
					game.requestFocus();
				}
				else {
					btnPause.setText("Resume");
					game.pause();
					game.requestFocus();
				}
			}
			
		}
	}
	
	/**
	 * Updater is a timertask that manages the score and deals with collisions.
	 * @author robersj
	 *
	 */
	private class Updater extends TimerTask {

		@Override
		public void run() {
			if(game.getSnakesHeadCoords().isOutOfBounds(game.getBodyCoords())) {
				game.stop();
				btnStart.setEnabled(true);
				btnPause.setEnabled(false);
				if(game.getScore() > highScore) {
					highScore = game.getScore();
					try {
						FileManagement.writeScore(highScore + "");
					} catch (IOException e) {
						System.err.println("Error writing file.");
						e.printStackTrace();
					}
				}
			}
			scoreReport.setText("Score: " + game.getScore() + "\nHigh Score: " + highScore);
		}
	}
}
