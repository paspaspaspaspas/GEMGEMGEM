package gemgemgem.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * It contains all the informations that are necessary to the view in order to
 * visualize the current model.
 * 
 * Istances of this class will be created by the model in order to be a
 * container that the controller will forward toward the view.
 * 
 * @author pas
 *
 */
public class ModelInfo {

	//ATTRIBUTES
	private BufferedImage[] imagesP1 = new BufferedImage[3];
	private BufferedImage[][] imagesBoard = new BufferedImage[5][5];
	private BufferedImage[] imagesP2 = new BufferedImage[3];
	private ArrayList<Integer[]> gems = new ArrayList<Integer[]>();
	private BufferedImage selectedCard;

	//CONSTRUCTOR
	public ModelInfo(BufferedImage[] imagesP1, BufferedImage[][] imagesBoard, BufferedImage[] imagesP2,
			ArrayList<Integer[]> gems, BufferedImage selectedCard) {
		this.imagesP1 = imagesP1;
		this.imagesBoard = imagesBoard;
		this.imagesP2 = imagesP2;
		this.gems = gems;
		this.selectedCard = selectedCard;
	}

	//GETTERS AND SETTERS
	public BufferedImage[] getImagesP1() {
		return imagesP1;
	}

	public void setImagesP1(BufferedImage[] imagesP1) {
		this.imagesP1 = imagesP1;
	}

	public BufferedImage[][] getImagesBoard() {
		return imagesBoard;
	}

	public void setImagesBoard(BufferedImage[][] imagesBoard) {
		this.imagesBoard = imagesBoard;
	}

	public BufferedImage[] getImagesP2() {
		return imagesP2;
	}

	public void setImagesP2(BufferedImage[] imagesP2) {
		this.imagesP2 = imagesP2;
	}

	public ArrayList<Integer[]> getGems() {
		return gems;
	}

	public void setGems(ArrayList<Integer[]> gems) {
		this.gems = gems;
	}

	public BufferedImage getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(BufferedImage selectedCard) {
		this.selectedCard = selectedCard;
	}

}
