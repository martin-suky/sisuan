package cz.none.sisuan.model;

public class Subtitle {
	private Long start;
	private Long end;
	private String text;

	public Subtitle(Long start, Long end, String text) {
		super();
		this.start = start;
		this.end = end;
		this.text = text;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Subtitle [start=" + start + ", end=" + end + ", text=" + text + "]";
	}

	public static class SubtitleBuilder {
		private Long start;
		private Long end;
		private StringBuilder text = new StringBuilder();
		private String delimiter = "";

		public SubtitleBuilder start(Long start) {
			this.start = start;
			return this;
		}

		public SubtitleBuilder end(Long end) {
			this.end = end;
			return this;
		}

		public SubtitleBuilder text(String text) {
			this.text.append(delimiter);
			this.text.append(text);
			delimiter = "\n";
			return this;
		}

		public Subtitle build() {
			if (null != start && null != end && text.length() > 0) {
				return new Subtitle(start, end, text.toString());
			}

			return null;
		}
	}

}
