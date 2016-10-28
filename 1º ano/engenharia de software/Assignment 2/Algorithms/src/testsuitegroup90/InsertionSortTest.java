package testsuitegroup90;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import Sorting.InsertionSort;

public class InsertionSortTest {

	@Test
	public void testInsertionSort() {
		int arr[] = { 10, 9, 7, 4, 3, 2, 5, 1, 6, 8 };
		int expected[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, InsertionSort.insertionSort(arr));
	}

}
