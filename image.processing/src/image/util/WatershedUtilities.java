package image.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public final class WatershedUtilities {

	public static BufferedImage wsAddMatrixToImage(boolean[][] wsMatrix, BufferedImage imageSource) {
		BufferedImage image = new BufferedImage(imageSource.getWidth(null), imageSource.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		int h = imageSource.getHeight();
		int w = imageSource.getWidth();
		// Graphics g = image.getGraphics();
		// g.drawImage(imageSource, w, h, null);
		// g.dispose();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				boolean b = wsMatrix[x][y];
				if (b) {
					imageSource.setRGB(x, y, Color.RED.getRGB());
				}
			}
		}
		Graphics g = image.getGraphics();
		g.drawImage(imageSource, 0, 0, null);
		g.dispose();

		return image;
	}

	public static int[][] wsMatrixToBinaryMatrix(boolean[][] wsMatrix, int value) {
		int w = wsMatrix.length;
		int h = wsMatrix[0].length;
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				boolean b = wsMatrix[x][y];
				if (b) {
					matrix[x][y] = value;
				}
			}
		}
		return matrix;
	}

}
