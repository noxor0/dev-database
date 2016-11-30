package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import client.*;

/**
 * A panel that contains all the Client related functionality. This includes:
 * Adding, Searching and Listing all current clients in the system.
 * @author concox
 *
 */
public class ClientGUI extends JPanel implements ActionListener,
TableModelListener {
	
	private static final long serialVersionUID = 1779525235233383929L;
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<Client> mList;
	private String[] mClientColumnNames = { "nameLast", "nameFirst", "middleInitial",
			"streetAddress", "city", "state", "zipcode" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddClient;

	/**
	 * Use this for Client administration. Add components that contain the list,
	 * search and add to this.
	 */
	public ClientGUI() {
		setLayout(new BorderLayout());

		mList = getData(null);
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}

	/*
	 * Returns the data (2d) to use in the list as well as the search panels.
	 * 
	 * @param title
	 * 
	 * @return
	 */
	private List<Client> getData(String searchKey) {
		if (searchKey != null) {
			String[] splitName = searchKey.split(" ");
			if (splitName.length == 1) {
				mList = ClientCollection.search(splitName[0]);				
			} else if (splitName.length == 2) {
				mList = ClientCollection.search(splitName[0], splitName[1]);				
			} else {
				JOptionPane.showMessageDialog(null, 
						"Enter First Name and/or Last Name");
			}
		} else {
			mList = ClientCollection.getClients();
		}

		if (mList != null) {
			mData = new Object[mList.size()][mClientColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getFirstName();
				mData[i][1] = mList.get(i).getLastName();
				mData[i][2] = mList.get(i).getMid();
				mData[i][3] = mList.get(i).getAddress();
				mData[i][4] = mList.get(i).getCity();
				mData[i][5] = mList.get(i).getState();
				mData[i][6] = mList.get(i).getZipcode();
			}
		}

		return mList;
	}

	/*
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	private void createComponents() {
		
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Client List");
		btnList.addActionListener(this);

		btnSearch = new JButton("Client Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Add Client");
		btnAdd.addActionListener(this);

		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);

		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);

		// List Panel
		pnlContent = new JPanel();
		table = new JTable(mData, mClientColumnNames);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);

		// Search Panel
		pnlSearch = new JPanel();
		lblTitle = new JLabel("Enter Name: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(lblTitle);
		pnlSearch.add(txfTitle);
		pnlSearch.add(btnTitleSearch);

		// Add Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(8, 0));
		String labelNames[] = {"Enter Full Name: (example: John B Howard)",
				"Enter Street Address: ", "Enter City", "Enter State: " ,
				"Enter zipcode:"};
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}

		JPanel panel = new JPanel();
		btnAddClient = new JButton("Add");
		btnAddClient.addActionListener(this);
		panel.add(btnAddClient);
		pnlAdd.add(panel);

		add(pnlContent, BorderLayout.CENTER);

	}

	/**
	 * Make the buttons work!
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnList) {
			mList = getData(null);
			pnlContent.removeAll();
			table = new JTable(mData, mClientColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();

		} else if (e.getSource() == btnSearch) {
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();

		} else if (e.getSource() == btnTitleSearch) {
			String title = txfTitle.getText();
			if (title.length() > 0) {
				mList = getData(title);
				pnlContent.removeAll();
				table = new JTable(mData, mClientColumnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
				txfTitle.setText("");
			}
		} else if (e.getSource() == btnAddClient) {
			performAddClient();
		}

	}

	/*
	 * Allows to add an Client. Only full name is required.
	 */
	private void performAddClient() {

		String fullName = txfField[0].getText();
		String[] nameSplit = fullName.split(" ");
		char[] midInitial = nameSplit[1].toCharArray();
		System.out.println(midInitial[0]);
		if (nameSplit[0].length() == 0 || nameSplit.length != 3) {
			JOptionPane.showMessageDialog(null, "Enter an correct client name");
			txfField[0].setFocusable(true);
			return;
		}
		String street = txfField[1].getText();
		if (street.length() == 0) {
			street = null;
		}
		String city = txfField[2].getText();
		if (city.length() != 0) {
			if (city.length() == 0) {
				city = null;
			}
		}
		String state = txfField[3].getText();
		if (state.length() != 0) {
			if (state.length() == 0) {
				state = null;
			}
		}
		String zipcode = txfField[4].getText();
		if (zipcode.length() != 0) {
			if (zipcode.length() == 0) {
				zipcode = null;
			}
		}
		Client client;
		if (street == null) {
			client = new Client(nameSplit[0], nameSplit[2], midInitial[0]);
		} else {
			client = new Client(nameSplit[0], nameSplit[2], midInitial[0],
					street, city, state, zipcode);
		}
		String message = "Client add failed";
		if (ClientCollection.add(client)) {
			message = "Client added";
		}
		JOptionPane.showMessageDialog(null, message);

		// Clear all text fields.
		for (int i = 0; i < txfField.length; i++) {
			if (txfField[i].getText().length() != 0) {
				txfField[i].setText("");
			}
		}
	}

	/**
	 * Listen to the cell changes on the table. 
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		if (data != null && ((String) data).length() != 0) {
			Client client = mList.get(row);
			if (!ClientCollection.update(client, columnName, data)) {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		}

	}
	
}
