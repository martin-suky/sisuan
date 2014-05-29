package cz.none.sisuan.parser.srt;

import java.util.List;

import cz.none.sisuan.parser.Subtitle;
import cz.none.sisuan.service.FileReader.DoWithLine;

public interface SrtLineParser extends DoWithLine {

	List<Subtitle> getSubtitles();

}