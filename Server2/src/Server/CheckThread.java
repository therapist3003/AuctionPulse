package Server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import CommonClasses.*;

public class CheckThread implements Runnable,Serializable{
	private static final long serialVersionUID = 1L;
	
	public int ID;
	public Socket s;
	public ObjectInputStream input;
	public ObjectOutputStream output;
	public Object obj;
	public CheckThread(Socket s1)
	{
		this.s=s1;
	}

	public void run() 
	{
		
		boolean n;
		try
		{
			input = new ObjectInputStream(s.getInputStream());
			output=new ObjectOutputStream(s.getOutputStream());
			while(true) 
			{
			obj = input.readObject();
			if(obj instanceof AdminInfo)
			{
				if(((AdminInfo) obj).nickname!=null)
				{
					n=Jdbc.ChangePassword(((AdminInfo) obj).name,((AdminInfo) obj).nickname,((AdminInfo) obj).password);
					output.writeObject(n);
					output.reset();
				}
				else
				{
					n=Jdbc.CheckAdmin(((AdminInfo) obj).name,((AdminInfo) obj).password);
					output.writeObject(n);
					output.reset();
					if(n)
					{
						AdminThread adminthread=new AdminThread(input,output);
						Thread t=new Thread(adminthread);
						t.start();
						return;
					}
				}
			}
			else if(obj instanceof BidderInfo)
			{
				if(((BidderInfo) obj).city !=null)
				{
					n=Jdbc.InsertBidder(((BidderInfo) obj));
					output.writeObject(n);
					output.reset();
				}
				else if(((BidderInfo) obj).nickname!=null)
				{
					n=Jdbc.ChangeBidderPassword(((BidderInfo) obj).name,((BidderInfo) obj).nickname,((BidderInfo) obj).password);
					output.writeObject(n);
					output.reset();
				}
				else
				{
					n=Jdbc.CheckBidder(((BidderInfo) obj).name,((BidderInfo) obj).password);
					output.writeObject(n);
					output.reset();
					if(n)
					{
						ID=Jdbc.GetBidderID(((BidderInfo) obj).name, ((BidderInfo) obj).password);
						BidderThread studentthread=new BidderThread(input,output,((BidderInfo) obj).name,((BidderInfo) obj).password,ID);
						Thread t=new Thread(studentthread);
						t.start();
						return;
					}
				}
			}
			else
			{
				if(Timer.Total_Time==-1)
				{
					output.writeObject("ServerActive");
				}
				else
					output.writeObject(Timer.checktime());
				output.reset();
			}
		  }
		} catch (Exception e) 
		{
			return;
		}
  }
}
