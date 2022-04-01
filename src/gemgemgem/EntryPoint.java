package gemgemgem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import gemgemgem.view.EndV;
import gemgemgem.view.MenuV;
import gemgemgem.view.NetV;

/**
 * This is the entry point for this project. You should run this class in order
 * to play the game.
 *
 * @author pas
 *
 */
public class EntryPoint {
	/**
	 * This method create the 3 control screen of the game that will help the player
	 * navigating through the game. </br>
	 * 
	 * These windows are visible only one at the time since each of them represents
	 * one different point of the game.</br>
	 * 
	 * They are also linked together in order to be able to switch from one to the
	 * other depending on the state of the game.
	 * 
	 * This class is also responsible to create and start a thread that will run the
	 * music in the background.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		//View initialization
		MenuV.main(args);

		//Loading the music
		Music backgroundMusic = new Music();
		Thread musicThread = new Thread(backgroundMusic);
		musicThread.start();

	}

}
