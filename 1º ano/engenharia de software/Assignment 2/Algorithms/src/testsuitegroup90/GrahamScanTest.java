package testsuitegroup90;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Geometry.GrahamScan;
import Geometry.Point;

public class GrahamScanTest {

	@Test
	public void testGetDirectionCollinear() {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1, 1);
		Point p3 = new Point(1, 1);
		assertEquals(GrahamScan.Direction.Collinear, GrahamScan.getDirection(p1, p2, p3));
	}

	@Test
	public void testGetDirectionRightTurn() {
		Point p1 = new Point(-3, -2);
		Point p2 = new Point(-4, -5);
		Point p3 = new Point(-6, -7);
		assertEquals(GrahamScan.Direction.RightTurn, GrahamScan.getDirection(p1, p2, p3));
	}
	
	@Test
	public void testGrahamScanRightTurn() {
		Point[] points = new Point[4];
		points[0] = new Point(1, 3);
		points[1] = new Point(-2, -4);
		points[2] = new Point(-3, -4);
		points[3] = new Point(-4, -5);
		List<Point> expected = new ArrayList<>();
		expected.add(points[3]);
		expected.add(points[1]);
		expected.add(points[0]);
		assertEquals(expected, GrahamScan.grahamScan(points));
	}

	@Test
	public void testGrahamScan() {
		Point[] points = new Point[5];
		points[0] = new Point(5, -6);
		points[1] = new Point(1, 4);
		points[2] = new Point(3, 3);
		points[3] = new Point(6, -2);
		points[4] = new Point(1, 1);
		List<Point> expected = new ArrayList<>();
		expected.add(points[0]);
		expected.add(points[3]);
		expected.add(points[2]);
		expected.add(points[1]);
		expected.add(points[4]);
		assertEquals(expected, GrahamScan.grahamScan(points));
	}
	
	@Test
	public void testGrahamScan2() {
		Point[] points = new Point[3];
		points[0] = new Point(1, 1);
		points[1] = new Point(1, 1);
		points[2] = new Point(1, 1);
		List<Point> expected = new ArrayList<>();
		expected.add(points[0]);
		expected.add(points[2]);
		assertEquals(expected, GrahamScan.grahamScan(points));
	}

}
