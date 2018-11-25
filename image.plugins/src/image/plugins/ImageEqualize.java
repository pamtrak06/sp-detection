package image.plugins;

import image.util.HistogramEq;
import image.util.MatrixImageUtilities;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageEqualize extends AbstractImage implements IProcessImage {
	
	public String getVersion() {
		return "1.0";
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws IOException {
		read();
		int width = getWidth();
		int height = getHeight();
		int size = width * height;
		float grayScale[] = new float[size];
		int histogram[] = new int[256];
		int cdf[] = new int[256];
		float equalized[] = new float[256];
		float picEqualized[] = new float[size];

		File file = new File(srcImagePath_);
		grayScale = HistogramEq.RGB2GS(file);
		histogram = HistogramEq.histogram(grayScale);
		cdf = HistogramEq.getCDF(histogram);
		equalized = HistogramEq.equalization(cdf, size);
		picEqualized = HistogramEq.picEqualized(grayScale, equalized, width, height);

		int counter = 0;
		BufferedImage im = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = im.getRaster();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				raster.setSample(i, j, 0, picEqualized[counter]);
				counter++;
			}
		}

		int[][] resultMatrix = MatrixImageUtilities.imageToMatrix2D(im);
		resultImage_ = MatrixImageUtilities.byteMatrix2DToRgbImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		// TODO Auto-generated method stub

	}

}
