package mypack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class myFrame{

	private myFrame myframe;
    private JPanel p;
	private JFrame frame;
	private Database mainDB;
	private Stack<Database> filtDB;
	private ArrayList<Filter> allFilter;
	private int filesCounter;
	private JScrollPane scrollPane;
	private JLabel lblNumberOfMacs,lblNumberOfLines;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					myFrame window = new myFrame();
					window.frame.setVisible(true);
					window.setMyframe(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	    
	
	}

	public void setMyframe(myFrame m){
		myframe =m;
	}
	
	/**
	 * Create the application.
	 */
	public myFrame()  {
		filtDB = new Stack<Database>();
		allFilter = new ArrayList<Filter>();
		initialize();
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()   {
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1800, 1600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblNumberOfLines = new JLabel("Number of lines: 0");
		lblNumberOfLines.setBounds(890, 1332, 230, 33);
		panel.add(lblNumberOfLines);
		
		lblNumberOfMacs = new JLabel("Number of Macs: 0");
		lblNumberOfMacs.setBounds(890, 1398, 230, 33);
		panel.add(lblNumberOfMacs);
		
		JButton btnCreateCsv = new JButton("Create csv");
		btnCreateCsv.setBounds(456, 1332, 372, 99);
		btnCreateCsv.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel.add(btnCreateCsv);
		
		JButton btnCreateKml = new JButton("Create KML");
		btnCreateKml.setBounds(26, 1332, 372, 99);
		btnCreateKml.setFont(new Font("Tahoma", Font.PLAIN, 36));
		panel.add(btnCreateKml);
		
		JButton btnAddFilter = new JButton("Add filter");
		btnAddFilter.setBounds(1387, 1194, 308, 81);
		btnAddFilter.setFont(new Font("Tahoma", Font.PLAIN, 36));
		btnAddFilter.setForeground(Color.BLACK);
		btnAddFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				FilterFrame f = new FilterFrame(myframe);
				f.setVisible(true);
				
			}
		});
		panel.add(btnAddFilter);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(1339, 28, 403, 1126);
		panel.add(scrollPane);
		
		
		JLabel lblNewLabel = new JLabel("Filters:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		p = new JPanel();
		p.setBounds(11, 28, 1302, 1247);
		p.setLayout(null);
		display();
		panel.add(p);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		menuBar.add(Box.createHorizontalGlue());
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u05D4\u05D5\u05E1\u05E4\u05D4");
		menu.setFont(new Font("Segoe UI", Font.PLAIN, 40));
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("\u05D4\u05D5\u05E1\u05E3 \u05E7\u05D5\u05D1\u05E5");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "CSV files", "csv");
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(menuItem);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					addFile(chooser.getSelectedFile().getPath());
					display();
				}
				
			}
		});
		menuItem.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		menu.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u05D4\u05D5\u05E1\u05E3 \u05EA\u05D9\u05E7\u05D9\u05D9\u05D4");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "CSV files", "csv");
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(menuItem);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					
					try 
					{
						ArrayList<String>paths = getAllPaths(chooser.getSelectedFile().getPath());
						for (int i = 0; i < paths.size(); i++) {
							addFile(paths.get(i));
						}
						display();
					} 
					catch (DataException e1) {
						
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		
		menuItem_1.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		menu.add(menuItem_1);
		
		
		
	}
	
	
	private void displayFilters(){
		
		for (int i = 0; i < allFilter.size(); i++) {
			  if(i+1 == allFilter.size()){
				    
			  }
		}
	}
	
	
	public void addFilter(Filter f){
		allFilter.add(f);
		Database hold = filtDB.peek();
		ArrayList<WifiSpots> newlist = new ArrayList<WifiSpots>();
		for (int i = 0; i < hold.getDB().size() ; i++) {
			  if(f.Filt(hold.getDB().get(i))){
				  newlist.add(hold.getDB().get(i));
			  }
		}
		Database newDb = new Database(newlist);
		filtDB.push(newDb);
		display();
		displayFilters();
	}
	
	private void display(){
		
		Database hold = null;
		String[] columns = {"Time","ID","Lat","Lon","Alt","#WiFi networks","SSID1","MAC1","Frequancy1","Signal1","SSID2","MAC2","Frequancy2","Signal2","SSID3"
				 ,"MAC3","Frequancy3","Signal3","SSID4","MAC4","Frequancy4","Signal4","SSID5","MAC5","Frequancy5","Signal5"
				,"SSID6","MAC6","Frequancy6","Signal6","SSID7","MAC7","Frequancy7","Signal7"
				,"SSID8","MAC8","Frequancy8","Signal8","SSID9,MAC9","Frequancy9","Signal9","SSID10","MAC10","Frequancy10","Signal10"};
		
		int rows = 0;
		int macs =0;
		if(filesCounter !=0){
			hold = filtDB.peek();
			rows = hold.getDB().size();
			macs = hold.getMacim().size();
		}
		String[][]  data = new String[rows][46];
		
		for (int i = 0; i < rows; i++) {
			  data[i][0] = hold.getDB().get(i).getFirstSeen();
			  data[i][1] = hold.getDB().get(i).getID();
			  data[i][2] = hold.getDB().get(i).getLatitude();
			  data[i][3] = hold.getDB().get(i).getLongtitude();
			  data[i][4] = hold.getDB().get(i).getAltitude();
			  data[i][5] = String.valueOf(hold.getDB().get(i).getSpots().size());
			  
			  int w = 6;
			  for (int j = 0; j < hold.getDB().get(i).getSpots().size(); j++) {
				   
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getSsid();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getMac();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getChanel();w++;
				   data[i][w] = hold.getDB().get(i).getSpots().get(j).getRssi();w++;	 
			}
		}
		
		lblNumberOfLines.setText("Number of Lines: "+ rows);
		lblNumberOfMacs.setText("Number of Macs: "+ macs);
		JTable table = new JTable(data, columns);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(0, 0, 1302, 1247);
		p.removeAll();
		p.add(scrollPane);
	}
	
	private ArrayList<String> getAllPaths(String path)  throws DataException{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> paths=new ArrayList<String>();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()){
				
					String str = listOfFiles[i].getPath();
					paths.add(str);
				
			}

		} 
		if(paths.size()==0){
			throw new DataException("No currect input files");
		}
		return paths;
	}
	
	private void updateStack(){
		
		filtDB = new Stack<Database>();
		filtDB.push(mainDB);
		for (int i = 0; i < allFilter.size(); i++) {
			  Database peek = filtDB.pop();
			  ArrayList<WifiSpots> newarray = new ArrayList<WifiSpots>();
			  for (int j = 0; j < peek.getDB().size(); j++) {
				   if(allFilter.get(i).Filt(peek.getDB().get(j))){
					   newarray.add(peek.getDB().get(j));
				   }
			  }
			  Database newdb = new Database(newarray);
			  filtDB.push(newdb);
		}
	}
	
	private void addFile(String path){
		  Csv myfileC=new Csv();
		  if(myfileC.hasRightFormat(path)){
			   
		     try {
		    	 Csv myfile = new Csv(path);
				  
			     if(filesCounter == 0) {mainDB = new Database(myfile.getLittleDB());filtDB.push(mainDB);}
				 else {
					 mainDB.addToDB(myfile.getLittleDB());
					 if(allFilter.size() > 0) updateStack();
					 else{ filtDB.pop(); filtDB.push(mainDB);}
			      }
			 }
			 catch (DataException e1) {
				e1.printStackTrace();
			 }    
		  }
		  else{
			  if(filesCounter == 0){ mainDB = new Database(path,"WifiSpots");filtDB.push(mainDB);}
			  else{
				  mainDB.add(path);
				  if(allFilter.size() > 0) updateStack();
				  else{ filtDB.pop(); filtDB.push(mainDB);}
			  }
		  }
		  filesCounter++;
	}
}
