package image.util;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixDetectUtilities {

	public static int[][] labelizeRegions(int[][] matrix, int regionPixelValue) {
		int w = matrix.length;
		int h = matrix[0].length;
		int[][] matrixLabel = new int[w][h];
		int labelId = 1;
		int previousId = 1;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (matrix[x][y] == regionPixelValue) {
					if (x > 0 && matrix[x - 1][y] == regionPixelValue) {
						if (matrixLabel[x][y] == 0) {
							matrixLabel[x - 1][y] = labelId;
						} else {
							matrixLabel[x - 1][y] = matrixLabel[x][y];
						}
					}
					if (y > 0 && matrix[x][y - 1] == regionPixelValue) {
						if (matrixLabel[x][y] == 0) {
							matrixLabel[x][y - 1] = labelId;
						} else {
							matrixLabel[x][y - 1] = matrixLabel[x][y];
						}
					}
					if (x + 1 < w && matrix[x + 1][y] == regionPixelValue) {
						if (matrixLabel[x][y] == 0) {
							matrixLabel[x + 1][y] = labelId;
						} else {
							matrixLabel[x + 1][y] = matrixLabel[x][y];
						}
					}
					if (y + 1 < h && matrix[x][y + 1] == regionPixelValue) {
						if (matrixLabel[x][y] == 0) {
							matrixLabel[x][y + 1] = labelId;
						} else {
							matrixLabel[x][y + 1] = matrixLabel[x][y];
						}
					}
					if (previousId % 2 != 0) {
						previousId++;
					}
				} else {
					if (previousId % 2 == 0) {
						labelId++;
						previousId++;
					}

				}
			}
		}
		return matrixLabel;
	}

	public static Collection<Polygon> matrixLabelToPolygon(int[][] matrixLabel) {
		List<Polygon> listPolygon = new ArrayList<Polygon>();
		Map<Integer, Polygon> mapPolyg = new HashMap<Integer, Polygon>();

		int w = matrixLabel.length;
		int h = matrixLabel[0].length;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				Polygon polygon = null;
				if (matrixLabel[x][y] != 0 && mapPolyg.get(matrixLabel[x][y]) == null) {
					polygon = new Polygon();
					polygon.addPoint(x, y);
					listPolygon.add(polygon);
					mapPolyg.put(matrixLabel[x][y], polygon);
				} else if (matrixLabel[x][y] != 0) {
					polygon = mapPolyg.get(matrixLabel[x][y]);
					polygon.addPoint(x, y);
				}
			}
		}
		return listPolygon;
	}

	// TODO To be ended
	public static Collection<Polygon> labelizeConnectedPixel(int[][] matrix) {
		List<Polygon> list = new ArrayList<Polygon>();
		Map<Integer, Polygon> mapPolyg = new HashMap<Integer, Polygon>();

		int lengthI = matrix.length;
		for (int i = 0; i < lengthI; i++) {
			int lengthJ = matrix[i].length;
			int holeNumber = 0;
			int indexPolyg = 0;
			Polygon polygon = null;
			for (int j = 0; j < lengthJ; j++) {
				int value = matrix[i][j];
				if (value == 1) {
					if (j + 1 < lengthJ && matrix[i][j + 1] == 0) {
						if (mapPolyg.get(indexPolyg) != null) {
							polygon = mapPolyg.get(indexPolyg);
						} else if (holeNumber % 2 == 0) {
							holeNumber = 0;
							polygon = new Polygon();
							mapPolyg.put(indexPolyg, polygon);
							list.add(polygon);
							indexPolyg++;
						}
						polygon.addPoint(i, j);

						holeNumber++;
					}
					// else if (j - 1 >= 0 && matrix[i][j - 1] == 0) {
					// if (mapPolyg.get(indexPolyg) != null) {
					// polygon = mapPolyg.get(indexPolyg);
					// } else if (holeNumber % 2 == 0) {
					// holeNumber = 0;
					// polygon = new Polygon();
					// mapPolyg.put(indexPolyg, polygon);
					// list.add(polygon);
					// indexPolyg++;
					// }
					// polygon.addPoint(i, j);
					// holeNumber++;
					// }
				}
			}
		}
		return list;

	}

}
