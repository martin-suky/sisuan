package cz.none.sisuan;

import javafx.application.Application;
import javafx.stage.Stage;
import cz.none.sisuan.ui.UiFactory;

public class Sisuan extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		UiFactory uiFactory = Factory.getUiFactory();
		uiFactory.getMainController(arg0);

	}

}
