package testsuitegroup90;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.DynamicStack;

public class DynamicStackTest {
	
	private DynamicStack<Integer> dynStack;

	@Before
	public void setUp() throws Exception {
		dynStack = new DynamicStack<>();
	}

	@After
	public void tearDown() throws Exception {
		dynStack = null;
	}

	@Test
	public void testPush() {
		dynStack.push(Integer.valueOf(10));
		dynStack.push(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), dynStack.pop());
	}

	@Test
	public void testPop() {
		dynStack.push(Integer.valueOf(10));
		dynStack.push(Integer.valueOf(11));
		assertEquals(Integer.valueOf(11), dynStack.pop());
		assertEquals(Integer.valueOf(10), dynStack.pop());
	}
	
	@Test(expected = EmptyStackException.class)
	public void testPopWhenStackIsEmpty() {
		dynStack.pop();
	}

	@Test
	public void testIsEmptyWhenStackIsEmpty() {
		assertTrue(dynStack.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenStackIsNotEmpty() {
		dynStack.push(Integer.valueOf(10));
		assertFalse(dynStack.isEmpty());
	}
	
}
