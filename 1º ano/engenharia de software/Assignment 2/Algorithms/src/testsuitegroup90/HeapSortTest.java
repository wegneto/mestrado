package testsuitegroup90;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import Sorting.HeapSort;

public class HeapSortTest {

	@Test
	public void testHeapSort() {
		int arr[] = { 6, 5, 3, 1, 8, 7, 2, 4 };
		int expected[] = { 8, 7, 6, 5, 4, 3, 2, 1 };

		assertArrayEquals(expected, HeapSort.heapSort(arr));
	}

}
