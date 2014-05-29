package cz.none.sisuan.util;

import java.io.File;

public class Util {

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		int lastIndexOf = fileName.lastIndexOf(".");
		int length = fileName.length();
		if (lastIndexOf < 0 || lastIndexOf == length) {
			return "";
		}

		return fileName.substring(lastIndexOf + 1);
	}
}
