package testsuitegroup90;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.ArrayList;

public class ArrayListTest {
	
	private ArrayList<String> arrayList;
	
	@Before
	public void setUp() throws Exception {
		arrayList = new ArrayList<>();
	}

	@After
	public void tearDown() throws Exception {
		arrayList = null;
	}

	@Test
	public void testAddSingleItem() {
		arrayList.add("test");
		assertEquals(1, arrayList.size());
	}
	
	@Test
	public void testAddMultipleItems() {
		for (int i = 0; i < 11; i++) {
			arrayList.add("test " + i);	
		}
		assertEquals(11, arrayList.size());
	}

	@Test
	public void testGet() {
		for (int i = 0; i < 11; i++) {
			arrayList.add("test " + i);	
		}
		assertEquals("test 0", arrayList.get(0));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetException() {
		arrayList.add("test");
		arrayList.get(1);
	}

	@Test
	public void testSize() {
		arrayList.add("test");
		assertEquals(1, arrayList.size());
	}

	@Test
	public void testRemove() {
		for (int i = 0; i < 11; i++) {
			arrayList.add("test " + i);	
		}
		arrayList.remove(0);
		assertEquals(10, arrayList.size());
	}

}
