package image.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public final class ImageUtilities {

	public static BufferedImage convertToGrayScale(Image image) {
		BufferedImage result = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		BufferedImageOp grayscaleConv = new ColorConvertOp(((BufferedImage) image).getColorModel().getColorSpace(),
				((BufferedImage) image).getColorModel().getColorSpace(), null);
		grayscaleConv.filter((BufferedImage) image, result);
		g.dispose();
		return result;

	}

	public static BufferedImage BlurFilter(BufferedImage image) {
		float[] blurMatrix = { 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f,
				1.0f / 9.0f, 1.0f / 9.0f, 1.0f / 9.0f };
		BufferedImageOp blurFilter = new ConvolveOp(new Kernel(3, 3, blurMatrix), ConvolveOp.EDGE_NO_OP, null);
		return blurFilter.filter(image, null);
	}

	public static BufferedImage colorFilter(BufferedImage image) {
		float[][] colorMatrix = { { 1f, 0f, 0f }, { 0.5f, 1.0f, 0.5f }, { 0.2f, 0.4f, 0.6f } };
		BandCombineOp changeColors = new BandCombineOp(colorMatrix, null);
		Raster sourceRaster = image.getRaster();
		WritableRaster displayRaster = sourceRaster.createCompatibleWritableRaster();
		changeColors.filter(sourceRaster, displayRaster);
		return new BufferedImage(image.getColorModel(), displayRaster, true, null);

	}

	public static BufferedImage sharpenFilter(BufferedImage image) {
		float[] sharpenMatrix = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f };
		BufferedImageOp sharpenFilter = new ConvolveOp(new Kernel(3, 3, sharpenMatrix), ConvolveOp.EDGE_NO_OP, null);
		return sharpenFilter.filter(image, null);
	}

	public static BufferedImage invertFilter(BufferedImage image) {
		byte[] invertArray = new byte[256];
		for (int counter = 0; counter < 256; counter++)
			invertArray[counter] = (byte) (255 - counter);
		BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
		return invertFilter.filter(image, null);

	}
	
	
}
