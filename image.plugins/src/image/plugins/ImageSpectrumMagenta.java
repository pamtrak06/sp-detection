package image.plugins;

import image.util.MatrixImageUtilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageSpectrumMagenta extends AbstractImage implements IProcessImage {
	// http://stackoverflow.com/questions/4615029/rgb-range-for-cold-and-warm-colors
	// HSB Red spectrum : Hue min(0) max(0.25)
	// HSB Green spectrum : Hue min(0.25) max(0.5)
	// HSB Blue spectrum : Hue min(0.5) max(0.75)
	// HSB Magenta spectrum : Hue min(0.75) max(1.0)
	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws IOException {
		read();
		int[][] matrix = MatrixImageUtilities.imageToMatrix2D(sourceImage_);
		resultImage_ = MatrixImageUtilities.byteMatrix2DToRgbImage(matrix);
		resultImage_ = MatrixImageUtilities.spectrumToColor(resultImage_, (float) 0.75, 1, 1, (float) 1.0, 1, 1,
				Color.BLACK);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		// TODO Auto-generated method stub
		
	}

}
