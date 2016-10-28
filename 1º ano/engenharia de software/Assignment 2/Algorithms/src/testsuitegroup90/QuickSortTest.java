package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.Test;

import Sorting.QuickSort;

public class QuickSortTest {

	@Test
	public void testPartition() {
		int arr[] = { 10, 9, 7, 4, 3, 2, 5, 1, 6, 8 };
		assertEquals(7, QuickSort.partition(arr, 0, arr.length - 1));
	}

	@Test
	public void testQuickSort() {
		int arr[] = { 10, 9, 7, 4, 3, 2, 5, 1, 6, 8 };
		int expected[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		QuickSort.quickSort(arr);
		assertArrayEquals(expected, arr);
	}

}
