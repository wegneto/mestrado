package testsuitegroup90;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.LinkedList;

public class LinkedListTest {

	private LinkedList<Integer> linkedList;
	
	@Before
	public void setUp() throws Exception {
		linkedList = new LinkedList<>();
	}

	@After
	public void tearDown() throws Exception {
		linkedList = null;
	}

	@Test
	public void testAdd() {
		for (int i = 1; i <= 5; i++) {
			linkedList.add(i);
		}
		assertThat(linkedList, hasItems(1, 2, 3, 4, 5));
	}

	@Test
	public void testGet() {
		linkedList.add(1);
		linkedList.add(2);
		assertEquals(Integer.valueOf(1), linkedList.get(0));
		assertEquals(Integer.valueOf(2), linkedList.get(1));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testGetWithException() {
		linkedList.get(0);
	}

	@Test
	public void testRemove() {
		linkedList.add(1);
		linkedList.add(2);
		linkedList.add(3);
		assertEquals(3, linkedList.size());
		linkedList.remove(2);
		linkedList.remove(1);
		linkedList.remove(0);
		assertEquals(0, linkedList.size());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveWithException() {
		linkedList.remove(1);
	}

	@Test
	public void testSize() {
		linkedList.add(1);
		assertEquals(1, linkedList.size());
	}

	@Test
	public void testIterator() {
		linkedList.add(1);
		for(Integer integer: linkedList) {
			assertEquals(Integer.valueOf(1), integer);
		}
	}
	
}
