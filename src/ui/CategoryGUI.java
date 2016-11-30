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
 * A penal that contains all the Category related functionality. This includes:
 * Displaying all categories, and adding new ones to the database.
 * @author concox
 *
 */
public class CategoryGUI extends JPanel implements ActionListener, TableModelListener{
	private static final long serialVersionUID = 1779520012425323529L;
	private JButton btnEdit, btnAdd;
	private JPanel pnlButtons, pnlContent;
	private String[] mList;
	private String[] mItemColumnNames = { "Category Name"};

	private Object[][] mData;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlAdd;
	private JLabel lblTitle;
	private JTextField txfTitle;
	private JButton btnNewCatName;

	private JPanel pnlEdit;
	private JLabel[] txfLabel = new JLabel[4];
	private JTextField[] txfField = new JTextField[4];
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCategories;
	private JButton btnEditCat;
	
	/**
	 * Use this for Item administration. Add components that contain the list,
	 * search and add to this.
	 */
	public CategoryGUI() {
		setLayout(new BorderLayout());

		mList = getData();
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
	private String[] getData() {
		mList = new String[ItemCollection.getCategories().length];
		Object[] hold = ItemCollection.getCategories();
		for (int i = 0; i < hold.length; i ++) {
			mList[i] = hold[i].toString();
		}
		
		if (mList != null) {
			mData = new String[mList.length][mItemColumnNames.length];
			for (int i = 0; i < mList.length; i++) {
				mData[i][0] = mList[i];
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
		
		// A button panel at the top for add
		pnlButtons = new JPanel();
		btnAdd = new JButton("Add Category");
		btnAdd.addActionListener(this);

//		Don't know if I need this or not
		btnEdit = new JButton("Edit Category");
		btnEdit.addActionListener(this);

		pnlButtons.add(btnEdit);
		pnlButtons.add(btnAdd);
		
		add(pnlButtons, BorderLayout.NORTH);

		pnlContent = new JPanel();
		table = new JTable(mData, mItemColumnNames);
//		table.setDefaultEditor(Object.class, null);
		scrollPane = new JScrollPane(table);
		pnlContent.add(scrollPane);
		table.getModel().addTableModelListener(this);

		// Add Panel
		pnlAdd = new JPanel();
		lblTitle = new JLabel("Item name to change:");
		txfTitle = new JTextField(25);
		btnNewCatName = new JButton("Add");
		btnNewCatName.addActionListener(this);
		pnlAdd.add(lblTitle);
		pnlAdd.add(txfTitle);
		pnlAdd.add(btnNewCatName);

		// Edit Panel
		pnlEdit = new JPanel();
		pnlEdit.setLayout(new GridLayout(6, 0));
		String labelNames[] = { "Item name to change:" };
		// Get categories to display in the combo box
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(1, 1));
		Object[] categories = ItemCollection.getCategories();
		if (categories != null) {
			cmbCategories = new JComboBox(categories);
			cmbCategories.setSelectedIndex(0);
			comboPanel.add(new JLabel("Select item's new category:"));
			comboPanel.add(cmbCategories);
			pnlEdit.add(comboPanel);
		}
		
		for (int i = 0; i < labelNames.length; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlEdit.add(panel);
		}

		JPanel panel = new JPanel();
		btnEditCat = new JButton("Change Item Category");
		btnEditCat.addActionListener(this);
		panel.add(btnEditCat);
		pnlEdit.add(panel);

		add(pnlContent, BorderLayout.CENTER);

	}

	/**
	 * Make the buttons work!
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnEdit) {
			pnlContent.removeAll();
			pnlContent.add(pnlEdit);
			pnlContent.revalidate();
			this.repaint();

		} else if (e.getSource() == btnEditCat) {
			preformEditCategory();
		
		} else if (e.getSource() == btnNewCatName) {
			performAddCategory();
		}

	}

	/**
	 * Changes the category of the selected item.
	 */
	private void preformEditCategory() {
		String itemName = txfField[0].getText();
		String category = cmbCategories.getSelectedItem().toString();
		List<Item> possibleItems = ItemCollection.search(itemName); 
		if (possibleItems.isEmpty()) {
			System.out.println("empty list");
			return;
		} else if (possibleItems.size() == 1) {
			Item retrievedItem = possibleItems.get(0);
			if (retrievedItem.getItemCategory().
					getCategory().equalsIgnoreCase(category)) {
				JOptionPane.showMessageDialog(null, "Please enter a new Category");
				return;
			} else {
				ItemCollection.changeCategory(retrievedItem.getName(), category);
				JOptionPane.showMessageDialog(null, "Changed!");
				return;
			}
		} else {
			String[] itemNames = new String[possibleItems.size()];	
			for (int i = 0; i < possibleItems.size(); i++) {
				itemNames[i] = possibleItems.get(i).getName();
			}
			Object response = JOptionPane.showInputDialog(null, "Select an item",
					"Which item do you want to select?", JOptionPane.QUESTION_MESSAGE,
					null, itemNames, "B");
			ItemCollection.changeCategory((String) response, category);
			
			JOptionPane.showMessageDialog(null, "Changed!");
		}
	}
	
	/*
	 * Allows to add a Category.
	 */
	private void performAddCategory() {
		String name = txfTitle.getText();
		if (name.length() == 0) {
			JOptionPane.showMessageDialog(null, "Enter an item name");
			txfTitle.setFocusable(true);
			return;
		}
		
		String message = "Category add failed";
		if (ItemCollection.addCategory(name)) {
			message = "Category added";
		}
		JOptionPane.showMessageDialog(null, message);

		// Clear all text fields.
		txfTitle.setText("");
	}

	/**
	 * Listen to the cell changes on the table. 
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String data = (String) model.getValueAt(row, column);
		if (data != null && ((String) data).length() != 0) {
			if (!ItemCollection.updateCategory("test" ,data)) {
				JOptionPane.showMessageDialog(null, "Update failed");
			}
		}

	}
	
}
