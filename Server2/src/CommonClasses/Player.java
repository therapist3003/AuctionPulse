package CommonClasses;

import java.io.Serializable;

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 506837230885805592L;
	public String name,Bidder_Name;
	public int Reserve_Price,ID,Hammer_Price;
	public byte b[];
	public Player(String name, int Reserve_Price)
	{
		this.name=name;
		this.Reserve_Price=Reserve_Price;
	}

}
