package plugins.manager.image;

public class PluginImage<T> {
	private final String version_;
	private final String name_;
	private T processImage_;

	public PluginImage(String version, String name, T processImage) {
		super();
		version_ = version;
		name_ = name;
		this.processImage_ = processImage;
	}

	public T getProcessImage() {
		return processImage_;
	}

	public void setProcessImage(T processImage) {
		processImage_ = processImage;
	}

	public String getVersion() {
		return version_;
	}

	public String getName() {
		return name_;
	}

}
