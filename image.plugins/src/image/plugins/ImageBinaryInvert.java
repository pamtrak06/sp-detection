package image.plugins;

import image.util.MatrixImageUtilities;
import image.util.MatrixMorphologicUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageBinaryInvert extends AbstractImage implements IProcessImage {

	public String getVersion() {
		return "1.0";
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws IOException {
		read();
		int[][] bitMatrix = MatrixImageUtilities.imageToBitMatrix2D(sourceImage_);
		int[][] resultMatrix = MatrixMorphologicUtilities.invertBinaryMatrix(bitMatrix);
		resultImage_ = MatrixImageUtilities.bitMatrix2DToGrayImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public String usage() {
		StringBuffer usage = new StringBuffer();
		usage.append(getName()
				+ " <png image (24 bits with two colors, background=black) file path> <png image result path>");
		return usage.toString();
	}

	@Override
	public void initParameters() {
		// TODO Auto-generated method stub

	}
}
