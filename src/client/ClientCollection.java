package client;

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
public class ClientCollection {
	
	/**
	 * The client database to use.
	 */
	private static ClientDB mClientDB;
	
	/**
	 * Search for a client by first and last name.
	 * @param firstName of the client to search for
	 * @param lastName of the client to search for
	 * @return a last of clients with the matching names
	 */
	public static List<Client> search(String firstName, String lastName) {
		List<Client> list = new ArrayList<Client>();
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}
		try {
			return mClientDB.getClients(firstName, lastName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Search for a client by either their first or last name
	 * @param name either first or last name to search for
	 * @return a list of clients with a matching first/last name
	 */
	public static List<Client> search(String name) {
		List<Client> list = new ArrayList<Client>();
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}
		try {
			return mClientDB.getClients(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Add a client to the database
	 * @param client to be added to the database
	 * @return true or false
	 */
	public static boolean add(Client client) {
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}

		String message = mClientDB.addClient(client);
		if (message.startsWith("Error adding Client:")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Return a client with the given id, null otherwise.
	 * 
	 * @param id client to look for
	 * @return client
	 */
	public static Client getClient(String id) {
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}
		try {
			return mClientDB.getClient(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all clients from the database
	 * @return A list of all the clients
	 */
	public static List<Client> getClients() {
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}
		try {
			return mClientDB.getClients();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Modify the particular column of the client with the given data.
	 * 
	 * @param client client to modify
	 * @param column the field in the table to modify
	 * @param data the value for the field
	 * @return true or false
	 */
	public static boolean update(Client client, String column, Object data) {
		if (mClientDB == null) {
			mClientDB = new ClientDB();
		}
		// Don't let the users change category or name.
		if (!(column.equals("category") || column.equals("name"))) {
			String message = mClientDB.updateClient(client, column, data);
			if (message.startsWith("Error updating item: ")) {
				return false;
			}
			return true;
		}
		return false;
	}
}
