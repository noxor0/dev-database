package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sale.Item;
import sale.ItemCategory;

import static org.hamcrest.CoreMatchers.is;

/**
 * Testing something to do with items
 * @author concox
 *
 */

@SuppressWarnings("unused")
public class ItemTest {

	@Before
	public void setUp()  {
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithNameNull() {
			new Item(null,  new ItemCategory("some cateogry"));
		
	}
	
	@Test
	public void testConstructorWithName() {
		Item item = new Item("some item", new ItemCategory("some category"));
		assertNotNull("Item Not null", item);
	}
}