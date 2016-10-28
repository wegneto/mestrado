package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.HashMap;

public class HashMapTest {
	
	private HashMap<Integer, Integer> hashMap;

	@Before
	public void setUp() throws Exception {
		hashMap = new HashMap<>();
	}

	@After
	public void tearDown() throws Exception {
		hashMap = null;
	}

	@Test
	public void testGet() {
		hashMap.put(1, 1);
		hashMap.put(2, 2);
		hashMap.put(3, 3);
		hashMap.put(4, 4);
		assertEquals(Integer.valueOf(1), hashMap.get(1));
		assertEquals(Integer.valueOf(2), hashMap.get(2));
		assertEquals(Integer.valueOf(3), hashMap.get(3));
		assertEquals(Integer.valueOf(4), hashMap.get(4));
	}
	
	@Test
	public void testPut() {
		for (int i = 0; i < 20; i++) {
				hashMap.put(i, i + 1);
		}
		assertEquals(20, hashMap.size());
	}

	@Test
	public void testSize() {
		assertEquals(0, hashMap.size());
	}
	
}
