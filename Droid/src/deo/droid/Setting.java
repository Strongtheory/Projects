package deo.droid;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import game.skeleton.FileIO;

public class Setting {
	public static boolean soundEnabled = true;
	public static boolean touchEnabled = true;
	public final static String file = ".Droid";
	
	public static void load(FileIO files){
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader
					(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(input.readLine());
			touchEnabled = Boolean.parseBoolean(input.readLine());
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
			output = new BufferedWriter(new OutputStreamWriter
					(files.writeFile(file)));
			output.write(Boolean.toString(soundEnabled));
			output.write("\n");
			output.write(Boolean.toString(touchEnabled));
		} catch (IOException e) {
		} finally {
			try {
				if(output != null)
					output.close();
			} catch (IOException e2) {
			}
		}
	}
}
