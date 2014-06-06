package cz.none.sisuan.parser.srt;

import java.util.List;

import cz.none.sisuan.model.Subtitle;
import cz.none.sisuan.service.FileReader.DoWithLine;

public interface SrtLineParser extends DoWithLine {

	List<Subtitle> getSubtitles();

}