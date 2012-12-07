package species;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.border.EtchedBorder;

public class EditSpecies extends JFrame implements ActionListener
{
    JLabel cNameLabel, sNameLabel, imageFileLabel, notesLabel, nAreaLabel;
    JTextField cName, sName, imageFile, nArea;
    JTextArea notes;
    JButton bDone, fileBrowse, quit, imageb;
    ImageIcon image;
    boolean pticon=false;
    public String nData="", snData="", picData="", notesData="", areaData="";
    public int selSpec, pIndexNum=0;

    public EditSpecies(int selected)
    {
        this.setResizable(false);
        selSpec=selected;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(540,400);
        setLocation(200,150);
        setTitle("View/Edit Species");
        getContentPane().setLayout(null);
        pIndexNum = selected;

        getData(selected);

        cNameLabel = new JLabel("Common Name:");
        cNameLabel.setBounds(20,20,225,20);
        cNameLabel.setForeground(new Color(50,100,200));
        getContentPane().add(cNameLabel);

        cName = new JTextField(nData);
        cName.setBounds(20,40,150,20);
        cName.setBackground(Color.white);
        getContentPane().add(cName);

        sNameLabel = new JLabel("Scientific Name:");
        sNameLabel.setBounds(20,60,225,20);
        sNameLabel.setForeground(new Color(50,100,200));
        getContentPane().add(sNameLabel);

        sName = new JTextField(snData);
        sName.setBounds(20,80,150,20);
        sName.setBackground(Color.white);
        getContentPane().add(sName);

        nAreaLabel = new JLabel("Native Area:");
        nAreaLabel.setBounds(20,100,225,20);
        nAreaLabel.setForeground(new Color(50,100,200));
        getContentPane().add(nAreaLabel);

        nArea = new JTextField(areaData);
        nArea.setBounds(20,120,150,20);
        nArea.setBackground(Color.white);
        getContentPane().add(nArea);

        imageFileLabel = new JLabel("Location of image file:");
        imageFileLabel.setBounds(20,140,225,20);
        imageFileLabel.setForeground(new Color(50,100,200));
        getContentPane().add(imageFileLabel);

        fileBrowse = new JButton("Browse");
        fileBrowse.setForeground(new Color(50,100,200));
        fileBrowse.setBounds(20,164,100,20);
        getContentPane().add(fileBrowse);
        fileBrowse.addActionListener(this);

        imageFile = new JTextField(picData);
        imageFile.setBounds(20,192,150,20);
        imageFile.setBackground(Color.white);
        getContentPane().add(imageFile);
        imageFile.addActionListener(this);

        notesLabel = new JLabel("Notes:");
        notesLabel.setBounds(20,220,225,20);
        notesLabel.setForeground(new Color(50,100,200));
        getContentPane().add(notesLabel);

        notes = new JTextArea(notesData);
        notes.setBounds(20,240,260,120);
        notes.setEditable(true);
        notes.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setForeground(new Color(50,100,200));
        getContentPane().add(notes);

        bDone = new JButton("Save Changes");
        bDone.setForeground(new Color(50,100,200));
        bDone.setBounds(320,260,150,40);
        getContentPane().add(bDone);
        bDone.addActionListener(this);

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

        EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                    reDrawImage();
                                }
            });
    }

    public void getData(int selectedNum)
    {
        String wds[] =new String[200];
        String specStr="";
        int index=0;
        try{
        BufferedReader reader = new BufferedReader ( new FileReader("data/database.txt"));
        String input = "";
        while( (input = reader.readLine()) != null){
            wds[index++] = input;}
        reader.close();
        }catch(Exception e){System.out.println("error "+e);}
        specStr=wds[selectedNum-1];
        int k=0;
        for(;;k++)
        {
            if(specStr.charAt(k) == 9)
                break;
        }
        k++;
        for(;;k++)
        {
            if(specStr.charAt(k) == 9)
                break;
            else
                nData += ""+specStr.charAt(k);
        }
        k++;
        for(;;k++)
        {
            if(specStr.charAt(k) == 9)
                break;
            else
                snData += ""+specStr.charAt(k);
        }
        k++;
        for(;;k++)
        {
            if(specStr.charAt(k) == 9)
                break;
            else
                picData += ""+specStr.charAt(k);
        }
        k++;
        for(;;k++)
        {
            if(specStr.charAt(k) == 9)
                break;
            else
                notesData += ""+specStr.charAt(k);
        }
        k++;
        for(;k<specStr.length();k++)
        {
            if(specStr.charAt(k) == 9)
                break;
            else
                areaData += ""+specStr.charAt(k);
        }
    }

    public void actionPerformed (ActionEvent evt)
    {
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
        if(evt.getSource() == bDone)
        {
            String wds[] =new String[200];
            int index=0;
            try{
            BufferedReader reader = new BufferedReader ( new FileReader("data/database.txt"));
            String input = "";
            while( (input = reader.readLine()) != null){
                wds[index++] = input;}
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter("data/database.txt"));
            out.write(wds[0]);
            int i=1;
            for(;i<(selSpec-1); i++){
                out.newLine();
                out.write(wds[i]);}
            out.newLine();
            out.write(pIndexNum+"\t"+cName.getText()+"\t"+sName.getText()+"\t"+imageFile.getText()+"\t"+notes.getText()+"\t"+nArea.getText());
            i++;
            for(;i<index; i++){
                out.newLine();
                out.write(wds[i]);}
            out.close();
            }catch(Exception e){System.out.println(""+e);}
            new BrowseSpecies();
            this.dispose();
        }
        else
        if(evt.getSource() == imageFile)
        {
            reDrawImage();
        }
        else if(evt.getSource() == quit)
        {
            new BrowseSpecies();
            this.dispose();
        }
        else if(evt.getSource() == imageb)
        {
            picData = imageFile.getText();
            new ViewWindow(picData);
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
    
    public void paint(Graphics g)
    {
        super.paint(g);/*
        try{
            image.paintIcon(this,g,200,40);
        }catch(NullPointerException e){System.out.println("Paint:NPE");}*/
    }
}