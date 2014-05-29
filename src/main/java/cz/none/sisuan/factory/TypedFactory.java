package cz.none.sisuan.factory;


public interface TypedFactory<FacObj extends TypedFactoryObject<Type>, Type> {
	public FacObj getObject(Type type);
}
