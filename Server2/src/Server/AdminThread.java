package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import CommonClasses.*;

public class AdminThread implements Runnable,Serializable{

	private static final long serialVersionUID = 1L;
	public ObjectInputStream input;
	public ObjectOutputStream output;int n;
	public AllBidders bidders;
	public Vector<Player> PlayerList;
	public boolean result;
	public SendCurrentPlayer current=new SendCurrentPlayer();
    public Iterator<Player> iterate;
    public Player player;
	public AdminThread(ObjectInputStream input,ObjectOutputStream output) throws Exception
	{
		this.input=input;
		this.output=output;
	}
	public void run()
	{
		try
		{
			PlayerList =Jdbc.LoadState();
			ServerCollection2.players =new HashMap<Integer, Player>();
			iterate= PlayerList.iterator();
			while(iterate.hasNext())
			{
				player =iterate.next();
				ServerCollection2.players.put(player.ID, player);
			}
			PlayerList =Jdbc.LoadState();
			output.writeObject(PlayerList);
			output.reset();
			while(true)
			{
				try 
				{
					Object obj=input.readObject();
					if(obj instanceof SendCurrentPlayer)
					{
						Jdbc.InsertCurrentPlayer(((SendCurrentPlayer)obj));
						current=Jdbc.CurrentPlayerStatus(current);
						output.writeObject(Jdbc.CurrentBidStatus(current.ID));
						output.reset();
					}
					if(obj instanceof String)
					{
						if(((String)obj).equals("BidderDetails"))
						{
							bidders=Jdbc.LoadAllBidders();
							output.writeObject(bidders);
							output.reset();
						}
						if(((String)obj).equals("PlayerList"))
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
						else if(((String)obj).equals("AuctionStarted"))
						{
							Jdbc.AuctionStarted();
							TimerThread t=new TimerThread(input,output,Jdbc.PlayerSize());
							Thread t2=new Thread(t);
							t2.start();
						}
						else if(((String)obj).equals("Status"))
						{
							current=Jdbc.CurrentPlayerStatus(current);
							output.writeObject(current);
							output.reset();
							output.writeObject(Jdbc.CurrentBidStatus(current.ID));
							output.reset();
						}
						else if(((String)obj).equals("AuctionStatus"))
						{
							output.writeObject(Jdbc.AuctionStatus());
							output.reset();
						}
					}
					else if(obj instanceof Player)
					{
						player =(Player)obj;
						result=Jdbc.InsertPlayer(player);
						output.writeObject(result);
						output.reset();
						synchronized(ServerCollection2.players) {
						PlayerList =Jdbc.LoadState();
						ServerCollection2.players.clear();
						iterate= PlayerList.iterator();
						while(iterate.hasNext())
						{
							player =iterate.next();
							ServerCollection2.players.put(player.ID, player);
						}
						}
					}
					else if(obj instanceof Integer)
					{
						Jdbc.DeletePlayer((Integer)obj);
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