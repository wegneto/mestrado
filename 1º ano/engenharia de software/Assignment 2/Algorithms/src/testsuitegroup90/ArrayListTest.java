package testsuitegroup90;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.ArrayList;

public class ArrayListTest {

	private ArrayList<Integer> arrayList;

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
		arrayList.add(1);
		assertThat(arrayList, hasItem(1));
	}

	@Test
	public void testAddMultipleItems() {
		for (int i = 0; i < 11; i++) {
			arrayList.add(i);
		}
		assertThat(arrayList, hasItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

	}

	@Test
	public void testGet() {
		for (int i = 0; i < 11; i++) {
			arrayList.add(i);
		}
		assertEquals(Integer.valueOf(0), arrayList.get(0));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetException() {
		arrayList.add(0);
		arrayList.get(1);
	}

	@Test
	public void testSize() {
		arrayList.add(0);
		assertEquals(1, arrayList.size());
	}

	@Test
	public void testRemove() {
		for (int i = 0; i < 11; i++) {
			arrayList.add(i);
		}
		arrayList.remove(0);
		assertThat(arrayList, hasItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
	}

}
