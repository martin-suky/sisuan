package cz.none.sisuan.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import cz.none.sisuan.Constant;
import cz.none.sisuan.factory.SubtitleFactory;
import cz.none.sisuan.model.Subtitle;

public class SubtitleChanger extends Thread {

	private static final long	WAIT		= 10;

	private SimpleDateFormat	sdf			= new SimpleDateFormat(Constant.Pattern.LABEL_TIME);
	private ReadWriteLock		lock		= new ReentrantReadWriteLock();
	private long				currentTime	= 0;
	private boolean				run			= true;
	private boolean				pause		= false;
	private boolean				ready		= false;
	private Long				maxTime;

	private Label				subtitleLabel;
	private Label				timeLabel;
	private Slider				timeSlider;
	private SubtitleFactory		factory;

	private Subtitle	subtitle;


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
			long begin = System.currentTimeMillis();
			lock.writeLock().lock();
			Long updateSubtitles = updateSubtitles();
			updateTime(currentTime);
			long duration = null == updateSubtitles ? 0 : updateSubtitles - currentTime;
			long sleep = WAIT;
			if (duration > WAIT + 200) {
				sleep = duration - (200 + WAIT);
			}
			currentTime += sleep;
			lock.writeLock().unlock();
			long wait = sleep - (System.currentTimeMillis() - begin);
			sleepThread(wait >= 0 ? wait : 0);
			while (pause) {
				sleepThread(500);
			}
		}
	}

	public void movetToPercentage(double percent) {
		double time = maxTime * percent;
		moveToTime((long) time);
	}

	public void moveToTime(long time) {
		lock.writeLock().lock();
		factory.jumpSubtitle(time);
		setTime(time);
		lock.writeLock().unlock();
	}

	private void setTime(long time) {
		currentTime = time;
		updateTime(currentTime);
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

	private Long updateSubtitles() {
		Long result = null;
		Subtitle subtitle = factory.nextSubtitle(currentTime);

		if (null == subtitle && this.subtitle != null) {
			setText("");
		} else if (null != subtitle && !subtitle.equals(this.subtitle)) {
			setText(subtitle.getText());
			result = subtitle.getEnd();
		}
		this.subtitle = subtitle;
		return result;
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
		setTime(0);
		factory.useSubtitles(subtitles);
		ready = null != subtitles && subtitles.size() > 0;
		maxTime = subtitles.get(subtitles.size() - 1).getEnd();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				timeSlider.setMin(0);
				timeSlider.setMax(maxTime);
			}
		});
	}

	public boolean isReady() {
		return ready;
	}

	public void pause(boolean pause) {
		this.pause = pause;
	}

	public void stopPlay() {
		pause(true);
		sleepThread(500);
		setTime(0);
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
