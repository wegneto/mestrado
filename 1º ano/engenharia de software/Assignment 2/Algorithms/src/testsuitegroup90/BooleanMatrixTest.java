package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.BooleanMatrix;

public class BooleanMatrixTest {
	
	private BooleanMatrix booleanMatrix;

	@Before
	public void setUp() throws Exception {
		booleanMatrix = new BooleanMatrix(2, 2);
	}

	@After
	public void tearDown() throws Exception {
		booleanMatrix = null;
	}

	@Test
	public void testGetAndSet() {
		booleanMatrix.set(0, 0, true);
		booleanMatrix.set(0, 1, false);
		booleanMatrix.set(1, 0, true);
		booleanMatrix.set(1, 1, false);
		
		assertEquals(true, booleanMatrix.get(0, 0));
	}
	
}
