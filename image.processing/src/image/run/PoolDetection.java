package image.run;

import image.process.WatershedGrayLevel;
import image.ui.ImageUi;
import image.util.ImageUtilities;
import image.util.MatrixLevelUtilities;
import image.util.MatrixDetectUtilities;
import image.util.MatrixImageUtilities;
import image.util.MatrixMorphologicUtilities;
import image.util.WatershedUtilities;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;

public class PoolDetection {
	public static void main(String[] args) {
		int[][] matrix = null; // le tableau contenant les valeurs (entre 0 et
								// 255) de l'image
		int width = 0, height = 0; // les dimension du tableau ci-avant
		boolean isBrightOnDark = true; // "true" si on cherche des objets
										// clairs sur un fond foncé, sinon
										// "false"
		try {
			BufferedImage imgSource = ImageIO.read(new File("data/piscines.png"));
			height = imgSource.getHeight();
			width = imgSource.getWidth();

			// BufferedImage imageFiltered =
			// ImageUtilities.invertFilter(imgSource);
			BufferedImage imageFiltered = imgSource;
			// imageFiltered = ImageUtilities.colorFilter(imageFiltered);
			int[][] byteMatrix = MatrixImageUtilities.imageToMatrix2D(imageFiltered);
			int[][] smoothMatrix = MatrixLevelUtilities.smooth4ByteMatrix(byteMatrix, 2);
			imageFiltered = MatrixImageUtilities.byteMatrix2DToRgbImage(smoothMatrix);

			imageFiltered = MatrixImageUtilities.spectrumToColor(imageFiltered, (float) 0.5, 1, 1, (float) 0.75, 1, 1,
					Color.BLACK);
			BufferedImage grayImage = ImageUtilities.convertToGrayScale(imageFiltered);
			matrix = MatrixImageUtilities.imageToByteMatrix2D(grayImage);

			// création de l'instance
			WatershedGrayLevel ws = new WatershedGrayLevel(matrix, width, height, isBrightOnDark);

			// appel de l'algorithme
			int step = 3; // nombre des niveaux voisins a explorer à chaque
			// remplissage
			boolean shed[][] = ws.process(step);

			BufferedImage imgWs = WatershedUtilities.wsAddMatrixToImage(shed, imgSource);
			int[][] wsBinMatrix = WatershedUtilities.wsMatrixToBinaryMatrix(shed, 0);
			// int[][] matrixDilated =
			// MatrixTopologicUtilities.dilateBinaryMatrixByManhattan(wsBinMatrix,
			// 1);
			// int[][] matrixEroded
			// =MatrixTopologicUtilities.erodeBinaryMatrix(matrixDilated);
			wsBinMatrix = MatrixMorphologicUtilities.invertBinaryMatrix(wsBinMatrix);
			// BufferedImage imgWs =
			// MatrixImageUtilities.byteMatrix2DToRgbImage(wsBinMatrix);
			// Collection<Polygon> list =
			// MatrixDetectUtilities.labelizeConnectedPixel(wsBinMatrix);
			imgWs = MatrixImageUtilities.addBinaryMatrixToImage(wsBinMatrix, imgSource, Color.RED);
			// int[][] matrixWs = ImageUtilities.imageToMatrix(imgWs);

			// le tableau shed[][] contient "true" si le pixel correspondant est
			// sur
			// une ligne de partage des eaux
			ImageUi.loadImage(imgWs);

		} catch (IOException e) {
		}

	}
}
