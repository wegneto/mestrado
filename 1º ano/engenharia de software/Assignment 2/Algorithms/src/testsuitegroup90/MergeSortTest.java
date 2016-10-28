package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.Test;

import Sorting.MergeSort;

public class MergeSortTest {

	@Test
	public void testCombine() {
		int a1[] = { 1, 2, 3 };
		int a2[] = { 4, 5, 6 };
		int expected[] = { 1, 2, 3, 4, 5, 6 };
		assertArrayEquals(expected, MergeSort.combine(a1, a2));
	}

	@Test
	public void testMergeSort() {
		int arr[] = { 10, 9, 7, 4, 3, 2, 5, 1, 6, 8 };
		int expected[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, MergeSort.mergeSort(arr));
	}

	@Test
	public void testMergeSortEmptyArray() {
		int arr[] = {};
		int expected[] = {};
		assertArrayEquals(expected, MergeSort.mergeSort(arr));
	}

	@Test
	public void testMergeSortArrayWithOneElement() {
		int arr[] = { 1 };
		int expected[] = { 1 };
		assertArrayEquals(expected, MergeSort.mergeSort(arr));
	}

}
