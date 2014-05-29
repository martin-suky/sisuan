package cz.none.sisuan.parser.srt.line.impl;

import cz.none.sisuan.factory.impl.TypedFactoryImpl;
import cz.none.sisuan.parser.srt.impl.LineType;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParser;
import cz.none.sisuan.parser.srt.line.SrtConcreteLineParserFactory;

public class SrtConcreteLineParserFactoryImpl extends TypedFactoryImpl<SrtConcreteLineParser, LineType>
		implements SrtConcreteLineParserFactory {

	public SrtConcreteLineParserFactoryImpl(SrtConcreteLineParser[] parsers) {
		super(parsers);
	}

}
