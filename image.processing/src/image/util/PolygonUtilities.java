package image.util;

import java.awt.Point;
import java.awt.Polygon;

public final class PolygonUtilities {

	/**
	 * Determines if the two polygons supplied intersect each other, by checking
	 * if either polygon has points which are contained in the other. (It
	 * doesn't detect body-only intersections, but is sufficient in most cases.)
	 */
	public static boolean doesPolygonIntersectPolygon(Polygon p1, Polygon p2) {
		Point p;
		for (int i = 0; i < p2.npoints; i++) {
			p = new Point(p2.xpoints[i], p2.ypoints[i]);
			if (p1.contains(p))
				return true;
		}
		for (int i = 0; i < p1.npoints; i++) {
			p = new Point(p1.xpoints[i], p1.ypoints[i]);
			if (p2.contains(p))
				return true;
		}
		return false;
	}

}
