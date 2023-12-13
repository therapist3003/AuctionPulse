package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.util.Vector;

import CommonClasses.*;

public class BidderThread implements Runnable,Serializable{

	private static final long serialVersionUID = 1L;
	public ObjectInputStream input;
	public ObjectOutputStream output;
	public int n,ID;
	public String name,password,store;
	public Vector<Player> PlayerList;
	public SendCurrentPlayer current=new SendCurrentPlayer();
	public BidderThread(ObjectInputStream input, ObjectOutputStream output, String name, String password, int ID) throws Exception
	{
		this.input=input;
		this.output=output;
		this.name=name;
		this.password=password;
		this.ID=ID;
	}
	public void run()
	{
		try
		{
			BidderInfo bidder=Jdbc.LoadBidderState(name,password);
			output.writeObject(bidder);
			output.reset();
			PlayerList =Jdbc.LoadState();
			output.writeObject(PlayerList);
			output.reset();
			while(true)
			{
				try
				{
					Object obj=input.readObject();
					if(obj instanceof BidderInfo)
					{
						if(((BidderInfo)obj).myorders==null)
							name=Jdbc.UpdateBidderInfo(((BidderInfo)obj),name,password);
					}
					else if(obj instanceof String)
					{
						store=String.valueOf(obj);
						if(store.equals("PlayerList"))
						{
							PlayerList =Jdbc.LoadState();
							output.writeObject(PlayerList);
							output.reset();
						}
						if(((String)obj).equals("SoldPlayerList"))
						{
							PlayerList =Jdbc.LoadSoldState();
							output.writeObject(PlayerList);
							output.reset();
						}
						else if(store.equals("AuctionStatus"))
						{
							output.writeObject(Jdbc.AuctionStatus());
							output.reset();
						}
						else if(store.equals("Status"))
						{
							current=Jdbc.CurrentPlayerStatus(current);
							output.writeObject(current);
							output.reset();
							output.writeObject(Jdbc.CurrentBidStatus(current.ID));
							output.reset();
						}
						else if(store.equals("MyOrders"))
						{
							PlayerList =Jdbc.MyOrders(ID);
							output.writeObject(PlayerList);
							output.reset();
						}
					}
					else if(obj instanceof SendCost)
					{
						Jdbc.InsertBidCost((SendCost)obj);
					}
				}
				catch(SocketException se)
				{
					break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}