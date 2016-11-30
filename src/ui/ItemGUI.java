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

import sale.*;

/**
 * A Panel that contains all the Item related functionality to 
 * list the items, search the items, add a new item, modify values within the item.
 * @author mabraham
 *
 */

public class ItemGUI extends JPanel implements ActionListener,
		TableModelListener {
	private static final long serialVersionUID = 1779520078061383929L;
	private JButton btnList, btnSearch, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private List<Item> mList;
	private String[] mItemColumnNames = { "name", "description", "price",
			"condition", "category" };

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTitle;;
	private JTextField txfTitle;
	private JButton btnTitleSearch;

	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCategories;
	private JButton btnAddItem;

	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public ItemGUI() {
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
	private List<Item> getData(String searchKey) {
		if (searchKey != null)
			mList = ItemCollection.search(searchKey);
		else
			mList = ItemCollection.getItems();

		if (mList != null) {
			mData = new Object[mList.size()][mItemColumnNames.length];
			for (int i = 0; i < mList.size(); i++) {
				mData[i][0] = mList.get(i).getName();
				mData[i][1] = mList.get(i).getDescription();
				mData[i][2] = mList.get(i).getPrice();
				mData[i][3] = mList.get(i).getCondition();
				mData[i][4] = mList.get(i).getItemCategory().getCategory();

			}
		}

		return mList;
	}

	/*
	 * Create the three panels to add to this GUI. One for list, one for search,
	 * one for add.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createComponents() {
		
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();
		btnList = new JButton("Item List");
		btnList.addActionListener(this);

		btnSearch = new JButton("Item Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Add Item");
		btnAdd.addActionListener(this);

		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);

		pnlButtons.add(btnAdd);
		add(pnlButtons, BorderLayout.NORTH);

		// List Panel
		pnlContent = new JPanel();
		table = new JTable(mData, mItemColumnNames);
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
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = { "Enter Name:", "Enter Description: ",
				"Enter Price: ", "Enter Condition: " };
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}

		// Get categories to display in the combo box
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(1, 1));
		Object[] categories = ItemCollection.getCategories();
		if (categories != null) {
			cmbCategories = new JComboBox(categories);
			cmbCategories.setSelectedIndex(0);
			comboPanel.add(new JLabel("Select category:"));
			comboPanel.add(cmbCategories);
			pnlAdd.add(comboPanel);
		}

		JPanel panel = new JPanel();
		btnAddItem = new JButton("Add");
		btnAddItem.addActionListener(this);
		panel.add(btnAddItem);
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
			table = new JTable(mData, mItemColumnNames);
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
				table = new JTable(mData, mItemColumnNames);
				table.getModel().addTableModelListener(this);
				scrollPane = new JScrollPane(table);
				pnlContent.add(scrollPane);
				pnlContent.revalidate();
				this.repaint();
				txfTitle.setText("");
			}
		} else if (e.getSource() == btnAddItem) {
			performAddItem();
		}

	}

	/*
	 * Allows to add an Item. Only name is required.
	 */
	private void performAddItem() {

		String name = txfField[0].getText();
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter an item name");
			txfField[0].setFocusable(true);
			return;
		}
		String desc = txfField[1].getText();
		if (desc.length() == 0) {
			desc = null;
		}
		String priceStr = txfField[2].getText();
		double price = 0.0;
		if (priceStr.length() != 0) {
			try {
				price = Double.parseDouble(priceStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter price as decimal");
				txfField[2].setText("");
				txfField[2].setFocusable(true);
				return;
			}
		}
		String condition = txfField[3].getText();
		if (condition.length() == 0) {
			condition = null;
		}
		String category = (String) cmbCategories.getSelectedItem();
		Item item;
		if (desc == null) {
			item = new Item(name, new ItemCategory(category));
		} else {
			item = new Item(name, desc, price, condition, new ItemCategory(
					category));
		}
		String message = "Item add failed";
		if (ItemCollection.add(item)) {
			message = "Item added";
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
			Item item = mList.get(row);
			if (!ItemCollection.update(item, columnName, data)) {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		}

	}

}
