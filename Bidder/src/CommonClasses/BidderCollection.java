package CommonClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;

public class BidderCollection implements Serializable{

	private static final long serialVersionUID = 1L;
	public static Vector<Player> PlayerList =null;
	public static Vector<Player> SoldPlayerList =null;
	public static HashMap<Integer,ImageIcon> Images=null;
	public static HashMap<Integer, Player> players =null;
	public static HashMap<Integer,ImageIcon> soldImages=null;
	public static HashMap<Integer, Player> soldplayers =null;
	public static Vector<Player> MyPlayerList =null;
}
