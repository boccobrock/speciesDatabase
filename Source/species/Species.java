/**
 * Species - Animal Information database
 * 
 * Shows the representation and creation of a database, in this case to record information about species of animals.
 * 
 * @author Jeffrey Brock
 */

package species;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Species extends JFrame implements ActionListener
{
	JButton add, browse, quit;
	public Species()
	{
		this.setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300,200);
		setLocation(350,150);
		setTitle("Species Database");
		getContentPane().setLayout(null);

		add = new JButton("Add Species");
		add.setForeground(new Color(50,100,200));
		add.setBounds(10,40,130,60);
		getContentPane().add(add);
		add.addActionListener(this);

		browse = new JButton("Browse Species");
		browse.setForeground(new Color(50,100,200));
		browse.setBounds(150,40,130,60);
		getContentPane().add(browse);
		browse.addActionListener(this);

		quit = new JButton("Quit");
		quit.setForeground(new Color(50,100,200));
		quit.setBounds(70,120,140,40);
		getContentPane().add(quit);
		quit.addActionListener(this);

		setVisible(true);
	}

	public void actionPerformed (ActionEvent evt)
	{
		if(evt.getSource() == add)
		{
			new AddSpecies();
			this.dispose();
		}
		else
		if(evt.getSource() == browse)
		{
			new BrowseSpecies();
			this.dispose();
		}
		else
		if(evt.getActionCommand().equals("Quit"))
		{
		    //catch user pressing quit, confirm quit
			int choice = JOptionPane.showConfirmDialog( null,
			"You pressed "+evt.getActionCommand()+" are you sure?",
			"Close Frame",JOptionPane.YES_NO_OPTION);
			if ( choice == 0)
				this.dispose();
		}
	}

	public static void main(String args[])
	{
		/*	Change look and feel to the Substance Look and Feel,
		*  	Substance is used under its BSD license.
		*  	Substance: https://substance.dev.java.net/
		*/
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true); 
		try
		{
		      UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceModerateLookAndFeel");
		} catch (Exception e) 
		{
		      System.out.println("Substance failed to initialize");
		}
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	          Species s = new Species();
	          s.setVisible(true);
	        }
	      });
	}// end of main()
}