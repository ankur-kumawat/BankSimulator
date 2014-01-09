package bank;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.sql.*;

public class BankSimulation implements ActionListener {
	
	private JFrame f;
	private JMenuBar jmb;
	private JMenu acnt,trans;
	private JMenuItem nw,cls,ext,dpst,wthdrw;
	private JDesktopPane dp;
	public boolean nflag=true,cflag=true,dflag=true,wflag=true;
		
	public BankSimulation(){
		nw=new JMenuItem("New");
		cls=new JMenuItem("Close");
		ext=new JMenuItem("Exit");
		acnt=new JMenu("Account");
		acnt.add(nw);
		acnt.add(cls);
		acnt.addSeparator();
		acnt.add(ext);
		nw.addActionListener(this);
		cls.addActionListener(this);
		ext.addActionListener(this);
		
		dpst=new JMenuItem("Deposit");
		wthdrw=new JMenuItem("WithDraw");
		trans=new JMenu("Transaction");
		trans.add(dpst);
		trans.add(wthdrw);
		dpst.addActionListener(this);
		wthdrw.addActionListener(this);
		
		jmb=new JMenuBar();
		jmb.add(acnt);
		jmb.add(trans);
		
		dp=new JDesktopPane();
		
		f=new JFrame();
		f.setJMenuBar(jmb);
		f.add(dp);
		f.setBounds(100,100,600,500);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void newAccount()
	{
		final JInternalFrame n=new JInternalFrame("Create New Accout");
		nflag=false;
		JPanel p1=new JPanel(new BorderLayout());
		p1.add(new JLabel("Account No.            :             Auto"),BorderLayout.NORTH);
		JPanel p2=new JPanel(new BorderLayout());
		final JTextField tn=new JTextField(20);
		JPanel px1=new JPanel();
		px1.add(new JLabel("Name  :  "));
		px1.add(tn);
		p2.add(px1,BorderLayout.NORTH);
		p1.add(p2);
		JPanel p3=new JPanel(new BorderLayout());
		JPanel px2=new JPanel();
		px2.add(new JLabel("Amount  :"));
		final JTextField ta=new JTextField(20);
		px2.add(ta);
		p3.add(px2,BorderLayout.NORTH);
		p2.add(p3);
		System.out.println("Hello");
		JButton sv=new JButton("Save");
		sv.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{	
					if(!(ta.getText().matches("[^0-9]*")))
					{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
						String s="insert into acnt(nme,balance,identy) values(?,?,?)";
						PreparedStatement stmt=con.prepareStatement(s);
						//System.out.println("Hmm	1");
						s=tn.getText();
						stmt.setString(1, s);
					//	System.out.println("Hmm2");
						stmt.setString(2, ta.getText());
					//	System.out.println("Hmm");
						Statement smt=con.createStatement();
						ResultSet r= smt.executeQuery("select * from acnt");
						int z=(int)(Math.random()*10000);
						System.out.println(z);
						while(r.next())
						{
							int x=Integer.parseInt(r.getString("identy"));
							if(x==z)
							{
								z=(int)(Math.random()*10000);
								System.out.println(z);
								r=smt.executeQuery("select * from acnt");
							}
						}
						stmt.setString(3,""+z);
						stmt.execute();
						Statement iii=con.createStatement();
						String o="select * from acnt where identy="+z;
						ResultSet an=iii.executeQuery(o);
						String k=null;
						if(an.next())
							k=an.getString("acc_no");
						JOptionPane.showMessageDialog(n, "Account Created Successfuly!!\nYour Account No. is : "+k,"Success!!!",JOptionPane.INFORMATION_MESSAGE);
						stmt.close();
						con.close();
						tn.setText("");
						ta.setText("");
					}
					else
						JOptionPane.showMessageDialog(n, "Only digits are allowed as amount ","Reinsert!!!",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}
		});
		JButton cn=new JButton("Cancel");
		cn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				tn.setText("");
				ta.setText("");
			}
		});
		JButton cl=new JButton("Close");
		cl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				n.setVisible(false);
				n.dispose();
			}
		});
		JPanel p4=new JPanel();
		p4.add(sv);
		p4.add(cl);
		p4.add(cl);
		p3.add(p4);
		n.add(p1);
		n.setBounds(130,130,300,200);
		n.setVisible(true);
		dp.add(n);
	}
	
	private void closeAccount()
	{
		cflag=false;
		final JButton del=new JButton("Delete");
		final JInternalFrame n=new JInternalFrame("Close Account");
		JPanel p1=new JPanel(new BorderLayout());
		JPanel px1=new JPanel(new FlowLayout());
		px1.add(new JLabel("Account No.: "));
		final JTextField ta=new JTextField(10);
		px1.add(ta);
		p1.add(px1,BorderLayout.NORTH);
		
		JPanel p2=new JPanel(new BorderLayout());
		JPanel px2=new JPanel();
		px2.add(new JLabel("Name   : "));
		final JLabel na=new JLabel("                   ");
		px2.add(na);
		p2.add(px2,BorderLayout.NORTH);
		p1.add(p2);
		
		JPanel p3=new JPanel(new BorderLayout());
		JPanel px3=new JPanel();
		px3.add(new JLabel("Amount  :"));
		final JLabel ba=new JLabel("                   ");
		px3.add(ba);
		p3.add(px3,BorderLayout.NORTH);
		p2.add(p3);
		
		System.out.println("Hello");
		ta.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				try
				{	
					if(!ta.getText().equals("")){
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
						String s="select nme,balance from acnt where acc_no="+ta.getText();
						Statement stmt=con.createStatement();
					//	System.out.println("Hmm	1");
						//s=tn.getText();
						//stmt.setInt(1, Integer.parseInt(ta.getText()));
						ResultSet rs=stmt.executeQuery(s);
					//	System.out.println("Hmm2");
					//	System.out.println("Hmm");
						if(!rs.next())
						{
							System.out.println("hey");
							JOptionPane.showMessageDialog(n, "Requsted Account number does not exists","Wrong Entry!!!",JOptionPane.INFORMATION_MESSAGE);
							ta.setText("");
							stmt.close();
							con.close();
							del.setEnabled(false);
							return;
						}
						na.setText(rs.getString("nme"));
						ba.setText(rs.getString("balance"));
						stmt.close();
						con.close();
						del.setEnabled(true);
						//tn.setText("");
					}
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}
			
		});
		ta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
			}	
		});
		del.setEnabled(false);
		del.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{	
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
					String s="delete * from acnt where acc_no="+ta.getText();
					Statement stmt=con.createStatement();
					//System.out.println("Hmm	1");
					//s=tn.getText();
				//	System.out.println("Hmm2");
				//	System.out.println("Hmm");
					stmt.execute(s);
					JOptionPane.showMessageDialog(n, "Account Removed Successfuly!!","Success!!!",JOptionPane.INFORMATION_MESSAGE);
					stmt.close();
					con.close();
					//tn.setText("");
					ta.setText("");
					na.setText("");
					ba.setText("");
					del.setEnabled(false);
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}
		});
		JButton cn=new JButton("Cancel");
		cn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ta.setText("");
				ba.setText("");
				na.setText("");
			}
		});
		JButton cl=new JButton("Close");
		cl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				n.setVisible(false);
				n.dispose();
			}
		});
		JPanel p4=new JPanel();
		p4.add(del);
		p4.add(cn);
		p4.add(cl);
		p3.add(p4);
		n.add(p1);
		n.setBounds(130,130,300,200);
		n.setVisible(true);
		dp.add(n);
	}
	
	private void deposit()
	{
		dflag=false;
		final JButton up=new JButton("Update");
		final JInternalFrame n=new JInternalFrame("Deposit Amount");
		JPanel p1=new JPanel(new BorderLayout());
		JPanel px1=new JPanel(new FlowLayout());
		px1.add(new JLabel("Account No.: "));
		final JTextField an=new JTextField(10);
		px1.add(an);
		p1.add(px1,BorderLayout.NORTH);
		
		JPanel p2=new JPanel(new BorderLayout());
		JPanel px2=new JPanel();
		px2.add(new JLabel("Name   : "));
		final JLabel na=new JLabel("                   ");
		px2.add(na);
		p2.add(px2,BorderLayout.NORTH);
		p1.add(p2);
		
		JPanel p3=new JPanel(new BorderLayout());
		JPanel px3=new JPanel();
		px3.add(new JLabel("Amount  :"));
		final JLabel ba=new JLabel("                   ");
		px3.add(ba);
		p3.add(px3,BorderLayout.NORTH);
		p2.add(p3);
		
		JPanel p4=new JPanel(new BorderLayout());
		JPanel px4=new JPanel();
		px4.add(new JLabel("Amount : "));
		final JTextField am=new JTextField(10);
		px4.add(am);
		p4.add(px4,BorderLayout.NORTH);
		p3.add(p4);
		
		am.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
							if(!"".equals(an.getText()))
								up.setEnabled(true);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		System.out.println("Hello");
		an.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e)
			{
				try
				{	
					if(!(an.getText().matches("[^0-9]*"))){
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
						String s="select nme,balance from acnt where acc_no="+an.getText();
						Statement stmt=con.createStatement();
					//	System.out.println("Hmm	1");
						//s=tn.getText();
						//stmt.setInt(1, Integer.parseInt(ta.getText()));
						ResultSet rs=stmt.executeQuery(s);
					//	System.out.println("Hmm2");
					//	System.out.println("Hmm");
						if(!rs.next())
						{
							System.out.println("hey");
							JOptionPane.showMessageDialog(n, "Requsted Account number does not exists","Wrong Entry!!!",JOptionPane.INFORMATION_MESSAGE);
							an.setText("");
							stmt.close();
							con.close();
							up.setEnabled(false);
							return;
						}
						na.setText(rs.getString("nme"));
						ba.setText(rs.getString("balance"));
						stmt.close();
						con.close();
						if(!"".equals(am.getText()))
							up.setEnabled(true);
						//tn.setText("");
					}
					else
						JOptionPane.showMessageDialog(n, "Only Integers are allowed as Account number","Wrong Entry!!!",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		up.setEnabled(false);
		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{	
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
					String s="update acnt set balance=? where acc_no=?";
					PreparedStatement stmt=con.prepareStatement(s);
					//System.out.println("Hmm	1");
					//s=tn.getText();
				//	System.out.println("Hmm2");
				//	System.out.println("Hmm");
					double l=Double.parseDouble(am.getText());
					double q=Double.parseDouble(ba.getText());
					stmt.setString(1, (q+l)+"");
					stmt.setString(2,an.getText());
					stmt.execute();
					JOptionPane.showMessageDialog(n, "Account "+an.getText()+" Updated Successfuly!!","Success!!!",JOptionPane.INFORMATION_MESSAGE);
					stmt.close();
					con.close();
					//tn.setText("");
					an.setText("");
					na.setText("");
					ba.setText("");
					am.setText("");
					up.setEnabled(false);
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}
		});
		JButton cn=new JButton("Cancel");
		cn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				an.setText("");
				ba.setText("");
				na.setText("");
				am.setText("");
			}
		});
		JButton cl=new JButton("Close");
		cl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				n.setVisible(false);
				n.dispose();
			}
		});
		JPanel p5=new JPanel();
		p5.add(up);
		p5.add(cn);
		p5.add(cl);
		p4.add(p5);
		n.add(p1);
		n.setBounds(130,130,300,200);
		n.setVisible(true);
		dp.add(n);
	}
	
	private void withdraw()
	{
		wflag=false;
		final JButton w=new JButton("Withdraw");
		final JInternalFrame n=new JInternalFrame("Withdraw Amount");
		JPanel p1=new JPanel(new BorderLayout());
		JPanel px1=new JPanel(new FlowLayout());
		px1.add(new JLabel("Account No.: "));
		final JTextField an=new JTextField(10);
		px1.add(an);
		p1.add(px1,BorderLayout.NORTH);
		
		JPanel p2=new JPanel(new BorderLayout());
		JPanel px2=new JPanel();
		px2.add(new JLabel("Name   : "));
		final JLabel na=new JLabel("                   ");
		px2.add(na);
		p2.add(px2,BorderLayout.NORTH);
		p1.add(p2);
		
		JPanel p3=new JPanel(new BorderLayout());
		JPanel px3=new JPanel();
		px3.add(new JLabel("Amount  :"));
		final JLabel ba=new JLabel("                   ");
		px3.add(ba);
		p3.add(px3,BorderLayout.NORTH);
		p2.add(p3);
		
		JPanel p4=new JPanel(new BorderLayout());
		JPanel px4=new JPanel();
		px4.add(new JLabel("Amount : "));
		final JTextField am=new JTextField(10);
		px4.add(am);
		p4.add(px4,BorderLayout.NORTH);
		p3.add(p4);
		
		am.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
							if(!"".equals(an.getText()))
								w.setEnabled(true);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		System.out.println("Hello");
		an.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e)
			{
				try
				{	
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
					String s="select nme,balance from acnt where acc_no="+an.getText();
					Statement stmt=con.createStatement();
				//	System.out.println("Hmm	1");
					//s=tn.getText();
					//stmt.setInt(1, Integer.parseInt(ta.getText()));
					ResultSet rs=stmt.executeQuery(s);
				//	System.out.println("Hmm2");
				//	System.out.println("Hmm");
					if(!rs.next())
					{
						System.out.println("hey");
						JOptionPane.showMessageDialog(n, "Requsted Account number does not exists","Wrong Entry!!!",JOptionPane.INFORMATION_MESSAGE);
						an.setText("");
						stmt.close();
						con.close();
						w.setEnabled(false);
						return;
					}
					na.setText(rs.getString("nme"));
					ba.setText(rs.getString("balance"));
					stmt.close();
					con.close();
					if(!"".equals(am.getText()))
						w.setEnabled(true);
					//tn.setText("");
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		w.setEnabled(false);
		w.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{	
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					Connection con=DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=account.mdb");
					String s="update acnt set balance=? where acc_no=?";
					PreparedStatement stmt=con.prepareStatement(s);
					//System.out.println("Hmm	1");
					//s=tn.getText();
				//	System.out.println("Hmm2");
				//	System.out.println("Hmm");
					double x=Double.parseDouble(ba.getText()),y=Double.parseDouble(am.getText()),z;
					if(x>y)
						z=x-y;
					else
					{
						JOptionPane.showMessageDialog(n, "Account "+an.getText()+" Does not have enoungh amount for the Requested Transaction!!","You Can not Proceed!!!",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					stmt.setString(1,z+"");
					stmt.setString(2,an.getText());
					stmt.execute();
					JOptionPane.showMessageDialog(n, "Account "+an.getText()+" Updated Successfuly!!","Success!!!",JOptionPane.INFORMATION_MESSAGE);
					stmt.close();
					con.close();
					//tn.setText("");
					an.setText("");
					na.setText("");
					ba.setText("");
					am.setText("");
					w.setEnabled(false);
				}
				catch(Exception a)
				{
					System.out.println("SQL Trouble");
					a.printStackTrace();
				}
			}
		});
		JButton cn=new JButton("Cancel");
		cn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				an.setText("");
				ba.setText("");
				na.setText("");
				am.setText("");
			}
		});
		JButton cl=new JButton("Close");
		cl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				n.setVisible(false);
				n.dispose();
			}
		});
		JPanel p5=new JPanel();
		p5.add(w);
		p5.add(cn);
		p5.add(cl);
		p4.add(p5);
		n.add(p1);
		n.setBounds(130,130,300,200);
		n.setVisible(true);
		dp.add(n);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JMenuItem m=(JMenuItem)arg0.getSource();
		if(m==nw)
		{
			newAccount();
		}
		if(m==cls)
		{
			closeAccount();
		}
		if(m==ext)
		{
			f.setVisible(false);
			f.dispose();
			System.exit(1);
		}
		if(m==dpst)
		{
			deposit();
		}
		if(m==wthdrw)
		{
			withdraw();
		}
	}

}
