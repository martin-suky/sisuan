package cz.none.sisuan;

import cz.none.sisuan.parser.SubtitleParser;
import cz.none.sisuan.parser.SubtitleParserFactory;
import cz.none.sisuan.parser.impl.SubtitleParserFactoryImpl;
import cz.none.sisuan.parser.srt.SrtLineParser;
import cz.none.sisuan.parser.srt.impl.SrtLineParserImpl;
import cz.none.sisuan.parser.srt.impl.SrtSubtitleParser;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParser;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParserFactory;
import cz.none.sisuan.parser.srt.line.impl.SrtConcreteLineParserFactoryImpl;
import cz.none.sisuan.parser.srt.line.impl.SrtTextLineParser;
import cz.none.sisuan.parser.srt.line.impl.SrtTimeLineParser;
import cz.none.sisuan.service.ConfigService;
import cz.none.sisuan.service.FileReader;
import cz.none.sisuan.service.impl.ConfigServiceImpl;
import cz.none.sisuan.service.impl.FileReaderImpl;
import cz.none.sisuan.ui.UiFactory;
import cz.none.sisuan.ui.loader.Loader;
import cz.none.sisuan.ui.loader.LoaderImpl;

public class Factory {
	private static final Factory INSTANCE = new Factory();

	private final UiFactory						uiFactory;
	private final Loader						loader;
	private final SubtitleParserFactory 		subtitleParserFactory;
	private final SrtSubtitleParser				srtSubtitleParser;
	private final FileReader					fileReader;
	private final SrtLineParser 				srtLineParser;
	private final SrtConcreteLineParserFactory 	srtConcreteLineParserFactory;
	private final ConfigService					configService;

	private Factory() {
		configService = new ConfigServiceImpl(Constant.CONFIG_FILE);
		loader = new LoaderImpl();
		uiFactory = new UiFactory();
		fileReader = new FileReaderImpl();
		srtConcreteLineParserFactory = new SrtConcreteLineParserFactoryImpl(new SrtConcreteLineParser[] {
				new SrtTextLineParser(), new SrtTimeLineParser() });
		srtLineParser = new SrtLineParserImpl(srtConcreteLineParserFactory);
		srtSubtitleParser = new SrtSubtitleParser(fileReader, srtLineParser);
		subtitleParserFactory = new SubtitleParserFactoryImpl(new SubtitleParser[] { srtSubtitleParser });
	}

	public static UiFactory getUiFactory() {
		return INSTANCE.uiFactory;
	}

	public static Loader getLoader() {
		return INSTANCE.loader;
	}

	public static SubtitleParserFactory getSubtitleParserFactory() {
		return INSTANCE.subtitleParserFactory;
	}

	public static ConfigService getConfigService() {
		return INSTANCE.configService;
	}
}
