package plugins.manager.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public abstract class AbstractImage extends AbstractMatrix {
	protected BufferedImage sourceImage_ = null;
	protected BufferedImage resultImage_ = null;
	protected String srcImagePath_ = null;


	public AbstractImage() {
		super();
	}


	public String usage() {
		StringBuffer usage = new StringBuffer();
		usage.append(getName() + " <png image file path> <png image result path> " + paramNameList_.toString());
		return usage.toString();
	}

	public void setImagePath(String srcImagePath) {
		srcImagePath_ = srcImagePath;
	}

	public BufferedImage read() throws IOException {
		sourceImage_ = ImageIO.read(new File(srcImagePath_));
		return sourceImage_;
	}

	public int getHeight() {
		return (sourceImage_ == null) ? -1 : sourceImage_.getHeight();
	}

	public int getWidth() {
		return (sourceImage_ == null) ? -1 : sourceImage_.getWidth();
	}

	public void write(String destImagePath) throws IOException {
		File outputfile = new File(destImagePath);
		ImageIO.write(resultImage_, "png", outputfile);
	}

}
