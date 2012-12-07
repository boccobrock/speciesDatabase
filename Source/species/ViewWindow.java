package species;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class ViewWindow extends JFrame
{
	public String pData;
	ImageIcon image;
	JButton imageb;
	
	public ViewWindow(String pictureData)
	{
		pData=pictureData;
		this.setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(840,660);
		setLocation(20,20);
		setTitle("View Image");
		getContentPane().setLayout(null);
		
		try{
            Image img1 = ImageIO.read(new File(pData));
            Image img2 = img1.getScaledInstance(800,600,1);
            image = new ImageIcon(img2);
        }
        catch(Exception e)
        {System.out.println ( "Invalid Image File\t"+e);}
        
        imageb= new JButton();
        imageb.setBounds(10,10,800,600);
        imageb.setIcon(image);
        imageb.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(imageb);
		
		setVisible(true);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				reDrawImage();
			}
		});
	}
	
	public void reDrawImage()
	{
		try{
			Image img1 = ImageIO.read(new File(pData));
			Image img2 = img1.getScaledInstance(800,600,1);
			image = new ImageIcon(img2);
			imageb.setIcon(image);
		}
		catch(Exception e)
		{System.out.println ( "Invalid Image File\t"+e);}
	}
}