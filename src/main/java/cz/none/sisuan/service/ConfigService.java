package cz.none.sisuan.service;

public interface ConfigService {

	public Double getDouble(String key);

	public String getString(String key);

	public void saveValue(String key, Object value);

}
