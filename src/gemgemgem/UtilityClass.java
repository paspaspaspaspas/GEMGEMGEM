package gemgemgem;

/**
 * This is just an utility class that contains some informations, like string or values, 
 * that are used numerous times throughout the program.
 * 
 * @author pas
 *
 */
public class UtilityClass {
	
	//IMAGES
	public static final String GET_BLUE_IMAGE = "resources/images/Cards/BlueCards/BaseCards/Card%s.png";
	public static final String GET_RED_IMAGE = "resources/images/Cards/RedCards/BaseCards/RedCard%s.png";
	public static final String GET_UTILITY_IMAGE = "resources/images/Utility/%s.png";
	
	//PLAYER COMMUNICATION
	public static final String MOVE_NOT_ALLOWED = "[System] : THIS CARD ISN'T STRONG ENOUGH";
	public static final String WRONG_PLACEMENT = "[System] : YOU CAN'T PLACE A CARD HERE";
	public static final String TWO_CARD_SELECTED = "[System] : YOU CAN'T HAVE 2 CARDS SELECTED";
	public static final String WRONG_TURN = "[System] : IT IS NOT YOUR TURN";
	public static final String WIN_MESSAGE = "[System] : YOU WON! CONGRATULATIONS!";
	public static final String DRAW_MESSAGE = "[System] : THIS IS A DRAW. COULD HAVE GONE WORSE";
	public static final String LOSS_MESSAGE = "[System] : YOU LOST... BETTER LUCK NEXT TIME";
	
	//NET 
	public static final String DRAW_COMMAND = "DRAW %d %s %d";
	public static final String GEM_COMMAND = "GEM %d %d %d";
	public static final String PLACE_COMMAND = "PLACE %d %d %s";
	public static final String PUSH_COMMAND = "PUSH %d %d %s %s";
	
	//ERRORS
	public static final String GET_IMAGE_ERROR = "[System] : THERE WAS AN ERROR RETRIVING AN IMAGE";
	public static final String COMMUNICATION_ERROR = "[System] : THERE HAS BEEN A COMMUNICATION ERROR";
}
