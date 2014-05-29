package cz.none.sisuan.ui.controller;

import javafx.stage.Stage;

public interface BaseController {

	public void setStage(Stage mystage);

	public void setParams(Object... params);

	public void start();

}
