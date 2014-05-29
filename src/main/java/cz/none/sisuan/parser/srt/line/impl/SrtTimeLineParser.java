package cz.none.sisuan.parser.srt.line.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.none.sisuan.Constant;
import cz.none.sisuan.parser.Subtitle.SubtitleBuilder;
import cz.none.sisuan.parser.srt.impl.LineType;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParser;

public class SrtTimeLineParser implements SrtConcreteLineParser {

	private static final Pattern linePattern = Pattern.compile(Constant.Pattern.SRT_TIME_LINE);
	private static final SimpleDateFormat sdf = new SimpleDateFormat(Constant.Pattern.SRT_TIME);

	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GTM"));
	}

	@Override
	public LineType getType() {
		return LineType.TIME;
	}

	@Override
	public SubtitleBuilder parse(SubtitleBuilder builder, String line) {

		Matcher matcher = linePattern.matcher(line);
		if (matcher.find()) {
			builder.start(parseTime(matcher.group(1)));
			builder.end(parseTime(matcher.group(2)));
		}

		return builder;
	}

	private Long parseTime(String group) {
		try {
			Date parse = sdf.parse(group);
			return parse.getTime();
		} catch (ParseException e) {
			return null;
		}
	}

}
