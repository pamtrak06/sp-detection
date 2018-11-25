package image.plugins;

import image.util.MatrixImageUtilities;
import image.util.MatrixLevelUtilities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.image.AbstractImage;
import plugins.manager.image.IProcessImage;

public class ImageToGrayLevel extends AbstractImage implements IProcessImage {
	
	public String getVersion() {
		return "1.0";
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public BufferedImage process() throws IOException {
		read();
		int[][] resultMatrix = MatrixImageUtilities.imageToByteMatrix2D(sourceImage_);
//		resultMatrix = MatrixLevelUtilities.matrix4ByteTo1Byte(resultMatrix);
		resultImage_ = MatrixImageUtilities.byteMatrix2DToGrayImage(resultMatrix);
		return resultImage_;
	}

	@Override
	public void initParameters() {
		// TODO Auto-generated method stub
		
	}

}
