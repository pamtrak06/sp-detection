package plugins.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractPlugin implements IPlugin {

	protected Map<String, PluginParameters> mapParams_ = new HashMap<String, PluginParameters>();
	protected Map<String, Integer> mapOrder_ = new HashMap<String, Integer>();
	protected int paramIndex_ = 0;
	protected List<String> paramNameList_ = new ArrayList<String>();

	public AbstractPlugin() {
		super();
		initParameters();
	}

	public abstract String getVersion();

	public abstract String getName();

	public abstract void initParameters();
	
	public List<String> getListParameters() {
		return paramNameList_;
	}

	// TODO respect parameter order : false with iterator !!!
	public PluginParameters getParameter(int index) {
		PluginParameters parameters = null;
		Iterator<String> iterator = mapParams_.keySet().iterator();
		int idx = 0;
		while (iterator.hasNext()) {
			String key = iterator.next();
			idx = mapOrder_.get(key);
			if (idx == index) {
				parameters = mapParams_.get(key);
				break;
			}
		}
		return parameters;
	}

	public PluginParameters getNextParameter() {
		PluginParameters parameter = getParameter(paramIndex_);
		if (parameter == null) {
			paramIndex_ = 0;
		} else {
			paramIndex_++;
		}
		return parameter;
	}

	public Object getParameterValue(String name) throws Exception {
		PluginParameters pluginParameters = mapParams_.get(name);
		if (pluginParameters == null) {
			throw new Exception("Parameter [" + name + "] must be defined");
		}
		Object value = pluginParameters.getValue();
		if (value == null) {
			throw new Exception("Value of parameter [" + name + "] must be defined");
		}
		return value;
	}

	public void addParameter(String name, Object value) {
		PluginParameters parameters = new PluginParameters(name, value);
		mapParams_.put(name, parameters);
		paramNameList_.add(name);
		mapOrder_.put(name, paramNameList_.size() - 1);
	}


}
