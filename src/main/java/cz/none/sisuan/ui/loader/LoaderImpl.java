package cz.none.sisuan.ui.loader;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoaderImpl implements Loader {

	@Override
	public Stage getWindow(Stage parent, Object controller, String file) {
		try {
			Scene scene = getScene(controller, file);
			return getStage(parent, scene);
		} catch (IOException e) {
			throw new RuntimeException("invalid fxml file", e);
		}
	}

	@Override
	public Stage getMainWindow(Stage mainStage, Object controller, String file) {
		try {
			Scene scene = getScene(controller, file);
			mainStage.setScene(scene);
			mainStage.getScene().setFill(null);
			mainStage.initStyle(StageStyle.TRANSPARENT);
			return mainStage;
		} catch (IOException e) {
			throw new RuntimeException("invalid fxml file", e);
		}
	}

	private Scene getScene(Object controller, String file) throws IOException {
		Parent parent = getParent(controller, file);
		Scene scene = new Scene(parent);
		return scene;
	}

	private Parent getParent(Object controller, String file) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		URL resource = getClass().getResource(file);
		fxmlLoader.setLocation(resource);
		fxmlLoader.setRoot(controller);
		fxmlLoader.setController(controller);
		Object load = fxmlLoader.load();
		return (Parent) load;
	}

	private Stage getStage(Stage parent, Scene scene) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(parent);
		stage.setScene(scene);
		return stage;
	}

}
