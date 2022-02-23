package gemgemgem;

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
	BEETO(new int[] {0, 1, 0, 0}),
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
	EDGEFARMER(new int[] {3, 3, 0, 3}),
	FAIRY(new int[] {0, 0, 0, 2}),
	FARRELS(new int[] {0, 2, 0, 1}),
	FLAILARMOR(new int[] {1, 1, 1, 1}),
	FLAREY(new int[] {3, 3, 0, 0}),
	FLEETO(new int[] {1, 1, 0, 0}),
	GASTRONOMER(new int[] {0, 1, 2, 0}),
	GOATICIAN(new int[] {0, 2, 1, 0}),
	GOUACHE(new int[] {2, 1, 1, 0}),
	GRANDMASWAMP(new int[] {1, 2, 0, 1}),
	GRAPPS(new int[] {2, 0, 2, 0}),
	GRIFFOTH(new int[] {1, 1, 1, 0}),
	HEDGEFARMER(new int[] {0, 1, 0, 2}),
	HEDGEPUPIL(new int[] {2, 0, 0, 1}),
	HENGINEER(new int[] {2, 1, 0, 1}),
	HOOPKID(new int[] {1, 1, 2, 0}),
	HOPPICLES(new int[] {0, 2, 0, 2}),
	INVISISHADE(new int[] {0, 0, 1, 1}),
	JUMBOLORB(new int[] {2, 2, 2, 0}),
	LEDGEFARMER(new int[] {2, 1, 0, 0}),
	LIQUIDSAMURAIARCHER(new int[] {0, 3, 3, 0}),
	LIQUIDSAMURAIREAVER(new int[] {3, 0, 0, 3}),
	LIQUIDSAMURAISWORDSMAN(new int[] {0, 0, 3, 3}),
	MACAWBE(new int[] {0, 0, 2, 2}),
	MANNY(new int[] {3, 3, 3, 0}),
	MEMMEC(new int[] {2, 2, 0, 0}),
	MISSY(new int[] {0, 3, 3, 3}),
	MOLEMINION(new int[] {2, 0, 2, 2}),
	PLAYINGKID(new int[] {2, 0, 1, 1}),
	PROPELLERRAT(new int[] {1, 0, 0, 0}),
	RATKING(new int[] {2, 0, 0, 0}),
	RUFFIAN(new int[] {0, 0, 2, 1}),
	SERPRIZE(new int[] {0, 2, 2, 0}),
	SHOVELSMITH(new int[] {1, 2, 1, 0}),
	SKIP(new int[] {1, 2, 0, 0}),
	SLIMULACRA(new int[] {3, 0, 3, 0}),
	SUPERSKELETON(new int[] {0, 1, 1, 1}),
	TADVOLT(new int[] {0, 0, 2, 0}),
	TOADSDRAKE(new int[] {0, 2, 2, 2}),
	TORPEETO(new int[] {0, 2, 0, 0}),
	TRAITORUS(new int[] {1, 0, 2, 0}),
	TROUPPLEACOLYTE(new int[] {0, 2, 1, 1}),
	TROUPPLEMISSIONARY(new int[] {1, 0, 1, 2}),
	WHIPPICLES(new int[] {2, 0, 0, 2}),
	WIZZEM(new int[] {1, 0, 1, 1}),
	ZAMBY(new int[] {0, 1, 1, 0});
	
	private int[] arrows;
	private int player;
	
	private EnumCards(int[] arrows) {
		this.arrows = arrows;
		this.player = -1;
	}

	public int[] getArrows() {
		return arrows;
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public BufferedImage getImage() {
		BufferedImage image = null;
		try {
			if(this.player == 1) {
				image = ImageIO.read(new File(String.format("resources/images/Cards/BlueCards/BaseCards/Card%s.png", this.toString())));
			}else {
				image = ImageIO.read(new File(String.format("resources/images/Cards/RedCards/BaseCards/RedCard%s.png", this.toString())));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}

