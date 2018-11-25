package plugins.manager.image;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ImageMatrix {
	private int[][] matrixBit_ = null;
	private int[][] matrix1Byte_ = null;
	private int[][] matrix2Byte_ = null;
	private int[][] matrix3Byte_ = null;
	
	protected BufferedImage sourceImage_ = null;
	
	
	
	public ImageMatrix(BufferedImage sourceImage) {
		super();
		sourceImage_ = sourceImage;
	}

    public void toMatrix1Bit(){
//    	Raster data = sourceImage_.getData().;
//    	raster.getDataBuffer().
//    	matrixBit_ = MatrixImageUtilities.imageToBitMatrix2D(sourceImage_);
    }

	
}
