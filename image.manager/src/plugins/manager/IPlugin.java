package plugins.manager;

import java.util.List;

public interface IPlugin {

	public String getVersion();

	public String getName();

	public String usage();

	public void addParameter(String name, Object value);

	public List<String> getListParameters();

	public PluginParameters getParameter(int index);

	public PluginParameters getNextParameter();

	public Object getParameterValue(String name) throws Exception;

}
