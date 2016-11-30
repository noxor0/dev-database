package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Unit Test class. Will test all the added implementation (hopefully). 
 * 
 * @author concox
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ DataConnectionTest.class, ItemTest.class,
	TransactionTest.class, ClientTest.class })
public class AllTests {
	// Don't really know how to do this
}

