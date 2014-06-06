package cz.none.sisuan.service;

import cz.none.sisuan.model.SubtitleFile;

public interface FileReader {
	public interface DoWithLine {
		void doLine(String line);
	}

	void readByLines(SubtitleFile file, DoWithLine doWithLine);
}
