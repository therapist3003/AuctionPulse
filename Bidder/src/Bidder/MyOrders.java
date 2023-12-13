package Bidder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import CommonClasses.*;

public class MyOrders extends JPanel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Player player;
	public int ID;
	public DefaultTableModel model;
	public JTable table;
	public JScrollPane j1;
	public JLabel lblNewLabel,label2;
	public byte Barray[]=new byte[120000];   //120 KBytes
	public FileOutputStream fos;
	public ImageIcon image;
	public MyOrders(BidderInfo bidder) throws Exception {

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
        model.addColumn("Player Name");
        model.addColumn("Player Image");
        model.addColumn("Owning Team");
        model.addColumn("Base Price");
        model.addColumn("Sold Price");
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12));
        table.setRowHeight(20);
        j1=new JScrollPane(table);
        j1.setBounds(40,71,890,420);
		add(j1);
		
		table.getColumn("Player Image").setCellRenderer(new LabelRenderer());
		
		lblNewLabel = new JLabel("List of Players Purchased");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(330, 24, 350, 30);
		lblNewLabel.setFont(new Font("Arial",Font.PLAIN,25));
		add(lblNewLabel);
	}
	public void refresh() throws Exception
	{
		int rows = model.getRowCount(); 
		for(int i = rows - 1; i >=0; i--)
		{
		   model.removeRow(i);
		}
		Iterator<Player> i= BidderCollection.MyPlayerList.iterator();
		while(i.hasNext())
		{
			player =i.next();
			Barray= player.b;
			File file=new File("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			fos=new FileOutputStream(file);
			fos.write(Barray);
			image=new ImageIcon("/home/tharun30/Package_sem5/Online-Auction-System-master/Bidder/BidderImages/"+String.valueOf(player.ID)+".txt");
			label2=new JLabel(image);
			model.addRow(new Object[]{player.ID, player.name,label2, player.Reserve_Price, player.Hammer_Price});
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
