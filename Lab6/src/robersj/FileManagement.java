package robersj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManagement {	
	
	public static int readScore() throws FileNotFoundException {
		FileReader fr = new FileReader(new File("highscore.txt"));
		Scanner stdIn = new Scanner(fr);
		int score;
		if(stdIn.hasNextInt())
			score = stdIn.nextInt();
		else
			score = 0;
		stdIn.close();
		return score;
		
	}
	
	public static void writeScore(String score) throws IOException {
		FileWriter fw = new FileWriter(new File("highscore.txt"));
		fw.write(score);
		fw.close();
	}
}
