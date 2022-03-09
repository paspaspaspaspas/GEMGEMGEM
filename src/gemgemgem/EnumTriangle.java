package gemgemgem;

public enum EnumTriangle {
	UP(2),
	RIGHT(3),
	DOWN(0),
	LEFT(1);
	
	private int index;
	
	private EnumTriangle(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}	
}
