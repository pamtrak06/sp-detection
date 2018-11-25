package image.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MatrixImageUtilities {

	public static BufferedImage byteMatrix2DToRgbImage(int[][] matrix) {
		int w = matrix.length;
		int h = matrix[0].length;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				image.setRGB(x, y, matrix[x][y]);
			}
		}
		return image;
	}

	public static BufferedImage byteMatrix2DToGrayImage(int[][] matrix) {
		int w = matrix.length;
		int h = matrix[0].length;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				image.setRGB(x, y, matrix[x][y]);
			}
		}
		return image;
	}

	public static BufferedImage bitMatrix2DToGrayImage(int[][] biMatrix) {
		int w = biMatrix.length;
		int h = biMatrix[0].length;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				image.setRGB(x, y, (biMatrix[x][y] == 1) ? Color.WHITE.getRGB() : Color.BLACK.getRGB());
			}
		}
		return image;
	}

	public static int[][] imageToMatrix2D(BufferedImage image) {
		int h = image.getHeight();
		int w = image.getWidth();
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgb = image.getRGB(x, y);
				matrix[x][y] = rgb;
			}
		}
		return matrix;
	}

	// TODO to be corrected
	public static int[][] imageToBitMatrix2D(BufferedImage image) {
		int h = image.getHeight();
		int w = image.getWidth();
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgb = image.getRGB(x, y);
				int alpha = ((rgb >> 24) & 0xFF);
				int red = ((rgb >> 16) & 0xFF);
				int green = ((rgb >> 8) & 0xFF);
				int blue = (rgb & 0xFF);
				matrix[x][y] = (red > 0 && green > 0 && blue > 0) ? 1 : 0;
			}
		}
		return matrix;
	}

	public static int[][] matrix2DByteToBit(int[][] matrix2D) {
		int w = matrix2D.length;
		int h = matrix2D[0].length;
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				matrix[x][y] = (matrix2D[x][y] > 0) ? 1 : 0;
			}
		}
		return matrix;
	}

	public static int[][] matrix2DBitToByte(int[][] matrix2D) {
		int w = matrix2D.length;
		int h = matrix2D[0].length;
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				matrix[x][y] = (matrix2D[x][y] > 1) ? 255 : 0;
			}
		}
		return matrix;
	}

	// TODO TBC
	public static int[] matrix2DTo1D(int[][] matrix2D) {
		int h = matrix2D.length;
		int w = matrix2D[0].length;
		int[] matrix1D = new int[w * h];
		for (int x = 0; x < h; x++) {
			for (int y = 0; y < w; y++) {
				matrix1D[x * y] = matrix2D[x][y];
			}
		}
		return matrix1D;

	}

	// TODO TBC
	public static int[][] matrix1DTo2D(int[] matrix1D, int h, int w) {
		int[][] matrix2D = new int[h][w];
		for (int x = 0; x < h; x++) {
			for (int y = 0; y < w; y++) {
				matrix2D[x][y] = matrix1D[x * y];
			}
		}
		return matrix2D;
	}

	public static int[] imageToMatrix1D(BufferedImage byteImage) {
		// Send an array 1D : RGB
		int h = byteImage.getHeight();
		int w = byteImage.getWidth();
		int[] matrix = new int[w * h];
		for (int x = 0; x < h; x++) {
			for (int y = 0; y < w; y++) {
				int rgb = byteImage.getRGB(y, x);
				matrix[x * y] = rgb;
			}
		}
		return matrix;
	}

	public static int[][] replaceColorInMatrix1Byte(int[][] grayMatrix, int colSource, int colDest) {
		int w = grayMatrix.length;
		int h = grayMatrix[0].length;
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (grayMatrix[x][y] == colSource) {
					matrix[x][y] = colDest;
				} else {
					matrix[x][y] = grayMatrix[x][y];
				}
			}
		}

		return matrix;
	}

	public static BufferedImage addBinaryMatrixToImage(int[][] binaryMatrix, BufferedImage imageSource,
			Color polyLineColor) {
		BufferedImage image = new BufferedImage(imageSource.getWidth(null), imageSource.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		int h = imageSource.getHeight();
		int w = imageSource.getWidth();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int value = binaryMatrix[x][y];
				if (value == 1) {
					imageSource.setRGB(x, y, polyLineColor.getRGB());
				}
			}
		}
		Graphics g = image.getGraphics();
		g.drawImage(imageSource, 0, 0, null);
		g.dispose();

		return image;
	}

	// public static BufferedImage spectrumToColor(BufferedImage imageSource,
	// Color min, Color max, Color color) {
	// return spectrumToColor(imageSource, min.getRed(), min.getGreen(),
	// min.getBlue(), max.getRed(), max.getGreen(),
	// max.getBlue(), color);
	// }

	public static BufferedImage spectrumToColor(BufferedImage imageSource, float hMin, float sMin, float bMin,
			float hMax, float sMax, float bMax, Color color) {
		BufferedImage image = new BufferedImage(imageSource.getWidth(null), imageSource.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		int h = imageSource.getHeight();
		int w = imageSource.getWidth();
//		Graphics gr = image.getGraphics();
//		gr.drawImage(imageSource, w, h, null);
//		gr.dispose();
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgb = imageSource.getRGB(x, y);
				int a = ((rgb >> 24) & 0xFF);
				int r = ((rgb >> 16) & 0xFF);
				int g = ((rgb >> 8) & 0xFF);
				int b = (rgb & 0xFF);
				// Color col = new Color(r, g, b);

				float[] hsbvals = new float[3];
				hsbvals = Color.RGBtoHSB(r, g, b, hsbvals);

				// Color min = new Color(rMin, gMin, bMin);
				float[] minHsb = new float[] { hMin, sMin, bMin };
				// minHsb = Color.RGBtoHSB(min.getRed(), min.getGreen(),
				// min.getBlue(), minHsb);

				// Color max = new Color(rMax, gMax, bMax);
				float[] maxHsb = new float[] { hMax, sMax, bMax };
				// maxHsb = Color.RGBtoHSB(max.getRed(), max.getGreen(),
				// max.getBlue(), maxHsb);

				if (hsbvals[0] >= minHsb[0] && hsbvals[0] <= maxHsb[0]
				 && hsbvals[1] >= minHsb[1] && hsbvals[1] <= maxHsb[1]
				 && hsbvals[2] >= minHsb[2] && hsbvals[2] <= maxHsb[2]) {
					image.setRGB(x, y, rgb);
				} else {
					image.setRGB(x, y, color.getRGB());
				}

			}
		}

		return image;

	}

	public static int[][] toArgb1DMatrix(BufferedImage byteImage) {
		// Send an array 2D : A, R, V, B
		int h = byteImage.getHeight();
		int w = byteImage.getWidth();
		int[][] matrix = new int[w * h][4];
		for (int x = 0; x < h; x++) {
			for (int y = 0; y < w; y++) {
				int rgb = byteImage.getRGB(y, x);
				int alpha = ((rgb >> 24) & 0xFF);
				matrix[x * w + y][0] = alpha;
				int red = ((rgb >> 16) & 0xFF);
				matrix[x * w + y][1] = red;
				int green = ((rgb >> 8) & 0xFF);
				matrix[x * w + y][2] = green;
				int blue = (rgb & 0xFF);
				matrix[x * w + y][3] = blue;
			}
		}
		return matrix;
	}

	public static int[][] imageToByteMatrix2D(BufferedImage dwordImage) {
		// Send an array 2D : A, R, V, B
		int h = dwordImage.getHeight();
		int w = dwordImage.getWidth();
		int[][] matrix = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgb = dwordImage.getRGB(x, y);
				int a = ((rgb >> 24) & 0xFF);
				int r = ((rgb >> 16) & 0xFF);
				int g = ((rgb >> 8) & 0xFF);
				int b = (rgb & 0xFF);
				// Convert to 8 bits value
				int byteValue = (int) (0.299 * r + 0.587 * g + 0.114 * b);
				matrix[x][y] = byteValue;
			}
		}
		return matrix;
	}

}
