package cz.none.sisuan.factory.impl;

import java.util.HashMap;
import java.util.Map;

import cz.none.sisuan.factory.TypedFactory;
import cz.none.sisuan.factory.TypedFactoryObject;

public abstract class TypedFactoryImpl<FacObj extends TypedFactoryObject<Type>, Type> implements
		TypedFactory<FacObj, Type> {

	private Map<Type, FacObj> parsers = new HashMap<>();

	public TypedFactoryImpl(FacObj[] parsers) {
		if (null != parsers) {
			for (FacObj subtitleParser : parsers) {
				this.parsers.put(subtitleParser.getType(), subtitleParser);
			}
		}
	}

	@Override
	public FacObj getObject(Type type) {
		return parsers.get(type);
	}

}
