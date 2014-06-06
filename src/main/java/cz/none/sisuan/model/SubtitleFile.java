package cz.none.sisuan.model;

import java.io.File;

public class SubtitleFile {
	private final File				file;
	private final SubtitleFormat	format;
	private final Encoding			encoding;

	public SubtitleFile(File file, SubtitleFormat format, Encoding encoding) {
		super();
		this.file = file;
		this.format = format;
		this.encoding = encoding;
	}

	public File getFile() {
		return file;
	}

	public SubtitleFormat getFormat() {
		return format;
	}

	public Encoding getEncoding() {
		return encoding;
	}

}
