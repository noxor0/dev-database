package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import client.*;
import sale.ItemCollection;

/**
 * Testing the category methods.
 * Adding a new category to database + Editing an existing item's category
 * @author concox
 *
 */
@SuppressWarnings("unused")
public class ClientTest {

	
	@Before
	public void setUp() {
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNameNull() {
			new Client(null, null, 'a');
	}
	
	@Test
	public void testAddNewClient(){
		Client client = new Client("Connor", "Cox", 'M');
		assertNotNull("Client Not Null", client);
		
	}
	
	@Test
	public void testAddNewClientSecond(){
		Client client = new Client("Connor", "Cox", 'M',
				"143 Street", "City", "WA", "12345");
		assertNotNull("Client Not Null", client);
		
	}
	
	@Ignore
	@Test
	public void testAddClient() {
		assertTrue(ClientCollection.add(new Client("Bob", "William", 'G', "Street"
				,"City", "WA", "12345")));
		
	}
	
	@Test
	public void testGetClients() {
		List<Client> list = ClientCollection.getClients();
		assertNotNull("List of clients not null", list);
	}
	
	
}
