package Bidder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import CommonClasses.BidderCollection;
import CommonClasses.Player;

public class ShowPlayers extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Player player;
	public DefaultTableModel model;
	public JTable table;
	public JScrollPane j1;
	private JLabel label,label2;
	public byte Barray[]=new byte[120000];   //120 KBytes
	public FileOutputStream fos;
	public ImageIcon image;
	public ShowPlayers(ObjectInputStream input, ObjectOutputStream output) throws Exception {

		setLayout(null);
		setBounds(309,180,980,564);
		
		model=new DefaultTableModel();
		table = new JTable(model){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };
	    };
		model.addColumn("No");
		model.addColumn("Player Image");
        model.addColumn("Player Name");
        model.addColumn("Owning Team");
        model.addColumn("Base Price");
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12));
        table.setRowHeight(20);
        j1=new JScrollPane(table);
        j1.setBounds(70,71,830,420);
		add(j1);
		
		table.getColumn("Player Image").setCellRenderer(new LabelRenderer());
		
		label = new JLabel("List of Players for Auction");
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Dialog", Font.PLAIN, 25));
		label.setBounds(325, 24, 350, 30);
		add(label);
		
		Iterator<Player> i= BidderCollection.PlayerList.iterator();
		BidderCollection.players =new HashMap<Integer, Player>();
		BidderCollection.Images=new HashMap<Integer,ImageIcon>();
		while(i.hasNext())
		{
			player =i.next();
			Barray= player.b;
			File file=new File("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			fos=new FileOutputStream(file);
			fos.write(Barray);
			image=new ImageIcon("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			label2=new JLabel(image);
			BidderCollection.players.put(player.ID, player);
			BidderCollection.Images.put(player.ID,image);
			model.addRow(new Object[]{player.ID,label2, player.name, player.Reserve_Price});
		}
	}
	public void refresh() throws Exception
	{
		int rows = model.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
		   model.removeRow(i); 
		}
		Iterator<Player> i= BidderCollection.PlayerList.iterator();
		BidderCollection.players.clear();
		BidderCollection.Images.clear();
		while(i.hasNext())
		{
			player =i.next();
			Barray= player.b;
			File file=new File("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			fos=new FileOutputStream(file);
			fos.write(Barray);
			image=new ImageIcon("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			label2=new JLabel(image);
			BidderCollection.players.put(player.ID, player);
			BidderCollection.Images.put(player.ID,image);
			model.addRow(new Object[]{player.ID,label2, player.name, player.Reserve_Price});
		}
	}
	class LabelRenderer implements TableCellRenderer
	{
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			TableColumn tc=table.getColumn("Player Image");
			tc.setMinWidth(200);
			table.setRowHeight(200);
			return (Component)value;
		}
	}
}
