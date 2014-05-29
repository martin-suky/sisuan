package cz.none.sisuan.service;

import java.io.File;

public interface FileReader {
	public interface DoWithLine {
		void doLine(String line);
	}

	void readByLines(File file, DoWithLine doWithLine);
}
