package gemgemgem.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public enum EnumCards {
	/*
	 * Per poter accedere al file ho bisogno che il nome non rispetti il CamelCase, ma 
	 * sia scritto tutto attaccato.
	 */
	ARMORER(new int[] {1, 0, 2, 1}),
	AXOLONGL(new int[] {0, 3, 0, 3}),
	BARDSPRITE(new int[] {1, 1, 1, 0}),
	BARTON(new int[] {0, 1, 1, 2}),
	BEETO(new int[] {0, 0, 1, 0}),
	BIGBOHTO(new int[] {2, 2, 0, 2}),
	BLACKKNIGHT(new int[] {0, 1, 2, 1}),
	BLITZSTEED(new int[] {0, 0, 0, 1}),
	BLORB(new int[] {0, 0, 1, 0}),
	BONECLANG(new int[] {0, 1, 0, 1}),
	CHESTER(new int[] {2, 0, 1, 0}),
	COOPER(new int[] {1, 1, 0, 1}),
	CROAKER(new int[] {0, 0, 1, 2}),
	DARKACOLYTE(new int[] {3, 0, 3, 3}),
	DARKREIZE(new int[] {3, 0, 3, 2}),
	DIVEDRAKE(new int[] {1, 0, 1, 0}),
	DOE(new int[] {1, 1, 0, 2}),
	DOZEDRAKE(new int[] {1, 0, 0, 1}),
	DUELISTDI(new int[] {1, 0, 0, 2}),
	EDGEFARMER(new int[] {3, 3, 0, 3});
	

	private int[] arrows;
	
	private EnumCards(int[] arrows) {
		this.arrows = arrows;
	}

	public int[] getArrows() {
		return arrows;
	}

	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(String.format("resources/images/Cards/BlueCards/BaseCards/Card%s.png", this.toString())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}

