package gemgemgem;

import gemgemgem.controller.MatchC;
import gemgemgem.view.MatchV;
import gemgemgem.view.MenuV;

public class EntryPoint {

	public static void main(String[] args) {
		MenuV.main(args);
		MatchC match = new MatchC();
	}

}
