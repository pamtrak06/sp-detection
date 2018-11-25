package image.plugins;

import image.util.MatrixImageUtilities;
import image.util.MatrixMorphologicUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageBinaryDilate extends AbstractImage implements IProcessImage {

	public static final String PARAM_FACTOR = "factor";

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void initParameters() {
		addParameter(PARAM_FACTOR, null);
	}

	@Override
	public BufferedImage process() throws Exception {
		read();
		int[][] matrix = MatrixImageUtilities.imageToBitMatrix2D(sourceImage_);
		int[][] resultMatrix = MatrixMorphologicUtilities.dilateBinaryMatrixByManhattan(matrix,
				(Integer) getParameterValue(PARAM_FACTOR));
		resultImage_ = MatrixImageUtilities.bitMatrix2DToGrayImage(resultMatrix);
		return null;
	}

	@Override
	public String usage() {
		StringBuffer usage = new StringBuffer();
		usage.append(getName()
				+ " <png image (24 bits with two colors, background=black) file path> <png image result path> <dilatation pixel number>");
		return usage.toString();
	}

}
