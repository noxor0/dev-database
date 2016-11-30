package sale;

import data.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds list of methods that help interact with the DB.
 * 
 * @author concox
 *
 */
public class ItemCollection {

	private static ItemDB mItemDB;

	/**
	 * Return a list of items with the matching name.
	 * 
	 * @param name item to look for
	 * @return a list of items that match
	 */
	public static List<Item> search(String name) {
		List<Item> list = new ArrayList<Item>();
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		try {
			return mItemDB.getItems(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * adds a new category to the database.
	 * 
	 * @param category name of the category to add
	 * @return true or false
	 */
	public static boolean addCategory(String category) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		Object[] catagoryArray = getCategories();
		for(Object o : catagoryArray) {
			if(o.toString().equalsIgnoreCase(category)){
				return false;
			}
		}
		String message = mItemDB.addItemCategory(category);
		if (message.startsWith("Error adding item:")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Changes the current category of the item.
	 * @param itemName of the item's category to be changed
	 * @param category the new category
	 * @return true /false
	 */
	public static boolean changeCategory(String itemName, String category) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		String message = mItemDB.changeItemCategory(itemName, category);
		if (message.startsWith("Error adding item:")) {
			return false;
		}
		return true;
	}

	/**
	 * Modify the particular column of the item with the given data.
	 * 
	 * @param item Item to modify
	 * @param column the field in the table to modify
	 * @param data the value for the field
	 * @return true or false
	 */
	public static boolean update(Item item, String column, Object data) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		 
		if (!(column.equals("category") || column.equals("name"))) {
			String message = mItemDB.updateItem(item, column, data);
			if (message.startsWith("Error updating item: ")) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean updateCategory(String OGCategory, String category) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		
		if(!OGCategory.equalsIgnoreCase(category)) {
			String message = mItemDB.updateCategory(OGCategory, category);
			if (message.startsWith("Error updating item: ")) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns all item categories available.
	 * 
	 * @return an array of categories.
	 */
	public static Object[] getCategories() {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}

		try {
			return mItemDB.getItemCategories();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Adds a new item to the data.
	 * 
	 * @param item
	 * @return true or false
	 */
	public static boolean add(Item item) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}

		String message = mItemDB.addItem(item);
		if (message.startsWith("Error adding item:")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Return a item with the given id, null otherwise.
	 * 
	 * @param id item to look for
	 * @return item
	 */
	public static Item getItem(String id) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		try {
			return mItemDB.getItem(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * Return a item with the given id, null otherwise.
	 * 
	 * @param itemName item to look for
	 * @return true/false
	 */
	public static void removeItem(String itemName) {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		mItemDB.removeItem(itemName);
	}

	/**
	 * 
	 * Return all items in the list, null otherwise.
	 * 
	 * @param id item to look for
	 * @return item
	 */
	public static List<Item> getItems() {
		if (mItemDB == null) {
			mItemDB = new ItemDB();
		}
		try {
			return mItemDB.getItems();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
