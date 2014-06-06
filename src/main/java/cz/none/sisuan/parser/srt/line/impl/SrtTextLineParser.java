package cz.none.sisuan.parser.srt.line.impl;

import cz.none.sisuan.model.Subtitle.SubtitleBuilder;
import cz.none.sisuan.parser.srt.impl.LineType;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParser;

public class SrtTextLineParser implements SrtConcreteLineParser {

	@Override
	public LineType getType() {
		return LineType.TEXT;
	}

	@Override
	public SubtitleBuilder parse(SubtitleBuilder builder, String line) {
		return builder.text(line);
	}

}
