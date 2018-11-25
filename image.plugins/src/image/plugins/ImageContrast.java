package image.plugins;

import image.util.MatrixLevelUtilities;
import image.util.MatrixImageUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageContrast extends AbstractImage implements IProcessImage {
	
	public static final String PARAM_FACTOR = "factor";

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
		int[][] resultMatrix = MatrixLevelUtilities.contrast1ByteMatrix(byteMatrix,
				(Integer) getParameterValue(PARAM_FACTOR));
		resultImage_ = MatrixImageUtilities.byteMatrix2DToGrayImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_FACTOR, null);
	}

}
