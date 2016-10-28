package testsuitegroup90;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Geometry.Point;

public class PointTest {

	@Test
	public void testMinus() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1, 1);
		Point expected = new Point(0, 0);
		assertEquals(expected.toString(), p1.minus(p2).toString());
	}

	@Test
	public void testToString() {
		Point p1 = new Point(5, 7);
		assertEquals("(5,7)", p1.toString());
	}

	@Test
	public void testGetCrossProduct() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1, 1);
		assertEquals(0, p1.getCrossProduct(p2));
	}

}
