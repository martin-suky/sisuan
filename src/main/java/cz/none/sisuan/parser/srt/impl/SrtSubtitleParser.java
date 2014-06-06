package cz.none.sisuan.parser.srt.impl;

import java.util.List;

import cz.none.sisuan.model.SubtitleFile;
import cz.none.sisuan.model.SubtitleFormat;
import cz.none.sisuan.parser.Subtitle;
import cz.none.sisuan.parser.SubtitleParser;
import cz.none.sisuan.parser.srt.SrtLineParser;
import cz.none.sisuan.service.FileReader;

public class SrtSubtitleParser implements SubtitleParser {

	private FileReader fileReader;
	private SrtLineParser lineParser;

	public SrtSubtitleParser(FileReader fileReader, SrtLineParser lineParser) {
		this.fileReader = fileReader;
		this.lineParser = lineParser;
	}

	@Override
	public List<Subtitle> parseSubtitles(SubtitleFile file) {
		fileReader.readByLines(file, lineParser);
		return lineParser.getSubtitles();
	}

	@Override
	public SubtitleFormat getType() {
		return SubtitleFormat.SRT;
	}

}
