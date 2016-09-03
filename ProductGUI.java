package finalProject;
/*
 * Eric Mueller CS50x COURSE LaunchCode STL
 * student spring2016
 * program ProductGUI.java for PERSONAL PROJECT(Inventory Management Program)
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public class ProductGUI {

	private JFrame frmEmInventoryManagement;
	private DefaultListModel<String> listModel; // DefaultListModel<Product>
	private JList<DefaultListModel<Product>> list;
	private static List<Product> products_AL;   // this var is for receiving the ArrayList<Product> from readproducts method.
	
	
	/**
	 * Launch the application. MAIN
	 */
	public static void main(String[] args) throws Exception {
		/* fetches from SQLDB and returns data in an ArrayList.
		 * I use this at the initialize method line69
		 */
		products_AL = new ArrayList<Product>();
		AccessProductDB db = new AccessProductDB();
		db.connectToDB();
		products_AL = db.readProducts();
		db.close();
		
		EventQueue.invokeLater(new Runnable() {  // Eric learn what exactly this does.  So I know where I can place code in my MAIN.
			public void run() {
				try {
					ProductGUI window = new ProductGUI();
					window.frmEmInventoryManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProductGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEmInventoryManagement = new JFrame();
		frmEmInventoryManagement.getContentPane().setBackground(new Color(230, 230, 250));
		frmEmInventoryManagement.setBackground(Color.lightGray);
		frmEmInventoryManagement.setTitle("EM Inventory Management Program");
		frmEmInventoryManagement.setBounds(100, 100, 1050, 472);
		frmEmInventoryManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEmInventoryManagement.getContentPane().setLayout(null);
		
		
		// ***** Displays the list of product items ******
		
		listModel = new DefaultListModel<String>();
		for(int i = 0; i < products_AL.size(); i++) {  // convert the ArrayList from DB to a DLM listModel for GUI compatible
			listModel.add(i, products_AL.get(i).getvItemName()); // CHANGED to just save names, not Product objects ******
		}
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.white);
		list.setBounds(426, 11, 510, 189);
		list.setVisibleRowCount(8);
//		JScrollPane listScroller = new JScrollPane(list);
//		//listScroller.setPreferredSize(new Dimension(426, 11));
//		listScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		frmEmInventoryManagement.getContentPane().add(list); // ERIC how to list only names or ID's of product(did when build listmodel)
		
		
		//************ BUTTON [create new product] ******** (SQL checks good! update with additional data model) *****************
		JButton btnCreateNewProduct = new JButton("Create New Product");
		btnCreateNewProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// int selected = list.getSelectedIndex();
				String name = (String)JOptionPane.showInputDialog(
						frmEmInventoryManagement, "Please enter a name for new Product: ",
						"Name Dialog", JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						"");
				int quant = (int)getPositiveValue("How many product items?", "Quantity dialog");
				float cost = (float)getPositiveValue("Cost amount for ea. item?", "Cost dialog");
				float price = (float)getPositiveValue("What is retail price?", "Price dialog");
				AccessProductDB toDB = new AccessProductDB();
				try {
					toDB.connectToDB();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					toDB.writeProducts(name, quant);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				toDB.close();
				// old code. Eric dispose of soon
//				Product p = new Product(name, quant, cost, price);  / this changed from making a object product to entering in SQL
//				listModel.add(listModel.size(), p);
			}
		});
		btnCreateNewProduct.setBounds(6, 48, 195, 29);
		frmEmInventoryManagement.getContentPane().add(btnCreateNewProduct);
		
		
		// ************  BUTTON [display product info] ******* (SQL DB all works!) ******************
		JButton btnNewButton = new JButton("Display Product Information");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int selected = list.getSelectedIndex();
				infoLabel(selected);  // Call to Erics method to display info in box *****************	
				}
				catch(Exception x) {
					System.out.println("Sorry, there must have not been a product selected or created!");
				}
			}
		});
		btnNewButton.setBounds(6, 89, 195, 29);
		frmEmInventoryManagement.getContentPane().add(btnNewButton);
		

	
		// ********* BUTTON [change price] *********** (SQL Checks OK!) ***************
		JButton btnNewButton_1 = new JButton("Change Price");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = list.getSelectedIndex();
				float price = (float)getPositiveValue("What is the new price? $", "price dialog");
				//listModel.get(selected).setvRetailPrice(price);
				products_AL.get(selected).setvRetailPrice(price); // change price in ArrayList
				System.out.println("DebugTESTER for correct AL Change new price is:" + products_AL.get(selected).getvRetailPrice());
				AccessProductDB toDB = new AccessProductDB();
				try {
					toDB.connectToDB();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					toDB.writeProdPrice(products_AL.get(selected).getvItemName(), price);
				} catch (Exception e2) {
					// TODO catch block
					System.out.println(e2 + "tester EXCEPTION THROWN at E2");
					e2.printStackTrace();
				}
				toDB.close();
			}
		});
		btnNewButton_1.setBounds(6, 130, 195, 29);
		frmEmInventoryManagement.getContentPane().add(btnNewButton_1);
		
		
		// *********** BUTTON [get profit MARGIN] ************(SQL Checks OK!) ***********
		JButton btnNewButton_2 = new JButton("Get Profit Margin");
		JLabel lblMmargin = new JLabel("Margins for this Product:"); // trying this outside of listener
		btnNewButton_2.setBackground(new Color(192, 192, 192));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = list.getSelectedIndex();
				lblMmargin.repaint(); // refresh the JLabel
				lblMmargin.setBorder(new LineBorder(new Color(0, 0, 0), 2));
				lblMmargin.setBounds(213, 340, 173, 40);
				float margin = ((products_AL.get(selected).getvRetailPrice())-(products_AL.get(selected).getvCost()));
				String s = Float.toString(margin);
				lblMmargin.setText("P Margin:$"+s);
				frmEmInventoryManagement.getContentPane().add(lblMmargin);
			}
		});
		btnNewButton_2.setBounds(6, 171, 195, 29);
		frmEmInventoryManagement.getContentPane().add(btnNewButton_2);
		
		
		// ************* BUTTON [exit]************** (all works!) ***************
		JButton btnNewButton_3 = new JButton("Exit Program"); // ERIC NOTE: this is a quick way to exit/ much discussion on SO; maybe use window listener
		btnNewButton_3.setBackground(Color.RED);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("No you cannot go! you must continue working!! work, work, work!");
				System.exit(0);
			}
		});
		btnNewButton_3.setBounds(6, 398, 195, 29);
		frmEmInventoryManagement.getContentPane().add(btnNewButton_3);
		
		
		
		// ************** JLable that holds PRODUCT IMAGE JPG file *******(works! still need to implement refresh with new image) **********
		JLabel lblNewLabel = new JLabel("Product Image");
		lblNewLabel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		lblNewLabel.setIcon(new ImageIcon("/Users/ericmueller/Pictures/e_personal/Eric_book/photos_e_book/E_furniture/D03-14061_LoRes.jpg"));
		lblNewLabel.setBounds(213, 11, 189, 189);
		frmEmInventoryManagement.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Product Image");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(213, 199, 189, 23);
		frmEmInventoryManagement.getContentPane().add(lblNewLabel_1);
		
		
		
		
	}
	
	// ******** Information Box  METHOD Jlabel style box **************** (SQL DB checks good) ********
	JLabel lblndInfolabelbox= new JLabel();
		public void infoLabel(int x) { // NEW
			
		    lblndInfolabelbox.setText("Information for: " + products_AL.get(x)); // listModel.get(x) CHANGED lets see if works
			lblndInfolabelbox.setBorder(new LineBorder(new Color(0, 0, 0), 3));
			lblndInfolabelbox.setBounds(80, 234, 860, 80);
			lblndInfolabelbox.setBackground(Color.WHITE);
			frmEmInventoryManagement.getContentPane().add(lblndInfolabelbox);
			
			SwingUtilities.updateComponentTreeUI(frmEmInventoryManagement);  // This helps to refresh Jlabel so display and not double itself
			System.out.println("bigROgerRoger2");
			
			// Border around info Box  (ERIC TO TRASH THIS )
//			JPanel panel = new JPanel();
//			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GRAY, null, null, null));
//			panel.setBounds(207, 216, 343, 111);
//			frmEmInventoryManagement.getContentPane().add(panel);
//			panel.setLayout(null);
//			
//			JLabel lblInformationBox = new JLabel("Information Box" + listModel.get(x));
//			lblInformationBox.setBounds(6, 18, 400, 75);
//			panel.add(lblInformationBox);
//			lblInformationBox.setBackground(Color.blue);
			
//			JList list_1 = new JList(x);
//			list_1.setBounds(426, 236, 395, 83);
//			frmEmInventoryManagement.getContentPane().add(list_1);
		}
		
		private void refreshJlist() {
//			for(int i = 0; i < products_AL.size(); i++) {  // convert the ArrayList from DB to a DLM listModel for GUI compatible
//				listModel.add(i, products_AL.get(i).getvItemName()); // CHANGED to just save names, not Product objects ******
//			}
			// eleminate above after you have placed it inside the change price button
			list = new JList(listModel);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setBackground(Color.white);
			list.setBounds(426, 11, 510, 189);
			list.setVisibleRowCount(8);
			frmEmInventoryManagement.getContentPane().add(list);
		}
		
		private double getPositiveValue(String prompt, String title)
		{
			String s = (String)JOptionPane.showInputDialog(frmEmInventoryManagement,
					prompt, title, JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"");
			double sint = Double.parseDouble(s);
			while(sint < 0)
			{
				s = (String)JOptionPane.showInputDialog(frmEmInventoryManagement,
						prompt + "(Please enter a positive value)",
						title, JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						"");
				sint = Double.parseDouble(s);
			}
			return sint;
		}
}
