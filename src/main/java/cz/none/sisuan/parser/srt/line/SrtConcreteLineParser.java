package cz.none.sisuan.parser.srt.line;

import cz.none.sisuan.factory.TypedFactoryObject;
import cz.none.sisuan.model.Subtitle.SubtitleBuilder;
import cz.none.sisuan.parser.srt.impl.LineType;

public interface SrtConcreteLineParser extends TypedFactoryObject<LineType> {
	SubtitleBuilder parse(SubtitleBuilder builder, String line);
}
