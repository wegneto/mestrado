package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.Test;

import Searching.BinarySearch;

public class BinarySearchTest {

	@Test
	public void testBinarySearchSortedArray() {
		int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertEquals(3, BinarySearch.binarySearch(arr, 4));
	}
	
	@Test
	public void testBinarySearchUnsorted() {
		int arr[] = { 10, 5, 3, 4, 2, 6, 7, 9, 8, 1 };
		assertEquals(-1, BinarySearch.binarySearch(arr, 4));
	}

}
