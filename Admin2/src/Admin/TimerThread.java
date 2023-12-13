package Admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;

import CommonClasses.*;

public class TimerThread implements Runnable,Comparator<PriorityBid>{

	public Iterator<ImageIcon> iterate;
	public ImageIcon image,icon;
	public Start parent;
	public String data;
	public char letter;
	public int i,i2,size,ID=-1,rows;
	public String store;
	public SendCurrentPlayer current=new SendCurrentPlayer();
	public ObjectInputStream input;
	public ObjectOutputStream output;
	public PriorityQueue<PriorityBid> pq;
	public PriorityBid pb;
	public Player player;
	public Vector<SendBidStatus> v;
	public SendBidStatus bid;
	public byte Barray[]=new byte[120000];   //120 KBytes
	public FileOutputStream fos;
	public boolean flag=false;
	public Sell sell;
	public AuctionRunning parent2;
	public StartAuction startAuction;
	public Vector<Player> PlayerList;
	public Iterator<Player> it;
	
	public TimerThread(ObjectInputStream input,ObjectOutputStream output,Start parent,Sell sell,AuctionRunning parent2,StartAuction startAuction)
	{
		this.parent=parent;
		this.input=input;
		this.output=output;
		this.sell=sell;
		this.parent2=parent2;
		this.startAuction=startAuction;
		pq=new PriorityQueue<PriorityBid>(100,this);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		Timer timer = new Timer();
		try 
		{
			synchronized(output) {
			output.writeObject("PlayerList");
			output.reset();
			ServerCollection.PlayerList =(Vector<Player>)input.readObject();
			Iterator<Player> i=ServerCollection.PlayerList.iterator();
			ServerCollection.players.clear();
			ServerCollection.Images.clear();
			while(i.hasNext())
			{
				player =i.next();
				Barray= player.b;
				File file=new File("/home/tharun30/AuctionPulse/Admin2/AdminImages/"+String.valueOf(player.ID)+".txt");
				fos=new FileOutputStream(file);
				fos.write(Barray);
				image=new ImageIcon("/home/tharun30/AuctionPulse/AdminImages/"+String.valueOf(player.ID)+".txt");
				ServerCollection.players.put(player.ID, player);
				ServerCollection.Images.put(player.ID,image);
			}
			timer.schedule(new GetStatus(), 0, 1000);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	class GetStatus extends TimerTask {
	    @SuppressWarnings("unchecked")
		public void run() {
	       
	    	if(flag)
	    		return;
	    	try 
	    	{
	    		synchronized(output) 
				{
	    			output.writeObject("Status");
					output.reset();
					current=(SendCurrentPlayer)input.readObject();
					v=(Vector<SendBidStatus>) input.readObject();
					if(current.ID==0)
					{
						flag=true;
						parent.setVisible(false);
						parent2.setVisible(false);
						sell.lblcost.setVisible(true);
						sell.lblFillTheFollowing.setVisible(true);
						sell.lblHeight.setVisible(true);
						sell.lblImage.setVisible(true);
						sell.lblName.setVisible(true);
						sell.btnAdd.setVisible(true);
						sell.btnNewButton.setVisible(true);
						sell.textPane.setVisible(true);
						sell.textPane2.setVisible(true);
						sell.textPane_1.setVisible(true);
						startAuction.btnStartAuction.setVisible(true);
						output.writeObject("PlayerList");
						output.reset();
						PlayerList=(Vector<Player>)input.readObject();
						ServerCollection.PlayerList =PlayerList;
						ServerCollection.players.clear();
						ServerCollection.Images.clear();
						it=PlayerList.iterator();
						if(it==null)
							return;
						while(it.hasNext())
						{
							player =it.next();
							Barray= player.b;
							File file=new File("/home/tharun30/AuctionPulse/Admin2/AdminImages/"+String.valueOf(player.ID)+".txt");
							fos=new FileOutputStream(file);
							fos.write(Barray);
							image=new ImageIcon("/home/tharun30/AuctionPulse/AdminImages/"+String.valueOf(player.ID)+".txt");
							ServerCollection.players.put(player.ID, player);
							ServerCollection.Images.put(player.ID,image);
						}
						cancel();
						return;
					}
				}
				for(Integer n:ServerCollection.players.keySet())
				{
					if(n==current.ID)
					{
						icon=ServerCollection.Images.get(n);
						player =ServerCollection.players.get(n);
						parent.lblName.setIcon(icon);
						parent.lblname.setText(player.name);
						parent.label_1.setText(Integer.toString(player.ID));
						parent.lblcost.setText(String.valueOf(player.Reserve_Price));
						parent.lblNewLabel.setText(current.Time);
						break;
					}
				}
				rows = parent.model.getRowCount();
				for(int i = rows - 1; i >=0; i--)
				{
					parent.model.removeRow(i);
				}
				Iterator<SendBidStatus> iterate=v.iterator();
				size=v.size();
				pq.clear();
				for ( int row = 0; row < size; row++ )
				{
					bid=iterate.next();
			        PriorityBid pc=new PriorityBid(bid,bid.Bid_Price);
			        pq.add(pc);
				}
				while(!pq.isEmpty())
				{
			    	pb=pq.poll();
					parent.model.addRow(new Object[]{pb.bid.Bidder_Name,pb.bid.Bid_Price});
				}	
			} catch (Exception e1) 
	    	{
				e1.printStackTrace();
			}
	    }
	}
	@Override
	public int compare(PriorityBid p1, PriorityBid p2) {
		if(p1.priority>p2.priority)
			return -1;
		else if(p1.priority<p2.priority)
			return 1;
		return -1;
	}
}
