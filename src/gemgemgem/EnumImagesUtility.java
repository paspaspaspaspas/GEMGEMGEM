package gemgemgem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This enum class contains the informations linked to some images needed
 * throughout the program that are not cards.
 * 
 * @author pas
 *
 */
public enum EnumImagesUtility {
	RED_BACK("RedCardBack"), 
	BLUE_BACK("BlueCardBack"), 
	ICON1("King_Knight_Ultimate_Supreme_Portrait"),
	ICON2("King_Knight_Mirror_Portrait"), 
	TABLE("table"), 
	BACKGROUND("background");

	//ATTRIBUTES
	private String name;

	//CONSTRUCTOR
	/**
	 * Private constructor for the EnumImagesUtility enum class.
	 * 
	 * @param name : String
	 */
	private EnumImagesUtility(String name) {
		this.name = name;
	}

	//GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	//METHODS
	/**
	 * It returns the image from the resources folder linked to a specific object 
	 * (ex. icons, backgrounds...) based on its name
	 * 
	 * @return image : BufferedImage
	 */
	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(String.format(UtilityClass.GET_UTILITY_IMAGE, this.name)));
		} catch (IOException e) {
			System.err.print(UtilityClass.GET_IMAGE_ERROR);
		}
		return image;
	}
}
