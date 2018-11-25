package plugins.manager.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import plugins.manager.IPlugin;
import plugins.manager.PluginParameters;

public interface IProcessImage extends IPlugin {

	public void setImagePath(String srcImagePath);

	public BufferedImage read() throws IOException;

	public void write(String destImagePath) throws IOException;

	public PluginParameters getNextParameter();

	public BufferedImage process() throws IOException, Exception;

}
