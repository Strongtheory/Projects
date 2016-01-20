package deo.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import deo.skeleton.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public final static int[] highscores = new int[] {100, 80, 50, 30, 10};
	public final static String file = ".jumper";
	
	public static void load(FileIO files){
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(input.readLine());
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(input.readLine());
			}
		} catch (IOException e) {
		} catch (NumberFormatException e) {
		} finally {
			try {
				if(input != null)
					input.close();
			} catch (IOException e2) {
			}
		}
	}
	
	public static void save(FileIO files){
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			output.write(Boolean.toString(soundEnabled));
			output.write("\n");
			for (int i = 0; i < 5; i++) {
				output.write(Integer.toBinaryString(highscores[i]));
				output.write("\n");
			}
		} catch (IOException e) {
		} finally {
			try {
				if(output != null)
					output.close();
			} catch (IOException e2) {
			}
		}
	}
	
	public static void addScore(int score){
		for (int i = 0; i < 5; i++) {
			if(highscores[i] < score){
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j-1];
				highscores[i] = score;
				break;
			}
		}
	}
}
