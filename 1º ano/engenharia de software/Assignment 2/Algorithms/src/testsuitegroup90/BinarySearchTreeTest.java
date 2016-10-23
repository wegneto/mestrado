package testsuitegroup90;

import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.BinarySearchTree;

public class BinarySearchTreeTest {

	private BinarySearchTree<Integer, Integer> bst;

	@Before
	public void setUp() throws Exception {
		bst = new BinarySearchTree<>();
	}

	@After
	public void tearDown() throws Exception {
		bst = null;
	}

	@Test
	public void testInsertRootNode() {
		bst.insert(1, 1);
		assertEquals(Integer.valueOf(1), bst.get(1));
	}

	@Test
	public void testInsertMultipleNodes() {
		bst.insert(5, 1);
		bst.insert(3, 2);
		bst.insert(7, 3);
		bst.insert(2, 4);
		bst.insert(4, 5);
		bst.insert(6, 6);
		bst.insert(8, 7);

		assertEquals(Integer.valueOf(1), bst.get(5));
	}

	@Test
	public void testGetRoot() {
		bst.insert(5, 1);
		bst.insert(3, 2);
		bst.insert(7, 3);
		assertEquals(Integer.valueOf(1), bst.get(5));
	}

	@Test
	public void testGetLeftNode() {
		bst.insert(5, 1);
		bst.insert(3, 2);
		bst.insert(7, 3);
		assertEquals(Integer.valueOf(2), bst.get(3));
	}

	@Test
	public void testGetRightNode() {
		bst.insert(5, 1);
		bst.insert(3, 2);
		bst.insert(7, 3);
		assertEquals(Integer.valueOf(3), bst.get(7));
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetExeception() {
		assertEquals(Integer.valueOf(3), bst.get(7));
	}

}
