package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import transaction.*;

public class TransactionTest {
	
	@Before
	public void setUp() {
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaleConstructorNull() {
			new Sale(null, null, 0, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPurchaseConstructorNull() {
			new Purchase(null, 0.00, 0, 0);
	}
	
	@Test
	public void testAddNewSale(){
		Sale sale = new Sale(1.00, 10.00, 12345, 123);
		assertNotNull("Sale Not Null", sale);
		
	}
	
	@Test
	public void testAddNewPurchase(){
		Purchase purchase = new Purchase("new", 10.00, 12345, 12345);
		assertNotNull("Sale Not Null", purchase);
	}
	
	@Test
	public void testAddPurchase() {
		Purchase purchase = new Purchase("new", 10.00, 12345, 12345);
		assertTrue(TransactionCollection.addPurchase(purchase));
	}
	
	@Test
	public void testAddSale() {
		Sale sale = new Sale(1.00, 10.00, 12345, 123);
		assertTrue(TransactionCollection.addSale(sale));
		
	}
	
	@Test
	public void getSales() {
		List<Sale> list = TransactionCollection.getSales();
		assertNotNull("List of clients not null", list);
	}
	
	@Test
	public void getPurchase() {
		List<Purchase> list = TransactionCollection.getPurchases();
		assertNotNull("List of clients not null", list);
	}
}
