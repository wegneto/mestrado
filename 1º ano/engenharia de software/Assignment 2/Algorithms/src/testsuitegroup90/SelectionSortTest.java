package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.Test;

import Sorting.QuickSort;
import Sorting.SelectionSort;

public class SelectionSortTest {

	@Test
	public void testSelectionSort() {
		int arr[] = { 10, 9, 7, 4, 3, 2, 5, 1, 6, 8 };
		int expected[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, SelectionSort.selectionSort(arr));
	}

}
