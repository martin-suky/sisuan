package cz.none.sisuan.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.none.sisuan.model.SubtitleFile;
import cz.none.sisuan.service.FileReader;

public class FileReaderImpl implements FileReader {

	@Override
	public void readByLines(SubtitleFile file, DoWithLine doWithLine) {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(
				file.getFile()), file.getEncoding().getName()))) {

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				doWithLine.doLine(line);
			}
		} catch (IOException e) {
			throw new RuntimeException("error while reading file", e);
		}
	}

}
