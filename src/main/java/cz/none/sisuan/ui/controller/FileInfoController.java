package cz.none.sisuan.ui.controller;

import java.io.File;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.sun.javafx.collections.ImmutableObservableList;

import cz.none.sisuan.model.Encoding;
import cz.none.sisuan.model.SubtitleFile;
import cz.none.sisuan.model.SubtitleFormat;
import cz.none.sisuan.ui.loader.Loader;
import cz.none.sisuan.util.Util;

public class FileInfoController extends GridPane {
	private Stage						stage;

	@FXML
	private Label						fileName;
	@FXML
	private ComboBox<SubtitleFormat>	fileFormat;
	@FXML
	private ComboBox<Encoding>			fileEncoding;

	private SubtitleFile				subtitleFile;

	private File						file;

	public FileInfoController(Stage parent, Loader loader) {
		stage = loader.getWindow(parent, this, "/fxml/file-info.fxml");
		fileFormat.setItems(new ImmutableObservableList<>(SubtitleFormat.values()));
		fileFormat.setValue(SubtitleFormat.SRT);
		fileEncoding.setItems(new ImmutableObservableList<>(Encoding.values()));
		fileEncoding.setValue(Encoding.UTF8);
		stage.show();
	}

	@FXML
	public void ok() {
		subtitleFile = new SubtitleFile(file, fileFormat.getValue(), fileEncoding.getValue());
		this.stage.close();
	}

	@FXML
	public void cancel() {
		subtitleFile = null;
		this.stage.close();
	}

	public void setOnCloseHandler(EventHandler<WindowEvent> handler) {
		this.stage.setOnHiding(handler);
	}

	public void setFile(File file) {
		this.file = file;
		fileName.setText(file.getName());
		String fileExtension = Util.getFileExtension(file);
		try {
			SubtitleFormat valueOf = SubtitleFormat.valueOf(fileExtension.toUpperCase());
			fileFormat.setValue(valueOf);
		} catch (Exception e) {
			return;
		}
	}

	public SubtitleFile getSubtitleFile() {
		return subtitleFile;
	}
}
