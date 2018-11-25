package image.plugins;

import image.process.WatershedGrayLevel;
import image.util.MatrixImageUtilities;
import image.util.WatershedUtilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageToWatershed extends AbstractImage implements IProcessImage {

	public static final String PARAM_STEPS = "steps level";

	public String getVersion() {
		return "1.0";
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws Exception {
		read();
		int[][] matrix = MatrixImageUtilities.imageToByteMatrix2D(sourceImage_);
		int width = getWidth();
		int height = getHeight();
		WatershedGrayLevel ws = new WatershedGrayLevel(matrix, width, height, true);
		boolean shed[][] = ws.process((Integer) getParameterValue(PARAM_STEPS));
		int[][] resultMatrix = WatershedUtilities.wsMatrixToBinaryMatrix(shed, 1);
		resultMatrix = MatrixImageUtilities.replaceColorInMatrix1Byte(resultMatrix, 1, Color.WHITE.getRGB());
		resultImage_ = MatrixImageUtilities.byteMatrix2DToRgbImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_STEPS, null);
	}

}
