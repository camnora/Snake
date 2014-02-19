package robersj;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class Game extends Canvas {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -3541579350260757481L;
	
	/**
	 * True -> user lost due to collision<br />
	 * False -> user is still playing
	 */
	boolean userLost;
	
	/**
	 * Responsible for keeping the score. Resets when game is over.
	 */
	private int score;
	
	/**
	 * 
	 */
	private Image apple;
	
	/**
	 * Image used when the snake is moving up.
	 */
	private Image snakeW;
	
	/**
	 * Image used when the snake is moving left.
	 */
	private Image snakeA;
	
	/**
	 * Image used when the snake is moving down.
	 */
	private Image snakeS;
	
	/**
	 * Image used when the snake is moving right.
	 */
	private Image snakeD;
	
	/**
	 * The snakes hat.
	 */
	private Image hat;
	
	/**
	 * The snakes hat up-side-down.
	 */
	private Image Uhat;
	
	/**
	 * Stands for "Snake's Body Coordinates." 
	 */
	private List<Coordinate> sbc;
	
	
	/**
	 * The coordinates of the snakes head.
	 */
	private Coordinate snakesHeadCoords;
	
	/**
	 * The coordinates of the apple<br />
	 * There can only be one apple at a time.
	 */
	private Coordinate appleCoords;
	
	/**
	 * Object used to generate random numbers.
	 */
	private Random rGen;
	
	/**
	 * Timer used for the Updater object
	 */
	private Timer timer;
	
	/**
	 * task is responsible for moving the snake.
	 */
	private Updater task;
	
	
	/**
	 * Creates a game object.<br />
	 * Recommended size: 500px by 500px.
	 * @param width Width of the game
	 * @param height Height of the game.
	 */
	public Game(int width, int height) {
		this.setSize(width, height);
		initializeInstanceVariables();	
	}
	
	/**
	 * Creates a game with given dimensions.<br />
	 * Recommended size: 500px by 500px.
	 * @param d The dimensions of the game.
	 */
	public Game(Dimension d) {
		this.setSize(d);
		initializeInstanceVariables();
	}
	
	/**
	 * Runs the game by creating a new timer and task.<br />
	 * Is used in resuming the game and starting a new one.
	 */
	public void run() {
		timer = new Timer();
		task = new Updater();
		timer.scheduleAtFixedRate(task, 250, 250);
	}
	
	/**
	 * Pauses the game.
	 */
	public void pause() {
		timer.cancel();
		timer = null;
	}
	
	/**
	 * Resumes the game.
	 */
	public void resume() {
		timer = new Timer();
		task = new Updater();
		timer.scheduleAtFixedRate(task, 250, 250);
	}
	
	/**
	 * Stops the game.
	 */
	public void stop() {
		timer.cancel();
	}

	/**
	 * Returns true if the game is paused.
	 * @return Returns true if the game is paused.
	 */
	public boolean isPaused() {
		return timer == null;
	}
	
	/**
	 * Resets the game.
	 */
	public void reset() {
		initializeInstanceVariables();
	}
	
	/**
	 * Paints the graphics to the screen
	 */
	public void paint(Graphics g) {
		Image db = createImage(Constants.GAME_SIZE, Constants.GAME_SIZE);
		Graphics dbg = db.getGraphics();
		dbg.setColor(Color.GRAY);
		dbg.fillRect(0, 0, Constants.GAME_SIZE, Constants.GAME_SIZE);
		dbg.setColor(new Color(34, 174, 82));
		
		/**
		 * This loop will iterate through the coords of the snakes body
		 * and paint it.
		 */
		for(Coordinate c : sbc)
			dbg.fillRect(c.x * Constants.TILE_SIZE, c.y * Constants.TILE_SIZE, 
					Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		if(Constants.moveDirection == 'w') {
			dbg.drawImage(snakeW, snakesHeadCoords.x * Constants.TILE_SIZE, 
					snakesHeadCoords.y * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
			
			dbg.drawImage(Uhat, snakesHeadCoords.x * Constants.TILE_SIZE, 
					(snakesHeadCoords.y + 1) * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
		}
		
		if(Constants.moveDirection == 'a') {
			dbg.drawImage(snakeA, snakesHeadCoords.x * Constants.TILE_SIZE, 
					snakesHeadCoords.y * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
			
			dbg.drawImage(hat, snakesHeadCoords.x * Constants.TILE_SIZE, 
					(snakesHeadCoords.y - 1) * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
		}
		if(Constants.moveDirection == 's') {
			dbg.drawImage(snakeS, snakesHeadCoords.x * Constants.TILE_SIZE, 
					snakesHeadCoords.y * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
			
			dbg.drawImage(hat, snakesHeadCoords.x * Constants.TILE_SIZE, 
					(snakesHeadCoords.y - 1) * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
		}
		if(Constants.moveDirection == 'd') {
			dbg.drawImage(snakeD, snakesHeadCoords.x * Constants.TILE_SIZE, 
					snakesHeadCoords.y * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
			
			dbg.drawImage(hat, snakesHeadCoords.x * Constants.TILE_SIZE, 
					(snakesHeadCoords.y - 1) * Constants.TILE_SIZE, 
					Constants.TILE_SIZE, Constants.TILE_SIZE, null);
		}
		dbg.setColor(Color.RED);
		if(appleCoords != null)
			dbg.drawImage(apple, appleCoords.x * Constants.TILE_SIZE, 
					appleCoords.y * Constants.TILE_SIZE, Constants.TILE_SIZE, 
					Constants.TILE_SIZE, null);
		
		g.drawImage(db, 0, 0, this);
		g.dispose();
		dbg.dispose();
	}
	
	/**
	 * Places the apple in a random location.<br />
	 * Note: it will not place the apple on the snake or out of bounds.
	 */
	private void placeApple() {	
		rGen = new Random();
		if (appleCoords == null)
			appleCoords = new Coordinate();
		int x = rGen.nextInt(Constants.UPPERBOUND);
		int y = rGen.nextInt(Constants.UPPERBOUND);
		
		/**
		 * Attempts to find an empty coordinate for the apple.
		 */
		while(!isOccupied(new Coordinate(x, y))) {
			x = rGen.nextInt(Constants.UPPERBOUND);
			y = rGen.nextInt(Constants.UPPERBOUND);
		}
		appleCoords.setCoordinates(x, y);
		
	}
	
	/**
	 * Returns true if the snake is on the apple.	
	 * @return
	 */
	private boolean isOnApple() {
		return snakesHeadCoords.equals(appleCoords);
	}
	
	/**
	 * Returns true if the coordinate is occupied.
	 * @param c The coordinate you're checking.
	 * @return Returns true if the coordinate is occupied.
	 */
	private boolean isOccupied(Coordinate c) {
		if(snakesHeadCoords.equals(c) || sbc.contains(c))
			return false;
		return true;
	}
	
	/**
	 * Returns the coordinates of the snakes body.
	 * @return Returns the corrdinates of the snakes body.
	 */
	public List<Coordinate> getBodyCoords() {
		return sbc;
	}
	
	/**
	 * Gets the coordinates of the snakes head.
	 * @return Returns the coordinates of the snakes head.
	 */
	public Coordinate getSnakesHeadCoords() {
		return snakesHeadCoords;
	}
	
	/**
	 * Returns the score.
	 * @return Returns the score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Initializes all the variables. This is used primarily by the constructor 
	 * and reset functions.
	 */
	private void initializeInstanceVariables() {
		timer = new Timer();
		task = new Updater();
		snakesHeadCoords = new Coordinate(10, 5);
		sbc = new ArrayList<Coordinate>();		
		userLost = false;	
		score = 0;
		try {
			apple = ImageIO.read(new File("apple.png"));
			snakeW = ImageIO.read(new File("snakeW.png"));
			snakeA = ImageIO.read(new File("snakeA.png"));
			snakeS = ImageIO.read(new File("snakeS.png"));
			snakeD = ImageIO.read(new File("snakeD.png"));
			hat = ImageIO.read(new File("hat.png"));
			Uhat = ImageIO.read(new File("Uhat.png"));
		} catch (IOException e) {
			System.err.println("Could not find graphics.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Class Responsible for moving the snake and adding parts to its body.
	 * @author robersj
	 *
	 */
	private class Updater extends TimerTask {

		@Override
		public void run() {	
			Coordinate temp = new Coordinate(snakesHeadCoords.x, snakesHeadCoords.y);
			if(appleCoords == null)
				placeApple();
			if(Constants.moveDirection == 'w') {
				snakesHeadCoords.y--;
			}
			if(Constants.moveDirection == 'a') {
				snakesHeadCoords.x--;
			}
			if(Constants.moveDirection == 's') {
				snakesHeadCoords.y++;
			}
			if(Constants.moveDirection == 'd') {
				snakesHeadCoords.x++;
			}
			if(!sbc.isEmpty()) {
				sbc.add(0, new Coordinate(temp.x, temp.y));
				sbc.remove(sbc.size() - 1);
			}
			if(isOnApple()) {
				if(!sbc.isEmpty())
					sbc.add(new Coordinate(Coordinate.getLastCoordinate(sbc)));
				else
					sbc.add(new Coordinate(temp));
				placeApple();
				score++;
			}
			repaint();
		}
	}
}