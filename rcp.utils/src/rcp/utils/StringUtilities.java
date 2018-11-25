package rcp.utils;

public final class StringUtilities {
	public static String setNotNull(String value) {
		return value == null ? "" : value;
	}
	
	public static int count(final String str, final char needle) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == needle) {
				count++;
			}
		}
		return count;
	}

	public static boolean isIntegerString(final String str) {
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String setFirstLetterToUpperCase(String str) {
		if (str != null) {
			str = str.substring(0,1).toUpperCase() + str.substring(1);
		}
		return str;
	}
}
