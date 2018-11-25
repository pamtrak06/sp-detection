package plugins.manager;

public class PluginParameters {
	private final String name_;
	private Object value_;

	public PluginParameters(String name, Object value) {
		super();
		name_ = name;
		value_ = value;
	}

	public String getName() {
		return name_;
	}

	public Object getValue() {
		return value_;
	}

	public void setValue(Object value) {
		value_ = value;
	}

}
