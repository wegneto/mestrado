package testsuitegroup90;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import Sorting.CountingSort;

public class CountingSortTest {

	@Test
	public void testCountingSort() {
		int[] arr = { 2, 8, 7, 1, 3, 5, 6, 4 };
		int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8 };
		assertArrayEquals(expected, CountingSort.countingSort(arr, arr.length));
	}

}
