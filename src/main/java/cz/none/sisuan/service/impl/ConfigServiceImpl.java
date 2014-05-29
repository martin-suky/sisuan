package cz.none.sisuan.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cz.none.sisuan.service.ConfigService;

public class ConfigServiceImpl implements ConfigService {

	private String		file;

	private Properties	properties;

	public ConfigServiceImpl(String file) {
		this.file = file;
		readFile();
	}

	@Override
	public Double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	@Override
	public String getString(String key) {
		return properties.getProperty(key);
	}

	@Override
	public void saveValue(String key, Object value) {
		properties.setProperty(key, value.toString());
		writeFile();
	}

	private void readFile() {
		try (FileInputStream in = new FileInputStream(file)) {
			properties = new Properties();
			properties.load(in);
		} catch (IOException e) {
			throw new RuntimeException("Bad properties file", e);
		}
	}

	private void writeFile() {
		try (FileOutputStream out = new FileOutputStream(file)) {
			properties.store(out, null);
		} catch (IOException e) {
			throw new RuntimeException("Bad properties file", e);
		}
	}

}
