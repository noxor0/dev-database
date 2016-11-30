package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import data.DataConnection;


@SuppressWarnings("unused")
public class DataConnectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConnection() {
		try {
			Connection conn = DataConnection.getConnection();
			assertNotNull(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
