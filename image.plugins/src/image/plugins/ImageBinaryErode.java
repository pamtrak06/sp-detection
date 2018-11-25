package image.plugins;

import image.util.MatrixImageUtilities;
import image.util.MatrixMorphologicUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageBinaryErode extends AbstractImage implements IProcessImage {

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
		int[][] matrix = MatrixImageUtilities.imageToBitMatrix2D(sourceImage_);
		// TODO ADD parameters
		int[][] resultMatrix = MatrixMorphologicUtilities.erodeBinaryMatrix(matrix);
		resultImage_ = MatrixImageUtilities.bitMatrix2DToGrayImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public String usage() {
		StringBuffer usage = new StringBuffer();
		usage.append(getName()
				+ " <png image (24 bits with two colors, background=black) file path> <png image result path> <erosion pixel number>");
		return usage.toString();
	}

	@Override
	public void initParameters() {
		// TODO Auto-generated method stub
		
	}

}
