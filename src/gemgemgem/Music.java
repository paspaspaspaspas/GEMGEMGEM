package gemgemgem;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * It is used to play the background music.
 * 
 * It creates a Clip object that reproduces a .wav file saved in resources on loop.ù
 * 
 * @author pas
 *
 */
public class Music implements Runnable{
	
	static Clip clip;

	//CONSTRUCTOR
	public Music() {
		// Create AudioInputStream object
		AudioInputStream audioInputStream;
		
		try {
			audioInputStream = AudioSystem
					.getAudioInputStream(new File("./resources/music.wav").getAbsoluteFile());
			// Create clip reference
			clip = AudioSystem.getClip();

			// Open audioInputStream to the clip
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println(UtilityClass.MUSIC_ERROR);
		}

		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * When the thread starts, this method is called and the music begin to play.
	 */
	public void run() {
		try {
			clip.start();
		}
		catch (Exception e) {
			System.err.println(UtilityClass.MUSIC_ERROR);
		}
	}

}
