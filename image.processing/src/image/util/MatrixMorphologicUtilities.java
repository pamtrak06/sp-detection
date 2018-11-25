package image.util;

//Copyright Stephen Ostermiller 1996-2011
//http://ostermiller.org/dilate_and_erode.html
public final class MatrixMorphologicUtilities {

	public static int[][] invertBinaryMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = (matrix[i][j] == 1 ? 0 : 1);
			}
		}
		return matrix;
	}

	public static int[][] dilateBinaryMatrix(int[][] matrix) {
		int constant = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 1) {
					if (i > 0 && matrix[i - 1][j] == 0)
						matrix[i - 1][j] = constant;
					if (j > 0 && matrix[i][j - 1] == 0)
						matrix[i][j - 1] = constant;
					if (i + 1 < matrix.length && matrix[i + 1][j] == 0)
						matrix[i + 1][j] = constant;
					if (j + 1 < matrix[i].length && matrix[i][j + 1] == 0)
						matrix[i][j + 1] = constant;
				}
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == constant) {
					matrix[i][j] = 1;
				}
			}
		}
		return matrix;
	}

	public static int[][] erodeBinaryMatrix(int[][] matrix) {
		// TODO add parameter
		int constant = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == 1) {
					if (i > 0 && matrix[i - 1][j] == 1)
						erodePixel(matrix, constant, i - 1, j);
					if (j > 0 && matrix[i][j - 1] == 1)
						erodePixel(matrix, constant, i, j - 1);
					if (i + 1 < matrix.length && matrix[i + 1][j] == 1)
						erodePixel(matrix, constant, i + 1, j);
					if (j + 1 < matrix[i].length && matrix[i][j + 1] == 1)
						erodePixel(matrix, constant, i, j + 1);

				}
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] == constant) {
					matrix[i][j] = 0;
				}
			}
		}
		return matrix;
	}

	private static void erodePixel(int[][] matrix, int constant, int i, int j) {
		if (i > 0 && matrix[i - 1][j] == 1)
			matrix[i - 1][j] = constant;
		if (j > 0 && matrix[i][j - 1] == 1)
			matrix[i][j - 1] = constant;
		if (i + 1 < matrix.length && matrix[i + 1][j] == 1)
			matrix[i + 1][j] = constant;
		if (j + 1 < matrix[i].length && matrix[i][j + 1] == 1)
			matrix[i][j + 1] = constant;
	}

	// n^4 (very inefficient) solution for dilate by k
	int[][] dilateBinaryMatrixByK(int[][] image, int k) {
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				if (image[i][j] == 1) {
					for (int l = i - k; l <= i + k; l++) {
						int remainingk = k - Math.abs(i - l);
						for (int m = j - remainingk; m <= j + remainingk; m++) {
							if (l >= 0 && m >= 0 && l < image.length && m < image.length && image[l][m] == 0) {
								image[l][m] = 2;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				if (image[i][j] == 2) {
					image[i][j] = 1;
				}
			}
		}
		return image;
	}

	// Manhattan Oracle : one dimension
	// O(n) solution to find the distance to "on" pixels in a single dimension
	// array
	private static int[] distance(int[] arr) {
		// traverse forwards
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				// first pass and pixel was on, it gets a zero
				arr[i] = 0;
			} else {
				// pixel was off
				// It is at most the length of array
				// away from a pixel that is on
				arr[i] = arr.length;
				// One more than the pixel to the left
				if (i > 0)
					arr[i] = Math.min(arr[i], arr[i - 1] + 1);
			}
		}
		// traverse backwards
		for (int i = arr.length - 1; i >= 0; i--) {
			// what we had on the first pass
			// or one more than the pixel to the right
			if (i + 1 < arr.length)
				arr[i] = Math.min(arr[i], arr[i + 1] + 1);
		}
		return arr;
	}

	// Manhattan Oracle : two dimension
	// O(n^2) solution to find the Manhattan distance to "on" pixels in a two
	// dimension array
	private static int[][] manhattan(int[][] image) {
		// traverse from top left to bottom right
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				if (image[i][j] == 1) {
					// first pass and pixel was on, it gets a zero
					image[i][j] = 0;
				} else {
					// pixel was off
					// It is at most the sum of the lengths of the array
					// away from a pixel that is on
					image[i][j] = image.length + image[i].length;
					// or one more than the pixel to the north
					if (i > 0)
						image[i][j] = Math.min(image[i][j], image[i - 1][j] + 1);
					// or one more than the pixel to the west
					if (j > 0)
						image[i][j] = Math.min(image[i][j], image[i][j - 1] + 1);
				}
			}
		}
		// traverse from bottom right to top left
		for (int i = image.length - 1; i >= 0; i--) {
			for (int j = image[i].length - 1; j >= 0; j--) {
				// either what we had on the first pass
				// or one more than the pixel to the south
				if (i + 1 < image.length)
					image[i][j] = Math.min(image[i][j], image[i + 1][j] + 1);
				// or one more than the pixel to the east
				if (j + 1 < image[i].length)
					image[i][j] = Math.min(image[i][j], image[i][j + 1] + 1);
			}
		}
		return image;
	}

	// n^2 solution with Manhattan oracle
	public static int[][] dilateBinaryMatrixByManhattan(int[][] image, int k) {
		image = manhattan(image);
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[i].length; j++) {
				image[i][j] = ((image[i][j] <= k) ? 1 : 0);
			}
		}
		return image;
	}
}
