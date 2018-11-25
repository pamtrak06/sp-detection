package image.plugins;

import image.util.MatrixImageUtilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageSpectrum extends AbstractImage implements IProcessImage {
	// http://stackoverflow.com/questions/4615029/rgb-range-for-cold-and-warm-colors
	// HSB Red spectrum : Hue min(0) max(0.25)
	// HSB Green spectrum : Hue min(0.25) max(0.5)
	// HSB Blue spectrum : Hue min(0.5) max(0.75)
	// HSB Magenta spectrum : Hue min(0.75) max(1.0)

	public static final String PARAM_HUE_MIN = "hue min";
	public static final String PARAM_SAT_MIN = "saturation min";
	public static final String PARAM_BRIL_MIN = "brillance min";
	public static final String PARAM_HUE_MAX = "hue max";
	public static final String PARAM_SAT_MAX = "saturation max";
	public static final String PARAM_BRIL_MAX = "brillance max";

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
		resultImage_ = MatrixImageUtilities.byteMatrix2DToRgbImage(matrix);
		resultImage_ = MatrixImageUtilities.spectrumToColor(
				resultImage_,
				//
				Float.parseFloat(getParameterValue(PARAM_HUE_MIN).toString()),
				Float.parseFloat(getParameterValue(PARAM_SAT_MIN).toString()),
				Float.parseFloat(getParameterValue(PARAM_BRIL_MIN).toString()),
				Float.parseFloat(getParameterValue(PARAM_HUE_MAX).toString()),
				Float.parseFloat(getParameterValue(PARAM_SAT_MAX).toString()),
				Float.parseFloat(getParameterValue(PARAM_BRIL_MAX).toString()), Color.BLACK);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_HUE_MIN, null);
		addParameter(PARAM_SAT_MIN, null);
		addParameter(PARAM_BRIL_MIN, null);
		addParameter(PARAM_HUE_MAX, null);
		addParameter(PARAM_SAT_MAX, null);
		addParameter(PARAM_BRIL_MAX, null);
	}

}
