package gemgemgem;

import gemgemgem.view.EndV;
import gemgemgem.view.MenuV;
import gemgemgem.view.NetV;

/**
 * This is the entry point for this project. You should run this class in order
 * to play the game.
 *
 * @author pas
 *
 */
public class EntryPoint {
	/**
	 * This method create the 3 control screen of the game that will help the player
	 * navigating through the game. </br>
	 * 
	 * These windows are visible only one at the time since each of them represents
	 * one different point of the game.</br>
	 * 
	 * They are also linked together in order to be able to switch from one to the
	 * other depending on the state of the game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MenuV.main(args);
		new NetV();
		new EndV();
	}

}
