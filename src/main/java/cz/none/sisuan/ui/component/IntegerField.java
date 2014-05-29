package cz.none.sisuan.ui.component;

import javafx.scene.control.TextField;

public class IntegerField extends TextField {

	@Override
	public void replaceSelection(String arg0) {
		if (validate(arg0)) {
			super.replaceSelection(arg0);
		}
	}

	@Override
	public void replaceText(int arg0, int arg1, String arg2) {
		if (validate(arg2)) {
			super.replaceText(arg0, arg1, arg2);
		}
	}

	private boolean validate(String text) {
		try {
			return "".equals(text) || text.matches("[0-9]");
		} catch (Exception e) {
			return false;
		}
	}

	public int getValue() {
		return Integer.parseInt(getText());
	}

}
