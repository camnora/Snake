package robersj;

/**
 * This class is responsible for most static variables.
 * @author robersj
 *
 */
public class Constants {

	/**
	 * The size of the tiles. Must be divisible by game size.
	 */
	public static final int TILE_SIZE = 25;
	
	/**
	 * The upper boundary of the game.
	 */
	public static final int UPPERBOUND = Constants.GAME_SIZE / Constants.TILE_SIZE;
	
	/**
	 * The lower boundary of the game.
	 */
	public static final int LOWERBOUND = 0;
	
	/**
	 * The size of the game window.
	 */
	public static final int GAME_SIZE = 500;
	
	/**
	 * The movement direction of the snakes head.
	 */
	public static char moveDirection = 'w';
	
	/**
	 * The filename of the highscores file
	 */
	public static final String HIGHSCORE_FILE = "highscore.txt";
}
