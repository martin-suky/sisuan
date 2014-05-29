package cz.none.sisuan.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import cz.none.sisuan.Constant;
import cz.none.sisuan.parser.Subtitle;

public class SubtitleChanger extends Thread {

	private static final long WAIT = 10;
	private SimpleDateFormat sdf = new SimpleDateFormat(Constant.Pattern.LABEL_TIME);

	private int subtitlesSize;

	private long currentTime = 0;
	private int currentSubtitle = 0;

	private List<Subtitle> subtitles;

	private Label subtitleLabel;

	private boolean run = true;

	private boolean pause;

	private Label timeLabel;

	private Slider timeSlider;

	public SubtitleChanger(Label subtitleLabel, Label timeLabel, Slider timeSlider) {
		this.subtitleLabel = subtitleLabel;
		this.timeLabel = timeLabel;
		this.timeSlider = timeSlider;
		sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
	}

	@Override
	public void run() {
		Subtitle subtitle = null;
		while (run) {
			subtitle = updateSubtitles(subtitle);
			updateTime(currentTime);

			currentTime += WAIT;
			do {
				sleepThread(WAIT);
			} while (pause);
		}
	}

	public void moveToTime(double time) {

	}

	private void updateTime(final long currentTime) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				timeLabel.setText(sdf.format(new Date(currentTime)));
				timeSlider.setValue(currentTime);
			}
		});
	}

	private Subtitle updateSubtitles(Subtitle subtitle) {
		if (null == subtitle || currentTime == 0) {
			if (currentSubtitle < subtitlesSize) {
				subtitle = subtitles.get(currentSubtitle);
				currentSubtitle++;
			}
		}

		if (subtitle.getStart() < currentTime) {
			setText(subtitle.getText());
		}

		if (subtitle.getEnd() < currentTime) {
			setText("");
			subtitle = null;
		}
		return subtitle;
	}

	private void setText(final String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				subtitleLabel.setText(text);
			}
		});
	}

	public void setSubtitles(List<Subtitle> subtitles) {
		this.subtitles = subtitles;
		this.subtitlesSize = subtitles.size();
		currentSubtitle = 0;
		currentTime = 0;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				timeSlider.setMin(0);
				timeSlider.setMax(SubtitleChanger.this.subtitles.get(subtitlesSize - 1).getEnd());
			}
		});
	}

	public void pause(boolean pause) {
		this.pause = pause;
	}

	public void stopPlay() {
		pause(true);
		sleepThread(500);
		currentSubtitle = 0;
		currentTime = 0;
	}

	public void stopThreadRun() {
		pause = false;
		run = false;
	}

	private void sleepThread(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}
	}

}
