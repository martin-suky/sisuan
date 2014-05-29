package cz.none.sisuan.parser.impl;

import cz.none.sisuan.factory.impl.TypedFactoryImpl;
import cz.none.sisuan.parser.ParserType;
import cz.none.sisuan.parser.SubtitleParser;
import cz.none.sisuan.parser.SubtitleParserFactory;

public class SubtitleParserFactoryImpl extends TypedFactoryImpl<SubtitleParser, ParserType> implements
		SubtitleParserFactory {

	public SubtitleParserFactoryImpl(SubtitleParser[] parsers) {
		super(parsers);
	}

}
