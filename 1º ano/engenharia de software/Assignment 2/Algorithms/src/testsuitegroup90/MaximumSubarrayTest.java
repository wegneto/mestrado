package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.Test;

import Searching.MaximumSubarray;

public class MaximumSubarrayTest {

	@Test
	public void testMaxCrossingSubarry() {
		int[] arr = { 1, 2, 3, 4, -5, 6, 7, 8, 9, -10 };
		assertEquals(35, MaximumSubarray.maxCrossingSubarry(arr, 0, (arr.length - 1) / 2, arr.length - 1));
	}

	@Test
	public void testMaxSubarray() {
		int[] arr = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		assertEquals(6, MaximumSubarray.maxSubarray(arr, 0, arr.length - 1));
	}

	@Test
	public void testBruteForce() {
		int[] arr = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		assertEquals(6, MaximumSubarray.bruteForce(arr));
	}

}
