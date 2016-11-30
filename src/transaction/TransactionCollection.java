package transaction;

import java.sql.SQLException;
import java.util.List;

import data.TransactionDB;

/**
 * Holds list of methods that help interact with the DB.
 * 
 * @author concox
 *
 */
public class TransactionCollection {
	
	private static TransactionDB mTransactionDB;
	
	/**
	 * Adds a new sale to the data.
	 * 
	 * @param sale to add to the data
	 * @return true or false
	 */
	public static boolean addSale(Sale sale) {
		if (mTransactionDB == null) {
			mTransactionDB = new TransactionDB();
		}
		String message = mTransactionDB.addSale(sale);
		if (message.startsWith("Error adding item:")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds a new purchase to the data.
	 * 
	 * @param purchase to add to the data
	 * @return true or false
	 */
	public static boolean addPurchase(Purchase purchase) {
		if (mTransactionDB == null) {
			mTransactionDB = new TransactionDB();
		}
		String message = mTransactionDB.addPurchase(purchase);
		if (message.startsWith("Error adding item:")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets all the purchases from the database.
	 * 
	 * @return a list of all the purchases
	 */
	public static List<Sale> getSales() {
		if (mTransactionDB == null) {
			mTransactionDB = new TransactionDB();
		}
		try {
			return mTransactionDB.getSales();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	/**
	 * Gets all the purchases from the database.
	 * 
	 * @return a list of all the purchases
	 */
	public static List<Purchase> getPurchases() {
		if (mTransactionDB == null) {
			mTransactionDB = new TransactionDB();
		}
		try {
			return mTransactionDB.getPurchases();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
