package gemgemgem;

/**
 * This enum class represents the arrow pointer that shows up once a card on the
 * board is hovered while holding another card.
 * The attribute linked with each value shows which arrow will oppose to this direction
 * of insertion.
 * 
 * @author pas
 *
 */
public enum EnumTriangle {
	UP(2),
	RIGHT(3),
	DOWN(0),
	LEFT(1);
	
	//ATTRIBUTES
	private int index;
	
	//CONSTRUCTOR
	private EnumTriangle(int index) {
		this.index = index;
	}

	//GETTERS AND SETTERS
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}	
}
