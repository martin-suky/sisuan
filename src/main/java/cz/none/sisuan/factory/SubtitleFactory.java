package cz.none.sisuan.factory;

import java.util.List;

import cz.none.sisuan.model.Subtitle;

public interface SubtitleFactory {

	void useSubtitles(List<Subtitle> subtitles);

	Subtitle nextSubtitle(long currentTime);

	Subtitle jumpSubtitle(long selectedTime);
}
