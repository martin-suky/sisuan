package cz.none.sisuan.ui.loader;

import javafx.stage.Stage;

public interface Loader {
	Stage getWindow(Stage parent, Object controller, String file);

	Stage getMainWindow(Stage mainStage, Object controller, String file);
}
