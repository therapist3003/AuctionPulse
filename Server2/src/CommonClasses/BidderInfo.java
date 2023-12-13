package CommonClasses;


import java.io.Serializable;
import java.util.Vector;


public class BidderInfo implements Serializable{

	private static final long serialVersionUID = -2880869040951508387L;
	public String name, city,dob,nickname,password;
	public long mob;
	public int ID;
	public Vector<Player> myorders=null;
}
