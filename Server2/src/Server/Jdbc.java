package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import CommonClasses.*;
public class Jdbc {

	public static Connection conn;
	public static Statement st1;
	public static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11,ps12,ps13,ps14,ps15,ps16,ps17,ps18,ps19,ps20,ps21;
	public static ResultSet rset1,rset2,rset3,rset4;
	public static int n;
	public static Vector<String> College_list=new Vector<String>(15);
	public static Iterator<String> I1;
	public static byte Barray[];
	public static void createConnection() throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/AuctionPulse";
		String userName = "root";
		String password = "Password123#@!";
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, userName, password);
		st1=conn.createStatement();
		ps1=conn.prepareStatement("update Status set Auction=?;");
		ps2=conn.prepareStatement("update Admin set Password=? where Name=?;");
		ps3=conn.prepareStatement("select * from Status;");
		ps4=conn.prepareStatement("Insert into Bid values(?,?,?,?)");
		ps5=conn.prepareStatement("Insert into Bidder(Name,City,Mob,DOB,Password,Nickname) values(?,?,?,?,?,?);");
		ps6=conn.prepareStatement("select Name,Mob,Password from Bidder;");
		ps7=conn.prepareStatement("update Bidder set Password=? where Name=?;");
		ps8=conn.prepareStatement("select * from Bidder where Name=? and Password=?;");
		ps9=conn.prepareStatement("update Bidder set Name=?,Nickname=?,City=?,Mob=?,DOB=? where Name=? and Password=?;");
		ps10=conn.prepareStatement("update Status set ID=?,Time=?;");
		ps11=conn.prepareStatement("select * from Bid where ID=?;");
		ps12=conn.prepareStatement("select count(*) as total from Player;");
		ps13=conn.prepareStatement("insert into SoldPlayer values(?,?,?,?,?,?,?,?);");
		ps14=conn.prepareStatement("select * from Bid where ID=? and Bid_Price in(select max(Bid_Price) from Bid where ID=?);");
		ps15=conn.prepareStatement("select Name,Password from Bidder;");
		ps16=conn.prepareStatement("select * from SoldPlayer where Bidder_ID=?;");
		ps17=conn.prepareStatement("insert into Player(Name,Reserve_Price,Image) values(?,?,?);");
		ps18=conn.prepareStatement("delete from Player where ID=?;");
		ps19=conn.prepareStatement("select * from Bidder where Id=?;");
		ps20=conn.prepareStatement("select * from Bidder where Name=? and Password=?;");
		ps21=conn.prepareStatement("select * from Player where ID=?;");
		ps1.setString(1,"NotStarted");
		ps1.executeUpdate();
	}
	public static boolean CheckAdmin(String s1,String s2) throws Exception
	{
		rset1 = st1.executeQuery("select * from Admin;");
		if(rset1.next())
		{
			if(rset1.getString("Name").equals(s1) && rset1.getString("Password").equals(s2))
				return true;
			else
				return false;
		}
		return false;
	}
	public static boolean CheckBidder(String s1, String s2) throws Exception
	{
		rset3 = st1.executeQuery("select * from Bidder;");
		boolean flag=false;
		while(rset3.next())
		{
			if(rset3.getString("Name").equals(s1) && rset3.getString("Password").equals(s2))
				flag=true;
		}
		return flag;
	}
	public static boolean ChangePassword(String s1,String s2,String s3) throws Exception
	{
		rset1 = st1.executeQuery("select * from Admin;");
		if(rset1.next())
		{
			if(rset1.getString("Name").equals(s1) && rset1.getString("Nickname").equals(s2))
			{
				ps2.setString(1,s3);
				ps2.setString(2,s1);
				ps2.executeUpdate();
				return true;
			}
			else
				return false;
		}
		return false;
	}
	public static boolean ChangeBidderPassword(String s1, String s2, String s3) throws Exception
	{
		rset1 = st1.executeQuery("select * from Bidder;");
		if(rset1.next())
		{
			if(rset1.getString("Name").equals(s1) && rset1.getString("Nickname").equals(s2))
			{
				ps7.setString(1,s3);
				ps7.setString(2,s1);
				ps7.executeUpdate();
				return true;
			}
			else
				return false;
		}
		return false;
	}
	public static boolean InsertBidder(BidderInfo s) throws Exception
	{
		rset1=ps6.executeQuery();
		while(rset1.next())
		{
			if(rset1.getLong("Mob")==s.mob && rset1.getString("Name").equals(s.name) && rset1.getString("Password").equals(s.password))
				return false;
		}
			ps5.setString(1,s.name);
			ps5.setString(2,s.city);
			ps5.setLong(3,s.mob);
			ps5.setString(4,s.dob);
			ps5.setString(5,s.password);
			ps5.setString(6,s.nickname);
			ps5.executeUpdate();
			return true;
	}
	public static void AuctionStarted() throws Exception
	{
		ps1.setString(1,"Started");
		ps1.executeUpdate();
	}
	public static void AuctionStopped() throws Exception
	{
		ps1.setString(1,"NotStarted");
		ps1.executeUpdate();
	}
	public static boolean AuctionStatus() throws Exception
	{
		synchronized(ps3)
		{
			rset2=ps3.executeQuery();
			String status;
			if(rset2.next())
			{
				status=rset2.getString("Auction");
				if(status.equals("Started"))
					return true;
			}
			return false;
		}
	}
	public static void InsertCurrentPlayer(SendCurrentPlayer c) throws Exception
	{
		ps10.setInt(1,c.ID);
		ps10.setString(2,c.Time);
		ps10.executeUpdate();
	}
	public static SendCurrentPlayer CurrentPlayerStatus(SendCurrentPlayer current) throws Exception
	{
		synchronized(ps3)
		{
		rset4=ps3.executeQuery();
		if(rset4.next())
		{
			current.ID=rset4.getInt("ID");
			current.Time=rset4.getString("Time");
		}}
		return current;
	}
	public static int PlayerSize() throws Exception
	{
		rset1=ps12.executeQuery();
		rset1.next();
		return rset1.getInt("total");
	}
	public static Vector<SendBidStatus> CurrentBidStatus(int ID) throws Exception
	{
		synchronized(ps11)
		{
		ps11.setInt(1,ID);
		rset1=ps11.executeQuery();
		Vector<SendBidStatus> i=new Vector<SendBidStatus>();
		SendBidStatus bid;
		while(rset1.next())
		{
			bid=new SendBidStatus(rset1.getInt("ID"),rset1.getString("Bidder_Name"),rset1.getInt("Bid_Price"),rset1.getInt("Bidder_ID"));
			i.add(bid);
		}
		return i;
		}
	}
	public static void InsertBidCost(SendCost cost) throws Exception
	{
		ps4.setInt(1,cost.ID);
		ps4.setString(2,cost.Bidder_Name);
		ps4.setInt(3,cost.cost);
		ps4.setInt(4,cost.Bidder_ID);
		ps4.executeUpdate();
	}
	public static void InsertBidder(int ID, String bidder, int price, int Bidder_ID) throws Exception
	{
		ps21.setInt(1,ID);
		rset2=ps21.executeQuery();
		rset2.next();
		ps13.setInt(1,rset2.getInt("ID"));
		ps13.setString(2,rset2.getString("Name"));
		ps13.setInt(3,rset2.getInt("Reserve_Price"));
		ps13.setBinaryStream(4,rset2.getBinaryStream("Image"));
		ps13.setInt(5,price);
		ps13.setInt(6,Bidder_ID);
		ps13.setString(7,bidder);
		ps13.executeUpdate();

		ps18.setInt(1,ID);
		ps18.executeUpdate();
	}
	public static int GetBidderID(String name, String password) throws Exception
	{
		ps20.setString(1,name);
		ps20.setString(2,password);
		rset1=ps20.executeQuery();
		rset1.next();
		return rset1.getInt("Id");
	}
	public static SendBidStatus GetHighestBidder(int ID) throws Exception
	{
		ps14.setInt(1,ID);
		ps14.setInt(2,ID);
		rset1=ps14.executeQuery();
		SendBidStatus bid;
		if(rset1.next())
			bid=new SendBidStatus(ID,rset1.getString("Bidder_Name"),rset1.getInt("Bid_Price"),rset1.getInt("Bidder_ID"));
		else
			bid=new SendBidStatus(ID,"NOT SOLD",-1,-1);
		return bid;
	}
	public static Vector<Player> MyOrders(int ID) throws Exception
	{
		ps16.setInt(1,ID);
		rset3=ps16.executeQuery();
		Vector<Player> PlayerList=new Vector<Player>();
		InputStream fis;
		int size;
		while(rset3.next())
		{
			Player player=new Player(rset3.getString("Name"),rset3.getInt("Reserve_Price"));
			ps19.setInt(1,ID);
			rset2=ps19.executeQuery();
			rset2.next();
			player.Bidder_Name=rset2.getString("Name");
			player.ID=rset3.getInt("ID");
			player.Hammer_Price=rset3.getInt("Hammer_Price");
			fis=(InputStream) rset3.getBinaryStream("Image");
			size=fis.available();
			player.b=new byte[size];
			fis.read(player.b);
			PlayerList.add(player);
		}
		return PlayerList;
	}
	public static String UpdateBidderInfo(BidderInfo bidder, String s1, String s2) throws Exception
	{
		ps9.setString(1,bidder.name);
		ps9.setString(2,bidder.nickname);
		ps9.setString(3,bidder.city);
		ps9.setLong(4,bidder.mob);
		ps9.setString(5,bidder.dob);
		ps9.setString(6,s1);
		ps9.setString(7,s2);
		ps9.executeUpdate();
		return bidder.name;
	}
	public static void DeletePlayer(int ID) throws Exception
	{
		ps18.setInt(1,ID);
		ps18.executeUpdate();
	}
	public static Vector<Player> LoadState() throws Exception
	{
		rset3 = st1.executeQuery("select * from Player;");
		Vector<Player> PlayerList=new Vector<Player>();
		InputStream fis;
		int size;
		while(rset3.next())
		{
			Player player=new Player(rset3.getString("Name"),rset3.getInt("Reserve_Price"));
			player.ID=rset3.getInt("ID");
			fis=(InputStream) rset3.getBinaryStream("Image");
			size=fis.available();
			player.b=new byte[size];
			fis.read(player.b);
			PlayerList.add(player);
		}
		return PlayerList;
	}
	public static Vector<Player> LoadSoldState() throws Exception
	{
		rset3 = st1.executeQuery("select * from SoldPlayer;");
		Vector<Player> PlayerList=new Vector<Player>();
		InputStream fis;
		int size;
		while(rset3.next())
		{
			Player player=new Player(rset3.getString("Name"),rset3.getInt("Reserve_Price"));
			player.Bidder_Name=rset3.getString("Bidder_Name");
			player.Hammer_Price=rset3.getInt("Hammer_Price");
			player.ID=rset3.getInt("ID");
			fis=(InputStream) rset3.getBinaryStream("Image");
			size=fis.available();
			player.b=new byte[size];
			fis.read(player.b);
			PlayerList.add(player);
		}
		return PlayerList;
	}
	public static BidderInfo LoadBidderState(String name, String password) throws Exception
	{
		ps8.setString(1,name);
		ps8.setString(2,password);
		rset1=ps8.executeQuery();
		if(rset1.next())
		{
			BidderInfo bidder=new BidderInfo();
			bidder.ID=rset1.getInt("Id");
			bidder.name=rset1.getString("Name");
			bidder.city =rset1.getString("City");
			bidder.dob=rset1.getString("DOB");
			bidder.nickname=rset1.getString("Nickname");
			bidder.password=rset1.getString("Password");
			bidder.mob=rset1.getLong("Mob");
			return bidder;
		}
		return null;
	}
	public static Boolean InsertPlayer(Player player) throws Exception
	{
		FileInputStream fis;
		File file=new File("Image");
		Path path=Paths.get(file.getAbsolutePath());
		Files.write(path,player.b);
		fis=new FileInputStream(file);
		ps17.setString(1,player.name);
		ps17.setInt(2,player.Reserve_Price);
		ps17.setBinaryStream(3,fis);
		ps17.executeUpdate();
		return true;
	}
	public static AllBidders LoadAllBidders() throws Exception
	{
		Vector<BidderInfo> students=new Vector<BidderInfo>();
		AllBidders h=new AllBidders();
		h.info=students;
		BidderInfo ct;
		rset3=ps15.executeQuery();
		while(rset3.next())
		{
			ct= LoadBidderState(rset3.getString("Name"),rset3.getString("Password"));
			h.info.add(ct);
		}
		return h;
	}
}
