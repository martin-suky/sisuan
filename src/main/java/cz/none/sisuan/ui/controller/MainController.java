package cz.none.sisuan.ui.controller;

import java.io.File;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import cz.none.sisuan.Constant.Config;
import cz.none.sisuan.Constant.Ico;
import cz.none.sisuan.Factory;
import cz.none.sisuan.factory.SubtitleFactory;
import cz.none.sisuan.parser.ParserType;
import cz.none.sisuan.parser.Subtitle;
import cz.none.sisuan.parser.SubtitleParser;
import cz.none.sisuan.parser.SubtitleParserFactory;
import cz.none.sisuan.service.ConfigService;
import cz.none.sisuan.thread.SubtitleChanger;
import cz.none.sisuan.ui.loader.Loader;
import cz.none.sisuan.util.Util;

public class MainController extends StackPane {

	private static final Duration	DURATION	= Duration.millis(500);
	private Stage					main;
	private SubtitleParserFactory	subtitleParserFactory;
	private List<Subtitle>			parseSubtitles;
	final FadeTransition			fade		= new FadeTransition(DURATION);
	private SubtitleChanger			subtitleChanger;

	@FXML
	private Label					subtitles;
	@FXML
	private Label					timeLabel;
	@FXML
	private Slider					timeSlider;
	@FXML
	private VBox					buttons;
	@FXML
	private StackPane				stackPane;
	@FXML
	private ImageView				ico;
	private double					xOffset;
	private double					yOffset;
	private Loader					loader;
	private ConfigService			configService;
	private SubtitleFactory			subtitleFactory;

	public MainController(Stage main, Loader loader, SubtitleParserFactory subtitleParserFactory,
 ConfigService configService, SubtitleFactory subtitleFactory) {
		this.main = main;
		this.loader = loader;
		this.subtitleParserFactory = subtitleParserFactory;
		this.configService = configService;
		this.subtitleFactory = subtitleFactory;
		this.main = loader.getMainWindow(main, this, "/fxml/main.fxml");

		setFileDragAndDrop(main.getScene());
		fade.setNode(buttons);
		fade.setAutoReverse(true);
		fade.setFromValue(0);
		fade.setToValue(1);
		subtitleChanger = new SubtitleChanger(this.subtitles, timeLabel, timeSlider, subtitleFactory);
		ico.setImage(Ico.EJECT);
		updateFromConfig();
		main.show();

	}

	private void updateFromConfig() {
		Double fontSize = configService.getDouble(Config.FONT_SIZE);
		subtitles.setFont(Font.font("System", fontSize));
		subtitles.setTextFill(Color.web(configService.getString(Config.FONT_COLOR)));
		Color web = Color.web(configService.getString(Config.BACKGROUND_COLOR));
		Double opacity = configService.getDouble(Config.BACKGROUND_OPACITY);
		stackPane.setStyle("-fx-background-color: rgba(" + web.getRed() + ", " + web.getGreen() + ", "
				+ web.getBlue() + ", " + opacity + ");");
		stackPane.setPrefWidth(configService.getDouble(Config.WINDOW_WIDTH));
		stackPane.setPrefHeight(fontSize * 3);
	}

	@FXML
	public void play() {
		if (!subtitleChanger.isAlive()) {
			subtitleChanger.start();

		} else {
			subtitleChanger.pause(false);
		}
		ico.setImage(Ico.PLAY);
	}

	@FXML
	public void pause() {
		ico.setImage(Ico.PAUSE);
		subtitleChanger.pause(true);
	}

	@FXML
	public void stop() {
		ico.setImage(Ico.STOP);
		subtitleChanger.stopPlay();
	}

	@FXML
	public void onMouseEntered(MouseEvent event) {
		fade.setCycleCount(1);
		fade.playFromStart();
	}

	@FXML
	public void onMouseExited(MouseEvent event) {
		fade.setCycleCount(2);
		fade.playFrom(DURATION);
	}

	@FXML
	public void onMousePressed(MouseEvent event) {
		xOffset = main.getX() - event.getSceneX();
		yOffset = main.getY() - event.getSceneY();
	}

	@FXML
	public void onMouseDragged(MouseEvent event) {
		main.setX(event.getSceneX() + xOffset);
		main.setY(event.getSceneY() + yOffset);
	}

	@FXML
	public void onTimeSliderClicked(MouseEvent event) {
		double value = timeSlider.getValue();
		long time = (long) value;
		subtitleChanger.moveToTime(time);
	}

	@FXML
	public void openSettings(MouseEvent event) {
		SettingsController settingsController = Factory.getUiFactory().getSettingsController(main);

	}

	private void setFileDragAndDrop(Scene scene) {
		scene.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		scene.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					File file = db.getFiles().get(0);
					handleDropFile(file);
				}

				event.setDropCompleted(success);
				event.consume();
			}

		});

	}

	protected void handleDropFile(File file) {
		if (!file.isFile()) {
			return;
		}

		String fileExtension = Util.getFileExtension(file);
		ParserType valueOf;
		try {
			valueOf = ParserType.valueOf(fileExtension.toUpperCase());
		} catch (Exception e) {
			return;
		}
		SubtitleParser subtitleParser = subtitleParserFactory.getObject(valueOf);
		parseSubtitles = subtitleParser.parseSubtitles(file);
		subtitleChanger.setSubtitles(parseSubtitles);
		ico.setImage(Ico.STOP);
	}
}
