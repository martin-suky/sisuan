package cz.none.sisuan.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import cz.none.sisuan.Constant;
import cz.none.sisuan.factory.SubtitleFactory;
import cz.none.sisuan.parser.Subtitle;

public class SubtitleChanger extends Thread {

	private static final long	WAIT		= 10;

	private SimpleDateFormat	sdf			= new SimpleDateFormat(Constant.Pattern.LABEL_TIME);
	private long				currentTime	= 0;
	private boolean				run			= true;
	private boolean				pause		= false;

	private Label				subtitleLabel;
	private Label				timeLabel;
	private Slider				timeSlider;
	private SubtitleFactory		factory;

	public SubtitleChanger(Label subtitleLabel, Label timeLabel, Slider timeSlider, SubtitleFactory factory) {
		this.subtitleLabel = subtitleLabel;
		this.timeLabel = timeLabel;
		this.timeSlider = timeSlider;
		this.factory = factory;
		sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
	}

	@Override
	public void run() {
		while (run) {
			updateSubtitles();
			updateTime(currentTime);

			currentTime += WAIT;
			do {
				sleepThread(WAIT);
			} while (pause);
		}
	}

	public void moveToTime(long time) {
		boolean isPaused = Boolean.valueOf(pause);
		if (!isPaused) {
			pause(true);
			sleepThread(500);
		}
		factory.jumpSubtitle(time);
		currentTime = time;
		updateTime(currentTime);
		if (!isPaused) {
			pause(false);
		}
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

	private void updateSubtitles() {
		Subtitle subtitle = factory.nextSubtitle(currentTime);

		if (null == subtitle) {
			setText("");
		} else {
			setText(subtitle.getText());
		}

	}

	private void setText(final String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				subtitleLabel.setText(text);
			}
		});
	}

	public void setSubtitles(final List<Subtitle> subtitles) {
		currentTime = 0;
		factory.useSubtitles(subtitles);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				timeSlider.setMin(0);
				timeSlider.setMax(subtitles.get(subtitles.size() - 1).getEnd());
			}
		});
	}

	public void pause(boolean pause) {
		this.pause = pause;
	}

	public void stopPlay() {
		pause(true);
		sleepThread(500);
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
