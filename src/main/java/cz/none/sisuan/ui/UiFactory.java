package cz.none.sisuan.ui;

import javafx.stage.Stage;
import cz.none.sisuan.Factory;
import cz.none.sisuan.ui.controller.MainController;
import cz.none.sisuan.ui.controller.SettingsController;

public class UiFactory {

	public MainController getMainController(Stage stage) {
		return new MainController(stage, Factory.getLoader(), Factory.getSubtitleParserFactory(), Factory.getConfigService(), Factory.getSubtitleFactory());
	}

	public SettingsController getSettingsController(Stage parent) {
		return new SettingsController(parent, Factory.getLoader(), Factory.getConfigService());
	}

}
