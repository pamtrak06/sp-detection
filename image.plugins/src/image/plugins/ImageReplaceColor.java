package image.plugins;

import image.util.MatrixImageUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageReplaceColor extends AbstractImage implements IProcessImage {

	private static final String PARAM_COLOR_BYTE_FROM = "<Color byte to get>";
	public static final String PARAM_COLOR_BYTE_TO = "<Color byte to put>";

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws Exception {
		read();
		int[][] matrix = MatrixImageUtilities.imageToMatrix2D(sourceImage_);
		int[][] result = MatrixImageUtilities.replaceColorInMatrix1Byte(matrix,
				(Integer) getParameterValue(PARAM_COLOR_BYTE_FROM), (Integer) getParameterValue(PARAM_COLOR_BYTE_TO));
		resultImage_ = MatrixImageUtilities.byteMatrix2DToRgbImage(result);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_COLOR_BYTE_FROM, null);
		addParameter(PARAM_COLOR_BYTE_TO, null);
	}

}
