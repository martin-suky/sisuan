package cz.none.sisuan.parser;

import java.io.File;
import java.util.List;

import cz.none.sisuan.factory.TypedFactoryObject;

public interface SubtitleParser extends TypedFactoryObject<ParserType> {
	List<Subtitle> parseSubtitles(File file);
}
