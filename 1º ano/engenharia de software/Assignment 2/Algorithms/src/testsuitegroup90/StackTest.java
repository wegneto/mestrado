package testsuitegroup90;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.Stack;

public class StackTest {
	
	private Stack<Integer> stack;

	@Before
	public void setUp() throws Exception {
		stack = new Stack<>(10);
	}

	@After
	public void tearDown() throws Exception {
		stack = null;
	}

	@Test
	public void testPush() {
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		assertEquals(Integer.valueOf(9), stack.pop());
	}

	@Test
	public void testPop() {
		stack.push(9);
		assertEquals(Integer.valueOf(9), stack.pop());
	}
	
	@Test(expected = EmptyStackException.class)
	public void testPopWhenStackIsEmpty() {
		stack.pop();
	}

	@Test
	public void testIsEmptyWhenStackIsEmpty() {
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenStackIsFull() {
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		assertFalse(stack.isEmpty());
	}

	@Test
	public void testIsFullWhenStackIsEmpty() {
		assertFalse(stack.isFull());
	}
	
	@Test
	public void testIsFullWhenStackIsFull() {
		for (int i = 0; i < 10; i++) {
			stack.push(i);
		}
		assertTrue(stack.isFull());
	}

}
