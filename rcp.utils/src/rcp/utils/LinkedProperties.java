package rcp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;

public class LinkedProperties extends Properties {

	private static final long serialVersionUID = 3331860834000364607L;
	private String fileName_;
	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();
	private Map<Object, String> comments = new HashMap<Object, String>();

	public Map<Object, String> getComments() {
		return comments;
	}

	public String getComment(String key) {
		return comments.get(key);
	}

	public LinkedProperties(final String fileName) {
		super();
		fileName_ = fileName;
	}

	public LinkedProperties(Properties defaults, final String fileName) {
		super(defaults);
		fileName_ = fileName;
	}

	public Enumeration<Object> keys() {
		return Collections.<Object> enumeration(keys);
	}

	public Object put(Object key, Object value) {
		keys.add(key);
		return super.put(key, value);
	}

	public synchronized Object setProperty(String paramString1,
			String paramString2, String comment) {
		// TODO Auto-generated method stub
		return super.setProperty(paramString1, paramString2);
	}

	@Override
	public void store(Writer arg0, String arg1) throws IOException {
		super.store(arg0, arg1);
	}

	@Override
	public synchronized void load(Reader reader) throws IOException {
		super.load(reader);

		StringBuffer buffer = new StringBuffer();
		FileReader fileReader = new FileReader(fileName_);
		BufferedReader inputStream = new BufferedReader(fileReader);
		String line = inputStream.readLine();

		while (line != null) {
			if (line.trim().startsWith("#")) {
				buffer.append(line);
				buffer.append(SystemConstants.LINE_SEPARATOR);
			} else if (!line.trim().equals("") && line.indexOf('=') != -1) {
				String[] split = line.split("=");
				String key = split[0];
				if (keys.contains(key)) {
					comments.put(key, buffer.toString());
					buffer = new StringBuffer();
				}
			}
			line = inputStream.readLine();
		}
		inputStream.close();
		fileReader.close();
	}

	public static String propertyGet(final String pathname, final String key)
			throws FileNotFoundException, IOException {
		LinkedProperties props = new LinkedProperties(pathname);
		FileReader fileReader = new FileReader(new File(pathname));
		props.load(fileReader);
		fileReader.close();
		return props.getProperty(key);
	}

	public static void propertySet(final String pathname, final String key,
			final String value) throws FileNotFoundException, IOException {
		LinkedProperties props = new LinkedProperties(pathname);
		FileReader fileReader = new FileReader(new File(pathname));
		props.load(fileReader);
		props.setProperty(key, value);
		fileReader.close();
		FileWriter fileWriter = new FileWriter(new File(pathname));
		props.store(fileWriter, GregorianCalendar.getInstance().toString());
		fileWriter.close();
	}

}
