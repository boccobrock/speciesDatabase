package species;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;

public class BrowseSpecies extends JFrame implements ActionListener
{
    JLabel loadLabel;
    JButton viewInfo, quit, delete, find, print, imageb;
    JTextField findT;
    JScrollPane scrollPane;
    ImageIcon picture;
    boolean loading=false;
    public int selectedRow, totalRows;
    public int spIndex[] = new int[200];
    public String wds[] =new String[200], picData[] = new String[1000];
    public Object[][] pData;
    public ArrayList<DataSpeciesRecord> speciesRecord;
    public String picStr = "";
    public JTable table;

    public BrowseSpecies()
    {
    	//The construct
        prepareData();
        
        this.setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(650,410);
        setLocation(200,150);
        setTitle("Browse Species Database");
        getContentPane().setLayout(null);

        viewInfo = new JButton("View/Edit Info");
        viewInfo.setForeground(new Color(50,100,200));
        viewInfo.setBounds(320,10,150,44);
        getContentPane().add(viewInfo);
        viewInfo.addActionListener(this);

        delete = new JButton("Delete");
        delete.setForeground(new Color(50,100,200));
        delete.setBounds(320,56,150,44);
        getContentPane().add(delete);
        delete.addActionListener(this);

        find = new JButton("Find");
        find.setForeground(new Color(50,100,200));
        find.setBounds(474,10,150,44);
        getContentPane().add(find);
        find.addActionListener(this);

        quit = new JButton("Quit");
        quit.setForeground(new Color(50,100,200));
        quit.setBounds(474,56,150,44);
        getContentPane().add(quit);
        quit.addActionListener(this);

        print = new JButton("Print");
        print.setForeground(new Color(50,100,200));
        print.setBounds(396,102,150,44);
        getContentPane().add(print);
        print.addActionListener(this);

        loadLabel = new JLabel();
        loadLabel.setBounds(350,200,24,24);
        getContentPane().add(loadLabel);

        pData = new Object[speciesRecord.size()][2];
        for(int sr=0; sr<speciesRecord.size(); sr++)
        {
            DataSpeciesRecord dsr = new DataSpeciesRecord();
            dsr = speciesRecord.get(sr);
            spIndex[sr] = dsr.indexNum;
            pData[sr][0] = dsr.speciesName;
            pData[sr][1] = dsr.scientificName;
            picData[sr] = dsr.img;
        }
        
        //Custom table initialization
        
        table = new JTable(new CreateTableModel(pData));

        (table.getColumnModel().getColumn(0)).setPreferredWidth(170);
        (table.getColumnModel().getColumn(1)).setPreferredWidth(182);
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(10,10,300,360);
        table.setPreferredScrollableViewportSize(new Dimension(400, 100));

        getContentPane().add(scrollPane1);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.removeIndexInterval((totalRows+1),200);
        rowSM.addListSelectionListener(new ListSelectionListener() 
        {
            public void valueChanged(ListSelectionEvent e) 
            {
                if (e.getValueIsAdjusting()) return;
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.isSelectionEmpty())
                {
                    loading = true;
                    selectedRow = 1;
                    // System.out.println(spIndex[selectedRow]);
                    picStr = "" + picData[selectedRow];
                    reDrawImage();
                }
                else
                {
                    loading = true;
                    selectedRow = lsm.getMinSelectionIndex();
                    // System.out.println(spIndex[selectedRow]);
                    picStr = "" + picData[selectedRow];
                    reDrawImage();
                }
            }
        });
        
        imageb=new JButton();
        imageb.setBounds(324,150,304,220);
        imageb.setBorder(BorderFactory.createEmptyBorder());
        imageb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        getContentPane().add(imageb);
        imageb.addActionListener(this);
        imageb.setVisible(false);

        setVisible(true);
    }

    public void actionPerformed (ActionEvent evt)
    {
        if(evt.getActionCommand().equals("Quit"))
        {
            new Species();
            this.dispose();
        }
        else if(evt.getActionCommand().equals("View/Edit Info"))
        {
            new EditSpecies(spIndex[selectedRow]);
            this.dispose();
        }
        else if(evt.getActionCommand().equals("Delete"))
        {
            System.out.println("index: "+spIndex[selectedRow]);
            deleteSpecies(spIndex[selectedRow]);
            this.dispose();
        }
        else if(evt.getSource() == imageb)
        {
            new ViewWindow(picStr);
        }
        else if(evt.getActionCommand().equals("Find"))
        {
            String testStr = JOptionPane.showInputDialog(findT,"Enter name to find","Find", 3);
            if(testStr == null)
                testStr="null";
            else if(testStr.compareTo("")==0)
                JOptionPane.showMessageDialog(this,"Not a vaild search string","Error",JOptionPane.ERROR_MESSAGE);
            else
            {
                ListSelectionModel rowSM = table.getSelectionModel();
                String testArr[] = new String[100];
                String testArr2[] = new String[100];
                String testStrB ="";
                StringTokenizer st, st2;
                for(int n=0;n<totalRows;n++)
                {
                    testStrB = (String)pData[n][0];
                    testArr[n] = testStrB;
                }
                for(int n=0;n<totalRows;n++)
                {
                    testStrB = (String)pData[n][1];
                    testArr2[n] = testStrB;
                }
                for(int m=0;m<totalRows;m++)
                {
                    st = new StringTokenizer(testArr[m]," ");
                    int totalTokens = st.countTokens();
                    for(int p=0;p<totalTokens;p++)
                    {
                        if(testStr.compareToIgnoreCase(st.nextToken())==0)
                        {
                            rowSM.setSelectionInterval(m,m);
                        }
                    }
                    st2 = new StringTokenizer(testArr2[m]," ");
                    int totalTokens2 = st2.countTokens();
                    for(int p=0;p<totalTokens2;p++)
                    {
                        if(testStr.compareToIgnoreCase(st2.nextToken())==0)
                        {
                            rowSM.setSelectionInterval(m,m);
                        }
                    }
                }
                 EventQueue.invokeLater(new Runnable() 
                 {
                     public void run() 
                     {
                         reDrawImage();
                     }
                 });
            }
        }
    }

    public void prepareData()
    {
        int index=0;
        speciesRecord = new ArrayList<DataSpeciesRecord>();
        StringTokenizer st;
        try{
            BufferedReader reader = new BufferedReader ( new FileReader("data/database.txt"));
            String input = "";
            while( (input = reader.readLine()) != null)
            {
                wds[index++] = input;
                st = new StringTokenizer(input,"\t");
                DataSpeciesRecord ds = new DataSpeciesRecord();
                ds.indexNum = Integer.parseInt(st.nextToken());
                ds.speciesName = st.nextToken();
                ds.scientificName = st.nextToken();
                ds.img = st.nextToken();
                speciesRecord.add(ds);
            }
            reader.close();
            totalRows=index;
        }catch(Exception e){System.out.println(""+e);}
        Collections.sort(speciesRecord, new FirstNameComparator());
    }

    public void reDrawImage()
    {
        try{
            Image img1 = ImageIO.read(new File(picStr));
            Image img2 = img1.getScaledInstance(304,220,1);
            picture = new ImageIcon(img2);
            imageb.setIcon(picture);
        }
        catch(Exception e)
        {System.out.println ( "Invalid Image File\t"+e);}
        imageb.setVisible(true);
    }
    
    public void deleteSpecies(int delNum)
    {
        String wds1[] =new String[200];
        String testStr="";
        int index=0;
        try{
        BufferedReader reader = new BufferedReader ( new FileReader("data/database.txt"));
        String input = "";
        while( (input = reader.readLine()) != null){
            wds1[index++] = input;}
        reader.close();
        BufferedWriter out = new BufferedWriter(new FileWriter("data/database.txt"));
        int i=1;
        if((delNum-1)==0){
            out.write(wds1[1]);
            i++;}
        else
            out.write(wds1[0]);
        for(;i<index; i++){
            if(i<(index-1))
                out.newLine();
            testStr="";
            for(int k=0; k<10;k++)
            {
                if(wds1[i].charAt(k) == 9)
                    break;
                testStr += wds1[i].charAt(k);
            }
            //System.out.println(testStr+"\t"+delNum);
            if(delNum==Integer.parseInt(testStr)){
                System.out.println("Skipping: "+(i));
                i++;}
            if(i<index){
                out.write(wds1[i]);
                System.out.println("Writing: "+(i));}
            }
            out.close();
        }catch(Exception e){System.out.println(""+e);}
        new BrowseSpecies();
    }
}

class CreateTableModel extends AbstractTableModel
{
    private String[] columnNames = {"Name",
                        "Scientific Name"};
    private Object[][] data = {
    {"temp", "temp2"}};

    public CreateTableModel(Object[][] nData)
    {
        data = nData;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) 
    {
        return data[row][col];
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Class getColumnClass(int c) 
    {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}