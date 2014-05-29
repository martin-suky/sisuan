package cz.none.sisuan;

public class Constant {

	public static final String	CONFIG_FILE	= "config/config.properties";

	public static final class Config {
		public static final String	FONT_SIZE			= "font.size";
		public static final String	FONT_COLOR			= "font.color";
		public static final String	BACKGROUND_COLOR	= "background.color";
		public static final String	BACKGROUND_OPACITY	= "background.opacity";
		public static final String	WINDOW_WIDTH		= "window.width";
	}

	public static final class Pattern {
		private Pattern() {
		}

		public static final String	SRT_TIME_LINE	= "([0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3}) --> ([0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3})";

		public static final String	SRT_TIME		= "HH:mm:ss,SSS";

		public static final String	LABEL_TIME		= "HH:mm:ss";
	}
}
