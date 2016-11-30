package data;

import client.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactis with sql database - specfically handles queries about clients
 * @author concox
 *
 */
public class ClientDB {
	private Connection mConnection;
	private List<Client> mClientList;
	
	/**
	 * Adds a new client to the Client table. 
	 * @param client
	 * @return Returns "Added Client Successfully" or "Error adding client: " with the sql exception.
	 */
	public String addClient(Client client) {
		String sql = "insert into Client(lastName, firstName, middleInitial,"
				+ " streetAddress, city, state, zipcode) values (?, ?, ?, ?, ?, ?, ?); ";

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
			preparedStatement.setString(1, client.getLastName());
			preparedStatement.setString(2, client.getFirstName());
			preparedStatement.setString(3, client.getMid() + "");
			preparedStatement.setString(4, client.getAddress());
			preparedStatement.setString(5, client.getCity());
			preparedStatement.setString(6, client.getState());
			preparedStatement.setString(7, client.getZipcode());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding client: " + e.getMessage();
		}
		return "Added Client Successfully";
	}
	
	/**
	 * Retrieves all clients from the Client table.
	 * 
	 * @return list of clients
	 * @throws SQLException
	 */
	public List<Client> getClients() throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Client ";

		mClientList = new ArrayList<Client>();
		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("clientId");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				char[] charArr = rs.getString("middleInitial").toCharArray();
				char mid = charArr[0];
				String address = rs.getString("streetAddress");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zipcode = rs.getString("zipcode");
				Client client = null;
				if (address == null) {
					client = new Client(firstName, lastName, mid);
					client.setId(new Integer(id).toString());
				} else {
					client = new Client(firstName, lastName, mid, address, city, state, zipcode);
					client.setId(new Integer(id).toString());
				}
				mClientList.add(client);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return mClientList;
	}
	
	/**
	 * Returns all clients that contain the search keyword.
	 * 
	 * @param firstName of the client to be seached
	 * @param lastName of the client to be seached
	 * @return list of clients that match.
	 * @throws SQLException
	 */
	public List<Client> getClients(String firstName, String lastName) throws SQLException {
		List<Client> filterList = new ArrayList<Client>();
		if (mClientList == null) {
			getClients();
		}
		firstName = firstName.toLowerCase();
		lastName = lastName.toLowerCase();
		for (Client client : mClientList) {
			if (client.getFirstName().toLowerCase().contains(firstName)
					&& client.getLastName().toLowerCase().contains(lastName)) {
				filterList.add(client);
			}
		}
		return filterList;
	}
	
	/**
	 * Returns all clients that contain the search keyword.
	 * @param name either first or last name
	 * @return list of clients that match.
	 * @throws SQLException
	 */
	public List<Client> getClients(String name) throws SQLException {
		List<Client> filterList = new ArrayList<Client>();
		if (mClientList == null) {
			getClients();
		}
		name = name.toLowerCase();
		for (Client client : mClientList) {
			if (client.getFirstName().toLowerCase().contains(name)
					|| client.getLastName().toLowerCase().contains(name)) {
				filterList.add(client);
			}
		}
		return filterList;
	}

	/**
	 * Retrieve the client with the given id or null if not found.
	 * @param id
	 * @return client
	 * @throws SQLException
	 */
	public Client getClient(String id) throws SQLException {
		if (mConnection == null) {
			mConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "select * " + "from Client where name = '" + id +"';";

		try {
			stmt = mConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				char[] charArr = rs.getString("middleInitial").toCharArray();
				char mid = charArr[0];
				String address = rs.getString("streetAddress");
				String city = rs.getString("condition");
				String state = rs.getString("category");
				String zipcode = rs.getString("zipcode");
				return new Client(firstName, lastName, mid, address, city, state, zipcode);
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
	 * Modifies the data on a client - Only client location properties can be edited
	 * @param Client to update
	 * @param columnName column to change
	 * @param data the data to repalce the information with
	 * @return Returns a message with success or failure.
	 */
	public String updateClient(Client client, String columnName, Object data) {
		String name = client.getLastName();
		int id = Integer.parseInt(client.getId());
		String sql = "update Client set `" + columnName
				+ "` = ?  where lastName= ? and clientId = ? ";
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
			return "Error updating client: " + e.getMessage();
		}
		return "Updated Client Successfully";
	
	}
	
}
