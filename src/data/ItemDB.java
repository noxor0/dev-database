package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sale.Item;
import sale.ItemCategory;

/**
 * This class contains methods to access Item and ItemCategory tables data.
 * @author mabraham
 *
 */
public class ItemDB {

	private Connection mConnection;
	private List<Item> mItemList;

	/**
	 * Retrieves all items from the Item table.
	 * 
	 * @return list of items
	 * @throws SQLException
	 */
	public List<Item> getItems() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Item ";

		mItemList = new ArrayList<Item>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("itemId");
				String name = rs.getString("name");
				String desc = rs.getString("description");
				double price = rs.getDouble("price");
				String condition = rs.getString("condition");
				String category = rs.getString("category");
				Item item = null;
				if (desc == null) {
					item = new Item(name, new ItemCategory(category));
					item.setId(new Integer(id).toString());
				} else {
					item = new Item(name, desc, price, condition,
							new ItemCategory(category));
					item.setId(new Integer(id).toString());
				}
				mItemList.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return mItemList;
	}

	/**
	 * Retrieves all categories from the ItemCategory table.
	 * 
	 * @return list of categories
	 * @throws SQLException
	 */
	public Object[] getItemCategories() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from ItemCategory ";

		List<String> list = new ArrayList<String>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return list.toArray();
	}

	/**
	 * Returns all items that contain the search keyword in the name or
	 * description(s)
	 * @param name
	 * @return list of items that match.
	 * @throws SQLException
	 */
	public List<Item> getItems(String name) throws SQLException {
		List<Item> filterList = new ArrayList<Item>();
		if (mItemList == null) {
			getItems();
		}
		name = name.toLowerCase();
		// this is a very slow operation - how does google do it?
		for (Item item : mItemList) {
			if (item.getName().toLowerCase().contains(name)) {
				filterList.add(item);
			}
			//used seperate if statement to reduce confusion. 
			if (item.getDescription() != null) {
				if (item.getDescription().toLowerCase().contains(name)) {
					filterList.add(item);
				}				
			}
		}
		return filterList;
	}

	/**
	 * Retrieve the item with the given id or null if not found.
	 * @param id
	 * @return item
	 * @throws SQLException
	 */
	public Item getItem(String id) throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Item where itemId = " + id;

		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String name = rs.getString("name");
				String desc = rs.getString("description");
				double price = rs.getDouble("price");
				String condition = rs.getString("condition");
				String category = rs.getString("category");
				return new Item(name, desc, price, condition, new ItemCategory(
						category));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}
	
	/**
	 * Removes the the item from the database
	 * @param name of the item to be removed
	 * @return success message
	 */
	public String removeItem(String name) {
		String sql = "delete from Item where name='" + name + "';";
		
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding client: " + e.getMessage();
		}
		return "Removed Item Sucessfully!";
	}

	/**
	 * Adds a new item to the Item table. 
	 * @param item
	 * @return Returns "Added Item Successfully" or "Error adding item: " with the sql exception.
	 */
	public String addItem(Item item) {
		String sql = "insert into Item(`name`, description, price, `condition`, category) values "
				+ "(?, ?, ?, ?, ?); ";

		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, item.getName());
			preparedStatement.setString(2, item.getDescription());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.setString(4, item.getCondition());
			preparedStatement.setString(5, item.getItemCategory().getCategory());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		return "Added Item Successfully";
	}
	
	/**
	 * Changes the item's current category to the new category.
	 * @param item
	 * @param category
	 * @return sucess message
	 */
	public String changeItemCategory(String itemName, String category) {
		String sql = "UPDATE Item SET category='" + category 
				+ "' WHERE name='" + itemName +"';";
//		System.out.println(sql);
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		return "Item sucessfully changed";
	}
	
	/**
	 * Adds a category to the data base.
	 * @param category name of the category to add
	 * @return success message 
	 */
	public String addItemCategory(String category) {
		String sql = "insert into ItemCategory values ('" + category + "');";
		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		
		return "Added Item Successfully";
	}

	/**
	 * Modifies the data on an Item - Only description, price and condition can be modified.
	 * @param row
	 * @param columnName
	 * @param data
	 * @return Returns a message with success or failure.
	 */
	public String updateItem(Item item, String columnName, Object data) {
		
		String name = item.getName();
		int id = Integer.parseInt(item.getId());
		String sql = "update Item set `" + columnName
				+ "` = ?  where name= ? and itemId = ? ";
		// For debugging - System.out.println(sql);
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data); 
			else if (data instanceof Double)
				preparedStatement.setDouble(1, (Double) data);
			preparedStatement.setString(2, name); // for name = ?
			preparedStatement.setInt(3, id); // for id = ?
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating item: " + e.getMessage();
		}
		return "Updated Item Successfully";
	
	}
	
	/**
	 * Changes the category name
	 * @param OGCategory old category
	 * @param category new category
	 * @return sucess / fail message
	 */
	public String updateCategory(String OGCategory, String category) {
		String sql = "UPDATE ItemCategory SET category='"+ category
			+ "' WHERE category='"+ OGCategory + "';";

		if (mConnection == null) {
			try {
				mConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		
		return "Updated Category Sucessfully";
	}
}
