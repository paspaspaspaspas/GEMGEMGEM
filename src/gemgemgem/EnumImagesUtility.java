package gemgemgem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum EnumImagesUtility {
	RED_BACK("RedCardBack"),
	BLUE_BACK("BlueCardBack"),
	ICON1("King_Knight_Ultimate_Supreme_Portrait"),
	ICON2("King_Knight_Mirror_Portrait"),
	TABLE("table"),
	BACKGROUND("background");
	
	private String name;
	
	private EnumImagesUtility(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(String.format("resources/images/Utility/%s.png", this.name)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
