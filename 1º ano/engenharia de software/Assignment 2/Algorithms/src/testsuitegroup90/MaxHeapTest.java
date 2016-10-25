package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.MaxHeap;

public class MaxHeapTest {

	private MaxHeap maxHeap;

	@Before
	public void setUp() throws Exception {
		maxHeap = new MaxHeap();
	}

	@After
	public void tearDown() throws Exception {
		maxHeap = null;
	}

	@Test
	public void testAddSingleItem() {
		maxHeap.add(1);
		assertFalse(maxHeap.isEmpty());
	}

	@Test
	public void testAddMultipleItems() {
		maxHeap.add(1);
		maxHeap.add(3);
		maxHeap.add(2);
		maxHeap.add(5);
		maxHeap.add(6);
		maxHeap.add(8);
		maxHeap.add(7);

		assertFalse(maxHeap.isEmpty());
	}

	@Test
	public void testRemove() {
		maxHeap.add(4);
		maxHeap.add(3);
		maxHeap.add(12);
		maxHeap.add(6);
		maxHeap.add(11);
		assertEquals(12, maxHeap.remove());
		assertEquals(11, maxHeap.remove());
		assertEquals(6, maxHeap.remove());
		assertEquals(4, maxHeap.remove());
		assertEquals(3, maxHeap.remove());
	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(maxHeap.isEmpty());
	}

}
