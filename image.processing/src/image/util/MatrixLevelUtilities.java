package image.util;

public final class MatrixLevelUtilities {

	public static int[][] smooth4ByteMatrix(int[][] byteMatrix, int n) {
		int height = byteMatrix.length;
		int width = byteMatrix[0].length;
		int[][] smoothMatrix = new int[height][width];
		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				int a = 0, r = 0, g = 0, b = 0, c = 0;
				for (int subrow = row - n; subrow <= row + n; ++subrow)
					if (subrow >= 0 && subrow < height)
						for (int subcol = col - n; subcol <= col + n; ++subcol)
							if (subcol >= 0 && subcol < width) {
								int rgb = byteMatrix[subrow][subcol];
								a += (rgb >> 24) & 0xff;
								r += (rgb >> 16) & 0xff;
								g += (rgb >> 8) & 0xff;
								b += rgb & 0xff;
								++c;
							}
				a /= c;
				r /= c;
				g /= c;
				b /= c;
				smoothMatrix[row][col] = (a << 24) | (r << 16) | (g << 8) | b;
			}
		}
		return smoothMatrix;
	}

	public static int[][] matrix4ByteTo1Byte(int[][] matrix4Byte) {
		int height = matrix4Byte.length;
		int width = matrix4Byte[0].length;
		int[][] matrix1Byte = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = matrix4Byte[y][x];
				matrix1Byte[y][x] = (int) (rgb * Math.pow(2, 8) / Math.pow(4, 8));
				matrix1Byte[y][x] = (matrix1Byte[y][x] > 255) ? 255 : 0;
			}
		}
		return matrix1Byte;
	}

	public static int[][] contrast1ByteMatrix(int[][] grayMatrix, int n) {
		int height = grayMatrix.length;
		int width = grayMatrix[0].length;
		int[][] contrastedMatrix = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = grayMatrix[y][x];
				contrastedMatrix[y][x] = (rgb > n) ? rgb - n + 127 : rgb + 172;
				// if (n > 127) {
				// contrastedMatrix[y][x] = (rgb > n) ? n : rgb;
				// } else if (n < 127) {
				// contrastedMatrix[y][x] = (rgb < n) ? n : rgb;
				// } else {
				// contrastedMatrix[y][x] = rgb;
				// }
			}
		}
		return contrastedMatrix;
	}

	public static int[][] thresholdMatrix(int[][] grayMatrix, int minLevel, int maxLevel) {
		int height = grayMatrix.length;
		int width = grayMatrix[0].length;
		int[][] contrastedMatrix = new int[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = grayMatrix[y][x];
				contrastedMatrix[y][x] = (rgb < Math.abs(minLevel)) ? minLevel : rgb;
				contrastedMatrix[y][x] = (rgb > maxLevel) ? maxLevel : rgb;
			}
		}
		return contrastedMatrix;
	}

}
