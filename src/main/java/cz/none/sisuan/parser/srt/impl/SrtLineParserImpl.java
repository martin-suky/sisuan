package cz.none.sisuan.parser.srt.impl;

import java.util.ArrayList;
import java.util.List;

import cz.none.sisuan.model.Subtitle;
import cz.none.sisuan.model.Subtitle.SubtitleBuilder;
import cz.none.sisuan.parser.srt.SrtLineParser;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParserFactory;

public class SrtLineParserImpl implements SrtLineParser {

	private List<Subtitle> subtitles = new ArrayList<>();
	private SubtitleBuilder builder = new SubtitleBuilder();
	private LineType lastType = LineType.EMPTY;

	private final SrtConcreteLineParserFactory factory;

	public SrtLineParserImpl(SrtConcreteLineParserFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public void doLine(String line) {
		lastType = getLineType(lastType, line);
		switch (lastType) {
		case HEADER:
			return;
		case TIME:
		case TEXT:
			factory.getObject(lastType).parse(builder, line);
			return;
		case EMPTY:
		default:
			Subtitle build = builder.build();
			builder = new SubtitleBuilder();
			if (null != build) {
				subtitles.add(build);
			}
			return;
		}
	}


	@Override
	public List<Subtitle> getSubtitles() {
		return subtitles;
	}

	private LineType getLineType(LineType lastType, String line) {
		if (null == line || line.length() == 0) {
			return LineType.EMPTY;
		}

		switch (lastType) {
		case EMPTY:
			return LineType.HEADER;
		case HEADER:
			return LineType.TIME;
		case TIME:
			return LineType.TEXT;
		case TEXT:
			return LineType.TEXT;
		default:
			return LineType.EMPTY;
		}

	}
}