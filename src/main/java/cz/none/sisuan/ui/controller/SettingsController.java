package cz.none.sisuan.ui.controller;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import cz.none.sisuan.service.ConfigService;
import cz.none.sisuan.ui.loader.Loader;

public class SettingsController extends GridPane {
	private Stage	stage;
	private ConfigService	configService;

	public SettingsController(Stage parrent, Loader loader, ConfigService configService) {
		this.configService = configService;
		stage = loader.getWindow(parrent, this, "/fxml/settings.fxml");
		stage.show();
	}
}
