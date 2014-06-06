package cz.none.sisuan.parser;

import java.util.List;

import cz.none.sisuan.factory.TypedFactoryObject;
import cz.none.sisuan.model.Subtitle;
import cz.none.sisuan.model.SubtitleFile;
import cz.none.sisuan.model.SubtitleFormat;

public interface SubtitleParser extends TypedFactoryObject<SubtitleFormat> {
	List<Subtitle> parseSubtitles(SubtitleFile file);
}
