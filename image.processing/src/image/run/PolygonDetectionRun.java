package image.run;

import image.ui.ImageUi;
import image.util.MatrixDetectUtilities;
import image.util.MatrixImageUtilities;
import image.util.PolygonUtilities;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

public class PolygonDetectionRun {

	public static void main(String[] args) throws IOException {
		BufferedImage imgSource = ImageIO.read(new File("data/Toulouse-piscines-segmented.png"));
		int h = imgSource.getHeight();
		int w = imgSource.getWidth();
		// int[][] matrix2D = MatrixImageUtilities.imageToMatrix2D(imgSource);
		int[][] matrix2D = MatrixImageUtilities.imageToBitMatrix2D(imgSource);
		int[][] matrixLabel = MatrixDetectUtilities.labelizeRegions(matrix2D, 1);
		Collection<Polygon> listPolygon = MatrixDetectUtilities.matrixLabelToPolygon(matrixLabel);

//		List<Polygon> mergedPolygons = new ArrayList<Polygon>();
//		// for (int x = 0; x < listPolygon.size(); x++) {
//		for (Polygon p1 : listPolygon) {
//			// Polygon p1 = listPolygon.get(x);
//			for (Polygon p2 : listPolygon) {
//				if (PolygonUtilities.doesPolygonIntersectPolygon(p1, p2)) {
//					// Polygon p0 = new Polygon();
//					// for (int i = 0; i < p1.npoints; i++) {
//					// p0.addPoint(p1.xpoints[i], p1.ypoints[i]);
//					// }
//					for (int i = 0; i < p2.npoints; i++) {
//						p1.addPoint(p2.xpoints[i], p2.ypoints[i]);
//						mergedPolygons.add(p1);
//					}
//				}
//			}
//
//		}

		// Sobel sobel = new Sobel();
		// int[] matrix1D = MatrixImageUtilities.matrix2DTo1D(matrix2D);
		// sobel.init(matrix1D, w, h);
		// int[] sobMatrix1D = sobel.process();
		// matrix2D = MatrixImageUtilities.matrix1DTo2D(sobMatrix1D, h, w);

		BufferedImage resultImg = MatrixImageUtilities.byteMatrix2DToRgbImage(matrix2D);
		ImageUi.loadImage(resultImg);

	}
}
