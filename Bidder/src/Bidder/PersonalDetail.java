package Bidder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Timer;

import CommonClasses.BidderInfo;


public class PersonalDetail extends JPanel{

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public boolean flag=false;
	public Container c2;
	public String c;
	public String name, city,dob,nickname,mob;
	public PersonalDetail thispanel;
	public JTextPane textPane,textPane2,textPane3,textPane_1,textPane_3;
	public JLabel lblFillTheFollowing, lblBidderName, lblBidderName2, lblBidderName3,lblPleaseEnterThe,lblPleaseEnterThe2,lblPleaseEnterThe3,lblPleaseEnterThe4,lblPleaseEnterThe6,
	lblMobileNumber,lblNickName;
	public JButton btnPutChoice;
	public JLabel label;
	public PersonalDetail(ObjectInputStream input, ObjectOutputStream output, ShowDetails parent, BidderInfo bidder) {
		
		setBounds(0,0,980,564);
		setLayout(null);
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		
		lblFillTheFollowing = new JLabel("Fill the Following Information");
		lblFillTheFollowing.setBounds(340, 20, 410, 30);
		lblFillTheFollowing.setForeground(Color.BLUE);
		lblFillTheFollowing.setFont(new Font("Arial", Font.PLAIN, 25));
		add(lblFillTheFollowing);
		
		lblBidderName = new JLabel("Name");
		lblBidderName.setBounds(201, 81, 153, 25);
		lblBidderName.setFont(new Font("Serif", Font.PLAIN, 20));
		add(lblBidderName);
		
		textPane = new JTextPane();
		textPane.setText(bidder.name);
		textPane.setBounds(409, 81, 439, 36);
		textPane.setFont(new Font("Arial", Font.PLAIN, 20));
		textPane.setBackground(new Color(179, 217, 255));
		add(textPane);
		
		lblBidderName2 = new JLabel("Hometown");
		lblBidderName2.setBounds(201, 141, 153, 25);
		lblBidderName2.setFont(new Font("Serif", Font.PLAIN, 20));
		add(lblBidderName2);
		
		textPane2 = new JTextPane();
		textPane2.setText(bidder.city);
		textPane2.setBounds(409, 141, 439, 36);
		textPane2.setFont(new Font("Arial", Font.PLAIN, 20));
		textPane2.setBackground(new Color(179, 217, 255));
		add(textPane2);
		
		lblBidderName3 = new JLabel("Date of Birth");
		lblBidderName3.setBounds(201, 201, 153, 25);
		lblBidderName3.setFont(new Font("Serif", Font.PLAIN, 20));
		add(lblBidderName3);
		
		
		textPane3 = new JTextPane();
		textPane3.setText(String.valueOf(bidder.dob));
		textPane3.setBounds(409, 201, 439, 36);
		textPane3.setFont(new Font("Arial", Font.PLAIN, 20));
		textPane3.setBackground(new Color(179, 217, 255));
		add(textPane3);
		
		lblPleaseEnterThe = new JLabel("Please Enter the valid text");
		lblPleaseEnterThe.setBounds(409, 116, 225, 15);
		lblPleaseEnterThe.setForeground(Color.RED);
		lblPleaseEnterThe.setVisible(false);
		add(lblPleaseEnterThe);
		
		lblPleaseEnterThe2 = new JLabel("Please Enter the valid text");
		lblPleaseEnterThe2.setBounds(409, 176, 225, 15);
		lblPleaseEnterThe2.setForeground(Color.RED);
		lblPleaseEnterThe2.setVisible(false);
		add(lblPleaseEnterThe2);
		
		lblPleaseEnterThe3 = new JLabel("Please Enter the valid text");
		lblPleaseEnterThe3.setBounds(409, 236, 225, 15);
		lblPleaseEnterThe3.setForeground(Color.RED);
		lblPleaseEnterThe3.setVisible(false);
		add(lblPleaseEnterThe3);
		
		lblPleaseEnterThe4 = new JLabel("Please Enter the valid text");
		lblPleaseEnterThe4.setBounds(409, 296, 225, 15);
		lblPleaseEnterThe4.setForeground(Color.RED);
		lblPleaseEnterThe4.setVisible(false);
		add(lblPleaseEnterThe4);
		
		lblPleaseEnterThe6 = new JLabel("Please Enter the valid text");
		lblPleaseEnterThe6.setBounds(409, 356, 225, 15);
		lblPleaseEnterThe6.setForeground(Color.RED);
		lblPleaseEnterThe6.setVisible(false);
		add(lblPleaseEnterThe6);
		
		textPane_1 = new JTextPane();
		textPane_1.setBounds(409, 261, 439, 36);
		textPane_1.setText(String.valueOf(bidder.mob));
		textPane_1.setFont(new Font("Arial", Font.PLAIN, 20));
		textPane_1.setBackground(new Color(179, 217, 255));
		add(textPane_1);
		
		lblMobileNumber = new JLabel("Mobile No");
		lblMobileNumber.setBounds(201, 261, 153, 25);
		lblMobileNumber.setFont(new Font("Serif", Font.PLAIN, 20));
		add(lblMobileNumber);
		
		textPane_3 = new JTextPane();
		textPane_3.setText(bidder.nickname);
		textPane_3.setBounds(409, 321, 439, 36);
		textPane_3.setFont(new Font("Arial", Font.PLAIN, 20));
		textPane_3.setBackground(new Color(179, 217, 255));
		add(textPane_3);
		
		lblNickName = new JLabel("Nick Name");
		lblNickName.setBounds(201, 321, 153, 25);
		lblNickName.setFont(new Font("Serif", Font.PLAIN, 20));
		add(lblNickName);
		
		btnPutChoice = new JButton("Submit");
		btnPutChoice.setBounds(409, 430, 198, 36);
		btnPutChoice.setFont(new Font("Arial",Font.BOLD, 20));
		btnPutChoice.setFocusable(true);
		thispanel=this;
		btnPutChoice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2) {
				
				try {
					output.reset();
				} catch (SocketException e1) 
				{	
					JOptionPane.showMessageDialog(null,"Server Down");
					return;
				}
				catch (IOException e) {
					
					e.printStackTrace();
				}
			   if(textPane.getText().isEmpty() || textPane2.getText().isEmpty() 
					   || textPane3.getText().isEmpty() ||textPane_1.getText().isEmpty() || textPane_3.getText().isEmpty())
			   {
				   if(textPane.getText().isEmpty())
						lblPleaseEnterThe.setVisible(true);
				   if(textPane2.getText().isEmpty())
						lblPleaseEnterThe2.setVisible(true);
				   if(textPane3.getText().isEmpty())
						lblPleaseEnterThe3.setVisible(true);
				   if(textPane_1.getText().isEmpty())
						lblPleaseEnterThe4.setVisible(true);
				   if(textPane_3.getText().isEmpty())
						lblPleaseEnterThe6.setVisible(true);
						Timer t=new Timer(2000,new ActionListener() 
						{
							public void actionPerformed(ActionEvent e)
							{
							   if(textPane.getText().isEmpty())
									lblPleaseEnterThe.setVisible(false);
							   if(textPane2.getText().isEmpty())
									lblPleaseEnterThe2.setVisible(false);
							   if(textPane3.getText().isEmpty())
									lblPleaseEnterThe3.setVisible(false);
							   if(textPane_1.getText().isEmpty())
									lblPleaseEnterThe4.setVisible(false);
							   if(textPane_3.getText().isEmpty())
									lblPleaseEnterThe6.setVisible(false);
							}
						});
						t.start();
						t.setRepeats(false);
						return;
				}
			   	bidder.name=textPane.getText();
				bidder.city =textPane2.getText();
				bidder.dob=textPane3.getText();
			   try 
			    {
				    formatter.parse(bidder.dob);
				    c=Character.toString(bidder.dob.charAt(bidder.dob.length()-1));
				    if((bidder.dob.length()!=10 && bidder.dob.length()!=9 && bidder.dob.length()!=8) || !c.matches("[0-9]+"))
				    {
				    	lblPleaseEnterThe3.setVisible(true);
						Timer t=new Timer(2000,new ActionListener() 
						{
							public void actionPerformed(ActionEvent e)
							{
								lblPleaseEnterThe3.setVisible(false);
							}
						});
						flag=true;
						t.setRepeats(false);
						t.start();
				    }
				} catch (ParseException e1) 
			    {
					lblPleaseEnterThe3.setVisible(true);
					Timer t=new Timer(2000,new ActionListener() 
					{
						public void actionPerformed(ActionEvent e)
						{
							lblPleaseEnterThe3.setVisible(false);
						}
					});
					flag=true;
					t.setRepeats(false);
					t.start();
				}
			    try 
			    {
			    	bidder.mob=Long.parseLong(textPane_1.getText());
			    	if(textPane_1.getText().length()!=10)
			    	{
			    		lblPleaseEnterThe4.setVisible(true);
						Timer t=new Timer(2000,new ActionListener() 
						{
							public void actionPerformed(ActionEvent e)
							{
								lblPleaseEnterThe4.setVisible(false);
							}
						});
						flag=true;
						t.setRepeats(false);
						t.start();
				    }
			    }
			    catch(NumberFormatException e1)
				{
			    	lblPleaseEnterThe4.setVisible(true);
					Timer t=new Timer(2000,new ActionListener() 
					{
						public void actionPerformed(ActionEvent e)
						{
							lblPleaseEnterThe4.setVisible(false);
						}
					});
					flag=true;
					t.setRepeats(false);
					t.start();
				}
			    if(flag)
			    {
			    	flag=false;
			    	return;
			    }
				bidder.nickname=textPane_3.getText();
				setVisible(false);
				parent.lblNewLabel.setText(bidder.name);
				parent.label.setText(bidder.city);
				parent.label_1.setText(bidder.dob);
				parent.label_2.setText(String.valueOf(bidder.mob));
				parent.label_4.setText(bidder.nickname);
				parent.lblNewLabel.setVisible(true);
				parent.label.setVisible(true);
				parent.label_1.setVisible(true);
				parent.label_2.setVisible(true);
				parent.label_4.setVisible(true);
				parent.btnEditProfile.setVisible(true);
				try 
				{
					output.writeObject(bidder);
					output.reset();
				} catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			 }
		});
		add(btnPutChoice);
		
		label = new JLabel("( yyyy-mm-dd)");
		label.setForeground(Color.BLACK);
		label.setBounds(201, 222, 111, 15);
		add(label);
	}
}