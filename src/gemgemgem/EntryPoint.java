package gemgemgem;

import gemgemgem.controller.MatchC;
import gemgemgem.view.MenuV;
import gemgemgem.view.ServerV;

public class EntryPoint {
	
	public static void main(String[] args) {
		MenuV.main(args);
		ServerV serverV = new ServerV();
	}

}
