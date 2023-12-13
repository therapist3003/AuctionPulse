package CommonClasses;

import java.io.Serializable;

public class SendBidStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int ID,Bid_Price, Bidder_ID;
	public String Bidder_Name;
	public SendBidStatus(int ID,String Bidder_Name,int Bid_Price,int Bidder_ID)
	{
		this.ID=ID;
		this.Bidder_Name =Bidder_Name;
		this.Bid_Price=Bid_Price;
		this.Bidder_ID =Bidder_ID;
	}
}
