package com.yuting.mobileReader.utils;

import java.util.LinkedList;
import java.util.List;

public abstract class SystemPropertiesUtils {

	public static String LINE_SEPARATOR = System.getProperty("line.separator");
	public static String FILE_SEPARATOR = System.getProperty("file.separator");
	public static String PATH_SEPARATOR = System.getProperty("path.separator");

	public static int MAX_CONNECTION_TIMEOUT = 1000000;

	public static List<String> INVALID_CHAR_REMOVED_FROM_HTML;

	static {
		INVALID_CHAR_REMOVED_FROM_HTML = new LinkedList<String>();
		INVALID_CHAR_REMOVED_FROM_HTML.add(" ");
		INVALID_CHAR_REMOVED_FROM_HTML.add("&nbsp;");
		INVALID_CHAR_REMOVED_FROM_HTML.add("<p>");
		INVALID_CHAR_REMOVED_FROM_HTML.add("</p>");
		INVALID_CHAR_REMOVED_FROM_HTML.add("<br/>");
	}
}
