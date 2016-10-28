package testsuitegroup90;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DataStructures.Matrix;

public class MatrixTest {

	private Matrix matrix;

	@Before
	public void setUp() throws Exception {
		matrix = new Matrix(2, 2);
		matrix.set(0,0,1);
		matrix.set(0,1,2);
		matrix.set(1,0,3);
		matrix.set(1,1,4);
	}

	@After
	public void tearDown() throws Exception {
		matrix = null;
	}

	@Test
	public void testGet() {
		assertEquals(1, matrix.get(0, 0));
	}

	@Test
	public void testSet() {
		matrix.set(0, 0, 9);
		assertEquals(9, matrix.get(0, 0));
	}

	@Test
	public void testMultiplyMatrix() {
		Matrix matrix2 = new Matrix(2, 2);
		matrix2.set(0,0,-1);
		matrix2.set(0,1,3);
		matrix2.set(1,0,4);
		matrix2.set(1,1,2);
		
		Matrix product = matrix2.multiply(matrix);
		assertEquals(8, product.get(0, 0));
		assertEquals(10, product.get(0, 1));
		assertEquals(10, product.get(1, 0));
		assertEquals(16, product.get(1, 1));
		
	}

	@Test
	public void testMultiplyInt() {
		Matrix product = matrix.multiply(2);
		assertEquals(2, product.get(0, 0));
		assertEquals(4, product.get(0, 1));
		assertEquals(6, product.get(1, 0));
		assertEquals(8, product.get(1, 1));
	}

	@Test
	public void testToString() {
		String expected = "[1, 2]\n[3, 4]\n";
		assertEquals(expected, matrix.toString());
	}

	@Test
	public void testIdentity() {
		matrix = Matrix.identity(2);
		assertEquals(1, matrix.get(0, 0));
		assertEquals(0, matrix.get(0, 1));
		assertEquals(0, matrix.get(1, 0));
		assertEquals(1, matrix.get(1, 1));
	}
	
}
