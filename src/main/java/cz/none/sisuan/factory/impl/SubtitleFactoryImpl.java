package cz.none.sisuan.factory.impl;

import java.util.List;

import cz.none.sisuan.factory.SubtitleFactory;
import cz.none.sisuan.parser.Subtitle;

public class SubtitleFactoryImpl implements SubtitleFactory {

	private int				position;

	private List<Subtitle>	subtitles;

	private Subtitle		lastSubtitle;

	private int				subtitleSize;

	@Override
	public void useSubtitles(List<Subtitle> subtitles) {
		this.subtitles = subtitles;
		subtitleSize = subtitles.size();
		this.position = 0;
	}

	@Override
	public Subtitle nextSubtitle(long currentTime) {
		if (position >= subtitleSize) {
			return null;
		}

		if (lastSubtitle == null || lastSubtitle.getEnd() < currentTime) {
			lastSubtitle = nextInFuture(currentTime);
		}

		if ((lastSubtitle.getStart() <= currentTime && lastSubtitle.getEnd() > currentTime)) {
			return lastSubtitle;
		}


		return null;
	}

	private Subtitle nextInFuture(long currentTime) {
		Subtitle subtitle = null;
		do {
			subtitle = subtitles.get(position++);
		} while (position >= subtitleSize || subtitle.getStart() < currentTime);

		return subtitle;
	}

	@Override
	public Subtitle jumpSubtitle(long selectedTime) {
		SubtitleCrate nearest = subtitleSize == 0 ? SubtitleCrate.EMPTY : findNearest(0, subtitleSize, selectedTime, null);
		lastSubtitle = nearest.getSubtitle();
		position = nearest.getPosition();
		return nearest.getSubtitle();
	}

	private SubtitleCrate findNearest(int first, int last, long time, SubtitleCrate localBest) {
		int countOfPotential = last - first + 1;
		if (countOfPotential <= 0) {
			return localBest;
		}

		int middle = countOfPotential / 2 + first;
		Subtitle subtitle = subtitles.get(middle);

		if (subtitle.getStart() < time) {
			return findNearest(middle + 1, last, time, localBest);
		} else {
			if (localBest != null && localBest.getSubtitle().getStart() < subtitle.getStart()) {
				return findNearest(first, middle - 1, time, localBest);
			} else {
				return findNearest(first, middle - 1, time, new SubtitleCrate(middle, subtitle));
			}
		}

	}

	private static final class SubtitleCrate {
		private static final SubtitleCrate	EMPTY	= new SubtitleCrate(0, null);

		private final int					position;
		private final Subtitle				subtitle;

		public SubtitleCrate(int position, Subtitle subtitle) {
			super();
			this.position = position;
			this.subtitle = subtitle;
		}

		public int getPosition() {
			return position;
		}

		public Subtitle getSubtitle() {
			return subtitle;
		}

	}

}
