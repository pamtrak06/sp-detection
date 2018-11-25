package image.plugins;

import image.util.MatrixImageUtilities;
import image.util.MatrixLevelUtilities;

import java.awt.image.BufferedImage;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageStretch extends AbstractImage implements IProcessImage {

	public static final String PARAM_LEVEL_MIN = "level min";
	public static final String PARAM_LEVEL_MAX = "level max";

	public String getVersion() {
		return "1.0";
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws Exception {
		read();
		int[][] byteMatrix = MatrixImageUtilities.imageToMatrix2D(sourceImage_);
		int[][] resultMatrix = MatrixLevelUtilities.thresholdMatrix(byteMatrix,
				(Integer) getParameterValue(PARAM_LEVEL_MIN), (Integer) getParameterValue(PARAM_LEVEL_MAX));
		resultImage_ = MatrixImageUtilities.byteMatrix2DToGrayImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_LEVEL_MIN, null);
		addParameter(PARAM_LEVEL_MAX, null);
	}

}
