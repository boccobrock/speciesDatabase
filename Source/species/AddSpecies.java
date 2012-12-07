package species;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.border.EtchedBorder;

public class AddSpecies extends JFrame implements ActionListener
{
	JLabel cNameLabel, sNameLabel, imageFileLabel, notesLabel, nAreaLabel;
	JTextField cName, sName, imageFile, nArea;
	JTextArea notes;
	JButton addDone, fileBrowse, quit, imageb;
	ImageIcon image;

	public AddSpecies()
	{
		//System.out.println("AddSpecies()");
		this.setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(540,400);
		setLocation(200,150);
		setTitle("Add Species to Database");
		getContentPane().setLayout(null);

		cNameLabel = new JLabel("Enter Common Name:");
		cNameLabel.setBounds(20,20,225,20);
		cNameLabel.setForeground(new Color(50,100,200));
		getContentPane().add(cNameLabel);

		cName = new JTextField(25);
		cName.setBounds(20,40,150,20);
		cName.setBackground(Color.white);
		getContentPane().add(cName);

		sNameLabel = new JLabel("Enter Scientific Name:");
		sNameLabel.setBounds(20,60,225,20);
		sNameLabel.setForeground(new Color(50,100,200));
		getContentPane().add(sNameLabel);

		sName = new JTextField(25);
		sName.setBounds(20,80,150,20);
		sName.setBackground(Color.white);
		getContentPane().add(sName);

		nAreaLabel = new JLabel("Enter Native Area:");
		nAreaLabel.setBounds(20,100,225,20);
		nAreaLabel.setForeground(new Color(50,100,200));
		getContentPane().add(nAreaLabel);

		nArea = new JTextField(25);
		nArea.setBounds(20,120,150,20);
		nArea.setBackground(Color.white);
		getContentPane().add(nArea);

		imageFileLabel = new JLabel("Enter Location of image file:");
		imageFileLabel.setBounds(20,140,225,20);
		imageFileLabel.setForeground(new Color(50,100,200));
		getContentPane().add(imageFileLabel);

		fileBrowse = new JButton("Browse");
		fileBrowse.setForeground(new Color(50,100,200));
		fileBrowse.setBounds(20,164,100,20);
		getContentPane().add(fileBrowse);
		fileBrowse.addActionListener(this);

		imageFile = new JTextField("Images/Zebra.jpg",25);
		imageFile.setBounds(20,192,150,20);
		imageFile.setBackground(Color.white);
		getContentPane().add(imageFile);
		imageFile.addActionListener(this);

		notesLabel = new JLabel("Enter Notes:");
		notesLabel.setBounds(20,220,225,20);
		notesLabel.setForeground(new Color(50,100,200));
		getContentPane().add(notesLabel);

		notes = new JTextArea("");
		notes.setBounds(20,240,260,120);
		notes.setEditable(true);
		notes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		notes.setForeground(new Color(50,100,200));
		getContentPane().add(notes);

		addDone = new JButton("Add Species");
		addDone.setForeground(new Color(50,100,200));
		addDone.setBounds(320,260,150,40);
		getContentPane().add(addDone);
		addDone.addActionListener(this);

		quit = new JButton("Cancel");
		quit.setForeground(new Color(50,100,200));
		quit.setBounds(320,310,150,40);
		getContentPane().add(quit);
		quit.addActionListener(this);
		
		try{
			Image img1 = ImageIO.read(new File(imageFile.getText()));
			Image img2 = img1.getScaledInstance(304,220,1);
			image = new ImageIcon(img2);
		}
		catch(Exception e)
		{System.out.println ( "Invalid Image File\t"+e);}
        
        imageb= new JButton();
        imageb.setBounds(200,10,304,220);
        imageb.setIcon(image);
        imageb.setBorder(BorderFactory.createEmptyBorder());
        imageb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getContentPane().add(imageb);
        imageb.addActionListener(this);

		setVisible(true);

		EventQueue.invokeLater(new Runnable(){
		    public void run() {
		        reDrawImage();
		    }
        });
	}

	public void actionPerformed (ActionEvent evt)
	{
		//System.out.println("actionPerformed()");
		if(evt.getSource() == fileBrowse)
		{
			JFileChooser chooser = new JFileChooser("Images/");
			chooser.showOpenDialog(this);
			try{
			imageFile.setText(chooser.getSelectedFile().getPath());
			}catch(Exception e){System.out.println("Cancel");}
			reDrawImage();
		}
		else
		if(evt.getSource() == addDone)
		{
			String wds[] =new String[200];
			int index=0;
			try{
			BufferedReader reader = new BufferedReader ( new FileReader("data/database.txt"));
			String input = "";
			while( (input = reader.readLine()) != null){
				wds[index++] = input;}
			reader.close();
			int highNum=0;
			String testStr="";
			for(int j=0;j<index;j++)
			{
				for(int k=0; k<10;k++)
				{
					if(wds[j].charAt(k) == 9)
						break;
					testStr += wds[j].charAt(k);
				}
				if(Integer.parseInt(testStr)>highNum)
				{
					highNum = Integer.parseInt(testStr);
				}
				testStr="";
			}
			//System.out.println("high: "+highNum);
			BufferedWriter out = new BufferedWriter(new FileWriter("data/database.txt"));
			out.write(wds[0]);
			for(int i=1; i<index; i++){
				out.newLine();
				out.write(wds[i]);}
			out.newLine();
			out.write((highNum+1)+"\t"+cName.getText()+"\t"+sName.getText()+"\t"+imageFile.getText()+"\t"+notes.getText()+"\t"+nArea.getText());
			out.close();
			}catch(Exception e){System.out.println(""+e);}
			new Species();
			this.dispose();
		}
		else
		if(evt.getSource() == imageFile)
		{
			reDrawImage();
		}
		else if(evt.getSource() == imageb)
        {
			new ViewWindow(imageFile.getText());
        }
		if(evt.getSource() == quit)
		{
			new Species();
			this.dispose();
		}
	}

	public void reDrawImage()
	{
		try{
			Image img1 = ImageIO.read(new File(imageFile.getText()));
			Image img2 = img1.getScaledInstance(304,220,1);
			image = new ImageIcon(img2);
			imageb.setIcon(image);
		}
		catch(Exception e)
		{System.out.println ( "Invalid Image File\t"+e);}
	}
}