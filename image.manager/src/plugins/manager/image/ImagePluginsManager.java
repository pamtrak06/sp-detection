package plugins.manager.image;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import plugins.manager.AbstractPluginsManager;
import plugins.manager.PluginParameters;

public class ImagePluginsManager extends AbstractPluginsManager<IProcessImage> {

	public ImagePluginsManager(String pluginPath) throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
			InvocationTargetException, IOException {
		super(pluginPath, IProcessImage.class);
	}

	private PluginImage<IProcessImage> runProcessToImageVersion(final String name, Object[] parameter,
			String srcImagePath, String destImagePath, IProcessImage processImage) throws IOException, Exception {
		PluginImage<IProcessImage> plugin = loadPluginAndParameters(name, null, srcImagePath, destImagePath, parameter);
		processImage.setImagePath(srcImagePath);
		processImage.process();
		processImage.write(destImagePath);
		System.out.println("          end");
		return plugin;
	}

	public PluginImage<IProcessImage> runProcessToImage(final String name, String srcImagePath, String destImagePath,
			Object[] parameter) throws Exception {
		PluginImage<IProcessImage> plugin = loadPluginAndParameters(name, null, srcImagePath, destImagePath, parameter);
		return runProcessToImageVersion(name, parameter, srcImagePath, destImagePath, plugin.getProcessImage());
	}

	public PluginImage<IProcessImage> runProcessToImage(final String name, String srcImagePath, String destImagePath)
			throws Exception {
		PluginImage<IProcessImage> plugin = loadPluginAndParameters(name, null, srcImagePath, destImagePath, null);
		return runProcessToImageVersion(name, null, srcImagePath, destImagePath, plugin.getProcessImage());
	}

	private static void processFile() throws Exception {
		ImagePluginsManager manager = new ImagePluginsManager("plugins");
		System.out.println(manager.getAvailablePlugins());

		// http://stackoverflow.com/questions/4615029/rgb-range-for-cold-and-warm-colors
		// HSB Red spectrum : Hue min(0) max(0.25)
		// HSB Green spectrum : Hue min(0.25) max(0.5)
		// HSB Blue spectrum : Hue min(0.5) max(0.75)
		// HSB Magenta spectrum : Hue min(0.75) max(1.0)

//		manager.runProcessToImage("ImageSpectrumBlue", "images/Toulouse-piscines.png", "images/piscines-tlse-blue.png");
		manager.runProcessToImage("ImageSpectrum", "images/Toulouse-piscines.png", "images/piscines-tlse-spct.png",
				new Object[] { 0.5, 1.0, 1.0, 0.6, 1.0, 1.0 });
		// manager.runProcessToImage("ImageToGrayLevel",
		// "images/piscines-tlse-blue.png", "images/piscines-tlse-gray.png");
		// manager.runProcessToImage("ImageStretch",
		// "images/piscines-tlse-gray.png", "images/piscines-tlse-str.png", new
		// Object[] { 0, 4 });
		// manager.runProcessToImage("ImageStretch",
		// "images/piscines-tlse-str.png", "images/piscines-tlse-str.png", new
		// Object[] { 4, 255 });
		// manager.runProcessToImage("ImageReplaceColor",
		// "images/piscines-tlse-str.png", "images/piscines-tlse-eq.png", new
		// Object[] { 4, 255 });
		manager.runProcessToImage("ImageToWatershed", "images/piscines-tlse-spct.png", "images/piscines-tlse-ws.png",
				new Object[] { 3 });

		// manager.runProcessToImage("ImageSmooth", "images/piscines.png",
		// "images/piscines-smoothed.png",
		// new Object[] { 2 });
		// manager.runProcessToImage("ImageSpectrumBlue",
		// "images/piscines-smoothed.png", "images/piscines-blue.png");
		// manager.runProcessToImage("ImageToGrayLevel",
		// "images/piscines-blue.png", "images/piscines-gray.png");
		// manager.runProcessToImage("ImageContrast",
		// "images/piscines-gray.png", "images/piscines-contrast.png",
		// new Object[] { 2 });
		// manager.runProcessToImage("ImageEqualize",
		// "images/piscines-gray.png", "images/piscines-equalized.png");
		// manager.runProcessToImage("ImageStretch",
		// "images/piscines-contrast.png", "images/piscines-threshold.png",
		// new Object[] { -200, 0, 255 });
		// manager.runProcessToImage("ImageToWatershed",
		// "images/piscines-threshold.png", "images/piscines-segmented.png",
		// new Object[] { 3 });
		// manager.runProcessToImage("ImageBinaryInvert",
		// "images/piscines-segmented.png", "images/piscines-inverted.png");
		// manager.runProcessToImage("ImageBinaryDilate",
		// "images/piscines-segmented.png", "images/piscines-dilated.png",
		// new Object[] { 2 });
		// manager.runProcessToImage("ImageBinaryErode",
		// "images/piscines-segmented.png", "images/piscines-eroded.png");
	}

	public static void main(String[] args) throws Exception {
		processFile();

	}
}
