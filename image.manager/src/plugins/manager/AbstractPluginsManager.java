package plugins.manager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import plugins.manager.image.IProcessImage;
import plugins.manager.image.PluginImage;

public abstract class AbstractPluginsManager<S extends IPlugin> {
	public static final String EXT_CLASS = ".class";
	public static final String EXT_JAR = ".jar";
	protected final String pluginPath_;
	protected List<PluginImage<S>> listPlugin = new ArrayList<PluginImage<S>>();

	public AbstractPluginsManager(String pluginPath, Class<S> classOfType) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException, IOException {
		super();
		this.pluginPath_ = pluginPath;

		initialize(classOfType);
	}

	protected void initialize(Class<S> classOfType) throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
			InvocationTargetException, IOException {
		loadPlugins(classOfType);
	}

	protected void loadPlugins(Class<S> classOfType) throws ClassNotFoundException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException,
			InvocationTargetException, IOException {
		File path = new File(pluginPath_);
		File[] listFiles = path.listFiles();
		for (File file : listFiles) {
			if (file.getName().endsWith(EXT_JAR)) {
				JarFile jarFile = new JarFile(file);
				Enumeration<JarEntry> entries = jarFile.entries();
				while (entries.hasMoreElements()) {
					JarEntry nextElement = entries.nextElement();
					String name = nextElement.getName();
					if (name.endsWith(EXT_CLASS)) {
						String className = name.substring(0, name.length() - EXT_CLASS.length()).replace("/", ".")
								.replace(" ", "");
						ClassLoader loader = URLClassLoader.newInstance(new URL[] { file.toURI().toURL() }, getClass()
								.getClassLoader());
						Class<?> clazz = Class.forName(className, true, loader);
						Class<?>[] interfaces = clazz.getInterfaces();
						for (Class<?> classTypes : interfaces) {
							if (classTypes.getName().endsWith(classOfType.getName())) {
								Class<? extends S> runClass = clazz.asSubclass(classOfType);
								Constructor<? extends S> ctor = runClass.getConstructor();
								S processImage = ctor.newInstance();
								PluginImage<S> pluginImage = new PluginImage<S>(processImage.getVersion(),
										processImage.getName(), processImage);
								listPlugin.add(pluginImage);
							}
						}
					}
				}
			}
		}
	}

	public PluginImage<S> loadPluginAndParameters(final String name, final String version, String srcImagePath,
			String destImagePath, Object[] parameter) throws Exception {
		PluginImage<S> plugin = getPlugin(name, version);
		if (plugin != null) {
			System.out.print("Process plugin " + plugin.getName() + "...");
			S processImage = plugin.getProcessImage();
			if (parameter != null && parameter.length != 0) {
				int idx = 0;
				PluginParameters nextParameter = processImage.getNextParameter();
				nextParameter.setValue(parameter[idx]);
				while (nextParameter != null) {
					idx++;
					nextParameter = processImage.getNextParameter();
					if (nextParameter != null) {
						nextParameter.setValue(parameter[idx]);
					}
				}
			}
		}
		return plugin;
	}

	protected PluginImage<S> getPlugin(final String name, final String version) {
		boolean isVersion = false;
		PluginImage<S> pluginImage = null;

		// Send the last version (max number) of the existing plugins with
		// specified name
		if (version == null) {
			pluginImage = getPluginLast(name);
		} else {
			// Send plugin with specified name and version
			for (PluginImage<S> plugin : listPlugin) {
				isVersion = plugin.getVersion().equals(version);
				if (plugin.getName().equals(name) && isVersion) {
					pluginImage = plugin;
					break;
				}
			}
		}
		return pluginImage;
	}

	protected PluginImage<S> getPluginLast(final String name) {
		PluginImage<S> pluginImage = null;
		Map<Integer, PluginImage<S>> map = new HashMap<Integer, PluginImage<S>>();
		List<Integer> list = new ArrayList<Integer>();
		for (PluginImage<S> plugin : listPlugin) {
			if (plugin.getName().equals(name)) {
				int versionNum = Integer.parseInt(plugin.getVersion().replace(".", ""));
				map.put(versionNum, plugin);
				list.add(versionNum);
			}
		}
		Collections.sort(list);
		Collections.reverse(list);
		if (!list.isEmpty()) {
			pluginImage = map.get(list.get(0));
		}
		return pluginImage;
	}

	protected String getAvailablePlugins() {
		StringBuffer buffer = new StringBuffer();
		for (PluginImage<S> plugin : listPlugin) {
			buffer.append(plugin.getProcessImage().usage());
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
