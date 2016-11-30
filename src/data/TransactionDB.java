package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import transaction.*;

/**
 * Handles ineractions with the sql dahabase - specificially selling and purchasing tables
 * 
 * @author concox
 *
 */
public class TransactionDB {
	
	private Connection mConnection;
	
	/**
	 * Adds a sale object to the database.
	 * @param sale to be added
	 * @return success / error message
	 */
	public String addSale(Sale sale) {
		String sql = "insert into Sale(commissionPaid, price, clientId, itemId) values "
				+ "(?, ?, ?, ?); ";

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
			preparedStatement.setDouble(1, sale.getCommission());
			preparedStatement.setDouble(2, sale.getPrice());
			preparedStatement.setInt(3, sale.getClientId());
			preparedStatement.setInt(4, sale.getItemId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		return "Added Item Successfully";
	}
	
	/**
	 * Adds a purchase to the database.
	 * @param purchase to be added
	 * @return success / error message
	 */
	public String addPurchase(Purchase purchase) {
		String sql = "insert into Purchase(cost, `condition`, clientId, itemId) values "
				+ "(?, ?, ?, ?); ";

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
			preparedStatement.setDouble(1, purchase.getCost());
			preparedStatement.setString(2, purchase.getCondition());
			preparedStatement.setInt(3, purchase.getClientId());
			preparedStatement.setInt(4, purchase.getItemId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding item: " + e.getMessage();
		}
		return "Added Item Successfully";
	}

	/**
	 * Gathers a list of the sales from the database. (Unused - not required for assignment)
	 * @return A list of all the sales
	 * @throws SQLException is connecting to the SQL database, connection errors may occur
	 */
	public List<Sale> getSales() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Sale ";

		List <Sale> saleList = new ArrayList<Sale>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("saleId");
				Double commission = rs.getDouble("commissionPaid");
				double price = rs.getDouble("price");
				int itemId = rs.getInt("itemId");
				int clientId = rs.getInt("clientId");
				Sale sale = new Sale(commission, price, itemId, clientId);
				sale.setSaleId(id);
				saleList.add(sale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return saleList;
	}
	
	/**
	 * Gathers a list of the sales from the database.  (Unused - not required for assignment)
	 * @return A list of all the sales
	 * @throws SQLException is connecting to the SQL database, connection errors may occur
	 */
	public List<Purchase> getPurchases() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Purchase ";

		List <Purchase> purchaseList = new ArrayList<Purchase>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("purchaseId");
				String condition = rs.getString("condition");
				double cost = rs.getDouble("cost");
				int itemId = rs.getInt("itemId");
				int clientId = rs.getInt("clientId");
				Purchase purchase = new Purchase(condition, cost, itemId, clientId);
				purchase.setPurchaseId(id);
				purchaseList.add(purchase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return purchaseList;
	}

}
